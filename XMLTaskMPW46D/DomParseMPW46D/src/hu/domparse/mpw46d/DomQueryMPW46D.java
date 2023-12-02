package hu.domparse.mpw46d;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomQueryMPW46D {
	public static void main(String[] args) {
		try {
			File inputFile = new File("XMLTaskMPW46D/XMLMPW46D.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Gyökérelem :" + doc.getDocumentElement().getNodeName());

			System.out.println("\n-----------------\n");

            //dolgozók neveinek kiírása
            System.out.println("\nDolgozók: ");
            NodeList dolgozoList = doc.getElementsByTagName("dolgozó");

            for (int i = 0; i < dolgozoList.getLength(); i++) {
                Node node = dolgozoList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element dolgozoElement = (Element) node;

                    // név elem lekérése
                    Element nevElement = (Element) dolgozoElement.getElementsByTagName("név").item(0);

                    // név kiírása
                    String nevValue = nevElement.getTextContent();
                    System.out.println("Dolgozó neve: " + nevValue);
                }
            }

            // budapesti cukrászdák neveinek kiírása
            System.out.println("\nBudapesti cukrászdák: ");
            NodeList cukraszdaList = doc.getElementsByTagName("cukrászda");

            for (int i = 0; i < cukraszdaList.getLength(); i++) {
                Node node = cukraszdaList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element cukraszdaElement = (Element) node;

                    // a város lekérése
                    Element cimElement = (Element) cukraszdaElement.getElementsByTagName("cím").item(0);
                    String varosValue = cimElement.getElementsByTagName("város").item(0).getTextContent();

                    // ha Budapest, akkor kiíratjuk
                    if ("Budapest".equals(varosValue)) {
                        String nevValue = cukraszdaElement.getElementsByTagName("név").item(0).getTextContent();
                        System.out.println("Cukrászda neve: " + nevValue);
                    }
                }
            }

            // azoknak a süteményeknek a kiírása, amelyeket rendeltek
            System.out.println("\nRendelt sütemények: ");
            NodeList rendelList = doc.getElementsByTagName("rendel");

            for (int i = 0; i < rendelList.getLength(); i++) {
                Node node = rendelList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element rendelElement = (Element) node;

                    // a rendel elem SID értéke
                    String sidValue = rendelElement.getAttribute("rendel_SID");

                    // a megfelelő értékű sütemény elem
                    Element sutemenyElement = getSutemenyElement(doc, sidValue);

                    // a sütemény nevének lekérdezése és kiíratása
                    Element nevElement = (Element) sutemenyElement.getElementsByTagName("név").item(0);
                    String sutemenyNev = nevElement.getTextContent();

                    System.out.println("Rendelt sütemény neve: " + sutemenyNev);
                }
            }

            // az igazgatók adatainak kiírása
            System.out.println("\nIgazgatók és adataik:");
            NodeList igazgatoList = doc.getElementsByTagName("igazgató");

            for (int i = 0; i < igazgatoList.getLength(); i++) {
                Node node = igazgatoList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element igazgatoElement = (Element) node;

                    // tulajdonság kiírása
                    String iidValue = igazgatoElement.getAttribute("IID");
                    System.out.println("IID: " + iidValue);

                    // a gyermekelemek kiírása
                    Element nevElement = (Element) igazgatoElement.getElementsByTagName("név").item(0);
                    String nevValue = nevElement.getTextContent();
                    System.out.println("név: " + nevValue);

                    Element fizetesElement = (Element) igazgatoElement.getElementsByTagName("fizetés").item(0);
                    String fizetesValue = fizetesElement.getTextContent();
                    System.out.println("fizetés: " + fizetesValue);

                    NodeList elerhetosegList = igazgatoElement.getElementsByTagName("elérhetőség");
                    for (int j = 0; j < elerhetosegList.getLength(); j++) {
                        Element elerhetosegElement = (Element) elerhetosegList.item(j);
                        String elerhetosegValue = elerhetosegElement.getTextContent();
                        System.out.println("elérhetőség: " + elerhetosegValue);
                    }

                    System.out.println();
                }
            }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    // a sütemény lekérése SID alapján
    private static Element getSutemenyElement(Document document, String sid) {
        NodeList sutemenyList = document.getElementsByTagName("sütemény");

        for (int i = 0; i < sutemenyList.getLength(); i++) {
            Node node = sutemenyList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getAttribute("SID").equals(sid)) {
                    return element;
                }
            }
        }

        return null; // nullot dob, ha nem találta a süteményt
    }
}
