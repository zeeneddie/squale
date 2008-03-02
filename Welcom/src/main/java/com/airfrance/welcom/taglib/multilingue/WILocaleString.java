/*
 * Créé le 10 janv. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.multilingue;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface WILocaleString
{
    /**
     * @param locale la locale
     * @return la valeur
     */
    public String getString( String locale );

    /**
     * @param locale la locale
     * @param string la valeur
     */
    public void setString( String locale, String string );

    /**
     * @return la key
     */
    public String getKey();

    /**
     * @param string la key
     */
    public void setKey( String string );
}
