package com.vijai;

import org.xml.sax.SAXException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.ximpleware.*;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException, TransformerException, XMLStreamException, XPathExpressionException, ParserConfigurationException, SAXException, XPathParseException, NavException, XPathEvalException {

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
