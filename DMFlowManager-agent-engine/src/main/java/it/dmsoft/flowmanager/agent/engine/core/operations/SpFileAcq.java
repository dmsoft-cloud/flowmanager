package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.nio.file.attribute.BasicFileAttributes;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SpFileParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FileUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class SpFileAcq extends Operation<SpFileParam> {

	private static final Logger logger = Logger.getLogger(SpFileAcq.class.getName());

	@Override
	public void execute() throws Exception {

		logger.info("start execution of " + SpFileAcq.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.SP_FL_ACQ);

		CallAs400 callAs400 = CallAs400.get(parameters);

		StringBuilder sb = new StringBuilder();

		sb.append("SPMQ" + Constants.PATH_DELIMITER + "SPFILEACQ");

		if (!StringUtils.isNullOrEmpty(parameters.getSpmqm())) {
			sb.append(Constants.SPACE + "SPMQM");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSpmqm());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getSpqName())) {
			sb.append(Constants.SPACE + "SPQNAME");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSpqName());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getStmdb())) {
			sb.append(Constants.SPACE + "STMDB");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(/*Constants.IFS.equals(parameters.getStmdb()) ?*/ Constants.IFS_FORMAT /*: Constants.AS400_FORMAT*/);
			sb.append(Constants.CLOSE_BRACKET);
		}

		/*
		if (!StringUtils.isNullOrEmpty(parameters.getDbfenc())) {
			sb.append(Constants.SPACE + "DBFENC");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getDbfenc());
			sb.append(Constants.CLOSE_BRACKET);
		}
		*/

		if (!StringUtils.isNullOrEmpty(parameters.getSender())) {
			sb.append(Constants.SPACE + "SENDER");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSender());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getCorrid())) {
			sb.append(Constants.SPACE + "CORRID");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getCorrid());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getSpuserid())) {
			sb.append(Constants.SPACE + "SPUSERID");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSpuserid());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getSppwd())) {
			sb.append(Constants.SPACE + "SPPWD");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSppwd());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getMode())) {
			sb.append(Constants.SPACE + "MODE");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getMode());
			sb.append(Constants.CLOSE_BRACKET);
		}

		if (!StringUtils.isNullOrEmpty(parameters.getCcsid())) {
			sb.append(Constants.SPACE + "CCSID");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getCcsid());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		/*
		if (!StringUtils.isNullOrEmpty(parameters.getUsrcls())) {
			sb.append(Constants.SPACE + "USRCLS");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getUsrcls());
			sb.append(Constants.CLOSE_BRACKET);
		}
		*/
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmf())) {
			sb.append(Constants.SPACE + "STMF");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(Constants.SINGLE_QUOTATION_MARK);
			sb.append(parameters.getStmf());
			sb.append(Constants.SINGLE_QUOTATION_MARK);
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getFile())) {
			sb.append(Constants.SPACE + "FILE");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getFile());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getMbr())) {
			sb.append(Constants.SPACE + "MBR");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getMbr());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		sb.append(Constants.SPACE + "SPMULTI");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(Constants.FIRST);
		sb.append(Constants.CLOSE_BRACKET);
		
		sb.append(Constants.SPACE + "INTNBR");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(Constants.SELECT);
		sb.append(Constants.CLOSE_BRACKET);
		
		/*sb.append(Constants.SPACE + "SPFENTRY");
		sb.append(Constants.OPEN_BRACKET);
		sb.append(Constants.UNREAD);
		sb.append(Constants.CLOSE_BRACKET);*/
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmfopt())) {
			
			sb.append(Constants.SPACE + "STMFOPT");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getStmfopt());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		
		if (!StringUtils.isNullOrEmpty(parameters.getSpfentry())) {
			
			sb.append(Constants.SPACE + "SPFENTRY");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getSpfentry());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmenc())) {
			sb.append(Constants.SPACE + "STMENC");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getStmenc());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		boolean doFileModificationCheck = parameters.getFile() == null && !StringUtils.isNullOrEmpty(parameters.getStmf());
		
		Long initialModified = doFileModificationCheck ? getModifiedTime(parameters.getStmf()) : null;		
		
		logger.debug("Command SpFileAcq :  " + sb.toString());
		callAs400.commandCall(sb.toString());
		
		Long finalModified = doFileModificationCheck ? getModifiedTime(parameters.getStmf()) : null;
		launchExceptionIfNoFileFound(initialModified, finalModified);
		
		logger.info("end execution of " + SpFileAcq.class.getName());
		FlowLogUtils.endDetail(OperationType.SP_FL_ACQ);
	}
	
	private long getModifiedTime(String filePath) {
		BasicFileAttributes fileAttr = FileUtils.getFileAttributes(parameters.getStmf());
		return fileAttr == null ? -1 : fileAttr.lastAccessTime().toMillis(); 
	}
	
	private void launchExceptionIfNoFileFound(Long initialModified, Long finalModified) throws OperationException {
		//NON DEVO EFFETTUARE IL CONTROLLO SULLA PRESENZA E MODIFICA DEL FILE IFS
		if (initialModified == null || finalModified == null) {
			return;
		} else if (parameters.isLaunchErrorIfNoFileFound() && (finalModified == -1|| initialModified == finalModified)) {
			throw new OperationException("No file found on remote queue");
		}
	}

}
