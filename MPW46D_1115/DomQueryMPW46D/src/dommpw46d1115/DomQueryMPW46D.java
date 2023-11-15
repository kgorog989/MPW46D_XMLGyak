package dommpw46d1115;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class DomQueryMPW46D {

    public static void main(String[] args) {
        try {
            File inputFile = new File("MPW46D_1115/DomQueryMPW46D/MPW46D_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.print("Root element: ");
            System.out.println(doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("kurzus");
            System.out.println("----------------------------");
            
            System.out.println("Kurzusnevek : ");
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  NodeList carNameList = eElement.getElementsByTagName("kurzusnev");
                  
                  for (int count = 0; count < carNameList.getLength(); count++) {
                     Node node1 = carNameList.item(count);
                     
                     if (node1.getNodeType() == Node.ELEMENT_NODE) {
                        Element car = (Element) node1;
                        System.out.println(car.getTextContent());
                     }
                  }
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
    }
}