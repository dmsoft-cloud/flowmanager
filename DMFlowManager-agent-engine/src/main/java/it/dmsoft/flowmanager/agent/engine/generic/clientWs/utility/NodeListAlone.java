package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListAlone implements NodeList{

	private Node n;
	
	public NodeListAlone(Node n){
		this.n = n;
	}

	public Node item(int index) {
		return n;
	}

	public int getLength() {
		return 1;
	}

}
