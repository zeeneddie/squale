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

import java.util.List;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;

/**
 * @author E6400802 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CheckstyleAdminComponentAccessTest
    extends SqualeTestCase
{

    /**
     * Teste la construction de RulesChecking component par AccessDelegateHelpe
     */
    public void testRulesCheckingAdminComponentAccess()
    {

        IApplicationComponent appComponent;
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "CheckstyleAdmin" );
            assertNotNull( appComponent );

        }
        catch ( Exception e )
        {

            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

    /**
     * Teste l'accès à la méthode GetAllVersions et son resultat
     */
    public void testGetAllVersions()
    {

        IApplicationComponent appComponent;
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "CheckstyleAdmin" );
            // appel à la methode getAllVersions
            List version = (List) appComponent.execute( "getAllVersions", null );
            assertTrue( "nb version", version.size() == 0 );

        }
        catch ( Exception e )
        {

            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

    /**
     * @throws JrafEnterpriseException
     */

    public void testParseFile()
    {
        IApplicationComponent appComponent;
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "CheckstyleAdmin" );

            StringBuffer errors = new StringBuffer();
            Object[] paramIn = new Object[2];
            CheckstyleDTO version = new CheckstyleDTO();
            version.setBytes( "n'import quoi".getBytes() );
            paramIn[0] = version;
            paramIn[1] = errors;
            // appel à la methode getAllVersions
            CheckstyleDTO result = (CheckstyleDTO) appComponent.execute( "parseFile", paramIn );

            assertTrue( "Errors", errors.length() > 0 );

        }
        catch ( Exception e )
        {

            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

}
