package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import javax.activation.DataHandler;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSBase64Exception;

public class OcsBase64 {
	private static Hashtable<String, OcsBase64> fileNames;
	private static ScriptEngineManager manager = new ScriptEngineManager();
	private static ScriptEngine engine = manager.getEngineByName("js");
	private int index;
	private String expression;

	public OcsBase64(String expression) {
		super();
		this.index = 0;
		this.expression = expression;
	}

	/**
	 * Data il path di un file ne restituisce i byte array
	 * 
	 * @param fileName
	 *            path del file
	 * @return byte array del file
	 * @throws IOException
	 */
	public static byte[] getByte(String fileName) throws IOException {
		File file = new File(fileName);
		return FileUtils.readFileToByteArray(file);
	}

	public static DataHandler getDataHandler(String fileName) throws IOException {
		OcsDataSource ds = new OcsDataSource();

		byte[] data = getByte(fileName);
		InputStream is = new ByteArrayInputStream(data);
		ds.setInputStream(is);
		ds.setContentType("application/octet-stream");
		ds.setName(fileName);
		return new DataHandler(ds);
	}

	public static void setFileNames(JSONObject parCli) {
		fileNames = new Hashtable<String, OcsBase64>();
		if (parCli.containsKey("FileNames")) {
			JSONArray arrayNs = (JSONArray) parCli.get("FileNames");
			for (int i = 0; i < arrayNs.size(); i++) {
				JSONObject nsRec = (JSONObject) arrayNs.get(i);
				fileNames.put((String) nsRec.get("Name"), new OcsBase64((String) nsRec.get("Expression")));
			}
		}

	}

	public static String getFileName(String key, byte[] content) throws OCSBase64Exception, IOException, ScriptException {
		if (content == null || content.length == 0) {
			return "";
		}
		OcsBase64 b64 = fileNames.get(key);
		if (null == b64) {
			throw new OCSBase64Exception("Key \"" + key + "\" non trovata.");
		}
		SimpleBindings vars = new SimpleBindings();
		vars.put("index", b64.index++);
		if (b64.expression == null || "".equals(b64.expression)) {
			return "";
		}
		b64.expression += ";fileName = fileName;";
		String fileName = (String) engine.eval(b64.expression, vars);
		File file = new File(fileName);
		FileUtils.writeByteArrayToFile(file, content);
		return fileName;
	}

	public static String getFileName(String key, DataHandler content) throws OCSBase64Exception, IOException, ScriptException {
		InputStream in = null;
		try {
			in = content.getInputStream();
			byte[] byteArray = org.apache.commons.io.IOUtils.toByteArray(in);
			return getFileName(key, byteArray);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static void deleteFileName(String fileName) {
		File file = new File(fileName);
		file.delete();
	}

	public static String encodeBase64(String data) throws UnsupportedEncodingException {
		if (data == null) {
			return null;
		}
		return encodeBase64(data.getBytes("UTF-8"));
	}

	public static String encodeBase64(byte[] data) {
		return Base64.encodeBase64String(data);
	}

	public static byte[] decodeBase64(String data) {
		if (data == null) {
			return null;
		}
		return Base64.decodeBase64(data);
	}

	public static boolean checkFileName(String key) {
		OcsBase64 b64 = fileNames.get(key);
		return !(null == b64);
	}

	/**
	 * Il metodo è da applicarsi nei casi in cui l'applicativo open riceve un
	 * file in formato base64 in un campo di tipo stringa e quindi non si
	 * innesca in automatico il meccanismo di salvataggio.
	 * 
	 * @param filePath
	 * @param base64Content
	 * @throws Exception
	 */
	public static void saveBase64inFile(String filePath, String base64Content) throws Exception {
		if (filePath == null || "".equals(filePath)) {
			return;
		}

		File file = new File(filePath);
		FileUtils.writeByteArrayToFile(file, OcsBase64.decodeBase64(base64Content));
	}

	public static String path2Base64(String filePath) throws IOException {
		if (filePath == null) {
			return "";
		}
		return OcsBase64.encodeBase64(OcsBase64.getByte(filePath));
	}
}
