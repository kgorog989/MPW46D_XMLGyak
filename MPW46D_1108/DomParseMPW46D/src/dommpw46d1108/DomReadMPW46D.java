package dommpw46d1108;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomReadMPW46D {

	public static void main(String[] args) {
		try {
			File inputFile = new File("MPW46D_kurzusfelvetel.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName() + " {tanev: " + doc.getDocumentElement().getAttribute("tanev") + ", egyetem: " + doc.getDocumentElement().getAttribute("egyetem") + "}");
			NodeList hallgatoList = doc.getElementsByTagName("hallgato");
			NodeList kurzusokList = doc.getElementsByTagName("kurzusok");
			NodeList kurzusList = doc.getElementsByTagName("kurzus");
			System.out.println("----------------------------");

			for (int temp = 0; temp < hallgatoList.getLength(); temp++) {
				Node nNode = hallgatoList.item(temp);
				Element eEl = (Element) nNode;
				System.out.println("\n<start> " + nNode.getNodeName() + " {evf : " + eEl.getAttribute("evf") + "}");

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println(
							" nev: " + eElement.getElementsByTagName("hnev").item(0).getTextContent());
					System.out.println(
							" szulev: " + eElement.getElementsByTagName("szulev").item(0).getTextContent());
					System.out.println(
							" szak: " + eElement.getElementsByTagName("szak").item(0).getTextContent());
				}
				System.out.println("<end> " + nNode.getNodeName());
			}
			
			for (int temp = 0; temp < kurzusokList.getLength(); temp++) {
				Node nNode = kurzusokList.item(temp);
				System.out.println("\n<start> " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					for (int tempp = 0; temp < kurzusList.getLength(); temp++) {
						Node nNodee = kurzusList.item(tempp);
						System.out.println("\n <start> " + nNodee.getNodeName());
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							System.out.println(
									"  kurzusnev: " + eElement.getElementsByTagName("kurzusnev").item(0).getTextContent());
							
						}
						System.out.println("  <end> " + nNodee.getNodeName());
					}
				}
				System.out.println(" <end> " + nNode.getNodeName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
