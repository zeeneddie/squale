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
package com.airfrance.squalecommon.util.xml;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;

/**
 * Adaptateur de fabrique pour le digester Cette classe est utilisée pour éviter la duplication de code dans les
 * diverses factory utilisées par le digester
 */
public abstract class FactoryAdapter
    implements ObjectCreationFactory
{
    /** Digester */
    private Digester mDigester;

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#getDigester()
     */
    public Digester getDigester()
    {
        return mDigester;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#setDigester(org.apache.commons.digester.Digester)
     */
    public void setDigester( Digester pDigester )
    {
        mDigester = pDigester;
    }
}