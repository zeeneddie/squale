/*
 * Créé le 21 juin 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.util;

import java.io.Serializable;

/**
 * Classe permettant d'avoir des constantes dans l'utilisation du 
 * plugin checkstyle
 * @author henix
 *
 */
public class ConstantRulesChecking implements Serializable {

    /**
     * Constantes Checkstyle de severite pour remplir le tableau de repartition dans le cas de checkstyle
     * Case info
     */
    public static final int INFO_INT = 0;

    /**
     * case warning
     */
    public static final int WARNING_INT = 1;

    /**
     * case error
     */
    public static final int ERROR_INT = 2;

    /**
     * Constantes Checkstyle du niveau de la sévérité de la règle
     * Case info
     */
    public static final String INFO_LABEL = "info";
    /**
     * case warning
     */
    public static final String WARNING_LABEL = "warning";
    /**
     * case error
     */
    public static final String ERROR_LABEL = "error";

    /** Tableau des sévérités */
    public static final String[] SEVERITIES = { ERROR_LABEL, WARNING_LABEL, INFO_LABEL };

}
