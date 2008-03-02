package com.airfrance.squalecommon.util;

/**
 * 
 */
public class SqualeCommonConstants
{

    // Clés pour la gestion des mails

    /** Les destinataires sont seulement les admins */
    public final static String ONLY_ADMINS = "onlyAdmins";

    /** Les destinataires sont seulement les Managers */
    public final static String ONLY_MANAGERS = "onlyManagers";

    /** Les destinataires sont les managers de l'appli et les lecteurs */
    public final static String MANAGERS_AND_READERS = "managersAndReaders";

    /** Les destinataires sont seulement les admins */
    public final static String MANAGERS_AND_ADMINS = "managersAndAdmins";

    // Constantes pour gérer les différents status des composants par rapport au plan d'action
    /**
     * Tous les composants
     */
    public final static String ALL_COMPONENTS = "components.all";

    /**
     * Les composants exclus du plan d'action
     */
    public final static String EXCLUDED_FROM_PLAN_COMPONENTS = "components.excluded";

    /**
     * les composants inclus dans le plan d'action
     */
    public final static String INCLUDED_FROM_PLAN_COMPONENTS = "components.included";
}
