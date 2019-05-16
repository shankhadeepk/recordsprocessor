package com.rabobank.recordprocessor.recordsprocessor.services;

import com.rabobank.recordprocessor.recordsprocessor.model.Record;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

@Service("xmlreader")
public class XMLRecordProcessorService implements RecordProcessorService {


    @Override
    public void read(String inputFile) throws IOException, JAXBException, XMLStreamException, ClassNotFoundException {

        JAXBContext jaxbContext=JAXBContext.newInstance(Record.class);
        Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
        XMLInputFactory xmlInputFactory=XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        XMLStreamReader xmlStreamReader=xmlInputFactory.createXMLStreamReader(new FileInputStream(inputFile));
        String className="com.rabobank.recordprocessor.recordsprocessor.model.Record";
        String elementName="record";
        JAXBElement jaxbElement=null;
        Record record=null;

        while(true){

            while(xmlStreamReader.hasNext()){
                if(xmlStreamReader.isStartElement() && elementName.equalsIgnoreCase(xmlStreamReader.getLocalName())){
                    break;
                }
                xmlStreamReader.next();
            }
            if(xmlStreamReader!=null
               && !(xmlStreamReader.getEventType() == XMLStreamReader.END_DOCUMENT)
               && !(xmlStreamReader.getEventType() == XMLStreamReader.END_ELEMENT)){
                jaxbElement=unmarshaller.unmarshal(xmlStreamReader,Class.forName(className));
            }
            if(jaxbElement==null) break;

            record=(Record) jaxbElement.getValue();
            System.out.println(record);
            jaxbElement=null;
        }

    }
}
