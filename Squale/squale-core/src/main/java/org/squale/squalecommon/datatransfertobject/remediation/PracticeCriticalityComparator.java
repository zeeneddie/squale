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
