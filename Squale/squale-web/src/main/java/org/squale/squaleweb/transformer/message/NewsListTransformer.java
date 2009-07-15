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
package org.squale.squaleweb.transformer.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.message.NewsDTO;
import org.squale.squalecommon.datatransfertobject.message.NewsListDTO;
import org.squale.squaleweb.applicationlayer.formbean.messages.NewsForm;
import org.squale.squaleweb.applicationlayer.formbean.messages.NewsListForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * 
 */
public class NewsListTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        NewsListForm form = new NewsListForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        NewsListForm form = (NewsListForm) pForm;
        NewsListDTO dto = (NewsListDTO) pObject[0];
        // conversion de la collection de dtos
        Collection coll = dto.getNewsList();
        Collection collResult = new ArrayList( 0 );
        if ( coll != null )
        {
            Iterator it = coll.iterator();
            while ( it.hasNext() )
            {
                NewsDTO newsDto = (NewsDTO) it.next();
                NewsForm newsForm = (NewsForm) ( WTransformerFactory.objToForm( NewsTranformer.class, newsDto ) );
                collResult.add( newsForm );
            }
        }
        form.setNewsList( collResult );

    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new NewsListDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        NewsListForm newsForm = (NewsListForm) pForm;
        NewsListDTO dto = (NewsListDTO) pObject[0];
        // conversion de la collection de forms
        Collection coll = newsForm.getNewsList();
        Collection collResult = null;
        if ( coll != null )
        {
            Iterator it = coll.iterator();
            while ( it.hasNext() )
            {
                NewsForm form = (NewsForm) it.next();
                NewsDTO newsDto = (NewsDTO) ( WTransformerFactory.formToObj( NewsTranformer.class, form ) )[0];
                collResult.add( newsDto );
            }
        }
        dto.setNewsList( collResult );
    }
}
