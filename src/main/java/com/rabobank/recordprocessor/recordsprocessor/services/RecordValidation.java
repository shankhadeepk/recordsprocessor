package com.rabobank.recordprocessor.recordsprocessor.services;

import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class RecordValidation {

    @Autowired
    public Map<String,String> referenceNumber;

    public boolean validateReferenceNumber(Record record){
        if(referenceNumber.get(record.getReference())!=null){
            return false;
        }
        referenceNumber.put(record.getReference(),record.getDescription());
        return true;
    }
    public boolean validateEndBalance(Record record){
        BigDecimal sum= record.getStartBalance().add(record.getMutation());
        sum=sum.setScale(2, RoundingMode.FLOOR);
        if(sum.compareTo(record.getEndBalance())!=0){
            return false;
        }
        return true;
    }
    public boolean validateFileName(String fileLocation){

        File file=new File(fileLocation);
        if(file.exists() && !file.isDirectory()){
            return true;
        }
        return false;
    }
    public boolean validateRecord(Record record){
        return (validateReferenceNumber(record) && validateEndBalance(record));
    }
}
