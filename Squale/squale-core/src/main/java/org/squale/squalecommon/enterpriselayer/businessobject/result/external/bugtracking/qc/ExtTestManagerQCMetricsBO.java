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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.external.bugtracking.qc;

import java.util.Iterator;
import java.util.Map;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.external.bugtracking.ExtBugTrackingMetricsBO;

/**
 * Extraction from Quality Center
 * 
 * @hibernate.subclass discriminator-value="TestManagerQCMetrics"
 */
public class ExtTestManagerQCMetricsBO
    extends ExtBugTrackingMetricsBO
{

    /** Number of requirements */
    private static final String NB_REQUIREMENTS = "nbReq";

    /** Number of new requirements */
    private static final String NB_NEW_REQUIREMENTS = "nbNewReq";

    /** Number of requirements with tests */
    private static final String NB_REQUIREMENTS_WITH_TESTS = "nbReqWithTests";

    /** Number of requirements with subrequirements */
    private static final String NB_REQUIREMENTS_WITH_SUBREQUIREMENTS = "nbReqWithSubReq";

    /** Requirements repartition by size (nb of subrequirements) */
    public static final String REQUIREMENTS_REPARTITION = "repReq";

    /** Number of tests */
    private static final String NB_TESTS = "nbTests";

    /** Number of tests with run */
    private static final String NB_TESTS_WITH_RUN = "nbTestsWithRun";

    /** Number of tests with a success run */
    private static final String NB_OK_TESTS = "nbOkTests";

    /** Number of tests where the last run is passed */
    private static final String NB_PASSED_TESTS = "nbPassedTests";

    /** Tests repartition (nb of steps) */
    public static final String TESTS_STEPS_REPARTITION = "repTestStep";

    /** Tests repartition (nb of covered requirements) */
    public static final String TESTS_REQ_REPARTITION = "repTestReq";

    /** Number of folders in tests definition */
    private static final String NB_FOLDERS = "nbFolders";

    /** Folders repartition by size (nb of tests) */
    public static final String FOLDERS_REPARTITION = "repFold";

    /** Number of scenario */
    private static final String NB_SCENARIO = "nbScen";

    /** Scenario repartition (nb of tests) */
    public static final String SCENARIO_REPARTITION = "repScen";

    /** Number of defects linked to tests */
    private static final String NB_DEFECTS_LINKED_TO_TEST = "nbTestDefects";

    /** Number of defects detected in development period */
    private static final String NB_DEFECTS_IN_DEV_PERIOD = "nbDefectsInDevPeriod";

    /** Number of defects detected in production not linked to test */
    private static final String NB_DEFECTS_IN_PROD_NOT_LINKED = "nbDefectsInProd";

    /** Number of defects linked to a test detected in development period */
    private static final String NB_LINKED_DEFECTS_IN_DEV_PERIOD = "nbLinkedDefectsInDevPeriod";

    /** Number of closed defects in previous version */
    private static final String NB_PREV_CLOSED_DEFECTS = "nbPrevClosedDefects";

    /** Number of closed defects in last version */
    private static final String NB_LAST_CLOSED_DEFECTS = "nbLastClosedDefects";

    /** Number of opened defects in previous version */
    private static final String NB_PREV_OPENED_DEFECTS = "nbPrevOpenedDefects";

    /** Number of runs */
    private static final String NB_RUNS = "nbRuns";

    /** Number od steps */
    private static final String NB_STEPS = "nbSteps";

    /** Number of steps with last run ok */
    private static final String NB_STEPS_LAST_RUN_OK = "nbOkSteps";

    /** requirements repartition */
    private IntegerArrayMetricBO repReq;

    /** folders repartition */
    private IntegerArrayMetricBO repFold;

    /** scenarios repartition */
    private IntegerArrayMetricBO repScen;

    /** tests repartition by steps */
    private IntegerArrayMetricBO repTestStep;

    /** tests repartition by requirements */
    private IntegerArrayMetricBO repTestReq;

    /**
     * Constructor
     */
    public ExtTestManagerQCMetricsBO()
    {
        this( new int[2][0], new int[2][0], new int[2][0], new int[2][0], new int[2][0] );
    }

    /**
     * Constructor
     * 
     * @param repReqInt intervals for requirements repartition
     * @param repTestStepInt intervals for tests repartition (by steps)
     * @param repTestReqInt intervals for tests repartition (by requirements)
     * @param repFoldInt intervals for folders repartition
     * @param repScenInt intervals for scenarios repartition
     */
    public ExtTestManagerQCMetricsBO( int[][] repReqInt, int[][] repTestStepInt, int[][] repTestReqInt,
                                      int[][] repFoldInt, int[][] repScenInt )
    {
        super();
        getMetrics().put( NB_REQUIREMENTS, new IntegerMetricBO() );
        getMetrics().put( NB_NEW_REQUIREMENTS, new IntegerMetricBO() );
        getMetrics().put( NB_REQUIREMENTS_WITH_TESTS, new IntegerMetricBO() );
        getMetrics().put( NB_REQUIREMENTS_WITH_SUBREQUIREMENTS, new IntegerMetricBO() );
        repReq = new IntegerArrayMetricBO( REQUIREMENTS_REPARTITION, repReqInt );

        getMetrics().put( NB_TESTS, new IntegerMetricBO() );
        getMetrics().put( NB_TESTS_WITH_RUN, new IntegerMetricBO() );
        getMetrics().put( NB_OK_TESTS, new IntegerMetricBO() );
        getMetrics().put( NB_PASSED_TESTS, new IntegerMetricBO() );
        repTestStep = new IntegerArrayMetricBO( TESTS_STEPS_REPARTITION, repTestStepInt );
        repTestReq = new IntegerArrayMetricBO( TESTS_REQ_REPARTITION, repTestReqInt );

        getMetrics().put( NB_FOLDERS, new IntegerMetricBO() );
        repFold = new IntegerArrayMetricBO( FOLDERS_REPARTITION, repFoldInt );

        getMetrics().put( NB_SCENARIO, new IntegerMetricBO() );
        repScen = new IntegerArrayMetricBO( SCENARIO_REPARTITION, repScenInt );

        getMetrics().put( NB_DEFECTS_LINKED_TO_TEST, new IntegerMetricBO() );
        getMetrics().put( NB_DEFECTS_IN_DEV_PERIOD, new IntegerMetricBO() );
        getMetrics().put( NB_DEFECTS_IN_PROD_NOT_LINKED, new IntegerMetricBO() );
        getMetrics().put( NB_LINKED_DEFECTS_IN_DEV_PERIOD, new IntegerMetricBO() );
        getMetrics().put( NB_PREV_CLOSED_DEFECTS, new IntegerMetricBO() );
        getMetrics().put( NB_LAST_CLOSED_DEFECTS, new IntegerMetricBO() );
        getMetrics().put( NB_PREV_OPENED_DEFECTS, new IntegerMetricBO() );

        getMetrics().put( NB_RUNS, new IntegerMetricBO() );

        getMetrics().put( NB_STEPS, new IntegerMetricBO() );
        getMetrics().put( NB_STEPS_LAST_RUN_OK, new IntegerMetricBO() );
    }

    /**
     * @return the number of defects detected in development period
     */
    public Integer getNbDefectsInDevPeriod()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_DEFECTS_IN_DEV_PERIOD ) ).getValue();
    }

    /**
     * @param pNbDefectsInDevPeriod the number of defects detected in development period
     */
    public void setNbDefectsInDevPeriod( Integer pNbDefectsInDevPeriod )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_DEFECTS_IN_DEV_PERIOD ) ).setValue( pNbDefectsInDevPeriod );
    }

    /**
     * @return the number of defects detected in production
     */
    public Integer getNbDefectsInProdNotLinkedToTest()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_DEFECTS_IN_PROD_NOT_LINKED ) ).getValue();
    }

    /**
     * @param pNbDefectsInProd the number of defects detected in production
     */
    public void setNbDefectsInProdNotLinkedToTest( Integer pNbDefectsInProd )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_DEFECTS_IN_PROD_NOT_LINKED ) ).setValue( pNbDefectsInProd );
    }

    /**
     * @return the number of defects linked to tests
     */
    public Integer getNbDefectsLinkedToTest()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_DEFECTS_LINKED_TO_TEST ) ).getValue();
    }

    /**
     * @param pNbDefectsLinkedToTest the number of defects linked to tests
     */
    public void setNbDefectsLinkedToTest( Integer pNbDefectsLinkedToTest )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_DEFECTS_LINKED_TO_TEST ) ).setValue( pNbDefectsLinkedToTest );
    }

    /**
     * @return the number of folders in tests definition
     */
    public Integer getNbFolders()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_FOLDERS ) ).getValue();
    }

    /**
     * @param pNbFolders the number of folders in tests definition
     */
    public void setNbFolders( Integer pNbFolders )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_FOLDERS ) ).setValue( pNbFolders );
    }

    /**
     * @return the number of scenarios
     */
    public Integer getNbScen()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_SCENARIO ) ).getValue();
    }

    /**
     * @param pNbScen the number of scenarios
     */
    public void setNbScen( Integer pNbScen )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_SCENARIO ) ).setValue( pNbScen );
    }

    /**
     * @return the number of closed defects in last version
     */
    public Integer getNbLastClosedDefects()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_LAST_CLOSED_DEFECTS ) ).getValue();
    }

    /**
     * @param pNbLastClosedDefects the number of closed defects in last version
     */
    public void setNbLastClosedDefects( Integer pNbLastClosedDefects )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_LAST_CLOSED_DEFECTS ) ).setValue( pNbLastClosedDefects );
    }

    /**
     * @return the number of defects linked to a test detected in development period
     */
    public Integer getNbLinkedDefectsInDevPeriod()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_LINKED_DEFECTS_IN_DEV_PERIOD ) ).getValue();
    }

    /**
     * @param pNbLinkedDefectsInDevPeriod the number of defects linked to a test detected in development period
     */
    public void setNbLinkedDefectsInDevPeriod( Integer pNbLinkedDefectsInDevPeriod )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_LINKED_DEFECTS_IN_DEV_PERIOD ) ).setValue( pNbLinkedDefectsInDevPeriod );
    }

    /**
     * @return the number of tests with a success run
     */
    public Integer getNbOkTests()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_OK_TESTS ) ).getValue();
    }

    /**
     * @param pNbOkTests the number of tests with a success run
     */
    public void setNbOkTests( Integer pNbOkTests )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_OK_TESTS ) ).setValue( pNbOkTests );
    }

    /**
     * @return the number of tests where the last run is passed
     */
    public Integer getNbPassedTests()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_PASSED_TESTS ) ).getValue();
    }

    /**
     * @param pNbPassedTests the number of tests where the last run is passed
     */
    public void setNbPassedTests( Integer pNbPassedTests )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_PASSED_TESTS ) ).setValue( pNbPassedTests );
    }

    /**
     * @return the number of closed defects in previous version
     */
    public Integer getNbPrevClosedDefects()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_PREV_CLOSED_DEFECTS ) ).getValue();
    }

    /**
     * @param pNbPrevClosedDefects the number of closed defects in previous version
     */
    public void setNbPrevClosedDefects( Integer pNbPrevClosedDefects )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_PREV_CLOSED_DEFECTS ) ).setValue( pNbPrevClosedDefects );
    }

    /**
     * @return the number of opened defects in previous version
     */
    public Integer getNbPrevOpenedDefects()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_PREV_OPENED_DEFECTS ) ).getValue();
    }

    /**
     * @param pNbPrevOpenedDefects the number of opened defects in previous version
     */
    public void setNbPrevOpenedDefects( Integer pNbPrevOpenedDefects )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_PREV_OPENED_DEFECTS ) ).setValue( pNbPrevOpenedDefects );
    }

    /**
     * @return the number of requirements
     */
    public Integer getNbRequirements()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_REQUIREMENTS ) ).getValue();
    }

    /**
     * @param pNbRequirements the number of requirements
     */
    public void setNbRequirements( Integer pNbRequirements )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_REQUIREMENTS ) ).setValue( pNbRequirements );
    }

    /**
     * @return the number of new requirements
     */
    public Integer getNbNewRequirements()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_NEW_REQUIREMENTS ) ).getValue();
    }

    /**
     * @param pNbRequirements the number of new requirements
     */
    public void setNbNewRequirements( Integer pNbRequirements )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_NEW_REQUIREMENTS ) ).setValue( pNbRequirements );
    }

    /**
     * @return the number of requirements with subrequirements
     */
    public Integer getNbRequirementsWithSubRequirements()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_REQUIREMENTS_WITH_SUBREQUIREMENTS ) ).getValue();
    }

    /**
     * @param pNbrequirementsWithSubRequirements the number of requirements with subrequirements
     */
    public void setNbRequirementsWithSubRequirements( Integer pNbrequirementsWithSubRequirements )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_REQUIREMENTS_WITH_SUBREQUIREMENTS ) ).setValue( pNbrequirementsWithSubRequirements );
    }

    /**
     * @return the number of requirements with tests
     */
    public Integer getNbRequirementsWithTests()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_REQUIREMENTS_WITH_TESTS ) ).getValue();
    }

    /**
     * @param pNbRequirementsWithTests the number of requirements with tests
     */
    public void setNbRequirementsWithTests( Integer pNbRequirementsWithTests )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_REQUIREMENTS_WITH_TESTS ) ).setValue( pNbRequirementsWithTests );
    }

    /**
     * @return the number of runs
     */
    public Integer getNbRuns()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_RUNS ) ).getValue();
    }

    /**
     * @param pNbRuns the number of runs
     */
    public void setNbRuns( Integer pNbRuns )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_RUNS ) ).setValue( pNbRuns );
    }

    /**
     * @return the number of steps
     */
    public Integer getNbSteps()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_STEPS ) ).getValue();
    }

    /**
     * @param pNbSteps the number of steps
     */
    public void setNbSteps( Integer pNbSteps )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_STEPS ) ).setValue( pNbSteps );
    }

    /**
     * @return the number of steps with last run ok
     */
    public Integer getNbStepsLastRunOk()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_STEPS_LAST_RUN_OK ) ).getValue();
    }

    /**
     * @param pNbSteps the number of steps with last run ok
     */
    public void setNbStepsLastRunOk( Integer pNbSteps )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_STEPS_LAST_RUN_OK ) ).setValue( pNbSteps );
    }

    /**
     * @return the number of tests
     */
    public Integer getNbTests()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_TESTS ) ).getValue();
    }

    /**
     * @param pNbTests the number of tests
     */
    public void setNbTests( Integer pNbTests )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_TESTS ) ).setValue( pNbTests );
    }

    /**
     * @return the number of tests with run
     */
    public Integer getNbTestsWithRun()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NB_TESTS_WITH_RUN ) ).getValue();
    }

    /**
     * @param pNbTestsWithRun the number of tests with run
     */
    public void setNbTestsWithRun( Integer pNbTestsWithRun )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_TESTS_WITH_RUN ) ).setValue( pNbTestsWithRun );
    }

    /**
     * @param pMap map of repartition
     */
    public void setRepTestStepMetrics( Map pMap )
    {
        setRepMetrics( repTestStep, pMap );
    }

    /**
     * @param pMap map of repartition
     */
    public void setRepTestReqMetrics( Map pMap )
    {
        setRepMetrics( repTestReq, pMap );
    }

    /**
     * @param pMap map of repartition
     */
    public void setRepFoldMetrics( Map pMap )
    {
        setRepMetrics( repFold, pMap );
    }

    /**
     * @param pMap map of repartition
     */
    public void setRepReqMetrics( Map pMap )
    {
        setRepMetrics( repReq, pMap );
    }

    /**
     * @param pMap map of repartition
     */
    public void setRepScenMetrics( Map pMap )
    {
        setRepMetrics( repScen, pMap );
    }

    /**
     * @param arrayMetric array to set
     * @param pMap map of repartition
     */
    private void setRepMetrics( IntegerArrayMetricBO arrayMetric, Map pMap )
    {
        Integer cur;
        for ( Iterator keyIt = pMap.keySet().iterator(); keyIt.hasNext(); )
        {
            cur = (Integer) keyIt.next();
            arrayMetric.setTabValue( cur.intValue(), (Integer) pMap.get( cur ) );
        }
    }

    /**
     * Reprensents a repartition
     */
    class IntegerArrayMetricBO
    {

        /** Metric's name */
        private String prefixMetricName;

        /** Array of intervals */
        private int[][] intervals;

        /**
         * Constructor
         * 
         * @param pPrefixMetricName metric's name
         * @param pIntervals intervals
         */
        public IntegerArrayMetricBO( String pPrefixMetricName, int[][] pIntervals )
        {
            intervals = pIntervals;
            prefixMetricName = pPrefixMetricName;
            int min = 0;
            int max = 0;
            // Put metrics
            for ( int i = 0; i < pIntervals.length; i++ )
            {
                String name = prefixMetricName + "_" + i;
                getMetrics().put( name, new IntegerMetricBO() );
            }
        }

        /**
         * @param index index in array
         * @return the value of the metric at index
         */
        public Integer getTabValue( int index )
        {
            return (Integer) ( (IntegerMetricBO) getMetrics().get( prefixMetricName + "_" + index ) ).getValue();
        }

        /**
         * Set value of the metric at index <code>index</code>
         * 
         * @param nb repartition number
         * @param pNewValue the new value for the metric
         */
        public void setTabValue( int nb, Integer pNewValue )
        {
            ( (IntegerMetricBO) getMetrics().get( prefixMetricName + "_" + getIndex( nb ) ) ).setValue( pNewValue );
        }

        /**
         * @param pValue value to find
         * @return index of interval which contains pValue
         */
        private int getIndex( int pValue )
        {
            int index = 0;
            while ( index < intervals.length && !isIn( pValue, intervals[index] ) )
            {
                index++;
            }
            return index;
        }

        /**
         * @param value value to test
         * @param interval interval
         * @return true if <code>value</code> i in the interval
         */
        private boolean isIn( int value, int[] interval )
        {
            boolean result = false;
            // -1 for infinity
            if ( interval[1] == -1 )
            {
                result = value >= interval[0];
            }
            else
            {
                result = value >= interval[0] && value <= interval[1];
            }
            return result;
        }
    }
}
