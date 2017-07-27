package com.vijai;

import com.vijai.classes.Footer;
import com.vijai.classes.Record;
import com.vijai.classes.RecordTable;
import com.vijai.classes.Row;
import com.vijai.utils.RandomString;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws JAXBException {

        RecordTable recordTable = new RecordTable();

        Scanner sc = new Scanner(System.in);
        System.out.print("How many record elements to generate? ");
        while (!sc.hasNextLong()) sc.next(); //allow input long type numbers only
        int recordsCount = sc.nextInt();

        Footer footer = new Footer();
        footer.setRecordCount(recordsCount);
        int recordRowCount = 0;

        List<Record> recordList = new ArrayList<>();
        // record element
        for (int i=0; i<recordsCount; i++) {
            List<String> stringList = new ArrayList<>(); // every record - new string list
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
        jaxbMarshaller.marshal( recordTable, new File( "src/output/recordFile.xml" ) );
        jaxbMarshaller.marshal( recordTable, System.out );
    }
}
