package it.dmsoft.flowmanager.agent.engine.core.operations;

import it.dmsoft.flowmanager.agent.engine.core.as400.CallAs400;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.Operation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.SpFileParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.LogDb;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class SpFileDsp extends Operation<SpFileParam> {

	private static final Logger logger = Logger.getLogger(SpFileDsp.class.getName());

	@Override
	public void execute() throws Exception {

		logger.info("start execution of " + SpFileDsp.class.getName());
		logger.info("parameters: " + parameters.toString());
		LogDb.start(OperationType.SP_FL_DSP);

		CallAs400 callAs400 = CallAs400.get(parameters);

		StringBuilder sb = new StringBuilder();

		sb.append("SPMQ" + Constants.PATH_DELIMITER + "SPFILEDSP");

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
		
		if (!StringUtils.isNullOrEmpty(parameters.getDbfenc())) {
			sb.append(Constants.SPACE + "DBFENC");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getDbfenc());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getUsrcls())) {
			sb.append(Constants.SPACE + "USRCLS");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getUsrcls());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
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
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmeor())) {
			sb.append(Constants.SPACE + "STMEOR");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getStmeor());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmtype())) {
			sb.append(Constants.SPACE + "STMTYPE");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getStmtype());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmenc())) {
			sb.append(Constants.SPACE + "STMENC");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getStmenc());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		if (!StringUtils.isNullOrEmpty(parameters.getStmenc())) {
			sb.append(Constants.SPACE + "STMRECL");
			sb.append(Constants.OPEN_BRACKET);
			sb.append(parameters.getStmrecl());
			sb.append(Constants.CLOSE_BRACKET);
		}
		
		logger.debug("Command SpFileDsp :  " + sb.toString());

		callAs400.commandCall(sb.toString());

		logger.info("end execution of " + SpFileDsp.class.getName());
		LogDb.end(OperationType.SP_FL_DSP);
	}

}
