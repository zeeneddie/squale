package com.airfrance.squaleweb.applicationlayer.action.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.message.NewsDTO;
import com.airfrance.squalecommon.datatransfertobject.message.NewsListDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.NewsListForm;
import com.airfrance.squaleweb.transformer.message.NewsListTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class StandardUserNewsAction
    extends DefaultAction
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
        ActionForward forward = pMapping.findForward( "viewNews" );
        try
        {
            // Peut etre null, la date n'a pas d'importance
            String which = pRequest.getParameter( "which" );
            // Peut etre null, la langue n'a pas d'importance
            String lang = pRequest.getParameter( "lang" );
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Messages" );
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
