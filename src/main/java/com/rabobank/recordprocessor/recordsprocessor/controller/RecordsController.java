package com.rabobank.recordprocessor.recordsprocessor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import com.rabobank.recordprocessor.recordsprocessor.model.ReportRequest;
import com.rabobank.recordprocessor.recordsprocessor.services.RecordProcessorService;
import com.rabobank.recordprocessor.recordsprocessor.services.RecordValidation;
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
    public ResponseEntity<String> checkStatements(@RequestBody ReportRequest request){

        Gson gsonBuilder = new GsonBuilder().create();
        referenceNumber.clear();
        listOfRecords.clear();

        try {

            if (request.getCsvFileLocation() != null) {
                if(!recordValidation.validateFileName(request.getCsvFileLocation()))
                    return new ResponseEntity<>(gsonBuilder.toJson("Failed! Please check the CSV file location"), HttpStatus.BAD_REQUEST);
                csvreader.read(request.getCsvFileLocation());
            }
            if (request.getXmlFileLocation() != null) {
                if(!recordValidation.validateFileName(request.getXmlFileLocation()))
                    return new ResponseEntity<>(gsonBuilder.toJson("Failed! Please check the XML file location"), HttpStatus.BAD_REQUEST);
                xmlreader.read(request.getXmlFileLocation());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(gsonBuilder.toJson("Failed! Please check if file exists or not"), HttpStatus.BAD_REQUEST);
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ResponseEntity<>(gsonBuilder.toJson("Failed! Please check data format of the element of the file"), HttpStatus.BAD_REQUEST);
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return new ResponseEntity<>(gsonBuilder.toJson("Failed! Please check format of file"), HttpStatus.BAD_REQUEST);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(gsonBuilder.toJson("Failed!"), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(gsonBuilder.toJson("Failed!"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gsonBuilder.toJson(listOfRecords), HttpStatus.CREATED);
    }
}
