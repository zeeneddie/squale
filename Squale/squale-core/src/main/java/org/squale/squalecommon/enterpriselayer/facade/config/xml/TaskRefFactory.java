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
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de référence de pratique
 */
class TaskRefFactory
    extends FactoryAdapter
{

    /** Tâches */
    private Hashtable mTasks;

    /**
     * Constructeur
     * 
     * @param pTasks tâches existantes
     */
    public TaskRefFactory( Hashtable pTasks )
    {
        mTasks = pTasks;
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
            // Détection d'objet inexistant
            throw new Exception( XmlConfigMessages.getString( "task.unknown", new Object[] { name } ) );
        }
        TaskRefBO ref = new TaskRefBO();
        ref.setTask( task );
        return ref;
    }

}