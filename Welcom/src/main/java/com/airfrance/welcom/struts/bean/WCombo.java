/*
 * Créé le 5 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WResultSetUtils;
import com.airfrance.welcom.outils.jdbc.WStatement;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WCombo {
    /** logger */
    private static Log log = LogFactory.getLog(WCombo.class);

    /**
     * @param session la session
     * @param jdbc le WJdbc
     * @param requestAttributeName le requestAttributeName
     * @param stacmd le stacmd
     * @throws SQLException exception pouvant etre levee
     */
    public static void get(final HttpSession session, final WJdbc jdbc, final String requestAttributeName, final String stacmd) throws SQLException {
        get(session, jdbc, requestAttributeName, stacmd, false);
    }

    /**
     * @param session la session
     * @param jdbc le WJdbc
     * @param requestAttributeName le requestAttributeName
     * @param stacmd le stacmd
     * @param messages le messageRessources
     * @param locale la locale
     * @throws SQLException exception pouvant etre levee
     */
    public static void get(final HttpSession session, final WJdbc jdbc, final String requestAttributeName, final String stacmd, final MessageResources messages, final Locale locale)
        throws SQLException {
        get(session, jdbc, requestAttributeName, stacmd, false, messages, locale);
    }

    /**
    * @param session la session
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param stacmd le stacmd
    * @param crypte boolean crypte
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final HttpSession session, final WJdbc jdbc, final String requestAttributeName, final String stacmd, final boolean crypte) throws SQLException {
        get(session, jdbc, requestAttributeName, stacmd, crypte, null, null);
    }

    /**
    * @param session la session
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param stacmd le stacmd
    * @param crypte boolean crypte
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(
        final HttpSession session,
        final WJdbc jdbc,
        final String requestAttributeName,
        final String stacmd,
        final boolean crypte,
        final MessageResources messages,
        final Locale locale)
        throws SQLException {
        final Object v = get(jdbc, stacmd, crypte, messages, locale);

        if (v != null) {
            session.setAttribute(requestAttributeName, v);
        }
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param stacmd le stacmd
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final ActionServlet servlet, final WJdbc jdbc, final String requestAttributeName, final String stacmd) throws SQLException {
        get(servlet, jdbc, requestAttributeName, stacmd, false);
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param stacmd le stacmd
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final ActionServlet servlet, final WJdbc jdbc, final String requestAttributeName, final String stacmd, final MessageResources messages, final Locale locale)
        throws SQLException {
        get(servlet, jdbc, requestAttributeName, stacmd, false, messages, locale);
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param stacmd le stacmd
    * @param decrypte boolean crypte
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final ActionServlet servlet, final WJdbc jdbc, final String requestAttributeName, final String stacmd, final boolean decrypte) throws SQLException {
        get(servlet, jdbc, requestAttributeName, stacmd, decrypte, null, null);
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param stacmd le stacmd
    * @param decrypte boolean crypte
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(
        final ActionServlet servlet,
        final WJdbc jdbc,
        final String requestAttributeName,
        final String stacmd,
        final boolean decrypte,
        final MessageResources messages,
        final Locale locale)
        throws SQLException {
        final Object combo = servlet.getServletContext().getAttribute(requestAttributeName);

        if ((combo == null) || (messages == null)) {
            final Object v = get(jdbc, stacmd, decrypte, messages, locale);

            if (v != null) {
                servlet.getServletContext().setAttribute(requestAttributeName, v);
            }
        }
    }

    /**
    * @param session la session
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final HttpSession session, final WJdbc jdbc, final String requestAttributeName, final WStatement sta) throws SQLException {
        get(session, jdbc, requestAttributeName, sta, false);
    }

    /**
    * @param session la session
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @param decrypte boolean crypte
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final HttpSession session, final WJdbc jdbc, final String requestAttributeName, final WStatement sta, final boolean decrypte) throws SQLException {
        get(session, jdbc, requestAttributeName, sta, decrypte, null, null);
    }

    /**
    * @param session la session
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @param decrypte boolean crypte
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(
        final HttpSession session,
        final WJdbc jdbc,
        final String requestAttributeName,
        final WStatement sta,
        final boolean decrypte,
        final MessageResources messages,
        final Locale locale)
        throws SQLException {
        final Object combo = session.getAttribute(requestAttributeName);

        if ((combo == null) || (messages == null)) {
            final Object v = get(jdbc, sta, false, messages, locale);

            if (v != null) {
                session.setAttribute(requestAttributeName, v);
            }
        }
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final ActionServlet servlet, final WJdbc jdbc, final String requestAttributeName, final WStatement sta, final MessageResources messages, final Locale locale)
        throws SQLException {
        get(servlet, jdbc, requestAttributeName, sta, false, messages, locale);
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @param decrypte boolean crypte
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final ActionServlet servlet, final WJdbc jdbc, final String requestAttributeName, final WStatement sta, final boolean decrypte) throws SQLException {
        get(servlet, jdbc, requestAttributeName, sta, decrypte, null, null);
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @param decrypte boolean crypte
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(
        final ActionServlet servlet,
        final WJdbc jdbc,
        final String requestAttributeName,
        final WStatement sta,
        final boolean decrypte,
        final MessageResources messages,
        final Locale locale)
        throws SQLException {
        final Object combo = servlet.getServletContext().getAttribute(requestAttributeName);

        if ((combo == null) || (messages == null)) {
            final Object v = get(jdbc, sta, false, messages, locale);

            if (v != null) {
                servlet.getServletContext().setAttribute(requestAttributeName, v);
            }
        }
    }

    /**
    * @param servlet la servlet
    * @param jdbc le WJdbc
    * @param requestAttributeName le requestAttributeName
    * @param sta le WStatement
    * @throws SQLException exception pouvant etre levee
    */
    public static void get(final ActionServlet servlet, final WJdbc jdbc, final String requestAttributeName, final WStatement sta) throws SQLException {
        get(servlet, jdbc, requestAttributeName, sta, false);
    }

    /**
    * @param jdbc le WJdbc
    * @param stacmd le stacmd
    * @param decrypte boolean decrypte
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    * @return le new WComboValueLabel
    */
    private static Object get(final WJdbc jdbc, final String stacmd, final boolean decrypte, final MessageResources messages, final Locale locale) throws SQLException {
        WStatement sta = null;
        sta = jdbc.getWStatement();
        sta.add(stacmd);

        return get(jdbc, sta, decrypte, messages, locale);
    }

    /**
    * @param jdbc le WJdbc
    * @param sta le WStatement
    * @param decrypte boolean crypte
    * @param messages le messageRessources
    * @param locale la locale
    * @throws SQLException exception pouvant etre levee
    * @return le new WComboValueLabel
    */
    private static Object get(final WJdbc jdbc, final WStatement sta, final boolean decrypte, final MessageResources messages, final Locale locale) throws SQLException {
        ResultSet rs = null;
        final ActionErrors errors = new ActionErrors();

        try {
            rs = sta.executeQuery();

            if (rs != null) {
                final WComboValueLabel newCombo = new WComboValueLabel();

                // Remplir le combo
                WResultSetUtils.populateCombo(newCombo, rs, messages, locale);

                if (decrypte) {
                    jdbc.decrypte(newCombo);
                }

                return newCombo;
            }
        } catch (final Exception ex) {
            log.error("2001-critical-Misc--Exception générée:" + ex.toString());
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(ex.getMessage()));
            log.error(ex, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (final SQLException e) {
                    log.error("2002-critical-Database--Erreur sur le close ResultSet:" + e.toString());
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database.resultSetClose"));
                }
            }

            sta.close();
        }

        return null;
    }
}