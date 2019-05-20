package com.rabobank.statementprocessor.model;
/*
*
* The JSON Request for the POST REST API
* @Author Shankhadeep Karmakar
* @Version 1.0
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
