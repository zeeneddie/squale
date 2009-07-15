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
package org.squale.squalecommon.datatransfertobject.transform.config;

import org.squale.squalecommon.datatransfertobject.config.ServeurDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * Transform d'un Serveur d'exécution Squalix
 */
public class ServeurTransform
{

    /**
     * Convertit un ServeurBO en ServeurDTO
     * 
     * @param pServeurBO objet à convertir
     * @return résultat de la conversion
     */
    public static ServeurDTO bo2dto( ServeurBO pServeurBO )
    {
        ServeurDTO lServeurDTO = new ServeurDTO();
        lServeurDTO.setServeurId( pServeurBO.getServeurId() );
        lServeurDTO.setName( pServeurBO.getName() );
        return lServeurDTO;
    }

    /**
     * Convertit un ServeurDTO en ServeurBO
     * 
     * @param pServeurDTO objet à convertir
     * @return résultat de la conversion
     */
    public static Object dto2bo( ServeurDTO pServeurDTO )
    {
        ServeurBO lServeurBO = new ServeurBO();
        lServeurBO.setServeurId( pServeurDTO.getServeurId() );
        lServeurBO.setName( pServeurDTO.getName() );
        return lServeurBO;
    }

}
