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
/*
 * Créé le 28 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author Rémy Bouquet/Arnaud Lehmann Wrapper pour construire une réponse Http xml pour l'implémentation Ajax.
 */
public class AjaxXmlResponseWrapper
{
    /** logger */
    private static Log log = LogFactory.getLog( AjaxXmlResponseWrapper.class );

    /** Défini le tag racine de la réponse XML */
    protected String rootTag = "roottag";

    /** Le string buffer d'écriture */
    protected StringBuffer buffer;

    /** la response HTTP */
    protected HttpServletResponse response;

    /** vrai si le buffer est vide */
    private boolean bufferEmpty = true;

    /**
     * Construceur
     * 
     * @param presponse reponse HTTP
     */
    public AjaxXmlResponseWrapper( final HttpServletResponse presponse )
    {
        response = presponse;
        buffer = new StringBuffer();
        beginTag( rootTag );
    }

    /**
     * Construceur
     * 
     * @param presponse reponse HTTP
     * @param pRootTag nom du tag racine de la réponse XML
     */
    public AjaxXmlResponseWrapper( final HttpServletResponse presponse, final String pRootTag )
    {
        rootTag = pRootTag;
        response = presponse;
        buffer = new StringBuffer();
        beginTag( rootTag );
    }

    /**
     * ajoute un tag de début
     * 
     * @param tag tag à formater
     */
    protected void beginTag( final String tag )
    {
        buffer.append( "<" + tag + ">" );
    }

    /**
     * ajoute un tag de fin
     * 
     * @param tag tag à formater
     */
    protected void endTag( final String tag )
    {
        buffer.append( "</" + tag + ">" );
    }

    /**
     * ajoute un item dans le buffer
     * 
     * @param tag le nom du tag item
     * @param content contenu du tag
     */
    public void addItem( final String tag, final String content )
    {
        bufferEmpty = false;
        beginTag( tag );
        buffer.append( content );
        endTag( tag );
    }

    /**
     * ecrit le flux xml dans la response et ferme le flux On envoi systematiquement le tag root pour corriger des
     * problemes IE sous UNIX quand rien ne matche .. (il bloque)
     * 
     * @throws IOException exception d'entrée sortie
     */
    public void close()
        throws IOException
    {
        final String charset = WelcomConfigurator.getMessage( WelcomConfigurator.ENCODING_CHARSET );
        response.setHeader( "Content-Type", "text/xml; charset=" + charset );
        response.setHeader( "Cache-Control", "no-cache" );

        final OutputStream out = response.getOutputStream();
        endTag( rootTag );
        // if (!bufferEmpty) {
        out.write( buffer.toString().getBytes( charset ) );
        // }
        try
        {
            out.close();
        }
        catch ( final SocketException se )
        {
            log.debug( "requète tuée par le browser" );
        }
    }

    /**
     * @return rootTag
     */
    public String getRootTag()
    {
        return rootTag;
    }

    /**
     * @param string rootTag
     */
    public void setRootTag( final String string )
    {
        rootTag = string;
    }

}
