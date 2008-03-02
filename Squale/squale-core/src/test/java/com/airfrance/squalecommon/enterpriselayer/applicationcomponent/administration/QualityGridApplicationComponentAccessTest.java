package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

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
