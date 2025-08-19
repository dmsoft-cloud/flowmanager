package it.dmsoft.flowmanager.agent.engine.core.operations;

import java.io.File;

import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.operations.core.ConstraintDependentOperation;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.HashCheckParam;
import it.dmsoft.flowmanager.agent.engine.core.persistence.HibernateSessionFactory;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FlowLogUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.HashUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.be.entities.FlowHash;
import jakarta.persistence.EntityManager;

public class CheckFileHash extends ConstraintDependentOperation<HashCheckParam, Boolean> {
	
	private static final Logger logger = Logger.getLogger(CheckFileHash.class.getName());
	
    @Override
    public void updateParameters() throws Exception {
    
    }

    @Override
    public Boolean executeOperation() throws Exception {
    	
    	logger.info("start execution of " + CheckFileHash.class.getName());
		logger.info("parameters: " + parameters.toString());
		FlowLogUtils.startDetail(OperationType.CHK_HASH);
        try {

            for (String file : operationParams.getFileNames()) {
            	String fileName = executionFlowData.getFlowFolder() + Constants.PATH_DELIMITER + file;
            	
            	File f = new File(fileName);
            	
				if (!f.exists() && !f.isDirectory()) {
					throw new OperationException("Il file e una cartella o non esiste: " + fileName);
				}

				if (f.length()==0) {
					logger.info("Il file e vuoto, non calcolata impronta hash --> " + fileName);
					return true;
				}
            	
                logger.info("checking hashing file --> " + fileName);
                String fileHash = HashUtils.calculateHash(fileName);
                
                logger.info("hash for " + fileName + "--> " + fileHash );

                EntityManager entityManager = HibernateSessionFactory.get().createEntityManager();
                FlowHash flowHash = entityManager.getReference(FlowHash.class, fileHash);

                if (flowHash != null) {
                    // Esegui il rename del file esistente, sovrascrivendo eventuali file duplicati
                    try {
                        File originalFile = new File(fileName);
                        File renamedFile = new File(fileName + "_duplicated");
                        
                        // Elimina il file duplicato se esiste gia
                        if (renamedFile.exists()) {
                            renamedFile.delete();
                        }
                        
                        boolean success = originalFile.renameTo(renamedFile);
                        
                        if (!success) {
                        	logger.error("Errore durante il rename del file");
                        }
                    } catch (Exception e) {
                        logger.error("Errore durante il rename del file", e);
                        throw e;
                    }

                    throw new OperationException("Duplicato rilevato. ID: " + flowHash.getHashId());
                } else if (parameters.isWrite()){
                	logger.info("writing hash for " + fileName + "--> " + fileHash );
                	flowHash = new FlowHash();
                	flowHash.setHashFootprint(fileHash);
                	flowHash.setHashId(executionFlowData.getFlowId());
                	flowHash.setHashProgrLogt(operationParams.getTransactionId());
                	entityManager.merge(flowHash);
                }
                
                entityManager.close();
            }
            
            logger.info("end execution of " + CheckFileHash.class.getName());
			FlowLogUtils.endDetail(OperationType.CHK_HASH);
			
            return true;
        } catch (OperationException e) {
            throw e;
        } catch (Throwable e) {
            throw new Exception("Error during hash check operation: ", e);
        }
    }

    @Override
    public void updateOperationParams(Boolean result) throws Exception {
        // Aggiorna i parametri dell'operazione se necessario
    }
}