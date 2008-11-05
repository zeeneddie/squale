package com.airfrance.squaleweb.applicationlayer.action;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.config.AdminParamsDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.UserForm;
import com.airfrance.squaleweb.connection.AuthenticationBean;
import com.airfrance.squaleweb.connection.IUserBeanAccessor;
import com.airfrance.squaleweb.connection.UserBeanAccessorHelper;
import com.airfrance.squaleweb.connection.exception.ConnectionException;
import com.airfrance.squaleweb.servlet.UserSqualeSessionContext;
import com.airfrance.squaleweb.transformer.LogonBeanTransformer;
import com.airfrance.squaleweb.transformer.UserTransformer;
import com.airfrance.squaleweb.util.ExceptionWrapper;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Struts Action that handles login into Squale.
 * 
 * @version 1.0
 * @author
 */
public class LoginAction
    extends DefaultAction
{
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( LoginAction.class );

    /**
     * ${@inheritDoc}
     */
    @Override
    public ActionForward execute( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        boolean sessionOk = initUserInSession( pRequest );
        if ( sessionOk )
        {

            // On redirige vers la gestion de son compte si l'email OU le nom n'est pas renseigné
            // Sinon on redirige vers la page d'accueil
            LogonBean logonBean = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
            if ( logonBean.getEmail().length() == 0 || logonBean.getUsername() == null
                || logonBean.getUsername().length() == 0 )
            {
                forward = pMapping.findForward( "email" );
            }
            else
            {
                String requestedPagePath = (String) pRequest.getSession().getAttribute( "requestedPagePath" );
                if ( requestedPagePath != null )
                {
                    // a specific page was required prior to login: go there
                    forward = new ActionForward( requestedPagePath, true );
                }
                else
                {
                    // normal login: go to main page
                    forward = pMapping.findForward( "success" );
                }
            }
        }
        else
        {
            forward = pMapping.findForward( "total_failure" );
        }

        return forward;
    }

    /**
     * Inits the user in session
     * 
     * @param pRequest la requete
     * @return true si l'utilisateur a pu être initialisé et enregistré en session
     */
    public boolean initUserInSession( HttpServletRequest pRequest )
    {
        ActionMessages errors = new ActionMessages();
        boolean success;
        try
        {

            LogonBean logonBeanSecurity = getUser( pRequest );

            pRequest.getSession().setAttribute( WConstants.USER_KEY, logonBeanSecurity );
            success = true;

            // Set the Squale's administrator mailing list
            configAdminMailingList( pRequest.getSession() );
        }
        catch ( Exception e )
        {
            // On ecrit l'erreur et on la sauvegarde pour affichage dans la jsp
            log.error( e, e );
            ExceptionWrapper.saveException( pRequest, e );
            // On invalide la session
            pRequest.getSession().invalidate();
            ActionMessage error = new ActionMessage( "error.cannot_act" );
            errors.add( ActionMessages.GLOBAL_MESSAGE, error );
            saveMessages( pRequest, errors );
            success = false;
        }
        return success;
    }

    /**
     * Returns the bean that stands for the authenticated user.
     * 
     * @param pRequest la requête
     * @return l'utilisateur connecté sous forme de LoginBean
     * @throws ConnectionException si erreur de récupération de l'utilisateur connecté
     * @throws JrafEnterpriseException si erreur Jraf
     * @throws WTransformerException si erreur lors de la transformation
     */
    public LogonBean getUser( HttpServletRequest pRequest )
        throws ConnectionException, JrafEnterpriseException, WTransformerException
    {
        // Obtention des informations sur l'utilisateur connecté

        AuthenticationBean authent = (AuthenticationBean) pRequest.getSession().getAttribute( "AuthenticatedUser" );
        String name = authent.getIdentifier();
        IUserBeanAccessor userBeanAccessor = UserBeanAccessorHelper.getUserBeanAccessor();
        boolean isAdmin = userBeanAccessor.getUserBean( pRequest ).isAdmin();
        UserDTO user = new UserDTO();
        user.setMatricule( name );

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Login" );
        Object[] paramIn = { user, Boolean.valueOf( isAdmin ) };

        user = (UserDTO) ac.execute( "verifyUser", paramIn );
        UserForm userForm = (UserForm) WTransformerFactory.objToForm( UserTransformer.class, user );
        LogonBean logonBeanSecurity = new LogonBean();
        WTransformerFactory.formToObj( LogonBeanTransformer.class, userForm, new Object[] { logonBeanSecurity,
            Boolean.valueOf( isAdmin ) } );

        return logonBeanSecurity;
    }

    /**
     * This method set in session a variable which contains Squale's administrator mailing list. The information set in
     * this variable come from the database
     * 
     * @param session The http session
     * @throws JrafEnterpriseException Error happen during the search in the database
     */
    private void configAdminMailingList( HttpSession session )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Mail" );
        String mailingList = ac.execute( "getAdminMailingList" ).toString();
        UserSqualeSessionContext sessionContext = UserSqualeSessionContext.getContext( session );
        sessionContext.setSqualeAdminsMailingList( mailingList );
        UserSqualeSessionContext.setContext( session, sessionContext );
    }

}
