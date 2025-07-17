package it.dmsoft.flowmanager.agent.engine.core.utils;



import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.dmsoft.flowmanager.agent.engine.core.db.dto.MasterdataOverride;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgffana.OtgffanaCoulmn;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;



public class FlowMasterDataUtils {	
	

	private static final Logger logger = Logger.getLogger(FlowMasterDataUtils.class.getName());
	private static Otgffana masterData;
	
	public static List<MasterdataOverride>  getMasterData(String md, Otgffana fana ) throws Exception {
			logger.info("MasterData rewriting data -> " + md);
			JSONParser parser = new JSONParser();
	        
			// Lista per memorizzare gli oggetti Otgffana passati in input al programma
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
		                Otgffana flowMasterData = new Otgffana();
		                //muovo nei campi del nuovo otgffana i valori dell'anagrafica se non overraidati altrimenti quanto trovo nel jsonObject
		                flowMasterData.setFana_Id(fana.getFana_Id());
		                flowMasterData.setFana_Desc(fana.getFana_Desc());
		                flowMasterData.setFana_Gruppo(fana.getFana_Gruppo()); 
		                flowMasterData.setFana_Cod_Interfaccia(fana.getFana_Cod_Interfaccia()); 
		                flowMasterData.setFana_Stato(fana.getFana_Stato()); 
		                flowMasterData.setFana_Tip_Flusso(fana.getFana_Tip_Flusso());
		                flowMasterData.setFana_Direzione(fana.getFana_Direzione());
		                flowMasterData.setFana_Libreria((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LIBRERIA.toString() , fana.getFana_Libreria()));
		                flowMasterData.setFana_File((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FILE.toString() , fana.getFana_File()));
		                flowMasterData.setFana_Membro((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_MEMBRO.toString() , fana.getFana_Membro()));

		                flowMasterData.setFana_Lib_Source((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LIB_SOURCE.toString() , fana.getFana_Lib_Source()));
		                flowMasterData.setFana_File_Source((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FILE_SOURCE.toString() , fana.getFana_File_Source()));
		                flowMasterData.setFana_Membro_Source((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_MEMBRO_SOURCE.toString() , fana.getFana_Membro_Source()));
		                flowMasterData.setFana_Folder((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FOLDER.toString() , fana.getFana_Folder()));
		                flowMasterData.setFana_File_Name((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FILE_NAME.toString() , fana.getFana_File_Name()));
		                flowMasterData.setFana_Formato(fana.getFana_Formato());		                
		                flowMasterData.setFana_Delim_Record(fana.getFana_Delim_Record());
		                flowMasterData.setFana_Delim_Stringa(fana.getFana_Delim_Stringa());
		                flowMasterData.setFana_Rimoz_Spazi(fana.getFana_Rimoz_Spazi());
		                flowMasterData.setFana_Delim_Campo(fana.getFana_Delim_Campo());
		                flowMasterData.setFana_Riemp_Campo(fana.getFana_Riemp_Campo());
		                flowMasterData.setFana_Codepage(fana.getFana_Codepage());
		                
		                flowMasterData.setFana_Mod_Acquisizione((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_MOD_ACQUISIZIONE.toString() , fana.getFana_Mod_Acquisizione()));
		                flowMasterData.setFana_From_Ccsid(fana.getFana_From_Ccsid());
		                flowMasterData.setFana_Pgm_Controllo(fana.getFana_Pgm_Controllo());
		                flowMasterData.setFana_Tipologia_Conn(fana.getFana_Tipologia_Conn());
		                flowMasterData.setFana_Host(fana.getFana_Host());
		                flowMasterData.setFana_Port(fana.getFana_Port());
		                flowMasterData.setFana_Remote_Folder((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_REMOTE_FOLDER.toString() , fana.getFana_Remote_Folder()));
		                flowMasterData.setFana_Remote_File_Name((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_REMOTE_FILE_NAME.toString() , fana.getFana_Remote_File_Name()));
		                
		                flowMasterData.setFana_Utente(fana.getFana_Utente());
		                flowMasterData.setFana_Password(fana.getFana_Password());
		                flowMasterData.setFana_Utente_Sftp(fana.getFana_Utente_Sftp());
		                flowMasterData.setFana_Intergiry_Check((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_INTEGRITY_CHECK.toString() , fana.getFana_Intergiry_Check()));
		                flowMasterData.setFana_Fl_Name_Semaforo((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_FL_NAME_SEMAFORO.toString() , fana.getFana_Fl_Name_Semaforo()));
			            
		                flowMasterData.setFana_Num_Tenta_Ricez(fana.getFana_Num_Tenta_Ricez());
		                flowMasterData.setFana_Intervallo_Retry(fana.getFana_Intervallo_Retry());
		                flowMasterData.setFana_Retention(fana.getFana_Retention());
		                flowMasterData.setFana_Compression(fana.getFana_Compression());
		                flowMasterData.setFana_De_Compression(fana.getFana_De_Compression());
		                flowMasterData.setFana_Backup(fana.getFana_Backup());
		                
		                flowMasterData.setFana_Invia_Mail((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_INVIA_MAIL.toString() , fana.getFana_Invia_Mail()));
		                flowMasterData.setFana_Lettera_Ok((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LETTERA_OK.toString() , fana.getFana_Lettera_Ok()));
		                flowMasterData.setFana_Lettera_Ko((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LETTERA_KO.toString() , fana.getFana_Lettera_Ko()));
			            
		                flowMasterData.setFana_Prog_Person(fana.getFana_Prog_Person());
		                flowMasterData.setFana_Known_Ht_Fl(fana.getFana_Known_Ht_Fl());
		                flowMasterData.setFana_Key_Fl(fana.getFana_Key_Fl());
		                flowMasterData.setFana_Modalita_Passiva(fana.getFana_Modalita_Passiva());
		                flowMasterData.setFana_Job_Desc(fana.getFana_Job_Desc());
		                flowMasterData.setFana_Lib_Job_Desc(fana.getFana_Lib_Job_Desc());
		                flowMasterData.setFana_Cancella_File(fana.getFana_Cancella_File());
		                flowMasterData.setFana_Risottomettibile(fana.getFana_Risottomettibile());
		                
		                flowMasterData.setFana_Lunghezza_Fl_Flat(new BigDecimal( (String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LUNGHEZZA_FL_FLAT.toString() , fana.getFana_Lunghezza_Fl_Flat().toString())));
			            System.out.println(jsonObject.get(OtgffanaCoulmn.FANA_LUNGHEZZA_FL_FLAT.toString() ));
		                flowMasterData.setFana_Tipo_Trasferimento(fana.getFana_Tipo_Trasferimento());
		                flowMasterData.setFana_Bypass_Qtemp(fana.getFana_Bypass_Qtemp());
		                flowMasterData.setFana_Esistenza_File(fana.getFana_Esistenza_File());
		                
		                flowMasterData.setFana_Lettera_Flusso((String)jsonObject.getOrDefault(OtgffanaCoulmn.FANA_LETTERA_FLUSSO.toString() , fana.getFana_Lettera_Flusso()));
			            
		                flowMasterData.setFana_Agg_Nomi_Col(fana.getFana_Agg_Nomi_Col());
		                flowMasterData.setFana_Crea_Vuoto(fana.getFana_Crea_Vuoto());
		                flowMasterData.setFana_Internaz(fana.getFana_Internaz());
		                flowMasterData.setFana_Sost_Val_Null(fana.getFana_Sost_Val_Null());
		                flowMasterData.setFana_Elim_Nom_Col(fana.getFana_Elim_Nom_Col());
		                flowMasterData.setFana_Flag_Ok_Vuoto(fana.getFana_Flag_Ok_Vuoto());
		                flowMasterData.setFana_Ftp_Secure(fana.getFana_Ftp_Secure());
		                flowMasterData.setFana_Interactive_Type(fana.getFana_Interactive_Type());
		                flowMasterData.setFana_Interactive_Program(fana.getFana_Interactive_Program());
		                flowMasterData.setFana_Interactive_Result(fana.getFana_Interactive_Result());
		                flowMasterData.setFana_Interactive_Command(fana.getFana_Interactive_Command());
		                flowMasterData.setFana_Delay_Semaforo(fana.getFana_Delay_Semaforo());
		                flowMasterData.setFana_Hash_Unico(fana.getFana_Hash_Unico());
		                flowMasterData.setFana_Export_Flag(fana.getFana_Export_Flag());
		                flowMasterData.setFana_Export_Code(fana.getFana_Export_Code());
		                flowMasterData.setFana_Fetch_Size(fana.getFana_Fetch_Size());
		                flowMasterData.setFana_char_empty_space(fana.getFana_char_empty_space());
		                
		                mo.setFanaData(flowMasterData);
		                
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


