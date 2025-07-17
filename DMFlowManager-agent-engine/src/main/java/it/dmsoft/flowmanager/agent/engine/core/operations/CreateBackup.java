package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgfflogtDAO;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.DependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.BackupParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

//<parametro in input della radice del path>/<nome della transazione>/<anno>/<nome file originazio>_<data di esecuziome AAAAMMGG>_<transactionId>      
public class CreateBackup extends DependentOperation<BackupParam> {

	private static final Logger logger = Logger.getLogger(CreateBackup.class.getName());

	private void createFolder(String path) {
		File f1 = new File(path);

		if (!f1.exists() && !f1.isDirectory()) {
			f1.mkdir();

		}

	}

	@Override
	public void updateParameters() throws Exception {
		if (Constants.OUTBOUND.equals(otgffana.getFana_Direzione())) {
			return;
		}
		
		List<String> backupFiles = new ArrayList<String>();
		List<String> sourceFiles = !Constants.NO.equals(otgffana.getFana_Compression()) ? operationParams.getZipFiles() : operationParams.getTrasmissionFiles();		
		
		for (String fileName : sourceFiles) {
			backupFiles.add(StringUtils.removePath(fileName));
		}
		
		parameters.setBackupFiles(backupFiles);		
	}

	@Override
	public void executeOperation() throws Exception {
		logger.info("start execution of " + CreateBackup.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.BACKUP);

		for (String backFile : parameters.getBackupFiles()) {

			String srcFile = parameters.getBackupFolder() + Constants.PATH_DELIMITER + backFile;

			File sourceFile = new File(srcFile);
			File path = new File(parameters.getPath());

			if (!sourceFile.exists() && !sourceFile.isFile())
				throw new OperationException("Source file not found:" + srcFile);

			if (!path.exists() && !path.isDirectory()) {
				throw new OperationException("Path  not found: " + parameters.getPath());
			}

			StringBuilder sb = new StringBuilder();

			sb.append(parameters.getPath());

			sb.append(Constants.PATH_DELIMITER + parameters.getTransactionName());
			createFolder(sb.toString());

			sb.append(Constants.PATH_DELIMITER + parameters.getAnno());
			createFolder(sb.toString());

			sb.append(Constants.PATH_DELIMITER + parameters.getData() + "_" + parameters.getTransactionId() + "_"
					+ backFile);

			logger.debug(sb.toString());

			logger.info("start execution of creating backup of file: " + sourceFile.toPath());

			String destFile = sb.toString();

			Files.copy(sourceFile.toPath(), new File(destFile).toPath(), StandardCopyOption.REPLACE_EXISTING);
			OtgfflogtDAO.updateBackupPath(new BigDecimal(parameters.getTransactionId()), destFile);
			logger.info("backup created in: " + destFile);
			
		}
		
		logger.info("end execution of " + CreateBackup.class.getName());
		LogDb.end(OperationType.BACKUP);		
	}

}
