package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Liste de ComponentForm
 * 
 * @author M400842
 */
public class ComponentListForm
    extends RootForm
{
    /**
     * Liste des projets
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des projets
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des projets
     */
    public void setList( List pList )
    {
        mList = pList;
    }

}
