package it.dmsoft.flowmanager.agent.engine.core.utils;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData.OtgffanaCoulmn;
import it.dmsoft.flowmanager.agent.engine.core.model.MasterdataOverride;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;



public class FlowMasterDataUtils {	
	

	private static final Logger logger = Logger.getLogger(FlowMasterDataUtils.class.getName());
	private static ExecutionFlowData masterData;
	
	public static List<MasterdataOverride>  getMasterData(String md, ExecutionFlowData fana ) throws Exception {
			logger.info("MasterData rewriting data -> " + md);
			JSONParser parser = new JSONParser();
	        
			// Lista per memorizzare gli oggetti ExecutionFlowData passati in input al programma
            List<MasterdataOverride> otgffanaList = new ArrayList<>();
			 try {
				 JSONArray jsonArray = (JSONArray) parser.parse(md);
		            if (jsonArray.isEmpty()) {
		                System.out.println("L'array JSON è vuoto, restituisco null.");
		                return null;
		            }
				 
				 for (Object obj : jsonArray) {
		                JSONObject jsonObject = (JSONObject) obj;
		                MasterdataOverride mo = new MasterdataOverride();
		                ExecutionFlowData flowMasterData = new ExecutionFlowData();
		                //muovo nei campi del nuovo executionFlowData i valori dell'anagrafica se non overraidati altrimenti quanto trovo nel jsonObject
		                flowMasterData.setFlowId(fana.getFlowId());
		                flowMasterData.setFlowDesc(fana.getFlowDesc());
		                flowMasterData.setFlowGruppo(fana.getFlowGruppo()); 
		                flowMasterData.setFlowCodInterfaccia(fana.getFlowCodInterfaccia()); 
		                flowMasterData.setFlowStato(fana.getFlowStato()); 
		                flowMasterData.setFlowTipFlusso(fana.getFlowTipFlusso());
		                flowMasterData.setFlowDirezione(fana.getFlowDirezione());
		                flowMasterData.setFlowLibreria((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LIBRERIA.toString() , fana.getFlowLibreria()));
		                flowMasterData.setFlowFile((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FILE.toString() , fana.getFlowFile()));
		                flowMasterData.setFlowMembro((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_MEMBRO.toString() , fana.getFlowMembro()));

		                flowMasterData.setFlowLibSource((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LIB_SOURCE.toString() , fana.getFlowLibSource()));
		                flowMasterData.setFlowFileSource((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FILE_SOURCE.toString() , fana.getFlowFileSource()));
		                flowMasterData.setFlowMembroSource((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_MEMBRO_SOURCE.toString() , fana.getFlowMembroSource()));
		                flowMasterData.setFlowFolder((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FOLDER.toString() , fana.getFlowFolder()));
		                flowMasterData.setFlowFileName((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FILE_NAME.toString() , fana.getFlowFileName()));
		                flowMasterData.setFlowFormato(fana.getFlowFormato());		                
		                flowMasterData.setFlowDelimRecord(fana.getFlowDelimRecord());
		                flowMasterData.setFlowDelimStringa(fana.getFlowDelimStringa());
		                flowMasterData.setFlowRimozSpazi(fana.getFlowRimozSpazi());
		                flowMasterData.setFlowDelimCampo(fana.getFlowDelimCampo());
		                flowMasterData.setFlowRiempCampo(fana.getFlowRiempCampo());
		                flowMasterData.setFlowCodepage(fana.getFlowCodepage());
		                
		                flowMasterData.setFlowModAcquisizione((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_MOD_ACQUISIZIONE.toString() , fana.getFlowModAcquisizione()));
		                flowMasterData.setFlowFromCcsid(fana.getFlowFromCcsid());
		                flowMasterData.setFlowPgmControllo(fana.getFlowPgmControllo());
		                flowMasterData.setFlowTipologiaConn(fana.getFlowTipologiaConn());
		                flowMasterData.setFlowHost(fana.getFlowHost());
		                flowMasterData.setFlowPort(fana.getFlowPort());
		                flowMasterData.setFlowRemoteFolder((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_REMOTE_FOLDER.toString() , fana.getFlowRemoteFolder()));
		                flowMasterData.setFlowRemoteFileName((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_REMOTE_FILE_NAME.toString() , fana.getFlowRemoteFileName()));
		                
		                flowMasterData.setFlowUtente(fana.getFlowUtente());
		                flowMasterData.setFlowPassword(fana.getFlowPassword());
		                flowMasterData.setFlowUtenteSftp(fana.getFlowUtenteSftp());
		                flowMasterData.setFlowIntegrityCheck((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_INTEGRITY_CHECK.toString() , fana.getFlowIntegrityCheck()));
		                flowMasterData.setFlowFlNameSemaforo((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FL_NAME_SEMAFORO.toString() , fana.getFlowFlNameSemaforo()));
			            
		                flowMasterData.setFlowNumTentaRicez(fana.getFlowNumTentaRicez());
		                flowMasterData.setFlowIntervalloRetry(fana.getFlowIntervalloRetry());
		                flowMasterData.setFlowRetention(fana.getFlowRetention());
		                flowMasterData.setFlowCompression(fana.getFlowCompression());
		                flowMasterData.setFlowDeCompression(fana.getFlowDeCompression());
		                flowMasterData.setFlowBackup(fana.getFlowBackup());
		                
		                flowMasterData.setFlowInviaMail((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_INVIA_MAIL.toString() , fana.getFlowInviaMail()));
		                flowMasterData.setFlowLetteraOk((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LETTERA_OK.toString() , fana.getFlowLetteraOk()));
		                flowMasterData.setFlowLetteraKo((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LETTERA_KO.toString() , fana.getFlowLetteraKo()));
			            
		                flowMasterData.setFlowProgPerson(fana.getFlowProgPerson());
		                flowMasterData.setFlowKnownHtFl(fana.getFlowKnownHtFl());
		                flowMasterData.setFlowKeyFl(fana.getFlowKeyFl());
		                flowMasterData.setFlowModalitaPassiva(fana.getFlowModalitaPassiva());
		                flowMasterData.setFlowJobDesc(fana.getFlowJobDesc());
		                flowMasterData.setFlowLibJobDesc(fana.getFlowLibJobDesc());
		                flowMasterData.setFlowCancellaFile(fana.getFlowCancellaFile());
		                flowMasterData.setFlowRisottomettibile(fana.getFlowRisottomettibile());
		                
		                flowMasterData.setFlowLunghezzaFlFlat(new BigDecimal( (String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LUNGHEZZA_FL_FLAT.toString() , fana.getFlowLunghezzaFlFlat().toString())));
			            System.out.println(jsonObject.get(OtgffanaCoulmn.FANA_LUNGHEZZA_FL_FLAT.toString() ));
		                flowMasterData.setFlowTipoTrasferimento(fana.getFlowTipoTrasferimento());
		                flowMasterData.setFlowBypassQtemp(fana.getFlowBypassQtemp());
		                flowMasterData.setFlowEsistenzaFile(fana.getFlowEsistenzaFile());
		                
		                flowMasterData.setFlowLetteraFlusso((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LETTERA_FLUSSO.toString() , fana.getFlowLetteraFlusso()));
			            
		                flowMasterData.setFlowAggNomiCol(fana.getFlowAggNomiCol());
		                flowMasterData.setFlowCreaVuoto(fana.getFlowCreaVuoto());
		                flowMasterData.setFlowInternaz(fana.getFlowInternaz());
		                flowMasterData.setFlowSostValNull(fana.getFlowSostValNull());
		                flowMasterData.setFlowElimNomCol(fana.getFlowElimNomCol());
		                flowMasterData.setFlowFlagOkVuoto(fana.getFlowFlagOkVuoto());
		                flowMasterData.setFlowFtpSecure(fana.getFlowFtpSecure());
		                flowMasterData.setFlowInteractiveType(fana.getFlowInteractiveType());
		                flowMasterData.setFlowInteractiveProgram(fana.getFlowInteractiveProgram());
		                flowMasterData.setFlowInteractiveResult(fana.getFlowInteractiveResult());
		                flowMasterData.setFlowInteractiveCommand(fana.getFlowInteractiveCommand());
		                flowMasterData.setFlowDelaySemaforo(fana.getFlowDelaySemaforo());
		                flowMasterData.setFlowHashUnico(fana.getFlowHashUnico());
		                flowMasterData.setFlowExportFlag(fana.getFlowExportFlag());
		                flowMasterData.setFlowExportCode(fana.getFlowExportCode());
		                flowMasterData.setFlowFetchSize(fana.getFlowFetchSize());
		                flowMasterData.setFlowcharemptyspace(fana.getFlowcharemptyspace());
		                
		                mo.setExecutionFlowData(flowMasterData);
		                
		                JSONArray mailDestArray = (JSONArray) jsonObject.get("mailDest");
		                
		                // Converto JSONArray in List<String>
		                List<String> mailDest = new ArrayList<>();
		                if (mailDestArray != null && !mailDestArray.isEmpty()) {
		                    for (Object email : mailDestArray) {
		                        mailDest.add((String) email);
		                    }
		                } 
		                mo.setMailDest(mailDest);
		                
		                otgffanaList.add(mo);

		            }

			 } catch (Exception e) {
		            System.out.println("Errore durante il parsing del JSON: " + e.getMessage());
		            return null;
		     }
						 
	        if (otgffanaList.isEmpty()) return null;
	        return otgffanaList;               
		}
	
	
			
}


