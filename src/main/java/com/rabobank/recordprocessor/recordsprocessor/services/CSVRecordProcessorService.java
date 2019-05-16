package com.rabobank.recordprocessor.recordsprocessor.services;

import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

@Service("csvreader")
public class CSVRecordProcessorService implements RecordProcessorService {
    @Override
    public void read(String inputFile) throws IOException {
        System.out.println("CSV Reader");
        Record record=null;
        Reader in=new FileReader(inputFile);
        Iterable<CSVRecord> csvRecords= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord csvRecord:csvRecords) {
            record = new Record();
            record.setReference(csvRecord.get(0));
            record.setAccountNumber(csvRecord.get(1));
            record.setDescription(csvRecord.get(2));
            record.setStartBalance(new BigDecimal(csvRecord.get(3)));
            record.setMutation(new BigDecimal(csvRecord.get(4)));
            record.setEndBalance(new BigDecimal(csvRecord.get(5)));

            System.out.println(record);
        }
        record=null;
    }
}
