package com.vijai;


import com.vijai.bussiness.XmlGenerator;
import com.vijai.bussiness.splitting.Splitter;
import com.vijai.bussiness.splitting.SplitterStAX;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException, SAXException, TransformerException, ParserConfigurationException, XMLStreamException {
        XmlGenerator xml = new XmlGenerator();
        //xml.generate();

        /*Splitter splitter = new Splitter();
        splitter.split(2000);*/

        SplitterStAX splitterStAX = new SplitterStAX();
        splitterStAX.split(2000);

    }
}
//XPathExpression splits = xpath.compile(".[sum(*/(string-length() + (string-length(name()) + 2 ) * 2  +1)) <1600]");
//NodeList sp = (NodeList) splits.evaluate(doc, XPathConstants.STRING);
//System.out.println(sp);
    /*private static void printEvent(XMLStreamReader xmlr) {
        System.out.print("EVENT:[" + xmlr.getLocation().getLineNumber() + "][" +
                xmlr.getLocation().getColumnNumber() + "] ");
        System.out.print(" [");
        switch (xmlr.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                System.out.print("<");
                printName(xmlr);
                printNamespaces(xmlr);
                printAttributes(xmlr);
                System.out.print(">");
                break;
            case XMLStreamConstants.END_ELEMENT:
                System.out.print("</");
                printName(xmlr);
                System.out.print(">");
                break;
            case XMLStreamConstants.SPACE:
            case XMLStreamConstants.CHARACTERS:
                int start = xmlr.getTextStart();
                int length = xmlr.getTextLength();
                System.out.print(new String(xmlr.getTextCharacters(),
                        start,
                        length));
                break;
            case XMLStreamConstants.PROCESSING_INSTRUCTION:
                System.out.print("<?");
                if (xmlr.hasText())
                    System.out.print(xmlr.getText());
                System.out.print("?>");
                break;
            case XMLStreamConstants.CDATA:
                System.out.print("<![CDATA[");
                start = xmlr.getTextStart();
                length = xmlr.getTextLength();
                System.out.print(new String(xmlr.getTextCharacters(),
                        start,
                        length));
                System.out.print("]]>");
                break;
            case XMLStreamConstants.COMMENT:
                System.out.print("<!--");
                if (xmlr.hasText())
                    System.out.print(xmlr.getText());
                System.out.print("-->");
                break;
            case XMLStreamConstants.ENTITY_REFERENCE:
                System.out.print(xmlr.getLocalName() + "=");
                if (xmlr.hasText())
                    System.out.print("[" + xmlr.getText() + "]");
                break;
            case XMLStreamConstants.START_DOCUMENT:
                System.out.print("<?xml");
                System.out.print(" version='" + xmlr.getVersion() + "'");
                System.out.print(" encoding='" + xmlr.getCharacterEncodingScheme() + "'");
                if (xmlr.isStandalone())
                    System.out.print(" standalone='yes'");
                else
                    System.out.print(" standalone='no'");
                System.out.print("?>");
                break;
        }
        System.out.println("]");
    }

    private static void printName(XMLStreamReader xmlr) {
        if (xmlr.hasName()) {
            String prefix = xmlr.getPrefix();
            String uri = xmlr.getNamespaceURI();
            String localName = xmlr.getLocalName();
            printName(prefix, uri, localName);
            String charCount = prefix+localName;
            int length = charCount.length();
        }
    }

    private static void printName(String prefix,
                                  String uri,
                                  String localName) {
        if (uri != null && !("".equals(uri))) System.out.print("['" + uri + "']:");
        if (prefix != null) System.out.print(prefix + ":");
        if (localName != null) System.out.print(localName);
    }

    private static void printAttributes(XMLStreamReader xmlr) {
        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
            printAttribute(xmlr, i);
        }
    }

    private static void printAttribute(XMLStreamReader xmlr, int index) {
        String prefix = xmlr.getAttributePrefix(index);
        String namespace = xmlr.getAttributeNamespace(index);
        String localName = xmlr.getAttributeLocalName(index);
        String value = xmlr.getAttributeValue(index);
        System.out.print(" ");
        printName(prefix, namespace, localName);
        System.out.print("='" + value + "'");
    }

    private static void printNamespaces(XMLStreamReader xmlr) {
        for (int i = 0; i < xmlr.getNamespaceCount(); i++) {
            printNamespace(xmlr, i);
        }
    }

    private static void printNamespace(XMLStreamReader xmlr, int index) {
        String prefix = xmlr.getNamespacePrefix(index);
        String uri = xmlr.getNamespaceURI(index);
        System.out.print(" ");
        if (prefix == null)
            System.out.print("xmlns='" + uri + "'");
        else
            System.out.print("xmlns:" + prefix + "='" + uri + "'");
    }
}*/
/*  VTDGen vg = new VTDGen();
        // fill constant tagSize
        if (vg.parseFile("src/main/java/xsd/tag-template.xml", false)) {
            VTDNav vn = vg.getNav();
            AutoPilot ap = new AutoPilot(vn);
            ap.selectXPath("//*");
            ap.evalXPath();
            TAGS_SIZE = vn.getXML().length();
        }

        int numberOfRowsInFile = (USER_GIVEN_SIZE - TAGS_SIZE) / ROW_SIZE;
        //System.out.println("Number of rows we can insert in file: " + numberOfRowsInFile);
        if (vg.parseFile("src/main/resources/recordFile.xml", false)){
            VTDNav vn = vg.getNav();
            AutoPilot ap = new AutoPilot(vn);
            ap.selectXPath("record/record_id");
            ap.evalXPath();
            System.out.println(vn.getXML().length());
        }

        if (vg.parseFile("src/main/resources/recordFile.xml", false)){
            VTDNav vn = vg.getNav();
            AutoPilot ap = new AutoPilot(vn);
            ap.selectXPath("//record-table/record[2]");
            byte[] header = "<record-table>".getBytes();
            byte[] tail = "</record-table>".getBytes();
            ap.evalXPath();
            System.out.println(vn.getXML().length());
            int i = -1,n=0;
            while((i=ap.evalXPath())!=-1){
                long l = vn.getElementFragment();
                FileOutputStream fops = new FileOutputStream("src/main/resources/part_"+n+".xml");
                fops.write(header);
                fops.write(vn.getXML().getBytes(), (int)l, ((int)(l>>32)));
                fops.write(tail);
                vg.parseFile("src/main/resources/part_"+n+".xml", false);
                VTDNav vn2 = vg.getNav();
                System.out.println(vn2.getXML().length());
                fops.close();
                n++;
            }
        }*/
/*VTDGen vg = new VTDGen();
        // fill constant tagSize
        if (vg.parseFile("src/main/java/xsd/tag-template.xml", false)) {
            VTDNav vn = vg.getNav();
            AutoPilot ap = new AutoPilot(vn);
            ap.selectXPath("//*");
            ap.evalXPath();
            TAGS_SIZE = vn.getXML().length();
        }

        int numberOfRowsInFile = (USER_GIVEN_SIZE - TAGS_SIZE) / ROW_SIZE;
        System.out.println("Number of rows we can insert in file: " + numberOfRowsInFile);

        FastLongBuffer flb = new FastLongBuffer(1);
        if (vg.parseFile("src/main/resources/recordFile.xml", false)) {
            VTDNav vn = vg.getNav();
            AutoPilot ap = new AutoPilot(vn);
            ap.selectXPath("//record[2]/record_rows/record_row[position()<='"+numberOfRowsInFile+"']");
            int i = -1;
            while ((i = ap.evalXPath()) != -1) {
                System.out.println(vn.toString(i).toString());
            }
        }*/




       /* DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        XPathFactory xfactory = XPathFactory.newInstance();
        XPath xpath = xfactory.newXPath();

        XPathExpression allRecordsExpression = xpath.compile("//record-table/record");
        NodeList recordsList = (NodeList) allRecordsExpression.evaluate(doc, XPathConstants.NODESET);

        XPathExpression splits = xpath.compile("//record-table/record[string-length(record) > 1]"); //
        NodeList sp = (NodeList) splits.evaluate(doc, XPathConstants.STRING);
        System.out.println(sp);

        XPathExpression nodesCount = xpath.compile("count(//record/descendant-or-self::*)"); //
        String nc = (String) nodesCount.evaluate(doc, XPathConstants.STRING); // add node count variable
        System.out.println("nodes number is: " + nc);
        System.out.println("record number is: " + recordsList.getLength());

        //We store the new XML file in supplierName.xml e.g. Sony.xml
        Document recordsXml = dBuilder.newDocument();

        //we have to recreate the root node <record-table>
        Element root = recordsXml.createElement("record-table");
        recordsXml.appendChild(root);

        for (int i=0; i<recordsList.getLength(); ++i)
        {
            Node recordNode = recordsList.item(i);
            System.out.println(recordNode.getNodeName().length());
            System.out.println(recordNode.getTextContent().length());

            //we append a record (cloned) to the new file
            Node clonedNode = recordNode.cloneNode(true);
            recordsXml.adoptNode(clonedNode); //We adopt the orphan :)
            root.appendChild(clonedNode);
        }
        //create footer;
        Element footer = recordsXml.createElement("footer");
        root.appendChild(footer);
        Element recordCount = recordsXml.createElement("record_count");
        footer.appendChild(recordCount);

        //At the end, we save the file XML on disk
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(recordsXml);

        StreamResult result =  new StreamResult(new File("src/main/resources/file"  + ".xml"));
        transformer.transform(source, result);*/

       /* XmlGenerator xmlFile = new XmlGenerator();
        xmlFile.generate();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader reader = sp.getXMLReader();
        reader.setContentHandler(new DataSaxHandler()); // need to implement this file
        reader.parse(new InputSource(new FileInputStream(new File(PATH, "recordFile.xml"))));*/
/*
        JAXBContext jc = JAXBContext.newInstance(RecordTable.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        RecordTable recordTable = (RecordTable) unmarshaller.unmarshal(new File("src/main/resources/recordFile.xml"));

        final int tagSize = 20;
        final int byteSize = 424/2 - tagSize;
        int currentSize = 0;

        while (currentSize <= byteSize) {
            for (Record record : recordTable.getRecord()) {
                currentSize = currentSize + record.getRecordId().toString().length();
                System.out.println(record.getRecordId());
                for (Row row : record.getRecordRow()) {
                    for (String rows : row.getString()) {
                        currentSize = currentSize + rows.length();
                        System.out.println(rows);
                    }
                }
            }
            System.out.println(currentSize);
        }*/
        /*Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(recordTable, System.out);*/

   /*     XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader reader = xif.createXMLStreamReader(new FileReader("src/main/resources/recordFile.xml"));

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("src/main/resources/recordFile.xml"));
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression exp = xPath.compile("//record",(int)(l2),(int)(l2>>32));
        NodeList nodeList = (NodeList)exp.evaluate(doc, XPathConstants.NODESET);
        System.out.println("Found " + nodeList.getLength() + " results");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            StringWriter buf = new StringWriter();
            Transformer xform = TransformerFactory.newInstance().newTransformer();
            xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            xform.transform(new DOMSource(node), new StreamResult(buf));
            System.out.println(buf.toString());
        }
*/
