package com.rabobank.recordsprocessor.services;

import com.rabobank.recordsprocessor.model.Record;
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
import java.util.Optional;

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
        Optional<CSVRecord> csvRecord1=null;

        try{
            LOG.info("CSV Record to be read");
            for (CSVRecord csvRecord:csvRecords) {
                csvRecord1=Optional.ofNullable(csvRecord);
                if(csvRecord1.isPresent()) {
                    record = new Record();
                    record.setReference(csvRecord1.get().get(0));
                    record.setAccountNumber(csvRecord1.get().get(1));
                    record.setDescription(csvRecord1.get().get(2));
                    record.setStartBalance(new BigDecimal(csvRecord1.get().get(3)));
                    record.setMutation(new BigDecimal(csvRecord1.get().get(4)));
                    record.setEndBalance(new BigDecimal(csvRecord1.get().get(5)));
                    if (!recordValidation.validateRecord(record))
                        listOfRecords.add(record);
                }
            }
            record=null;
        }
        finally {
            in.close();
        }
    }
}
