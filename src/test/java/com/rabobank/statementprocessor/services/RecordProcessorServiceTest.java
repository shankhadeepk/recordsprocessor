package com.rabobank.statementprocessor.services;

import com.rabobank.statementprocessor.services.RecordProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordProcessorServiceTest {

    @Resource
    public RecordProcessorService csvreader;

    @Resource
    public RecordProcessorService xmlreader;

    @Test
    public void checkCSVReader() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/records.csv");
    }

    @Test(expected = NumberFormatException.class)
    public void checkCSVReaderIfRecordHasNoStartBalance() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsNoStartBalance.csv");
    }

    @Test(expected = NumberFormatException.class)
    public void checkCSVReaderIfRecordHasNoEndBalance() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsNoEndBalance.csv");
    }

    @Test(expected = NumberFormatException.class)
    public void checkCSVReaderIfRecordHasNoMutation() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsNoMutation.csv");
    }

    @Test(expected = NumberFormatException.class)
    public void checkCSVReaderIfRecordHasWrongStartBalance() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsWrongStartBalance.csv");
    }

    @Test
    public void checkXMLReader() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
         xmlreader.read("src/test/resources/records.xml");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void checkXMLReaderIfRecordHasNoStartBalance() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsNoStartBalance.xml");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void checkXMLReaderIfRecordHasNoEndBalance() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsNoEndBalance.xml");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void checkXMLReaderIfRecordHasNoMutation() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsNoMutation.xml");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void checkXMLReaderIfRecordHasWrongStartBalance() throws ClassNotFoundException, JAXBException, XMLStreamException, IOException {
        csvreader.read("src/test/resources/recordsWrongStartBalance.xml");
    }
}