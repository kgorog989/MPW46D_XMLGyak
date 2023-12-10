package hu.saxmpw46d;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class XsdMPW46D {

    public static void main(String[] args) {
        String xmlFilePath = "MPW46D_1025/SaxMPW46D/src/xml/MPW46D_kurzusfelvetel.xml";
        String xsdFilePath = "MPW46D_1025/SaxMPW46D/src/xml/MPW46D_kurzusfelvetel.xsd";

        if (validateXMLAgainstXSD(xmlFilePath, xsdFilePath)) {
            System.out.println("Successful validation");
        } else {
            System.out.println("Unsuccessful validation");
        }
    }

    private static boolean validateXMLAgainstXSD(String xmlFilePath, String xsdFilePath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFilePath));
            return true;
        } catch (SAXException | java.io.IOException e) {
            return false;
        }
    }
}
