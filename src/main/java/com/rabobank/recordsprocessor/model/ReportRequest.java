package com.rabobank.recordsprocessor.model;
/*
*
* The JSON Request for the POST REST API
*
*
* */
public class ReportRequest {

    private String csvFileLocation;
    private String xmlFileLocation;

    public String getCsvFileLocation() {
        return csvFileLocation;
    }

    public void setCsvFileLocation(String csvFileLocation) {
        this.csvFileLocation = csvFileLocation;
    }

    public String getXmlFileLocation() {
        return xmlFileLocation;
    }

    public void setXmlFileLocation(String xmlFileLocation) {
        this.xmlFileLocation = xmlFileLocation;
    }
}
