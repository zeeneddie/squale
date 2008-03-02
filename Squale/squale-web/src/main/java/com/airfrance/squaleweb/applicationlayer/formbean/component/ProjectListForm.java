package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Liste de projets
 * 
 * @author M400842
 */
public class ProjectListForm
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
