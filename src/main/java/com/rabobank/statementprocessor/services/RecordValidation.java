package com.rabobank.statementprocessor.services;

import com.rabobank.statementprocessor.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger LOG = LoggerFactory.getLogger(RecordValidation.class);

    /**
     * The Reference number.
     */
    @Autowired
    public Map<String,String> referenceNumber;

    private synchronized boolean validateReferenceNumber(Record record){
        LOG.info("Validating reference number: "+record.getReference());
        if(referenceNumber.get(record.getReference())!=null){
            return false;
        }
        referenceNumber.put(record.getReference(),record.getDescription());
        return true;
    }
    private synchronized boolean validateEndBalance(Record record){
        Optional<BigDecimal> startBalance=Optional.ofNullable(record.getStartBalance());
        Optional<BigDecimal> mutationBalance=Optional.ofNullable(record.getMutation());
        Optional<BigDecimal> endbalance=Optional.ofNullable(record.getEndBalance());

        if (startBalance.isPresent() && mutationBalance.isPresent() && endbalance.isPresent()) {
            LOG.info("Validating Balance: startbalance ["+startBalance.get()+"], Mutation ["+mutationBalance.get()+"], Endbalance ["+endbalance.get()+"]");
            BigDecimal sum = startBalance.get().add(mutationBalance.get());
            sum = sum.setScale(2, RoundingMode.FLOOR);
            if (sum.compareTo(endbalance.get()) != 0) {
                return false;
            }
            return true;
        }else {
            throw new NumberFormatException("Failed! Please check the startBalance, Mutation and Endbalance of the records in XML");
        }
    }

    /**
     * Validate file name, return true if filename is proper, else false
     *
     * @param fileLocation the file location
     * @return the boolean
     */
    public synchronized boolean validateFileName(String fileLocation){
        LOG.info("Validating file location: "+fileLocation);
        File file=new File(fileLocation);
        if(file.exists() && !file.isDirectory()){
            return true;
        }
        return false;
    }

    /**
     * Validate record checks the reference number (if duplicate or not)
     * and checks End balance (if matches corresponding to Startbalance and Mutation)
     *
     * @param record the record
     * @return the boolean
     */
    public synchronized boolean validateRecord(Record record){
        return (validateReferenceNumber(record) && validateEndBalance(record));
    }
}
