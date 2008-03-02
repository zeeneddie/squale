package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient une liste de résultats et de facteurs
 * 
 * @author M400842
 */
public class FactorsResultListForm
    extends RootForm
{
    /**
     * Nom de la grille
     */
    private String mGridName;

    /**
     * la date de mise à jour de la grille
     */
    private Date mGridUpdateDate;

    /**
     * Liste de facteurs
     */
    private Collection mFactors = new ArrayList();

    /**
     * Liste des résultats
     */

    private Collection mResults = new ArrayList();

    /**
     * @return la liste des résultats
     */
    public Collection getFactors()
    {
        return mFactors;
    }

    /**
     * @param pList la liste des résultats
     */
    public void setFactors( Collection pList )
    {
        mFactors = pList;
    }

    /**
     * @return résultats
     */
    public Collection getResults()
    {
        return mResults;
    }

    /**
     * @param pCollection résultats
     */
    public void setResults( Collection pCollection )
    {
        mResults = pCollection;
    }

    /**
     * @return nom de la grille qualité
     */
    public String getGridName()
    {
        return mGridName;
    }

    /**
     * @param pString nom de la grille qualité
     */
    public void setGridName( String pString )
    {
        mGridName = pString;
    }

    /**
     * @return la date de mise à jour de la grille
     */
    public Date getGridUpdateDate()
    {
        return mGridUpdateDate;
    }

    /**
     * @param pDate la date de mise à jour
     */
    public void setGridUpdateDate( Date pDate )
    {
        mGridUpdateDate = pDate;
    }

}
