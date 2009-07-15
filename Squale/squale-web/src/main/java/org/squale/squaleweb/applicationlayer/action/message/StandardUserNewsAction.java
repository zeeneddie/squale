/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squaleweb.applicationlayer.action.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.message.NewsDTO;
import org.squale.squalecommon.datatransfertobject.message.NewsListDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.applicationlayer.formbean.messages.NewsListForm;
import org.squale.squaleweb.transformer.message.NewsListTransformer;
import org.squale.welcom.struts.transformer.WTransformerFactory;

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
