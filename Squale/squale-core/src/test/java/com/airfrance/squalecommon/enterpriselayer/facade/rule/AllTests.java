package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 */
public class AllTests {

    /**
     * Suite de tests
     * @return tests
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.airfrance.squalecommon.enterpriselayer.facade.rule");
        //$JUnit-BEGIN$
        //suite.addTest(new TestSuite(AuditComputingTest.class));
        suite.addTest(new TestSuite(FormulaConverterTest.class));
        suite.addTest(new TestSuite(FormulaInterpreterTest.class));
        suite.addTest(new TestSuite(QualityGridImportTest.class));
        //$JUnit-END$
        return suite;
    }
}
