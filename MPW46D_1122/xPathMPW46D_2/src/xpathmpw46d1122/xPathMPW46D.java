package xpathmpw46d1122;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class xPathMPW46D {
   
   public static void main(String[] args) {
      
      try {
         File inputFile = new File("MPW46D_1122/xPathMPW46D_2/MPW46D_kurzusfelvetel.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder;

         dBuilder = dbFactory.newDocumentBuilder();

         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();

         String expression1 = "//hallgato[1]/hnev";
         String expression2 = "//kurzus[@id='GEIAL331-B gy']/kurzusnev";
         String expression3 = "//kurzus[@id='GEIAK130-B gy']/idopont/nap";
         
         modifyNodeValue(doc, expression1, "Nagy Tamás");
         modifyNodeValue(doc, expression2, "Matematika");
         modifyNodeValue(doc, expression3, "péntek");

         saveModifiedXML(doc, "MPW46D_1122/xPathMPW46D_2/kurzusfelvetelMPW46D_1.xml");

         System.out.println("\n-----------------Printing modified document...\n");
         printDocument(doc);
      
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void modifyNodeValue(Document document, String xpathExpression, String newValue) {
      try {
          XPath xPath = XPathFactory.newInstance().newXPath();
          XPathExpression expr = xPath.compile(xpathExpression);

          Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
  
          if (node != null) {
              node.setTextContent(newValue);
              System.out.println("Node value modified successfully.");
          } else {
              System.out.println("Node not found.");
          }
      } catch (XPathExpressionException e) {
          e.printStackTrace();
      }
  }
  

   public static void saveModifiedXML(Document document, String fileName) {
      try {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         transformer.transform(new DOMSource(document), new StreamResult(fileName));
         System.out.println("Modified XML saved successfully.");
      } catch (TransformerException e) {
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
