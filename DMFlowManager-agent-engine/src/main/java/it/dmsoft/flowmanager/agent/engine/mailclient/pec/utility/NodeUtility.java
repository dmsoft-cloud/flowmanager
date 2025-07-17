package it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class NodeUtility {
	public static ArrayList<String> nodeRetrieveMultiString(String elementValue, Document datiCertDocument, Logger logger) {
		ArrayList<String> resultArray = new ArrayList<String>();
		NodeList itemList = datiCertDocument.getElementsByTagName(elementValue);
		for (int i = 0; i < itemList.getLength(); i++) {
			String s = nodeRetrieveSingleString(elementValue, datiCertDocument, i, logger);
			if (!s.equals("")) {
				resultArray.add(s);
			}
		}
		return resultArray;
	}

	public static String nodeRetrieveSingleString(String elementValue, Document datiCertDocument, int i, Logger logger) {
		NodeList itemList = datiCertDocument.getElementsByTagName(elementValue);
		if (itemList.getLength() == 0) {
			logger.debug("Nessun elemento trovato");
			return "";
		}
		Node itemNode = itemList.item(i);
		String itemValue = itemNode.getTextContent();
		if (itemValue.equals("")) {
			logger.debug("Nessun valore trovato");
			return "";
		} else {
			logger.debug("Valore trovato: " + itemValue);
		}
		return itemValue;
	}
}
