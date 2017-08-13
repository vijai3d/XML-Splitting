package com.vijai.bussiness.splitting;

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

    public void split() throws FileNotFoundException, XMLStreamException, JAXBException {
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
        List<Record> recordList = new ArrayList<Record>();
        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.getEventType() == XMLEvent.START_ELEMENT && streamReader.getLocalName().equals("record")) {
                JAXBElement<Record> unmarshalledObj = unmarshaller.unmarshal(streamReader, Record.class);
                Record record = unmarshalledObj.getValue();
                recordList.add(record);

                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(unmarshalledObj, System.out);
            }
        }
        streamReader.close();

    }
}
