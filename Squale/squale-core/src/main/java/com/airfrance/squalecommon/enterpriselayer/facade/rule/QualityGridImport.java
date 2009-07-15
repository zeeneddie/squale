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
package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.rule.QualityGridDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityGridTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.xml.GridImport;

/**
 * Quality grid import
 */
public final class QualityGridImport
{

    /**
     * Private default constructor
     */
    private QualityGridImport()
    {

    }

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Import of a grid without database saving. Test if a grid with the same name already exist in the database
     * 
     * @param pStream Grid stream
     * @param pErrors Buffer for recover the error which could happen
     * @return A collection grids import as object of type QualityGridDTO
     * @throws JrafEnterpriseException Error during the parse of the quality grid file
     */
    public static Collection importGrid( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        GridImport gridImport = new GridImport();
        // Import of the grid
        Collection grids = gridImport.importGrid( pStream, pErrors );

        // Conversion of the gris from bo to dto and test the existence of the grid in the database
        ArrayList result = new ArrayList();
        Iterator gridsIt = grids.iterator();
        ISession session = null;
        try
        {
            // Recover the hibernate session
            session = PERSISTENTPROVIDER.getSession();
            // Instanciation of the grid checker
            QualityGridChecker gridChecker = new QualityGridChecker();
            // Browse of the grid
            while ( gridsIt.hasNext() )
            {
                QualityGridBO gridBO = (QualityGridBO) gridsIt.next();
                // Check the grid
                gridChecker.checkGrid( gridBO, pErrors );
                QualityGridDTO gridDTO = QualityGridTransform.bo2Dto( gridBO );
                // Assignment of the id if the grid already exist in the database
                QualityGridBO existingGrid = QualityGridDAOImpl.getInstance().findWhereName( session, gridBO.getName() );
                if ( existingGrid != null )
                {
                    gridDTO.setId( existingGrid.getId() ); // Information used in the web portal
                }
                result.add( gridDTO );
            }
        }
        catch ( JrafDaoException e )
        {
            // return an exception
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".get" );
        }
        finally
        {
            // close thesession
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".get" );
        }
        return result;
    }

    /**
     * Import of a gris and save it in the database
     * 
     * @param pStream grid stream
     * @param pErrors buffer for recover the error which could happen during the parse
     * @return collection de grilles importées sous la forme de QualityGridDTO
     * @throws JrafEnterpriseException Error happen during the parse of the grid file or the record in the database
     */
    public static Collection createGrid( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        GridImport gridImport = new GridImport();
        // Import of the grids
        Collection grids = gridImport.importGrid( pStream, pErrors );
        // Transformation of grids
        // and existing verification
        ArrayList result = new ArrayList();
        Iterator gridsIt = grids.iterator();
        ISession session = null;
        try
        {
            // Getting session
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl gridDAO = QualityGridDAOImpl.getInstance();
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
            // Iterate grids
            while ( gridsIt.hasNext() )
            {
                QualityGridBO gridBO = (QualityGridBO) gridsIt.next();
                // Get old grid to replace profiles links
                QualityGridBO oldGridBO = gridDAO.findWhereName( session, gridBO.getName() );
                // creation
                gridBO = gridDAO.createGrid( session, gridBO );
                if ( null != oldGridBO )
                {
                    // Update profiles linked to this quality grid
                    for ( Iterator itProfiles = oldGridBO.getProfiles().iterator(); itProfiles.hasNext(); )
                    {
                        ProjectProfileBO profileBO = (ProjectProfileBO) itProfiles.next();
                        ArrayList gridsProfile = new ArrayList( profileBO.getGrids() );
                        int gridIndex = gridsProfile.indexOf( oldGridBO );
                        if ( gridIndex >= 0 )
                        { // defensive code
                            gridsProfile.remove( gridIndex );
                            gridsProfile.add( gridBO );
                            // save profile with new grids
                            profileBO.setGrids( new HashSet( gridsProfile ) );
                            profileDAO.save( session, profileBO );
                        }
                    }
                }

                // Update projects which used the same quality grid
                projectDAO.updateQualityGrid( session, gridBO );               
                // Transformation in DTO
                QualityGridDTO gridDTO = QualityGridTransform.bo2Dto( gridBO );
                result.add( gridDTO );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".get" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".get" );
        }
        return result;
    }
}
