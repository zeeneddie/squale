/*
 * Créé le 8 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class JActionFormSelectable
    extends JActionForm
    implements WISelectable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1371973496505173479L;

    /** boolean selected */
    private boolean selected = false;

    /**
     * @return true si le form est selectionne
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * @param b la nouvelle selection
     */
    public void setSelected( final boolean b )
    {
        selected = b;
    }
}
