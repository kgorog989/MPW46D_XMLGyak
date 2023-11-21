package dommpw46d1115;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DomQueryMPW46D {
   private static Set<String> uniqueOktatos = new HashSet<>();

   public static void main(String[] args) {
      try {
         File inputFile = new File("MPW46D_1115/DomQueryMPW46D/MPW46D_kurzusfelvetel.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.print("Root element: ");
         System.out.println(doc.getDocumentElement().getNodeName());
         NodeList kurzusList = doc.getElementsByTagName("kurzus");
         System.out.println("----------------------------");

         System.out.println("Kurzusnevek : ");
         for (int temp = 0; temp < kurzusList.getLength(); temp++) {
            Node nNode = kurzusList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               NodeList kurzusNameList = eElement.getElementsByTagName("kurzusnev");

               for (int count = 0; count < kurzusNameList.getLength(); count++) {
                  Node node1 = kurzusNameList.item(count);

                  if (node1.getNodeType() == Node.ELEMENT_NODE) {
                     Element car = (Element) node1;
                     System.out.println(car.getTextContent());
                  }
               }
            }
         }

         NodeList hallgatoList = doc.getElementsByTagName("hallgato");
         System.out.println("\n---------\nPrinting to file...\n");

         if (hallgatoList.getLength() > 0) {
            Node firstInstance = hallgatoList.item(0);
            if (firstInstance.getNodeType() == Node.ELEMENT_NODE) {
               Element firstElement = (Element) firstInstance;

               String attributeValue = firstElement.getAttribute("evf");
               String textContent = firstElement.getTextContent();
               System.out.println("Attribute: (evf) " + attributeValue);
               System.out.println("Text Content: " + textContent);

               writeToFile(firstElement, "MPW46D_1115/DomQueryMPW46D/output.xml");
            }
         }

         System.out.println("----------------------------");

         System.out.println("Oktatok : ");
         for (int temp = 0; temp < kurzusList.getLength(); temp++) {
            Node nNode = kurzusList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               NodeList oktatoNameList = eElement.getElementsByTagName("oktato");

               for (int count = 0; count < oktatoNameList.getLength(); count++) {
                  Node node1 = oktatoNameList.item(count);

                  if (node1.getNodeType() == Node.ELEMENT_NODE) {
                     Element oktato = (Element) node1;
                     String oktatoText = oktato.getTextContent();

                     if (uniqueOktatos.add(oktatoText)) {
                        System.out.println(oktatoText);
                     }
                  }
               }
            }
         }

         System.out.println("\n---------\nSubjects held on Wednesday\n");

         for (int temp = 0; temp < kurzusList.getLength(); temp++) {
            Node nNode = kurzusList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element kurzusElement = (Element) nNode;

               NodeList idopontList = kurzusElement.getElementsByTagName("idopont");
               for (int i = 0; i < idopontList.getLength(); i++) {
                  Node idopontNode = idopontList.item(i);
                  if (idopontNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element idopontElement = (Element) idopontNode;
                     String nap = idopontElement.getElementsByTagName("nap").item(0).getTextContent();

                     // azon kurzusok neveinek es id-einek kiiratasa, amik szerdan vannak
                     if ("szerda".equalsIgnoreCase(nap)) {
                        String kurzusId = kurzusElement.getAttribute("id");
                        String kurzusname = kurzusElement.getElementsByTagName("kurzusnev").item(0).getTextContent();
                        System.out.println("Kurzus Id: " + kurzusId + ", Kurzusnev: " + kurzusname);
                     }
                  }
               }
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void writeToFile(Element element, String fileName) throws Exception {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document newDocument = builder.newDocument();
      Node importedNode = newDocument.importNode(element, true);
      newDocument.appendChild(importedNode);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(newDocument);
      StreamResult result = new StreamResult(new File(fileName));
      transformer.transform(source, result);
   }
}