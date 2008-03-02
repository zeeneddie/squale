package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * Liste de facteurs
 */
public class FactorListForm
    extends WActionForm
{
    /**
     * Liste des facteurs
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des facteurs
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des facteurs
     */
    public void setList( List pList )
    {
        mList = pList;
    }

}