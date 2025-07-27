package it.dmsoft.flowmanager.agent.engine.core.flow.builder;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.dmsoft.flowmanager.agent.be.entities.Email;
import it.dmsoft.flowmanager.agent.be.entities.MailParms;
import it.dmsoft.flowmanager.agent.be.entities.Recipient;
import it.dmsoft.flowmanager.agent.be.repositories.EmailRepository;
import it.dmsoft.flowmanager.agent.engine.core.db.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.exception.ParameterException;
import it.dmsoft.flowmanager.agent.engine.core.flow.Flow;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.ChkDbFileEmpty;
import it.dmsoft.flowmanager.agent.engine.core.operations.ChkObj;
import it.dmsoft.flowmanager.agent.engine.core.operations.CopyFile;
import it.dmsoft.flowmanager.agent.engine.core.operations.CreateBackup;
import it.dmsoft.flowmanager.agent.engine.core.operations.CreateZip;
import it.dmsoft.flowmanager.agent.engine.core.operations.CrtDbFile;
import it.dmsoft.flowmanager.agent.engine.core.operations.Db2File;
import it.dmsoft.flowmanager.agent.engine.core.operations.Db2FileFixed;
import it.dmsoft.flowmanager.agent.engine.core.operations.DelayIntegrityCheck;
import it.dmsoft.flowmanager.agent.engine.core.operations.DeleteFile;
import it.dmsoft.flowmanager.agent.engine.core.operations.File2Db;
import it.dmsoft.flowmanager.agent.engine.core.operations.File2Db2Fixed;
import it.dmsoft.flowmanager.agent.engine.core.operations.FtpReceiver;
import it.dmsoft.flowmanager.agent.engine.core.operations.FtpSend;
import it.dmsoft.flowmanager.agent.engine.core.operations.InteractiveCommandCall;
import it.dmsoft.flowmanager.agent.engine.core.operations.InteractiveProgramCall;
import it.dmsoft.flowmanager.agent.engine.core.operations.ReadFileNames;
import it.dmsoft.flowmanager.agent.engine.core.operations.ReadOtgffempa;
import it.dmsoft.flowmanager.agent.engine.core.operations.ReadSpoolFiles;
import it.dmsoft.flowmanager.agent.engine.core.operations.SendMail;
import it.dmsoft.flowmanager.agent.engine.core.operations.SftpReceiver;
import it.dmsoft.flowmanager.agent.engine.core.operations.SftpSend;
import it.dmsoft.flowmanager.agent.engine.core.operations.SpFileAcq;
import it.dmsoft.flowmanager.agent.engine.core.operations.SpFileDsp;
import it.dmsoft.flowmanager.agent.engine.core.operations.SubmitJob;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.BackupParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkDbFileEmptyParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkObjParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CopyFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CreateDbFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DbConversionParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DelayIntegrityCheckParams;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.DeleteFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.GenericAS400Param;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.InteractiveCommandCallParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.InteractiveProgramCallParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ReadFileNamesParams;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ReadSpoolFilesParams;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SendMailParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SpFileParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SubmitJobParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.TrasmissionParams;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ZipParam;
import it.dmsoft.flowmanager.agent.engine.core.operations.remote.File2Table;
import it.dmsoft.flowmanager.agent.engine.core.operations.remote.File2TableFixed;
import it.dmsoft.flowmanager.agent.engine.core.operations.remote.Table2File;
import it.dmsoft.flowmanager.agent.engine.core.operations.remote.Table2FileFixed;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesConstants;
import it.dmsoft.flowmanager.agent.engine.core.properties.PropertiesUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.ConfigUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.TransferType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowIdNumeratorUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipResponse;
import it.dmsoft.flowmanager.common.domain.Domains.RecipientType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public class FlowBuilder {

	protected Flow flow;
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	protected EmailRepository emailRepository;

	public FlowBuilder() {
		this.flow = new Flow();
	}

	public Flow build() {
		return flow;
	}
	
	public FlowBuilder readFileNames(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {

		Operation<ReadFileNamesParams> readNameFiles = new ReadFileNames();

		ReadFileNamesParams readNameFilesParams = new ReadFileNamesParams();
		
		String fileName = operationParams.getFileNames().get(0);
		readNameFilesParams.setFileName(fileName);
		readNameFilesParams.setDefaultShell(PropertiesUtils.get(PropertiesConstants.DEFAULT_SHELL));
		
		
		String folder = executionFlowData.getFlowFolder();
		readNameFilesParams.setFolder(folder);
		
		readNameFilesParams.setListFileFolder(operationParams.getListFileFolder());
		
		readNameFilesParams.setListFile(operationParams.getListFile());
		readNameFilesParams.setOperationParams(operationParams);
		readNameFilesParams.setLaunchErrorIfNoFileFound(Constants.SI.equals(executionFlowData.getFlowEsistenzaFile()));
		
		updateGenericAs400(executionFlowData, readNameFilesParams);

		readNameFiles.setParameters(readNameFilesParams);

		flow.addOperation(readNameFiles);

		return this;
	}
	
	public FlowBuilder readSpoolFiles(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {

		Operation<ReadSpoolFilesParams> readSpoolFiles = new ReadSpoolFiles();

		ReadSpoolFilesParams readSpoolFilesParams = new ReadSpoolFilesParams();
		
		readSpoolFilesParams.setDefaultShell(PropertiesUtils.get(PropertiesConstants.DEFAULT_SHELL));		
		
		String folder = executionFlowData.getFlowFolder();
		readSpoolFilesParams.setFolder(folder);
		
		readSpoolFilesParams.setOperationParams(operationParams);
		
		updateGenericAs400(executionFlowData, readSpoolFilesParams);

		readSpoolFiles.setParameters(readSpoolFilesParams);

		flow.addOperation(readSpoolFiles);

		return this;
	}
	
	public FlowBuilder controlProgram(ExecutionFlowData executionFlowData, OperationParams operationParams) {

		Operation<SubmitJobParam> submitJob = new SubmitJob();

		SubmitJobParam submitJobParams = new SubmitJobParam();

		submitJobParams.setCommand(executionFlowData.getFlowPgmControllo());
		updateGenericAs400(executionFlowData, submitJobParams);

		submitJob.setParameters(submitJobParams);

		flow.addOperation(submitJob);

		return this;
	}
	
	public FlowBuilder interactiveCommand(ExecutionFlowData executionFlowData, OperationParams operationParams) {

		Operation<InteractiveCommandCallParam> intCmd = new InteractiveCommandCall();

		InteractiveCommandCallParam intCmdParams = new InteractiveCommandCallParam();

		intCmdParams.setCommand(executionFlowData.getFlowInteractiveCommand());
		updateGenericAs400(executionFlowData, intCmdParams);

		intCmd.setParameters(intCmdParams);

		flow.addOperation(intCmd);

		return this;
	}
	
	public FlowBuilder interactiveProgram(ExecutionFlowData executionFlowData, OperationParams operationParams) {

		Operation<InteractiveProgramCallParam> intPgm = new InteractiveProgramCall();

		InteractiveProgramCallParam intPgmParams = new InteractiveProgramCallParam();

		intPgmParams.setPgm(executionFlowData.getFlowInteractiveProgram());
		intPgmParams.setResult(executionFlowData.getFlowInteractiveResult());
		updateGenericAs400(executionFlowData, intPgmParams);

		intPgm.setParameters(intPgmParams);

		flow.addOperation(intPgm);

		return this;
	}
	
	public FlowBuilder sendOutcomeMail(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		if (Constants.SI.equals(executionFlowData.getFlowInviaMail())) {		
			String letterCode = Constants.OK.equals(operationParams.getOutcome()) ? executionFlowData.getFlowLetteraOk() : executionFlowData.getFlowLetteraKo();
			flow.addOperations(sendMail(executionFlowData, operationParams, letterCode, null));
		}
		return this;
	}
	
	private List<Operation<?>> sendMail(ExecutionFlowData executionFlowData, OperationParams operationParams, String letterCode, List<String> attachmentFiles) throws SQLException, Exception {

		if (StringUtils.isNullOrEmpty(letterCode)) {
			return new ArrayList<Operation<?>>();
		}
		
		Operation<SendMailParam> sendMail = new SendMail();
		Operation<SendMailParam> readOtgffempa = new ReadOtgffempa();

		ArrayList<String> tos = new ArrayList<String>();
		ArrayList<String> ccs = new ArrayList<String>();
		ArrayList<String> bccs = new ArrayList<String>();
		ArrayList<Allegato> attachments = null;
		
		SendMailParam sendMailParam = new SendMailParam();

		updateGenericAs400(executionFlowData, sendMailParam);

		Email mail = null;

		mail = emailRepository.getReferenceById(letterCode);
		if (mail == null) {
			throw new ParameterException("Mail " + letterCode + " not  found");
		}

		if (operationParams.getOverrideMailDests() != null && !operationParams.getOverrideMailDests().isEmpty()) {
			for (String dest : operationParams.getOverrideMailDests()) {
				tos.add(dest);
			}
		} 
		else {
		   for (Recipient recipient : mail.getRecipients()) {
		
			String dest = recipient.getEmailAddress();
			RecipientType recType = recipient.getType();
			
			if (recType == null || RecipientType.TO.equals(recType)) {
				tos.add(dest);
			} else if (RecipientType.CC.equals(recType)) {
				ccs.add(dest);
			} else if (RecipientType.BCC.equals(recType)) {
				bccs.add(dest);
			}
		  }
		}
		
		if (attachmentFiles != null) {
			attachments = new ArrayList<Allegato>();
			for (String attachmentFile : attachmentFiles) {
				Allegato allegato = new Allegato();
				allegato.setContentId(StringUtils.removePath(attachmentFile));
				if (Constants.SI.equals(executionFlowData.getFlowCompression()) || Constants.SPEDIZIONE.equals(executionFlowData.getFlowCompression())) {
					allegato.setPath(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + attachmentFile + Constants.ZIP_EXTENSION);
				}
				else allegato.setPath(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + attachmentFile);
				
				attachments.add(allegato);
			}
		}
	

		sendMailParam.setHostName("");
		sendMailParam.setPort(BigDecimal.ZERO);
		//sendMailParam.setSmtpUsername(operationParams.getMailAccount());
		sendMailParam.setFrom(operationParams.getMailAccount());
		sendMailParam.setTos(tos);
		sendMailParam.setCcs(ccs);
		sendMailParam.setBccs(bccs);
		sendMailParam.setSubject(mail.getSubject() + Constants.SPACE + executionFlowData.getFlowId() 
						+ Constants.SPACE + operationParams.getTransactionId() + Constants.SPACE + operationParams.getExecutionDate());
		sendMailParam.setAllegati(attachments);
		sendMailParam.setTimeout(Constants.MAIL_TIMEOUT);
		sendMailParam.setBody(mail.getBodyHtml());
		sendMailParam.setTestoHtml(true);
		
		sendMailParam.setPgmLibrary(PropertiesUtils.get(Constants.PGM_LIBRARY_KEY));
		//if(Optional.ofNullable(operationParams.getLegacyModernization()).filter(Constants.SI::equals).isPresent()) {
		if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {	
			MailParms mp = ConfigUtils.getMailConfig();
			sendMailParam.setSmtpUsername(mp.getSmtpUser());
			sendMailParam.setSmtpPassword(mp.getSmtpPassword());
			
			sendMailParam.setPort(Optional.ofNullable(mp.getSmtpPort()).orElse(BigDecimal.valueOf(25)));
			sendMailParam.setHostName(mp.getSmtpHost());
			sendMailParam.setSecure(Optional.ofNullable(mp.getSmtpSecure()).orElse(YesNo.NO).getBool());
		}
		
		sendMail.setParameters(sendMailParam);
		readOtgffempa.setParameters(sendMailParam);
		
		List<Operation<?>> retList = new ArrayList<Operation<?>>();
		//if(!Optional.ofNullable(operationParams.getLegacyModernization()).filter(Constants.SI::equals).isPresent()) retList.add(readOtgffempa);
		if(!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) retList.add(readOtgffempa);
		
		retList.add(sendMail);
		
		return retList;
	}

	public FlowBuilder zipOperation(ExecutionFlowData executionFlowData, OperationParams operationParams, ZipOperation zipOperation) throws ParameterException {

		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowDirezione())) {
			Operation<ZipParam> createZipOperation = zipOperation.get(executionFlowData, operationParams);
			flow.addOperation(createZipOperation);
		} else {
			throw new ParameterException("Direzione non valorizzata"); 
		}

		return this;
	}

	public FlowBuilder deleteSources(List<String> sources, ExecutionFlowData executionFlowData, OperationParams operationParams) {

		DependentOperation<DeleteFileParam> deleteOperation = new DeleteFile();
		DeleteFileParam deleteFileParam = new DeleteFileParam();
		deleteFileParam.setSources(sources);

		deleteOperation.setParameters(deleteFileParam);
		deleteOperation.setOtgffana(executionFlowData);
		deleteOperation.setOperationParams(operationParams);
		flow.addOperation(deleteOperation);

		return this;
	}

	protected static void updateGenericAs400(ExecutionFlowData executionFlowData, GenericAS400Param genericAS400Param) {
		genericAS400Param.setJobd(executionFlowData.getFlowJobDesc());
		genericAS400Param.setJobdLibrary(executionFlowData.getFlowLibJobDesc());
		try {
			genericAS400Param.setUser(PropertiesUtils.get(Constants.USER));
			genericAS400Param.setPassword(PropertiesUtils.get(Constants.PASSWORD));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public FlowBuilder createBackup(ExecutionFlowData executionFlowData, OperationParams operationParams) {

		DependentOperation<BackupParam> createBackup = new CreateBackup();
		BackupParam backupParam = new BackupParam();

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		backupParam.setPath(operationParams.getPathBackup());
		backupParam.setTransactionName(executionFlowData.getFlowId());
		backupParam.setTransactionId(operationParams.getTransactionId().toString());
		backupParam.setAnno(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		backupParam.setData(dateFormat.format(date).toString());
		backupParam
				.setBackupFolder(StringUtils.setDefault(operationParams.getBackupFolder(), executionFlowData.getFlowFolder()));

		backupParam.setBackupFiles(operationParams.getBackupFiles() != null ? operationParams.getBackupFiles()
				: operationParams.getFileNames());
		// backupParam.setBackupFiles(StringUtils.setDefault(operationParams.getBackupFile(),
		// executionFlowData.getFlowFileName()));

		createBackup.setParameters(backupParam);
		createBackup.setOtgffana(executionFlowData);
		createBackup.setOperationParams(operationParams);
		flow.addOperation(createBackup);

		return this;
	}

	protected FlowBuilder conversionToDestFile(ExecutionFlowData executionFlowData, OperationParams operationParams, boolean bypassQtemp) throws Exception {
		String library = operationParams.getLibrary();
		
		if(!bypassQtemp) {
			operationParams.setLibrary(Constants.QTEMP);
		}
		
		if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
			operationParams.setLibrary(executionFlowData.getFlowLibreria());
		}
		
		List<Operation<?>> conversionOperations = ConversionOperation.valueOf(executionFlowData.getFlowFormato())
				.get(executionFlowData , operationParams);
		
		operationParams.setLibrary(library);
		flow.addOperations(conversionOperations);
		return this;
		
	}
	
	public FlowBuilder conversion(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		List<Operation<?>> conversionOperations = ConversionOperation.valueOf(executionFlowData.getFlowFormato())
				.get(executionFlowData, operationParams);
		flow.addOperations(conversionOperations);
		return this;
	}

	public enum ZipOperation {

		ZIP("ZIP") {

			@Override
			public Operation<ZipParam> get(ExecutionFlowData executionFlowData, OperationParams operationParams) {

				ConstraintDependentOperation<ZipParam, ResponseWrapper<ZipResponse>> db2CreateZip = new CreateZip();

				ZipParam dbZipParam = new ZipParam();

				/*
				 * dbZipParam.setDestinations( executionFlowData.getFlowFolder() + "/" +
				 * executionFlowData.getFlowFileName() + Constants.ZIP_EXTENSION);
				 */

				dbZipParam.setOperation(this);

				List<String> sourceFiles = new ArrayList<String>();
				List<String> destinationFiles = new ArrayList<String>();
				
				for (String fileName : operationParams.getFileNames()) {
					sourceFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + fileName);
					destinationFiles.add(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + fileName
							+ Constants.ZIP_EXTENSION);
				}

				dbZipParam.setSourceFiles(sourceFiles);
				dbZipParam.setDestinationFiles(destinationFiles);

				db2CreateZip.setOperationParams(operationParams);
				db2CreateZip.setOtgffana(executionFlowData);
				db2CreateZip.setParameters(dbZipParam);

				if (Constants.SI.equals(executionFlowData.getFlowCompression())
						|| Constants.SPEDIZIONE.equals(executionFlowData.getFlowCompression())) {
					List<String> backupFileNames = new ArrayList<String>();
					for (String fileName : operationParams.getFileNames()) {
						backupFileNames.add(fileName + Constants.ZIP_EXTENSION);

					}
					operationParams.setBackupFiles(backupFileNames);
					operationParams.setBackupFolder(executionFlowData.getFlowFolder());

					if (Constants.SPEDIZIONE.equals(executionFlowData.getFlowCompression())) {
						operationParams.setTrasmissionFolder(executionFlowData.getFlowFolder());
						operationParams.setTrasmissionFiles(addZipPostfix(operationParams.getTrasmissionFiles()));
						//operationParams.setFileNames(addZipPostfix(operationParams.getFileNames()));
					}

				}

				return db2CreateZip;
			}

		},

		UNZIP("UNZIP") {

			@Override
			public Operation<ZipParam> get(ExecutionFlowData executionFlowData, OperationParams operationParams) {
				ConstraintDependentOperation<ZipParam, ResponseWrapper<ZipResponse>> db2CreateZip = new CreateZip();

				ZipParam dbZipParam = new ZipParam();

				dbZipParam.setDestinationFiles(Arrays.asList(executionFlowData.getFlowFolder()));
				dbZipParam.setOperation(this);
				
				List<String> sourceFiles = new ArrayList<String>(operationParams.getTrasmissionFiles().size());
				
				for (String trasmissionFileName : operationParams.getTrasmissionFiles()) {
					sourceFiles.add(executionFlowData.getFlowFolder() + "/" + trasmissionFileName);
				}
				dbZipParam.setSourceFiles(sourceFiles);

				db2CreateZip.setOperationParams(operationParams);
				db2CreateZip.setOtgffana(executionFlowData);
				db2CreateZip.setParameters(dbZipParam);

				if (!Constants.NO.equals(executionFlowData.getFlowCompression())) {

					operationParams.setBackupFiles(Arrays.asList(executionFlowData.getFlowFileName()));
					operationParams.setBackupFolder(executionFlowData.getFlowFolder());

				}

				return db2CreateZip;
			}

		};
		
		private String code;
		
		private ZipOperation(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}

		public abstract Operation<ZipParam> get(ExecutionFlowData executionFlowData, OperationParams operationParams);
		
		protected List<String> addZipPostfix(List<String> fileNames) {
			List<String> retList = new ArrayList<String>(fileNames.size());
			for (String fileName : fileNames) {
				retList.add(fileName + Constants.ZIP_EXTENSION);
			}
			
			return retList;
		}

	}

	public enum ConversionDirection {

		I {
			@Override
			public Operation<DbConversionParam> get(ConversionOperation conversionOperation, DbConversionParam dbConversionParam, ExecutionFlowData executionFlowData, OperationParams operationParams) {
				DependentOperation<DbConversionParam> file2Db ;
				if (Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
					file2Db = ConversionOperation.CSV.equals(conversionOperation) ? new File2Table() : new File2TableFixed();
					
				} else {
					file2Db = ConversionOperation.CSV.equals(conversionOperation) ? new File2Db() : new File2Db2Fixed();
					
				}
				
				//DependentOperation<DbConversionParam> file2Db2 = ConversionOperation.CSV.equals(conversionOperation) ? new File2Db() : new File2Db2Fixed();
				file2Db.setParameters(dbConversionParam);
				file2Db.setOtgffana(executionFlowData);
				file2Db.setOperationParams(operationParams);
				return file2Db;
			}
		},
		O {
			@Override
			public Operation<DbConversionParam> get(ConversionOperation conversionOperation, DbConversionParam dbConversionParam, ExecutionFlowData executionFlowData, OperationParams operationParams) {
				//TODO VALUTARE DI ESTENDERE A FILE2DBFIXED ANCHE PER I FLUSSI FIXED NON SU THEMA
				String transferTypeCode = executionFlowData.getFlowTipoTrasferimento();
				
				boolean doCpytostmf = (TransferType.THEMA_SPAZIO.getCode().equals(transferTypeCode) || Constants.FIXED.contentEquals(executionFlowData.getFlowDelimRecord()))
											&& ConversionOperation.FIXED.name().equals(executionFlowData.getFlowFormato());
				

				ConstraintDependentOperation<DbConversionParam, Boolean> db2File;
				if (Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
					db2File = ConversionOperation.FIXED.name().equals(executionFlowData.getFlowFormato()) ? new Table2FileFixed() : new Table2File();
					
				} else {
					db2File = doCpytostmf ? new Db2FileFixed() : new Db2File();
					
				}	
				db2File.setOperationParams(operationParams);
				db2File.setOtgffana(executionFlowData);
				db2File.setParameters(dbConversionParam);
				return db2File;
			}
		};

		public abstract Operation<DbConversionParam> get(ConversionOperation conversionOperation, DbConversionParam dbConversionParam, ExecutionFlowData executionFlowData, OperationParams operationParams);
	}

	public enum ConversionOperation {

		CSV {
			@Override
			public List<Operation<?>> get(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				
				CreateDbFileParam createDbFileParam = getCreateDbFileParam(executionFlowData, operationParams);
				
				Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
				if (Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
							createDbFileParam.setLibreria(DbConstants.SCHEMA);
				} else {
							createDbFileParam.setLibreria(Constants.QTEMP);
				}	

				crtDbFile.setParameters(createDbFileParam);

				DbConversionParam dbConversionParam = new DbConversionParam();
				updateGenericAs400(executionFlowData, dbConversionParam);
				dbConversionParam.setLibrary(StringUtils.setDefault(operationParams.getLibrary(), Constants.LIBL));
				dbConversionParam.setFile(executionFlowData.getFlowFile());
				dbConversionParam.setMember(executionFlowData.getFlowMembro());
				

				// TODO Vedere se gestire data
				dbConversionParam.setFolderIfs(executionFlowData.getFlowFolder());
				dbConversionParam.setFileNameIfs(operationParams.getFileNames().get(0));
				dbConversionParam.setRecordDelimiter(executionFlowData.getFlowDelimRecord());
				dbConversionParam.setFieldDelimiter(executionFlowData.getFlowDelimCampo());
				dbConversionParam.setStringDelimiter(executionFlowData.getFlowDelimStringa());
				dbConversionParam.setCodepage(executionFlowData.getFlowCodepage());
				dbConversionParam.setMemberOptionAddReplace(operationParams.getMemberOptionAddReplace());
				dbConversionParam.setRemoveBlanks(executionFlowData.getFlowRimozSpazi());
				dbConversionParam.setFromCcsid(executionFlowData.getFlowFromCcsid());
				dbConversionParam.setColumnName(executionFlowData.getFlowAggNomiCol());
				dbConversionParam.setFieldFilling(executionFlowData.getFlowRiempCampo());
				dbConversionParam.setReplaceNullVal(executionFlowData.getFlowSostValNull());
				dbConversionParam.setRemoveColName(executionFlowData.getFlowElimNomCol());
				
				//Controllo CSV format IT
				String csvFormat = PropertiesUtils.get(Constants.CSV_FORMAT);
				
				if(!StringUtils.isNullOrEmpty(executionFlowData.getFlowInternaz())) {
					if(Constants.CSV_FORMAT_IT.equals(executionFlowData.getFlowInternaz())) {
						if(Constants.COMMA.equals(executionFlowData.getFlowDelimCampo())) 
							throw new ParameterException("String delimiter can't be , if csv format of Fana_Internaz is IT");
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_COMMA);
					}
					if(Constants.CSV_FORMAT_NO.equals(executionFlowData.getFlowInternaz())
						&& Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_NONE);		
					}
				}
				
				else if(Constants.CSV_FORMAT_IT.equals(csvFormat)) {
					if(Constants.COMMA.equals(dbConversionParam.getFieldDelimiter())) {
						throw new ParameterException("String delimiter can't be , if csv format of properties's file is IT");
					}
					dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_COMMA);
				}
					
				
				Operation<?> dbConversionOperation = (Operation<?>) ConversionDirection.valueOf(executionFlowData.getFlowDirezione()).get(this, dbConversionParam, executionFlowData, operationParams);
				List<Operation<?>> ret = new ArrayList<Operation<?>>();
				
				if(!Constants.OUTBOUND.equals(executionFlowData.getFlowDirezione()) && !Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent())
				{
					ret.add(crtDbFile);
				}
				
				ret.add(dbConversionOperation);
				return ret;

			}			
			
		},
		FIXED {
			@Override
			public List<Operation<?>> get(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {

				DbConversionParam dbConversionParam = new DbConversionParam();
				updateGenericAs400(executionFlowData, dbConversionParam);

				dbConversionParam.setLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
				//se è remote allora la libreria non è qtemp ma lo schema principale se non è specificata nelle proprerties una libreria temporanea
				if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {				
					dbConversionParam.setLibrary(executionFlowData.getFlowLibreria());
				}
								
				dbConversionParam.setFile(executionFlowData.getFlowFile());
				dbConversionParam.setMember(executionFlowData.getFlowMembro());
				
				
				
				// TODO Vedere se gestire data
				dbConversionParam.setFolderIfs(executionFlowData.getFlowFolder());
				dbConversionParam.setFileNameIfs(operationParams.getFileNames().get(0));
				// dbConversionParam.setFrom_Ccsid("*FILE");
				dbConversionParam.setRecordDelimiter(executionFlowData.getFlowDelimRecord());
				dbConversionParam.setStringDelimiter(executionFlowData.getFlowDelimStringa());
				dbConversionParam.setCodepage(executionFlowData.getFlowCodepage());
				dbConversionParam.setMemberOptionAddReplace(operationParams.getMemberOptionAddReplace());
				dbConversionParam.setFromCcsid(executionFlowData.getFlowFromCcsid());
				dbConversionParam.setColumnName(executionFlowData.getFlowAggNomiCol());
				dbConversionParam.setFieldFilling(executionFlowData.getFlowRiempCampo());
				dbConversionParam.setReplaceNullVal(executionFlowData.getFlowSostValNull());
				dbConversionParam.setRemoveColName(executionFlowData.getFlowElimNomCol());
				
				if(Constants.FIXED.equals(executionFlowData.getFlowDelimRecord())) {
					dbConversionParam.setTabexpn(Constants.NO_AS400);
				}
				
				//aggiunto per gestire la conversione db anche per i fixed
				if(!StringUtils.isNullOrEmpty(executionFlowData.getFlowInternaz())) {
					if(Constants.CSV_FORMAT_IT.equals(executionFlowData.getFlowInternaz())) {
						if(Constants.COMMA.equals(executionFlowData.getFlowDelimCampo())) 
							throw new ParameterException("String delimiter can't be , if csv format of Fana_Internaz is IT");
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_COMMA);
					}
					if(Constants.CSV_FORMAT_NO.equals(executionFlowData.getFlowInternaz())
							&& Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_NONE);		
					}
				}
				
				
				
				
				List<Operation<?>> ret = new ArrayList<>(3);
				
				CreateDbFileParam createDbFileParam = new CreateDbFileParam();
				createDbFileParam.setLibreria(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
				createDbFileParam.setFile(executionFlowData.getFlowFile());
				createDbFileParam.setRecordLength(executionFlowData.getFlowLunghezzaFlFlat());
				
				DependentOperation<CopyFileParam> copyFile = new CopyFile();
				
				CopyFileParam copyFileParam= new CopyFileParam();
				
				updateGenericAs400(executionFlowData, copyFileParam);
				
				copyFileParam.setMbrOpt(executionFlowData.getFlowModAcquisizione());
				
				copyFileParam.setFormatOption(Constants.NOCHECK);
				
				copyFile.setOperationParams(operationParams);
				copyFile.setParameters(copyFileParam);
				
				//se non è remote creo il file temporaneo con crtpf e poi lo popolerò con cpyf
				if(!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {				
					ret.add(getCreateDbFile(executionFlowData, createDbFileParam));
				}
				
				
				copyFileParam.setFromFile(executionFlowData.getFlowFile());
				copyFileParam.setToFile(executionFlowData.getFlowFile());
				
				if (Constants.INBOUND.equals(executionFlowData.getFlowDirezione())) {
					copyFileParam.setFromLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
					copyFileParam.setToLibrary(executionFlowData.getFlowLibreria());
					
					ret.add(ConversionDirection.valueOf(executionFlowData.getFlowDirezione()).get(this, dbConversionParam, executionFlowData, operationParams));
					if (!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent())
							ret.add(copyFile);
				} else if (Constants.OUTBOUND.equals(executionFlowData.getFlowDirezione())) {	
					copyFileParam.setFromLibrary(executionFlowData.getFlowLibreria());					
					copyFileParam.setToLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
					
					if(!operationParams.getSkipCpyFrmFile() && !Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
						ret.add(copyFile);
					}
					
					ret.add(ConversionDirection.valueOf(executionFlowData.getFlowDirezione()).get(this, dbConversionParam, executionFlowData, operationParams));
				}
				
				return ret;

			}
			
		};

		public abstract List<Operation<?>> get(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception;

		
	}

	public enum SftpDirection {

		I {
			@Override
			public Operation<TrasmissionParams> get(TrasmissionParams trasmissionParams, OperationParams operationParams) {
				ConstraintOperation<TrasmissionParams, ResponseWrapper<SftpResponse>> db2SftpReceiver = new SftpReceiver();
				db2SftpReceiver.setParameters(trasmissionParams);
				db2SftpReceiver.setOperationParams(operationParams);
				return db2SftpReceiver;
			}
		},

		O {
			@Override
			public Operation<TrasmissionParams> get(TrasmissionParams trasmissionParams, OperationParams operationParams) {
				Operation<TrasmissionParams> db2SftpSend = new SftpSend();
				db2SftpSend.setParameters(trasmissionParams);
				return db2SftpSend;
			}
		};

		public abstract Operation<TrasmissionParams> get(TrasmissionParams trasmissionParams, OperationParams operationParams);
	}

	public enum FtpDirection {

		I {
			@Override
			public Operation<TrasmissionParams> get(TrasmissionParams trasmissionParams, OperationParams operationParams) {
				ConstraintOperation<TrasmissionParams, ResponseWrapper<FtpResponse>> db2FtpReceiver = new FtpReceiver();
				db2FtpReceiver.setParameters(trasmissionParams);
				db2FtpReceiver.setOperationParams(operationParams);
				return db2FtpReceiver;
			}
		},

		O {
			@Override
			public Operation<TrasmissionParams> get(TrasmissionParams trasmissionParams, OperationParams operationParams) {
				Operation<TrasmissionParams> db2FtpSend = new FtpSend();
				db2FtpSend.setParameters(trasmissionParams);
				return db2FtpSend;
			}
		};

		public abstract Operation<TrasmissionParams> get(TrasmissionParams trasmissionParams, OperationParams operationParams);
	}
	
	public enum SpFileDirection {

		I {
			@Override
			public Operation<SpFileParam> get(SpFileParam fileParam) {
				Operation<SpFileParam> db2SftpReceiver = new SpFileAcq();
				db2SftpReceiver.setParameters(fileParam);
				return db2SftpReceiver;
			}
		},

		O {
			@Override
			public Operation<SpFileParam> get(SpFileParam fileParam) {
				Operation<SpFileParam> db2SftpSend = new SpFileDsp();
				db2SftpSend.setParameters(fileParam);
				return db2SftpSend;
			}
		};

		public abstract Operation<SpFileParam> get(SpFileParam fileParam);
		
	}

	public enum SemaphoreOperation {
		SFTP {

			@Override
			public Operation<TrasmissionParams> get(ExecutionFlowData executionFlowData, OperationParams operationParams) {
				TrasmissionParams trasmissionParams = getSftpParams(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), executionFlowData, operationParams);
				trasmissionParams.setLaunchErrorIfNoFileFound(true);
				return getSftp(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), executionFlowData, operationParams, trasmissionParams);
			}

		},

		FTP {

			@Override
			public Operation<TrasmissionParams> get(ExecutionFlowData executionFlowData, OperationParams operationParams) {
				TrasmissionParams trasmissionParams = getFtpParams(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), executionFlowData, operationParams);
				trasmissionParams.setLaunchErrorIfNoFileFound(true);
				return getFtp(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), executionFlowData, operationParams, trasmissionParams);
			}

		};

		public abstract Operation<TrasmissionParams> get(ExecutionFlowData executionFlowData, OperationParams operationParams);
	}

	public enum TransferTypeOperation {
		THEMA(TransferType.THEMA_SPAZIO) {
			@Override
			public List<Operation<?>> get(FlowBuilder flowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return getThemaSpazio(executionFlowData, operationParams);
			}
		},
		
		CFT(TransferType.CFT) {
			@Override
			public List<Operation<?>> get(FlowBuilder flowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return cftSend(executionFlowData, operationParams);
			}
		},
		
		MAIL(TransferType.MAIL) {
			@Override
			public List<Operation<?>> get(FlowBuilder flowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return flowBuilder.sendMail(executionFlowData, operationParams, executionFlowData.getFlowLetteraFlusso(), operationParams.getFileNames());
			}
		},
		
		TRASMISSION(TransferType.TRANSMISSION) {
			@Override
			public List<Operation<?>> get(FlowBuilder flowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				if (StringUtils.isNullOrEmpty(executionFlowData.getFlowTipologiaConn())) {
					return new ArrayList<Operation<?>>();
				} else {
					return TrasmissionOperation.valueOf(executionFlowData.getFlowTipologiaConn()).get(executionFlowData, operationParams);
				}
			}
		};
		
		private TransferType transferType;
		
		private TransferTypeOperation(TransferType transferType) {
			this.transferType = transferType;
		}

		public TransferType getTransferType() {
			return transferType;
		}
		
		public abstract List<Operation<?>> get(FlowBuilder flowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception;
		
		public static TransferTypeOperation getTransferTypeOperation(TransferType transferType) {
			for (TransferTypeOperation transferTypeOperation : TransferTypeOperation.values()) {
				if (transferTypeOperation.getTransferType().equals(transferType)) {
					return transferTypeOperation;
				}
			}
			
			return null;
		}
	}
	
	
	public enum TrasmissionOperation {
		
		SFTP {

			@Override
			public Operation<?> get(String remotefileName, String trasmissionFile, ExecutionFlowData executionFlowData, OperationParams operationParams) {
				return getSftp(remotefileName, trasmissionFile, executionFlowData, operationParams);
			}

		},

		FTP {

			@Override
			protected Operation<?> get(String remotefileName, String trasmissionFile, ExecutionFlowData executionFlowData, OperationParams operationParams) {
				return getFtp(remotefileName, trasmissionFile, executionFlowData, operationParams);
			}

		};

		public List<Operation<?>> get(ExecutionFlowData executionFlowData, OperationParams operationParams) {
			List<Operation<?>> trasmOperation = new ArrayList<Operation<?>>();
			
			for (int i = 0; i < operationParams.getTrasmissionFiles().size(); i++) {
				int remoteTrasmissionFileIndex = operationParams.getRemoteTrasmissionFiles().size() == 1 ? 0 : i; 
				String remoteTrasmissionFile = operationParams.getRemoteTrasmissionFiles().get(remoteTrasmissionFileIndex);
				trasmOperation.add(this.get(remoteTrasmissionFile, operationParams.getTrasmissionFiles().get(i), executionFlowData, operationParams));
			}
			
			return trasmOperation;
		}
		
		protected abstract Operation<?> get(String remotefileName, String trasmissionFile, ExecutionFlowData executionFlowData, OperationParams operationParams);

	}
	
	private static Operation<CreateDbFileParam> getCreateDbFile(ExecutionFlowData executionFlowData, CreateDbFileParam createDbFileParam) {
		Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
		updateGenericAs400(executionFlowData, createDbFileParam);
		crtDbFile.setParameters(createDbFileParam);		
		return crtDbFile;
	}
	
	public FlowBuilder createDbFile(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		flow.addOperation(getCreateDbFile(executionFlowData, getCreateDbFileParam(executionFlowData, operationParams)));

		return this;
	}
	
	protected static CreateDbFileParam getCreateDbFileParam(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		CreateDbFileParam createDbFileParam = new CreateDbFileParam();

		updateGenericAs400(executionFlowData, createDbFileParam);
		createDbFileParam.setFile(executionFlowData.getFlowFile());
		createDbFileParam.setLibreria(StringUtils.setDefault(operationParams.getLibrary(), executionFlowData.getFlowLibreria()));
		createDbFileParam.setSrcFile(executionFlowData.getFlowFileSource());
		createDbFileParam.setSrcLibreria(executionFlowData.getFlowLibSource());
		createDbFileParam.setSrcMembro(executionFlowData.getFlowMembroSource());
		
		return createDbFileParam;
	}


	public FlowBuilder trasmit(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		if (Constants.INBOUND.equals(executionFlowData.getFlowDirezione())) {
			addSemaphore(executionFlowData, operationParams);
			addDelayIntegrityCheck(executionFlowData, operationParams);
			addTrasmit(executionFlowData, operationParams);
		} else {
			addTrasmit(executionFlowData, operationParams);
			addDelayIntegrityCheck(executionFlowData, operationParams);
			addSemaphore(executionFlowData, operationParams);
		}
		
		return this;
	}
	
	private void addDelayIntegrityCheck(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		if (Constants.SI.equals(executionFlowData.getFlowIntergiryCheck())
				&& !StringUtils.isNullOrEmpty(executionFlowData.getFlowFlNameSemaforo()) &&
					TransferType.TRANSMISSION.getCode().equals(executionFlowData.getFlowTipoTrasferimento()) &&
					BigDecimal.ZERO.compareTo(executionFlowData.getFlowDelaySemaforo()) != 0) {
			
			DelayIntegrityCheckParams delayParams = new DelayIntegrityCheckParams();
			updateGenericAs400(executionFlowData, delayParams);
			delayParams.setDelaySecond(executionFlowData.getFlowDelaySemaforo());
			Operation<DelayIntegrityCheckParams> dleayOperation = new DelayIntegrityCheck();
			dleayOperation.setParameters(delayParams);
			
			flow.addOperation(dleayOperation);
		}
	}
	
	private void addTrasmit(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		String transferTypeCode = executionFlowData.getFlowTipoTrasferimento();
		
		TransferType transferType = TransferType.getTransferType(transferTypeCode);
		TransferTypeOperation transferTypeOperation = TransferTypeOperation.getTransferTypeOperation(transferType);
		
		List<Operation<?>> trasmissionOperations = transferTypeOperation.get(this, executionFlowData, operationParams);
		flow.addOperations(trasmissionOperations);
	}
	
	/*
	private static SpFileParam getThemaSpazioParam(String trasmissionFile, ExecutionFlowData executionFlowData, Otgffanasp otgffanasp) {
		SpFileParam fileParam = new SpFileParam();
		
		updateGenericAs400(executionFlowData, fileParam);
		fileParam.setCcsid(otgffanasp.getOtgfanasp_Ccsid());
		fileParam.setCorrid(otgffanasp.getOtgfanasp_Corrid());
		fileParam.setDbfenc(otgffanasp.getOtgfanasp_Dbfenc());
		fileParam.setId(otgffanasp.getOtgfanasp_Id());
		fileParam.setMode(otgffanasp.getOtgfanasp_Mode());
		fileParam.setSender(otgffanasp.getOtgfanasp_Sender());
		fileParam.setSpmqm(otgffanasp.getOtgfanasp_Spmqm());
		fileParam.setSppwd(otgffanasp.getOtgfanasp_Sppwd());
		fileParam.setSpqName(otgffanasp.getOtgfanasp_SpqName());
		fileParam.setSpuserid(otgffanasp.getOtgfanasp_Spuserid());
		
		if (Constants.SI.equals(otgffanasp.getOtgfanasp_Direct_From_Db())) {
			fileParam.setFile((StringUtils.isNullOrEmpty(executionFlowData.getFlowLibreria()) ? "" : executionFlowData.getFlowLibreria() + Constants.PATH_DELIMITER)
					+ executionFlowData.getFlowFile());
			fileParam.setMbr(StringUtils.isNullOrEmpty(executionFlowData.getFlowMembro()) ? Constants.FIRST : executionFlowData.getFlowMembro());
			fileParam.setDbfenc(otgffanasp.getOtgfanasp_StmEnc());
		} else {
			fileParam.setStmf(executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + trasmissionFile);
			fileParam.setStmdb(otgffanasp.getOtgfanasp_Stmdb());
			fileParam.setStmenc(otgffanasp.getOtgfanasp_StmEnc());
		}
		
		fileParam.setStmeor(otgffanasp.getOtgfanasp_Stmeor());
		fileParam.setStmtype(otgffanasp.getOtgfanasp_Stmtype());		
		fileParam.setStmrecl(executionFlowData.getFlowLunghezzaFlFlat().toString());
		fileParam.setStmfopt(otgffanasp.getOtgfanasp_Rep_Add());
		fileParam.setUsrcls(otgffanasp.getOtgfanasp_Usrcls());		
		fileParam.setSpfentry(otgffanasp.getOtgfanasp_Acq_Fl_Let());
		
		fileParam.setLaunchErrorIfNoFileFound(Constants.GESTOREFLUSSI.equals(executionFlowData.getFlowEsistenzaFile()));
		
		return fileParam;
	}
	*/

	private void addSemaphore(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		if (Constants.SI.equals(executionFlowData.getFlowIntergiryCheck())
				&& !StringUtils.isNullOrEmpty(executionFlowData.getFlowFlNameSemaforo()) &&
					TransferType.TRANSMISSION.getCode().equals(executionFlowData.getFlowTipoTrasferimento())) {
			
			Operation<TrasmissionParams> trasmissionOperation = SemaphoreOperation
					.valueOf(executionFlowData.getFlowTipologiaConn()).get(executionFlowData, operationParams);
			flow.addOperation(trasmissionOperation);
		}
	}

	private static TrasmissionParams getFtpParams(String remoteFileName, String fileName, ExecutionFlowData executionFlowData,
			OperationParams operationParams) {
		TrasmissionParams dbTrasmissionParams = new TrasmissionParams();

		dbTrasmissionParams.setHost(executionFlowData.getFlowHost());
		dbTrasmissionParams.setPort(executionFlowData.getFlowPort());
		dbTrasmissionParams.setLocal_Folder(
				StringUtils.setDefault(operationParams.getTrasmissionFolder(), executionFlowData.getFlowFolder()));
		dbTrasmissionParams.setLocal_File_Name(fileName);
		dbTrasmissionParams.setRemote_Folder(executionFlowData.getFlowRemoteFolder());
		dbTrasmissionParams.setRemote_File_Name(remoteFileName);
		dbTrasmissionParams.setUser(executionFlowData.getFlowUtente());
		dbTrasmissionParams.setPassword(executionFlowData.getFlowPassword());
		dbTrasmissionParams.setKnown_Hosts_File(executionFlowData.getFlowKnownHtFl());
		dbTrasmissionParams.setKeyFile(executionFlowData.getFlowKeyFl());
		dbTrasmissionParams.setPassive_mode(executionFlowData.getFlowModalitaPassiva());
		dbTrasmissionParams.setRetryCount(executionFlowData.getFlowNumTentaRicez());
		dbTrasmissionParams.setRetryIntervall(executionFlowData.getFlowIntervalloRetry());
		dbTrasmissionParams.setFtp_secure(executionFlowData.getFlowFtpSecure());
		dbTrasmissionParams.setLaunchErrorIfNoFileFound(Constants.SI.equals(executionFlowData.getFlowEsistenzaFile()));

		if(Constants.INBOUND.equals(executionFlowData.getFlowDirezione()) && Constants.SI.equals(executionFlowData.getFlowCancellaFile())) {
			dbTrasmissionParams.setRemoveRemoteFile(true);
		}
		
		return dbTrasmissionParams;
	}
	
	private static Operation<TrasmissionParams> getFtp(String remoteFileName, String fileName, ExecutionFlowData executionFlowData,
			OperationParams operationParams) {
		TrasmissionParams trasmissionParams = getFtpParams(remoteFileName, fileName, executionFlowData, operationParams);
		return getFtp(remoteFileName, fileName, executionFlowData, operationParams, trasmissionParams);
	}
	
	private static Operation<TrasmissionParams> getFtp(String remoteFileName, String fileName, ExecutionFlowData executionFlowData,
			OperationParams operationParams, TrasmissionParams trasmissionParams) {
		return FtpDirection.valueOf(executionFlowData.getFlowDirezione()).get(trasmissionParams, operationParams);
	}

	private static TrasmissionParams getSftpParams(String remoteFileName, String fileName, ExecutionFlowData executionFlowData,
			OperationParams operationParams) {
		TrasmissionParams dbTrasmissionParams = new TrasmissionParams();

		dbTrasmissionParams.setHost(executionFlowData.getFlowUtente() + Constants.AT + executionFlowData.getFlowHost());
		dbTrasmissionParams.setPort(executionFlowData.getFlowPort());
		dbTrasmissionParams.setRemote_Folder(executionFlowData.getFlowRemoteFolder());
		dbTrasmissionParams.setRemote_File_Name(remoteFileName);
		dbTrasmissionParams.setUser(executionFlowData.getFlowUtente());

		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowKnownHtFl())) {
			dbTrasmissionParams.setKnown_Hosts_File(executionFlowData.getFlowKnownHtFl());
			dbTrasmissionParams.setKeyFile(executionFlowData.getFlowKeyFl());
			dbTrasmissionParams.setHostKeyAlias(executionFlowData.getFlowUtenteSftp());
		} else if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowUtenteSftp())) {
			dbTrasmissionParams.setKnown_Hosts_File(Constants.HOME + Constants.PATH_DELIMITER
					+ executionFlowData.getFlowUtenteSftp() + Constants.PATH_DELIMITER + Constants.KNOWN_HOSTS_FILE);
			dbTrasmissionParams.setKeyFile(Constants.HOME + Constants.PATH_DELIMITER + executionFlowData.getFlowUtenteSftp()
					+ Constants.PATH_DELIMITER + Constants.KEY_FILE);
		}
		

		dbTrasmissionParams.setUser_Sftp(executionFlowData.getFlowUtenteSftp());

		dbTrasmissionParams.setPassword(executionFlowData.getFlowPassword());

		dbTrasmissionParams.setLocal_Folder(
				StringUtils.setDefault(operationParams.getTrasmissionFolder(), executionFlowData.getFlowFolder()));
		dbTrasmissionParams.setLocal_File_Name(fileName);
		dbTrasmissionParams.setRetryCount(executionFlowData.getFlowNumTentaRicez());
		dbTrasmissionParams.setRetryIntervall(executionFlowData.getFlowIntervalloRetry());
		dbTrasmissionParams.setLaunchErrorIfNoFileFound(Constants.SI.equals(executionFlowData.getFlowEsistenzaFile()));
		
		if(Constants.INBOUND.equals(executionFlowData.getFlowDirezione()) && Constants.SI.equals(executionFlowData.getFlowCancellaFile())) {
			dbTrasmissionParams.setRemoveRemoteFile(true);
		}

		return dbTrasmissionParams;
	}
	
	private static Operation<TrasmissionParams> getSftp(String remoteFileName, String fileName, ExecutionFlowData executionFlowData,
			OperationParams operationParams) {
		TrasmissionParams trasmissionParams = getSftpParams(remoteFileName, fileName, executionFlowData, operationParams);
		return getSftp(remoteFileName, fileName, executionFlowData, operationParams, trasmissionParams);
	}
	
	private static Operation<TrasmissionParams> getSftp(String remoteFileName, String fileName, ExecutionFlowData executionFlowData,
			OperationParams operationParams, TrasmissionParams trasmissionParams) {
		return SftpDirection.valueOf(executionFlowData.getFlowDirezione()).get(trasmissionParams, operationParams);
	}
	
	private static List<Operation<?>> getThemaSpazio(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		List<Operation<?>> spazioOperations = new ArrayList<Operation<?>>(operationParams.getTrasmissionFiles().size());
		/*Otgffanasp otgffanasp = OtgffanaspDAO.read(executionFlowData.getFlowId());
		
		operationParams.setBypassConversion(Constants.SI.equals(otgffanasp.getOtgfanasp_Direct_From_Db()));
		
		for (String trasmissionFile : operationParams.getTrasmissionFiles()) {
			SpFileParam fileParam = getThemaSpazioParam(trasmissionFile, executionFlowData, otgffanasp);
			spazioOperations.add(SpFileDirection.valueOf(executionFlowData.getFlowDirezione()).get(fileParam));
		}
		*/
		return spazioOperations;
	}
	
	public static List<Operation<?>> cftSend(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
	    /*CftFileParam cftFileParam = new CftFileParam();
	    Otgffanacf otgffanacf = OtgffanacfDAO.read(executionFlowData.getFlowId());
	    updateGenericAs400(executionFlowData, cftFileParam);
	    cftFileParam.setId(otgffanacf.getOtgfanacf_Id());
	    cftFileParam.setPartner(otgffanacf.getOtgfanacf_Part());
	    cftFileParam.setIdf(otgffanacf.getOtgfanacf_Idf());
	    cftFileParam.setfName(otgffanacf.getOtgfanacf_Fname());
	    cftFileParam.setParm(otgffanacf.getOtgfanacf_Parm());
	    cftFileParam.setExec(otgffanacf.getOtgfanacf_Exec());
	    cftFileParam.setExecE(otgffanacf.getOtgfanacf_Exece());
	    cftFileParam.setnFname(otgffanacf.getOtgfanacf_Nfname());
	    cftFileParam.setArchiFname(otgffanacf.getOtgfanacf_Archifname());
	    cftFileParam.setLaunchErrorIfNoFileFound(Constants.GESTOREFLUSSI.equals(executionFlowData.getFlowEsistenzaFile()));
	    cftFileParam.setTransactionId(operationParams.getTransactionId());
	    
	    Operation<CftFileParam> cftSendOperation = new CftFileSnd();
	    cftSendOperation.setParameters(cftFileParam);
		*/
	    // Ritorna come lista
	    List<Operation<?>> operations = new ArrayList<>();
	    //operations.add(cftSendOperation);

	    return operations;
	}
	
	public FlowBuilder addDbProgressiveSequenceId(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		if (Constants.DB2.equals(executionFlowData.getFlowTipFlusso())) {
			//operationParams.setTrasmissionFiles(Arrays.asList(replaceLocal));
			//operationParams.setFileNames
			String fileName = operationParams.getFileNames().get(0);
			if(fileName.matches(Constants.REGEXP_WILDCARD + Constants.$PR__REGEXP + Constants.REGEXP_WILDCARD)) {
				BigDecimal startProgr = FlowIdNumeratorUtils.getNextId(executionFlowData.getFlowId(), FormatUtils.date(operationParams.getExecutionDate()), 
						BigDecimal.valueOf(operationParams.getFileNames().size()), entityManager);
				
				fileName = getProgressiveWildcard(fileName, 0, startProgr, getProgressiveWildCardFillSize(fileName));
				operationParams.setFileNames(Arrays.asList(fileName));
				
				if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowTipFlusso())) {
					operationParams.setTrasmissionFiles(Arrays.asList(fileName));
				} 
			}
			
		}
		
		return this;
	}
	
	public FlowBuilder addProgressiveSequenceId(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		BigDecimal fillSize = getProgressiveWildCardFillSize(executionFlowData.getFlowRemoteFileName());
		if(fillSize == null) {
			return this;
		}
		
		List<String> remoteFileNames = new ArrayList<String>(operationParams.getFileNames().size());
		int remoteTrasmissionFilesCount = operationParams.getFileNames().size();
		
		BigDecimal startProg = operationParams.getDayStartProgressiveIndex();
		String remoteFileName = operationParams.getRemoteTrasmissionFiles().get(0);
		
		for (int i = 0 ; i < remoteTrasmissionFilesCount; i++) {
			remoteFileNames.add(getProgressiveWildcard(remoteFileName, i, startProg, fillSize));
		}
		
		operationParams.setRemoteTrasmissionFiles(remoteFileNames);
	
		return this;
	}
	
	public FlowBuilder checkDb2Obj(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		ChkObjParam chkObjParam = new ChkObjParam();
		updateGenericAs400(executionFlowData, chkObjParam);
		chkObjParam.setObj(executionFlowData.getFlowFile());
		chkObjParam.setLibreria(executionFlowData.getFlowLibreria());
		chkObjParam.setMbr(executionFlowData.getFlowMembro());
		
		DependentOperation<ChkObjParam> chOperation = new ChkObj();
		chOperation.setParameters(chkObjParam);
		chOperation.setOperationParams(operationParams);
		
		flow.addOperation(chOperation);
		
		return this; 
		
	}
	
	public FlowBuilder checkDbFileEmpty(ExecutionFlowData executionFlowData, OperationParams operationParams) {
		ChkDbFileEmptyParam chkDbFileEmptyParam = new ChkDbFileEmptyParam();
		
		updateGenericAs400(executionFlowData, chkDbFileEmptyParam);
		chkDbFileEmptyParam.setFile(executionFlowData.getFlowFile());
		chkDbFileEmptyParam.setLibreria(executionFlowData.getFlowLibreria());
		
		Operation<ChkDbFileEmptyParam> chOperation = new ChkDbFileEmpty();
		chOperation.setParameters(chkDbFileEmptyParam);
		
		flow.addOperation(chOperation);
		
		return this;
		
	}
	
	
	
	protected String getProgressiveWildcard(String str, int index, BigDecimal startProg, BigDecimal fillSize) {
		return str.replaceAll(Constants.$PR__REGEXP, StringUtils.leftPad(String.valueOf(index + startProg.intValue()), '0', fillSize.intValue()));
	}
	
	protected BigDecimal getProgressiveWildCardFillSize(String wildacardFileName) {
		Pattern pattern = Pattern.compile(Constants.$PR__REGEXP);
		Matcher matcher = pattern.matcher(wildacardFileName);
		if (matcher.find())
		{
		    return new BigDecimal(matcher.group(1));
		}
		
		return null;
	}

}
