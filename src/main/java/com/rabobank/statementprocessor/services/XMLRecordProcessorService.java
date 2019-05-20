package com.rabobank.statementprocessor.services;

import com.rabobank.statementprocessor.model.Record;
import com.rabobank.statementprocessor.util.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/*
 * Service takes care of XML Record Processor
 * @Author Shankhadeep Karmakar
 * @Version 1.0
 *
 *
 * */
@Service("xmlreader")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class XMLRecordProcessorService implements RecordProcessorService {

    private final Logger LOG = LoggerFactory.getLogger(XMLRecordProcessorService.class);

    private  final JAXBContext jaxbContext = JAXBContext.newInstance(Record.class);
    private  final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    @Resource
    public List<Record> listOfRecords;

    @Autowired
    public RecordValidation recordValidation;

    public XMLRecordProcessorService() throws JAXBException {
    }

    /**
     * read reads each record from the XML file and validates it with business scenarios
     *
     *
     * @param inputFile accepts the input file from request
     * @return the response entity
     * @throws IOException              the io exception
     */
    @Override
    public void read(String inputFile) throws IOException, JAXBException, XMLStreamException, ClassNotFoundException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(inputFile));

        try {
            JAXBElement jaxbElement = null;
            Record record = null;

            LOG.info("XML Record to be read");
            while (true) {

                while (xmlStreamReader.hasNext()) {
                    if (xmlStreamReader.isStartElement()
                            && ApplicationConstants.ELEMENT_NAME.getValue().equalsIgnoreCase(xmlStreamReader.getLocalName())) {
                        break;
                    }
                    xmlStreamReader.next();
                }
                if (xmlStreamReader != null
                        && !(XMLStreamReader.END_DOCUMENT == xmlStreamReader.getEventType())
                        && !(XMLStreamReader.END_ELEMENT == xmlStreamReader.getEventType())) {
                    jaxbElement = unmarshaller.unmarshal(xmlStreamReader, Class.forName(ApplicationConstants.CLASS.getValue()));
                }
                if (jaxbElement == null) break;

                record = (Record) jaxbElement.getValue();

                LOG.info("Record read from XML file :"+record);
                if(!recordValidation.validateRecord(record))
                    listOfRecords.add(record);
                jaxbElement = null;
            }

        }
        finally{
            xmlStreamReader.close();
        }
    }

}
