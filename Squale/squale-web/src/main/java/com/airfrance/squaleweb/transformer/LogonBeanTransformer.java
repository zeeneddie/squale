package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProfileForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.UserForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'informations sur un LogonBean
 */
public class LogonBeanTransformer implements WITransformer {

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * {@inheritDoc}
     */
    public WActionForm objToForm(Object[] arg0) throws WTransformerException {
        throw new WTransformerException("not implemented");
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] arg0, WActionForm arg1) throws WTransformerException {
        throw new WTransformerException("not implemented");
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public Object[] formToObj(WActionForm arg0) throws WTransformerException {
        return null;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     * {@inheritDoc}
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        // Extraction des paramètres
        UserForm pUser = (UserForm) pForm;
        LogonBean bean = (LogonBean) pObject[0];
        boolean pIsAdmin = ((Boolean)pObject[1]).booleanValue();
        bean.setId(pUser.getId());
        bean.setName(pUser.getName());
        bean.setEmail(pUser.getEmail());
        bean.setMatricule(pUser.getMatricule());
        // Traitement des profils
        Iterator profilesEntries = pUser.getProfiles().entrySet().iterator();
        HashMap idProfiles = new HashMap();
        while (profilesEntries.hasNext()) {
            Map.Entry entry = (Entry) profilesEntries.next();
            // On stocke l'id ainsi que le nom du profile dans la map
            idProfiles.put(new Long(((ApplicationForm)entry.getKey()).getId()), ((ProfileForm)entry.getValue()).getName());
        }
        bean.setProfiles(idProfiles); // Contient la liste des applications et les profils associés
        bean.setApplicationsList(pUser.getApplicationsList());
        // recupere la liste des applications avec droit administrateurs
        ArrayList mAdminList = new ArrayList();
        // Les applications validées ou en cours de validation pour les gestionnaires
        addManagerProfile(pUser, pIsAdmin, mAdminList, pUser.getApplicationsList());
        // Les applications en cours de validation
        bean.setAdminList(mAdminList);
        // Liste des applications seulement consultables
        // cad les applications qui sont accessibles mais pas administrables
        ArrayList readOnly = new ArrayList(pUser.getApplicationsList());
        readOnly.removeAll(bean.getAdminList());
        bean.setReadOnlyList(readOnly);
        bean.setDefaultAccess(pUser.getDefaultAccess().getName());
        bean.setAdmin(pIsAdmin);
        bean.setCurrentAccess(pUser.getDefaultAccess().getName());
        bean.setPublicList(pUser.getPublicApplicationsList());
    }

    /**
     * Ajoute à la liste des applications avec droit administrateurs les applications
     * ayant ce droit se trouvant <code>dans pApplis</code> ou toutes si il s'agit
     * d'un administrateur.
     * @param pUser le bean utilisateur
     * @param pIsAdmin pour indiquer si il s'agit d'un administrateur ou pas
     * @param mAdminList la liste des applications administrables
     * @param pApplis la liste des applications à ajouter
     */
    private void addManagerProfile(UserForm pUser, boolean pIsAdmin, ArrayList mAdminList, List pApplis) {
        Iterator it = pApplis.listIterator();
        while (it.hasNext()) {
            ApplicationForm application = (ApplicationForm) it.next();
            ProfileForm profile = (ProfileForm) pUser.getProfiles().get(application);
            // On positionne la liste des applications avec un privilège admin
            if (profile.getName().equals(ProfileBO.MANAGER_PROFILE_NAME) || pIsAdmin) {
                mAdminList.add(application);
            }
        }
        
    }

}
