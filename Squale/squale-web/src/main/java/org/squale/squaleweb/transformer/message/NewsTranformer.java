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

import org.squale.squalecommon.datatransfertobject.message.MessageDTO;
import org.squale.squalecommon.datatransfertobject.message.NewsDTO;
import org.squale.squaleweb.applicationlayer.formbean.messages.MessageForm;
import org.squale.squaleweb.applicationlayer.formbean.messages.NewsForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * 
 */
public class NewsTranformer
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
        NewsForm form = new NewsForm();
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
        NewsDTO newsDTO = (NewsDTO) pObject[0];
        NewsForm form = (NewsForm) pForm;
        form.setLangSet( newsDTO.getLangSet() );
        form.setBeginningDate( newsDTO.getBeginningDate() );
        form.setEndDate( newsDTO.getEndDate() );
        form.setKey( newsDTO.getKey() );
        form.setId( newsDTO.getId() );
        MessageForm messForm =
            (MessageForm) WTransformerFactory.objToForm( MessTransformer.class, newsDTO.getMessage() );
        form.setMessage( messForm );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new NewsDTO() };
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
        NewsForm newsForm = (NewsForm) pForm;
        NewsDTO dto = (NewsDTO) pObject[0];
        MessageDTO messDto =
            (MessageDTO) WTransformerFactory.formToObj( MessTransformer.class, newsForm.getMessage() )[0];
        dto.setLangSet( newsForm.getLangSet() );
        dto.setMessage( messDto );
        dto.setId( newsForm.getId() );
        dto.setBeginningDate( newsForm.getBeginningDate() );
        dto.setEndDate( newsForm.getEndDate() );
        dto.setKey( newsForm.getKey() );
    }

}
