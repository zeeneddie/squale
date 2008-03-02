package com.airfrance.welcom.taglib.canvas;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ICanvasHeaderRenderer
{

    /**
     * Dessine l'entete .. itétiere etc
     * 
     * @param headerImageURL imga e du header
     * @return l'entete
     */
    public abstract StringBuffer drawHeader( String headerImageURL );

}