/*
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * @author M400841
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class UserTransform implements Serializable {
    
    /**
     * Constructeur prive
     */
    private UserTransform() {
    }

    /**
     * A lieu quand on affecte un utilisateur a un projet OU au login
     * DTO -> BO pour un User
     * @param pUserDTO UserDTO à transformer
     * @return UserBO
     */
    public static UserBO dto2Bo(UserDTO pUserDTO) {
        
        // Initialisation 
        UserBO userBO = new UserBO(); // retour du transform
        
        // Initialisation des autres valuers de UserBO
        userBO.setMatricule(pUserDTO.getMatricule());
        userBO.setPassword(pUserDTO.getPassword());
        userBO.setEmail(pUserDTO.getEmail());
        userBO.setFullName(pUserDTO.getFullName());
        userBO.setId(pUserDTO.getID());
        userBO.setUnsubscribed(pUserDTO.isUnsubscribed());
        return userBO;
   }

    /**
     * Transformation quand login effectué
     * BO -> DTO pour un User
     * @param pUserBO UserBO
     * @return UserDTO
     */
    public static UserDTO bo2Dto(UserBO pUserBO) {
      
        // Initialisation du retour et de la collection de sous-projet
        UserDTO userDTO = new UserDTO();
        
        // Initialisation des autres valuers de UserBO
        userDTO.setMatricule(pUserBO.getMatricule());
        if(pUserBO.getPassword() != null){
            userDTO.setPassword(pUserBO.getPassword());
        }
        if(pUserBO.getDefaultProfile() != null){
            userDTO.setDefaultProfile(ProfileTransform.bo2Dto(pUserBO.getDefaultProfile()));
        }
        userDTO.setEmail(pUserBO.getEmail());
        userDTO.setFullName(pUserBO.getFullName());
        userDTO.setID(pUserBO.getId());
        userDTO.setUnsubscribed(pUserBO.isUnsubscribed());
        
        return userDTO;  
        
    }

}
