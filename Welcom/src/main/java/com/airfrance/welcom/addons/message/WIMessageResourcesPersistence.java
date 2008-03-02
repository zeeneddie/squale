package com.airfrance.welcom.addons.message;

import java.util.Vector;

/**
 * @created Jul 22, 2003
 * @version InterFacePersistence.java
 */
public interface WIMessageResourcesPersistence
{
    /**
     * Method getMessagesByLanguage. reads all Messages for given locale from persistence store
     * 
     * @param locale : locale
     * @return Vector : Tous les message dispo pour cette locale
     */
    public Vector getAppMessagesByLocale( String locale );

    /**
     * Liste des locales utilisables
     * 
     * @return Liste des locales
     */
    public Vector getavailableLocales();
}