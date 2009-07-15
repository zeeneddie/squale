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
package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import java.io.IOException;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * @author S Porto Rico
 */
public class CheckstyleTransform
{

    /**
     * Constructeur prive
     */
    private CheckstyleTransform()
    {

    }

    /**
     * DTO -> BO pour une Version
     * 
     * @param pVersionDTO VersionDTO à transformer
     * @return VersionBO
     * @throws IOException si le fichier n'est pas disponible
     */
    public static CheckstyleRuleSetBO dto2Bo( CheckstyleDTO pVersionDTO )
        throws IOException
    {

        CheckstyleRuleSetBO versionBO = new CheckstyleRuleSetBO();

        // Initialisation des champs de VersionBO
        RuleSetTransform.dto2Bo( pVersionDTO, versionBO );
        versionBO.setValue( pVersionDTO.getBytes() );
        return versionBO;
    }

    /**
     * BO -> DTO pour une Version
     * 
     * @param pVersionBO VersionBO
     * @return VersionDTO
     */
    public static CheckstyleDTO bo2Dto( CheckstyleRuleSetBO pVersionBO )
    {

        // Initialisation
        CheckstyleDTO versionDTO = new CheckstyleDTO();

        // Initialisation des champs de VersionBO
        RuleSetTransform.bo2Dto( pVersionBO, versionDTO );
        versionDTO.setBytes( pVersionBO.getValue() );

        return versionDTO;
    }
}
