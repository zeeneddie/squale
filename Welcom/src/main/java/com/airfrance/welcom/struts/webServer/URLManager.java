/*
 * Créé le 30 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.webServer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class URLManager {

    /** Fichier wel */
    private WebFile mWebFile=null;
    /** Type mime */
    private String mimeType = "";
    /** Url Saisie */
    private String sUrl = "";
    /** Servlet HTTP */
    private HttpServlet servlet;
    /** logger */
    private static Log logger = LogFactory.getLog(URLManager.class);

    /**
     *  Construit un nouvel URL Manager
     * @param pSUrl : URL sous formatChaine 
     * @param pServlet : servlet
     * @throws IOException Pb sur la stream
     * */
    public URLManager(final String pSUrl, final HttpServlet pServlet) throws IOException {
        sUrl = pSUrl;
        // Pour compatibilité avec le calendrier popup v3
        if (sUrl.indexOf('?') > 0) {
            sUrl = sUrl.substring(0, sUrl.indexOf('?'));
        }
        servlet = pServlet;
        computeURL();
    }

    /** 
    * Recontruit l'URL 
    * et recherche la derniere date de modification
    * @throws IOException Pb sur la stream
    */
    private void computeURL() throws IOException {

        mWebFile=getWebFileFromContext( servlet.getServletContext(), sUrl);
        
        // Si webFile est null ..
        if (mWebFile == null  && Util.isTrue(WelcomConfigurator.getMessage(WelcomConfigurator.OPTIFLUX_JSOBFUSCATOR_DISABLED))) {

            if (mWebFile==null) {
                mWebFile = getWebFileFromClassPath("/web/" + WelcomConfigurator.getCharte().getWelcomConfigPrefix() + "/debug/" + sUrl);
            }
            
            if (mWebFile==null) {
                mWebFile = getWebFileFromClassPath("/web/debug/" + sUrl);                        
            } 
        }
        if (mWebFile==null) {
            mWebFile = getWebFileFromClassPath("/web/" + WelcomConfigurator.getCharte().getWelcomConfigPrefix() + "/" + sUrl);                        
        } 

        if (mWebFile==null) {
            mWebFile = getWebFileFromClassPath("/web/" + sUrl);                        
        } 

        // Si l'url n'est pas sur le site
        if (mWebFile == null) {
            mWebFile = getWebFileFromHTTP(sUrl);
        }

        // Teste si le logger est en debug, permet d'augmenté les perfs
        if (logger.isDebugEnabled()) {
            logger.info("web file : "+sUrl+" , " + mWebFile.getUrl());
        }

        // Stocke le type mime en demandant a WAS le type associé
        mimeType = servlet.getServletConfig().getServletContext().getMimeType(sUrl);

    }
    
    
    /**
     * Retourn l'url a partie d'un site distant
     * @param pUrl url
     * @throws MalformedURLException problem sur l'url
     * @throws IOException  exception
     * @return webfile
     */
    private WebFile getWebFileFromHTTP(String pUrl) throws MalformedURLException, IOException {
        WebFile webFile = new WebFile(WebFile.TYPE_DISTANT);
        webFile.setUrl(new URL(pUrl));
        final URLConnection urlcon = webFile.getUrl().openConnection();
        urlcon.setUseCaches(true);
        urlcon.connect();
        webFile.setLastDate(new Date(urlcon.getLastModified()));
        
        if (urlcon.getLastModified() == 0) {
            webFile.setLastDate(new Date(urlcon.getDate()));
        }
        
        return webFile;
    }


    /**
     * Retoune le webFile dans le repertoire webcontent
     * @param context context
     * @param pUrl url
     * @return le webfile 
     */
    public WebFile getWebFileFromContext(ServletContext context, String pUrl) {
        WebFile webFile = new WebFile(WebFile.TYPE_SERVLET);
        // Compatibilité Tomcat !!
        try {
            webFile.setUrl(context.getResource(pUrl));
        } catch (final Exception e) {
            try {
                // Test compatiblite Tomcat (Necessite un /)
                webFile.setUrl(context.getResource("/" + pUrl));
            } catch (final Exception ee) {
                // Recherche si la resource demandé ne se trouve pas dans le jar Welcom
            }
        }
        
        if (webFile.getUrl()!=null) {
            // recherche la date
            final String file = context.getRealPath(sUrl);

            final File f = new File(file);
            webFile.setLastDate(new Date(f.lastModified()));
            return webFile;    
        } else {
            return null;
        }
        
    }

    /**
     * Retourne le web file a partir du classPath
     * @param pUrl url
     * @return le webFile
     */
    public WebFile getWebFileFromClassPath(String pUrl) {
         logger.debug("TEST : " + pUrl);
         WebFile webFile = new WebFile(WebFile.TYPE_CLASSPATH);
         try {
             webFile.setUrl( this.getClass().getResource(pUrl) );
             if (webFile.getUrl() != null) {
                 webFile.setLastDate(getURLDate(webFile.getUrl()));
             }
                   
         } catch (final Exception e) {
             // Ne fais rien -> webFile.getUrl()== null 
         } 
         if (webFile.getUrl()== null) {
             return null;
         } else {
             return webFile;
         }
         
               
     }

    /**
     * Recupere la date du fichier dont le chemin est l'url
     * @param pUrl : L'url
     * @return : Date dir URL OK
     * @throws IOException Probleme sur l'ouverture de la connection
     */
    public Date getURLDate(final URL pUrl) throws IOException {

        final URLConnection urlcon = pUrl.openConnection();
        urlcon.setUseCaches(true);
        urlcon.connect();
        return new Date(urlcon.getLastModified());

    }

    /**
     * @return Date de derniere modification
     */
    public Date getLastDate() {

        if ((WelcomConfigurator.getLastDate() != null) && (WelcomConfigurator.getLastDate().getTime() >= mWebFile.getLastDate().getTime())) {
            return WelcomConfigurator.getLastDate();
        } else {
            return mWebFile.getLastDate();
        }
    }

    /**
     * @return Type mime
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @return Url d'origine
     */
    public String getSUrl() {
        return sUrl;
    }

    /**
     * @return  Réelle URL
     */
    public URL getUrl() {
        return mWebFile.getUrl();
    }

    /** 
     * Retourne true si c'est une image
     * @return vrais si c'est une image
     */
    public boolean isImage() {
        return (getMimeType().indexOf("image") > -1);
    }
    


}
