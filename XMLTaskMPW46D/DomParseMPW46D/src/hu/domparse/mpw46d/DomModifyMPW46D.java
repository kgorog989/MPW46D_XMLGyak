package hu.domparse.mpw46d;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;


public class DomModifyMPW46D {
    
	public static void main(String[] args) {
		try {
			File inputFile = new File("XMLTaskMPW46D/XMLMPW46D.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Gyökérelem :" + doc.getDocumentElement().getNodeName());

			System.out.println("\n-----------------\n");

            // az első dolgozó nevének megváltoztatása
            NodeList dolgozoList = doc.getElementsByTagName("dolgozó");
            for (int temp = 0; temp < dolgozoList.getLength(); temp++) {
                Node nNode = dolgozoList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element dolgozoElement = (Element) nNode;

                NodeList nevList = dolgozoElement.getElementsByTagName("név");

                if (dolgozoElement.getAttribute("DID").equals("d1")) {
                    nevList.item(0).setTextContent("Kiss Márta");
                }
                }
            }

            // ahol a rendezvény házszáma nem üres, megváltoztatjuk 1000-re
            NodeList rendezvenyList = doc.getElementsByTagName("rendezvény");
            for (int temp = 0; temp < rendezvenyList.getLength(); temp++) {
                Node nNode = rendezvenyList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rendezvenyElement = (Element) nNode;

                NodeList hazszamList = rendezvenyElement.getElementsByTagName("házszám");

                if (hazszamList.getLength() != 0) {
                    hazszamList.item(0).setTextContent("1000");
                }
                }
            }

            // új allergén adása a csokis szelethez
            NodeList sutemenyList = doc.getElementsByTagName("sütemény");
            for (int i = 0; i < sutemenyList.getLength(); i++) {
                Node sutemenyNode = sutemenyList.item(i);
                if (sutemenyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sutemenyElement = (Element) sutemenyNode;
                    String currentSid = sutemenyElement.getAttribute("SID");
                    if (currentSid.equals("s3")) {
                        Element newOsszegElement = doc.createElement("allergének");
                        newOsszegElement.appendChild(doc.createTextNode("mogyoró"));

                        sutemenyElement.appendChild(newOsszegElement);
                    }
                }
            }

            // az igatgatók fizetésének megemelése 50000-el
            NodeList igazgatoList = doc.getElementsByTagName("igazgató");
            for (int i = 0; i < igazgatoList.getLength(); i++) {
                Node node = igazgatoList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Element fizetesElement = (Element) element.getElementsByTagName("fizetés").item(0);

                    int currentSalary = Integer.parseInt(fizetesElement.getTextContent());
                    int newSalary = currentSalary + 50000;
                    fizetesElement.setTextContent(Integer.toString(newSalary));
                }
            }

            // az összeg származtatott tulajdonság kiszámítása (rendelés darabszám * sütemény egységár) és módosítása
            calcOsszeg(doc);

            System.out.println("\nMódosítások elvégezve!------------\n");

			// a módosított XML kiírása a konzolra
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

    // az összeg kiszámítása
    private static void calcOsszeg(Document document) {
        NodeList rendelList = document.getElementsByTagName("rendel");
    
        for (int i = 0; i < rendelList.getLength(); i++) {
            Node node = rendelList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Element darabszamElement = (Element) element.getElementsByTagName("darabszám").item(0);
                String sidValue = element.getAttribute("rendel_SID");
    
                // a megfelelő sütemény elem egységárának lekérdezése
                Element sutemenyElement = getSutemenyElement(document, sidValue);
                Element egysegarElement = (Element) sutemenyElement.getElementsByTagName("egységár").item(0);
    
                // az összeg kiszámítása
                int darabszam = Integer.parseInt(darabszamElement.getTextContent());
                int egysegAr = Integer.parseInt(egysegarElement.getTextContent());
                int osszeg = darabszam * egysegAr;
    
                Element newOsszegElement = document.createElement("összeg");
                newOsszegElement.appendChild(document.createTextNode(Integer.toString(osszeg)));
    
                element.appendChild(newOsszegElement);
            }
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
