package com.airfrance.squaleweb.applicationlayer.action.component;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ProjectConfTransformer;
import com.airfrance.squaleweb.util.SqualeWebConstants;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * @version 1.0
 * @author
 */
public class CreateProjectAction
    extends ReaderAction
{

    /**
     * Méthode permettant de factoriser la récupération d'un projet
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return le projet
     * @throws JrafEnterpriseException en cas d'échec
     * @throws WTransformerException si erreur lors de la transformation
     */
    private CreateProjectForm getProject( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                          HttpServletResponse pResponse )
        throws JrafEnterpriseException, WTransformerException
    {
        // On récupère l'id du projet
        String projectId = pRequest.getParameter( "projectId" );
        // On supprime le projet en session
        pRequest.getSession().removeAttribute( "createProjectForm" );
        CreateProjectForm project = null;
        if ( null != projectId )
        {
            // Si l'id du projet est renseigné, on modifie les attributs
            // en session pour qu'ils correspondent à ceux du projet
            setProfiles( pRequest );
            setSourceManagements( pRequest );
            // On récupère l'application correspondant au projet et on la place en session
            CreateApplicationForm application =
                ManageApplicationUtils.getCreateApplicationForm( ( (CreateProjectForm) pForm ).getApplicationId(),
                                                                 pRequest );
            List projects = application.getProjects();
            // Recherche du projet dans la liste des projets
            // au niveau de l'application
            for ( int i = 0; null == project && i < projects.size(); i++ )
            {
                project = (CreateProjectForm) projects.get( i );
                // Tant qu'on a pas trouvé le projet, project reste à null
                // et on continue la recherche
                if ( new Long( project.getProjectId() ).longValue() == Long.parseLong( projectId ) )
                {
                    // On passe tous les id des paramètres à -1 pour pouvoir
                    // faire un update
                    changeMapParametersId( (MapParameterDTO) project.getParameters() );
                    // On enregistre le projet en session
                    pRequest.getSession().setAttribute( "createProjectForm", project );
                }
                else
                {
                    project = null;
                }
            }
        }
        return project;
    }

    /**
     * Permet de sélectionner le projet dont on veux modifier la configuration.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward selectProjectToModify( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                                HttpServletResponse pResponse )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        CreateProjectForm project = null;
        try
        {
            project = getProject( pMapping, pForm, pRequest, pResponse );
            if ( null != project )
            {
                forward = pMapping.findForward( "project" );
                pRequest.getSession().setAttribute( "modification", "true" );
            }
            else
            {
                // Projet non trouvé
                ActionMessage error = new ActionMessage( "error.project_not_got" );
                errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                forward = pMapping.findForward( "application" );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }

    /**
     * Permet de sélectionner le projet dont on veux voir la configuration.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward selectProjectToView( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                              HttpServletResponse pResponse )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        CreateProjectForm project = null;
        try
        {
            project = getProject( pMapping, pForm, pRequest, pResponse );
            if ( null != project )
            {
                forward = pMapping.findForward( "project" );
                pRequest.getSession().removeAttribute( "modification" );
            }
            else
            {
                // Projet non trouvé
                ActionMessage error = new ActionMessage( "error.project_not_got" );
                errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                forward = pMapping.findForward( "application" );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }

    /**
     * Change l'id des paramètres à -1 pour éviter les conflit lors de l'enregistrement en base
     * 
     * @param pParams la map de paramètres
     */
    private void changeMapParametersId( MapParameterDTO pParams )
    {
        pParams.setId( -1 );
        Map mapParam = pParams.getParameters();
        // On récupère toutes les clés des paramètres
        java.util.Set keys = mapParam.keySet();
        Iterator it = keys.iterator();
        String currentKey;
        // On parcours la map des paramètres afin de changer tous les id
        // de tous les paramètres stockés dans la map
        while ( it.hasNext() )
        {
            currentKey = (String) it.next();
            // Si le paramètre est de type Map ou List, il faut changer les id
            // des paramètres qu'il contient
            if ( mapParam.get( currentKey ) instanceof MapParameterDTO )
            {
                changeMapParametersId( (MapParameterDTO) mapParam.get( currentKey ) );
            }
            else
            {
                if ( mapParam.get( currentKey ) instanceof ListParameterDTO )
                {
                    changeListParametersId( (ListParameterDTO) mapParam.get( currentKey ) );
                }
                else
                {
                    // Si le paramètre est de type String, on change l'id directement
                    if ( mapParam.get( currentKey ) instanceof StringParameterDTO )
                    {
                        ( (StringParameterDTO) mapParam.get( currentKey ) ).setId( -1 );
                    }
                }
            }
        }
    }

    /**
     * Change l'id des paramètres à -1 pour éviter les conflit lors de l'enregistrement en base
     * 
     * @param pParams la liste de paramètres
     */
    private void changeListParametersId( ListParameterDTO pParams )
    {
        pParams.setId( -1 );
        List listParam = pParams.getParameters();
        // On parcours la liste des paramètres afin de changer tous les id
        // de tous les paramètres stockés dans la liste
        for ( int i = 0; i < listParam.size(); i++ )
        {
            // Si le paramètre est de type Map ou List, il faut changer les id
            // des paramètres qu'il contient
            if ( listParam.get( i ) instanceof MapParameterDTO )
            {
                changeMapParametersId( (MapParameterDTO) listParam.get( i ) );
            }
            else
            {
                if ( listParam.get( i ) instanceof ListParameterDTO )
                {
                    changeListParametersId( (ListParameterDTO) listParam.get( i ) );
                }
                else
                {
                    // Si le paramètre est de type String, on change l'id directement
                    if ( listParam.get( i ) instanceof StringParameterDTO )
                    {
                        ( (StringParameterDTO) listParam.get( i ) ).setId( -1 );
                    }
                }
            }
        }
    }

    /**
     * Positionnement des profiles
     * 
     * @param pRequest requête
     * @throws JrafEnterpriseException si erreur
     */
    private void setProfiles( HttpServletRequest pRequest )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "SqualixConfig" );
        pRequest.getSession().setAttribute( "profiles", ac.execute( "getProfiles" ) );
    }

    /**
     * Positionnement des outils de récupération des sources
     * 
     * @param pRequest requête
     * @throws JrafEnterpriseException si erreur
     */
    private void setSourceManagements( HttpServletRequest pRequest )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "SqualixConfig" );
        pRequest.getSession().setAttribute( "sourceManagements", ac.execute( "getSourceManagements" ) );
    }

    /**
     * Création d'un nouveau projet
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward newProject( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                     HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        try
        {
            setProfiles( pRequest );
            setSourceManagements( pRequest );
            ( (CreateProjectForm) pForm ).reset();
            // On remet à jour le form en session avec les données passées en requete
            String applicationId = (String) pRequest.getParameter( "applicationId" );
            ( (CreateProjectForm) pForm ).setApplicationId( applicationId );
            ManageApplicationUtils.getCreateApplicationForm( applicationId, pRequest );
            forward = pMapping.findForward( "project" );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Sauvegarde le projet
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward saveProject( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                      HttpServletResponse pResponse )
    {
        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        CreateProjectForm project = (CreateProjectForm) pForm;
        try
        {
            ProjectConfDTO dto =
                (ProjectConfDTO) WTransformerFactory.formToObj( ProjectConfTransformer.class, project )[0];

            IApplicationComponent ac = AccessDelegateHelper.getInstance( "ApplicationAdmin" );

            // On récupére en base le bon objet ApplicationConfDTO car celui en session n'est pas forcément le bon
            // car on a pu faire des backs
            ApplicationConfDTO applicationDTO = new ApplicationConfDTO();
            applicationDTO.setId( new Long( project.getApplicationId() ).longValue() );
            applicationDTO = (ApplicationConfDTO) ac.execute( "getApplicationConf", new Object[] { applicationDTO } );
            // On change le nom de l'utilisateur et la date de dernière modification en cas
            applicationDTO.setLastUser( ( (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY ) ).getMatricule() );
            applicationDTO.setLastUpdate( Calendar.getInstance().getTime() );
            Object[] paramIn = { dto, applicationDTO };
            String action =
                "/manageApplication.do?" + "action=selectApplicationToConfig&applicationId=" + applicationDTO.getId();
            forward = new ActionForward( action );
            // Appel de la couche métier
            dto = (ProjectConfDTO) ac.execute( "saveProject", paramIn );
            if ( dto != null )
            {
                project.setProjectId( "" + dto.getId() );
                // permet d'envoyer un mail si un nouveau projet est crée
                manageMailforProjectCreation( pRequest, applicationDTO, project );
            }
            // On recharge les profils de l'utilisateur
            ActionUtils.refreshUser( pRequest );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }

    /**
     * Méthode permettant d'envoyer un mail dans le cas d'un nouveau projet
     * 
     * @param pRequest la requete http
     * @param pApplicationDTO l'application courante
     * @param pProject le projet courant
     */
    private void manageMailforProjectCreation( HttpServletRequest pRequest, ApplicationConfDTO pApplicationDTO,
                                               CreateProjectForm pProject )
    {
        // Si l'application est validée et que c'est un nouveau projet, alors on avertit les administrateurs qu'un
        // nouveau projet a été ajouté
        // à une application déjà validée
        if ( pRequest.getSession().getAttribute( SqualeWebConstants.NEW_PROJECT ) != null
            && pApplicationDTO.getStatus() == ApplicationBO.VALIDATED )
        {
            String[] params = new String[] { pProject.getProjectName(), pApplicationDTO.getName() };
            String sender = WebMessages.getString( getLocale( pRequest ), "mail.sender.squale" );
            String header = WebMessages.getString( getLocale( pRequest ), "mail.headerForAdmin" );
            String object = sender + WebMessages.getString( pRequest, "mail.project.added.object" );
            String content =
                header + (String) WebMessages.getString( pRequest.getLocale(), "mail.project.added.content", params );
            SqualeCommonUtils.notifyByEmail( MailerHelper.getMailerProvider(), SqualeCommonConstants.ONLY_ADMINS, null,
                                             object, content, false );
            // on efface l'attribut
            pRequest.getSession().removeAttribute( SqualeWebConstants.NEW_PROJECT );
        }
    }
}
