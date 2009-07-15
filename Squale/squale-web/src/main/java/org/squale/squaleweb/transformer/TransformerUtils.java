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
package org.squale.squaleweb.transformer;

import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * Fonctions utilitaires pour les transformer
 */
public class TransformerUtils
{
    /**
     * Retourne le nom de l'application stockée en session.
     * 
     * @param pId l'id de l'application.
     * @param pApplications la liste des applications.
     * @return le nom de l'application.
     */
    public static String getApplicationName( long pId, final Collection pApplications )
    {
        Iterator it = pApplications.iterator();
        String name = null;
        ComponentDTO application = null;
        while ( it.hasNext() && null == name )
        {
            application = (ComponentDTO) it.next();
            if ( application.getID() == pId )
            {
                name = application.getName();
            }
        }
        return name;
    }

}
