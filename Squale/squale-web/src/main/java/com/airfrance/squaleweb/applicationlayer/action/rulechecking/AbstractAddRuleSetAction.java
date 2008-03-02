package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.applicationlayer.formbean.UploadFileForm;

/**
 * Importation de ruleset
 */
public abstract class AbstractAddRuleSetAction
    extends AdminAction
{
    /**
     * Obtention du nom de l'access component
     * 
     * @return nom de l'access component lié à l'action
     */
    protected abstract String getAccessComponentName();

    /**
     * Importation d'un fichier de configuration CppTest
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward importConfiguration( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                              HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward;
        try
        {
            UploadFileForm form = (UploadFileForm) pForm;
            IApplicationComponent ac = AccessDelegateHelper.getInstance( getAccessComponentName() );
            StringBuffer importErrors = new StringBuffer();
            ac.execute( "importConfiguration", new Object[] { form.getInputStream(), importErrors } );
            if ( importErrors.length() > 0 )
            {
                // Affichage des messages d'erreur
                // obtenus pendant l'importation
                ActionMessage error = new ActionMessage( "rulechecking.file.fail", importErrors.toString() );
                errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                forward = pMapping.findForward( "failure" );
            }
            else
            {
                ActionMessage message = new ActionMessage( "rulechecking.file.success" );
                errors.add( ActionMessages.GLOBAL_MESSAGE, message );
                // Redirection vers la page courante
                forward = pMapping.findForward( "success" );
            }
        }
        catch ( Exception e )
        {
            // Traitement des exceptions
            handleException( e, errors, pRequest );
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

}
