/*
 * Créé le 3 sept. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author M327836
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TableUtil {

    /** logger */
    private static Log log = LogFactory.getLog(TableUtil.class);

    /**
     * Retourne la collection trié
     * @param session : Session en cour
     * @param name : Nom du bean
     * @return la collection triée
     */
    public static Collection getSortedTable(final HttpSession session, final String name) {

        return InternalTableUtil.getSortedTable(session, name, null);
    }

    /**
     * Retourne la collection trié
     * @param session : Session en cour
     * @param name : Nom du bean
     * @param property : Nom de la property du bean
     * @return la collection triée
     */
    public static Collection getSortedTable(final HttpSession session, final String name, final String property) {

        return InternalTableUtil.getSortedTable(session, name, property);
    }

    /**
     * Suuprime le trie surla table spécifié
     * @param session : Session en cour
     * @param name : Nom du bean
     */
    public static void resetSortOfTable(final HttpSession session, final String name) {

        InternalTableUtil.resetSortOfTable(session, name, null);
    }

    /**
     * Retourne la collection trié
     * @param session : Session en cour
     * @param name : Nom du bean
     * @param property : Nom de la property du bean
     */
    public static void resetSortOfTable(final HttpSession session, final String name, final String property) {

        InternalTableUtil.resetSortOfTable(session, name, property);
    }

}