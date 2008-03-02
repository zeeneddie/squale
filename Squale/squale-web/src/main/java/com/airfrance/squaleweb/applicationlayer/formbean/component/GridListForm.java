package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Liste des grilles qualité
 */
public class GridListForm
    extends RootForm
{
    /**
     * Liste des grilles
     */
    private List mGrids = new ArrayList();

    /**
     * Liste des noms des grilles qui ne sont liées à aucun profil et à aucun audit
     */
    private List mUnlinkedGrids = new ArrayList();

    /**
     * @return la liste des grilles
     */
    public List getGrids()
    {
        return mGrids;
    }

    /**
     * @return la liste des noms des grilles qui ne sont liées à aucun profil et à aucun audit
     */
    public List getUnlinkedGrids()
    {
        return mUnlinkedGrids;
    }

    /**
     * @param pGrids la liste des grilles
     */
    public void setGrids( List pGrids )
    {
        mGrids = pGrids;
    }

    /**
     * @param pUnlikedGrids la liste des noms des grilles qui ne sont liées à aucun profil et à aucun audit
     */
    public void setUnlinkedGrids( List pUnlikedGrids )
    {
        mUnlinkedGrids = pUnlikedGrids;
    }

}
