package com.airfrance.squalecommon.datatransfertobject.transform.config.web;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.config.web.HomepageComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.UserTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.HomepageComponentBO;

/**
 * In this class you will found method for transform an HomepageComponent from BO to DTO and vice versa
 *
 */
public final class HomepageComponentTransform
{

    /**
     * Constructor
     */
    private HomepageComponentTransform()
    {

    }

    /**
     * Method for transform an HomepageComponent from BO to DTO
     * 
     * @param compoBO The HomepageComponentBO to transform
     * @return The HomepageComponentDTO
     */
    public static HomepageComponentDTO bo2dto( HomepageComponentBO compoBO )
    {
        HomepageComponentDTO compoDTO = new HomepageComponentDTO();
        compoDTO.setId( compoBO.getId() );
        compoDTO.setUser( UserTransform.bo2Dto( compoBO.getUser() ) );
        compoDTO.setComponentName( compoBO.getComponentName() );
        compoDTO.setComponentPosition( compoBO.getComponentPosition() );
        compoDTO.setComponentValue( compoBO.getComponentValue() );

        return compoDTO;
    }
    
    /**
     * Method for tranform a list of HomepageComponentBO to a list HomepageComponentDTO 
     * 
     * @param compoBOList The list of HomepageComponentBO to transform
     * @return The list of HomepageComponentDTO
     */
    public static List<HomepageComponentDTO> bo2dto( List<HomepageComponentBO> compoBOList )
    {
        List<HomepageComponentDTO> compoDTOList = new ArrayList<HomepageComponentDTO>();
        HomepageComponentDTO compoDTO;
        for (HomepageComponentBO compoBO : compoBOList)
        {
            compoDTO=bo2dto( compoBO );
            compoDTOList.add( compoDTO );
        }
        return compoDTOList;
    }
    
    /**
     * Method for transform an HomepageComponent from DTO to BO
     * 
     * @param compoDTO The HomepageComponentDTO to transform
     * @return The HomepageComponentBO
     */
    public static HomepageComponentBO dto2bo( HomepageComponentDTO compoDTO )
    {
        HomepageComponentBO compoBO = new HomepageComponentBO();
        compoBO.setId( compoDTO.getId() );
        compoBO.setUser( UserTransform.dto2Bo( compoDTO.getUser() ) );
        compoBO.setComponentName( compoDTO.getComponentName() );
        compoBO.setComponentPosition( compoDTO.getComponentPosition() );
        compoBO.setComponentValue( compoDTO.getComponentValue() );
        return compoBO;
    }

}
