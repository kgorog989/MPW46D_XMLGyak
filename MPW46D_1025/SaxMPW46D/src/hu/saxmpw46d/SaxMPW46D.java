package hu.saxmpw46d;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxMPW46D {
	public static void main(String[] args) {

		try {
			File inputFile = new File("MPW46D_1025/SaxMPW46D/src/xml/MPW46D_kurzusfelvetel.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			HandlerMPW46D handler = new HandlerMPW46D();
			saxParser.parse(inputFile, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
