package hu.domparse.mpw46d;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
			File inputFile = new File("XMLTaskMPW46D/XMLMPW46D.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Gyökérelem :" + doc.getDocumentElement().getNodeName());

			System.out.println("\n-----------------\n");

			// XML kiírása a konzolra
			printElement(doc.getDocumentElement(), "");

			// XML mentése a DomReadMPW46D_output.xml fájlba
			writeXmlToFile(doc, "XMLTaskMPW46D/DomParseMPW46D/DomReadMPW46D_output.xml");

			System.out.println("\n----------------\nXML kiírása az output fájlba teljesítve.\n");

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
			System.out.print("/>");
		} else {
			// ha vannak gyermekelemei, akkor normális záró taggel folytatjuk
			System.out.print(">");

			boolean hasTextContent = false;

			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				// ha a gyermekelem is tartalmaz elemet, akkor arra is meghívjuk a
				// printElementet, ha nem, akkor kiírjuk a szöveget
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					// ha van gyermeke új sorban folytatjuk, majd meghívjuk rá is a függvényt
					System.out.println();
					printElement((Element) child, indent + "\t");
					hasTextContent = false;
				} else if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().length() > 0) {
					System.out.print(child.getNodeValue().trim());
					hasTextContent = true;
				}
			}

			// a jelenlegi elem záró tagje
			if (hasTextContent) {
				System.out.print("</" + element.getTagName() + ">");
			} else {
				System.out.print("\n" + indent + "</" + element.getTagName() + ">");
			}
		}
	}

	private static void writeXmlToFile(Document document, String outputFilePath) throws IOException {
		try (
				// outputstream nyitása a fájlnak
				OutputStream outputStream = new FileOutputStream(outputFilePath);

				// UTF-8 writer
				Writer writer = new OutputStreamWriter(outputStream, "UTF-8")) {
			// transformer készítése a DOM streammé formálásához
			javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory
					.newInstance();
			javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();

			// az indentálás, a szóközök beállítása, az xml deklarációjának
			// elvetése/meghagyása
			// transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "no");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
			// "1");
			// transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION,
			// "yes");

			// a forrás (tehát a DOM) és a cél (az output) deklarálása
			javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(document);
			javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(writer);

			// transzformálás
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
