//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\facade\\ErrorFacade.java

package com.airfrance.squalecommon.enterpriselayer.facade.quality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.result.ErrorDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.result.ErrorTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;

/**
 */
public class ErrorFacade
    implements IFacade
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Permet de récupérer les erreurs d'un projet relatifs à un audit et une tache et un audit donné
     * 
     * @param pError ErrorDTO avec ID du projet, de l'audit et la clé de la tache renseigné
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de départ
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB30180
     */
    public static Collection getErrors( ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart )
        throws JrafEnterpriseException
    {

        // Initialisation des parametres
        Collection collection = null; // retour de la facade
        List tasks = new ArrayList(); // parametre de la methode appelee

        // Initialisation des parametres de la methodes

        if ( pError.getTaskName() != null )
        {
            tasks.add( pError.getTaskName() );
        }
        else
        {
            tasks = null;
        }

        collection = getErrorsByTask( tasks, pError, pNbLignes, pIndexDepart );

        return collection;

    }

    /**
     * Permet de récupérer une liste d'erreurs pour un projet, un audit et plusieurs audits
     * 
     * @param pAuditDTOs liste d'AuditDTOs
     * @param pError ErrorDTO avec ID du projet et la clé de la tache renseigné
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de départ
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB3019F
     */
    public static Collection getErrorsByAudit( List pAuditDTOs, ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart )
        throws JrafEnterpriseException
    {

        // Initialisation
        Collection collection = new ArrayList(); // retour de la facade
        Collection errorsPerAudit = null; // retour de getErrors

        // Utilisation n fois de la methode getErrors()
        Iterator auditIterator = pAuditDTOs.iterator();
        AuditDTO auditTemp = null;
        while ( auditIterator.hasNext() )
        {

            // initialisation du retour de la facade
            if ( collection == null )
            {
                collection = new ArrayList();
            }

            auditTemp = (AuditDTO) auditIterator.next();
            pError.setAuditId( auditTemp.getID() );

            errorsPerAudit = getErrors( pError, pNbLignes, pIndexDepart );
            if ( errorsPerAudit == null )
            {
                collection = null;
            }
            else
            {
                collection.addAll( errorsPerAudit );
            }

        }

        return collection;
    }

    /**
     * Permet de récupérer des erreurs pour un projet, un audit et une liste de taches
     * 
     * @param pTaskKeys liste des clés des taches souhaitees sinon <code>null</code> pour toutes les taches, sinon
     *            <code>null</code> pour toutes les taches
     * @param pError ErrorDTO avec ID du projet et de l'audit renseigné
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de départ
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB301AB
     */
    public static Collection getErrorsByTask( List pTaskKeys, ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart )
        throws JrafEnterpriseException
    {

        // Initialisation
        Collection errorDTOs = null; // retour de la facade
        Collection errorBOs = null; // retour de la DAO
        Long auditID = new Long( pError.getAuditId() ); // identifiant de l'audit
        Long projectId = new Long( pError.getProjectId() ); // identifiant du composant

        ISession session = null;

        try
        {
            // récupération d'une se session
            session = PERSISTENTPROVIDER.getSession();

            ErrorDAOImpl errorDAO = ErrorDAOImpl.getInstance();

            // récupération des ErrorBO correspondant à l'audit, au projet et à la tache
            errorBOs =
                (Collection) errorDAO.findWhere( session, auditID, projectId, pTaskKeys, pNbLignes, pIndexDepart );

            Iterator errorIterator = errorBOs.iterator();
            ErrorBO errorTemp = null;
            // initialisation de la collection de retour
            errorDTOs = new ArrayList();
            while ( errorIterator.hasNext() )
            {
                // pour chaque ErrorBO
                errorTemp = (ErrorBO) errorIterator.next();
                // transformation et ajout à la collection de retour
                errorDTOs.add( ErrorTransform.bo2Dto( errorTemp ) );
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ErrorFacade.class.getName() + ".getErrorsByTask" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ErrorFacade.class.getName() + ".getErrorsByTask" );
        }

        return errorDTOs;
    }

    /**
     * Constructeur vide
     * 
     * @roseuid 42CBFFB301C9
     */
    private ErrorFacade()
    {
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return un booléen indiquant si il y eu des erreurs
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static int getNumberOfErrors( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        int result = 0;
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            ErrorDAOImpl errorDAO = ErrorDAOImpl.getInstance();
            // récupération du nombre d'erreurs correspondant à l'audit
            result = errorDAO.getNumberOfErrorsWhere( session, pAuditId, pProjectId, null ).intValue();
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ErrorFacade.class.getName() + ".getNumberOfErrors" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ErrorFacade.class.getName() + ".getNumberOfErrors" );
        }
        return result;
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return le nombre d'erreurs provoqué par le projet par niveau : 0 -> ErrorBO.CRITICITY_FATAL 1 ->
     *         ErrorBO.CRITICITY_WARNING 1 -> ErrorBO.CRITICITY_LOW
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static Integer[] getErrorsRepartition( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        final int NB_ERRORS_LEVELS = 3;
        Integer[] result = new Integer[NB_ERRORS_LEVELS];
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            ErrorDAOImpl errorDAO = ErrorDAOImpl.getInstance();
            // récupération du nombre d'erreurs correspondant à l'audit par niveau
            result[0] = errorDAO.getNumberOfErrorsWhere( session, pAuditId, pProjectId, ErrorBO.CRITICITY_FATAL );
            result[1] = errorDAO.getNumberOfErrorsWhere( session, pAuditId, pProjectId, ErrorBO.CRITICITY_WARNING );
            result[2] = errorDAO.getNumberOfErrorsWhere( session, pAuditId, pProjectId, ErrorBO.CRITICITY_LOW );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ErrorFacade.class.getName() + ".getNumberOfErrors" );
            result = null;
        }
        finally
        {
            FacadeHelper.closeSession( session, ErrorFacade.class.getName() + ".getNumberOfErrors" );
        }
        return result;
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return les noms des tâches en échec
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static List getFailedTasks( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        List results = new ArrayList( 0 );
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            ErrorDAOImpl errorDAO = ErrorDAOImpl.getInstance();
            // récupération des noms des tâches
            results = errorDAO.getTasksNameWhere( session, pAuditId, pProjectId, ErrorBO.CRITICITY_FATAL );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ErrorFacade.class.getName() + ".getNumberOfErrors" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ErrorFacade.class.getName() + ".getNumberOfErrors" );
        }
        return results;
    }

    /**
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @return les noms des tâches possédant des erreurs
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static List getAllTasks( Long pProjectId, Long pAuditId )
        throws JrafEnterpriseException
    {
        List results = new ArrayList( 0 );
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            ErrorDAOImpl errorDAO = ErrorDAOImpl.getInstance();
            // récupération des noms des tâches
            results = errorDAO.getTasksNameWhere( session, pAuditId, pProjectId, null );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ErrorFacade.class.getName() + ".getAllTasks" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ErrorFacade.class.getName() + ".getAllTasks" );
        }
        return results;
    }
    
    /**
     * Get errors for a list of audits and a criticity level (facultative)
     * 
     * @param pAuditsDTO list of audits (currentt audit, previous audit)
     * @param pCriticity level of errors
     * @return list of map of errors for each audit (same order) like : 
     * (current audit map, previous audit map)
     * key : project name
     * value : List of ErrorDTO for this project and this audit 
     * @throws JrafEnterpriseException if error
     */
    public static List getAllErrors(List pAuditsDTO, String pCriticity) throws JrafEnterpriseException {
        // Initialisation
        List errors = new ArrayList();
        ISession session = null;
        ErrorDAOImpl errorDAO = ErrorDAOImpl.getInstance();
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        try {
            // Get hibernate session
            session = PERSISTENTPROVIDER.getSession();
            Long currentAuditId;
            Collection currentProjects;
            ProjectBO currentProject;
            for(int i=0; i<pAuditsDTO.size(); i++) {
                currentAuditId = new Long(((AuditDTO) pAuditsDTO.get( i )).getID());
                // Get projects for the current audit
                currentProjects = projectDAO.findWhere( session, currentAuditId );
                // Get errors by project
                int nbProjects = currentProjects.size();
                HashMap auditErrors = new HashMap(nbProjects);
                for(Iterator projetIt=currentProjects.iterator(); projetIt.hasNext();) {
                    currentProject = (ProjectBO)projetIt.next();
                    auditErrors.put( currentProject.getName(),  ErrorTransform.bo2Dto( errorDAO.findAllWhere( session, currentAuditId, new Long(currentProject.getId()), pCriticity )));
                }
                // Add map in list returned
                errors.add( auditErrors );
            }
        } catch(JrafDaoException jde) {
            FacadeHelper.convertException( jde, ErrorFacade.class.getName() + ".getAllErrors" );
        } finally {
            FacadeHelper.closeSession( session, ErrorFacade.class.getName() + ".getAllErrors" );
        }
        return errors;
    }
}
