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
package org.squale.squalecommon.datatransfertobject.remediation;

import java.util.Comparator;

/**
 * Comparator for the ComponentCriticalityDTO. It compares two practice by their practice-component criticality
 * 
 * @author bfranchet
 */
public class PracticeCriticalityComparator
    implements Comparator<PracticeCriticalityDTO>
{

    /**
     * {@inheritDoc}
     */
    public int compare( PracticeCriticalityDTO o1, PracticeCriticalityDTO o2 )
    {

        float criticalityO1 = o1.getPracticeComponentCriticality();

        float criticalityO2 = o2.getPracticeComponentCriticality();

        if ( criticalityO1 < criticalityO2 )
        {
            return 1;
        }
        else if ( criticalityO1 > criticalityO2 )
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

}
