package com.rabobank.recordsprocessor.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabobank.recordsprocessor.model.AppError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@ControllerAdvice
public class RecordProcessorErrorAdvice {

    private final Logger LOG = LoggerFactory.getLogger(RecordProcessorErrorAdvice.class);

    @ExceptionHandler({ InvalidFilePathException.class})
    public ResponseEntity<String> handleInvalidFilePathException(InvalidFilePathException ex, HttpStatus status){
        LOG.error("File path is not proper ",ex);
        AppError error=new AppError(status,ex.getMessage());
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), status);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ ClassNotFoundException.class})
    public ResponseEntity<String> handleClassNotFound(Exception ex){
        LOG.error("Class path is not correct ",ex);
        AppError error=new AppError(HttpStatus.NOT_FOUND,"Failed! Mapping object does not exists");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ JAXBException.class})
    public ResponseEntity<String> handleJAXBException(Exception ex){
        LOG.error("Data to Object Mapping is not proper ",ex);
        AppError error=new AppError(HttpStatus.BAD_REQUEST,"Failed! Please check data format of the element of the file");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ XMLStreamException.class})
    public ResponseEntity<String> handleXMLException(Exception ex){
        LOG.error("XML Format needs to be checked ",ex);
        AppError error=new AppError(HttpStatus.BAD_REQUEST,"Failed! Please check format of file");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ IOException.class})
    public ResponseEntity<String> handleIOException(Exception ex){
        LOG.error("File not found ",ex);
        AppError error=new AppError(HttpStatus.NOT_FOUND,"Failed! Please check if file exists or not");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.NOT_FOUND);
    }
}
