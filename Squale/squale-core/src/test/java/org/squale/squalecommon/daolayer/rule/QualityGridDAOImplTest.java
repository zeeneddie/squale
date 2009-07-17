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
package org.squale.squalecommon.daolayer.rule;

import java.util.ArrayList;

import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test de la couche DAO pour la grille qualité
 */
public class QualityGridDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test de création de grille
     */
    public void testCreateGrid()
    {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl daoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName( "grid" );
            daoImpl.createGrid( session, grid );
            assertEquals( 1, daoImpl.count( session ).intValue() );
            daoImpl.remove( session, grid );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * Test du find
     */
    public void testFindWhereName()
    {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl daoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName( "grid" );
            daoImpl.createGrid( session, grid );
            assertEquals( grid.getId(), daoImpl.findWhereName( session, grid.getName() ).getId() );
            assertNull( daoImpl.findWhereName( session, "none" ) );
            daoImpl.remove( session, grid );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * Test de suppression de grille
     */
    public void testRemoveGrids()
    {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl daoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName( "grid" );
            daoImpl.createGrid( session, grid );
            assertEquals( 1, daoImpl.count( session ).intValue() );
            ArrayList l = new ArrayList();
            l.add( new Long( grid.getId() ) );
            daoImpl.removeGrids( session, l );
            assertEquals( 0, daoImpl.count( session ).intValue() );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }
}
