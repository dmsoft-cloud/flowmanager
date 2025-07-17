package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture.ParametriGenerali;

public class GesParametriGenerali {
	private static ObjectMapper mapper = new ObjectMapper();

	private GesParametriGenerali() {

	}

	public static ParametriGenerali marshall(JSONObject jsonObject) throws IOException {
		ParametriGenerali parGen = new ParametriGenerali();
		if (jsonObject == null) {
			return parGen;
		}

		JsonNode jsonNode = mapper.readTree(jsonObject.toJSONString());
		parGen = mapper.treeToValue(jsonNode, ParametriGenerali.class);
		return parGen;
	}

	public static void main(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		String parametri = "{\r\n"
				+ "    \"wsHost20\": {\r\n"
				+ "        \"VERSIONE\": \"\",\r\n"
				+ "        \"CENTRO_COSTO\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"NUMERO_JOB_BCI\": \"516550\",\r\n"
				+ "    \"APP_OPEN\": \"OCSBUILDPROJECT\",\r\n"
				+ "    \"fdi\": {\r\n"
				+ "        \"WS_PWD\": \"\",\r\n"
				+ "        \"WS_USER\": \"\",\r\n"
				+ "        \"AS400_IP\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"CLASS\": \"it.ocsnet.ocs.ocsBuildProject.BuildProjectManager\",\r\n"
				+ "    \"ifcedoges\": {\r\n"
				+ "        \"filiale\": \"\",\r\n"
				+ "        \"ruolo\": \"\",\r\n"
				+ "        \"maxRows\": \"00000\",\r\n"
				+ "        \"objectStore\": \"\",\r\n"
				+ "        \"dogesUrl\": \"\",\r\n"
				+ "        \"objectClass\": \"\",\r\n"
				+ "        \"dogesPath\": \"\",\r\n"
				+ "        \"matricola\": \"\",\r\n"
				+ "        \"dogesCifratura\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"NUMERO_JOB\": \"538970\",\r\n"
				+ "    \"comuni\": {\r\n"
				+ "        \"REMOTO_USER\": \"\",\r\n"
				+ "        \"CERT_CLIENT_ALIAS\": \"\",\r\n"
				+ "        \"INIT_VECTOR\": \"\",\r\n"
				+ "        \"BASIC_PWD\": \"\",\r\n"
				+ "        \"SECRET_KEY\": \"\",\r\n"
				+ "        \"LOG_LEVEL_ALT\": \"\",\r\n"
				+ "        \"CLASSE\": \"\\/QSYS.LIB\\/OCSSY401.LIB\\/OXTSFAO08.FILE\\/AO00000144.MBR\",\r\n"
				+ "        \"LOG_NUM_DOC\": \"01\",\r\n"
				+ "        \"PROTOCOLLO\": \"\",\r\n"
				+ "        \"PROXY_IP\": \"\",\r\n"
				+ "        \"REMOTO_REP\": \"\",\r\n"
				+ "        \"TRUSTSTORE_PATH\": \"\",\r\n"
				+ "        \"PROXY_PORTA\": \"\",\r\n"
				+ "        \"LOG_DOCUM_ALT\": \"\",\r\n"
				+ "        \"TRUSTSTORE_PWD\": \"\",\r\n"
				+ "        \"ABILITA_LOG_SINGOLO\": \"N\",\r\n"
				+ "        \"KEYSTORE_SERVER_ALIAS\": \"\",\r\n"
				+ "        \"TOKEN_JWT\": \"\",\r\n"
				+ "        \"KEYSTORE_SERVER_PATH\": \"\",\r\n"
				+ "        \"LOG_LEVEL\": \"DEBUG\",\r\n"
				+ "        \"PROXY_USER\": \"\",\r\n"
				+ "        \"PROXY_PWD\": \"\",\r\n"
				+ "        \"VISUALIZATION\": \"\",\r\n"
				+ "        \"REMOTO_SOC\": \"\",\r\n"
				+ "        \"AMBIENTE_WS\": \"\",\r\n"
				+ "        \"BASIC_USER_ID\": \"\",\r\n"
				+ "        \"FILE_PATH_OUT\": \"\",\r\n"
				+ "        \"CERT_CLIENT_PWD\": \"\",\r\n"
				+ "        \"VERSIONE_JAVA\": \"\\/QOpenSys\\/QIBM\\/ProdData\\/JavaVM\\/jdk80\\/64bit\",\r\n"
				+ "        \"FILE_URL\": \"\",\r\n"
				+ "        \"LOG_DIM\": \"0005\",\r\n"
				+ "        \"LOG_DOCUM\": \"\\/home\\/tmp\\/AO\\/log\\/B2B\\/OcsBuildProject.log\",\r\n"
				+ "        \"REMOTO_PWD\": \"\",\r\n"
				+ "        \"USA_TOKEN_FISICO\": \"N\",\r\n"
				+ "        \"URL_WS_BATCH_GEDO\": \"\",\r\n"
				+ "        \"URL_TOKEN\": \"\",\r\n"
				+ "        \"TIMEOUT\": \"00000\",\r\n"
				+ "        \"KEYSTORE_SERVER_PWD\": \"\",\r\n"
				+ "        \"CERT_CLIENT_PATH\": \"\",\r\n"
				+ "        \"URL_WS\": \"https:\\/\\/builder.ocsnet.it\\/appopen\"\r\n"
				+ "    },\r\n"
				+ "    \"cerved\": {\r\n"
				+ "        \"USER_CERVED\": \"\",\r\n"
				+ "        \"CLASSE_VER\": \"\",\r\n"
				+ "        \"CHK_CERTIFICATO\": \"\",\r\n"
				+ "        \"CLASSE_INS\": \"\",\r\n"
				+ "        \"CLASSE_ANN\": \"\",\r\n"
				+ "        \"PWD_CERVED\": \"\",\r\n"
				+ "        \"INDEPENDENT_ASP\": \"\",\r\n"
				+ "        \"MACCHINA\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"cse\": {\r\n"
				+ "        \"SERVICE_NAME\": \"\",\r\n"
				+ "        \"SERVICE_VERS\": \"\",\r\n"
				+ "        \"ABI\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"visCamerali\": {\r\n"
				+ "        \"CRIF_REMOTO_PWD\": \"\",\r\n"
				+ "        \"CRIF_REMOTO_USER\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"PACKAGE\": \"it.ocsnet.ocs.ocsBuildProject\",\r\n"
				+ "    \"METHOD\": \"runWs\",\r\n"
				+ "    \"inps\": {\r\n"
				+ "        \"MIN_INTERVALLO\": \"00000\",\r\n"
				+ "        \"NUM_MAX_TRASM\": \"00000\"\r\n"
				+ "    },\r\n"
				+ "    \"kccs\": {\r\n"
				+ "        \"CERT_ALIAS_REQ\": \"\",\r\n"
				+ "        \"CERT_ALIAS_RES\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"crim\": {\r\n"
				+ "        \"LANGUAGE\": \"\",\r\n"
				+ "        \"ARCH_PASSWORD\": \"\",\r\n"
				+ "        \"VERSION_MAJOR\": \"\",\r\n"
				+ "        \"VERSION_MINOR\": \"\",\r\n"
				+ "        \"APPLICAZIONE\": \"\",\r\n"
				+ "        \"SERVICE_TYPE\": \"\",\r\n"
				+ "        \"ENV\": \"\",\r\n"
				+ "        \"ARCH_USER_ID\": \"\",\r\n"
				+ "        \"HEADER_VER\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"assilea\": {\r\n"
				+ "        \"NUM_MAX_TENTATIVI\": \"000\"\r\n"
				+ "    }\r\n"
				+ "}\r\n"
				+ "";
		System.out.println(GesParametriGenerali.marshall((JSONObject) parser.parse(parametri)).toString());
		System.out.println(GesParametriGenerali.marshall((JSONObject) parser.parse(parametri)).toString());
	}
}
