package com.airfrance.squaleweb.applicationlayer.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.action.message.AdminNewsAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ApplicationListTransformer;
import com.airfrance.squaleweb.transformer.SplitAuditsListTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * @version 1.0
 * @author
 */
public class IndexAction
    extends DefaultAction
{

    /**
     * Nombre de jours sur lequel on afficher les audits réalisés dans la page de news
     */
    public static final int NUMBER_OF_DAYS_FOR_NEWS = 15;

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

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try
        {
            SplitAuditsListForm auditList = (SplitAuditsListForm) pForm;
            // On récupère les applications non publiques appartenant à l'utilisateur
            Collection applications = getUserNotPublicApplicationList( pRequest );
            // On récupère les publiques
            Collection publics = getUserPublicApplicationList( pRequest );
            // Recherche des audits des 15 derniers jours
            // Calcul de la date d'ancienneté d'audit
            Calendar cal = Calendar.getInstance();
            cal.add( Calendar.DATE, -NUMBER_OF_DAYS_FOR_NEWS );
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
            boolean displayAllAudits = auditList.isAllAudits();
            ApplicationListForm appliForm = new ApplicationListForm();
            appliForm.setList( (ArrayList) applications );
            ArrayList userApplicationsDTO =
                (ArrayList) WTransformerFactory.formToObj( ApplicationListTransformer.class, appliForm )[0];
            Object[] paramIn = { userApplicationsDTO, cal.getTime(), new Boolean( true ) };
            if ( displayAllAudits )
            {
                paramIn[1] = null;
            }
            // les audits en échec sont écartés
            paramIn[2] = new Boolean( false );
            Collection audits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );
            appliForm.setList( (ArrayList) publics );
            paramIn[0] = (ArrayList) WTransformerFactory.formToObj( ApplicationListTransformer.class, appliForm )[0];
            // prise en compte des audits en échec
            paramIn[2] = new Boolean( true );
            Collection publicAudits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );
            // On transforme les listes en formulaire
            WTransformerFactory.objToForm( SplitAuditsListTransformer.class, (WActionForm) pForm, publicAudits, audits );

            // Récupère la liste des news et les met en session
            AdminNewsAction newsAction = new AdminNewsAction();
            // Pour ne récupérer que les news valides
            pRequest.setAttribute( "which", "current" );
            // avec la langue correspondant à celle du navigateur
            pRequest.setAttribute( "lang", pRequest.getLocale().getLanguage() );
            newsAction.listNews( pMapping, pForm, pRequest, pResponse );

            // On récupère l'application
            // L'utilisateur accède à ses applications, si il n'est pas administrateur SQUALE,
            // on enregistre l'accès
            LogonBean user = (LogonBean) getWILogonBean( pRequest );
            if ( !user.isAdmin() )
            {
                String matricule = pRequest.getRemoteUser();
                Integer maxAccesses =
                    new Integer( Integer.parseInt( WebMessages.getString( pRequest, "application.max.accesses" ) ) );
                Object[] accessParams = new Object[] { userApplicationsDTO, matricule, maxAccesses };
                AccessDelegateHelper.getInstance( "ApplicationAdmin" ).execute( "addUserAccess", accessParams );
            }

            forward = pMapping.findForward( "success" );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        return forward;
    }

    /**
     * Enregistre l'utilisateur en session
     * 
     * @param pForm le formulaire
     * @param pRequest la requête
     * @return true si l'utilisateur a pu être mis en session
     */
    public boolean initUserSession( ActionForm pForm, HttpServletRequest pRequest )
    {
        boolean sessionOk;

        ActionMessages errors = new ActionMessages();

        try
        {
            SplitAuditsListForm auditList = (SplitAuditsListForm) pForm;
            // On récupère les applications non publiques appartenant à l'utilisateur
            Collection applications = getUserNotPublicApplicationList( pRequest );
            // On récupère les publiques
            Collection publics = getUserPublicApplicationList( pRequest );
            // Recherche des audits des 15 derniers jours
            // Calcul de la date d'ancienneté d'audit
            Calendar cal = Calendar.getInstance();
            cal.add( Calendar.DATE, -NUMBER_OF_DAYS_FOR_NEWS );
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
            boolean displayAllAudits = auditList.isAllAudits();
            ApplicationListForm appliForm = new ApplicationListForm();
            appliForm.setList( (ArrayList) applications );
            Object[] paramIn =
                { (ArrayList) WTransformerFactory.formToObj( ApplicationListTransformer.class, appliForm )[0],
                    cal.getTime(), new Boolean( true ) };
            if ( displayAllAudits )
            {
                paramIn[1] = null;
            }
            Collection audits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );
            appliForm.setList( (ArrayList) publics );
            paramIn[0] = (ArrayList) WTransformerFactory.formToObj( ApplicationListTransformer.class, appliForm )[0];
            Collection publicAudits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );
            // On transforme les listes en formulaire
            WTransformerFactory.objToForm( SplitAuditsListTransformer.class, (WActionForm) pForm, publicAudits, audits );
            sessionOk = true;
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            sessionOk = false;
        }
        return sessionOk;
    }

}
