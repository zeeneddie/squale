/*
 * Créé le 11 août 05, par M400832.
 */
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400832
 * @version 1.0
 */
public class CRightListForm
    extends RootForm
{

    /**
     * Liste de droits.
     */
    private List mRightList = new ArrayList();

    /**
     * @return une liste de droits.
     */
    public List getRightList()
    {
        return mRightList;
    }

    /**
     * @param pRightList une liste de droits.
     */
    public void setRightList( List pRightList )
    {
        mRightList = pRightList;
    }

}
