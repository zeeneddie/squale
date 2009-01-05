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
package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;

/**
 * @author M400841
 * @deprecated ComponentTransform doit etre utilisé a la place de ApplicationTransform
 */
public class ApplicationTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private ApplicationTransform()
    {
    }

    /**
     * ApplicationDTO -> ApplicationBO
     * 
     * @param pApplicationDTO ApplicationDTO à transformer
     * @return ApplicationBO
     */
    public static ApplicationBO dto2Bo( ComponentDTO pApplicationDTO )
    {

        // Initialisation du retour
        ApplicationBO applicationBO = new ApplicationBO();
        applicationBO.setId( pApplicationDTO.getID() );
        applicationBO.setName( pApplicationDTO.getName() );
        applicationBO.setExcludedFromActionPlan( pApplicationDTO.getExcludedFromActionPlan() );
        applicationBO.setJustification( pApplicationDTO.getJustification() );

        return applicationBO;
    }

    /**
     * ApplicationBO -> ApplicationDTO
     * 
     * @param pApplicationBO ApplicationBO
     * @return ApplicationDTO
     */
    public static ComponentDTO bo2Dto( ApplicationBO pApplicationBO )
    {

        // Initialisation du retour
        ComponentDTO applicationDTO = new ComponentDTO();
        applicationDTO.setID( pApplicationBO.getId() );
        applicationDTO.setName( pApplicationBO.getName() );
        applicationDTO.setType( ComponentType.APPLICATION );
        pApplicationBO.setExcludedFromActionPlan( applicationDTO.getExcludedFromActionPlan() );
        pApplicationBO.setJustification( applicationDTO.getJustification() );

        return applicationDTO;
    }
}
