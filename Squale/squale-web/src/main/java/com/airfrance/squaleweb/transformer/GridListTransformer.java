package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.airfrance.squaleweb.applicationlayer.formbean.component.GridForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.GridListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une liste de grilles qualité
 */
public class GridListTransformer
    extends AbstractListTransformer
{

    /**
     * @param pObject le tableau de QualityGridDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        GridListForm form = new GridListForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject le tableau de QualityGridDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ArrayList listGridsDTO = (ArrayList) pObject[0];
        GridListForm gridListForm = (GridListForm) pForm;
        ArrayList listGridForm = new ArrayList();
        if ( null != listGridsDTO )
        {
            Iterator it = listGridsDTO.iterator();
            while ( it.hasNext() )
            {
                listGridForm.add( WTransformerFactory.objToForm( GridTransformer.class, it.next() ) );
            }
        }
        gridListForm.setGrids( listGridForm );
        if ( pObject.length > 1 )
        {
            // On récupère les grilles sans profil ni audit
            ArrayList uListGridForm = new ArrayList();
            for ( Iterator uIt = ( (ArrayList) pObject[1] ).iterator(); uIt.hasNext(); )
            {
                uListGridForm.add( WTransformerFactory.objToForm( GridTransformer.class, uIt.next() ) );
            }
            gridListForm.setUnlinkedGrids( uListGridForm );
        }
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de QualityGridDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        ArrayList listObject = (ArrayList) pObject[0];
        ListIterator it = ( (GridListForm) pForm ).getGrids().listIterator();
        while ( it.hasNext() )
        {
            GridForm gridForm = (GridForm) it.next();
            if ( gridForm.isSelected() )
            {
                listObject.add( WTransformerFactory.formToObj( GridTransformer.class, gridForm )[0] );
            }
        }
    }
}
