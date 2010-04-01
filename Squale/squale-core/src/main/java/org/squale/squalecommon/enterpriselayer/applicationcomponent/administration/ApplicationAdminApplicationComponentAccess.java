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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\applicationcomponent\\ApplicationAdminApplicationComponentAccess.java

package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.ProjectConfDTO;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.AuditFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.ProjectFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;

/**
 * <p>
 * Title : ApplicationAdminApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component de configuration du projet
 * </p>
 */
public class ApplicationAdminApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( ApplicationAdminApplicationComponentAccess.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CBFC010285
     */
    public ApplicationAdminApplicationComponentAccess()
    {
    }

    /**
     * Ajoute un projet à l'application courante
     * 
     * @param pProjectConf objet projet à ajouter
     * @param pApplicationConf objet application auquel on ajoute le projet
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @deprecated utilisation de la methode saveProject(...)
     * @roseuid 42CBFC0102A3
     */
    public Integer addProject( ProjectConfDTO pProjectConf, ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        // Teste si le projet existe au sein de l'application
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Ajout du projet à l'application
            pApplicationConf.getProjectConfList().add( pProjectConf );
            // Utilisation de ApplicationFacade pour modifier l'application
            ApplicationFacade.update( pApplicationConf, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { pProjectConf.getName() };
            String message = ACMessages.getString( "ac.exception.applicationadmin.addproject", tab );
            status = new Integer( 1 );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return status;
    }

    /**
     * sauve l'application en base de données
     * 
     * @param pApplicationConf ApplicationConfDTO associé
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     */
    public Integer saveApplication( ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            //Update the application name in the squalereference table
            SqualeReferenceFacade.updateApplicationName( Long.toString( pApplicationConf.getId() ), pApplicationConf.getName(), session );
            // Utilisation de ApplicationFacade pour modifier l'application
            ApplicationFacade.update( pApplicationConf, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pApplicationConf.getId() ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.saveapplication", tab );
            status = new Integer( 1 );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return status;
    }

    /**
     * Modifie les paramétres de l'application concernant les dernières modifiations
     * 
     * @param pApplicationId l'id de l'application
     * @param pLastUser le nom du dernier utilisateur ayant modifié l'application
     * @param pLastUpdate la date de dernière modification
     * @throws JrafEnterpriseException si erreur
     */
    public void updateLastModifParams( Long pApplicationId, String pLastUser, Date pLastUpdate )
        throws JrafEnterpriseException
    {
        ApplicationFacade.updateLastModifParams( pApplicationId, pLastUser, pLastUpdate );
    }

    /**
     * Permet de savoir si une application existe en base grace a son nom et le crée en fonction du résultat
     * 
     * @param pApplicationConf ApplicationConfDTO renseignant le nom de l'année à vérifier
     * @param pUserCreating gestionnaire de l'application
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0102FD
     */
    public ApplicationConfDTO createApplication( ApplicationConfDTO pApplicationConf, UserDTO pUserCreating )
        throws JrafEnterpriseException
    {

        // Initialisation
        ApplicationConfDTO projectConfDTO = null; // retour de la facade

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Utilisation de ApplicationFacade pour insérer l'application
            projectConfDTO = ApplicationFacade.insert( pApplicationConf, pUserCreating, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( projectConfDTO.getId() ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.createapplication", tab );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return projectConfDTO;
    }

    /**
     * Permet de supprimer un projet d'une application
     * 
     * @param pProjectId l'ID du projet
     * @return le projet
     * @throws JrafEnterpriseException Exception JRAF
     */
    public ProjectConfDTO removeProject( Long pProjectId )
        throws JrafEnterpriseException
    {
        // Initialisation
        ISession session = null;
        ProjectConfDTO result = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Utilisation de ProjectFacade pour supprimer "logiquement" le projet
            result = ProjectFacade.delete( pProjectId, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pProjectId ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.removeproject", tab );
            result = null;
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return result;
    }

    /**
     * Permet de désactiver ou d'activer un projet d'une application
     * 
     * @param pProjectId l'ID du projet
     * @return le projet
     * @throws JrafEnterpriseException Exception JRAF
     */
    public ProjectConfDTO disactiveOrReactiveProject( Long pProjectId )
        throws JrafEnterpriseException
    {
        // Initialisation
        ISession session = null;
        ProjectConfDTO result = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Utilisation de ProjectFacade pour désactiver le projet
            result = ProjectFacade.disactiveOrReactiveProject( pProjectId, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pProjectId ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.removeproject", tab );
            result = null;
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return result;
    }

    /**
     * modifie un projet donné
     * 
     * @param pProjectConf projet à modifier
     * @param pApplicationConf application attachée, si <code>null</code> modification des propriétés uniquement
     * @return pProjectConfDTO si l'update s'est correctement deroulé, sinon <code>null</code>
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC01037F
     */
    public ProjectConfDTO saveProject( ProjectConfDTO pProjectConf, ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        ProjectConfDTO projectConf = null;
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Utilisation de la ProjectFacade pour modifier le projet
            projectConf = ProjectFacade.update( pProjectConf, pApplicationConf, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            String tab[] = { String.valueOf( pProjectConf.getId() ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.saveproject", tab );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return projectConf;
    }

    /**
     * Permet d'ajouter un milestone à une application existante
     * 
     * @param pAudit AuditDTO définissant un milestone
     * @return l'audit crée ou null si il y a eu un problème
     * @throws JrafEnterpriseException Exception JRAF
     */
    public AuditDTO addMilestone( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        AuditDTO result = null;
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Ajout du milestone au projet par AuditFacade
            result = AuditFacade.insertAudit( pAudit, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pAudit.getApplicationId() ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.addmilestone", tab );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return result;
    }

    /**
     * Ajoute un audit de suivi à réaliser sur l'application. Les éventuels audits de suivi programmés sont supprimés au
     * profit du nouvel audit créé
     * 
     * @param pAuditDTO audit de suivi à programmer
     * @return le DTO de l'audit inséré (null en cas d'erreur d'insertion)
     * @throws JrafEnterpriseException Exception JRAF
     * @throws JrafPersistenceException exception Persistence
     */
    public AuditDTO addBranch( AuditDTO pAuditDTO )
        throws JrafEnterpriseException, JrafPersistenceException
    {
        ISession lSession = null;
        AuditDTO lAuditDTO = null;
        try
        {
            lSession = PERSISTENTPROVIDER.getSession();
            lSession.beginTransaction();
            // remplacement des audits de suivi programmés
            lAuditDTO = AuditFacade.modifyNextNormalAudit( pAuditDTO.getApplicationId(), pAuditDTO, lSession );
            lSession.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pAuditDTO.getApplicationId() ) };
            String message = ACMessages.getString( "ac.exception.applicationadmin.addmilestone", tab );
            if ( lSession != null )
            {
                lSession.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        finally
        {
            lSession.closeSession();
        }
        return lAuditDTO;
    }

    /**
     * permet de récupérer un ApplicationConfDTO à partir d'un identifiant
     * 
     * @param pApplicationConf ApplicationConfDTO renseignant l'ID de l'application
     * @return ApplicationConfDTO application concernée
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0103CF
     */
    public ApplicationConfDTO getApplicationConf( ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        ApplicationConfDTO projectConf = new ApplicationConfDTO();
        projectConf = (ApplicationConfDTO) ApplicationFacade.getApplicationConf( pApplicationConf );
        return projectConf;
    }

    /**
     * Permet de reprogrammé un audit sur une application au lendemain. si il s'agit d'un audit de suivi, l'audit déjà
     * programmé est supprimé
     * 
     * @param pAudit AuditDTO définissant l'id de l'audit
     * @return l'audit reprogrammé
     * @throws JrafEnterpriseException si erreur
     */
    public AuditDTO restartAudit( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        AuditDTO auditDTO = AuditFacade.restartAudit( pAudit );
        return auditDTO;
    }

    /**
     * Récupère l'audit de jalon programmé pour l'application d'id <code>pApplicationId</code> si il existe
     * 
     * @param pApplicationId l'id de l'application
     * @return l'audit trouvé ou null sinon
     * @throws JrafEnterpriseException si erreur
     */
    public AuditDTO getMilestoneAudit( Long pApplicationId )
        throws JrafEnterpriseException
    {
        AuditDTO auditDTO = AuditFacade.getMilestoneAudit( pApplicationId );
        return auditDTO;
    }

    /**
     * Supprime l'audit de jalon d'id <code>removeAudit</code>
     * 
     * @param pAuditId l'id de l'audit
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException si erreur
     */
    public Integer removeAudit( Long pAuditId )
        throws JrafEnterpriseException
    {
        Integer status = AuditFacade.removeAudit( pAuditId );
        return status;
    }

    /**
     * Modifie l'audit de jalon d'id <code>removeAudit</code>
     * 
     * @param pAudit l'audit à mettre à jour
     * @return l'audit modifié
     * @throws JrafEnterpriseException si erreur
     */
    public AuditDTO modifyAudit( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        AuditDTO auditDto = AuditFacade.modifyAudit( pAudit );
        return auditDto;
    }

    /**
     * @param auditId l'id de l'audit (on veut récupérer l'application correspondante)
     * @return l'application sous la forme d'un componentDTO
     */
    public ComponentDTO loadByAuditId( Long auditId )
    {
        ComponentDTO result = null;
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            result = ApplicationFacade.loadByAuditId( auditId, session );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
        return result;
    }

    /**
     * @return la liste des applications définies
     */
    public Collection listAll()
    {
        Collection result = new ArrayList( 0 );
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            result = ApplicationFacade.listAll( session );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
        return result;
    }

    /**
     * Ajoute un accès utilisateur à la date du jour
     * 
     * @param pApplicationId application id
     * @param pMatricule le matricule de l'utilisateur
     * @param maxSize le nombre max d'accès à sauvegarder
     * @throws JrafEnterpriseException si erreur
     */
    public void addUserAccess( Long pApplicationId, String pMatricule, Integer maxSize )
        throws JrafEnterpriseException
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            ApplicationFacade.addUserAccess( session, pApplicationId, pMatricule, maxSize );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
    }

    /**
     * adds a tag to an application
     * 
     * @param pApplicationId application accessed
     * @param pTag The tag that will be added to the application
     * @throws JrafEnterpriseException if an error occurs
     */
    public void addTag( Long pApplicationId, TagDTO pTag )
        throws JrafEnterpriseException
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            ApplicationFacade.addTag( session, pApplicationId, pTag );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
    }

    /**
     * removes a tag from an application
     * 
     * @param pApplicationId application accessed
     * @param pTag The tag that will be removed from the application
     * @throws JrafEnterpriseException if an error occurs
     */
    public void removeTag( Long pApplicationId, TagDTO pTag )
        throws JrafEnterpriseException
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            ApplicationFacade.removeTag( session, pApplicationId, pTag );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
    }
}
