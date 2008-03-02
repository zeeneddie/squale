package com.airfrance.squaleweb.applicationlayer.action.message;

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
import com.airfrance.squaleweb.messages.MessageProvider;
import com.airfrance.squaleweb.resources.DataBaseMessages;
import com.airfrance.squaleweb.transformer.message.MessagesDTOTransformer;

/**
 * Action sur les messages
 */
public class MessageAction
    extends AdminAction
{
    /**
     * Importation d'un fichier de messages
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward importMessages( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                         HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward;
        try
        {
            UploadFileForm form = (UploadFileForm) pForm;
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Messages" );
            StringBuffer importErrors = new StringBuffer();
            MessageProvider messages =
                MessagesDTOTransformer.transform( ac.execute( "importMessages", new Object[] { form.getInputStream(),
                    importErrors } ) );
            if ( importErrors.length() > 0 )
            {
                // Affichage des messages d'erreur
                // obtenus pendant l'importation
                ActionMessage error = new ActionMessage( "message_admin.errors", importErrors.toString() );
                errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                forward = pMapping.findForward( "fail" );
            }
            else
            {
                // On place un message de confirmation de chargement
                ActionMessage message = new ActionMessage( "message_admin.loaded" );
                errors.add( ActionMessages.GLOBAL_MESSAGE, message );
                // Mise à jour des messages
                DataBaseMessages.update( messages );
                // Redirection vers la page courante
                forward = pMapping.findForward( "success_messages" );
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

    /**
     * Rechargement des messages
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward reloadMessages( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                         HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward;
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Messages" );
            MessageProvider messages = MessagesDTOTransformer.transform( ac.execute( "getMessages" ) );
            DataBaseMessages.update( messages );
            // On place un message de confirmation de chargement
            ActionMessage message = new ActionMessage( "message_admin.reloaded" );
            errors.add( ActionMessages.GLOBAL_MESSAGE, message );
            // Redirection vers la page courante
            forward = pMapping.findForward( "success_messages" );
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
