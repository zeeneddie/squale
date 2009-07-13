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
package org.squale.welcom.struts.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class GZIPResponseStream
    extends ServletOutputStream
{

    /** Flux de sortie interne */
    protected ByteArrayOutputStream baos = null;

    /** Stream pour compresser */
    protected DeflaterOutputStream gzipstream = null;

    /** Closed ? */
    protected boolean closed = false;

    /** La response */
    protected HttpServletResponse response = null;

    /** La outputstream */
    protected ServletOutputStream output = null;

    /** Le contentType */
    protected String contentType;

    /** Type permis de compressé */
    protected Vector allowedType;

    /** l'entete */
    protected Hashtable headers;

    /** Ignore la compression */
    protected boolean ignoreZip = false;

    /** Niveau de compression max */
    private static final int LEVEL_COMPRESSION = 9;

    /**
     * Contructeur
     * 
     * @param pResponse : la response
     * @throws IOException : Probleme sur les streams
     */
    public GZIPResponseStream( final HttpServletResponse pResponse )
        throws IOException
    {
        super();
        closed = false;
        this.response = pResponse;
        this.output = response.getOutputStream();
        baos = new ByteArrayOutputStream();
        response.addHeader( "Content-Encoding",
                            WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_MODE ).toLowerCase() );
    }

    /**
     * Cloture
     * 
     * @throws IOException : Probleme sur les streams
     */
    public void close()
        throws IOException
    {
        if ( closed )
        {
            return;
        }

        if ( ( ( contentType == null ) || GZIPAllowedContentType.isAllowZipType( contentType ) ) && !ignoreZip )
        {
            final ByteArrayOutputStream baos2 = new ByteArrayOutputStream();

            if ( Util.isEqualsIgnoreCase(
                                          WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_MODE ),
                                          "deflate" ) )
            {
                gzipstream = new DeflaterOutputStream( baos2, new Deflater( LEVEL_COMPRESSION, true ) );
            }
            else
            {
                gzipstream = new GZIPOutputStream( baos2 );
            }

            final byte bytes[] = baos.toByteArray();

            gzipstream.write( bytes );
            gzipstream.finish();

            output.write( baos2.toByteArray() );
        }
        else
        {
            response.reset();

            response.setContentType( contentType );

            if ( headers != null )
            {
                for ( final Enumeration e = headers.keys(); e.hasMoreElements(); )
                {
                    final String key = (String) e.nextElement();
                    response.addHeader( key, (String) headers.get( key ) );
                }
            }

            output.write( baos.toByteArray() );
        }

        output.close();
        closed = true;
    }

    /**
     * Flush la stream si pas fermé
     * 
     * @throws IOException : Probleme sur les streams
     */
    public void flush()
        throws IOException
    {
        if ( !closed )
        {
            baos.flush();
        }
    }

    /**
     * Ecrit dans la stream
     * 
     * @param b un byte...
     * @throws IOException : Probleme sur les streams
     */
    public void write( final int b )
        throws IOException
    {
        if ( closed )
        {
            throw new IOException( "Cannot write to a closed output stream" );
        }

        baos.write( (byte) b );
    }

    /**
     * Ecrit dans la stream
     * 
     * @param b un tableau de byte
     * @throws IOException : Probleme sur les streams
     */
    public void write( final byte b[] )
        throws IOException
    {
        write( b, 0, b.length );
    }

    /**
     * Ecrit dans la stream
     * 
     * @param b un tableau de byte
     * @param off : Offeset
     * @param len : longueur
     * @throws IOException : Probleme sur les streams
     */
    public void write( final byte b[], final int off, final int len )
        throws IOException
    {
        if ( closed )
        {
            throw new IOException( "Cannot write to a closed output stream" );
        }

        baos.write( b, off, len );
    }

    /**
     * @return Si c'est fermé
     */
    public boolean closed()
    {
        return ( this.closed );
    }

    /**
     * Reset, fait rien
     */
    public void reset()
    {
        // noop
    }

    /**
     * @return le contentType
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * @param string le contentType
     */
    public void setContentType( final String string )
    {
        contentType = string;
    }

    /**
     * @return le header
     */
    public Hashtable getHeaders()
    {
        return headers;
    }

    /**
     * @param hashtable les headers
     */
    public void setHeaders( final Hashtable hashtable )
    {
        headers = hashtable;
    }

    /**
     * @return Ignore la compression
     */
    public boolean isIgnoreZip()
    {
        return ignoreZip;
    }

    /**
     * @param b Ignore la compression
     */
    public void setIgnoreZip( final boolean b )
    {
        ignoreZip = b;
    }
}