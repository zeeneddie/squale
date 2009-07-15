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

import java.util.Collection;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.enterpriselayer.facade.component.TaskFacade;

/**
 */
public class TaskAdministrationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * @param pProjectId l'id du projet
     * @return la liste des taches du projet dans leur ordre d'exécution
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Collection getAllTasks( Long pProjectId )
        throws JrafEnterpriseException
    {
        return TaskFacade.getAllTasks( pProjectId );
    }
}
