package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.ListIterator;

import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformArrayList;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformer des listes d'applications.
 * 
 * @author M400842
 *
 */
public class ApplicationListTransformer extends AbstractListTransformer {

    /**
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ApplicationListForm form = new ApplicationListForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        ArrayList listDTO = (ArrayList) pObject[0];
        ApplicationListForm listForm = (ApplicationListForm) pForm;
        WITransformer transformer = WTransformerFactory.getSingleTransformer(ApplicationTransformer.class);
        ArrayList list = WTransformArrayList.objToForm(listDTO, transformer);
        listForm.setList(list);
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de ProjectDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        ArrayList listObject = (ArrayList) pObject[0];
        if (null != ((ApplicationListForm) pForm).getList()) {
            ListIterator it = ((ApplicationListForm) pForm).getList().listIterator();
            while (it.hasNext()) {
                listObject.add(WTransformerFactory.formToObj(ApplicationTransformer.class, (ApplicationForm) it.next())[0]);
            }
        }
    }

}
