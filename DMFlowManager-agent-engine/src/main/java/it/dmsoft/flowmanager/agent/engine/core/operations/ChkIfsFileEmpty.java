package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.ChkIfsFileEmptyParam;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class ChkIfsFileEmpty extends ConstraintDependentOperation< ChkIfsFileEmptyParam, Boolean > {
	
	private static final Logger logger = Logger.getLogger(ChkIfsFileEmpty.class.getName());
	
	@Override
	public void updateParameters() throws Exception {		
		if (Constants.INBOUND.equals(executionFlowData.getFlowDirezione())) {
			setFiles(operationParams.getRemoteTrasmissionFiles());					
		}		
	}
	
	private void setFiles(List<String> fileNames) {
		List<String> checkedFiles = new ArrayList<String>();
		
		for (String fileName : operationParams.getTrasmissionFiles()) {
			String checkedFile = executionFlowData.getFlowFolder();			
			
			checkedFile += Constants.PATH_DELIMITER + StringUtils.removePath(fileName);
			
			logger.info("checked file --> " + checkedFile );
			checkedFiles.add(checkedFile);
		}
		logger.info(parameters.toString());
		parameters.setCheckedFiles(checkedFiles);
		operationParams.setCheckedFiles(checkedFiles);
	}
	
	
	@Override
	public Boolean executeOperation() throws Exception {
		try {			
			Boolean resp = true;
			logger.info("start execution of " + ChkIfsFileEmpty.class.getName());
			logger.info("parameters: " + parameters.toString());
			FlowLogUtils.startDetail(OperationType.CK_IFS_EMP);

			for (int index = 0; index < parameters.getCheckedFiles().size(); index++) {
				String destinationPath = parameters.getCheckedFiles().get(index);
				

				File f = new File(destinationPath);
				if (!f.exists() && !f.isDirectory()) {
					continue;

				}

				if (f.length()==0) {
					logger.info("File" + destinationPath + "is empty");
					resp = true;
				} else { 
				    logger.info("File" + destinationPath + "is not empty");
				    resp = false;
				}
			}

			logger.info("end execution of " + ChkIfsFileEmpty.class.getName());
			FlowLogUtils.endDetail(OperationType.CK_IFS_EMP);
			
			return resp; 
		} catch (OperationException e1) {
			throw e1;
		} catch (Throwable e) {
			throw new Exception("Error on check ifs empty execution: ", e);
		}
		
	}

	@Override
	public void updateOperationParams(Boolean data) throws Exception {
		if (Constants.INBOUND.equals(executionFlowData.getFlowDirezione()) && data.equals(true)) {
			operationParams.setBypassConversion(true);
			logger.info("BYPASS DB2 PHASE DUE TO EMPTY FILE  ");
		}
		
	}
}
