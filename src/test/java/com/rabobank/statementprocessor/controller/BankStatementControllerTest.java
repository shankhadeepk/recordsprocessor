package com.rabobank.statementprocessor.controller;

import com.rabobank.statementprocessor.exception.InvalidFilePathException;
import com.rabobank.statementprocessor.model.ReportRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankStatementControllerTest {

    @Autowired
    private BankStatementController bankStatementController;

    @Test
    public void checkStatements() {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setCsvFileLocation("src/test/resources/records.csv");
        reportRequest.setXmlFileLocation("src/test/resources/records.xml");
        ResponseEntity<String> response=null;
        try {
            response=bankStatementController.checkStatements(reportRequest);
            assertNotNull(response);
            assertEquals(response.getStatusCode(),HttpStatus.CREATED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkStatementsIfCSVNotAvailable() {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setXmlFileLocation("src/test/resources/records.xml");
        ResponseEntity<String> response=null;
        try {
            response=bankStatementController.checkStatements(reportRequest);
            assertNotNull(response);
            assertEquals(response.getStatusCode(),HttpStatus.CREATED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void checkStatementsIfXMLNotAvailable() {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setXmlFileLocation("src/test/resources/records.xml");
        ResponseEntity<String> response=null;
        try {
            response=bankStatementController.checkStatements(reportRequest);
            assertNotNull(response);
            assertEquals(response.getStatusCode(),HttpStatus.CREATED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void checkStatementsIfNoneAvailable() {
        ReportRequest reportRequest=new ReportRequest();
        ResponseEntity<String> response=null;
        try {
            response=bankStatementController.checkStatements(reportRequest);
            assertNotNull(response);
            assertEquals(response.getStatusCode(),HttpStatus.CREATED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidFilePathException.class)
    public void checkStatementsIfCSVNotProper() {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setCsvFileLocation("src/test/resources/records");
        ResponseEntity<String> response=null;
        try {
            response=bankStatementController.checkStatements(reportRequest);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidFilePathException.class)
    public void checkStatementsIfXMLNotProper() throws ClassNotFoundException, IOException, XMLStreamException, JAXBException {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setXmlFileLocation("src/test/resources/records");
        ResponseEntity<String> response=null;

        response=bankStatementController.checkStatements(reportRequest);

    }

    @Test(expected = XMLStreamException.class)
    public void checkStatementsIfWrongXmlFormat() throws ClassNotFoundException, IOException, XMLStreamException, JAXBException {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setXmlFileLocation("src/test/resources/recordsWrongFormat.xml");
        ResponseEntity<String> response=null;

        response=bankStatementController.checkStatements(reportRequest);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void checkStatementsIfWrongCSVFormat() throws ClassNotFoundException, IOException, XMLStreamException, JAXBException {
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setCsvFileLocation("src/test/resources/recordsWrongFormat.csv");
        ResponseEntity<String> response=null;

        response=bankStatementController.checkStatements(reportRequest);
    }
}