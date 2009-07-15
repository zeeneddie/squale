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

import java.text.NumberFormat;
import java.util.Comparator;

import org.squale.squaleweb.applicationlayer.formbean.results.ResultForm;

/**
 * 
 */
public class ResultFormComparator
    implements Comparator
{

    /**
     * Compare les résultats suivant la valeur de la note courante
     * 
     * @param pResult1 le premier résultat
     * @param pResult2 le second
     * @return la comparaison des valeurs des notes.
     */
    public int compare( Object pResult1, Object pResult2 )
    {
        int result = 1;
        String mark1 = null;
        String mark2 = null;
        // Vérification du type des objets
        if ( pResult1 instanceof ResultForm )
        {
            mark1 = ( (ResultForm) pResult1 ).getCurrentMark();
            mark2 = ( (ResultForm) pResult2 ).getCurrentMark();
            if ( mark1 != null )
            {
                if ( mark2 != null )
                {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits( 2 );
                    nf.setMaximumFractionDigits( 2 );
                    mark1 = nf.format( Float.parseFloat( mark1 ) );
                    mark2 = nf.format( Float.parseFloat( mark2 ) );
                    result = mark1.compareTo( mark2 );
                }
                else
                {
                    result = -1;
                }
            }
        }
        return result;
    }
}
