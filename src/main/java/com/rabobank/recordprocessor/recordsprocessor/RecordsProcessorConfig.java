package com.rabobank.recordprocessor.recordsprocessor;

import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class RecordsProcessorConfig {

    /**
     * Holds the reference Number of the all Records
     *
     * */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Map<String,String> referenceNumber(){
        return new ConcurrentHashMap<>();
    }

    /**
     * Holds the Record which will be sent in Response
     *
     * */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public List<Record> listOfRecords(){
        return new CopyOnWriteArrayList<>();
    }
}
