/*
 * Créé le 23 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.jdbc;

import java.util.StringTokenizer;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;

/**
 * @author M327837
 *
 */
public class WConnectionString {
    /** mot clef URL JNDI pour le parsing ex : URL=jdbc/nomappli*/
    private static final String URL = "URL";

    /** mot clef PASS pour le parsing  mot de passe BD si non definit dans le JNDI*/
    private static final String PASS = "PASS";

    /** mot clef USER pour le parsing user BD si non definit dan le JNDI*/
    private static final String USER = "USER";

    /** mot clef NAME pour le parsing nom de la connexion pour le WJDBC ou WMJDBC*/
    private static final String NAME = "NAME";

    /** mot clef DESCRIPTION pour le parsing, description du type de la connexion*/
    private static final String DESCRIPTION = "DESCRIPTION";

    /** mot clef DEFAULT pour le parsing, spécifie que c'est la connexion par defaut */
    private static final String DEFAULT = "DEFAULT";
    
    /** nom de l'utilisateur pour la bd*/
    private String userName = "";
    
    /** password pour la bd */
    private String userPassword = "";
    
    /** Url du jndi */
    private String urlJndi = "";
    
    /** Nom de la connexion pour le WJDBC */
    private String name = "";
    
    /** Descrption de la connexion */
    private String description = "";
    
    /** NOm du provider */
    private String providerUrl = "";
    
    /** Nom de la data source */
    private String jndiDataSource = "";
    
    /** Est-ce pa connexion par defaut ? */
    private String parDefault = "false";

    /**
     * @return accesseur
     */
    public String getUrlJndi() {
        return urlJndi;
    }

    /**
     * @return accesseur
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return accesseur
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param url Init de l'url
     */
    public void setUrlJndi(final String url) {
        providerUrl = url.substring(0, url.indexOf("jdbc"));
        jndiDataSource = url.substring(url.indexOf("jdbc"));
        urlJndi = url;
    }

    /**
     * @param string userName
     */
    public void setUserName(final String string) {
        userName = string;
    }

    /**
     * @param string userPassword
     */
    public void setUserPassword(final String string) {
        userPassword = string;
    }

    /**
     * Initialise un objet connectionString a partir d'une string
     * @param connectionString la chaine
     */
    public void setConnectionString(final String connectionString) {
        final StringTokenizer st = new StringTokenizer(connectionString, ";");

        while (st.hasMoreElements()) {
            final String element = (String) st.nextElement();
            final int posEqual = element.indexOf("=");
            String key = "";
            String value = "";

            if (posEqual != -1) {
                key = element.substring(0, posEqual);
                value = element.substring(element.indexOf("=") + 1, element.length());
            } else {
                key = element;
            }

            // Set les valeurs dans le bean
            setAttribute(key, value);
        }
    }

    /** 
     * Set les attributes en fonction du parsing
     * @param key : key
     * @param value : valeur
     */
    private void setAttribute(final String key, final String value) {
        if (!GenericValidator.isBlankOrNull(key) && !GenericValidator.isBlankOrNull(value)) {
            if (Util.isEquals(URL, key)) {
                setUrlJndi(value);
            }
        
            if (Util.isEquals(PASS, key)) {
                setUserPassword(value);
            }
        
            if (Util.isEquals(USER, key)) {
                setUserName(value);
            }
        
            if (Util.isEquals(NAME, key)) {
                setName(value);
            }
        
            if (Util.isEquals(DESCRIPTION, key)) {
                setDescription(value);
            }
        }
        
        if (Util.isEquals(DEFAULT, key)) {
            setParDefault("true");
        }
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param string description
     */
    public void setDescription(final String string) {
        description = string;
    }

    /**
     * @param string name
     */
    public void setName(final String string) {
        name = string;
    }

    /**
     * @return jndiDataSource
     */
    public String getJndiDataSource() {
        return jndiDataSource;
    }

    /**
     * @return providerUrl
     */
    public String getProviderUrl() {
        return providerUrl;
    }

    /**
     * @param string jndiDataSource
     */
    public void setJndiDataSource(final String string) {
        jndiDataSource = string;
    }

    /**
     * @param string providerUrl
     */
    public void setProviderUrl(final String string) {
        providerUrl = string;
    }

    /**
     * @return parDefault
     */
    public String getParDefault() {
        return parDefault;
    }

    /**
     * @param string parDefault
     */
    public void setParDefault(final String string) {
        parDefault = string;
    }
}