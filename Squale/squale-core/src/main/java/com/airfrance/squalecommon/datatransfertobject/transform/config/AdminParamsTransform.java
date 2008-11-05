package com.airfrance.squalecommon.datatransfertobject.transform.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.AdminParamsDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;

/**
 * Transform an adminParams object from BO to DTO and vice versa
 */
public final class AdminParamsTransform
{

    /**
     * Default constructor set as private
     */
    private AdminParamsTransform()
    {

    }

    /**
     * Convert an adminParamsBO to an adminParamsDTO
     * 
     * @param adminParam The adminParams as BO to convert
     * @return The adminParams as DTO
     */
    public static AdminParamsDTO bo2dto( AdminParamsBO adminParam )
    {
        AdminParamsDTO dto = new AdminParamsDTO();
        dto.setParamKey( adminParam.getParamKey() );
        dto.setParamValue( adminParam.getParamValue() );
        return dto;
    }

    /**
     * Convert a collection of adminParamsBO to a collection of adminParamsDTO
     * 
     * @param adminParams The collection of adminParamsBO to convert
     * @return The collection of adminParamsDTO
     */
    public static Collection<AdminParamsDTO> bo2dto( Collection<AdminParamsBO> adminParams )
    {
        Collection<AdminParamsDTO> collectionsDTO = new ArrayList<AdminParamsDTO>();
        AdminParamsDTO dto;
        AdminParamsBO bo;
        Iterator<AdminParamsBO> it = adminParams.iterator();
        while ( it.hasNext() )
        {
            bo = it.next();
            dto = bo2dto( bo );
            collectionsDTO.add( dto );
        }
        return collectionsDTO;
    }

    /**
     * Convert an adminparamsDTO to an adminParamsBO
     * 
     * @param adminParam The adminParams as DTO to convert
     * @return the adminParams as BO
     */
    public static AdminParamsBO dto2bo( AdminParamsDTO adminParam )
    {
        AdminParamsBO bo = new AdminParamsBO();
        bo.setParamKey( adminParam.getParamKey() );
        bo.setParamValue( adminParam.getParamValue() );
        return bo;
    }

}
