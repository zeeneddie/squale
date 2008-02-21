package com.airfrance.welcom.struts.plugin;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import com.airfrance.welcom.outils.jdbc.WConnectionString;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WelcomJdbcPlugIn extends WelcomPlugIn {
    /** logger */
    private static Log logStartup = LogFactory.getLog("Welcom");

    /** List de source de données */
    private Hashtable dataSource = new Hashtable();

    /** Nom de la datasource */
    private String dataSourceName = "";

    /** password */
    private String bdPassword = "";

    /** user */
    private String bdUser = "";

    /**
     * @see org.apache.struts.action.PlugIn#destroy()
     */
    public void destroy() {
        dataSource = null;
    }

    /**
     * Recupere un data source
     * @param key : key
     * @return : La cahine de connexion
     */
    public WConnectionString getDataSource(final String key) {
        if (!dataSource.containsKey(key)) {
            dataSource.put(key, new WConnectionString());
        }

        return (WConnectionString) dataSource.get(key);
    }

    /**
     * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
     */
    public void init(final ActionServlet servlet, final ModuleConfig config) throws ServletException {

        //Repositionne le Welcom Context car on est dans le meme thread que tout le monde    	
        WelcomContext.initWelcomContextName();

        // Specifie le mode d'utilisation
        if (dataSource.size() == 0) {
            useMode = WConstants.MODE_JDBC;
        } else {
            useMode = WConstants.MODE_MJDBC;
        }

        logStartup.info("========== WELCOM ==========");
        logStartup.info(Welcom.about());

        Welcom.initParameter(servlet, config);

        Welcom.initRequestProcessor(servlet, config);

        Welcom.initUseMode(servlet, useMode);

        if (dataSource.size() == 0) {
            Welcom.initJdbc(dataSourceName, bdPassword, bdUser);
        } else {
            final Enumeration enumeration = dataSource.elements();

            while (enumeration.hasMoreElements()) {
                final WConnectionString element = (WConnectionString) enumeration.nextElement();
                Welcom.initJdbc(element);
            }
        }

        Welcom.initWelcom(getProperties());

        Welcom.initDefaults();        

        Welcom.initRender();

        Welcom.initFontPDF(servlet);

        Welcom.initForward(servlet, config);

        Welcom.initMapping(config);

        Welcom.initMessageFactory(getMessagePersistanceClass());

        Welcom.initException(servlet, config);

        Welcom.initAddons(servlet, config);

        Welcom.dumpConfigJVM();

        logStartup.info(dataSource);

        logStartup.info("===========================");
    }

    /**
     * @return password
     */
    public String getBdPassword() {
        return bdPassword;
    }

    /**
     * @return user
     */
    public String getBdUser() {
        return bdUser;
    }

    /**
     * @param string password
     */
    public void setBdPassword(final String string) {
        bdPassword = string;
    }

    /**
     * @param string user
     */
    public void setBdUser(final String string) {
        bdUser = string;
    }

    /**
     * @return Nom datasource
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * @param string Nom data source
     */
    public void setDataSourceName(final String string) {
        dataSourceName = string;
    }
}