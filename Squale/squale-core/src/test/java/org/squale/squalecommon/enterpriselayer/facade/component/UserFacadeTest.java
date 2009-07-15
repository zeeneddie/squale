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
package org.squale.squalecommon.enterpriselayer.facade.component;

import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * Test de la facade User
 */
public class UserFacadeTest
    extends SqualeTestCase
{

    /**
     * provider de persistence
     */
    private static IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Test de getUser
     */
    public void testGetUser()
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            UserBO user = getComponentFactory().createUser( session );
            UserDTO dto = new UserDTO();
            dto.setID( user.getId() );
            UserDTO out = UserFacade.getUser( dto, Boolean.FALSE );
            assertNotNull( out );
            dto.setID( -1 );
            out = UserFacade.getUser( dto, Boolean.FALSE );
            assertNull( out );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
