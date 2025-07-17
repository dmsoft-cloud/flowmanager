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

import it.dmsoft.flowmanager.agent.engine.core.db.dao.DbConstants;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffanacfDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffanaspDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffmailDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffmdestDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgffprogDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.MailParms;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanacf;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffanasp;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffmail;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffmdest;
import it.dmsoft.flowmanager.agent.engine.core.exception.ParameterException;
import it.dmsoft.flowmanager.agent.engine.core.flow.Flow;
import it.dmsoft.flowmanager.agent.engine.core.operations.CftFileSnd;
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
import it.dmsoft.flowmanager.agent.engine.core.operations.params.CftFileParam;
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
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.MailReceiverType;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.TransferType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.ftp.model.FtpResponse;
import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.ResponseWrapper;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;
import it.dmsoft.flowmanager.agent.engine.sftp.model.SftpResponse;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipResponse;

public class FlowBuilder {

	protected Flow flow;

	public FlowBuilder() {
		this.flow = new Flow();
	}

	public Flow build() {
		return flow;
	}
	
	public FlowBuilder readFileNames(Otgffana otgffana, OperationParams operationParams) throws Exception {

		Operation<ReadFileNamesParams> readNameFiles = new ReadFileNames();

		ReadFileNamesParams readNameFilesParams = new ReadFileNamesParams();
		
		String fileName = operationParams.getFileNames().get(0);
		readNameFilesParams.setFileName(fileName);
		readNameFilesParams.setDefaultShell(PropertiesUtils.get(PropertiesConstants.DEFAULT_SHELL));
		
		
		String folder = otgffana.getFana_Folder();
		readNameFilesParams.setFolder(folder);
		
		readNameFilesParams.setListFileFolder(operationParams.getListFileFolder());
		
		readNameFilesParams.setListFile(operationParams.getListFile());
		readNameFilesParams.setOperationParams(operationParams);
		readNameFilesParams.setLaunchErrorIfNoFileFound(Constants.SI.equals(otgffana.getFana_Esistenza_File()));
		
		updateGenericAs400(otgffana, readNameFilesParams);

		readNameFiles.setParameters(readNameFilesParams);

		flow.addOperation(readNameFiles);

		return this;
	}
	
	public FlowBuilder readSpoolFiles(Otgffana otgffana, OperationParams operationParams) throws Exception {

		Operation<ReadSpoolFilesParams> readSpoolFiles = new ReadSpoolFiles();

		ReadSpoolFilesParams readSpoolFilesParams = new ReadSpoolFilesParams();
		
		readSpoolFilesParams.setDefaultShell(PropertiesUtils.get(PropertiesConstants.DEFAULT_SHELL));		
		
		String folder = otgffana.getFana_Folder();
		readSpoolFilesParams.setFolder(folder);
		
		readSpoolFilesParams.setOperationParams(operationParams);
		
		updateGenericAs400(otgffana, readSpoolFilesParams);

		readSpoolFiles.setParameters(readSpoolFilesParams);

		flow.addOperation(readSpoolFiles);

		return this;
	}
	
	public FlowBuilder controlProgram(Otgffana otgffana, OperationParams operationParams) {

		Operation<SubmitJobParam> submitJob = new SubmitJob();

		SubmitJobParam submitJobParams = new SubmitJobParam();

		submitJobParams.setCommand(otgffana.getFana_Pgm_Controllo());
		updateGenericAs400(otgffana, submitJobParams);

		submitJob.setParameters(submitJobParams);

		flow.addOperation(submitJob);

		return this;
	}
	
	public FlowBuilder interactiveCommand(Otgffana otgffana, OperationParams operationParams) {

		Operation<InteractiveCommandCallParam> intCmd = new InteractiveCommandCall();

		InteractiveCommandCallParam intCmdParams = new InteractiveCommandCallParam();

		intCmdParams.setCommand(otgffana.getFana_Interactive_Command());
		updateGenericAs400(otgffana, intCmdParams);

		intCmd.setParameters(intCmdParams);

		flow.addOperation(intCmd);

		return this;
	}
	
	public FlowBuilder interactiveProgram(Otgffana otgffana, OperationParams operationParams) {

		Operation<InteractiveProgramCallParam> intPgm = new InteractiveProgramCall();

		InteractiveProgramCallParam intPgmParams = new InteractiveProgramCallParam();

		intPgmParams.setPgm(otgffana.getFana_Interactive_Program());
		intPgmParams.setResult(otgffana.getFana_Interactive_Result());
		updateGenericAs400(otgffana, intPgmParams);

		intPgm.setParameters(intPgmParams);

		flow.addOperation(intPgm);

		return this;
	}
	
	public FlowBuilder sendOutcomeMail(Otgffana otgffana, OperationParams operationParams) throws Exception {
		if (Constants.SI.equals(otgffana.getFana_Invia_Mail())) {		
			String letterCode = Constants.OK.equals(operationParams.getOutcome()) ? otgffana.getFana_Lettera_Ok() : otgffana.getFana_Lettera_Ko();
			flow.addOperations(sendMail(otgffana, operationParams, letterCode, null));
		}
		return this;
	}
	
	private static List<Operation<?>> sendMail(Otgffana otgffana, OperationParams operationParams, String letterCode, List<String> attachmentFiles) throws SQLException, Exception {

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

		updateGenericAs400(otgffana, sendMailParam);

		Otgffmail otgffmail = null;
		List<Otgffmdest> otgffmdestList = null;

		otgffmail = OtgffmailDAO.read(letterCode);
		if (otgffmail == null) {
			throw new ParameterException("Mail " + letterCode + " not  found");
		}
		otgffmdestList = OtgffmdestDAO.read(letterCode);

		if (operationParams.getOverrideMailDests() != null && !operationParams.getOverrideMailDests().isEmpty()) {
			for (String dest : operationParams.getOverrideMailDests()) {
				tos.add(dest);
			}
		} 
		else {
		   for (Otgffmdest otgffmdest : otgffmdestList) {
		
			String dest = otgffmdest.getMdest_Destinatario();
			String destType = otgffmdest.getMdest_Tipo_Dest();
			
			if (StringUtils.isNullOrEmpty(destType) || MailReceiverType.RECEIVER.getCode().equals(destType)) {
				tos.add(dest);
			} else if (MailReceiverType.CARBON_COPY.getCode().equals(destType)) {
				ccs.add(dest);
			} else if (MailReceiverType.BLIND_CARBON_COPY.getCode().equals(destType)) {
				bccs.add(dest);
			}
		  }
		}
		
		if (attachmentFiles != null) {
			attachments = new ArrayList<Allegato>();
			for (String attachmentFile : attachmentFiles) {
				Allegato allegato = new Allegato();
				allegato.setContentId(StringUtils.removePath(attachmentFile));
				if (Constants.SI.equals(otgffana.getFana_Compression()) || Constants.SPEDIZIONE.equals(otgffana.getFana_Compression())) {
					allegato.setPath(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + attachmentFile + Constants.ZIP_EXTENSION);
				}
				else allegato.setPath(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + attachmentFile);
				
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
		sendMailParam.setSubject(otgffmail.getMail_Oggetto() + Constants.SPACE + otgffana.getFana_Id() 
						+ Constants.SPACE + operationParams.getTransactionId() + Constants.SPACE + operationParams.getExecutionDate());
		sendMailParam.setAllegati(attachments);
		sendMailParam.setTimeout(Constants.MAIL_TIMEOUT);
		sendMailParam.setBody(otgffmail.getMail_Testo());
		sendMailParam.setTestoHtml(false);
		
		sendMailParam.setPgmLibrary(PropertiesUtils.get(Constants.PGM_LIBRARY_KEY));
		//if(Optional.ofNullable(operationParams.getLegacyModernization()).filter(Constants.SI::equals).isPresent()) {
		if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {	
			MailParms mp = ConfigUtils.getMailConfig();
			sendMailParam.setSmtpUsername(mp.getSmtp_user());
			sendMailParam.setSmtpPassword(mp.getSmtp_password());
			
			sendMailParam.setPort(new BigDecimal(Optional.ofNullable(mp.getSmtp_port()).orElse("25")));
			sendMailParam.setHostName(mp.getSmtp_host());
			sendMailParam.setSecure( Optional.ofNullable(mp.getSmtp_secure())
		            .map(String::trim) 
		            .filter(Constants.SI::equals) 
		            .isPresent());
		}
		
		sendMail.setParameters(sendMailParam);
		readOtgffempa.setParameters(sendMailParam);
		
		List<Operation<?>> retList = new ArrayList<Operation<?>>();
		//if(!Optional.ofNullable(operationParams.getLegacyModernization()).filter(Constants.SI::equals).isPresent()) retList.add(readOtgffempa);
		if(!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) retList.add(readOtgffempa);
		
		retList.add(sendMail);
		
		return retList;
	}

	public FlowBuilder zipOperation(Otgffana otgffana, OperationParams operationParams, ZipOperation zipOperation) throws ParameterException {

		if (!StringUtils.isNullOrEmpty(otgffana.getFana_Direzione())) {
			Operation<ZipParam> createZipOperation = zipOperation.get(otgffana, operationParams);
			flow.addOperation(createZipOperation);
		} else {
			throw new ParameterException("Direzione non valorizzata"); 
		}

		return this;
	}

	public FlowBuilder deleteSources(List<String> sources, Otgffana otgffana, OperationParams operationParams) {

		DependentOperation<DeleteFileParam> deleteOperation = new DeleteFile();
		DeleteFileParam deleteFileParam = new DeleteFileParam();
		deleteFileParam.setSources(sources);

		deleteOperation.setParameters(deleteFileParam);
		deleteOperation.setOtgffana(otgffana);
		deleteOperation.setOperationParams(operationParams);
		flow.addOperation(deleteOperation);

		return this;
	}

	protected static void updateGenericAs400(Otgffana otgffana, GenericAS400Param genericAS400Param) {
		genericAS400Param.setJobd(otgffana.getFana_Job_Desc());
		genericAS400Param.setJobdLibrary(otgffana.getFana_Lib_Job_Desc());
		try {
			genericAS400Param.setUser(PropertiesUtils.get(Constants.USER));
			genericAS400Param.setPassword(PropertiesUtils.get(Constants.PASSWORD));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public FlowBuilder createBackup(Otgffana otgffana, OperationParams operationParams) {

		DependentOperation<BackupParam> createBackup = new CreateBackup();
		BackupParam backupParam = new BackupParam();

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		backupParam.setPath(operationParams.getPathBackup());
		backupParam.setTransactionName(otgffana.getFana_Id());
		backupParam.setTransactionId(operationParams.getTransactionId().toString());
		backupParam.setAnno(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		backupParam.setData(dateFormat.format(date).toString());
		backupParam
				.setBackupFolder(StringUtils.setDefault(operationParams.getBackupFolder(), otgffana.getFana_Folder()));

		backupParam.setBackupFiles(operationParams.getBackupFiles() != null ? operationParams.getBackupFiles()
				: operationParams.getFileNames());
		// backupParam.setBackupFiles(StringUtils.setDefault(operationParams.getBackupFile(),
		// otgffana.getFana_File_Name()));

		createBackup.setParameters(backupParam);
		createBackup.setOtgffana(otgffana);
		createBackup.setOperationParams(operationParams);
		flow.addOperation(createBackup);

		return this;
	}

	protected FlowBuilder conversionToDestFile(Otgffana otgffana, OperationParams operationParams, boolean bypassQtemp) throws Exception {
		String library = operationParams.getLibrary();
		
		if(!bypassQtemp) {
			operationParams.setLibrary(Constants.QTEMP);
		}
		
		if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
			operationParams.setLibrary(otgffana.getFana_Libreria());
		}
		
		List<Operation<?>> conversionOperations = ConversionOperation.valueOf(otgffana.getFana_Formato())
				.get(otgffana , operationParams);
		
		operationParams.setLibrary(library);
		flow.addOperations(conversionOperations);
		return this;
		
	}
	
	public FlowBuilder conversion(Otgffana otgffana, OperationParams operationParams) throws Exception {
		List<Operation<?>> conversionOperations = ConversionOperation.valueOf(otgffana.getFana_Formato())
				.get(otgffana, operationParams);
		flow.addOperations(conversionOperations);
		return this;
	}

	public enum ZipOperation {

		ZIP("ZIP") {

			@Override
			public Operation<ZipParam> get(Otgffana otgffana, OperationParams operationParams) {

				ConstraintDependentOperation<ZipParam, ResponseWrapper<ZipResponse>> db2CreateZip = new CreateZip();

				ZipParam dbZipParam = new ZipParam();

				/*
				 * dbZipParam.setDestinations( otgffana.getFana_Folder() + "/" +
				 * otgffana.getFana_File_Name() + Constants.ZIP_EXTENSION);
				 */

				dbZipParam.setOperation(this);

				List<String> sourceFiles = new ArrayList<String>();
				List<String> destinationFiles = new ArrayList<String>();
				
				for (String fileName : operationParams.getFileNames()) {
					sourceFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + fileName);
					destinationFiles.add(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + fileName
							+ Constants.ZIP_EXTENSION);
				}

				dbZipParam.setSourceFiles(sourceFiles);
				dbZipParam.setDestinationFiles(destinationFiles);

				db2CreateZip.setOperationParams(operationParams);
				db2CreateZip.setOtgffana(otgffana);
				db2CreateZip.setParameters(dbZipParam);

				if (Constants.SI.equals(otgffana.getFana_Compression())
						|| Constants.SPEDIZIONE.equals(otgffana.getFana_Compression())) {
					List<String> backupFileNames = new ArrayList<String>();
					for (String fileName : operationParams.getFileNames()) {
						backupFileNames.add(fileName + Constants.ZIP_EXTENSION);

					}
					operationParams.setBackupFiles(backupFileNames);
					operationParams.setBackupFolder(otgffana.getFana_Folder());

					if (Constants.SPEDIZIONE.equals(otgffana.getFana_Compression())) {
						operationParams.setTrasmissionFolder(otgffana.getFana_Folder());
						operationParams.setTrasmissionFiles(addZipPostfix(operationParams.getTrasmissionFiles()));
						//operationParams.setFileNames(addZipPostfix(operationParams.getFileNames()));
					}

				}

				return db2CreateZip;
			}

		},

		UNZIP("UNZIP") {

			@Override
			public Operation<ZipParam> get(Otgffana otgffana, OperationParams operationParams) {
				ConstraintDependentOperation<ZipParam, ResponseWrapper<ZipResponse>> db2CreateZip = new CreateZip();

				ZipParam dbZipParam = new ZipParam();

				dbZipParam.setDestinationFiles(Arrays.asList(otgffana.getFana_Folder()));
				dbZipParam.setOperation(this);
				
				List<String> sourceFiles = new ArrayList<String>(operationParams.getTrasmissionFiles().size());
				
				for (String trasmissionFileName : operationParams.getTrasmissionFiles()) {
					sourceFiles.add(otgffana.getFana_Folder() + "/" + trasmissionFileName);
				}
				dbZipParam.setSourceFiles(sourceFiles);

				db2CreateZip.setOperationParams(operationParams);
				db2CreateZip.setOtgffana(otgffana);
				db2CreateZip.setParameters(dbZipParam);

				if (!Constants.NO.equals(otgffana.getFana_Compression())) {

					operationParams.setBackupFiles(Arrays.asList(otgffana.getFana_File_Name()));
					operationParams.setBackupFolder(otgffana.getFana_Folder());

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

		public abstract Operation<ZipParam> get(Otgffana otgffana, OperationParams operationParams);
		
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
			public Operation<DbConversionParam> get(ConversionOperation conversionOperation, DbConversionParam dbConversionParam, Otgffana otgffana, OperationParams operationParams) {
				DependentOperation<DbConversionParam> file2Db ;
				if (Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
					file2Db = ConversionOperation.CSV.equals(conversionOperation) ? new File2Table() : new File2TableFixed();
					
				} else {
					file2Db = ConversionOperation.CSV.equals(conversionOperation) ? new File2Db() : new File2Db2Fixed();
					
				}
				
				//DependentOperation<DbConversionParam> file2Db2 = ConversionOperation.CSV.equals(conversionOperation) ? new File2Db() : new File2Db2Fixed();
				file2Db.setParameters(dbConversionParam);
				file2Db.setOtgffana(otgffana);
				file2Db.setOperationParams(operationParams);
				return file2Db;
			}
		},
		O {
			@Override
			public Operation<DbConversionParam> get(ConversionOperation conversionOperation, DbConversionParam dbConversionParam, Otgffana otgffana, OperationParams operationParams) {
				//TODO VALUTARE DI ESTENDERE A FILE2DBFIXED ANCHE PER I FLUSSI FIXED NON SU THEMA
				String transferTypeCode = otgffana.getFana_Tipo_Trasferimento();
				
				boolean doCpytostmf = (TransferType.THEMA_SPAZIO.getCode().equals(transferTypeCode) || Constants.FIXED.contentEquals(otgffana.getFana_Delim_Record()))
											&& ConversionOperation.FIXED.name().equals(otgffana.getFana_Formato());
				

				ConstraintDependentOperation<DbConversionParam, Boolean> db2File;
				if (Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
					db2File = ConversionOperation.FIXED.name().equals(otgffana.getFana_Formato()) ? new Table2FileFixed() : new Table2File();
					
				} else {
					db2File = doCpytostmf ? new Db2FileFixed() : new Db2File();
					
				}	
				db2File.setOperationParams(operationParams);
				db2File.setOtgffana(otgffana);
				db2File.setParameters(dbConversionParam);
				return db2File;
			}
		};

		public abstract Operation<DbConversionParam> get(ConversionOperation conversionOperation, DbConversionParam dbConversionParam, Otgffana otgffana, OperationParams operationParams);
	}

	public enum ConversionOperation {

		CSV {
			@Override
			public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception {
				
				CreateDbFileParam createDbFileParam = getCreateDbFileParam(otgffana, operationParams);
				
				Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
				if (Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
							createDbFileParam.setLibreria(DbConstants.SCHEMA);
				} else {
							createDbFileParam.setLibreria(Constants.QTEMP);
				}	

				crtDbFile.setParameters(createDbFileParam);

				DbConversionParam dbConversionParam = new DbConversionParam();
				updateGenericAs400(otgffana, dbConversionParam);
				dbConversionParam.setLibrary(StringUtils.setDefault(operationParams.getLibrary(), Constants.LIBL));
				dbConversionParam.setFile(otgffana.getFana_File());
				dbConversionParam.setMember(otgffana.getFana_Membro());
				

				// TODO Vedere se gestire data
				dbConversionParam.setFolderIfs(otgffana.getFana_Folder());
				dbConversionParam.setFileNameIfs(operationParams.getFileNames().get(0));
				dbConversionParam.setRecordDelimiter(otgffana.getFana_Delim_Record());
				dbConversionParam.setFieldDelimiter(otgffana.getFana_Delim_Campo());
				dbConversionParam.setStringDelimiter(otgffana.getFana_Delim_Stringa());
				dbConversionParam.setCodepage(otgffana.getFana_Codepage());
				dbConversionParam.setMemberOptionAddReplace(operationParams.getMemberOptionAddReplace());
				dbConversionParam.setRemoveBlanks(otgffana.getFana_Rimoz_Spazi());
				dbConversionParam.setFromCcsid(otgffana.getFana_From_Ccsid());
				dbConversionParam.setColumnName(otgffana.getFana_Agg_Nomi_Col());
				dbConversionParam.setFieldFilling(otgffana.getFana_Riemp_Campo());
				dbConversionParam.setReplaceNullVal(otgffana.getFana_Sost_Val_Null());
				dbConversionParam.setRemoveColName(otgffana.getFana_Elim_Nom_Col());
				
				//Controllo CSV format IT
				String csvFormat = PropertiesUtils.get(Constants.CSV_FORMAT);
				
				if(!StringUtils.isNullOrEmpty(otgffana.getFana_Internaz())) {
					if(Constants.CSV_FORMAT_IT.equals(otgffana.getFana_Internaz())) {
						if(Constants.COMMA.equals(otgffana.getFana_Delim_Campo())) 
							throw new ParameterException("String delimiter can't be , if csv format of Fana_Internaz is IT");
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_COMMA);
					}
					if(Constants.CSV_FORMAT_NO.equals(otgffana.getFana_Internaz())
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
					
				
				Operation<?> dbConversionOperation = (Operation<?>) ConversionDirection.valueOf(otgffana.getFana_Direzione()).get(this, dbConversionParam, otgffana, operationParams);
				List<Operation<?>> ret = new ArrayList<Operation<?>>();
				
				if(!Constants.OUTBOUND.equals(otgffana.getFana_Direzione()) && !Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent())
				{
					ret.add(crtDbFile);
				}
				
				ret.add(dbConversionOperation);
				return ret;

			}			
			
		},
		FIXED {
			@Override
			public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception {

				DbConversionParam dbConversionParam = new DbConversionParam();
				updateGenericAs400(otgffana, dbConversionParam);

				dbConversionParam.setLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
				//se è remote allora la libreria non è qtemp ma lo schema principale se non è specificata nelle proprerties una libreria temporanea
				if(Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {				
					dbConversionParam.setLibrary(otgffana.getFana_Libreria());
				}
								
				dbConversionParam.setFile(otgffana.getFana_File());
				dbConversionParam.setMember(otgffana.getFana_Membro());
				
				
				
				// TODO Vedere se gestire data
				dbConversionParam.setFolderIfs(otgffana.getFana_Folder());
				dbConversionParam.setFileNameIfs(operationParams.getFileNames().get(0));
				// dbConversionParam.setFrom_Ccsid("*FILE");
				dbConversionParam.setRecordDelimiter(otgffana.getFana_Delim_Record());
				dbConversionParam.setStringDelimiter(otgffana.getFana_Delim_Stringa());
				dbConversionParam.setCodepage(otgffana.getFana_Codepage());
				dbConversionParam.setMemberOptionAddReplace(operationParams.getMemberOptionAddReplace());
				dbConversionParam.setFromCcsid(otgffana.getFana_From_Ccsid());
				dbConversionParam.setColumnName(otgffana.getFana_Agg_Nomi_Col());
				dbConversionParam.setFieldFilling(otgffana.getFana_Riemp_Campo());
				dbConversionParam.setReplaceNullVal(otgffana.getFana_Sost_Val_Null());
				dbConversionParam.setRemoveColName(otgffana.getFana_Elim_Nom_Col());
				
				if(Constants.FIXED.equals(otgffana.getFana_Delim_Record())) {
					dbConversionParam.setTabexpn(Constants.NO_AS400);
				}
				
				//aggiunto per gestire la conversione db anche per i fixed
				if(!StringUtils.isNullOrEmpty(otgffana.getFana_Internaz())) {
					if(Constants.CSV_FORMAT_IT.equals(otgffana.getFana_Internaz())) {
						if(Constants.COMMA.equals(otgffana.getFana_Delim_Campo())) 
							throw new ParameterException("String delimiter can't be , if csv format of Fana_Internaz is IT");
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_COMMA);
					}
					if(Constants.CSV_FORMAT_NO.equals(otgffana.getFana_Internaz())
							&& Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
						dbConversionParam.setDecimalPointer(Constants.CSV_FORMAT_NONE);		
					}
				}
				
				
				
				
				List<Operation<?>> ret = new ArrayList<>(3);
				
				CreateDbFileParam createDbFileParam = new CreateDbFileParam();
				createDbFileParam.setLibreria(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
				createDbFileParam.setFile(otgffana.getFana_File());
				createDbFileParam.setRecordLength(otgffana.getFana_Lunghezza_Fl_Flat());
				
				DependentOperation<CopyFileParam> copyFile = new CopyFile();
				
				CopyFileParam copyFileParam= new CopyFileParam();
				
				updateGenericAs400(otgffana, copyFileParam);
				
				copyFileParam.setMbrOpt(otgffana.getFana_Mod_Acquisizione());
				
				copyFileParam.setFormatOption(Constants.NOCHECK);
				
				copyFile.setOperationParams(operationParams);
				copyFile.setParameters(copyFileParam);
				
				//se non è remote creo il file temporaneo con crtpf e poi lo popolerò con cpyf
				if(!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {				
					ret.add(getCreateDbFile(otgffana, createDbFileParam));
				}
				
				
				copyFileParam.setFromFile(otgffana.getFana_File());
				copyFileParam.setToFile(otgffana.getFana_File());
				
				if (Constants.INBOUND.equals(otgffana.getFana_Direzione())) {
					copyFileParam.setFromLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
					copyFileParam.setToLibrary(otgffana.getFana_Libreria());
					
					ret.add(ConversionDirection.valueOf(otgffana.getFana_Direzione()).get(this, dbConversionParam, otgffana, operationParams));
					if (!Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent())
							ret.add(copyFile);
				} else if (Constants.OUTBOUND.equals(otgffana.getFana_Direzione())) {	
					copyFileParam.setFromLibrary(otgffana.getFana_Libreria());					
					copyFileParam.setToLibrary(StringUtils.isNullOrEmpty(operationParams.getTmpLibrary()) ? Constants.QTEMP : operationParams.getTmpLibrary());
					
					if(!operationParams.getSkipCpyFrmFile() && !Optional.ofNullable(DbConstants.REMOTE_HOST).filter(Constants.SI::equals).isPresent()) {
						ret.add(copyFile);
					}
					
					ret.add(ConversionDirection.valueOf(otgffana.getFana_Direzione()).get(this, dbConversionParam, otgffana, operationParams));
				}
				
				return ret;

			}
			
		};

		public abstract List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception;

		
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
			public Operation<TrasmissionParams> get(Otgffana otgffana, OperationParams operationParams) {
				TrasmissionParams trasmissionParams = getSftpParams(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), otgffana, operationParams);
				trasmissionParams.setLaunchErrorIfNoFileFound(true);
				return getSftp(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), otgffana, operationParams, trasmissionParams);
			}

		},

		FTP {

			@Override
			public Operation<TrasmissionParams> get(Otgffana otgffana, OperationParams operationParams) {
				TrasmissionParams trasmissionParams = getFtpParams(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), otgffana, operationParams);
				trasmissionParams.setLaunchErrorIfNoFileFound(true);
				return getFtp(operationParams.getSempahoreFile(), operationParams.getSempahoreFile(), otgffana, operationParams, trasmissionParams);
			}

		};

		public abstract Operation<TrasmissionParams> get(Otgffana otgffana, OperationParams operationParams);
	}

	public enum TransferTypeOperation {
		THEMA(TransferType.THEMA_SPAZIO) {
			@Override
			public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception {
				return getThemaSpazio(otgffana, operationParams);
			}
		},
		
		CFT(TransferType.CFT) {
			@Override
			public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception {
				return cftSend(otgffana, operationParams);
			}
		},
		
		MAIL(TransferType.MAIL) {
			@Override
			public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception {
				return sendMail(otgffana, operationParams, otgffana.getFana_Lettera_Flusso(), operationParams.getFileNames());
			}
		},
		
		TRASMISSION(TransferType.TRANSMISSION) {
			@Override
			public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception {
				if (StringUtils.isNullOrEmpty(otgffana.getFana_Tipologia_Conn())) {
					return new ArrayList<Operation<?>>();
				} else {
					return TrasmissionOperation.valueOf(otgffana.getFana_Tipologia_Conn()).get(otgffana, operationParams);
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
		
		public abstract List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) throws Exception;
		
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
			public Operation<?> get(String remotefileName, String trasmissionFile, Otgffana otgffana, OperationParams operationParams) {
				return getSftp(remotefileName, trasmissionFile, otgffana, operationParams);
			}

		},

		FTP {

			@Override
			protected Operation<?> get(String remotefileName, String trasmissionFile, Otgffana otgffana, OperationParams operationParams) {
				return getFtp(remotefileName, trasmissionFile, otgffana, operationParams);
			}

		};

		public List<Operation<?>> get(Otgffana otgffana, OperationParams operationParams) {
			List<Operation<?>> trasmOperation = new ArrayList<Operation<?>>();
			
			for (int i = 0; i < operationParams.getTrasmissionFiles().size(); i++) {
				int remoteTrasmissionFileIndex = operationParams.getRemoteTrasmissionFiles().size() == 1 ? 0 : i; 
				String remoteTrasmissionFile = operationParams.getRemoteTrasmissionFiles().get(remoteTrasmissionFileIndex);
				trasmOperation.add(this.get(remoteTrasmissionFile, operationParams.getTrasmissionFiles().get(i), otgffana, operationParams));
			}
			
			return trasmOperation;
		}
		
		protected abstract Operation<?> get(String remotefileName, String trasmissionFile, Otgffana otgffana, OperationParams operationParams);

	}
	
	private static Operation<CreateDbFileParam> getCreateDbFile(Otgffana otgffana, CreateDbFileParam createDbFileParam) {
		Operation<CreateDbFileParam> crtDbFile = new CrtDbFile();
		updateGenericAs400(otgffana, createDbFileParam);
		crtDbFile.setParameters(createDbFileParam);		
		return crtDbFile;
	}
	
	public FlowBuilder createDbFile(Otgffana otgffana, OperationParams operationParams) {
		flow.addOperation(getCreateDbFile(otgffana, getCreateDbFileParam(otgffana, operationParams)));

		return this;
	}
	
	protected static CreateDbFileParam getCreateDbFileParam(Otgffana otgffana, OperationParams operationParams) {
		CreateDbFileParam createDbFileParam = new CreateDbFileParam();

		updateGenericAs400(otgffana, createDbFileParam);
		createDbFileParam.setFile(otgffana.getFana_File());
		createDbFileParam.setLibreria(StringUtils.setDefault(operationParams.getLibrary(), otgffana.getFana_Libreria()));
		createDbFileParam.setSrcFile(otgffana.getFana_File_Source());
		createDbFileParam.setSrcLibreria(otgffana.getFana_Lib_Source());
		createDbFileParam.setSrcMembro(otgffana.getFana_Membro_Source());
		
		return createDbFileParam;
	}


	public FlowBuilder trasmit(Otgffana otgffana, OperationParams operationParams) throws Exception {
		if (Constants.INBOUND.equals(otgffana.getFana_Direzione())) {
			addSemaphore(otgffana, operationParams);
			addDelayIntegrityCheck(otgffana, operationParams);
			addTrasmit(otgffana, operationParams);
		} else {
			addTrasmit(otgffana, operationParams);
			addDelayIntegrityCheck(otgffana, operationParams);
			addSemaphore(otgffana, operationParams);
		}
		
		return this;
	}
	
	private void addDelayIntegrityCheck(Otgffana otgffana, OperationParams operationParams) {
		if (Constants.SI.equals(otgffana.getFana_Intergiry_Check())
				&& !StringUtils.isNullOrEmpty(otgffana.getFana_Fl_Name_Semaforo()) &&
					TransferType.TRANSMISSION.getCode().equals(otgffana.getFana_Tipo_Trasferimento()) &&
					BigDecimal.ZERO.compareTo(otgffana.getFana_Delay_Semaforo()) != 0) {
			
			DelayIntegrityCheckParams delayParams = new DelayIntegrityCheckParams();
			updateGenericAs400(otgffana, delayParams);
			delayParams.setDelaySecond(otgffana.getFana_Delay_Semaforo());
			Operation<DelayIntegrityCheckParams> dleayOperation = new DelayIntegrityCheck();
			dleayOperation.setParameters(delayParams);
			
			flow.addOperation(dleayOperation);
		}
	}
	
	private void addTrasmit(Otgffana otgffana, OperationParams operationParams) throws Exception {
		String transferTypeCode = otgffana.getFana_Tipo_Trasferimento();
		
		TransferType transferType = TransferType.getTransferType(transferTypeCode);
		TransferTypeOperation transferTypeOperation = TransferTypeOperation.getTransferTypeOperation(transferType);
		
		List<Operation<?>> trasmissionOperations = transferTypeOperation.get(otgffana, operationParams);
		flow.addOperations(trasmissionOperations);
	}
	
	private static SpFileParam getThemaSpazioParam(String trasmissionFile, Otgffana otgffana, Otgffanasp otgffanasp) {
		SpFileParam fileParam = new SpFileParam();
		
		updateGenericAs400(otgffana, fileParam);
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
			fileParam.setFile((StringUtils.isNullOrEmpty(otgffana.getFana_Libreria()) ? "" : otgffana.getFana_Libreria() + Constants.PATH_DELIMITER)
					+ otgffana.getFana_File());
			fileParam.setMbr(StringUtils.isNullOrEmpty(otgffana.getFana_Membro()) ? Constants.FIRST : otgffana.getFana_Membro());
			fileParam.setDbfenc(otgffanasp.getOtgfanasp_StmEnc());
		} else {
			fileParam.setStmf(otgffana.getFana_Folder() + Constants.PATH_DELIMITER + trasmissionFile);
			fileParam.setStmdb(otgffanasp.getOtgfanasp_Stmdb());
			fileParam.setStmenc(otgffanasp.getOtgfanasp_StmEnc());
		}
		
		fileParam.setStmeor(otgffanasp.getOtgfanasp_Stmeor());
		fileParam.setStmtype(otgffanasp.getOtgfanasp_Stmtype());		
		fileParam.setStmrecl(otgffana.getFana_Lunghezza_Fl_Flat().toString());
		fileParam.setStmfopt(otgffanasp.getOtgfanasp_Rep_Add());
		fileParam.setUsrcls(otgffanasp.getOtgfanasp_Usrcls());		
		fileParam.setSpfentry(otgffanasp.getOtgfanasp_Acq_Fl_Let());
		
		fileParam.setLaunchErrorIfNoFileFound(Constants.GESTOREFLUSSI.equals(otgffana.getFana_Esistenza_File()));

		return fileParam;
	}

	private void addSemaphore(Otgffana otgffana, OperationParams operationParams) {
		if (Constants.SI.equals(otgffana.getFana_Intergiry_Check())
				&& !StringUtils.isNullOrEmpty(otgffana.getFana_Fl_Name_Semaforo()) &&
					TransferType.TRANSMISSION.getCode().equals(otgffana.getFana_Tipo_Trasferimento())) {
			
			Operation<TrasmissionParams> trasmissionOperation = SemaphoreOperation
					.valueOf(otgffana.getFana_Tipologia_Conn()).get(otgffana, operationParams);
			flow.addOperation(trasmissionOperation);
		}
	}

	private static TrasmissionParams getFtpParams(String remoteFileName, String fileName, Otgffana otgffana,
			OperationParams operationParams) {
		TrasmissionParams dbTrasmissionParams = new TrasmissionParams();

		dbTrasmissionParams.setHost(otgffana.getFana_Host());
		dbTrasmissionParams.setPort(otgffana.getFana_Port());
		dbTrasmissionParams.setLocal_Folder(
				StringUtils.setDefault(operationParams.getTrasmissionFolder(), otgffana.getFana_Folder()));
		dbTrasmissionParams.setLocal_File_Name(fileName);
		dbTrasmissionParams.setRemote_Folder(otgffana.getFana_Remote_Folder());
		dbTrasmissionParams.setRemote_File_Name(remoteFileName);
		dbTrasmissionParams.setUser(otgffana.getFana_Utente());
		dbTrasmissionParams.setPassword(otgffana.getFana_Password());
		dbTrasmissionParams.setKnown_Hosts_File(otgffana.getFana_Known_Ht_Fl());
		dbTrasmissionParams.setKeyFile(otgffana.getFana_Key_Fl());
		dbTrasmissionParams.setPassive_mode(otgffana.getFana_Modalita_Passiva());
		dbTrasmissionParams.setRetryCount(otgffana.getFana_Num_Tenta_Ricez());
		dbTrasmissionParams.setRetryIntervall(otgffana.getFana_Intervallo_Retry());
		dbTrasmissionParams.setFtp_secure(otgffana.getFana_Ftp_Secure());
		dbTrasmissionParams.setLaunchErrorIfNoFileFound(Constants.SI.equals(otgffana.getFana_Esistenza_File()));

		if(Constants.INBOUND.equals(otgffana.getFana_Direzione()) && Constants.SI.equals(otgffana.getFana_Cancella_File())) {
			dbTrasmissionParams.setRemoveRemoteFile(true);
		}
		
		return dbTrasmissionParams;
	}
	
	private static Operation<TrasmissionParams> getFtp(String remoteFileName, String fileName, Otgffana otgffana,
			OperationParams operationParams) {
		TrasmissionParams trasmissionParams = getFtpParams(remoteFileName, fileName, otgffana, operationParams);
		return getFtp(remoteFileName, fileName, otgffana, operationParams, trasmissionParams);
	}
	
	private static Operation<TrasmissionParams> getFtp(String remoteFileName, String fileName, Otgffana otgffana,
			OperationParams operationParams, TrasmissionParams trasmissionParams) {
		return FtpDirection.valueOf(otgffana.getFana_Direzione()).get(trasmissionParams, operationParams);
	}

	private static TrasmissionParams getSftpParams(String remoteFileName, String fileName, Otgffana otgffana,
			OperationParams operationParams) {
		TrasmissionParams dbTrasmissionParams = new TrasmissionParams();

		dbTrasmissionParams.setHost(otgffana.getFana_Utente() + Constants.AT + otgffana.getFana_Host());
		dbTrasmissionParams.setPort(otgffana.getFana_Port());
		dbTrasmissionParams.setRemote_Folder(otgffana.getFana_Remote_Folder());
		dbTrasmissionParams.setRemote_File_Name(remoteFileName);
		dbTrasmissionParams.setUser(otgffana.getFana_Utente());

		if (!StringUtils.isNullOrEmpty(otgffana.getFana_Known_Ht_Fl())) {
			dbTrasmissionParams.setKnown_Hosts_File(otgffana.getFana_Known_Ht_Fl());
			dbTrasmissionParams.setKeyFile(otgffana.getFana_Key_Fl());
			dbTrasmissionParams.setHostKeyAlias(otgffana.getFana_Utente_Sftp());
		} else if (!StringUtils.isNullOrEmpty(otgffana.getFana_Utente_Sftp())) {
			dbTrasmissionParams.setKnown_Hosts_File(Constants.HOME + Constants.PATH_DELIMITER
					+ otgffana.getFana_Utente_Sftp() + Constants.PATH_DELIMITER + Constants.KNOWN_HOSTS_FILE);
			dbTrasmissionParams.setKeyFile(Constants.HOME + Constants.PATH_DELIMITER + otgffana.getFana_Utente_Sftp()
					+ Constants.PATH_DELIMITER + Constants.KEY_FILE);
		}
		

		dbTrasmissionParams.setUser_Sftp(otgffana.getFana_Utente_Sftp());

		dbTrasmissionParams.setPassword(otgffana.getFana_Password());

		dbTrasmissionParams.setLocal_Folder(
				StringUtils.setDefault(operationParams.getTrasmissionFolder(), otgffana.getFana_Folder()));
		dbTrasmissionParams.setLocal_File_Name(fileName);
		dbTrasmissionParams.setRetryCount(otgffana.getFana_Num_Tenta_Ricez());
		dbTrasmissionParams.setRetryIntervall(otgffana.getFana_Intervallo_Retry());
		dbTrasmissionParams.setLaunchErrorIfNoFileFound(Constants.SI.equals(otgffana.getFana_Esistenza_File()));
		
		if(Constants.INBOUND.equals(otgffana.getFana_Direzione()) && Constants.SI.equals(otgffana.getFana_Cancella_File())) {
			dbTrasmissionParams.setRemoveRemoteFile(true);
		}

		return dbTrasmissionParams;
	}
	
	private static Operation<TrasmissionParams> getSftp(String remoteFileName, String fileName, Otgffana otgffana,
			OperationParams operationParams) {
		TrasmissionParams trasmissionParams = getSftpParams(remoteFileName, fileName, otgffana, operationParams);
		return getSftp(remoteFileName, fileName, otgffana, operationParams, trasmissionParams);
	}
	
	private static Operation<TrasmissionParams> getSftp(String remoteFileName, String fileName, Otgffana otgffana,
			OperationParams operationParams, TrasmissionParams trasmissionParams) {
		return SftpDirection.valueOf(otgffana.getFana_Direzione()).get(trasmissionParams, operationParams);
	}
	
	private static List<Operation<?>> getThemaSpazio(Otgffana otgffana, OperationParams operationParams) throws Exception {
		List<Operation<?>> spazioOperations = new ArrayList<Operation<?>>(operationParams.getTrasmissionFiles().size());
		Otgffanasp otgffanasp = OtgffanaspDAO.read(otgffana.getFana_Id());
		
		operationParams.setBypassConversion(Constants.SI.equals(otgffanasp.getOtgfanasp_Direct_From_Db()));
		
		for (String trasmissionFile : operationParams.getTrasmissionFiles()) {
			SpFileParam fileParam = getThemaSpazioParam(trasmissionFile, otgffana, otgffanasp);
			spazioOperations.add(SpFileDirection.valueOf(otgffana.getFana_Direzione()).get(fileParam));
		}
		
		return spazioOperations;
	}
	
	public static List<Operation<?>> cftSend(Otgffana otgffana, OperationParams operationParams) throws Exception {
	    CftFileParam cftFileParam = new CftFileParam();
	    Otgffanacf otgffanacf = OtgffanacfDAO.read(otgffana.getFana_Id());
	    updateGenericAs400(otgffana, cftFileParam);
	    cftFileParam.setId(otgffanacf.getOtgfanacf_Id());
	    cftFileParam.setPartner(otgffanacf.getOtgfanacf_Part());
	    cftFileParam.setIdf(otgffanacf.getOtgfanacf_Idf());
	    cftFileParam.setfName(otgffanacf.getOtgfanacf_Fname());
	    cftFileParam.setParm(otgffanacf.getOtgfanacf_Parm());
	    cftFileParam.setExec(otgffanacf.getOtgfanacf_Exec());
	    cftFileParam.setExecE(otgffanacf.getOtgfanacf_Exece());
	    cftFileParam.setnFname(otgffanacf.getOtgfanacf_Nfname());
	    cftFileParam.setArchiFname(otgffanacf.getOtgfanacf_Archifname());
	    cftFileParam.setLaunchErrorIfNoFileFound(Constants.GESTOREFLUSSI.equals(otgffana.getFana_Esistenza_File()));
	    cftFileParam.setTransactionId(operationParams.getTransactionId());
	    
	    Operation<CftFileParam> cftSendOperation = new CftFileSnd();
	    cftSendOperation.setParameters(cftFileParam);

	    // Ritorna come lista
	    List<Operation<?>> operations = new ArrayList<>();
	    operations.add(cftSendOperation);

	    return operations;
	}
	
	public FlowBuilder addDbProgressiveSequenceId(Otgffana otgffana, OperationParams operationParams) throws Exception {
		if (Constants.DB2.equals(otgffana.getFana_Tip_Flusso())) {
			//operationParams.setTrasmissionFiles(Arrays.asList(replaceLocal));
			//operationParams.setFileNames
			String fileName = operationParams.getFileNames().get(0);
			if(fileName.matches(Constants.REGEXP_WILDCARD + Constants.$PR__REGEXP + Constants.REGEXP_WILDCARD)) {
				BigDecimal startProgr = OtgffprogDAO.getNextProgressive(otgffana.getFana_Id(), FormatUtils.date(operationParams.getExecutionDate()), 
						BigDecimal.valueOf(operationParams.getFileNames().size()));
				
				fileName = getProgressiveWildcard(fileName, 0, startProgr, getProgressiveWildCardFillSize(fileName));
				operationParams.setFileNames(Arrays.asList(fileName));
				
				if (!StringUtils.isNullOrEmpty(otgffana.getFana_Tip_Flusso())) {
					operationParams.setTrasmissionFiles(Arrays.asList(fileName));
				} 
			}
			
		}
		
		return this;
	}
	
	public FlowBuilder addProgressiveSequenceId(Otgffana otgffana, OperationParams operationParams) {
		BigDecimal fillSize = getProgressiveWildCardFillSize(otgffana.getFana_Remote_File_Name());
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
	
	public FlowBuilder checkDb2Obj(Otgffana otgffana, OperationParams operationParams) {
		ChkObjParam chkObjParam = new ChkObjParam();
		updateGenericAs400(otgffana, chkObjParam);
		chkObjParam.setObj(otgffana.getFana_File());
		chkObjParam.setLibreria(otgffana.getFana_Libreria());
		chkObjParam.setMbr(otgffana.getFana_Membro());
		
		DependentOperation<ChkObjParam> chOperation = new ChkObj();
		chOperation.setParameters(chkObjParam);
		chOperation.setOperationParams(operationParams);
		
		flow.addOperation(chOperation);
		
		return this; 
		
	}
	
	public FlowBuilder checkDbFileEmpty(Otgffana otgffana, OperationParams operationParams) {
		ChkDbFileEmptyParam chkDbFileEmptyParam = new ChkDbFileEmptyParam();
		
		updateGenericAs400(otgffana, chkDbFileEmptyParam);
		chkDbFileEmptyParam.setFile(otgffana.getFana_File());
		chkDbFileEmptyParam.setLibreria(otgffana.getFana_Libreria());
		
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
