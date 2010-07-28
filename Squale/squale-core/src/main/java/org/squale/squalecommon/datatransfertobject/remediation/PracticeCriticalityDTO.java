package org.squale.squalecommon.datatransfertobject.remediation;

import org.squale.squalecommon.enterpriselayer.businessobject.result.MarkBO;

/**
 * Dto for the practice with the linked criticality. Specific for the remediation by risk
 * 
 * @author bfranchet
 */
public class PracticeCriticalityDTO
{
    /**
     * The minimal mark value
     */
    private static final float MIN_MARK = 0.01f;

    /**
     * The maximal mark value
     */
    private static final float MAX_MARK = 3.0f;

    /**
     * The max value for the practiceComponentCriticality attribute. This value corresponding to : -log(0.01/3.0) where
     * 0.01 corresponding to MIN_MARK and 3.0 MAX_MARK
     */
    private static final float MAX_CRITICALITY_PRACTICE_COMPONENT = 5.70F;

    /**
     * The technical id of the practice rule
     */
    private long practiceId;

    /**
     * The name of the practice rule
     */
    private String name;

    /**
     * The mark of the practice
     */
    private float mark;

    /**
     * The practice criticality
     */
    private int practiceCriticality = -1;

    /**
     * The practice effort
     */
    private int practiceEffort = -1;

    /**
     * The computed criticality for the current practice and mark. Criticality (component,practice)
     */
    private float practiceComponentCriticality;

    /**
     * Constructor
     */
    public PracticeCriticalityDTO()
    {
    }

    /**
     * Constructor
     * 
     * @param pId The technical id of the practice rule
     * @param pName The practice rule name
     * @param pPracticeCriticality The practice criticality
     * @param pPracticeEffort The practice effort
     * @param pMark The practice current mark
     */
    public PracticeCriticalityDTO( long pId, String pName, int pPracticeCriticality, int pPracticeEffort, float pMark )
    {
        practiceId = pId;
        name = pName;
        mark = pMark;
        practiceCriticality = pPracticeCriticality;
        practiceEffort = pPracticeEffort;
    }

    /**
     * Getter method for the attribute practiceId
     * 
     * @return The practice rule technical id
     */
    public long getPracticeId()
    {
        return practiceId;
    }

    /**
     * Setter method for the attribute practiceId
     * 
     * @param pId The new practice rule technical id
     */
    public void setPracticeId( long pId )
    {
        practiceId = pId;
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The practice rule name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new practice rule name
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute mark
     * 
     * @return The practice current mark
     */
    public float getMark()
    {
        return mark;
    }

    /**
     * Setter method for the attribute mark
     * 
     * @param pMark The new practice current mark
     */
    public void setMark( float pMark )
    {
        mark = pMark;
    }

    /**
     * Getter method for the attribute practiceCriticality
     * 
     * @return The practice criticality
     */
    public int getPracticeCriticality()
    {
        return practiceCriticality;
    }

    /**
     * Setter method for the attribute practiceCriticality
     * 
     * @param pCriticality The new practice criticality
     */
    public void setPracticeCriticality( int pCriticality )
    {
        practiceCriticality = pCriticality;
    }

    /**
     * Getter method for the attribute practiceEffort
     * 
     * @return The practice effort
     */
    public int getPracticeEffort()
    {
        return practiceEffort;
    }

    /**
     * Setter method for the attribute practiceEffort
     * 
     * @param pPracticeEffort The new practice effort
     */
    public void setPracticeEffort( int pPracticeEffort )
    {
        practiceEffort = pPracticeEffort;
    }

    /**
     * Getter method for the attribute practiceComponentCriticality
     * 
     * @return The practice computed criticality
     */
    public float getPracticeComponentCriticality()
    {
        return practiceComponentCriticality;
    }

    /**
     * This method computes the criticalitY (practice, component)
     */
    public void computePracticeComponentCriticality()
    {
        if ( mark != MarkBO.NOT_NOTED_VALUE )
        {
            if ( mark <= MIN_MARK )
            {
                practiceComponentCriticality = MAX_CRITICALITY_PRACTICE_COMPONENT * practiceCriticality;
            }
            else
            {
                practiceComponentCriticality =
                    -( (Double) Math.log( mark / MAX_MARK ) ).floatValue() * practiceCriticality;
            }
        }

    }

}
