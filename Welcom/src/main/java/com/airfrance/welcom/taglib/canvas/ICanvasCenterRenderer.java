package com.airfrance.welcom.taglib.canvas;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ICanvasCenterRenderer
{

    /**
     * debut du canvas center
     * 
     * @param hasCanvasLeft si posséde un canvas gauche
     * @return le debuit du canvas
     */
    public abstract String drawStart( boolean hasCanvasLeft );

    /**
     * debut du canvas center
     * 
     * @param soustitre soustitre
     * @param subTitleKey clef de sous titre
     * @param titre titre
     * @return le titre
     */
    public abstract String drawTitre( String titre, String subTitleKey, String soustitre );

    /**
     * le fin du canvas gauche
     * 
     * @return la fin du canvas
     */
    public abstract String drawEnd();

}
