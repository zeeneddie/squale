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

import org.squale.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import org.squale.squaleweb.applicationlayer.formbean.rulechecking.CheckstyleRuleSetForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * @author E6400802 Transformation d'un jeu de règles checkstyle
 */
public class CheckstyleRuleSetTransformer
    extends AbstractRuleSetTransformer
{
    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        CheckstyleRuleSetForm form = new CheckstyleRuleSetForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new CheckstyleDTO() };
        formToObj( pForm, obj );
        return obj;
    }

}
