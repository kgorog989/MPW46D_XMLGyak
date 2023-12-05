package hu.domparse.mpw46d;

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
        root = doc.createElement("MPW46D_cukrászda");
        root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaMPW46D.xsd");
        doc.appendChild(root);
    }

	// metódus a dolgozó hozzáadásához
    public void addDolgozo(String did, String cid, String nev, int fizetes, String beosztas) {
        // elem létrehozása
		Element dolgozo = doc.createElement("dolgozó");
		// attribútumok hozzáadása
        dolgozo.setAttribute("DID", did);
        dolgozo.setAttribute("dolgozó_CID", cid);

		// a node elemei hozzádása
        createElement("név", nev, dolgozo);
        createElement("fizetés", String.valueOf(fizetes), dolgozo);
        createElement("beosztás", beosztas, dolgozo);

		// hozzáadjuk a dokumentumhoz
        root.appendChild(dolgozo);
    }

	// metódus az igazgató hozzáadásához
    public void addIgazgato(String iid, String nev, int fizetes, String... elerhetoseg) {
        // elem létrehozása
		Element igazgato = doc.createElement("igazgató");
		// attribútum hozzáadása
        igazgato.setAttribute("IID", iid);

		// a node elemei
        createElement("név", nev, igazgato);
        createElement("fizetés", String.valueOf(fizetes), igazgato);

		// az elérhetőségek hozzáadása iteratívan
        for (String elerhet : elerhetoseg) {
            createElement("elérhetőség", elerhet, igazgato);
        }

		// hozzáadás a dokumentumhoz
        root.appendChild(igazgato);
    }

	// metódus a cukrászda hozzáadásához
    public void addCukraszda(String cid, String nev, String varos, int iranyitoszam, String utca, int hazszam) {
        // cukrászda létrehozása
		Element cukraszda = doc.createElement("cukrászda");
		// attribútum hozzáadása
        cukraszda.setAttribute("CID", cid);

		// elem hozzáadása
        createElement("név", nev, cukraszda);

		// a cím létrehozása
        Element cim = doc.createElement("cím");
        createElement("város", varos, cim);
        createElement("irányítószám", String.valueOf(iranyitoszam), cim);
        createElement("utca", utca, cim);
        createElement("házszám", String.valueOf(hazszam), cim);

		// a cím hozzáadása a cukrászdához
        cukraszda.appendChild(cim);
		// a cukrászda hozzáadása a dokumentumhoz
        root.appendChild(cukraszda);
    }

	// az árul hozzáadása
	public void addArul(String cid, String sid) {
		// árul létrehozása
        Element arul = doc.createElement("árul");
		// attrubútumok létrehozása
        arul.setAttribute("árul_CID", cid);
        arul.setAttribute("árul_SID", sid);

		// arul hozzáadása a dokumentumhoz
        root.appendChild(arul);
    }

	// sütemény hozzáadása
    public void addSutemeny(String sid, String nev, int egysegar, String... allergenek) {
        // sütemény létrehozása
		Element sutemeny = doc.createElement("sütemény");
		// ID hozzáadása
        sutemeny.setAttribute("SID", sid);

		// elemek hozzáadása
        createElement("név", nev, sutemeny);
        createElement("egységár", String.valueOf(egysegar), sutemeny);

		// az allergéneket iteratívan adjuk hozzá
        for (String allergen : allergenek) {
            createElement("allergének", allergen, sutemeny);
        }

		// hozzáadás a dokumentumhoz
        root.appendChild(sutemeny);
    }

	// rendel hozzáadása
    public void addRendel(String sid, String mid, int darabszam) {
        // rendel létrehozása
		Element rendel = doc.createElement("rendel");
		// attribútumok hozzáadása
        rendel.setAttribute("rendel_SID", sid);
        rendel.setAttribute("rendel_MID", mid);

		// darabszám hozzáadása
        createElement("darabszám", String.valueOf(darabszam), rendel);

		// rendel hozzáadása a dokumentumhoz
        root.appendChild(rendel);
    }

	// megrendelő hozzáadása
    public void addMegrendelo(String mid, String nev, String... elerhetoseg) {
        // létrehozás és attribútum hozzáadása
		Element megrendelo = doc.createElement("megrendelő");
        megrendelo.setAttribute("MID", mid);

        createElement("név", nev, megrendelo);
        // az elérhetőségek hozzáadása iteratívan
        for (String elerhet : elerhetoseg) {
            createElement("elérhetőség", elerhet, megrendelo);
        }

		// hozzáadás a dokumentumhoz
        root.appendChild(megrendelo);
    }

	// rendezvény hozzáadása
    public void addRendezveny(String rid, String mid, String cid, String kezdes, String varos, int iranyitoszam, String utca, int hazszam) {
        // rendezvény létrehozása
        Element rendezveny = doc.createElement("rendezvény");
        // attribútumok hozzáadása
        rendezveny.setAttribute("RID", rid);
        rendezveny.setAttribute("rendezvény_MID", mid);
        rendezveny.setAttribute("rendezvény_CID", cid);

        // kezdés elem létrehozása
        createElement("kezdes", kezdes, rendezveny);

        // helyszín összetett tulajdonság létrehozása
        Element helyszin = doc.createElement("helyszín");
        createElement("város", varos, helyszin);
        createElement("irányítószám", String.valueOf(iranyitoszam), helyszin);
        createElement("utca", utca, helyszin);
        createElement("házszám", String.valueOf(hazszam), helyszin);

        // helyszín hozzáadása a rendezvényhez, rendezvény hozzáadása a dokumenumhoz
        rendezveny.appendChild(helyszin);
        root.appendChild(rendezveny);
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

    // elem létrehozása
    private void createElement(String elementName, String value, Element parent) {
        Element element = doc.createElement(elementName);
        Text textNode = doc.createTextNode(value);
        element.appendChild(textNode);
        parent.appendChild(element);
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

            // dolgozók hozzáadása
			builder.addDolgozo("d1", "c1", "Nagy Emese", 300000, "cukrász");
			builder.addDolgozo("d2", "c1", "Kiss Tamás", 700000, "főcukrász");
			builder.addDolgozo("d3", "c1", "Kovács Evelin", 250000, "cukrászgyakornok");
			builder.addDolgozo("d4", "c2", "Varga Levente", 500000, "cukrász");
			builder.addDolgozo("d5", "c2", "Sárosi Ferencné", 800000, "főcukrász");
			builder.addDolgozo("d6", "c2", "Nagy Andor", 600000, "cukrász");
			builder.addDolgozo("d7", "c3", "Nagy Andrea", 900000, "főcukrász");
			builder.addDolgozo("d8", "c3", "Tóth Tamás", 600000, "cukrász");

			// igazgatók hozzáadása
			builder.addIgazgato("c1", "Farkas Benedek", 1000000, "farkas.benedek@gmail.com", "+36201234567");
			builder.addIgazgato("c2", "Szőke László", 2000000, "+36702345645", "");
			builder.addIgazgato("c3", "Takács Ádám", 1500000, "+36705656789", "adam.takacs@gmail.com");

			// cukrászdák hozzáadása
			builder.addCukraszda("c1", "Kristály Cukrászda", "Budapest", 1082, "József krt.", 53);
			builder.addCukraszda("c2", "Daubner Cukrászda", "Budapest", 1025, "Szépvölgyi út", 50);
			builder.addCukraszda("c3", "Kismandula Cukrászda", "Debrecen", 4024, "Liszt Ferenc utca", 10);

			// árusítások hozzáadása
			builder.addArul("c1", "s1");
			builder.addArul("c1", "s2");
			builder.addArul("c1", "s3");
			builder.addArul("c1", "s4");
			builder.addArul("c2", "s1");
			builder.addArul("c2", "s2");
			builder.addArul("c2", "s3");
			builder.addArul("c2", "s4");
			builder.addArul("c3", "s1");
			builder.addArul("c3", "s2");
			builder.addArul("c3", "s3");
			builder.addArul("c3", "s4");

			// sütemények hozzáadása
			builder.addSutemeny("s1", "Mindenmentes süti", 3000);
			builder.addSutemeny("s2", "kókuszos kocka", 600, "tojás", "tej", "liszt");
			builder.addSutemeny("s3", "csokis szelet", 700, "liszt", "tojás");
			builder.addSutemeny("s4", "Madeira sütemény", 900, "liszt", "tej", "tojás", "sajt");

			// rendelések
			builder.addRendel("s1", "m1", 30);
			builder.addRendel("s4", "m2", 20);
			builder.addRendel("s3", "m3", 50);

			// megrendelők hozzáadása
			builder.addMegrendelo("m1", "Kiss Lászlóné", "+36203453434");
			builder.addMegrendelo("m2", "Horváth Csaba", "+36305675656");
			builder.addMegrendelo("m3", "Remete Ákos", "+36204557887");

			// rendezvények hozzáadása
			builder.addRendezveny("r1", "m1", "c1", "2023-12-12 16:00", "Budapest", 1181, "Városház utca", 1);
			builder.addRendezveny("r2", "m2", "c1", "2023-12-21 18:00", "Budapest", 1119, "Fehérvári út", 47);
			builder.addRendezveny("r3", "m3", "c2", "2023-12-29 19:00", "Budapest", 1133, "Kárpát utca", 23);
            
            System.out.println("A dokumentum felépítése megtörtént. ");

            builder.docToFile("XMLTaskMPW46D/DomParseMPW46D/XMLMPW46D1.xml");
            System.out.println("A kiírás megtörtént az XMLMPW46D1.xml dokumentumba. ");
            System.out.println("\n----------------------\n");

            builder.printXml();
            System.out.println("A kiírás megtörtént. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
