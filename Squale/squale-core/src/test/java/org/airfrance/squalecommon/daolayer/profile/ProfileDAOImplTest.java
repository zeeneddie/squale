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
package com.airfrance.squalecommon.daolayer.profile;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * @author M400843 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProfileDAOImplTest
    extends SqualeTestCase
{
    /** provider de persistence */
    private static IPersistenceProvider PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * @see ProfileDAOImpl#loadByKey(ISession, String)
     */
    public void testloadByKey()
    {
        try
        {
            ISession session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            UserBO user = getComponentFactory().createUser( session );
            String existant_key = ProfileBO.ADMIN_PROFILE_NAME;
            String nonExistant_key = "je_n_existe_pas";
            ProfileDAOImpl profileDAO = ProfileDAOImpl.getInstance();

            assertNotNull( profileDAO.loadByKey( session, existant_key ) );
            assertNull( profileDAO.loadByKey( session, nonExistant_key ) );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
