/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
