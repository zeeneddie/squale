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
package org.squale.squaleweb.transformer.rulechecking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.squale.squaleweb.applicationlayer.formbean.rulechecking.AbstractRuleSetForm;
import org.squale.squaleweb.applicationlayer.formbean.rulechecking.AbstractRuleSetListForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une liste de ruleset
 */
public abstract class AbstractRuleSetListTransformer
    implements WITransformer
{

    /**
     * Transformer pour les ruleset
     */
    private Class mRuleSetTransformerClass;

    /**
     * Constructeur
     * 
     * @param pRuleSetTransformerClass class pour transformer chaque ruleset
     */
    protected AbstractRuleSetListTransformer( Class pRuleSetTransformerClass )
    {
        mRuleSetTransformerClass = pRuleSetTransformerClass;
    }

    /**
     * @param pObject le tableau de CheckstyleDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        Collection listcheckstyleRuleSetsDTO = (Collection) pObject[0];
        AbstractRuleSetListForm checkStyleRuleSetListForm = (AbstractRuleSetListForm) pForm;
        ArrayList listRuleSetForm = new ArrayList();
        Iterator it = listcheckstyleRuleSetsDTO.iterator();
        while ( it.hasNext() )
        {
            listRuleSetForm.add( WTransformerFactory.objToForm( mRuleSetTransformerClass, it.next() ) );
        }
        checkStyleRuleSetListForm.setRuleSets( listRuleSetForm );
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de CheckstyleDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        Collection listObject = (Collection) pObject[0];
        ListIterator it = ( (AbstractRuleSetListForm) pForm ).getRuleSets().listIterator();
        while ( it.hasNext() )
        {
            AbstractRuleSetForm ruleSetForm = (AbstractRuleSetForm) it.next();
            if ( ruleSetForm.isSelected() )
            {
                listObject.add( WTransformerFactory.formToObj( mRuleSetTransformerClass, ruleSetForm )[0] );
            }
        }
    }

    /**
     * @param pForm le formulaire
     * @return le formulaire transformé
     * @throws WTransformerException si erreur
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        ArrayList list = new ArrayList();
        Object[] result = { list };
        formToObj( pForm, result );
        return result;
    }

}
