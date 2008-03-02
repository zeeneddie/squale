/*
 * Créé le 30 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.webServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WebEngine
{
    /** logger */
    private static Log log = LogFactory.getLog( WebEngine.class );

    /**
     * constant d'état de trame http
     */
    private final static int STATUS_NOT_MODIFIED = 304;

    /**
     * constant d'état de trame http
     */
    private final static int COMPRESSION_LEVEL = 9;

    /**
     * Mille
     */
    private final static int MILLE = 1000;

    /** Format DATE Universel WEB */
    private final SimpleDateFormat sdfWeb = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH );

    /** Servlet */
    private HttpServlet servlet = null;

    /** Request */
    private HttpServletRequest request = null;

    /** Repsonse */
    private HttpServletResponse response = null;

    /**
     * Contructeur d'un seveur WEB pour la gestion des reponses
     * 
     * @param pServlet servlet
     * @param pRequest http request
     * @param pResponse http response
     */
    public WebEngine( final HttpServlet pServlet, final HttpServletRequest pRequest, final HttpServletResponse pResponse )
    {
        this.servlet = pServlet;
        this.request = pRequest;
        this.response = pResponse;
    }

    /**
     * gzip / deflate / none
     * 
     * @return OutPuStream en focntion de la configuration
     * @throws IOException IOException
     */
    public OutputStream getOutputStream()
        throws IOException
    {
        OutputStream out;
        if ( ( request.getHeader( "Accept-Encoding" ) != null )
            && ( request.getHeader( "Accept-Encoding" ).indexOf(
                                                                 WelcomConfigurator.getMessage(
                                                                                                WelcomConfigurator.OPTIFLUX_COMPRESSION_MODE ).toLowerCase() ) > -1 ) )
        {
            response.setHeader(
                                "Content-Encoding",
                                WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_MODE ).toLowerCase() );

            if ( Util.isEqualsIgnoreCase(
                                          WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_MODE ),
                                          "deflate" ) )
            {
                out = new DeflaterOutputStream( response.getOutputStream(), new Deflater( COMPRESSION_LEVEL, true ) );
            }
            else
            {
                out = new GZIPOutputStream( response.getOutputStream() );
            }
        }
        else
        {
            out = response.getOutputStream();
        }
        return out;
    }

    /**
     * isModified, Enter le fichier a telechager, et aussi la conf Welcom
     * 
     * @param urlManager : URL Managé
     * @return true if modified
     */
    public boolean isModified( final URLManager urlManager )
    {
        try
        {
            if ( request.getHeader( "If-Modified-Since" ) != null )
            {
                final Date clientLastDate = sdfWeb.parse( request.getHeader( "If-Modified-Since" ) );

                final long sClient = clientLastDate.getTime() / MILLE;
                final long sServer = urlManager.getLastDate().getTime() / MILLE;

                // precision a la seconde ;)
                if ( sClient >= sServer )
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

        }
        catch ( final ParseException e )
        {
            log.error( e, e );
        }
        return true;

    }

    /**
     * @param pathFile : Resources
     * @throws IOException : Probleme sur OutPutStream
     * @throws ServletException
     */
    public void process( String pathFile )
        throws IOException
    {
        // On remplace les // par / sauf pour (http:// )
        pathFile = pathFile.replaceAll( "//", "/" );
        pathFile = pathFile.replaceAll( "http:/", "http://" );
        pathFile = pathFile.replaceAll( "https:/", "https://" );

        URLManager urlManager = null;
        try
        {
            urlManager = new URLManager( pathFile, servlet );
        }
        catch ( final IOException e )
        {
            log.error( e, e );
            throw e;
        }

        if ( !isModified( urlManager ) )
        {

            response.setStatus( STATUS_NOT_MODIFIED );
        }
        else
        {
            response.setHeader( "Date", sdfWeb.format( new Date( System.currentTimeMillis() ) ) );
            response.setHeader( "Last-Modified", sdfWeb.format( urlManager.getLastDate() ) );
            response.setHeader( "Content-Type", urlManager.getMimeType() );

            // Récupere la bonne outputStream
            final OutputStream out = getOutputStream();

            // Ouverture de la stream
            final InputStream is = urlManager.getUrl().openStream();
            if ( urlManager.isImage() )
            {
                response.setHeader( "Accept-ranges", "bytes" );
                CopyUtils.copy( is, out );
            }
            else
            {
                // Optimization des .css
                String s = IOUtils.toString( is );
                IOUtils.closeQuietly( is );

                if ( Util.isEquals( urlManager.getMimeType(), "text/css" )
                    && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_REMOVECOMMENTS_CSS ) ) )
                {
                    final int lastSlashPos = urlManager.getSUrl().lastIndexOf( "/" );
                    s =
                        s.replaceAll( "@import url\\(\\.\\/", "@import url(css.do?value="
                            + urlManager.getSUrl().substring( 0, lastSlashPos + 1 ) );
                    s = smartReplaceAll( s );
                    s = removeComments( s );
                    s = s.replaceAll( "\\n\\p{Space}*", "\n" );
                    s = s.replaceAll( "[\n\r\t\f]", "" );
                    s = s.replaceAll( "[^}]*(UNI|MAJ)[^}]*}", "" ); // Suppresion de tout ce qui contient la charte
                                                                    // unitaire
                }

                // Optimization des .js
                if ( Util.isEquals( urlManager.getMimeType(), "application/x-javascript" )
                    && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_REMOVECOMMENTS_JS ) ) )
                {
                    s = smartReplaceAll( s );
                    s = removeComments( s );
                    s = s.replaceAll( "\\n\\p{Space}*", "\n" );
                    // s= s.replaceAll("([;{])\\p{Space}*[\n\r\t\f]", "\1");
                }

                s = convertWithConfigurator( s );

                try
                {
                    CopyUtils.copy( s, out );
                }
                catch ( final IOException e )
                {
                    log.error( "Probleme sur le flux ... :" + pathFile, e );
                }
            }

            try
            {
                out.close();
            }
            catch ( final SocketException se )
            {
                log.error( "Connection reset by peer : " + urlManager.getSUrl() );
            }
        }
    }

    /**
     * Suppression des commentaires /*
     * 
     * @param s chaine à nettoyer
     * @return chaine nettoyée
     */
    private String removeComments( final String s )
    {

        final StringBuffer sb = new StringBuffer();

        final Pattern patternDebut = Pattern.compile( "/\\*", Pattern.MULTILINE );
        final Pattern patternFin = Pattern.compile( "\\*/", Pattern.MULTILINE );
        final Matcher matcherDebut = patternDebut.matcher( s );
        final Matcher matcherFin = patternFin.matcher( s );

        int posStart = 0;
        int posEnd = 0;
        while ( matcherDebut.find( posStart ) && matcherFin.find( posStart ) )
        {

            while ( matcherFin.end() < matcherDebut.start() )
            {
                matcherFin.find( matcherDebut.start() );
            }

            posEnd = matcherDebut.start();
            sb.append( s.substring( posStart, posEnd ) );

            posStart = matcherFin.end();
        }
        sb.append( s.substring( posStart, s.length() ) );

        return sb.toString();
    }

    /**
     * Suppresion des commentaires //
     * 
     * @param s chaine à nettoyer
     * @return chaine nettoyée
     */
    private String smartReplaceAll( final String s )
    {
        Pattern pattern = Pattern.compile( "(.)(//[^\\n\\\\]*)\\n", Pattern.DOTALL | Pattern.MULTILINE );
        Matcher matcher = pattern.matcher( s );

        StringBuffer sb = new StringBuffer();
        boolean result = matcher.find();
        while ( result )
        {
            if ( Util.isEquals( matcher.group( 1 ), "\\" ) )
            {
                matcher.appendReplacement( sb, "\\" + matcher.group() );
            }
            else if ( Util.isEquals( matcher.group( 1 ), ":" ) )
            {
                matcher.appendReplacement( sb, matcher.group() );
            }
            else
            {
                matcher.appendReplacement( sb, matcher.group( 1 ) );
            }
            result = matcher.find();
        }
        matcher.appendTail( sb );

        // Recherche le ligne qui ont échappé au filtre et qui sont des commentaires
        pattern = Pattern.compile( "^//[^\\n]*\\n", Pattern.DOTALL | Pattern.MULTILINE );
        matcher = pattern.matcher( sb.toString() );
        sb = new StringBuffer();
        while ( matcher.find() )
        {
            matcher.appendReplacement( sb, "" );
        }
        matcher.appendTail( sb );

        return sb.toString();
    }

    /**
     * remplace les varable du welcom configurator dynamiquement dans les .Js
     * 
     * @param s le flux js à traiter
     * @return le flux mis à jour
     */
    private String convertWithConfigurator( final String s )
    {
        final Pattern regurl = Pattern.compile( "\\{#([\\w\\.]*)#\\}" );
        final StringBuffer sb = new StringBuffer();

        final Matcher matchurl = regurl.matcher( s );

        while ( matchurl.find() )
        {
            String pattern = matchurl.group( 1 );
            if ( pattern.startsWith( "chartevx" ) )
            {
                pattern = pattern.replaceFirst( "chartevx", WelcomConfigurator.getCharte().getWelcomConfigPrefix() );
            }
            final String res = WelcomConfigurator.getMessage( pattern );

            if ( !GenericValidator.isBlankOrNull( res ) )
            {
                matchurl.appendReplacement( sb, res );
            }
            else
            {
                matchurl.appendReplacement( sb, pattern );
            }
        }

        matchurl.appendTail( sb );

        return sb.toString();
    }

}
