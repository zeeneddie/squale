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
package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de tâche
 */
public class TaskFactory
    extends FactoryAdapter
{

    /** Tâches */
    private Hashtable mTasks;

    /**
     * Constructeur
     */
    public TaskFactory()
    {
        mTasks = new Hashtable();
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes attributes )
        throws Exception
    {
        String name = attributes.getValue( "name" );
        TaskBO task = (TaskBO) mTasks.get( name );
        if ( task == null )
        {
            task = new TaskBO();
            task.setName( name );
            mTasks.put( name, task );
        }
        else
        {
            // Détection d'objet dupliqué
            throw new Exception( XmlConfigMessages.getString( "task.duplicate", new Object[] { name } ) );
        }
        return task;
    }

    /**
     * @return facteurs
     */
    public Hashtable getTasks()
    {
        return mTasks;
    }

}
