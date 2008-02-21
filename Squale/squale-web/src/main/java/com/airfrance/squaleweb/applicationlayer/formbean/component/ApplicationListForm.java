package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Liste d'applications
 * @author M400842
 *
 */
public class ApplicationListForm extends RootForm{
    /**
     * Liste des applications
     */
    private List mList = new ArrayList();

    
    /**
     * @return la liste des applications
     */
    public List getList() {
        return mList;
    }

    /**
     * @param pList la liste des applications
     */
    public void setList(List pList) {
        mList = pList;
    }

}
