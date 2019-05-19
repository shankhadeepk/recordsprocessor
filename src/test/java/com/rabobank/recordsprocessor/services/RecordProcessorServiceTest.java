package com.rabobank.recordsprocessor.services;

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
    public void checkCSVReader(){
        try {
            csvreader.read("src/test/resources/records.csv");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkXMLReader(){
        try {
            xmlreader.read("src/test/resources/records.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //xmlreader.read();
    }
}