package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Données synthétiques d'une grille qualité
 */
public class GridForm
    extends ActionIdFormSelectable
{
    /**
     * Nom de la grille
     */
    private String mName = "";

    /** Date de mise à jour */
    private Date mUpdateDate;

    /**
     * Constructeur par défaut.
     */
    public GridForm()
    {
    }

    /**
     * @return le nom.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pName le nom.
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @return date de mise à jour
     */
    public Date getUpdateDate()
    {
        return mUpdateDate;
    }

    /**
     * @param pString date de mise à jour
     */
    public void setUpdateDate( Date pString )
    {
        mUpdateDate = pString;
    }

    /**
     * le text que peut remplir l'administrateur pour indiquer aux gestionnaires d'applications utilisant cette grille
     * les différentes modifications
     */
    private String mAdminText = "";

    /**
     * @return le texte
     */
    public String getAdminText()
    {
        return mAdminText;
    }

    /**
     * @param pText le nouveau texte
     */
    public void setAdminText( String pText )
    {
        mAdminText = pText;
    }

    /**
     * Juste pour l'affichage
     */
    private String mDisplayedName = "";

    /**
     * @param pDisplayedName le nom a afficher
     */
    public void setDisplayedName( String pDisplayedName )
    {
        mDisplayedName = pDisplayedName;
    }

    /**
     * @return le nom utilisé pour l'affichage
     */
    public String getDisplayedName()
    {
        return mDisplayedName;
    }

}
