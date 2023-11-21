package dommpw46d1115;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;

public class DomModifyMPW46D {
   public static void main(String[] args) {
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document document = builder.parse(new File("MPW46D_1115/DomModifyMPW46D/MPW46D_kurzusfelvetel.xml"));

         NodeList kurzusList = document.getElementsByTagName("kurzus");
         for (int temp = 0; temp < kurzusList.getLength(); temp++) {
            Node nNode = kurzusList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element kurzusElement = (Element) nNode;

               NodeList oktatoList = kurzusElement.getElementsByTagName("oktato");

               if (oktatoList.getLength() == 0 || oktatoList.item(0).getTextContent().trim().isEmpty()) {
                  Element newOktatoElement = document.createElement("oktato");
                  newOktatoElement.setTextContent("The teacher was missing here.");
                  kurzusElement.appendChild(newOktatoElement);
               }
            }
         }

         saveDocument(document, "MPW46D_1115/DomModifyMPW46D/kurzusfelvetelModify1MPW46D.xml");

         printDocument(document);

         System.out.println("\n----------\nModifying 'angol' to 'nemet'...\n");

         NodeList nyelvList = document.getElementsByTagName("nyelv");
         for (int i = 0; i < nyelvList.getLength(); i++) {
            Node nyelvNode = nyelvList.item(i);
            if (nyelvNode.getNodeType() == Node.ELEMENT_NODE) {
               Element nyelvElement = (Element) nyelvNode;

               if ("angol".equals(nyelvElement.getTextContent())) {
                  nyelvElement.setTextContent("nemet");
               }
            }
         }

         saveDocument(document, "MPW46D_1115/DomModifyMPW46D/kurzusfelvetelModify2MPW46D.xml");

         printDocument(document);

         System.out.println("\n----------\nMore complex modification...\n");

         for (int i = 0; i < kurzusList.getLength(); i++) {
            Node kurzusNode = kurzusList.item(i);
            if (kurzusNode.getNodeType() == Node.ELEMENT_NODE) {
               Element kurzusElement = (Element) kurzusNode;

               NodeList helyList = kurzusElement.getElementsByTagName("hely");
               NodeList idopontList = kurzusElement.getElementsByTagName("idopont");
               if (helyList.getLength() > 0 && idopontList.getLength() > 0) {
                  Element helyElement = (Element) helyList.item(0);
                  Element idopontElement = (Element) idopontList.item(0);
                  String currentHely = helyElement.getTextContent();
                  String currentNap = idopontElement.getElementsByTagName("nap").item(0).getTextContent();

                  if ("XXX.".equals(currentHely) && "hétfő".equals(currentNap)) {
                     NodeList kreditList = kurzusElement.getElementsByTagName("kredit");
                     if (kreditList.getLength() > 0) {
                        Element kreditElement = (Element) kreditList.item(0);
                        kreditElement.setTextContent("20");
                     }
                  }
               }
            }
         }

         saveDocument(document, "MPW46D_1115/DomModifyMPW46D/kurzusfelvetelModify3MPW46D.xml");

         printDocument(document);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void saveDocument(Document document, String fileName) {
      try {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(document);
         StreamResult result = new StreamResult(new File(fileName));
         transformer.transform(source, result);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void printDocument(Document document) {
      try {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         StringWriter writer = new StringWriter();
         transformer.transform(new DOMSource(document), new StreamResult(writer));

         System.out.println(writer.toString());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
