package com.rabobank.recordprocessor.recordsprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RecordsProcessorConfig {

    @Bean
    public Map<String,String> referenceNumber(){
        return new HashMap<>();
    }
}
