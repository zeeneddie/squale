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
package org.squale.squaleweb.comparator;

import java.util.Comparator;
import java.util.Locale;

import org.squale.squaleweb.resources.WebMessages;

/**
 * Comparateur pour les nom des règles internationalisés
 */
public class RuleNameComparator
    implements Comparator
{

    /** La locale */
    private Locale mLocale;

    /**
     * Constructeur par défaut
     * 
     * @param pLocale la locale
     */
    public RuleNameComparator( Locale pLocale )
    {
        mLocale = pLocale;
    }

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object) {@inheritDoc}
     */
    public int compare( Object ruleName1, Object ruleName2 )
    {
        int result = 1;
        // Vérification des éléments
        String key1 = WebMessages.getString( mLocale, (String) ruleName1 );
        String key2 = WebMessages.getString( mLocale, (String) ruleName2 );
        if ( null != key1 && null != key2 )
        {
            result = key1.compareTo( key2 );
        }
        return result;
    }

}
