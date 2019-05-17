package com.rabobank.recordprocessor.recordsprocessor.services;

import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordValidationTest {

    @Autowired
    public RecordValidation recordValidation;

    @Test
    public void validateReferenceNumber() {
        Record record=new Record();
        record.setReference("122240");
        record.setAccountNumber("NL43AEGO0773393871");
        record.setDescription("Subscription for Erik King");
        record.setStartBalance(new BigDecimal(78.95));
        record.setMutation(new BigDecimal(-25.38));
        record.setEndBalance(new BigDecimal(53.57));
        assertTrue(recordValidation.validateReferenceNumber(record));
    }

    @Test
    public void validateEndBalance() {
        Record record=new Record();
        record.setReference("122240");
        record.setAccountNumber("NL43AEGO0773393871");
        record.setDescription("Subscription for Erik King");
        record.setStartBalance(new BigDecimal(78.95));
        record.setMutation(new BigDecimal(-25.38));
        record.setEndBalance(new BigDecimal(53.57));
        assertTrue(recordValidation.validateEndBalance(record));
    }

    @Test
    public void validateFileName() {
    }

    @Test
    public void validateRecord() {
    }
}