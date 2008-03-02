package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * Liste des pratiques
 */
public class PracticeListForm
    extends WActionForm
{
    /**
     * Liste des pratiques
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des pratiques
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des pratiques
     */
    public void setList( List pList )
    {
        mList = pList;
    }

}
