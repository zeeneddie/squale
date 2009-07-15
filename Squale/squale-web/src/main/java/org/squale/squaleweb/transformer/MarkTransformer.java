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
package org.squale.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.result.ResultsDTO;
import org.squale.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import org.squale.squaleweb.applicationlayer.formbean.results.ComponentForm;
import org.squale.squaleweb.applicationlayer.formbean.results.MarkForm;
import org.squale.squaleweb.util.SqualeWebActionUtils;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des notes
 */
public class MarkTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        MarkForm result = new MarkForm();
        objToForm( object, result );
        return result;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm pForm )
        throws WTransformerException
    {
        // Récupération des paramètres
        MarkForm markForm = (MarkForm) pForm;
        int index = 0;
        ResultsDTO result = (ResultsDTO) object[index++];
        FactorRuleDTO factor = (FactorRuleDTO) object[index++];
        PracticeRuleDTO practice = (PracticeRuleDTO) object[index++];
        // Si il reste un seul élément, c'est une note
        if ( object.length == index + 1 )
        {
            markForm.setMarkValue( ( (Integer) object[index++] ).intValue() );
            markForm.setMinMark( -1 );
            markForm.setMaxMark( -1 );
        }
        // si il en reste deux , c'est un interval
        if ( object.length == index + 2 )
        {
            markForm.setMinMark( ( (Double) object[index++] ).doubleValue() );
            markForm.setMaxMark( ( (Double) object[index++] ).doubleValue() );
            markForm.setMarkValue( -1 );
        }

        // Liste des tre
        List tre = (List) result.getResultMap().get( null );
        result.getResultMap().remove( null );
        // Liste des composants DTO
        LinkedList componentsDTO = new LinkedList();
        componentsDTO.addAll( result.getResultMap().keySet() );
        // Liste des composants
        ArrayList components = new ArrayList();
        Iterator it = componentsDTO.listIterator();
        ComponentForm cform = null;
        ComponentDTO dto = null;
        // Parcours des composants renvoyés
        while ( it.hasNext() )
        {
            dto = (ComponentDTO) it.next();
            // Création de la form associée et ajout
            cform = new ComponentForm();
            cform.setId( dto.getID() );
            cform.setName( dto.getName() );
            cform.setFullName( dto.getFullName() );
            cform.getMetrics().addAll( SqualeWebActionUtils.getAsStringsList( (List) result.getResultMap().get( dto ) ) );
            components.add( cform );
        }
        // Mise en formulaire des valeurs
        markForm.setPracticeName( practice.getName() );
        markForm.setPracticeId( practice.getId() + "" );
        markForm.setTreNames( tre );
        markForm.setComponents( components );
        markForm.setFactorName( factor.getName() );
        markForm.setFactorId( factor.getId() + "" );

    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

}
