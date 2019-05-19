package com.rabobank.recordsprocessor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabobank.recordsprocessor.exception.InvalidFilePathException;
import com.rabobank.recordsprocessor.model.Record;
import com.rabobank.recordsprocessor.model.ReportRequest;
import com.rabobank.recordsprocessor.services.RecordProcessorService;
import com.rabobank.recordsprocessor.services.RecordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
 * Rest Controller exposing RESTFul API to generate report of failed transactions
 *
 *  The RESTFul API
 *  POST : http://<url>:<Port>/record/reports
 *
 *  Request:
 * {
 *   "csvFileLocation":<Location of csv file in server>,
 *	 "xmlFileLocation":<Location of xml file in server>
 * }
 *
 * where
 *
 * csvFileLocation (optional)
 * xmlFileLocation (optional)
 *
 * */

@RestController
@RequestMapping("/record")
public class RecordsController {

    @Resource
    public RecordProcessorService csvreader;

    @Resource
    public RecordProcessorService xmlreader;

    @Autowired
    public RecordValidation recordValidation;

    @Resource
    public Map<String,String> referenceNumber;

    @Resource
    public List<Record> listOfRecords;

    @PostMapping(value="/reports",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkStatements(@RequestBody ReportRequest request) throws ClassNotFoundException, JAXBException, XMLStreamException, IOException,InvalidFilePathException{

        Gson gsonBuilder = new GsonBuilder().create();
        referenceNumber.clear();
        listOfRecords.clear();

        if (request.getCsvFileLocation() != null) {
            if(!recordValidation.validateFileName(request.getCsvFileLocation()))
                throw new InvalidFilePathException("Failed! Please check the CSV file location");
            csvreader.read(request.getCsvFileLocation());
        }
        if (request.getXmlFileLocation() != null) {
            if(!recordValidation.validateFileName(request.getXmlFileLocation()))
                throw new InvalidFilePathException("Failed! Please check the XML file location");
            xmlreader.read(request.getXmlFileLocation());
        }
        return new ResponseEntity<>(gsonBuilder.toJson(listOfRecords), HttpStatus.CREATED);
    }
}
