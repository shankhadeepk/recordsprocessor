package com.rabobank.recordprocessor.recordsprocessor.controller;

import com.rabobank.recordprocessor.recordsprocessor.model.ReportRequest;
import com.rabobank.recordprocessor.recordsprocessor.services.RecordProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordsController {

    @Resource
    public RecordProcessorService csvreader;

    @Resource
    public RecordProcessorService xmlreader;

    @Autowired
    public Map<String,String> referenceNumber;

    @PostMapping(value="/reports",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkStatements(@RequestBody ReportRequest request){

        referenceNumber.clear();

        return new ResponseEntity<>("Success! "+request.getOutputReportLocation()+" is generated", HttpStatus.CREATED);
    }
}
