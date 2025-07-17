package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OcsXmlConverter {

	static Hashtable<String, String> nameSpaces;

	public static void setNameSpaces(JSONObject parCli) {
		nameSpaces = new Hashtable<String, String>();
		if (parCli.containsKey("NameSpaces")) {
			JSONArray arrayNs = (JSONArray) parCli.get("NameSpaces");
			for (int i = 0; i < arrayNs.size(); i++) {
				JSONObject nsRec = (JSONObject) arrayNs.get(i);
				nameSpaces.put(((String) nsRec.get("URI")), ((String) nsRec.get("Name")));
			}
		}
	}

	public static void parseXML(String xml, JSONObject parCli, JSONObject nodiIniziali) throws ParserConfigurationException, SAXException, IOException {

		setNameSpaces(parCli);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);
		dbFactory.setIgnoringComments(true);
		dbFactory.setCoalescing(true);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputStream is = new OcsInputStream(xml);

		Document doc = dBuilder.parse(is);
		Node n = doc.getFirstChild();
		NodeList l = n.getChildNodes();
		analizzaFigli(l, nodiIniziali, new StringBuffer());
	}

	public static void parseDOM(Node n, JSONObject parCli, JSONObject nodiIniziali) throws ParserConfigurationException, SAXException, IOException {
		setNameSpaces(parCli);
		analizzaFigli(new NodeListAlone(n), nodiIniziali, new StringBuffer());
	}

	@SuppressWarnings("unchecked")
	private static JSONObject parseNode(Node n) {
		if (n.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}

		NodeList list = n.getChildNodes();
		JSONObject dati = new JSONObject();
		StringBuffer value = new StringBuffer();
		JSONObject childrenValues = new JSONObject();
		JSONObject attributes = new JSONObject();

		analizzaFigli(list, childrenValues, value);

		if (!childrenValues.isEmpty()) {
			dati.put("children", childrenValues);
		}
		String valueString = value.toString();
		if (!valueString.equals("")) {
			dati.put("value", valueString);
		}
		attributes = parseAttributes(n);
		if (!attributes.isEmpty()) {
			dati.put("attributes", attributes);
		}

		return dati;
	}

	@SuppressWarnings("unchecked")
	private static void analizzaFigli(NodeList list, JSONObject childrenValues, StringBuffer nodiTesto) {
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				JSONObject obj = parseNode(node);
				if (obj == null) {
					continue;
				}
				JSONArray a = null;
				if (!childrenValues.containsKey(getNodeName(node))) {
					a = new JSONArray();
					childrenValues.put(getNodeName(node), a);
				} else {
					a = (JSONArray) childrenValues.get(getNodeName(node));
				}
				a.add(obj);
				break;
			case Node.TEXT_NODE:
				nodiTesto.append(node.getNodeValue());
				continue;
			default:
				continue;
			}
		}
	}

	private static String getNodeName(Node node) {
		String name = node.getNodeName();
		String nsUri = node.getNamespaceURI();
		if (nsUri == null) {
			return name;
		}
		String ns = nameSpaces.get(nsUri);
		if (ns == null) {
			return name;
		}
		String pf = node.getPrefix();
		if (pf == null) {
			return ns + "|" + name;
		}
		String[] names = name.split(":");
		return ns + "|" + names[1];
	}

	@SuppressWarnings("unchecked")
	private static JSONObject parseAttributes(Node n) {
		JSONObject attributes = new JSONObject();
		NamedNodeMap attr = n.getAttributes();

		for (int i = 0; i < attr.getLength(); i++) {
			Node a = attr.item(i);
			attributes.put(getNodeName(a), a.getNodeValue());
		}

		return attributes;
	}
}
