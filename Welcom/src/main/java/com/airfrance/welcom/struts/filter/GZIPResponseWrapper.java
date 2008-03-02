package com.airfrance.welcom.struts.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author M327837 Wrapper de la response pour le filtre
 */
public class GZIPResponseWrapper
    extends HttpServletResponseWrapper
{
    /** Response d'origine */
    protected HttpServletResponse origResponse = null;

    /** output stream de la servlet */
    protected ServletOutputStream stream = null;

    /** le writer */
    protected PrintWriter writer = null;

    /** le contentType */
    protected String contentType;

    /** Les headers */
    protected Hashtable headers;

    /** Ignore le zip */
    protected boolean ignoreZip = false;

    /**
     * Le contructeur
     * 
     * @param response : La response d'origne
     */
    public GZIPResponseWrapper( final HttpServletResponse response )
    {
        super( response );
        origResponse = response;
    }

    /**
     * Creation de la outputstream
     * 
     * @return : la outpoutstream
     * @throws IOException : Probleme sur la stream
     */
    public ServletOutputStream createOutputStream()
        throws IOException
    {
        final GZIPResponseStream zips = new GZIPResponseStream( origResponse );
        zips.setContentType( contentType );
        zips.setHeaders( headers );
        zips.setIgnoreZip( ignoreZip );

        return ( zips );
    }

    /**
     * Cloture des responses
     */
    public void finishResponse()
    {
        try
        {
            if ( writer != null )
            {
                writer.close();
            }
            else
            {
                if ( stream != null )
                {
                    stream.close();
                }
            }
        }
        catch ( final IOException e )
        {
            ;
        }
    }

    /**
     * Flush de la stream
     * 
     * @throws IOException : Probleme sur la stream
     */
    public void flushBuffer()
        throws IOException
    {
        stream.flush();
    }

    /**
     * @return Recupere la output stream
     * @throws IOException : Probleme sur la stream
     */
    public ServletOutputStream getOutputStream()
        throws IOException
    {
        if ( writer != null )
        {
            throw new IllegalStateException( "getWriter() has already been called!" );
        }

        if ( stream == null )
        {
            stream = createOutputStream();
        }

        return ( stream );
    }

    /**
     * @return retourne le Writter
     * @throws IOException : Probleme sur la stream
     */
    public PrintWriter getWriter()
        throws IOException
    {
        if ( writer != null )
        {
            return ( writer );
        }

        if ( stream != null )
        {
            throw new IllegalStateException( "getOutputStream() has already been called!" );
        }

        stream = createOutputStream();
        writer = new PrintWriter( new OutputStreamWriter( stream, "ISO-8859-1" ) );

        return ( writer );
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addHeader(java.lang.String, java.lang.String)
     */
    public void addHeader( final String arg0, final String arg1 )
    {
        if ( arg0.equalsIgnoreCase( "Content-Type" ) )
        {
            setContentType( arg1 );
        }
        else
        {
            if ( arg0.equalsIgnoreCase( "Content-Encoding" ) )
            {
                if ( stream != null )
                {
                    ( (GZIPResponseStream) stream ).setIgnoreZip( true );
                }

                ignoreZip = true;
            }

            if ( headers == null )
            {
                headers = new Hashtable();
            }

            headers.put( arg0, arg1 );

            if ( stream != null )
            {
                ( (GZIPResponseStream) stream ).setHeaders( headers );
            }

            super.addHeader( arg0, arg1 );
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setHeader(java.lang.String, java.lang.String)
     */
    public void setHeader( final String arg0, final String arg1 )
    {
        if ( arg0.equalsIgnoreCase( "Content-Type" ) )
        {
            setContentType( arg1 );
        }
        else
        {
            if ( arg0.equalsIgnoreCase( "Content-Encoding" ) )
            {
                if ( stream != null )
                {
                    ( (GZIPResponseStream) stream ).setIgnoreZip( true );
                }

                ignoreZip = true;
            }

            if ( headers == null )
            {
                headers = new Hashtable();
            }

            headers.put( arg0, arg1 );

            if ( stream != null )
            {
                ( (GZIPResponseStream) stream ).setHeaders( headers );
            }

            super.setHeader( arg0, arg1 );
        }
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#setContentType(java.lang.String)
     */
    public void setContentType( final String arg0 )
    {

        if ( stream != null )
        {
            ( (GZIPResponseStream) stream ).setContentType( arg0 );
        }

        super.setContentType( arg0 );
    }

    /**
     * Set le content Lent
     * 
     * @param length Longueur
     */
    public void setContentLength( final int length )
    {
    }

    /**
     * @return le content Type
     */
    public String getContentType()
    {
        return contentType;
    }
}