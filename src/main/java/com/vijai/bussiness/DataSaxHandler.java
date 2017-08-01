package com.vijai.bussiness;

import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class DataSaxHandler extends DefaultHandler {

    // Change this to the directory where the files will be stored
    public static final String DIRECTORY = "target/results";

    // Change this to the approximate size of the resulting files (in characters(
    public static final long MAX_SIZE = 1000000;


    public static final long TAG_CHAR_SIZE = 5; //"<></>"

    // counts number of files created
    private int fileCount = 0;

    // counts characters to decide where to split file
    private long charCount = 0;
    // data line buffer (is reset when the file is split)
    private StringBuilder recordRowDataLines = new StringBuilder();

    // temporary variables used for the parser events
    private String currentElement = null;
    private String currentRecordId = null;
    private String currentRecordRowData = null;

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        currentElement = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("record_rows")) { // no more records - save last file here!
            try {
                saveFragment();
            } catch (IOException ex) {
                throw new SAXException(ex);
            }
        }
        if (qName.equals("record_row")) { // one record finished - save in buffer & calculate size so far
            charCount += tagSize("record_row");
            recordRowDataLines.append("<record_row>")
                    .append(currentRecordRowData)
                    .append("</record_row>");
            if (charCount >= MAX_SIZE) { // if max size was reached, save what was read so far in a new file
                try {
                    saveFragment();
                } catch (IOException ex) {
                    throw new SAXException(ex);
                }
            }
        }
        currentElement = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println(new String(ch, start, length));
        if (currentElement == null) {
            return;
        }
        if (currentElement.equals("record_id")) {
            currentRecordId = new String(ch, start, length);
        }
        if (currentElement.equals("record_row")) {
            currentRecordRowData = new String(ch, start, length);
            charCount += currentRecordRowData.length(); // storing size so far
        }
    }

    public long tagSize(String tagName) {
        return TAG_CHAR_SIZE + tagName.length() * 2; // size of text + tags
    }

    /**
     * Saves a new file containing approximately MAX_SIZE in chars
     */
    public void saveFragment() throws IOException {
        ++fileCount;
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("<record part='")
                .append(fileCount)
                .append("'><record_id>")
                .append(currentRecordId)
                .append("</record_id>")
                .append("<record_rows>")
                .append(recordRowDataLines)
                .append("</record_rows></record>");
        File fragment = new File(DIRECTORY, "data_part_" + fileCount + ".xml");
        FileWriter out = new FileWriter(fragment);
        out.write(fileContent.toString());
        out.flush();
        out.close();

        // reset fragment data - record buffer and char count
        recordRowDataLines = new StringBuilder();
        charCount = 0;
    }
}
