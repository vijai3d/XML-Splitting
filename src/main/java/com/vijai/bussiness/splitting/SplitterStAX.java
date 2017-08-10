package com.vijai.bussiness.splitting;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.*;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import com.ximpleware.*;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.XMLStreamWriter2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SplitterStAX {

    public void split(int USER_GIVEN_SIZE) throws IOException, XMLStreamException, TransformerException, XPathParseException, NavException, XPathEvalException {
        /*int filePartNumber=0;
        VTDGen vg = new VTDGen();
        if (!vg.parseFile("src/main/resources/recordFile.xml", false)){
            System.out.println("error");
            return;
        }
        File file = new File("src/main/resources/part" + filePartNumber + ".xml");
        VTDNav vn = vg.getNav();
        AutoPilot ap = new AutoPilot(vn);
        ap.selectXPath("//record-table/record");
        int i=0;
        int recordCounter = 0;
        FileOutputStream fos =new FileOutputStream(file);
        while((i=ap.evalXPath())!=-1){

                if (file.length() <= USER_GIVEN_SIZE) {
                    recordCounter++;
                    fos.write("<record-table>".getBytes());
                    long l = vn.getElementFragment();
                    fos.write(vn.getXML().getBytes(), (int)l, (int)(l>>32));
                    fos.write("<footer>".getBytes());
                    fos.write(String.valueOf(recordCounter).getBytes());
                    fos.write("</footer>".getBytes());
                    fos.write("</record-table>".getBytes());
                } else {
                    fos.close();
                    filePartNumber++;
                    file = new File("src/main/resources/part" + filePartNumber + ".xml");
                    fos =new FileOutputStream(file);

                    long l = vn.getElementFragment();
                    fos.write(vn.getXML().getBytes(), (int)l, (int)(l>>32));
                }
        }*/

        long filePartNumber = 0;
        boolean allowNextTag = true;
        int recordCount =0;

        String newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
        //InputStream in = new FileInputStream(filename);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        //XMLEventReader reader = factory.createXMLEventReader(filename, in);
        //XMLEventWriter writer = xof.createXMLEventWriter(out);
        String filePath = "src/main/resources/recordFile.xml";
        InputStream xmlInputStream = new FileInputStream(filePath);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
        XMLStreamReader2 streamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
        File file = new File(newFilePath);
        FileOutputStream fos = new FileOutputStream(file);

        XMLOutputFactory xmlOutputFactory =  XMLOutputFactory.newFactory();
        XMLStreamWriter streamWriter = xmlOutputFactory.createXMLStreamWriter(fos);
        //streamWriter = new IndentingXMLStreamWriter(streamWriter);

        while (streamReader.hasNext()) {

            if (allowNextTag) {
                streamReader.next();
            }
                if ( streamReader.getEventType() == XMLEvent.START_ELEMENT && streamReader.getLocalName().equals("record")) {

                    if (file.length() <= USER_GIVEN_SIZE) {
                        if (recordCount==0) {
                            streamWriter = xmlOutputFactory.createXMLStreamWriter((new FileOutputStream(file,true)));
                            streamWriter = new IndentingXMLStreamWriter(streamWriter);
                            streamWriter.writeStartDocument();
                            streamWriter.writeStartElement("record-table");
                            streamWriter.flush();
                        }
                        recordCount++;

                        fos = new FileOutputStream(file, true);
                        transformer.transform(new StAXSource(streamReader), new StreamResult(fos));
                        //XmlReaderToWriter.write(streamReader, streamWriter);
                        //streamWriter.copyEventFromReader(streamReader,true);
                        allowNextTag = true;
                    } else {

                        streamWriter.writeStartElement("footer");
                        streamWriter.writeStartElement("record_count");
                        streamWriter.writeCharacters(String.valueOf(recordCount));
                        streamWriter.writeEndElement();
                        streamWriter.writeStartElement("record_row_count");
                        streamWriter.writeCharacters("???");
                        streamWriter.writeEndElement();
                        streamWriter.writeEndElement();
                        streamWriter.writeEndElement();
                        streamWriter.writeEndDocument();
                        streamWriter.flush();
                        streamWriter.close();

                        recordCount=0;
                        allowNextTag = false;
                        filePartNumber++;
                        newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
                        file = new File(newFilePath);


                    }
                }

        }
        streamReader.close();
        /*XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        FileReader fileReader = new FileReader("src/main/resources/recordFile.xml");
        XMLStreamReader2 streamReader = (XMLStreamReader2) inputFactory.createXMLStreamReader(fileReader);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        long filePartNumber = 0;

        String newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
        File file = new File(newFilePath);


        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        XMLOutputFactory2 xmlOutputFactory = (XMLOutputFactory2) XMLOutputFactory.newFactory();
        XMLStreamWriter2 streamWriter = (XMLStreamWriter2) xmlOutputFactory.createXMLStreamWriter((new FileOutputStream(file)));
        boolean allowNextTag = true;
        while (streamReader.hasNext()) {
            streamReader.next();

            if (streamReader.isStartElement()) {
                streamWriter.copyEventFromReader(streamReader, true);
                if (file.length() <= USER_GIVEN_SIZE) {
                    //XmlReaderToWriter.write(streamReader, streamWriter);


                }
            }*/

            /*if (allowNextTag) {
                streamReader.next();

            }
            if (streamReader.getEventType() == XMLEvent.START_ELEMENT && streamReader.getLocalName().equals("record")) {
                    if (file.length() <= USER_GIVEN_SIZE) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                        transformer.transform(new StAXSource(streamReader), new StreamResult(fileOutputStream));
                        allowNextTag = true;

                    } else {
                        allowNextTag = false;
                        filePartNumber++;
                        newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
                        file = new File(newFilePath);
                    }
            }*/
        }
        //streamReader.close();
        //fileReader.close();
    }


