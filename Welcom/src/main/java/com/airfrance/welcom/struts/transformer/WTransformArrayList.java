/*
 * Créé le 25 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.transformer;

import java.util.ArrayList;
import java.util.Iterator;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WTransformArrayList
{

    /**
     * Transforme une liste de BO/DTO en un liste de Bean Struts en fonction du Transformer
     * 
     * @param list : liste de BO/DTO
     * @param transformer : Le transformer (ecrit par le developpeur)
     * @return liste de bean a la sauce Struts
     * @throws WTransformerException Erreur a la convertion
     */
    public static ArrayList objToForm( final ArrayList list, final WITransformer transformer )
        throws WTransformerException
    {
        final ArrayList returnList = new ArrayList();
        final Iterator iter = list.iterator();

        while ( iter.hasNext() )
        {
            final Object obj[] = { iter.next() };
            returnList.add( transformer.objToForm( obj ) );
        }

        return returnList;
    }

    /**
     * Transforme une liste de Bean Struts en une liste de BO/DTO en fonction du Transformer
     * 
     * @param listForm : liste de bean struts
     * @param transformer : Le transformer (ecrit par le developpeur)
     * @return Liste de DO/BO Jraf
     * @throws WTransformerException Erreur a la transformation
     */
    public static ArrayList formToObj( final ArrayList listForm, final WITransformer transformer )
        throws WTransformerException
    {
        final ArrayList returnList = new ArrayList();
        final Iterator iter = listForm.iterator();

        while ( iter.hasNext() )
        {
            returnList.add( transformer.formToObj( (WActionForm) iter.next() ) );
        }

        return returnList;
    }

    /**
     * ???
     * 
     * @param listForm listForm : liste de bean struts
     * @param listObject Liste des objet
     * @param transformer : le transformer
     * @throws WTransformerException : Probleme sur le transformation
     */
    public static void formToObj( final ArrayList listForm, final ArrayList listObject, final WITransformer transformer )
        throws WTransformerException
    {
        WActionForm formElement;
        final Object obj[] = new Object[1];

        for ( int i = 0; i < listForm.size(); i++ )
        {
            formElement = (WActionForm) listForm.get( i );
            obj[0] = listObject.get( i );
            transformer.formToObj( formElement, obj );
        }
    }
}