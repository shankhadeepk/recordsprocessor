package com.rabobank.recordsprocessor.services;

import com.rabobank.recordsprocessor.model.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordValidationTest {

    @Autowired
    public RecordValidation recordValidation;

    @Resource
    public Map<String,String> referenceNumber;


    @Test
    public void validateRecord() {
        Record record=new Record();
        record.setReference("122240");
        record.setAccountNumber("NL43AEGO0773393871");
        record.setDescription("Subscription for Erik King");
        record.setStartBalance(new BigDecimal(78.95));
        record.setMutation(new BigDecimal(-25.38));
        record.setEndBalance(new BigDecimal(53.57));
        assertTrue(recordValidation.validateRecord(record));
    }

    @Test
    public void validateRecordIfAlreadyExists() {
        Record record=new Record();
        record.setReference("122240");
        record.setAccountNumber("NL43AEGO0773393871");
        record.setDescription("Subscription for Erik King");
        record.setStartBalance(new BigDecimal(78.95));
        record.setMutation(new BigDecimal(-25.38));
        record.setEndBalance(new BigDecimal(53.57));
        referenceNumber.put(record.getReference(),record.getDescription());
        assertFalse(recordValidation.validateRecord(record));
        referenceNumber.clear();
    }
}