package com.rabobank.statementprocessor.services;

import com.rabobank.statementprocessor.exception.InvalidFilePathException;
import com.rabobank.statementprocessor.model.Record;
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
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*
* Service takes care of XML Record Processor
* @Author Shankhadeep Karmakar
* @Version 1.0
*
*
* */
@Service("csvreader")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CSVRecordProcessorService implements RecordProcessorService {

    private final Logger LOG = LoggerFactory.getLogger(CSVRecordProcessorService.class);

    /**
     * The List of records.
     */
    @Resource
    public List<Record> listOfRecords;

    /**
     * The Record validation.
     */
    @Autowired
    public RecordValidation recordValidation;


    /**
     * read reads each record from the CSV file and validates it with business scenarios
     *
     *
     * @param inputFile accepts the input file from request
     * @return the response entity
     * @throws IOException              the io exception
     */
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

                    LOG.info("Record read from CSV file :"+record);
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
