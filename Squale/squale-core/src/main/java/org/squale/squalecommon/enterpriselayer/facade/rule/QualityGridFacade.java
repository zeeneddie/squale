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
package org.squale.squalecommon.enterpriselayer.facade.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.component.AuditGridDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.rule.QualityGridDAOImpl;
import org.squale.squalecommon.daolayer.rule.QualityRuleDAOImpl;
import org.squale.squalecommon.datatransfertobject.rule.CriteriumRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import org.squale.squalecommon.datatransfertobject.transform.rule.GridMetricsTransform;
import org.squale.squalecommon.datatransfertobject.transform.rule.QualityGridTransform;
import org.squale.squalecommon.datatransfertobject.transform.rule.QualityRuleTransform;
import org.squale.squalecommon.datatransfertobject.transform.rule.RuleMetricsTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;

/**
 * Facade pour la grille qualité
 */
public final class QualityGridFacade
{
    /**
     * Private constructor
     */
    private QualityGridFacade()
    {

    }

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Obtention des grilles
     * 
     * @param pLastVersions indique si seulement les dernières versions sont récupérées
     * @return grilles qualité
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getGrids( boolean pLastVersions )
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList(); // retour de la facade

        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            // Obtention de toutes les grilles
            Collection grids = qualityGridDAO.findGrids( session, pLastVersions );
            Iterator it = grids.iterator();
            // transformation de BO en DTO
            while ( it.hasNext() )
            {
                QualityGridDTO userDTO = QualityGridTransform.bo2Dto( (QualityGridBO) it.next() );
                result.add( userDTO );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".getGrids" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".getGrids" );
        }

        return result;
    }

    /**
     * Obtention des grilles sans profil ni audit
     * 
     * @return grilles qualité sans profil ni audit
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getUnlinkedGrids()
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList(); // retour de la facade

        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            AuditGridDAOImpl auditGridDAO = AuditGridDAOImpl.getInstance();
            // Obtention de toutes les dernières version des grilles sans profil
            Collection grids = qualityGridDAO.findGridsWithoutProfiles( session );
            Iterator it = grids.iterator();
            // transformation de BO en DTO
            QualityGridBO curGrid = null;
            while ( it.hasNext() )
            {
                curGrid = (QualityGridBO) it.next();
                // On ajoute le nom de la grille seulement si elle est inutilisée (sans audits)
                if ( !auditGridDAO.isGridUsed( session, new Long( curGrid.getId() ) ) )
                {
                    result.add( QualityGridTransform.bo2DtoLight( curGrid ) );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".getUnlinkedGrids" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".getUnlinkedGrids" );
        }

        return result;
    }

    /**
     * Permet de récupérer une grille par son identifiant
     * 
     * @param pGridId l'identifiant
     * @return la grille
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static QualityGridDTO loadGridById( Long pGridId )
        throws JrafEnterpriseException
    {
        QualityGridDTO result = null;
        QualityGridBO bo = null;
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            if ( pGridId != null )
            {
                bo = qualityGridDAO.loadById( session, pGridId.longValue() );
            }
            if ( bo != null )
            {
                result = QualityGridTransform.bo2Dto( bo );
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

    /**
     * update une grille en base
     * 
     * @param pGrid la grille
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static void updateGrid( QualityGridConfDTO pGrid )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            QualityGridBO bo = QualityGridTransform.dto2Bo( pGrid );
            qualityGridDAO.save( session, bo );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".updateGrid" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".updateGrid" );
        }
    }

    /**
     * Obtention d'une grille
     * 
     * @param pQualityGrid grille qualité
     * @return grille correspondante
     * @throws JrafEnterpriseException si erreur
     */
    public static QualityGridConfDTO get( QualityGridDTO pQualityGrid )
        throws JrafEnterpriseException
    {
        QualityGridConfDTO result = new QualityGridConfDTO();
        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            // Obtention de la grille
            QualityGridBO grid = (QualityGridBO) qualityGridDAO.load( session, new Long( pQualityGrid.getId() ) );
            QualityGridTransform.bo2Dto( grid, result );
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

    /**
     * Obtention d'une règle qualité
     * 
     * @param pQualityRule règle qualité
     * @param deepTransformation un booléen indiquant si on veut aussi transformer les formules ou pas.
     * @return règle qualité
     * @throws JrafEnterpriseException si erreur
     */
    public static QualityRuleDTO getQualityRule( QualityRuleDTO pQualityRule, boolean deepTransformation )
        throws JrafEnterpriseException
    {
        QualityRuleDTO result = null;
        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            QualityRuleDAOImpl factorRuleDAO = QualityRuleDAOImpl.getInstance();
            // Obtention de la règle qualité
            QualityRuleBO qualityRule = (QualityRuleBO) factorRuleDAO.get( session, new Long( pQualityRule.getId() ) );
            result = QualityRuleTransform.bo2Dto( qualityRule, deepTransformation );
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

    /**
     * Obtention d'une règle qualité et des mesures utilisées
     * 
     * @param pQualityRule règle qualité
     * @param deepTransformation un booléen indiquant si on veut aussi transformer les formules ou pas.
     * @return un tableau à deux entrées contenant la règle qualité et ses mesures extraites
     * @throws JrafEnterpriseException si erreur
     */
    public static Object[] getQualityRuleAndUsedTres( QualityRuleDTO pQualityRule, boolean deepTransformation )
        throws JrafEnterpriseException
    {
        Object[] result = new Object[2];
        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            QualityRuleDAOImpl factorRuleDAO = QualityRuleDAOImpl.getInstance();
            // Obtention de la règle qualité
            QualityRuleBO qualityRule = (QualityRuleBO) factorRuleDAO.get( session, new Long( pQualityRule.getId() ) );
            result[0] = QualityRuleTransform.bo2Dto( qualityRule, deepTransformation );
            result[1] = qualityRule.accept( new RuleMetricsTransform( new FormulaMeasureExtractor() ), null );
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

    /**
     * Destruction de grilles qualité
     * 
     * @param pGrids grilles
     * @return grilles ne pouvant pas être supprimées
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection deleteGrids( Collection pGrids )
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection result = new ArrayList();
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // Parcours des grilles à détruire
            Iterator gridsIt = pGrids.iterator();
            ArrayList gridsId = new ArrayList();
            AuditGridDAOImpl auditGridDAO = AuditGridDAOImpl.getInstance();
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();

            while ( gridsIt.hasNext() )
            {
                QualityGridDTO gridDTO = (QualityGridDTO) gridsIt.next();
                Long gridId = new Long( gridDTO.getId() );
                // On vérifie que la grille n'est pas utilisée
                // au niveau d''un projet ou au niveau d'un audit réalisé
                if ( projectDAO.isGridUsed( session, gridId ) || auditGridDAO.isGridUsed( session, gridId )
                    || qualityGridDAO.hasProfile( session, gridId ) )
                {
                    result.add( gridDTO );
                }
                else
                {
                    gridsId.add( gridId );
                }
            }
            // Destruction des grilles qui ne sont plus référencées
            if ( gridsId.size() > 0 )
            {
                qualityGridDAO.removeGrids( session, gridsId );
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

    /**
     * Obtention des métriques d'une grille Cette méthode est utilsiée pour la construction du menu Top
     * 
     * @param pQualityGrid grille qualité
     * @return map donnant pour chaque type de composant les métriques utilisées par la grille qualité, une map vide est
     *         renvoyée si la grille n'existe pas
     * @throws JrafEnterpriseException si erreur
     */
    public static Map getGridMetrics( QualityGridDTO pQualityGrid )
        throws JrafEnterpriseException
    {
        HashMap result = new HashMap();
        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            // Obtention de la grille
            QualityGridBO grid = (QualityGridBO) qualityGridDAO.load( session, new Long( pQualityGrid.getId() ) );
            if ( grid != null )
            {
                // Conversion de la map
                GridMetricsTransform.bo2dto( grid, result, new FormulaMeasureExtractor() );
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

    /**
     * update une grille en base
     * 
     * @param pGrid la grille
     * @param pErrors erreur rencontrées
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static void updateGrid( QualityGridConfDTO pGrid, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl qualityGridDAO = QualityGridDAOImpl.getInstance();
            /* On modifie la grille à partir du DTO */
            // On update les facteurs
            SortedSet updatedFactors = new TreeSet();
            Collection factors = pGrid.getFactors();
            for ( Iterator it = factors.iterator(); it.hasNext(); )
            {
                FactorRuleDTO factorDTO = (FactorRuleDTO) it.next();
                updatedFactors.add( updateFactor( session, factorDTO ) );
            }
            // On charge la grille
            QualityGridBO bo = (QualityGridBO) qualityGridDAO.get( session, new Long( pGrid.getId() ) );
            // Transformation
            bo.setFactors( updatedFactors );
            // Vérification de la grille
            QualityGridChecker gridChecker = new QualityGridChecker();
            gridChecker.checkGrid( bo, pErrors );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".updateGrid" );
        }
        finally
        {
            if ( pErrors.length() > 0 && session != null )
            {
                // On ne met pas à jour
                session.rollbackTransaction();
            }
            else
            {
                FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".updateGrid" );
            }
        }
    }

    /**
     * update d'un facteur
     * 
     * @param session la session
     * @param factorDTO le facteur
     * @return le facteur mis à jour
     * @throws JrafEnterpriseException en cas d'échec
     * @throws JrafDaoException erreur dao
     */
    private static FactorRuleBO updateFactor( ISession session, FactorRuleDTO factorDTO )
        throws JrafEnterpriseException, JrafDaoException
    {
        FactorRuleBO bo = null;
        QualityRuleDAOImpl qualityRuleDAO = QualityRuleDAOImpl.getInstance();
        // On update les critères
        SortedMap updatedCriteria = new TreeMap();
        Collection criteria = factorDTO.getCriteria().keySet();
        for ( Iterator it = criteria.iterator(); it.hasNext(); )
        {
            CriteriumRuleDTO criteriaDTO = (CriteriumRuleDTO) it.next();
            updatedCriteria.put( updateCriterium( session, criteriaDTO ),
                                 (Float) factorDTO.getCriteria().get( criteriaDTO ) );
        }
        // On charge le facteur
        bo = (FactorRuleBO) qualityRuleDAO.get( session, new Long( factorDTO.getId() ) );
        /* On modifie le facteur */
        bo.setCriteria( updatedCriteria );
        return bo;
    }

    /**
     * update d'un critère
     * 
     * @param session la session
     * @param criteriaDTO le critère
     * @return le critère mis à jour
     * @throws JrafEnterpriseException en cas d'échec
     * @throws JrafDaoException erreur dao
     */
    private static CriteriumRuleBO updateCriterium( ISession session, CriteriumRuleDTO criteriaDTO )
        throws JrafEnterpriseException, JrafDaoException
    {
        CriteriumRuleBO bo = null;
        QualityRuleDAOImpl qualityRuleDAO = QualityRuleDAOImpl.getInstance();
        // On charge le critère
        bo = (CriteriumRuleBO) qualityRuleDAO.get( session, new Long( criteriaDTO.getId() ) );
        // On update les pratiques
        SortedMap updatedPractices = new TreeMap();
        Collection practices = criteriaDTO.getPractices().keySet();
        for ( Iterator it = practices.iterator(); it.hasNext(); )
        {
            PracticeRuleDTO practiceDTO = (PracticeRuleDTO) it.next();
            updatedPractices.put( updatePractice( session, practiceDTO ),
                                  (Float) criteriaDTO.getPractices().get( practiceDTO ) );
        }
        /* On modifie le critère à partir du DTO */
        bo.setPractices( updatedPractices );
        return bo;
    }

    /**
     * update d'une pratique
     * 
     * @param practiceDTO la pratique
     * @param session la session
     * @return la pratique mise à jour
     * @throws JrafEnterpriseException en cas d'échec
     * @throws JrafDaoException erreur dao
     */
    private static PracticeRuleBO updatePractice( ISession session, PracticeRuleDTO practiceDTO )
        throws JrafEnterpriseException, JrafDaoException
    {
        // Initialisation
        PracticeRuleBO bo = null;
        QualityRuleDAOImpl qualityRuleDAO = QualityRuleDAOImpl.getInstance();
        // On charge la pratique
        bo = (PracticeRuleBO) qualityRuleDAO.get( session, new Long( practiceDTO.getId() ) );
        // Transformation
        QualityRuleTransform.dto2Bo( bo, practiceDTO );
        return bo;
    }

    /**
     * This method return the list of manual practices rules of the grid linked to the project
     * 
     * @param projectId Id of the project
     * @return A list of manual practices rules
     * @throws JrafEnterpriseException exception happen during the search in the database
     */
    public static HashSet<PracticeRuleDTO> loadManualPracticesRulesByProjectId( long projectId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        QualityGridDTO dto = null;
        HashSet<PracticeRuleDTO> practicesRuleList = new HashSet<PracticeRuleDTO>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            ProjectBO project = (ProjectBO) projectDAO.get( session, projectId );
            Iterator<FactorRuleBO> factorsIt = project.getQualityGrid().getFactors().iterator();
            // For each factor 
            while ( factorsIt.hasNext() )
            {
                FactorRuleBO factor = factorsIt.next();
                Iterator<CriteriumRuleBO> criteriumIt = factor.getCriteria().keySet().iterator();
                //For each criterium
                while ( criteriumIt.hasNext() )
                {
                    CriteriumRuleBO criterium = criteriumIt.next();
                    Iterator<PracticeRuleBO> practiceIt = criterium.getPractices().keySet().iterator();
                    // For each practice
                    while ( practiceIt.hasNext() )
                    {
                        PracticeRuleBO practiceBO = practiceIt.next();

                        // If the practices is a manual practices
                        if ( practiceBO.getFormula() == null )
                        {
                            PracticeRuleDTO practiceDTO = new PracticeRuleDTO();
                            //We add the practice to the list
                            practicesRuleList.add( (PracticeRuleDTO) QualityRuleTransform.bo2Dto( practiceBO, true ) );
                        }
                    }
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".loadPracticeByProjectId" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".loadPracticeByProjectId" );
        }

        return practicesRuleList;
    }

}
