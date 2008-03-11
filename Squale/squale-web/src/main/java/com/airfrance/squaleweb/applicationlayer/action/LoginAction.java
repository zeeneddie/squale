package com.airfrance.squaleweb.applicationlayer.action;

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
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.UserForm;
import com.airfrance.squaleweb.connection.AuthenticationBean;
import com.airfrance.squaleweb.connection.IUserBeanAccessor;
import com.airfrance.squaleweb.connection.UserBeanAccessorHelper;
import com.airfrance.squaleweb.connection.exception.ConnectionException;
import com.airfrance.squaleweb.transformer.LogonBeanTransformer;
import com.airfrance.squaleweb.transformer.UserTransformer;
import com.airfrance.squaleweb.util.ExceptionWrapper;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
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
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
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
                forward = pMapping.findForward( "success" );
            }
        }
        else
        {
            forward = pMapping.findForward( "total_failure" );
        }

        return forward;
    }

    /**
     * @param pRequest la requpete
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
        
        AuthenticationBean authent =(AuthenticationBean)pRequest.getSession().getAttribute( "AuthenticatedUser" );
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

}
