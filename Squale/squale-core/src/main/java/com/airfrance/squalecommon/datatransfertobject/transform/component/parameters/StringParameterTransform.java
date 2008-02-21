package com.airfrance.squalecommon.datatransfertobject.transform.component.parameters;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 */
public class StringParameterTransform {

    /**
     * 
     * @param pStringParameterDTO le DTO à transformer en BO
     * @return le BO
     */
    public static StringParameterBO dto2Bo(StringParameterDTO pStringParameterDTO) {
        StringParameterBO result = new StringParameterBO();
        result.setId(pStringParameterDTO.getId());
        result.setValue(pStringParameterDTO.getValue());
        return result;
    }

   /**
    * 
    * @param pStringParameterBO le BO à transformer en DTO
    * @return le DTO
    */
    public static StringParameterDTO bo2Dto(StringParameterBO pStringParameterBO) {
        StringParameterDTO result = new StringParameterDTO();
        result.setId(pStringParameterBO.getId());
        result.setValue(pStringParameterBO.getValue());
        return result; 

    }

}