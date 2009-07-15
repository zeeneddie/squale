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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import org.squale.squalecommon.enterpriselayer.facade.component.ServeurFacade;

import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.commons.exception.JrafEnterpriseException;

import java.util.Collection;

/**
 * ComponentApplication du Serveur d'exécution de Squalix
 */
public class ServeurComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Retourne la liste des serveurs
     * 
     * @return la liste des serveurs
     * @throws JrafEnterpriseException si une erreur survient
     */
    public Collection listeServeurs()
        throws JrafEnterpriseException
    {
        Collection lListeServeurs = ServeurFacade.listeServeurs();
        return lListeServeurs;
    }

}
