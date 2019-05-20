package com.rabobank.statementprocessor.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabobank.statementprocessor.model.AppError;
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

/**
 * The type Record processor error advice.
 */
@ControllerAdvice
public class RecordProcessorErrorAdvice {

    private final Logger LOG = LoggerFactory.getLogger(RecordProcessorErrorAdvice.class);


    /**
     * User defined exception handled, exception occurs in case file paths are not proper.
     *
     * @param ex     the ex
     * @param status the status
     * @return the response entity
     */
    @ExceptionHandler({ InvalidFilePathException.class})
    public ResponseEntity<String> handleInvalidFilePathException(InvalidFilePathException ex, HttpStatus status){
        LOG.error("File path is not proper ",ex);
        AppError error=new AppError(status,ex.getMessage());
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), status);
    }


    /**
     * Exception handled when the xml data does not match the object
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({ ClassNotFoundException.class})
    public ResponseEntity<String> handleClassNotFound(Exception ex){
        LOG.error("Class path is not correct ",ex);
        AppError error=new AppError(HttpStatus.NOT_FOUND,"Failed! Mapping object does not exists, please check the xml/csv data");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handled when the format of the XML is not matching Object
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({ JAXBException.class})
    public ResponseEntity<String> handleJAXBException(Exception ex){
        LOG.error("Data to Object Mapping is not proper ",ex);
        AppError error=new AppError(HttpStatus.BAD_REQUEST,"Failed! Please check data format of the element of the file");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle xml exception response entity when Format of XML is not proper.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({ XMLStreamException.class})
    public ResponseEntity<String> handleXMLException(Exception ex){
        LOG.error("XML Format needs to be checked ",ex);
        AppError error=new AppError(HttpStatus.BAD_REQUEST,"Failed! Please check format of file");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle io exception response entity when file is not present.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({ IOException.class})
    public ResponseEntity<String> handleIOException(Exception ex){
        LOG.error("File not found",ex);
        AppError error=new AppError(HttpStatus.NOT_FOUND,"Failed! Please check if file exists or not");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle number format exception response entity when decimal data is not proper in CSV or XML.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({ NumberFormatException.class})
    public ResponseEntity<String> handleNumberFormatException(Exception ex){
        LOG.error("Format of the StartBalance or EndBalance or Mutation is not correct:",ex);
        AppError error=new AppError(HttpStatus.NOT_FOUND,"Failed! Please check the startBalance, Mutation and Endbalance of the records in CSV/XML file");
        Gson gsonBuilder = new GsonBuilder().create();
        return new ResponseEntity<>(gsonBuilder.toJson(error), HttpStatus.NOT_FOUND);
    }
}
