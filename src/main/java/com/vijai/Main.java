package com.vijai;

import com.vijai.xmlClasses.Record;
import com.vijai.xmlClasses.RecordTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import com.ximpleware.*;

public class Main {
        final int TAG_SYMBOLS = 5;
        final int userGivenSize = 1024/2;

    public static void main(String[] args) throws JAXBException, IOException, TransformerException, XMLStreamException, XPathExpressionException, ParserConfigurationException, SAXException, XPathParseException, NavException, XPathEvalException {




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


        VTDGen vg = new VTDGen();
        if (vg.parseFile("src/main/resources/recordFile.xml", false)){
            VTDNav vn = vg.getNav();
            AutoPilot ap = new AutoPilot(vn);
            ap.selectXPath("/record-table/record");
            byte[] header = "<record-table>".getBytes();
            byte[] tail = "</record-table>".getBytes();
            int i = -1,j=0;
            while((i=ap.evalXPath())!=-1){
                long l = vn.getElementFragment();
                FileOutputStream fops = new FileOutputStream("src/main/resources/"+j+".xml");
                fops.write(header);

                fops.write(vn.getXML().getBytes(), (int)l, ((int)(l>>32)));
                fops.write(tail);
                fops.close();
                j++;
            }

        }
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


    }

}
