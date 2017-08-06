package com.vijai.bussiness.splitting;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Splitter {

    public void split(int USER_GIVEN_SIZE) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse("src/main/resources/recordFile.xml");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");


        NodeList recordsList = doc.getElementsByTagName("record");
        int filePartNumber = 0;
        String newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
        File file = new File(newFilePath);

        Document recordsXml = newDocument();

        for (int i = 0; i < recordsList.getLength(); i++) {

            if (file.length() <= USER_GIVEN_SIZE) {
                Node recordNode = recordsList.item(i);
                Node clonedNode = recordsXml.importNode(recordNode, true);

                Element root = recordsXml.getDocumentElement();

                root.appendChild(clonedNode);

                Node footer = recordsXml.getElementsByTagName("footer").item(0);
                root.appendChild(footer);

                Node recordCount = recordsXml.getElementsByTagName("record_count").item(0);
                footer.appendChild(recordCount);

                DOMSource source = new DOMSource(recordsXml);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);

            } else {
                filePartNumber++;
                i--;
                newFilePath = "src/main/resources/part" + filePartNumber + ".xml";
                file = new File(newFilePath);
                recordsXml = newDocument();
            }
        }
    }

     Document newDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document result = dBuilder.newDocument();
        Element root = result.createElement("record-table");
        result.appendChild(root);

        Element footer = result.createElement("footer");
        root.appendChild(footer);
        Element recordCount = result.createElement("record_count");
        footer.appendChild(recordCount);
        return result;
    }
}
