package com.airfrance.squaleweb.applicationlayer.action.export.xml;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Les données XML
 */
public abstract class XMLData {

    /** la requête */
    protected HttpServletRequest request = null;

    /** Le document XML */
    protected Document document = null;

    /**
     * Contructeur
     * @param pRequest pRequest
     * @throws ParserConfigurationException si erreur 
     */
    public XMLData(final HttpServletRequest pRequest) throws ParserConfigurationException {
        this.request = pRequest;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.newDocument();
    }

    /** 
     * Accesseur 
     * @return la requête
     * */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Definiton du templissage du document
     */
    public abstract void fill();

    /**
     * @return le document contenant les informations XML
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @return le document XML sous forme de String
     * @throws TransformerException si erreur
     */
    public String print() throws TransformerException {
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        transformer.transform(domSource, result);
        return writer.toString();
    }
}