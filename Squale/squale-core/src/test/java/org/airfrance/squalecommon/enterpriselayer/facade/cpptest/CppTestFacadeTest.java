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
package com.airfrance.squalecommon.enterpriselayer.facade.cpptest;

import java.io.InputStream;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.rulechecking.CppTestRuleSetDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest.CppTestRuleSetBO;

/**
 * Tests de la facade CppTest
 */
public class CppTestFacadeTest
    extends SqualeTestCase
{

    /**
     * Test d'importation de la facade
     */
    public void testImport()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/cpptest/cpptest.xml" );
        try
        {
            CppTestRuleSetDTO ruleset = CppTestFacade.importCppTestConfig( stream, errors );
            assertNotNull( ruleset );
            CppTestRuleSetDAOImpl dao = CppTestRuleSetDAOImpl.getInstance();
            assertEquals( 1, dao.count( getSession() ).intValue() );
            assertEquals( "valeur à vérifier dans le fichier", "default", ruleset.getName() );
            assertEquals( "valeur à vérifier dans le fichier", "builtin://MustHaveRules", ruleset.getCppTestName() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'obtention de toutes les configurations
     */
    public void testGetAll()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/cpptest/cpptest.xml" );
        try
        {
            // Précondition
            assertEquals( 0, CppTestFacade.getCppTestConfigurations().size() );
            // Chargement des données
            CppTestRuleSetDTO ruleset = CppTestFacade.importCppTestConfig( stream, errors );
            // Vérification de l'obtention des ruleset
            assertEquals( 1, CppTestFacade.getCppTestConfigurations().size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'obtention d'un ruleset
     */
    public void testGetCppTestConfiguration()
    {
        CppTestRuleSetBO ruleset = new CppTestRuleSetBO();
        ruleset.setName( "name" );
        try
        {
            getSession().beginTransaction();
            CppTestRuleSetDAOImpl.getInstance().create( getSession(), ruleset );
            getSession().commitTransactionWithoutClose();
            // Récupération du dto en nominal
            CppTestRuleSetDTO dto = CppTestFacade.getCppTestConfiguration( ruleset.getName() );
            assertNotNull( "ruleset existant dans la base", dto );
            assertEquals( "identité des champs", dto.getName(), ruleset.getName() );
            // Récuperation d'une config inexistante
            dto = CppTestFacade.getCppTestConfiguration( "unknown" );
            assertNull( "ruleset inexistant dans la base", dto );
        }
        catch ( JrafPersistenceException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
