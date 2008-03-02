package com.airfrance.squaleweb.applicationlayer.formbean.access;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour une liste d'accès utilisateur
 */
public class AccessListForm
    extends RootForm
{
    /**
     * Liste des accès
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des accès
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des accès
     */
    public void setList( List pList )
    {
        mList = pList;
    }

}
