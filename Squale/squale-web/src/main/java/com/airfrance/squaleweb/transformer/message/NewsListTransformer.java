package com.airfrance.squaleweb.transformer.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.message.NewsDTO;
import com.airfrance.squalecommon.datatransfertobject.message.NewsListDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.NewsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.NewsListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

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
