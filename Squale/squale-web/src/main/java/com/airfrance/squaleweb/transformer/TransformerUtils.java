package com.airfrance.squaleweb.transformer;

import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * Fonctions utilitaires pour les transformer
 */
public class TransformerUtils {
    /**
     * Retourne le nom de l'application stockée en session.
     * @param pId l'id de l'application.
     * @param pApplications la liste des applications.
     * @return le nom de l'application.
     */
    public static String getApplicationName(long pId, final Collection pApplications) {
        Iterator it = pApplications.iterator();
        String name = null;
        ComponentDTO application = null;
        while (it.hasNext() && null == name) {
            application = (ComponentDTO) it.next();
            if (application.getID() == pId) {
                name = application.getName();
            }
        }
        return name;
    }

}
