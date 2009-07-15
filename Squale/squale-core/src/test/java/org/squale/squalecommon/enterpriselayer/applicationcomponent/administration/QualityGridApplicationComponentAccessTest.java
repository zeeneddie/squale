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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.Collection;
import java.util.Iterator;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.util.initialisor.JRafConfigurator;

/**
 *
 */
public class QualityGridApplicationComponentAccessTest
    extends SqualeTestCase
{

    /**
     * Test sur la récupération des grilles qualités
     */
    public void testGetGrids()
    {
        QualityGridApplicationComponentAccess comp = new QualityGridApplicationComponentAccess();
        try
        {
            JRafConfigurator.initialize();
            Collection grids = comp.getGrids( Boolean.FALSE );
            Iterator gridsIterator = grids.iterator();
            while ( gridsIterator.hasNext() )
            {
                QualityGridConfDTO grid = comp.getGrid( (QualityGridDTO) gridsIterator.next() );
            }
        }
        catch ( JrafEnterpriseException e )
        {
            fail( "unexpected exception" );
            e.printStackTrace();
        }
    }

}
