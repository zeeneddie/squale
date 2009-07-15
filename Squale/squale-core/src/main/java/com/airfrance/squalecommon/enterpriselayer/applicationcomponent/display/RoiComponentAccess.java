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
package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.display;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.result.RoiDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.roi.RoiFacade;

/**
 * Classe permettant la gestion du ROI
 */
public class RoiComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Modifie la formule du ROI
     * 
     * @param pRoiDto le ROI
     * @param pErrors pour récupérer les erreurs
     * @return le ROI sous forme DTO modifié
     * @throws JrafEnterpriseException si erreur
     */
    public RoiDTO updateFormula( RoiDTO pRoiDto, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        return RoiFacade.updateFormula( pRoiDto, pErrors );
    }

    /**
     * Récupère les informations nécessaires au traitement du ROI
     * 
     * @param pApplicationId l'id de l'application dont on veut le ROI -1 si le veut pour toutes les applications
     * @return le roi sous forme DTO
     * @throws JrafEnterpriseException si erreur
     */
    public RoiDTO getROI( Long pApplicationId )
        throws JrafEnterpriseException
    {
        return RoiFacade.getROI( pApplicationId );
    }
}
