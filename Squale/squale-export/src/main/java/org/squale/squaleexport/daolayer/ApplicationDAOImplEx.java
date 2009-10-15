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
package org.squale.squaleexport.daolayer;

import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * This class is an implementation of the abstract class {@link AbstractDAOImpl}. This class is a dao linked to the
 * business object {@link ApplicationBO } specific to SqualeExport
 */
public final class ApplicationDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * Singleton instance of ApplicationDAOImplEx
     */
    private static ApplicationDAOImplEx instance;

    /**
     * Private constructor
     */
    private ApplicationDAOImplEx()
    {
        initialize( ApplicationBO.class );
    }

    /**
     * Return the singleton. Create the instance if it not already exist.
     * 
     * @return The instance of the ApplicationDAOImplEx
     */
    public static ApplicationDAOImplEx getInstance()
    {
        if ( instance == null )
        {
            instance = new ApplicationDAOImplEx();
        }
        return instance;
    }

}
