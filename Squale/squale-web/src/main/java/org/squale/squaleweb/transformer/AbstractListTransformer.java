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

import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation de liste en form Cette classe permet de factoriser le comportement des form qui contiennent une liste
 * sous form d'ArrayList
 */
public abstract class AbstractListTransformer
    implements WITransformer
{

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        ArrayList resultList = new ArrayList();
        Object obj[] = { resultList };
        formToObj( pForm, obj );
        return obj;
    }

}
