package com.airfrance.squaleweb.applicationlayer.action.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import com.airfrance.squalecommon.datatransfertobject.message.NewsDTO;
import com.airfrance.squalecommon.datatransfertobject.message.NewsListDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.NewsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.NewsListForm;
import com.airfrance.squaleweb.transformer.message.NewsListTransformer;
import com.airfrance.squaleweb.transformer.message.NewsTranformer;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * 
 */
public class AdminNewsAction
    extends AdminAction
{

    /**
     * Permet de lister les news ou les messages pour l'administrateur
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward listNews( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                   HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = null;
        try
        {
            // Peut etre null, la date n'a pas d'importance
            String which = pRequest.getParameter( "which" );
            if ( which == null )
            {
                // On essaye de le récupéré dans les attributs
                which = (String) pRequest.getAttribute( "which" );
            }
            // Peut etre null, la langue n'a pas d'importance
            String lang = pRequest.getParameter( "lang" );
            if ( lang == null )
            {
                lang = (String) pRequest.getAttribute( "lang" );
            }
            // Appel de la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Messages" );
            // On récupères les langues possibles
            Collection langs = (Collection) ac.execute( "findLangs" );
            Object[] paramIn = new Object[] { which, lang };
            // Renvoie une collection de newsDto
            NewsListDTO dtoList = (NewsListDTO) ac.execute( "getNews", paramIn );
            paramIn = new Object[0];

            Collection coll = dtoList.getNewsList();
            Collection result = new ArrayList( 0 );
            // ajout de l'ensembre des langues disponibles
            if ( coll != null )
            {
                Iterator it = coll.iterator();
                while ( it.hasNext() )
                {
                    NewsDTO dto = (NewsDTO) it.next();
                    // ajout de l'ensemble de langues
                    dto.setLangSet( langs );
                    result.add( dto );
                }
            }
            dtoList.setNewsList( result );
            NewsListForm newsListForm =
                (NewsListForm) WTransformerFactory.objToForm( NewsListTransformer.class, dtoList );
            // on ne met le form en session que si il y a des news
            if ( newsListForm.getNewsList().size() != 0 )
            {
                pRequest.getSession().setAttribute( "newsListForm", newsListForm );
            }
            else
            { // sinon on le supprime de la session
                pRequest.getSession().removeAttribute( "newsListForm" );
            }
            if ( pRequest.getParameter( "kind" ) != null )
            {
                forward = pMapping.findForward( "viewNews" );
            }
            else
            {
                forward = pMapping.findForward( "success_news" );
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
     * Permet de supprimer des nouveautés
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward purge( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward();
        try
        {
            // Récupération des news sélectionnées par checkbox
            NewsListForm form = (NewsListForm) pForm;
            List newsSelected = getSelection( null, form.getNewsList() );
            // Vérification de la sélection
            if ( newsSelected.size() == 0 )
            {
                pRequest.setAttribute( "SelectionProblem", "news.emptySelection" );
                forward = pMapping.findForward( "success_purge" );
            }
            else
            {
                ActionErrors messages = new ActionErrors();
                // Parcours de la sélection et purge de chaque audit
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Messages" );
                Object[] paramIn = { null };
                Iterator iterator = newsSelected.iterator();
                while ( iterator.hasNext() )
                {
                    NewsForm newsForm = (NewsForm) iterator.next();
                    NewsDTO newsDTO = (NewsDTO) WTransformerFactory.formToObj( NewsTranformer.class, newsForm )[0];
                    paramIn[0] = ( newsDTO );
                    ac.execute( "purgeNews", paramIn );
                    // Construction d'un message de confirmation pour chaque
                    // audit purgé
                    ActionMessage message = new ActionMessage( "info.purge_news", newsDTO.getKey() );
                    messages.add( ActionMessages.GLOBAL_MESSAGE, message );
                }
                saveMessages( pRequest, messages );
                pRequest.removeAttribute( "EmptySelection" );
                forward = pMapping.findForward( "success_purge" );
            }
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward checkModify( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                      HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward();
        NewsListForm form = (NewsListForm) pForm;
        // élément indiquant qu'on est
        // en train de modifier et pas d'ajouter
        pRequest.getSession().setAttribute( "modify", "true" );
        try
        {
            List newsSelected = getSelection( null, form.getNewsList() );
            // Vérification de la sélection
            // on ne doit avoir qu'un seul audit de sélectionné
            if ( newsSelected.size() != 1 )
            {
                if ( newsSelected.size() == 0 )
                {
                    pRequest.setAttribute( "SelectionProblem", "news.emptySelection" );
                }
                else
                {
                    pRequest.setAttribute( "SelectionProblem", "news.notUniqueSelection" );
                }
                forward = pMapping.findForward( "success_purge" );
            }
            else
            {
                pRequest.getSession().setAttribute( "newsForm", (NewsForm) newsSelected.get( 0 ) );
                // redirection vers la page commune à l'ajout et à la modification
                forward = pMapping.findForward( "addOrModify" );
            }
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward checkAdd( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                   HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward();
        NewsListForm form = (NewsListForm) pForm;
        // suppression de l'élément indiquant qu'on est
        // en train de modifier et pas d'ajouter
        pRequest.getSession().removeAttribute( "modify" );
        try
        {
            // on ne fait pas de tests sur les éventuels audits sélectionnés
            // on peut ajouter quel que soit l'état de la sélection
            // on ajoute la liste des langues disponibles pour la sélection au form à ajouter
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Messages" );
            Collection langs = (Collection) ac.execute( "findLangs" );
            NewsForm addedForm = new NewsForm();
            addedForm.setLangSet( langs );
            pRequest.getSession().setAttribute( "newsForm", addedForm );
            forward = pMapping.findForward( "addOrModify" );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Récupère la sélection des news à partir du paramètre et de la liste fournie.
     * 
     * @param pParam le paramètre déterminant l'id de l'audit sélectionné.
     * @param pColl la liste des news (NewsForm).
     * @return la liste des audits sélectionnés.
     */
    public List getSelection( final Object pParam, final Collection pColl )
    {
        ArrayList newsSelected = new ArrayList( 0 );
        if ( null == pParam )
        {
            if ( pColl != null )
            {
                // S'il s'agit d'une sélection par check boxes
                Iterator it = pColl.iterator();
                NewsForm newsForm;
                // Parcours de la liste des audits
                while ( it.hasNext() )
                {
                    newsForm = (NewsForm) it.next();
                    // Ajout des audits sélectionnés
                    if ( newsForm.isSelected() )
                    {
                        newsSelected.add( newsForm );
                    }
                }
            }
        }
        else
        {
            // Le paramètre fournit l'id de l'audit recherché
            Long objId = new Long( (String) pParam );
            Iterator it = pColl.iterator();
            NewsForm newsForm;
            // Parcours de la liste des audits à la recherche de l'audit
            // la liste est supposée courte pour ne pas ajouter une condition de sortie
            while ( it.hasNext() )
            {
                newsForm = (NewsForm) it.next();
                if ( newsForm.getId() == objId.longValue() )
                {
                    newsSelected.add( newsForm );
                }
            }
        }
        return newsSelected;
    }
}
