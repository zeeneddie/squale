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
package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import com.airfrance.squalecommon.datatransfertobject.rule.CriteriumRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.CriteriumRuleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorRuleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.QualityRuleForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'un facteur
 */
public class FactorTransformer
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
        FactorRuleForm form = new FactorRuleForm();
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
        Locale local = (Locale) pObject[1];
        FactorRuleDTO factorDTO = (FactorRuleDTO) pObject[0];
        FactorRuleForm form = (FactorRuleForm) pForm;
        form.setId( factorDTO.getId() );
        form.setName( factorDTO.getName() );
        String ponderation = "";
        // Transformation de chaque critere
        Iterator criteriaIt = factorDTO.getCriteria().keySet().iterator();
        while ( criteriaIt.hasNext() )
        {
            CriteriumRuleDTO currentDTO = (CriteriumRuleDTO) criteriaIt.next();
            form.addCriterium( (CriteriumRuleForm) WTransformerFactory.objToForm( CriteriumTransformer.class,
                                                                                  new Object[] { currentDTO, local } ) );
            ponderation += QualityRuleForm.SEPARATOR + ( (Float) factorDTO.getCriteria().get( currentDTO ) ).toString();
        }
        if ( ponderation.length() > 2 )
        {
            // On supprime la virgule inutile
            ponderation = ponderation.substring( 2 );
        }
        form.setPonderation( ponderation );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        FactorRuleDTO dto = new FactorRuleDTO();
        formToObj( pForm, new Object[] { dto } );
        return new Object[] { dto };
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        FactorRuleForm form = (FactorRuleForm) pForm;
        FactorRuleDTO dto = (FactorRuleDTO) pObject[0];
        ArrayList criteria = (ArrayList) form.getCriteria().getList();
        dto.setId( form.getId() );
        dto.setName( form.getName() );
        String ponderation = form.getPonderation();
        // on découpe les pondérations qui sont sous la forme d'une String dans le form
        // les valeurs sont séparés par une "," (QualityRuleForm.SEPARATOR)
        StringTokenizer st = new StringTokenizer( ponderation, QualityRuleForm.SEPARATOR );
        int i = 0;
        // Si le nombre de pondérations est différent du nombre de critères
        // on lance une exception
        if ( st.countTokens() != criteria.size() )
        {
            throw new WTransformerException();
        }
        while ( st.hasMoreTokens() )
        {
            CriteriumRuleForm criteriaForm = (CriteriumRuleForm) criteria.get( i++ );
            CriteriumRuleDTO criteriaDTO =
                (CriteriumRuleDTO) WTransformerFactory.formToObj( CriteriumTransformer.class, criteriaForm )[0];
            dto.addCriterium( criteriaDTO, new Float( Float.parseFloat( (String) st.nextElement() ) ) );
        }
    }

}
