package com.vijai.bussiness.splitting;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.*;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

public class SplitterStAX {

    public void split(int USER_GIVEN_SIZE) throws TransformerException, FileNotFoundException, XMLStreamException {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(new FileReader("src/main/resources/recordFile.xml"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        long filePartNumber = 0;
        String newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
        File file = new File(newFilePath);


        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.getEventType() == XMLEvent.START_ELEMENT && streamReader.getLocalName().equals("record")) {
                while (streamReader.hasNext()) {
                    if (file.length() <= USER_GIVEN_SIZE) {

                        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                        transformer.transform(new StAXSource(streamReader), new StreamResult(fileOutputStream));
                        streamReader.next();
                    } else {
                        filePartNumber++;
                        newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
                        file = new File(newFilePath);
                    }
                }
            }

        }
        streamReader.close();
    }
}
