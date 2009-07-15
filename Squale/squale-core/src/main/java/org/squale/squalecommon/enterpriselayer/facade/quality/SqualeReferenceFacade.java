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
package org.squale.squalecommon.enterpriselayer.facade.quality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.component.ApplicationDAOImpl;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.daolayer.result.QualityResultDAOImpl;
import org.squale.squalecommon.daolayer.result.SqualeReferenceDAOImpl;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.result.SqualeReferenceDTO;
import org.squale.squalecommon.datatransfertobject.transform.result.SqualeReferenceTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.SqualeReferenceBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAProjectMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMProjectMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * 
 */
public class SqualeReferenceFacade
    implements IFacade
{

    /**
     * provider de persistance
     */
    private static final IPersistenceProvider PERSISTENCEPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Permet d'insérer un audit et ses résultats dans le référentiel
     * 
     * @dev-squale entrée des résultats de projets dans le référentiel
     * @param pAudit AuditDTO contenant l'ID de l'audit
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB502B9
     */
    public static void insertAudit( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        insertAudit( pAudit, null );
    }

    /**
     * Permet d'insérer un audit et ses résultats dans le référentiel
     * 
     * @dev-squale entrée des résultats de projets dans le référentiel
     * @param pAudit AuditDTO dont les champs sont à jour
     * @param pSession session Hibernate
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB502C3
     */
    public static void insertAudit( AuditDTO pAudit, ISession pSession )
        throws JrafEnterpriseException
    {

        // Initialisation
        ISession session = pSession; // session Hibernate

        Long auditId = new Long( pAudit.getID() ); // identifiant de l'audit
        Collection projectBOs = null; // collection de projets relatifs a un audit

        try
        {

            // Initialisation des Daos
            ProjectDAOImpl projectDao = ProjectDAOImpl.getInstance();
            QualityResultDAOImpl qualityResultDao = QualityResultDAOImpl.getInstance();
            SqualeReferenceDAOImpl squaleReferenceDAO = SqualeReferenceDAOImpl.getInstance();
            AuditDAOImpl auditDAOImpl = AuditDAOImpl.getInstance();
            AuditBO auditBO = (AuditBO) auditDAOImpl.get( pSession, auditId );
            if ( null == session )
            {
                // récupération d'une session si celle-ci n'est pas déjà initialisée
                session = PERSISTENCEPROVIDER.getSession();
            }

            // récupération des projets liés à l'audit
            projectBOs = (Collection) projectDao.findWhere( session, auditId );

            Iterator projectIterator = projectBOs.iterator();
            ProjectBO currentProject = null;
            SqualeReferenceBO currentSqualeReference = null;
            Long projectId = null;
            // Pour chaque projet :
            while ( projectIterator.hasNext() )
            {

                // Recuperation du projet courant
                currentProject = (ProjectBO) projectIterator.next();
                projectId = new Long( currentProject.getId() );

                // Recupere l'ancienne reference
                currentSqualeReference =
                    SqualeReferenceDAOImpl.getInstance().loadByName( session, currentProject.getParent().getName(),
                                                                     currentProject.getName() );
                boolean validated = false;
                // si elle existe
                if ( currentSqualeReference != null )
                {
                    // recupere sa validation
                    validated = currentSqualeReference.getHidden();
                    // et supprime la reference du referenciel
                    SqualeReferenceDAOImpl.getInstance().remove( session, currentSqualeReference );
                }

                // Initialisation du nouveau SqualeReferenceBO
                currentSqualeReference = new SqualeReferenceBO();
                // en gardant la reference precedente
                currentSqualeReference.setHidden( validated );
                // Grille qualité correspondant à cet audit
                QualityGridBO grid = auditBO.getAuditGrid( currentProject ).getGrid();
                // Récupération de la grille qualité
                currentSqualeReference.setQualityGrid( grid );
                // Recuperation des resultats
                // Pour tous les facteurs qualités du projet
                Iterator it = grid.getFactors().iterator();
                while ( it.hasNext() )
                {
                    FactorRuleBO qRule = (FactorRuleBO) it.next();
                    // recupere la note
                    FactorResultBO factorResult =
                        (FactorResultBO) QualityResultDAOImpl.getInstance().load( session, projectId, auditId,
                                                                                  new Long( qRule.getId() ) );
                    if ( factorResult != null )
                    {
                        // si la régle à été trouvée, on affecte sa note à la référence
                        currentSqualeReference.getFactors().put( qRule, new Float( factorResult.getMeanMark() ) );
                    }
                }

                // set des différent paramètres de la référence
                currentSqualeReference.setDate( pAudit.getDate() );
                currentSqualeReference.setLanguage( currentProject.getProfile().getName() );
                currentSqualeReference.setProgrammingLanguage( currentProject.getProfile().getLanguage() );
                currentSqualeReference.setApplicationName( currentProject.getParent().getName() );
                currentSqualeReference.setProjectName( currentProject.getName() );
                currentSqualeReference.setVersion( pAudit.getName() );
                currentSqualeReference.setAuditType( pAudit.getType() );
                // Obtention du caractère public de l'application de rattachement
                // L'application est censée exister et donc le code défensif est ici inutile
                // Le caractère public d'un audit est lié au caractère public de l'application
                // au moment où cet audit est créé dans la base
                boolean publicApplication =
                    ApplicationDAOImpl.getInstance().loadByName( session, currentProject.getParent().getName() ).getPublic();
                currentSqualeReference.setPublic( publicApplication );

                // Volumétrie
                // TODO : Mettre les valeurs de volumétrie dans les profils et ne plus utiliser de référence en dur
                RSMProjectMetricsBO projectVol =
                    (RSMProjectMetricsBO) MeasureDAOImpl.getInstance().load( session, projectId, auditId,
                                                                             RSMProjectMetricsBO.class );
                if ( projectVol != null )
                {
                    currentSqualeReference.setCodeLineNumber( projectVol.getSloc().intValue() );
                }
                McCabeQAProjectMetricsBO presult =
                    (McCabeQAProjectMetricsBO) MeasureDAOImpl.getInstance().load( session, projectId, auditId,
                                                                                  McCabeQAProjectMetricsBO.class );
                if ( presult != null )
                {
                    currentSqualeReference.setClassNumber( presult.getNumberOfClasses().intValue() );
                    currentSqualeReference.setMethodNumber( presult.getNumberOfMethods().intValue() );
                    // S'il n'y a pas de données de RSM, alors prendre la donnée depuis McCabe (Cas pour le COBOL)
                    if ( projectVol == null )
                    {
                    	currentSqualeReference.setCodeLineNumber( presult.getProjectsloc().intValue() );
                    }
                    
                }

                // Creation en base
                squaleReferenceDAO.create( session, currentSqualeReference );
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualeReferenceFacade.class.getName() + ".insertAudit" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualeReferenceFacade.class.getName() + ".insertAudit" );
        }
    }

    /**
     * Permet de récupérer références
     * 
     * @use by ResultsComponent dans la comparaison d'applications
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de départ
     * @param pIsAdmin booleen permettant de savoir si on doit récupérer les projets masqués
     * @param pUserId l'id de l'utilisateur
     * @return Collection de SqualeReferenceDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public static List getProjectResults( Integer pNbLignes, Integer pIndexDepart, boolean pIsAdmin, Long pUserId )
        throws JrafEnterpriseException
    {

        // Initialisation
        List referenceDTOs = null; // retour de la méthode
        List referenceBOs = null; // retour de la DAO
        ISession session = null; // session Hibernate

        try
        {

            SqualeReferenceDAOImpl squaleDAO = SqualeReferenceDAOImpl.getInstance();
            // récupération d'un session
            session = PERSISTENCEPROVIDER.getSession();

            // Récupération des références souhaitées
            referenceBOs = (List) squaleDAO.findWhereScrollable( session, pNbLignes, pIndexDepart, pIsAdmin, pUserId );

            Iterator referenceIterator = referenceBOs.iterator();
            SqualeReferenceDTO currentReferenceDTO = null;

            if ( referenceBOs != null )
            {
                referenceDTOs = new ArrayList();

                while ( referenceIterator.hasNext() )
                {
                    // pour chaque référence :
                    // on la transforme en SqualeReferenceDTO
                    currentReferenceDTO =
                        SqualeReferenceTransform.bo2Dto( (SqualeReferenceBO) referenceIterator.next() );
                    // et on l'ajoute à la collection de retour
                    referenceDTOs.add( currentReferenceDTO );
                }
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualeReferenceFacade.class.getName() + ".getProjectResults" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualeReferenceFacade.class.getName() + ".getProjectResults" );
        }

        return referenceDTOs;
    }

    /**
     * Permet de supprimer une collection d'audits d'applications du référentiel
     * 
     * @param pAuditsToRemove collection de SqualeReferenceDTO à supprimer
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB502E0
     */
    public static void deleteAuditList( Collection pAuditsToRemove )
        throws JrafEnterpriseException
    {
        deleteAuditList( pAuditsToRemove, null );
    }

    /**
     * Permet de supprimer une collection d'audits d'applications du référentiel
     * 
     * @param pAuditsToRemove collection de SqualeReferenceDTO à supprimer
     * @param pSession session JRAF
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB502E2
     */
    public static void deleteAuditList( Collection pAuditsToRemove, ISession pSession )
        throws JrafEnterpriseException
    {

        // Initialisation
        Iterator referenceIterator = pAuditsToRemove.iterator();
        SqualeReferenceBO squaleReference = null;
        Long currentReferenceId = null;

        try
        {
            if ( pSession == null )
            {
                // si aucune session n'a été fournie à la façade, on en récupère une
                //CHECKSTYLE:OFF
                pSession = PERSISTENCEPROVIDER.getSession();
                //CHECKSTYLE:ON
            }

            SqualeReferenceDAOImpl referenceDao = SqualeReferenceDAOImpl.getInstance();
            // Pour chaque audit d'application dans le référentiel
            while ( referenceIterator.hasNext() )
            {
                // récupération de l'id de la référence en cours
                currentReferenceId = new Long( ( (SqualeReferenceDTO) referenceIterator.next() ).getId() );

                // Chargement de l'objet en base
                squaleReference = (SqualeReferenceBO) referenceDao.load( pSession, currentReferenceId );
                // Suppression de l'objet en base
                referenceDao.remove( pSession, squaleReference );
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualeReferenceFacade.class.getName() + ".deleteAuditList" );
        }
        finally
        {
            FacadeHelper.closeSession( pSession, SqualeReferenceFacade.class.getName() + ".deleteAuditList" );
        }
    }

    /**
     * Permet de valider une collection de résultats d'une application dans le referentiel
     * 
     * @param pReferences collection de SqualeReferenceDTO à valider
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB502EB
     */
    public static void validateAuditList( Collection pReferences )
        throws JrafEnterpriseException
    {
        updateReferentiel( pReferences, null );
    }

    /**
     * Permet de valider une collection de résultats d'une application dans le referentiel
     * 
     * @param pReferences collection de SqualeReferenceDTO à valider
     * @param pSession session Hibernate
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB502ED
     */
    public static void updateReferentiel( Collection pReferences, ISession pSession )
        throws JrafEnterpriseException
    {

        // Initialisation
        Iterator referenceIterator = pReferences.iterator();
        Long currentReferenceId = null;
        SqualeReferenceBO referenceBO = null;

        try
        {

            // Initialisation de la session
            if ( pSession == null )
            {
                //CHECKSTYLE:OFF
                pSession = PERSISTENCEPROVIDER.getSession();
                //CHECKSTYLE:ON
            }
            // Récupération d'une instance de SqualeReferenceDAOImpl
            SqualeReferenceDAOImpl referenceDao = SqualeReferenceDAOImpl.getInstance();
            // Pour chaque référence
            while ( referenceIterator.hasNext() )
            {
                // Chargement de l'objet et modification du champs "validated"
                SqualeReferenceDTO currentReference = (SqualeReferenceDTO) referenceIterator.next();
                referenceBO =
                    (SqualeReferenceBO) referenceDao.loadByName( pSession, currentReference.getApplicationName(),
                                                                 currentReference.getProjectName() );
                // Si l'application et le projet n'était pas déjà référencé, on ne fait rien
                // Ce cas ne doit pas arriver, sauf lors de cas particuliers comme des migrations ou des passages en
                // prod par exemple
                if ( referenceBO != null )
                {
                    referenceBO.setHidden( !currentReference.getHidden() );
                    // sauvegarde de l'objet modifié
                    referenceDao.save( pSession, referenceBO );
                }
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualeReferenceFacade.class.getName() + ".updateReferentiel" );
        }
        finally
        {
            FacadeHelper.closeSession( pSession, SqualeReferenceFacade.class.getName() + ".updateReferentiel" );
        }

    }

    /**
     * @param pAppliName le nom du projet
     * @param pSession la session
     * @return l'objet référence correspondant si il existe, null sinon
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static SqualeReferenceDTO getReferencedAudit( String pAppliName, ISession pSession )
        throws JrafEnterpriseException
    {
        SqualeReferenceBO referenceBO = null;
        SqualeReferenceDTO result = null;
        try
        {

            // Initialisation de la session
            if ( pSession == null )
            {
                //CHECKSTYLE:OFF
                pSession = PERSISTENCEPROVIDER.getSession();
                //CHECKSTYLE:ON
            }
            // Récupération d'une instance de SqualeReferenceDAOImpl
            SqualeReferenceDAOImpl referenceDao = SqualeReferenceDAOImpl.getInstance();

            referenceBO = referenceDao.findReferenceByAppliName( pSession, pAppliName );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualeReferenceFacade.class.getName() + ".validateAuditList" );
        }
        finally
        {
            FacadeHelper.closeSession( pSession, SqualeReferenceFacade.class.getName() + ".validateAuditList" );
        }
        if ( referenceBO != null )
        {
            result = SqualeReferenceTransform.bo2Dto( referenceBO );
        }
        return result;
    }

    /**
     * @return la collection des noms d'application stockées dans le référentiel
     * @throws JrafEnterpriseException en cas d'échec
     * @throws JrafPersistenceException en cas d'échec
     */
    public static Collection listReferentiel()
        throws JrafEnterpriseException, JrafPersistenceException
    {
        Collection result = new ArrayList( 0 );
        ISession mSession = PERSISTENCEPROVIDER.getSession();
        try
        {
            // Récupération d'une instance de SqualeReferenceDAOImpl
            SqualeReferenceDAOImpl referenceDao = SqualeReferenceDAOImpl.getInstance();
            result = referenceDao.findAllDistinctAppliName( mSession );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualeReferenceFacade.class.getName() + ".listReferentiel" );
        }
        finally
        {
            FacadeHelper.closeSession( mSession, SqualeReferenceFacade.class.getName() + ".listReferentiel" );
        }
        return result;
    }
}
