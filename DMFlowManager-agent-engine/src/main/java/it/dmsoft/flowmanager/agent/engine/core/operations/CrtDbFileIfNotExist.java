package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkObjParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateDbFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class CrtDbFileIfNotExist extends Operation<CreateDbFileParam> {

	private static final Logger logger = Logger.getLogger(CrtDbFileIfNotExist.class.getName());
	
	@Override
	public void execute() throws Exception {
		
			logger.info("start execution of " + CrtDbFileIfNotExist.class.getName());
			logger.info("parameters: " + parameters.toString());
			LogDb.start(OperationType.CPY_2_DST);
			
			Operation<ChkObjParam> chkObj = new ChkObj();
			chkObj.setParameters(getChkObjParam(parameters));
			
			try {
			chkObj.execute();
			}
			catch(Exception e) {
				Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
				crtDbFile.setParameters(parameters);
				crtDbFile.execute();
			}
			
			logger.info("end execution of " + CrtDbFileIfNotExist.class.getName());
			LogDb.end(OperationType.CPY_2_DST);
		
	}
	
	private ChkObjParam getChkObjParam(CreateDbFileParam createDbFileParam) {
		ChkObjParam chkObjParam = new ChkObjParam();
		chkObjParam.setObj(createDbFileParam.getFile());
		chkObjParam.setLibreria(createDbFileParam.getLibreria());
		chkObjParam.setMbr(createDbFileParam.getMembro());
		return chkObjParam; 
	}

}
