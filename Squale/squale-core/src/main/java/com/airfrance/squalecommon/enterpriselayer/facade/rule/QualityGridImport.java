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
 * Importation de grille qualité
 */
public class QualityGridImport
{
    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Importation d'une grille sans création dans la base
     * 
     * @param pStream flux de grille
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return collection de grilles importées sous la forme de QualityGridDTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection importGrid( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        GridImport gridImport = new GridImport();
        // Importation des grilles
        Collection grids = gridImport.importGrid( pStream, pErrors );
        // Conversion de chacune des grilles
        // et vérification de leur existence
        ArrayList result = new ArrayList();
        Iterator gridsIt = grids.iterator();
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // Vérification de la grille
            QualityGridChecker gridChecker = new QualityGridChecker();
            // Parcours des grilles
            while ( gridsIt.hasNext() )
            {
                QualityGridBO gridBO = (QualityGridBO) gridsIt.next();
                // Vérification de la grille
                gridChecker.checkGrid( gridBO, pErrors );
                QualityGridDTO gridDTO = QualityGridTransform.bo2Dto( gridBO );
                // Affectation de l'ID dans le cas d'une grille déjà présente dans
                // la base de données
                QualityGridBO existingGrid = QualityGridDAOImpl.getInstance().findWhereName( session, gridBO.getName() );
                if ( existingGrid != null )
                {
                    gridDTO.setId( existingGrid.getId() ); // Cette information sera exploitée par la partie WEB
                }
                result.add( gridDTO );
            }
        }
        catch ( JrafDaoException e )
        {
            // Renvoi d'une exception
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".get" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".get" );
        }
        return result;
    }

    /**
     * Importation d'une grille et création dans la base
     * 
     * @param pStream flux de grille
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return collection de grilles importées sous la forme de QualityGridDTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection createGrid( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        GridImport gridImport = new GridImport();
        // Import grids
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
                // Mise à jour des profils utilisant la même grille qualité
                // Transformation en DTO
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
