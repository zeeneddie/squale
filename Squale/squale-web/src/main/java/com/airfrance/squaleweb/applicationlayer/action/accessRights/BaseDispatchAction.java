package com.airfrance.squaleweb.applicationlayer.action.accessRights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectListForm;
import com.airfrance.squaleweb.applicationlayer.tracker.Tracker;
import com.airfrance.squaleweb.applicationlayer.tracker.TrackerStructure;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ApplicationListTransformer;
import com.airfrance.squaleweb.transformer.ProjectListTransformer;
import com.airfrance.squaleweb.util.ExceptionWrapper;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.squaleweb.util.SqualeWebConstants;
import com.airfrance.welcom.struts.action.WDispatchAction;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Action de base Cette classe permet de factoriser les actions de base de squale
 */
public abstract class BaseDispatchAction
    extends WDispatchAction
{
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( BaseDispatchAction.class );

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des projets du projet actuellement
     * sélectionné.
     */
    public final static String PROJECTS_KEY = "projects.list";

    /** Constantes */

    /** défini la constante pour le dto */
    public final static String REQUEST_DTO = "requestDTO";

    /** constante pour l'application */
    public final static String APPLI_DTO = "applicationDTO";

    /** constante pour l'application */
    public final static String PROJECT_DTO = "projectDTO";

    /** constante pour l'application */
    public final static String CURRENT_AUDIT_DTO = "currentAuditDTO";

    /** constante pour l'application */
    public final static String PREVIOUS_AUDIT_DTO = "previousAuditDTO";

    /** constante pour l'application */
    public final static String DO_NOT_UPDATE_PROJECT_NAME = "doNotUpdateProjectName";

    /**
     * Paramètre de la requête pour indiquer qu'il ne faut pas modifier le formulaire en session. C'est le cas par
     * exemple pour la création d'un projet ou d'une application
     */
    public final static String DO_NOT_RESET_FORM = "doNotResetForm";

    /**
     * Traitement d'une exception Une exception interceptée dans une action est traitée par l'appel de cette méthode qui
     * log celle-ci et la place dans les paramètres de la requête pour une exploitation par la page jsp d'erreur
     * associée
     * 
     * @param pException exception
     * @param pErrors erreurs
     * @param pRequest requête
     */
    protected void handleException( Exception pException, ActionMessages pErrors, HttpServletRequest pRequest )
    {
        log.error( pException, pException );
        // Enregistrement de l'exception
        ExceptionWrapper.saveException( pRequest, pException );
        ActionMessage error = new ActionMessage( "error.cannot_act" );
        pErrors.add( ActionMessages.GLOBAL_MESSAGE, error );
    }

    /**
     * Méthode pour factoriser l'utilisation du tracker
     * 
     * @param pDisplayName le nom du lien (la valeur affichée)
     * @param pUrl la valeur du lien
     * @param pType le type de l'élément qu'on a ajouté au tracker
     * @param pRequest la requete http
     * @param pReset le booléen indiquant si le tracker doit etre réinitialisé
     */
    protected void updateHistTracker( String pDisplayName, String pUrl, int pType, HttpServletRequest pRequest,
                                      boolean pReset )
    {
        // on ajoute les éléments à la requete si ils sont présents
        String requestComplement = "";
        if ( pRequest.getParameter( "currentAuditId" ) != null )
        {
            requestComplement += "&currentAuditId=" + (String) pRequest.getParameter( "currentAuditId" );
        }
        if ( pRequest.getParameter( "projectId" ) != null )
        {
            requestComplement += "&projectId=" + (String) pRequest.getParameter( "projectId" );
        }
        if ( pRequest.getParameter( "applicationId" ) != null )
        {
            requestComplement += "&applicationId=" + (String) pRequest.getParameter( "applicationId" );
        }
        String newUrl = pUrl + requestComplement;
        TrackerStructure ts = new TrackerStructure( pDisplayName, newUrl, pType );
        new Tracker().updateHistTracker( ts, pRequest, pReset );
    }

    /**
     * Méthode pour factoriser l'utilisation du tracker sur les composants
     * 
     * @param pId l'id du composant à rajouter
     * @param pName le nom du composant à rajouter
     * @param pRequest la requete http
     */
    protected void updateTrackerComponent( long pId, String pName, HttpServletRequest pRequest )
    {
        List list = (List) pRequest.getSession().getAttribute( SqualeWebConstants.TRACKER_COMPONENT );
        // vérifie que le form n'existe pas déjà, évite les problèmes de refresh entre autres
        boolean alreadyExists = false;
        List newList = new ArrayList( 0 );
        int i = 0;
        for ( i = 0; !alreadyExists && i < list.size(); i++ )
        {
            TrackerStructure ts = (TrackerStructure) ( list.get( i ) );
            // profite de la boucle pour construire la sous liste
            // dans le cas où on aurait cliqué sur un élément du traceur
            // la méthode sublist ne permet pas d'avoir une arrayList
            newList.add( i, ts );
            if ( ts.getDisplayName().equals( pName ) )
            {
                alreadyExists = true;
            }
        }
        // si il n'existe pas, on l'ajoute
        if ( !alreadyExists )
        {
            TrackerStructure lastOne = new TrackerStructure();
            StringTokenizer st = new StringTokenizer( pName, "(" );
            String name = pName;
            if ( st.hasMoreTokens() )
            {
                name = st.nextToken();
            }
            lastOne.setDisplayName( name );
            lastOne.setLink( "<a href=\"project_component.do?action=component&component=" + pId );
            list.add( lastOne );
            pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_COMPONENT, list );
        }
        else
        { // si il existe déja, on revient à l'endroit où il était dans le traceur
            pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_COMPONENT, newList );
        }

    }

    /**
     * Méthode pour réinitialiser le tracker Utilisée si on doit juste réinitialiser le tracker
     * 
     * @param pRequest la requete http
     */
    protected void resetTracker( HttpServletRequest pRequest )
    {
        new Tracker().reset( pRequest );
    }

    /**
     * change le paramètre indiquant de quel type de vue on vient
     * 
     * @param pRequest la requete http
     * @param pWay indique si on vient d'une vue composant
     */
    protected void changeWay( HttpServletRequest pRequest, String pWay )
    {
        pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_BOOL, pWay );
    }

    /**
     * change le paramètre indiquant si il faut reinitialiser le traceur ou pas
     * 
     * @param pRequest la requete
     * @param pReset le paramètre indiquant si il faut reinitialiser le traceur ou pas
     */
    protected void needToReset( HttpServletRequest pRequest, String pReset )
    {
        pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_RESET, pReset );
    }

    /**
     * Applications accessibles à l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur
     */
    protected List getUserApplicationList( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        return sessionUser.getApplicationsList();
    }

    /**
     * L'utilisateur étant admin,l'ensemble des applications
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur
     */
    protected List getUserAdminApplicationList( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        return sessionUser.getAdminList();
    }

    /**
     * Applications publiques accessibles à l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur
     */
    protected List getUserPublicApplicationList( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        return sessionUser.getPublicList();
    }

    /**
     * Applications non publiques accessibles à l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur
     */
    protected List getUserNotPublicApplicationList( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        return sessionUser.getNotPublicList();
    }

    /**
     * Applications possédant des résultats lisibles par l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur
     */
    protected List getUserApplicationWithResultsList( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        List applis = sessionUser.getNotPublicList();
        return getWithResults( applis );
    }

    /**
     * Applications possédant des résultats lisibles par l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur
     */
    protected List getUserPublicApplicationWithResultsList( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        List applis = sessionUser.getPublicList();
        return getWithResults( applis );
    }

    /**
     * @param applis les applications
     * @return la liste des applications ayant des résultats
     */
    private List getWithResults( List applis )
    {
        List results = new ArrayList();
        for ( int i = 0; i < applis.size(); i++ )
        {
            ApplicationForm current = (ApplicationForm) applis.get( i );
            if ( current.getHasResults() )
            {
                results.add( current );
            }
        }
        return results;
    }

    /**
     * Applications accessibles à l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur sous la forme de DTO
     */
    protected List getUserApplicationListAsDTO( HttpServletRequest pRequest )
    {
        ApplicationListForm applicationListForm = new ApplicationListForm();
        ArrayList applicationsList = new ArrayList();
        applicationsList.addAll( getUserApplicationList( pRequest ) );
        applicationListForm.setList( applicationsList );
        List applications = null;
        try
        {
            applications =
                (List) ( WTransformerFactory.formToObj( ApplicationListTransformer.class, applicationListForm )[0] );
        }
        catch ( WTransformerException e )
        {
            log.error( e, e );
        }
        return applications;
    }

    /**
     * Applications accessibles à l'utilisateur
     * 
     * @param pRequest requête HTTP
     * @return liste des applications accessibles à l'utilisateur sous la forme de DTO
     */
    protected List getUserAdminApplicationListAsDTO( HttpServletRequest pRequest )
    {
        ApplicationListForm applicationListForm = new ApplicationListForm();
        ArrayList applicationsList = new ArrayList();
        applicationsList.addAll( getUserAdminApplicationList( pRequest ) );
        applicationListForm.setList( applicationsList );
        List applications = null;
        try
        {
            applications =
                (List) ( WTransformerFactory.formToObj( ApplicationListTransformer.class, applicationListForm )[0] );
        }
        catch ( WTransformerException e )
        {
            log.error( e, e );
        }
        return applications;
    }

    /**
     * Utilisateur administrateur
     * 
     * @param pRequest requête HTTP
     * @return true si l'utilisateur est administrateur
     */
    protected boolean isUserAdmin( HttpServletRequest pRequest )
    {
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        return sessionUser.isAdmin();
    }

    /**
     * Obtention de l'utilisateur courant sous la forme de DTO
     * 
     * @param pRequest requête
     * @return utilisateur courant avec l'id et le matricule renseignés
     */
    protected UserDTO getUserAsDTO( HttpServletRequest pRequest )
    {
        UserDTO user = new UserDTO();
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        user.setID( sessionUser.getId() );
        user.setMatricule( sessionUser.getMatricule() );
        return user;
    }

    /**
     * Vérifie que l'utilisateur a les droits pour afficher la page demandée
     * 
     * @param pUser l'utilisateur courant
     * @param pApplicationId l'id de l'application
     * @return un booléen indiquant si l'utilisateur a les droits requis
     */
    protected abstract boolean checkRights( LogonBean pUser, Long pApplicationId );

    /**
     * @param pMapping l'actionMapping utilisée pour cette instance
     * @param pForm l'actionform bean utilisée (optionnel)
     * @param pRequest la requete http
     * @param pResponse la réponse http que l'on crée
     * @return l'ActionForward résultat de l'action
     * @throws Exception en cas d'échec
     */
    public ActionForward wExecute( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                   HttpServletResponse pResponse )
        throws Exception
    {
        // redirection vers la page d'erreur par défaut
        ActionForward fw = pMapping.findForward( "unauthorized_access" );
        UserDTO user = getUserAsDTO( pRequest );
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        ArrayList usersList = null;
        RootForm rootForm = (RootForm) pForm;
        initCacheAndForm( pRequest, rootForm );
        Long appliId = null;
        if ( !rootForm.getApplicationId().equals( "" ) )
        {
            appliId = new Long( rootForm.getApplicationId() );
            sessionUser.setCurrentAccess( (String) sessionUser.getProfile( "" + appliId ) );
        }
        try
        {
            if ( checkRights( sessionUser, appliId ) )
            { // Check des droits
                // Exécution du code de l'action car l'utilisateur a les droits
                fw = super.wExecute( pMapping, rootForm, pRequest, pResponse );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            ActionMessages errors = new ActionMessages();
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            fw = pMapping.findForward( "failure" );
        }
        // pour gérer tout ce qui est problème de session et de back
        pResponse.setHeader( "Cache-Control", "no-store" ); // HTTP 1.1
        pResponse.setHeader( "Pragma", "no-cache" ); // HTTP 1.0
        pResponse.setDateHeader( "Expires", 0 ); // prevents caching at the proxy server

        return fw;
    }

    /**
     * Initialise le dto
     * 
     * @param pRequest la requete http
     * @param pForm le formulaire
     * @throws JrafEnterpriseException en cas d'échec
     * @throws NumberFormatException en cas de problème de conversion
     * @throws WTransformerException si erreur lors de la transformation
     */
    private void initCacheAndForm( HttpServletRequest pRequest, RootForm pForm )
        throws NumberFormatException, JrafEnterpriseException, WTransformerException
    {
        // on vérifie que les informations ne sont pas présentes ni dans
        // les paramètres de la requete ni dans les attributs de la requete.
        // Si ils sont présents dans un des deux, on les récupère.
        String appliId = getParameterOrAttribute( pRequest, "applicationId" );
        String projectId = getParameterOrAttribute( pRequest, "projectId" );
        String currentAuditId = getParameterOrAttribute( pRequest, "currentAuditId" );
        String previousAuditId = getParameterOrAttribute( pRequest, "previousAuditId" );
        // On récupère le nom du projet courant, car sinon dans le cas d'une modification
        // on perd la modification
        String currentProjectName = pForm.getProjectName();
        // boite noire avec gestion en cache
        Map map = getDTOs( appliId, projectId, currentAuditId, previousAuditId, pRequest );
        // On reset les informations en cache du formulaire (sauf dans le cas de création
        // de l'application ou du projet)
        if ( null == pRequest.getParameter( DO_NOT_RESET_FORM ) )
        {
            pForm.resetCache();
        }
        // Création de l'application
        ComponentDTO appliDTO = (ComponentDTO) map.get( APPLI_DTO );
        if ( appliDTO != null )
        {
            pForm.setApplicationId( "" + appliDTO.getID() );
            pForm.setApplicationName( appliDTO.getName() );
            pForm.setNumberOfChildren( "" + appliDTO.getNumberOfChildren() );
        }
        // Création du projet
        ComponentDTO projectDTO = (ComponentDTO) map.get( PROJECT_DTO );
        if ( projectDTO != null )
        {
            pForm.setProjectId( "" + projectDTO.getID() );
            // On modifie tout le temps le nom du projet avec le nom du projet récupéré en base
            // sauf dans le cas ou on veut modifier le nom du projet
            if ( null == pRequest.getParameter( DO_NOT_UPDATE_PROJECT_NAME ) )
            {
                pForm.setProjectName( projectDTO.getName() );
            }
        }
        // Création de l'audit
        AuditDTO curAudit = (AuditDTO) map.get( CURRENT_AUDIT_DTO );
        if ( curAudit != null )
        {
            pForm.setCurrentAuditId( "" + curAudit.getID() );
            pForm.setAuditDate( SqualeWebActionUtils.getFormattedDate( pRequest.getLocale(), curAudit.getRealDate(),
                                                                       "date.format" ) );
            pForm.setAuditSqualeVersion( "" + curAudit.getSqualeVersion() );
            pForm.setAuditName( pForm.getAuditDate() );
            if ( curAudit.getType().equals( AuditBO.MILESTONE ) )
            {
                pForm.setAuditName( curAudit.getName() + " (" + pForm.getAuditDate() + ")" );
            }
        }
        // Création de l'audit précédent
        AuditDTO precAudit = (AuditDTO) map.get( PREVIOUS_AUDIT_DTO );
        if ( precAudit != null )
        {
            pForm.setPreviousAuditId( "" + precAudit.getID() );
            pForm.setPreviousAuditDate( SqualeWebActionUtils.getFormattedDate( pRequest.getLocale(),
                                                                               precAudit.getRealDate(), "date.format" ) );
            pForm.setPreviousAuditName( pForm.getPreviousAuditDate() );
            if ( precAudit.getType().equals( AuditBO.MILESTONE ) )
            {
                pForm.setPreviousAuditName( precAudit.getName() + " (" + pForm.getPreviousAuditDate() + ")" );
            }
        }
        // remet en session à chaque fois pour éviter de faire trop de tests
        pRequest.getSession().setAttribute( APPLI_DTO, appliDTO );
        pRequest.getSession().setAttribute( PROJECT_DTO, projectDTO );
        pRequest.getSession().setAttribute( CURRENT_AUDIT_DTO, curAudit );
        pRequest.getSession().setAttribute( PREVIOUS_AUDIT_DTO, precAudit );
        // Construit la liste des projets de l'application
        // et les met en session
        buildChildrenList( appliDTO, pRequest );
    }

    /**
     * Récupère une chaîne placé soit dans les paramètres, soit dans les attributs de la requête
     * 
     * @param pRequest la requête
     * @param pKey la clé de recherche
     * @return la chaîne
     */
    private String getParameterOrAttribute( HttpServletRequest pRequest, String pKey )
    {
        // On essaye de récupérer la chaîne dans les paramètres de la requête
        String result = pRequest.getParameter( pKey );
        if ( null == result )
        {
            // Sinon on essaye dans les attributs de la requête
            result = (String) pRequest.getAttribute( pKey );
        }
        return result;
    }

    /** 2 constantes permettant de savoir comment on récupère les audits */

    /** On a directement l'id de l'audit */
    private final static String BY_AUDIT_ID = "byAuditId";

    /** on a juste l'id de l'application, on récupère le dernier audit */
    private final static String BY_APPLI_ID = "byAppliId";

    /**
     * Gére la récupération des données soit en base soit depuis la session. Gestion par cache.
     * 
     * @param appliId l'id de l'appli
     * @param projectId l'id du projet
     * @param currentAuditId l'id de l'audit courant
     * @param previousAuditId l'id de l'audit précédent
     * @param pRequest la requete
     * @return la map contenant les différents dtos
     * @throws JrafEnterpriseException en cas de problème lors de la récupération
     * @throws NumberFormatException en cas de problème de conversion
     */
    private Map getDTOs( String appliId, String projectId, String currentAuditId, String previousAuditId,
                         HttpServletRequest pRequest )
        throws JrafEnterpriseException, NumberFormatException
    {
        Map map = new HashMap();
        // Initialisation à null par défaut
        ComponentDTO appliDTO = null;
        ComponentDTO projectDTO = null;
        AuditDTO currentAuditDTO = null;
        AuditDTO previousAuditDTO = null;
        AuditDTO[] tab = new AuditDTO[2];
        // indique si on a repéré un accès illégal
        boolean validAccess = true;
        // si on rentre ici, on a au moins l'application et le dernier audit
        if ( currentAuditId != null && !"".equals( currentAuditId ) )
        {
            // si previous auditId vaut null, on récupère celui juste avant le current
            // sinon on récupère celui avec l'identifiant donné
            // Au passage on récupère l'application
            tab = retrieveAuditAndPreviousByAuditId( currentAuditId, previousAuditId, pRequest );
            currentAuditDTO = tab[0];
            previousAuditDTO = tab[1];
            // Vérifie que les 2 audits concernent bien la meme application (sécurité)s
            validAccess =
                ( currentAuditDTO != null && ( previousAuditDTO == null || currentAuditDTO.getApplicationId() == previousAuditDTO.getApplicationId() ) );
            if ( validAccess )
            {
                // on peut récupérer l'application et/ou le projet
                // Si le projet est défini
                if ( projectId != null && !"".equals( projectId ) )
                {
                    projectDTO = retrieveAppliOrProjectByID( new Long( projectId ).longValue() );
                    appliDTO = retrieveAppliOrProjectByID( projectDTO.getIDParent() );
                }
                else
                {
                    // Si on a l'application de l'id, on la récupère directement
                    if ( appliId != null && !"".equals( appliId ) )
                    {
                        appliDTO = retrieveAppliOrProjectByID( new Long( appliId ).longValue() );
                    }
                    else
                    {
                        // sinon on la récupère avec l'audit
                        appliDTO = retrieveAppliOrProjectByAuditID( currentAuditDTO.getID() );
                    }
                }
            }
        }
        else
        { // on n'a pas d'audits spécifiés, on va récupérer les 2 plus récents
            // le projet est prioritaire
            if ( projectId != null && !"".equals( projectId ) )
            {
                projectDTO = retrieveAppliOrProjectByID( new Long( projectId ).longValue() );
                validAccess = ( projectDTO != null );
                if ( validAccess )
                {
                    appliDTO = retrieveAppliOrProjectByID( projectDTO.getIDParent() );
                }
            }
            else
            {
                if ( appliId != null && !"".equals( appliId ) )
                {
                    appliDTO = retrieveAppliOrProjectByID( new Long( appliId ).longValue() );
                }
            }
            // si on a pu récupérer l'application, on affecte la variable
            // pour pouvoir récupérer les projets
            String newAppliId = appliId;
            if ( appliDTO != null )
            {
                newAppliId = "" + appliDTO.getID();
            }
            if ( newAppliId != null && !"".equals( newAppliId ) )
            { // tout peut etre nul, page d'accueil par exemple
                // Récupère les 2 derniers audits
                tab = retrieve2LastAudits( new Long( newAppliId ).longValue(), pRequest, BY_APPLI_ID );
                currentAuditDTO = tab[0];
                previousAuditDTO = tab[1];
            }
        }
        // Dans le cas où il n'y a qu'un seul projet, comme les différentes actions redirigent
        // directement vers le projet il faut passer directement le projet et non pas l'application
        if ( projectDTO == null && appliDTO != null && appliDTO.getNumberOfChildren() == 1 )
        {
            projectDTO = retrieveProjectWhereApplication( appliDTO );
        }
        if ( projectDTO != null )
        {
            // Dans ce cas on reconstruit le menu tops car on va etre redirigé vers le projet
            // Mise en place du menu top contextuel à la grille dynamique
            // Récupération du menu top
            buildTopMenu( currentAuditDTO, projectDTO, pRequest );
        }
        initMap( map, appliDTO, projectDTO, currentAuditDTO, previousAuditDTO, validAccess );
        return map;
    }

    /**
     * @param pAuditId l'id de l'audit
     * @return l'application associée
     * @throws JrafEnterpriseException en cas d'échec
     */
    private ComponentDTO retrieveAppliOrProjectByAuditID( long pAuditId )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
        Object[] paramIn = { new Long( pAuditId ) };
        return (ComponentDTO) ac.execute( "loadByAuditId", paramIn );
    }

    /**
     * @param currentAuditId l'id de l'audit courant
     * @param previousAuditId l'id de l'audit précédent
     * @param pRequest la requete, sert pour formater les dates des audits
     * @return les audits DTO
     * @throws JrafEnterpriseException en cas de problème lors de la récupération
     * @throws NumberFormatException en cas de problème de conversion
     */
    private AuditDTO[] retrieveAuditAndPreviousByAuditId( String currentAuditId, String previousAuditId,
                                                          HttpServletRequest pRequest )
        throws JrafEnterpriseException, NumberFormatException
    {
        AuditDTO[] result = new AuditDTO[2];
        if ( previousAuditId == null || "".equals( previousAuditId ) )
        {
            result = retrieve2LastAudits( new Long( currentAuditId ).longValue(), pRequest, BY_AUDIT_ID );
        }
        else
        {
            result[0] = retrieveAuditById( new Long( currentAuditId ), pRequest );
            result[1] = retrieveAuditById( new Long( previousAuditId ), pRequest );
        }
        return result;
    }

    /**
     * @param pAuditId l'id de l'audit qu'on veut récupérer
     * @param pRequest la requete http
     * @return l'audit
     * @throws JrafEnterpriseException en cas de problème lors de la récupération
     */
    private AuditDTO retrieveAuditById( Long pAuditId, HttpServletRequest pRequest )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
        Object[] paramIn = { pAuditId };
        AuditDTO currentAudit = (AuditDTO) ac.execute( "getById", paramIn );
        if ( currentAudit.getRealDate() != null )
        {
            currentAudit.setFormattedDate( SqualeWebActionUtils.getFormattedDate( pRequest.getLocale(),
                                                                                  currentAudit.getRealDate(),
                                                                                  "date.format" ) );
        }
        return currentAudit;
    }

    /**
     * @param pMap la map
     * @param pAppliDTO l'application
     * @param pProjectDTO le projet
     * @param pCurrentAuditDTO l'audit courant
     * @param pPreviousAuditDTO l'audit précédent
     * @param pValidAccess le booléen indiquant si l'accès a vérifié les règles
     */
    private void initMap( Map pMap, ComponentDTO pAppliDTO, ComponentDTO pProjectDTO, AuditDTO pCurrentAuditDTO,
                          AuditDTO pPreviousAuditDTO, boolean pValidAccess )
    {
        // remplit la map
        // on remplit tout meme si c'est null pour ne pas avoir à faire de tests
        // null est une valeur autorisée et gérée
        if ( pValidAccess )
        { // Accès valide, on remplit normalement la map
            pMap.put( APPLI_DTO, pAppliDTO );
            pMap.put( PROJECT_DTO, pProjectDTO );
            pMap.put( CURRENT_AUDIT_DTO, pCurrentAuditDTO );
            pMap.put( PREVIOUS_AUDIT_DTO, pPreviousAuditDTO );
        }
        else
        { // accès incorrect, on met tout à null ce qui provoquera un pb de droit d'accès
            pMap.put( APPLI_DTO, null );
            pMap.put( PROJECT_DTO, null );
            pMap.put( CURRENT_AUDIT_DTO, null );
            pMap.put( PREVIOUS_AUDIT_DTO, null );
        }
    }

    /**
     * PRECONDITION: pApplication.getNumberOfChildren() == 1 est vrai
     * 
     * @param pApplication l'application parent
     * @return l'unique projet de l'application
     * @throws JrafEnterpriseException en cas d'échec
     */
    private ComponentDTO retrieveProjectWhereApplication( ComponentDTO pApplication )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
        Object[] paramIn = { pApplication };
        List children = Arrays.asList( ( (Collection) ac.execute( "getChildren", paramIn ) ).toArray() );
        return (ComponentDTO) children.get( 0 );

    }

    /**
     * récupère la liste des projets de l'application et les met en session
     * 
     * @param appliDTO l'application
     * @param pRequest la requete http
     * @throws JrafEnterpriseException en cas d'échec de récupération
     * @throws WTransformerException si erreur lors de la transformation en formulaire
     */
    private void buildChildrenList( ComponentDTO appliDTO, HttpServletRequest pRequest )
        throws JrafEnterpriseException, WTransformerException
    {
        if ( appliDTO == null )
        {
            pRequest.getSession().removeAttribute( PROJECTS_KEY );
        }
        else
        { // sinon on le remet à jour
            // et on remet à jour en session
            // Il existe plusieurs projets, on construit la liste
            // de ceux-ci pour renseigner le menu correspondant
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
            Object[] paramIn =
                { appliDTO, null, (AuditDTO) pRequest.getSession().getAttribute( CURRENT_AUDIT_DTO ), null };
            // List children = Arrays.asList(((Collection) ac.execute("getChildren", paramIn)).toArray());
            Collection children = (Collection) ac.execute( "getChildren", paramIn );
            // On transforme les projets en formulaire pour les utiliser dans les JSPs
            ProjectListForm childrenForm =
                (ProjectListForm) WTransformerFactory.objToForm( ProjectListTransformer.class,
                                                                 new Object[] { children } );
            pRequest.getSession().setAttribute( PROJECTS_KEY, childrenForm );
        }
    }

    /**
     * Permet de récupérer une application ou un projet
     * 
     * @param componentId l'id du composant que l'on veut
     * @return le composant sous la forme d'un dto
     * @throws JrafEnterpriseException en cas d'échec de la récupération
     */
    private ComponentDTO retrieveAppliOrProjectByID( long componentId )
        throws JrafEnterpriseException
    {
        // l'id est unique
        ComponentDTO appliParam = new ComponentDTO();
        appliParam.setID( componentId );
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
        Object[] paramIn = new Object[] { appliParam };
        return (ComponentDTO) ac.execute( "get", paramIn );
    }

    /**
     * Récupère les auditsDTO
     * 
     * @param id l'id servant à récupérer l'audit (id de l'application ou de l'audit) que l'on veut
     * @param pRequest la requete
     * @param pType indiquant comment on récupère les audits
     * @return un tableau contenant les 2 derniers audits ou null si ils n'existent pas
     * @throws JrafEnterpriseException en cas d'échec de la récupération
     */
    private AuditDTO[] retrieve2LastAudits( long id, HttpServletRequest pRequest, String pType )
        throws JrafEnterpriseException
    {
        AuditDTO currentAudit = null;
        AuditDTO previousAudit = null;
        AuditDTO[] tab = new AuditDTO[2];
        Collection coll = getAudits( pType, id );
        if ( coll.size() > 0 )
        { // il y a des audits pour cette application
            Iterator it = coll.iterator();
            // Le test précédent indique qu'il y a au moins 1 audit
            currentAudit = (AuditDTO) ( it.next() );
            // Test si il y en a un 2ieme dans la collection
            // <=> un audit précédent
            if ( it.hasNext() )
            {
                previousAudit = (AuditDTO) ( it.next() );
            }
            // format la date des audits
            if ( currentAudit != null && currentAudit.getRealDate() != null )
            {
                currentAudit.setFormattedDate( SqualeWebActionUtils.getFormattedDate( pRequest.getLocale(),
                                                                                      currentAudit.getRealDate(),
                                                                                      "date.format" ) );
            }
            if ( previousAudit != null && previousAudit.getRealDate() != null )
            {
                previousAudit.setFormattedDate( SqualeWebActionUtils.getFormattedDate( pRequest.getLocale(),
                                                                                       previousAudit.getRealDate(),
                                                                                       "date.format" ) );
            }
        }
        // remplit la map pour les audits
        tab[0] = currentAudit;
        tab[1] = previousAudit;
        return tab;
    }

    /**
     * @param pType la façon dont on va récupérer
     * @param pId l'id permettant le calcul
     * @return la collection d'audits
     * @throws JrafEnterpriseException en cas d'échec de la récupération
     */
    private Collection getAudits( String pType, long pId )
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList( 0 );
        // récupère les 2 derniers audits pour l'application
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
        if ( pType.equals( BY_AUDIT_ID ) )
        {
            // Récupération par l'id de l'audit
            AuditDTO auditRef = null;
            auditRef = new AuditDTO();
            auditRef.setID( pId );
            Object[] paramIn = new Object[] { auditRef };
            result = (Collection) ac.execute( "getLastTwoAuditsByAuditId", paramIn );
            // s'il il n'y a aucun audit terminé, on récupère les 2 derniers failed
            if ( result == null || result.size() == 0 )
            {
                paramIn = new Object[] { auditRef };
                result = (Collection) ac.execute( "getLastTwoAuditsByAuditId", paramIn );
            }
        }
        if ( pType.equals( BY_APPLI_ID ) )
        {
            // Récupération par l'id de l'application
            ComponentDTO appliParam = new ComponentDTO();
            appliParam.setID( pId );
            Object[] paramIn = new Object[] { appliParam, null, new Integer( 2 ), new Integer( AuditBO.TERMINATED ) };
            result = (Collection) ac.execute( "getLastAllAudits", paramIn );
            // sil il n'y a aucun audit terminé, on récupère les 2 derniers failed
            if ( result == null || result.size() == 0 )
            {
                paramIn = new Object[] { appliParam, null, new Integer( 2 ), new Integer( AuditBO.FAILED ) };
                result = (Collection) ac.execute( "getLastAllAudits", paramIn );
            }
        }
        return result;
    }

    /**
     * @param currentAudit l'audit
     * @param projectDTO le projet
     * @param pRequest la requete http
     * @throws JrafEnterpriseException en cas d'échec
     */
    private void buildTopMenu( AuditDTO currentAudit, ComponentDTO projectDTO, HttpServletRequest pRequest )
        throws JrafEnterpriseException
    {
        HashMap topMenu = new HashMap();
        // Si le currentAudit n'est pas renseigné, pas de menu tops
        // Code protecteur car suivant d'ou on vient il peut parfois etre nul
        if ( currentAudit != null )
        {
            // On va récupérer la grille qualité correspondant au dernier audit
            IApplicationComponent acResult = AccessDelegateHelper.getInstance( "Results" );
            AuditGridDTO auditGridDto =
                (AuditGridDTO) acResult.execute( "getProjectAuditGrid", new Object[] { projectDTO, currentAudit } );
            // On construit ensuite le menu des grilles
            IApplicationComponent acGrid = AccessDelegateHelper.getInstance( "QualityGrid" );
            topMenu = (HashMap) acGrid.execute( "getGridMetrics", new Object[] { auditGridDto.getGrid() } );
        }
        pRequest.getSession().setAttribute( SqualeWebConstants.TOP_KEY, topMenu );
    }

    /**
     * Add an user access to the application with id <code>pAppliId</code>
     * 
     * @param pRequest request
     * @param pAppliId id of application
     * @throws JrafEnterpriseException if error
     */
    public void addUserAccess( HttpServletRequest pRequest, long pAppliId )
        throws JrafEnterpriseException
    {
        // If the user is not a SQUALE administrator,
        // we save access
        LogonBean user = (LogonBean) getWILogonBean( pRequest );
        if ( !user.isAdmin() )
        {
            String matricule = user.getMatricule();
            Integer maxAccesses =
                new Integer( Integer.parseInt( WebMessages.getString( pRequest, "application.max.accesses" ) ) );
            Object[] accessParams = new Object[] { new Long( pAppliId ), matricule, maxAccesses };
            AccessDelegateHelper.getInstance( "ApplicationAdmin" ).execute( "addUserAccess", accessParams );
        }
    }
}
