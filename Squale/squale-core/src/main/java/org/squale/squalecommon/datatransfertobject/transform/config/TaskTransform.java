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

import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;

/**
 * Transformation d'une tâche Squalix
 */
public class TaskTransform
{

    /**
     * Convertit un TaskBO en TaskDTO
     * 
     * @param pTaskRefBO objet à convertir
     * @return résultat de la conversion
     */
    public static TaskDTO bo2dto( TaskRefBO pTaskRefBO )
    {
        TaskDTO result = new TaskDTO();
        result.setId( pTaskRefBO.getId() );
        result.setClassName( pTaskRefBO.getTask().getClassName() );
        result.setName( pTaskRefBO.getTask().getName() );
        result.setConfigurable( pTaskRefBO.getTask().isConfigurable() );
        result.setStandard( pTaskRefBO.getTask().isStandard() );
        result.setMandatory( pTaskRefBO.getTask().isMandatory() );
        // Conversion des paramètres
        Iterator it = pTaskRefBO.getParameters().iterator();
        while ( it.hasNext() )
        {
            result.addParameter( TaskParameterTransform.bo2dto( (TaskParameterBO) it.next() ) );
        }
        return result;
    }

    /**
     * Convertit un TaskDTO en TaskRefBO
     * 
     * @param pTaskDTO objet à convertir
     * @return résultat de la conversion
     */
    public static Object dto2bo( TaskDTO pTaskDTO )
    {
        TaskRefBO result = new TaskRefBO();
        TaskBO task = new TaskBO();
        task.setId( pTaskDTO.getId() );
        task.setClassName( pTaskDTO.getClassName() );
        task.setName( pTaskDTO.getName() );
        task.setConfigurable( pTaskDTO.isConfigurable() );
        task.setMandatory( pTaskDTO.isMandatory() );
        result.setTask( task );
        return result;
    }

}
