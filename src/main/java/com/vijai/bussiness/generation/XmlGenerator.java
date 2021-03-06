package com.vijai.bussiness.generation;

import com.vijai.utils.RandomString;
import com.vijai.utils.XmlValidation;
import com.vijai.xmlClasses.Footer;
import com.vijai.xmlClasses.Record;
import com.vijai.xmlClasses.RecordTable;
import com.vijai.xmlClasses.Row;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class XmlGenerator {
    public void generate() throws JAXBException, IOException, SAXException {
        RecordTable recordTable = new RecordTable();

        Scanner sc = new Scanner(System.in);
        System.out.print("How many record elements to generate? ");
        while (!sc.hasNextLong()) sc.next(); //allow input long type numbers only
        long recordsCount = sc.nextLong();

        Footer footer = new Footer();
        footer.setRecordCount(recordsCount);
        int recordRowCount = 0;

        List<Record> recordList = new ArrayList();
        // record element
        for (long i=0; i<recordsCount; i++) {
            List<String> stringList = new ArrayList(); // every record - new string list
            Record record = new Record();
            record.setRecordId((long) (i+1));
            // random number of random string rows
            Random random = new Random();
            int randomNumber = random.nextInt(30)+1;
            recordRowCount = recordRowCount + randomNumber;

            for (int r = 0; r < randomNumber; r++) { //how many rows to add
                RandomString string = new RandomString();
                String randomString = string.getRandomString();
                stringList.add(randomString);
                Row row = new Row();
                row.setString(stringList);
                record.setRecordRow(row);
            }
            recordList.add(record); //create record
        }
        recordTable.setRecord(recordList); // creates list of records
        footer.setRecordRowCount(recordRowCount);
        recordTable.setFooter(footer); // creates footer

        JAXBContext jaxbContext = JAXBContext.newInstance( RecordTable.class );
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        jaxbMarshaller.marshal( recordTable, new File( "src/main/resources/recordFile.xml" ) );
        jaxbMarshaller.marshal( recordTable, System.out );

        // validate with schema
        XmlValidation validation = new XmlValidation();
        Record record = new Record();
        Row row = new Row();
        validation.validate(recordTable);
        validation.validate(footer);
        validation.validate(record);
        validation.validate(row);
    }
}
