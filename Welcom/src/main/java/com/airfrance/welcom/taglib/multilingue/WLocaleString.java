/*
 * Créé le 10 janv. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.multilingue;

import java.util.HashMap;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WLocaleString
    implements WILocaleString
{
    /** les valeurs (key=locale, value= string dans la locale) */
    private final HashMap strings = new HashMap();

    /** la cle pour retrouver la valeur dans l'ApplicationResource */
    private String key = "";

    /**
     * @see com.airfrance.welcom.taglib.multilingue#getString()
     */
    public String getString( final String locale )
    {
        return (String) strings.get( locale );
    }

    /**
     * @see com.airfrance.welcom.taglib.multilingue#setString()
     */
    public void setString( final String locale, final String string )
    {
        strings.put( locale, string );
    }

    /**
     * @return key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param string key
     */
    public void setKey( final String string )
    {
        key = string;
    }

}
