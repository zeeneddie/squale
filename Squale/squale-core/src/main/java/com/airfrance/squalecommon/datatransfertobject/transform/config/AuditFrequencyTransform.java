package com.airfrance.squalecommon.datatransfertobject.transform.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.AuditFrequencyDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;

/**
 * Transforme les fréquences d'audit bo <-> dto
 */
public class AuditFrequencyTransform {

    /**
     * Convertit un AuditFrequencyBO en AuditFrequencyDTO
     * @param pAuditFrequency l'objet à convertir
     * @return le résultat de la conversion
     */
    public static AuditFrequencyDTO bo2dto(AuditFrequencyBO pAuditFrequency) {
        AuditFrequencyDTO result = new AuditFrequencyDTO();
        result.setId(pAuditFrequency.getId());
        result.setDays(pAuditFrequency.getDays());
        result.setFrequency(pAuditFrequency.getFrequency());
        return result;
    }

    /**
     * Convertit une liste de AuditFrequencyBO en liste de AuditFrequencyDTO
     * @param pAuditFrequencies la liste des fréquences à convertir
     * @return le résultat de la conversion
     */
    public static Collection bo2dto(Collection pAuditFrequencies) {
        Collection frequenciesDTO = new ArrayList();
        AuditFrequencyDTO frequencyDTO;
        AuditFrequencyBO frequencyBO;
        for (Iterator it = pAuditFrequencies.iterator(); it.hasNext();) {
            frequencyBO = (AuditFrequencyBO) it.next();
            frequencyDTO = bo2dto(frequencyBO);
            frequenciesDTO.add(frequencyDTO);
        }
        return frequenciesDTO;
    }

    /**
     * @param pAuditFrequencyDTO la fréquence d'audit
     * @return le BO de la fréquence
     */
    public static AuditFrequencyBO dto2bo(AuditFrequencyDTO pAuditFrequencyDTO) {
        AuditFrequencyBO result = new AuditFrequencyBO();
        result.setId(pAuditFrequencyDTO.getId());
        result.setDays(pAuditFrequencyDTO.getDays());
        result.setFrequency(pAuditFrequencyDTO.getFrequency());
        return result;
    }

    /**
     * Convertit une liste de AuditFrequencyDTO en liste de AuditFrequencyBO
     * @param pAuditFrequencies la liste des fréquences à convertir
     * @return le résultat de la conversion
     */
    public static Collection dto2bo(Collection pAuditFrequencies) {
        Collection frequenciesBO = new ArrayList();
        AuditFrequencyBO frequencyBO;
        AuditFrequencyDTO frequencyDTO;
        for (Iterator it = pAuditFrequencies.iterator(); it.hasNext();) {
            frequencyDTO = (AuditFrequencyDTO) it.next();
            frequencyBO = dto2bo(frequencyDTO);
            frequenciesBO.add(frequencyBO);
        }
        return frequenciesBO;
    }

}
