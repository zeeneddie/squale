package com.airfrance.squaleweb.applicationlayer.formbean.reference;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Formulaire contenant la liste des formulaires du référentiel
 */
public class ReferenceListForm extends RootForm {

    /**
     * Liste des références, liste de ReferenceForm
     */
    private List mList = new ArrayList(0);

    /**
     * @return la liste des références
     */
    public List getList() {
        return mList;
    }
    
    /**
     * 
     * @param pList la liste des référence
     */
    public void setList(List pList) {
        mList = pList;
    }
}
