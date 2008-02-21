package com.airfrance.welcom.outils.jdbc.wrapper;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.struts.util.WConstants;

/**
 * Classe d'accès au pool de connection base de données.
 * Les paramètres sont initialisés par la méthode initDataSource
 * Date de création : (18/10/2001 16:41:32)
 * @author: Cédric Torcq
 */
public final class ConnectionPool {
    /** logger */
    private static Log logStartup = LogFactory.getLog("Welcom");
   
    /** Retoune si la connexion est activé */
    private static boolean initialized = false;
    
    /** Datasource retouvé */
    private static javax.sql.DataSource ds = null;

    /**
      * The application scope attribute under which our JNDI datasource
      * is stored.
      */    
    /** JNDI : datasource */
    private static String JNDI_DATASOURCE_KEY = "";
    /** JNDI : Prefix */
    private static String PROVIDER_URL = "";
    /** JNDI : User */
    private static String USER;
    /** JNDI : Password */
    private static String PASSWD;

    /**
     * Commentaire relatif au constructeur ConnectionPool.
     */
    public ConnectionPool() {
        super();
    }

    /**
     * Returns a JDBC connection from a connection pool or other
     * resource, to be used and closed promptly.
     * <p>
     * @return JDBC connection from resource layer.
     * @exception SQLException on SQL or other errors. May wrap other
     * exceptions depending on implementation. Will not return null.
     */
    public static final Connection getConnection() throws SQLException {
        try {
            /* Version JNDI */

            /*******************************************************/
            if (ds == null) {
                // create parameter list to access naming system
                final java.util.Hashtable parms = new java.util.Hashtable();

                try {
                    Class.forName("com.ibm.websphere.naming.WsnInitialContextFactory");
                    parms.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
                } catch (final ClassNotFoundException e) {
                    logStartup.info("Passage en mode compatibilité WAS 3.5 ");
                }

                // parms.put(javax.naming.Context.PROVIDER_URL, PROVIDER_URL);
                // access naming system
                final javax.naming.Context ctx = new javax.naming.InitialContext(parms);

                // get DataSource factory object from naming system
                ds = (javax.sql.DataSource) ctx.lookup(JNDI_DATASOURCE_KEY);
            }

            // use DataSource factory to get data server connection
            Connection conn = null;

            if (!GenericValidator.isBlankOrNull(USER)) {
                conn = ds.getConnection(USER, PASSWD);
            } else {
                conn = ds.getConnection();
            }
            return conn;
        } catch (final Exception ex) {
            logStartup.error("ConnectionPool : Echec connexion : ",ex);

            if ((ex == null) || (ex.getMessage() == null)) {
                logStartup.info("Verifier la version de votre classes12.zip");
            }

            return null;
        }
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (19/10/2001 08:33:16)
     * @return java.lang.String 
     */
    public static final String getParameters() {
        final StringBuffer st = new StringBuffer();
        st.append(" url : ").append(PROVIDER_URL).append(" - source : ").append(JNDI_DATASOURCE_KEY).append(" - user/pwd : ").append(USER).append("/").append(PASSWD);

        return st.toString();
    }


    /**
     * Initilisation de la datasource
     * @param url : Chaine de connection JDBC (ex: jdbc:oracle:thin:@SERVEUR:POST:INSTANCE)
     * @param password : Mots de passe bd 
     * @param login : Login BD
     */
    public final static void initDataSource(final String url, final String password, final String login) {
        initialized = true;

        /*    String url =
              srvConfig.getInitParameter(WConstants.KEY_CONFIG_DATASOURCE_URL);
        
            PASSWD =
              srvConfig.getInitParameter(WConstants.KEY_CONFIG_DATASOURCE_PASSWD);
            USER =
              srvConfig.getInitParameter(WConstants.KEY_CONFIG_DATASOURCE_USER);
        */
        PASSWD = password;
        USER = login;

        try {
            PROVIDER_URL = url.substring(0, url.indexOf("jdbc"));
            JNDI_DATASOURCE_KEY = url.substring(url.indexOf("jdbc"));
            logStartup.info("Connexion sur : " + PROVIDER_URL + JNDI_DATASOURCE_KEY);
            testConnection();
        } catch (final Exception ex) {
            logStartup.info("Pas de connexion BD dans le web.xml sous la clef : " + WConstants.KEY_CONFIG_DATASOURCE_URL);
        }
    }

    /**
     * 
     * @return Retoun vrai si la connexion JDBC est correcte
     */
    private static boolean testConnection() {
        try {
            final Connection conn = getConnection();
            logStartup.info("URL : " + conn.getMetaData().getURL());
            logStartup.info("User : " + conn.getMetaData().getUserName());

            return true;
        } catch (final Exception e) {
            logStartup.info("impossible d'établir une connexion à la base de données");

            return false;
        }
    }

    /**
     * 
     * @return Vrai si la connection a la dateSource a été effectué
     */
    public static boolean isInitalized() {
        return initialized;
    }
}