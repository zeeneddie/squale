package com.airfrance.squalecommon.datatransfertobject.transform.component.parameters;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 * Transforme dto <-> bo pour un ListParameter
 */
public class ListParameterTransform {

    /**
     * 
     * @param pListParameterDTO le DTO à transformer en BO
     * @return le BO
     */
    public static ListParameterBO dto2Bo(ListParameterDTO pListParameterDTO) {
        ListParameterBO result = new ListParameterBO();
        result.setId(pListParameterDTO.getId());
        // On récupère les paramètres associés
        List paramsDTO = pListParameterDTO.getParameters();
        // On va remplir la liste avec des objets transformés en BO.
        List paramsBO = new ArrayList();
        for(int i=0; i<paramsDTO.size(); i++) {
            // Si l'objet courant est une map, on appelle la méthode qui transforme tous
            // les éléments de la liste en BO.
            if (paramsDTO.get(i) instanceof MapParameterDTO) {
                paramsBO.add(MapParameterTransform.dto2Bo((MapParameterDTO) paramsDTO.get(i)));
            } else {
                // Si il s'agit d'une liste, on rappelle la méthode pour transformer à son tour
                // tous ses éléments en BO.
                if (paramsDTO.get(i) instanceof ListParameterDTO) {
                    paramsBO.add(ListParameterTransform.dto2Bo((ListParameterDTO) paramsDTO.get(i)));
                } else {
                    if (paramsDTO.get(i) instanceof StringParameterDTO) {
                        paramsBO.add(StringParameterTransform.dto2Bo((StringParameterDTO) paramsDTO.get(i)));
                    }
                }
            }
        }
        result.setParameters(paramsBO);
        return result;
    }

    /**
     * 
     * @param pListParameterBO le BO à transformer en DTO
     * @return le DTO
     */
    public static ListParameterDTO bo2Dto(ListParameterBO pListParameterBO) {
        ListParameterDTO result = new ListParameterDTO();
        result.setId(pListParameterBO.getId());
        // On récupère les paramètres associés
        List paramsBO = pListParameterBO.getParameters();
        // On va remplir la liste avec des objets transformés en DTO.
        List paramsDTO = new ArrayList();
        for(int i=0; i<paramsBO.size(); i++) {
            // Si l'objet courant est une map, on appelle la méthode qui transforme tous
            // les éléments de la liste en DTO.
            if (paramsBO.get(i) instanceof MapParameterBO) {
                paramsDTO.add(MapParameterTransform.bo2Dto((MapParameterBO) paramsBO.get(i)));
            } else {
                // Si il s'agit d'une liste, on rappelle la méthode pour transformer à son tour
                // tous ses éléments en DTO.
                if (paramsBO.get(i) instanceof ListParameterBO) {
                    paramsDTO.add(ListParameterTransform.bo2Dto((ListParameterBO) paramsBO.get(i)));
                } else {
                    if (paramsBO.get(i) instanceof StringParameterBO) {
                        paramsDTO.add(StringParameterTransform.bo2Dto((StringParameterBO) paramsBO.get(i)));
                    }
                }
            }
        }
        result.setParameters(paramsDTO);
        return result;
    }

}