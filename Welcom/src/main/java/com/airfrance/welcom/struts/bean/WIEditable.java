/*
 * Créé le 30 juin 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface WIEditable {
    /**
     * @return true si l'objet qui implemente l'interface est selectionne
     */
    public boolean isEdited();

    /**
     * change le flag d'edition de l'objet 
     * @param b la nouvelle selection
     */
    public void setEdited(boolean b);
}