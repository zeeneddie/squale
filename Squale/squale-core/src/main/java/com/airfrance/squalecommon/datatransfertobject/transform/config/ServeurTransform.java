package com.airfrance.squalecommon.datatransfertobject.transform.config;

import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * Transform d'un Serveur d'exécution Squalix
 */
public class ServeurTransform {

    /**
     * Convertit un ServeurBO en ServeurDTO
     * @param pServeurBO objet à convertir
     * @return résultat de la conversion
     */
    public static ServeurDTO bo2dto(ServeurBO pServeurBO) {
        ServeurDTO lServeurDTO = new ServeurDTO();
        lServeurDTO.setServeurId(pServeurBO.getServeurId());
        lServeurDTO.setName(pServeurBO.getName());
        return lServeurDTO;
    }

    /**
     * Convertit un ServeurDTO en ServeurBO
     * @param pServeurDTO objet à convertir
     * @return résultat de la conversion
     */
    public static Object dto2bo(ServeurDTO pServeurDTO) {
        ServeurBO lServeurBO = new ServeurBO();
        lServeurBO.setServeurId(pServeurDTO.getServeurId());
        lServeurBO.setName(pServeurDTO.getName());
        return lServeurBO;
    }

}
