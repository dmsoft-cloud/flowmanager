/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dmsoft.flowmanager.agent.engine.core.as400;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.JobDescription;
import com.ibm.as400.access.ObjectDescription;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.ObjectList;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.access.RequestNotSupportedException;
import com.ibm.as400.access.SecureAS400;

import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.exception.AS400ObjectNotFoundException;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.GenericAS400Param;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

/**
 * Classe che permette di eseguire le chiamate su AS400
 * 
 * @author davidec
 */
public class CallAs400 {
	
	private static final Logger logger = Logger.getLogger(CallAs400.class.getName());

	private static Map<GenericAS400Param, CallAs400> instances = new HashMap<>();
	/**
	 * Indica se l'oggetto è utilizzato
	 */
	private boolean busy;
	/**
	 * Istanza AS400
	 */
	private AS400 as400;

	/**
	 * Costruttore che oltre ad istanziare l'oggetto as400 aggiunge le librerie
	 * trovate nel file di configurazione.
	 * 
	 * @throws Exception
	 */

	public static CallAs400 get(GenericAS400Param genericAS400Param) throws Exception {

		logger.info("GenericAS400Param -> " + genericAS400Param.toString());
		CallAs400 instance = instances.get(genericAS400Param);
		if (instance == null) {
			instance = new CallAs400(genericAS400Param);
			instances.put(genericAS400Param, instance);
		}

		logger.info("Istanza CallAS400 -> " + instance.toString());
		

		
		
		return instance;
	}

	private CallAs400(GenericAS400Param genericAS400Param) throws Exception {
		this(genericAS400Param.getJobd(), genericAS400Param.getJobdLibrary(), genericAS400Param.getUser(), genericAS400Param.getPassword());
	}

	/*
	 * private CallAs400(ExecutionFlowData executionFlowData) throws Exception {
	 * this(executionFlowData.getFlowJobDesc(), executionFlowData.getFlowLibJobDesc()); }
	 * 
	 * 
	 * private CallAs400(String jobdName) throws Exception { this(jobdName,
	 * Constants.QGPL); }
	 */

	private CallAs400(String jobdName, String library, String username, String password) throws Exception {
		logger.debug("Tentativo di connessione all'as400");
		//as400 = new SecureAS400("as400prod.ocsdom.lan","DIEGOA","DIENZWE17" );
		
		if(StringUtils.isNullOrEmpty(username) && StringUtils.isNullOrEmpty(password)) {
			
			as400 = new AS400();
			
		}
		else if (!StringUtils.isNullOrEmpty(username) && !StringUtils.isNullOrEmpty(password) && Optional.ofNullable(DbConstants.SECURE_CONNECTION).filter(Constants.SI::equals).isPresent()) {
			as400 = new SecureAS400(DbConstants.DB_HOST,username,password );
			//as400 = new SecureAS400("as400prod.ocsdom.lan","DIEGOA","DIENZWE21" );
		}
		else {
			logger.info("Utilizzo dell'utente " + username + " per lancio del flusso");
			//as400 = new AS400(Constants.LOCALHOST, username, password);
			as400 = new AS400(DbConstants.DB_HOST, username, password);
		}
		

		if (StringUtils.isNullOrEmpty(jobdName)) {
			busy = false;
			return;
		}

		// Inserimento librerie
		logger.info("Inserimento librerie da JOBD " + library + "/" + jobdName);

		JobDescription jobd = new JobDescription(as400, StringUtils.setDefault(library, Constants.QGPL), jobdName);

		StringBuilder librariessb = new StringBuilder();

		for (String lib : jobd.getInitialLibraryList()) {
			librariessb.append((librariessb.length() == 0 ? "" : Constants.SPACE) + lib);
		}
		String curlib = JdbcConnection.getCurrentLibrary();
		
		if (!StringUtils.isNullOrEmpty(DbConstants.GF_CURLIB)) {
			curlib = DbConstants.GF_CURLIB;
		}

		CommandCall commandCall = new CommandCall(as400);

		String cmd = "CHGLIBL LIBL(" + librariessb + ") CURLIB(" + curlib + ")";
		logger.info(cmd);
		
		boolean done = commandCall.run(cmd);

		if (!done) {
			throw new OperationException("Librerie non inserite");
		}

		busy = false;
	}

	public void commandCall(String clCommand) throws Exception {
		if (isBusy()) {

			throw new OperationException("Oggetto occupato");
		}
		busy = true;

		CommandCall commandCall = new CommandCall(as400);

		System.out.println(clCommand);
		handleCommandCallError(commandCall, clCommand);

		System.out.println("CALL DONE");
		busy = false;
	}

	public void programCall(String pgmPath, ProgramParameter[] parameters) throws Exception {
		if (isBusy()) {
			throw new OperationException("Oggetto occupato");
		}
		busy = true;

		logger.debug("Composizione input");
		// Componi input

		ProgramCall programCall = new ProgramCall(as400);

		logger.info("Call al programma: " + pgmPath);

		programCall.setProgram(pgmPath, parameters);

		handleProgramCallError(programCall, pgmPath, parameters);

		busy = false;
	}
	
    private void handleProgramCallError(ProgramCall programCall, String pgmPath, ProgramParameter[] parameters) throws AS400SecurityException, ErrorCompletingRequestException, IOException, InterruptedException, PropertyVetoException, OperationException, ObjectDoesNotExistException {
    	boolean done = programCall.run();
    	
    	if (!done) {
        	//TODO Throw Exception
    		busy = false;
    		OperationException oe = new OperationException("Call non eseguita " + pgmPath);        	    	
        	AS400Message[] messages = programCall.getMessageList();
        	
        	for (AS400Message message : messages) {
        		logger.error(message.getText());
        		oe.addKeyValueLog(Constants.ERROR, message.getText());
        	}
            throw oe;
        }
		
	}

	private void handleCommandCallError(CommandCall commandCall, String command)
			throws AS400SecurityException, ErrorCompletingRequestException, IOException, InterruptedException,
			PropertyVetoException, OperationException {
		boolean done = commandCall.run(command);
		if (!done) {
			// TODO Throw Exception
			busy = false;
			OperationException oe = new OperationException("Call non eseguita " + command);
			AS400Message[] messages = commandCall.getMessageList();

			for (AS400Message message : messages) {
				logger.error(message.getText());
				oe.addKeyValueLog(Constants.ERROR, message.getText());
			}
			throw oe;
		}
	}

	public String getObjectPath(String objectName, String objectType)
			throws AS400Exception, AS400SecurityException, ErrorCompletingRequestException, InterruptedException,
			IOException, ObjectDoesNotExistException, RequestNotSupportedException, AS400ObjectNotFoundException {
		ObjectList objectList = null;
		try {
			objectList = new ObjectList(as400, ObjectList.LIBRARY_LIST, objectName, objectType);
			@SuppressWarnings("unchecked")
			Enumeration<ObjectDescription> objectDescriptions = objectList.getObjects();
			if (!objectDescriptions.hasMoreElements()) {
				throw new AS400ObjectNotFoundException("Oggetto " + objectName + " non trovato in lista librerie");
			}
			return objectDescriptions.nextElement().getPath();
		} finally {
			if (objectList != null) {
				objectList.close();
			}
		}
	}

	/**
	 * Ritorna se l'oggetto è occupato
	 * 
	 * @return
	 */
	public boolean isBusy() {
		return busy;
	}

	/**
	 * Disconnette l'istanze dell'oggetto AS400
	 */
	public void close() {
		as400.disconnectAllServices();
	}

	public AS400 getAs400() {
		return as400;
	}

}