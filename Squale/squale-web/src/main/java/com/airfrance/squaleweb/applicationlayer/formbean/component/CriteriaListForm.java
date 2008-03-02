package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * Liste des critères
 */
public class CriteriaListForm
    extends WActionForm
{
    /**
     * Liste des critères
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des critères
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des critères
     */
    public void setList( List pList )
    {
        mList = pList;
    }

}
