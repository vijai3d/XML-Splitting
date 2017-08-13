package com.vijai.bussiness.splitting;

import com.vijai.xmlClasses.Footer;
import com.vijai.xmlClasses.Record;
import com.vijai.xmlClasses.RecordTable;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.bind.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SplitterJAXB {

    public void split() throws IOException, XMLStreamException, JAXBException {
        long filePartNumber = 0;
        boolean allowNextTag = true;
        long recordCounter = 0;
        long recordRowCounter = 0;
        String newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
        String filePath = "src/main/resources/recordFile.xml";
        InputStream xmlInputStream = new FileInputStream(filePath);
        XMLInputFactory xmlInputFactory =  XMLInputFactory.newInstance();
        XMLStreamReader streamReader =  xmlInputFactory.createXMLStreamReader(xmlInputStream);
        JAXBContext context = JAXBContext.newInstance(RecordTable.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File(newFilePath);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        List<Record> recordList = new ArrayList<Record>();

        while (streamReader.hasNext()) {
            if (allowNextTag) {
                streamReader.next();
            }
            if (streamReader.getEventType() == XMLEvent.START_ELEMENT && streamReader.getLocalName().equals("record")) {

                if (file.length() <= 1000) {
                    recordCounter++;
                    FileOutputStream fos = new FileOutputStream(file);
                    JAXBElement<Record> recordObj = unmarshaller.unmarshal(streamReader, Record.class);
                    Record record = recordObj.getValue();
                    recordList.add(record);
                    RecordTable recordTable = new RecordTable();
                    recordTable.setRecord(recordList);

                    Footer footer = new Footer();
                    footer.setRecordCount(recordCounter);
                    footer.setRecordRowCount();
                    recordTable.setFooter(footer);
                    marshaller.marshal(recordTable, fos);

                    allowNextTag = true;
                } else {
                    recordList.clear();
                    recordCounter = 0;
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
