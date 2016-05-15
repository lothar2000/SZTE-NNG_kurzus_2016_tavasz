package navigation;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.w3c.dom.Document;

/**
 * Implement your graph representation here. This class will be instantiated
 * during the unit tests.
 */
public class GraphImpl implements Graph {



	private LinkedList<Element> NodeList;
	private LinkedList<Element> EdgeList;

	private HashMap<Integer,Element> NodeMap;



	public GraphImpl() {
		NodeList=new LinkedList<Element>();
		EdgeList=new LinkedList<Element>();
		NodeMap=new HashMap<Integer,Element>();
	}

	@Override
	public void initializeFromFile(File inputXmlFile) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputXmlFile);
			NodeList xmlNodeList;
			NodeList xmlEdgeList;
			xmlNodeList = doc.getElementsByTagName("node");

			//System.out.println("NODELIST:" + "\n");

			for(int i=0;i<xmlNodeList.getLength();i++) {

				Node p=xmlNodeList.item(i);
				Element xmlNode =(Element) p;
				NodeList.add(xmlNode);
				NodeMap.put(Integer.parseInt(xmlNode.getAttribute("id")),xmlNode);

				//System.out.println("NodeList" + i + ". elemenek Y-ja:" + xmlNode.getChildNodes().item(1).getTextContent() + "\n" + "id: " + xmlNode.getAttribute("id") + "\n");




				/*Node p=xmlNodeList.item(i);
				if(p.getNodeType()==Node.ELEMENT_NODE) {
					Element xmlNode =(Element) p;
					if("node".equals(xmlNode.getTagName())) {
						NodeList.add(xmlNode);
						NodeMap.put(Integer.parseInt(xmlNode.getAttribute("id")),xmlNode);
					} else if("edge".equals(xmlNode.getTagName())) {
						EdgeList.add(xmlNode);
					}
					/*String id=xmlNode.getAttribute("id");
					NodeList childNodeList = xmlNode.getChildNodes();
					for(int j=0;j<childNodeList.getLength();j++) {
						Node n=childNodeList.item(j);
						if(n.getNodeType()==Node.TEXT_NODE) {
							Element coordinate = (Element) childNodeList.;
							System.out.println("Person " + id + ": " + coordinate.getTagName() +
							"-" + coordinate.getTextContent());
						}

					}*/
				}
			xmlEdgeList = doc.getElementsByTagName("edge");

			//System.out.println("EDGELIST:" + "\n");

			for(int j=0;j<xmlEdgeList.getLength();j++) {
				Node p = xmlEdgeList.item(j);
				Element xmlNode = (Element) p;
				EdgeList.add(xmlNode);

				//System.out.println("EdgeList " + j + ". elemenek Y-ja:" + EdgeList.get(j).getChildNodes().item(1).getTextContent());
				//System.out.println("EdgeList aktualis merete: " + EdgeList.size() + "\n");
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	public LinkedList<Element> getNodeList() {
		return NodeList;
	}

	public void setNodeList(LinkedList<Element> nodeList) {
		NodeList = nodeList;
	}

	public LinkedList<Element> getEdgeList() {
		return EdgeList;
	}

	public void setEdgeList(LinkedList<Element> edgeList) {
		EdgeList = edgeList;
	}

	public HashMap<Integer, Element> getNodeMap() {
		return NodeMap;
	}

}
