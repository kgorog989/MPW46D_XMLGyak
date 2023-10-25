package hu.saxneptunkod;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandlerMPW46D extends DefaultHandler {
	boolean hnev = false;
	boolean szulev = false;
	boolean szak = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("MPW46D_kurzusfelvetel")) {
			System.out.println("Start Element: " + qName);
			String tanev = attributes.getValue("tanev");
			System.out.println("Tanév : " + tanev);
			String egyetem = attributes.getValue("egyetem");
			System.out.println("Egyetem : " + egyetem);
		}

		if (qName.equalsIgnoreCase("hallgato")) {
			System.out.println("	Start Element: " + qName);
			String evf = attributes.getValue("evf");
			System.out.println("	Évfolyam : " + evf);
		} else if (qName.equalsIgnoreCase("hnev")) {
			hnev = true;
		} else if (qName.equalsIgnoreCase("szulev")) {
			szulev = true;
		} else if (qName.equalsIgnoreCase("szak")) {
			szak = true;
		}
		
		if (qName.equalsIgnoreCase("kurzusok")) {
			System.out.println("	Start Element: " + qName);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("hallgato")) {
			System.out.println("	End Element :" + qName);
		}
		
		if (qName.equalsIgnoreCase("kurzusok")) {
			System.out.println("	End Element :" + qName);
		}
		
		if (qName.equalsIgnoreCase("MPW46D_kurzusfelvetel")) {
			System.out.println("End Element :" + qName);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (hnev) {
			System.out.println("	Hallgató neve: " + new String(ch, start, length));
			hnev = false;
		} else if (szulev) {
			System.out.println("	Születési év: " + new String(ch, start, length));
			szulev = false;
		} else if (szak) {
			System.out.println("	Szak: " + new String(ch, start, length));
			szak = false;
		}
	}
}
