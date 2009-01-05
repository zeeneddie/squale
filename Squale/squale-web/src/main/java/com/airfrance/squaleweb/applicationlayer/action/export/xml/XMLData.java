/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
public abstract class XMLData
{

    /** la requête */
    protected HttpServletRequest request = null;

    /** Le document XML */
    protected Document document = null;

    /**
     * Contructeur
     * 
     * @param pRequest pRequest
     * @throws ParserConfigurationException si erreur
     */
    public XMLData( final HttpServletRequest pRequest )
        throws ParserConfigurationException
    {
        this.request = pRequest;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.newDocument();
    }

    /**
     * Accesseur
     * 
     * @return la requête
     */
    public HttpServletRequest getRequest()
    {
        return request;
    }

    /**
     * Definiton du templissage du document
     */
    public abstract void fill();

    /**
     * @return le document contenant les informations XML
     */
    public Document getDocument()
    {
        return document;
    }

    /**
     * @return le document XML sous forme de String
     * @throws TransformerException si erreur
     */
    public String print()
        throws TransformerException
    {
        DOMSource domSource = new DOMSource( document );
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult( writer );
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
        transformer.setOutputProperty( OutputKeys.ENCODING, "ISO-8859-1" );
        transformer.transform( domSource, result );
        return writer.toString();
    }
}