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
package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

import com.airfrance.squaleweb.transformer.rulechecking.CheckStyleRuleSetListTransformer;

/**
 * Action pour gerer les différents fichiers de configuration checkstyle
 * 
 * @author henix
 */
public class CheckstyleAction
    extends AbstractRuleSetAction
{

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getRuleSetListTransformer()
     * @return le transformer à utiliser
     */
    protected Class getRuleSetListTransformer()
    {
        return CheckStyleRuleSetListTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getAccessComponentName()
     * @return le nom utilisé
     */
    protected String getAccessComponentName()
    {
        return "CheckstyleAdmin";
    }
}
