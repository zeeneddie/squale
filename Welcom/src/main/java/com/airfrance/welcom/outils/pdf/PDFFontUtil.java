/*
 * Créé le 18 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.lowagie.text.FontFactory;

/**
 * @author M327836
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class PDFFontUtil {
    /**
     * Commons Logger for this class
     */
    private static Log logger = LogFactory.getLog(PDFFontUtil.class);

    /** Singleton */
    private static PDFFontUtil pdfFontUtil;

    /** L'actionServlet pour la conf */
    private ActionServlet servlet;

    /**
     * Contructer privé
     * 
     * @param s Action Servlet pour la conf
     */
    private PDFFontUtil(final ActionServlet s) {
        servlet = s;
    }

    /**
     * Initolisation du repertoire de font pour l'impression
     * @param s Action Servlet pour la fonc
     * @return True si TVB
     */
    public static boolean init(final ActionServlet s) {
        pdfFontUtil = new PDFFontUtil(s);
        boolean resultSR = true;
        boolean resultIT = true;
        
        /* TODO FAB : XReport doit être sorti car il s'agit d'une lib non OSS
        try {
            Class.forName("inetsoft.report.ReportEnv");
            inetsoft.report.ReportEnv.setProperty("font.metrics.source", "truetype");
            inetsoft.report.ReportEnv.setProperty("font.truetype.path", PDFFontUtil.getRealFontPath().trim());

            resultSR = true;
        } catch (final ClassNotFoundException e) {
            resultSR = false;
        }
        */

        try {
            Class.forName("com.lowagie.text.Document");
            final String path = PDFFontUtil.getRealFontPath().trim();
            final File fontDir = new File(path);
            final File[] listFile = fontDir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".ttf");
                }
            });
            if (listFile != null) {

                for (int i = 0; i < listFile.length; i++) {

                    FontFactory.register(listFile[i].getCanonicalPath());
                }
                if (logger.isDebugEnabled()) {
                    for (final Iterator iter = FontFactory.getRegisteredFamilies().iterator(); iter.hasNext();) {
                        final String font = (String) iter.next();

                        logger.debug("registering " + font);

                    }
                }
            }

            resultIT = true;
        } catch (final ClassNotFoundException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Erreur lors du chargement des fonts pour itext", e);
            }
            resultIT = false;
        } catch (final IOException ie) {
            if (logger.isDebugEnabled()) {
                logger.debug("Erreur lors du chargement des fonts pour itext", ie);
            }

            resultIT = false;
        }

        return resultSR || resultIT;
    }

    /**
     * 
     * @return Retourne le chemin absolu
     */
    private String getInternalRealFontPath() {

        final String inConf = WelcomConfigurator.getMessage(WelcomConfigurator.FONT_PATH);
        URL url;
        try {
            url = this.getClass().getClassLoader().getResource(inConf);
            if (url != null) {
                return url.getFile();
            }
        } catch (final Exception e) {
            e.printStackTrace();

        }
        return servlet.getServletContext().getRealPath(inConf);
    }

    /**
     * 
     * @return Retourne le chemin absolu
     */
    public static String getRealFontPath() {
        return pdfFontUtil.getInternalRealFontPath();
    }

    /**
     * @return Verifie que le chemin est trouvé
     */
    public static boolean isSpecificRealFontPath() {
        final File f = new File(getRealFontPath());

        return (f.isDirectory());
    }
}