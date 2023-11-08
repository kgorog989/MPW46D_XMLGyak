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
			File inputFile = new File("MPW46D_1108/DomParseMPW46D/MPW46D_kurzusfelvetel.xml");
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
				Node hallgatoNode = hallgatoList.item(temp);
				Element hElement = (Element) hallgatoNode;
				System.out.println("\n<start> " + hallgatoNode.getNodeName() + " {evf : " + hElement.getAttribute("evf") + "}");

				if (hallgatoNode.getNodeType() == Node.ELEMENT_NODE) {
					Element kElement = (Element) hallgatoNode;
					System.out.println(
							" nev: " + kElement.getElementsByTagName("hnev").item(0).getTextContent());
					System.out.println(
							" szulev: " + kElement.getElementsByTagName("szulev").item(0).getTextContent());
					System.out.println(
							" szak: " + kElement.getElementsByTagName("szak").item(0).getTextContent());
				}
				System.out.println("<end> " + hallgatoNode.getNodeName());
			}
			
			for (int temp = 0; temp < kurzusokList.getLength(); temp++) {
				Node kurzusokNode = kurzusokList.item(temp);
				System.out.println("\n<start> " + kurzusokNode.getNodeName());

				if (kurzusokNode.getNodeType() == Node.ELEMENT_NODE) {
					for (int t = 0; t < kurzusList.getLength(); t++) {
						Node kurzusNode = kurzusList.item(t);
						if (kurzusokNode.getNodeType() == Node.ELEMENT_NODE) {
							Element kElement = (Element) kurzusNode;
							System.out.println("\n <start> " + kurzusNode.getNodeName() + " {id: " + kElement.getAttribute("id") + ", jovahagyas: " + kElement.getAttribute("jovahagyas") + "}");

							System.out.println(
									"  kurzusnev: " + kElement.getElementsByTagName("kurzusnev").item(0).getTextContent());
							System.out.println("  kredit: " + kElement.getElementsByTagName("kredit").item(0).getTextContent());
							System.out.println("  hely: " + kElement.getElementsByTagName("hely").item(0).getTextContent());
							System.out.println("   <start> idopont");
							System.out.println("    nap: " + kElement.getElementsByTagName("nap").item(0).getTextContent());
							System.out.println("    kezdopont: " + kElement.getElementsByTagName("kezdopont").item(0).getTextContent());
							System.out.println("    vegpont: " + kElement.getElementsByTagName("vegpont").item(0).getTextContent());
							System.out.println("   <end> idopont");
							System.out.println("  oktato: " + kElement.getElementsByTagName("oktato").item(0).getTextContent());
						}
						System.out.println(" <end> " + kurzusNode.getNodeName());
					}
				}
				System.out.println("<end> " + kurzusokNode.getNodeName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
