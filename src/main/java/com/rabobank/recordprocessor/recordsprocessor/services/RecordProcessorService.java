package com.rabobank.recordprocessor.recordsprocessor.services;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface RecordProcessorService {

    void read(String inputFile) throws IOException, JAXBException, XMLStreamException, ClassNotFoundException;
}
