package dommpw46d1108;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

public class DomWriteMPW46D {

    private Document doc;
    private Element root;

    public DomWriteMPW46D() throws Exception {
        // konstruktor a dokumentum építőhöz
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();

        // létrehozzuk a gyökérelemet
        root = doc.createElement("MPW46D_kurzusfelvetel");
        root.setAttribute("tanev", "2023/24 I.");
        root.setAttribute("egyetem", "ME");
        root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xs:noNamespaceSchemaLocation", "MPW46D_kurzusfelvetel.xsd");
        doc.appendChild(root);
    }

    // hallgató hozzáadása
    public void addHallgato(String evf, String hnev, String szulev, String szak) {
        Element hallgato = doc.createElement("hallgato");
        hallgato.setAttribute("evf", evf);

        createElement("hnev", hnev, hallgato);
        createElement("szulev", szulev, hallgato);
        createElement("szak", szak, hallgato);

        root.appendChild(hallgato);
    }

    // kurzus hozzáadása
    public void addKurzus(String id, String jovahagyas, String kurzusnev, String kredit, String hely,
            String nap, String kezdopont, String vegpont, String oktato) {
        // megnézzük, létezik-e a kurzusok element, ha nem, akkor létrehozzuk
        Element kurzusokElement = (Element) root.getElementsByTagName("kurzusok").item(0);
        if (kurzusokElement == null) {
            kurzusokElement = doc.createElement("kurzusok");
            root.appendChild(kurzusokElement);
        }

        Element kurzus = doc.createElement("kurzus");
        kurzus.setAttribute("id", id);
        kurzus.setAttribute("jovahagyas", jovahagyas);

        createElement("kurzusnev", kurzusnev, kurzus);
        createElement("kredit", kredit, kurzus);
        createElement("hely", hely, kurzus);

        Element idopont = doc.createElement("idopont");
        createElement("nap", nap, idopont);
        createElement("kezdopont", kezdopont, idopont);
        createElement("vegpont", vegpont, idopont);
        kurzus.appendChild(idopont);

        createElement("oktato", oktato, kurzus);

        kurzusokElement.appendChild(kurzus);
    }

    // elem létrehozása
    private void createElement(String elementName, String value, Element parent) {
        Element element = doc.createElement(elementName);
        Text textNode = doc.createTextNode(value);
        element.appendChild(textNode);
        parent.appendChild(element);
    }

    // a dokumentum kiírása fájlba
    public void docToFile(String fileName) throws Exception {
        // transzformer létrehozása
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // indentálás beállítása
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

        // forrás és cél beállítása
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(fileName));

        // transzformálás
        transformer.transform(source, result);
    }

    // xml kiíratása a konzolra
    public void printXml() throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);

        // DOMSource áttranszformálása StreamResulttá
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(source, result);

        // kiíratás
        System.out.println(stringWriter.toString());
    }

    public static void main(String[] args) {
        try {
            DomWriteMPW46D builder = new DomWriteMPW46D();

            // Hallgató hozzáadása
            builder.addHallgato("3", "Görög Krisztina Erzsébet", "2002-06-14", "programtervező informatikus");

            // Kurzusok hozzáadása
            builder.addKurzus("GEIAL331-B", "igen", "WEB technológiák I.", "5", "XXX.", "hétfő", "14", "16",
                    "Dr. Agárdi Anita");
            builder.addKurzus("GEIAL331-B gy", "igen", "WEB technológiák I.", "5", "inf/202.", "hétfő", "16", "18",
                    "Dr. Agárdi Anita");
            builder.addKurzus("GEIAK130-B", "igen", "Mesterséges intelligencia", "5", "XXXII.", "kedd", "10", "12",
                    "Kunné Dr. Tamás Judit");
            builder.addKurzus("GEIAL332-B", "igen", "Adatkezelés XML-ben", "5", "XXXII.", "kedd", "12", "14",
                    "Dr. Bednarik László");
            builder.addKurzus("GEMAK234-B", "igen", "Algoritmusok és vizsgálatuk", "5", "A1/320.", "kedd", "14", "18",
                    "Dr. Házy Attila");
            builder.addKurzus("GEIAL332-B", "igen", "Adatkezelés XML-ben", "5", "inf/103.", "szerda", "12", "14",
                    "Dr. Bednarik László");
            builder.addKurzus("GEIAL315-B", "igen", "Vállalati információs rendszerek fejlesztése", "5", "inf/101.",
                    "szerda", "14", "16", "Dr. Sasvári Péter");
            builder.addKurzus("GEIAL315-B gy", "igen", "Vállalati információs rendszerek fejlesztése", "5", "inf/101.",
                    "szerda", "18", "20", "Dr. Sasvári Péter");
            builder.addKurzus("GEIAK130-B gy", "igen", "Mesterséges intelligencia", "5", "I.", "csütörtök", "10", "12",
                    "Fazekas Levente");

            System.out.println("A dokumentum felépítése megtörtént. ");

            builder.docToFile("MPW46D_1108/DomParseMPW46D/MPW46D_kurzusfelvetel1.xml");
            System.out.println("A kiírás megtörtént az MPW46D_kurzusfelvetel1.xml dokumentumba. ");
            System.out.println("\n----------------------\n");

            builder.printXml();
            System.out.println("A kiírás a konzolra megtörtént. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
