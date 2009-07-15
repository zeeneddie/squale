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
package com.airfrance.squalecommon.datatransfertobject.transform.config;

import com.airfrance.squalecommon.datatransfertobject.config.TaskParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO;

/**
 * Conversion des paramètres de tâche
 */
public class TaskParameterTransform
{

    /**
     * Transformation BO - DTO
     * 
     * @param pTaskParameter paramètre de tâche
     * @return paramètre converti
     */
    static public TaskParameterDTO bo2dto( TaskParameterBO pTaskParameter )
    {
        TaskParameterDTO result = new TaskParameterDTO();
        result.setName( pTaskParameter.getName() );
        result.setValue( pTaskParameter.getValue() );
        return result;
    }

    // Le dto2bo est inutile car on ne modifie pas les paramètres dans le web
}
