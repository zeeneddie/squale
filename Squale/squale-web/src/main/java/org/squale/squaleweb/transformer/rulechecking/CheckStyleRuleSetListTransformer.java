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

import org.squale.squaleweb.applicationlayer.formbean.rulechecking.CheckstyleRuleSetListForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'une liste de jeux de règles checkstyle
 */
public class CheckStyleRuleSetListTransformer
    extends AbstractRuleSetListTransformer
{

    /**
     * Constructeur
     */
    public CheckStyleRuleSetListTransformer()
    {
        super( CheckstyleRuleSetTransformer.class );
    }

    /**
     * @param pObject le tableau de CheckstyleDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        CheckstyleRuleSetListForm form = new CheckstyleRuleSetListForm();
        objToForm( pObject, form );
        return form;
    }

}
