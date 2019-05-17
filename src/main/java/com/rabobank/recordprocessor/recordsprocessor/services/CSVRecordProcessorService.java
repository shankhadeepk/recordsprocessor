package com.rabobank.recordprocessor.recordsprocessor.services;

import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
/*
* This class implements CSV Record Processor Service
* @Author Shankhadeep Karmakar
* @Version 1.0
*
*
* */
@Service("csvreader")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CSVRecordProcessorService implements RecordProcessorService {

    private final Logger LOG = LoggerFactory.getLogger(CSVRecordProcessorService.class);

    @Resource
    public List<Record> listOfRecords;

    @Autowired
    public RecordValidation recordValidation;

    @Override
    public void read(String inputFile) throws IOException {
        Record record=null;
        Reader in=new FileReader(inputFile);
        Iterable<CSVRecord> csvRecords= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

        try{
            LOG.info("CSV Record to be read");
            for (CSVRecord csvRecord:csvRecords) {
                record = new Record();
                record.setReference(csvRecord.get(0));
                record.setAccountNumber(csvRecord.get(1));
                record.setDescription(csvRecord.get(2));
                record.setStartBalance(new BigDecimal(csvRecord.get(3)));
                record.setMutation(new BigDecimal(csvRecord.get(4)));
                record.setEndBalance(new BigDecimal(csvRecord.get(5)));
                if(!recordValidation.validateRecord(record))
                    listOfRecords.add(record);
            }
            record=null;
        }
        finally {
            in.close();
        }
    }
}
