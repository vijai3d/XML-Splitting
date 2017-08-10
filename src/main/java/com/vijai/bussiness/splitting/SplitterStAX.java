package com.vijai.bussiness.splitting;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class SplitterStAX {


    public void split(int USER_GIVEN_SIZE) throws IOException, XMLStreamException, TransformerException, XPathParseException, NavException, XPathEvalException {

        long filePartNumber = 0;
        boolean allowNextTag = true;
        long recordCounter = 0;
        long recordRowCounter = 0;
        String newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        String filePath = "src/main/resources/recordFile.xml";
        InputStream xmlInputStream = new FileInputStream(filePath);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
        XMLStreamReader2 streamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
        File file = new File(newFilePath);
        FileOutputStream fos = new FileOutputStream(file);

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter streamWriter = xmlOutputFactory.createXMLStreamWriter(fos);

        while (streamReader.hasNext()) {

            if (allowNextTag) {
                streamReader.next();
            }
            if (streamReader.getEventType() == XMLEvent.START_ELEMENT && streamReader.getLocalName().equals("record")) {

                if (file.length() <= USER_GIVEN_SIZE) {
                    if (recordCounter == 0) {
                        streamWriter = xmlOutputFactory.createXMLStreamWriter((new FileOutputStream(file, true)));
                        streamWriter = new IndentingXMLStreamWriter(streamWriter);
                        streamWriter.writeStartDocument();
                        streamWriter.writeStartElement("record-table");
                        streamWriter.flush();
                    }
                    recordCounter++;

                    fos = new FileOutputStream(file, true);
                    transformer.transform(new StAXSource(streamReader), new StreamResult(fos));


                    allowNextTag = true;
                } else {
                    recordRowCounter = new WordCounter().countWord("<record_row>",file);
                    streamWriter.writeStartElement("footer");
                    streamWriter.writeStartElement("record_count");
                    streamWriter.writeCharacters(String.valueOf(recordCounter));
                    streamWriter.writeEndElement();
                    streamWriter.writeStartElement("record_row_count");
                    streamWriter.writeCharacters(String.valueOf(recordRowCounter));
                    streamWriter.writeEndElement();
                    streamWriter.writeEndElement();
                    streamWriter.writeEndElement();
                    streamWriter.writeEndDocument();
                    streamWriter.flush();
                    streamWriter.close();

                    recordCounter = 0;
                    System.out.println(recordRowCounter);

                    allowNextTag = false;
                    filePartNumber++;
                    newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
                    file = new File(newFilePath);

                }
            }
        }
        streamReader.close();
    }
}


