package hu.saxmpw46d;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandlerMPW46D extends DefaultHandler {

    private StringBuilder currentData;
    private int depth;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentData = new StringBuilder();

        // kiírom a nevét
        System.out.print(indent(depth) + qName);

        // ha van attribútuma, kiírom
        if (attributes.getLength() > 0) {
            System.out.print(", {");
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(attributes.getQName(i) + ": " + attributes.getValue(i));
                if (i < attributes.getLength() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("}");
        }

        System.out.println(":");
        depth++;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        depth--;

        // az egyes objektumok legvégén nem írunk már ki értéket
        if (qName.equals("hallgato") || qName.equals("kurzus") || qName.equals("idopont")
                || qName.equals("kurzusok") || qName.equals("MPW46D_kurzusfelvetel")) {
            return;
        }

        String currentDataTrimmed = currentData.toString().trim();
        if (!currentDataTrimmed.isEmpty()) {
            System.out.println(indent(depth) + currentDataTrimmed);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentData.append(ch, start, length);
    }

    private String indent(int depth) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indentation.append("	");
        }
        return indentation.toString();
    }
}
