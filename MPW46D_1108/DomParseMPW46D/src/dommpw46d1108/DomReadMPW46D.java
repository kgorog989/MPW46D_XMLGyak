package dommpw46d1108;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class DomReadMPW46D {

	public static void main(String[] args) {
		try {
			File inputFile = new File("MPW46D_1108/DomParseMPW46D/MPW46D_kurzusfelvetel.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Gyökérelem :" + doc.getDocumentElement().getNodeName());

			System.out.println("\n-----------------\n");

			printElement(doc.getDocumentElement(), "");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printElement(Element element, String indent) {
		// jelenlegi elem
		System.out.print(indent + "<" + element.getTagName());

		// az elem attribútumai
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			System.out.print(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
		}

		// az elem gyermekelemei
		NodeList children = element.getChildNodes();
		if (children.getLength() == 0) {
			// ha nincsenek gyermekelemei, akkor záró taggel fejezzük be
			System.out.println("/>");
		} else {
			// ha vannak gyermekelemei, akkor normális záró taggel folytatjuk
			System.out.println(">");

			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				// ha a gyermekelem is tartalmaz elemet, akkor arra is meghívjuk a printElementet, ha nem, akkor kiírjuk a szöveget
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					printElement((Element) child, indent + "	");
				} else if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().length() > 0) {
					System.out.println(indent + "  " + child.getNodeValue().trim());
				}
			}

			// a jelenlegi elem záró tagje
			System.out.println(indent + "</" + element.getTagName() + ">");
		}
	}

}
