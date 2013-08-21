package com.integer.quiz.app;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.String;

public interface XMLHelper {
    String NAME = "quiz_XMLHelperService";

    public String convertXMLToString(Document document) throws TransformerException;

    public Document convertStringToXML(String xmlSource) throws ParserConfigurationException, IOException, SAXException;
}