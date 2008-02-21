/*
 * Créé le 23 déc. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.message.synchro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

import com.airfrance.welcom.addons.message.MessageResourcesAddons;
import com.airfrance.welcom.addons.message.WAddOnsMessageManager;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;
import com.airfrance.welcom.outils.jdbc.WStatement;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TaskCheckUpdateMessageRessources extends TimerTask {

    /** logger */
    private static Log log = LogFactory.getLog(TaskCheckUpdateMessageRessources.class);

    /** Date de derniere maj */
    private Date internalLastDate;

    /** */
    private ServletContext servletContext;

    /**
     * Initilise le verificateur de synchronisation des messages
     * @param pServletContext : servelet
     */
    public TaskCheckUpdateMessageRessources(final ServletContext pServletContext) {
        servletContext = pServletContext;
    }

    /** (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {

        final Date lastDateInBD = getTimeStampBD(WAddOnsMessageManager.ADDONS_MESSAGEMANAGER_NAME);

        if (lastDateInBD != null) {

            if (internalLastDate == null) {
                internalLastDate = lastDateInBD;
            }

            if (internalLastDate.getTime() < lastDateInBD.getTime()) {

                log.info("Synchronisation des MessageResources, suite a mise à jour");

                // Reset
                 ((MessageResourcesAddons) servletContext.getAttribute(Globals.MESSAGES_KEY)).resetCache();

                // Memorise le nouveau
                internalLastDate = lastDateInBD;
            }

        }

    }

    /**
     * @return Derniere date de modification, null si la table n'existe pas
     * @param addonName : nom de l'addons
     */
    private Date getTimeStampBD(final String addonName) {
        Date bdDate = null;
        WJdbcMagic jdbcMagic = null;
        try {
            jdbcMagic = new WJdbcMagic();
            final WStatement sta = jdbcMagic.getWStatement();
            sta.add("select * from WEL_ADDONS where");
            sta.addParameter("NAME=?", addonName);
            final ResultSet rs = sta.executeQuery();
            if ((rs != null) && rs.next()) {
                bdDate = new Date(rs.getLong("PARAMETERS"));
            }
            sta.close();

        } catch (final SQLException e) {
            return null;
        } finally {
            if (jdbcMagic != null) {
                jdbcMagic.close();
            }
        }
        return bdDate;
    }

}
