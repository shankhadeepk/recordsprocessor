package com.rabobank.recordsprocessor.services;

import com.rabobank.recordsprocessor.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

/*
 * This class implements Validation of Records
 * @Author Shankhadeep Karmakar
 * @Version 1.0
 *
 *
 * */

@Service
public class RecordValidation {

    @Autowired
    public Map<String,String> referenceNumber;

    private synchronized boolean validateReferenceNumber(Record record){
        if(referenceNumber.get(record.getReference())!=null){
            return false;
        }
        referenceNumber.put(record.getReference(),record.getDescription());
        return true;
    }
    private synchronized boolean validateEndBalance(Record record){
        Optional<BigDecimal> startBalance=Optional.ofNullable(record.getStartBalance());
        Optional<BigDecimal> mutationBalance=Optional.ofNullable(record.getMutation());

        if (startBalance.isPresent() && mutationBalance.isPresent()) {
            BigDecimal sum = startBalance.get().add(mutationBalance.get());
            sum = sum.setScale(2, RoundingMode.FLOOR);
            if (sum.compareTo(record.getEndBalance()) != 0) {
                return false;
            }
            return true;
        }else return false;
    }
    public synchronized boolean validateFileName(String fileLocation){

        File file=new File(fileLocation);
        if(file.exists() && !file.isDirectory()){
            return true;
        }
        return false;
    }
    public synchronized boolean validateRecord(Record record){
        return (validateReferenceNumber(record) && validateEndBalance(record));
    }
}
