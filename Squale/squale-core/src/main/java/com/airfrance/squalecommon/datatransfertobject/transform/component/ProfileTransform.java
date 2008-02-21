/*
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.ProfileDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.AtomicRightsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;

/**
 * @author M400841
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProfileTransform implements Serializable {


    /**
     * Constructeur prive
     */
    private ProfileTransform() {
    }

    /**
     * DTO -> BO pour un Profile
     * @param pProfileDTO ProfileDTO à transformer
     * @return ProfileBO
     * @deprecated non utilise car on ne renverra pas de profile
     */
    public static ProfileBO dto2Bo(ProfileDTO pProfileDTO) {
        
        // Initialisation
        ProfileBO profileBO = new ProfileBO();
        Map rightsBO = new HashMap(); // droits
        
        profileBO.setName(pProfileDTO.getName());
        profileBO.setRights(rightsBO);
        
        return profileBO;
        
    }

    /**
     * Utilisé pour récupérer un utilisateur
     * BO -> DTO pour un Profile
     * @param pProfileBO ProfileBO
     * @return ProfileDTO
     */
    public static ProfileDTO bo2Dto(ProfileBO pProfileBO) {
        
        // Initialisation
        ProfileDTO profileDTO = new ProfileDTO();
        Map rightsDTO = new HashMap(); // droits
        
        Iterator rightsIterator = pProfileBO.getRights().keySet().iterator();
        AtomicRightsBO currentKey = null;
        while(rightsIterator.hasNext()){
            currentKey = (AtomicRightsBO) rightsIterator.next();
            // Ajout dans la map DTO du nom du droit atomique et de la valeur 
            // correspondante a la clé
            rightsDTO.put(currentKey.getName(), pProfileBO.getRights().get(currentKey));
        }
        
        
        profileDTO.setName(pProfileBO.getName());
        profileDTO.setRights(rightsDTO);
        
        return profileDTO;
    }

}
