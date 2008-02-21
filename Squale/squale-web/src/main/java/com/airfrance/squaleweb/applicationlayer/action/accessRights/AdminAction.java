package com.airfrance.squaleweb.applicationlayer.action.accessRights;

import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;

/**
 */
public class AdminAction extends BaseDispatchAction {
   
    /** 
     * vérifie les droits de l'utilisateur à effectuer cette action
     * @param pApplicationId l'id de l'application
     * @param pUser l'utilisateur courant
     * @return true si l'utilisateur est admin
     */
    protected boolean checkRights(LogonBean pUser, Long pApplicationId) {
        return pUser.isAdmin();
    }

}
