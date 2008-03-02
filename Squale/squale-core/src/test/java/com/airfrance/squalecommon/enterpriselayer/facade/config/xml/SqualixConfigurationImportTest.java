package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SqualixConfigurationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

/**
 * Test d'importation de la configuration de squalix
 */
public class SqualixConfigurationImportTest
    extends TestCase
{

    /**
     * Constructor for ConfigImportTest.
     * 
     * @param arg0 nom
     */
    public SqualixConfigurationImportTest( String arg0 )
    {
        super( arg0 );
        JRafConfigurator.initialize();
    }

    /**
     * Teste l'importation de la configuration Squalix à partir du fichier xml.
     * 
     * @throws JrafEnterpriseException si erreur
     */
    public void testImport()
        throws JrafEnterpriseException
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/config/squalix-config_simple.xml" );
        SqualixConfigImport configImport = new SqualixConfigImport();
        SqualixConfigurationBO conf = configImport.importConfig( stream, errors );
        final int nbStopTimes = 1;
        assertEquals( nbStopTimes, conf.getStopTimes().size() );
        StopTimeBO stopTimeBO = (StopTimeBO) conf.getStopTimes().iterator().next();
        assertEquals( "monday", stopTimeBO.getDay() );
        assertEquals( "4:00", stopTimeBO.getTime() );
        final int nbFrequencies = 1;
        assertEquals( nbFrequencies, conf.getFrequencies().size() );
        AuditFrequencyBO frequencyBO = (AuditFrequencyBO) conf.getFrequencies().iterator().next();
        assertEquals( 1, frequencyBO.getDays() );
        assertEquals( 1, frequencyBO.getFrequency() );
        assertEquals( 1, conf.getSourceManagements().size() );
        ArrayList sourceM = (ArrayList) conf.getSourceManagements();
        SourceManagementBO cc = (SourceManagementBO) sourceM.get( 0 );
        assertEquals( "clearcase", cc.getName() );
        assertEquals( true, cc.isNormalAudit() );
        TaskBO mandatory = ( (TaskRefBO) cc.getAnalysisTasks().get( 0 ) ).getTask();
        assertEquals( true, mandatory.isStandard() );
        final int nbProfiles = 1;
        assertEquals( nbProfiles, conf.getProfiles().size() );
        ArrayList profiles = (ArrayList) conf.getProfiles();
        ProjectProfileBO javaProfile = (ProjectProfileBO) profiles.get( 0 );
        assertEquals( "java1.4", javaProfile.getName() );
        assertEquals( true, javaProfile.getExportIDE() );

        Set grids = javaProfile.getGrids();
        assertEquals( 1, grids.size() );
        assertEquals( "javaGrid", ( (QualityGridBO) grids.iterator().next() ).getName() );

        Set confs = javaProfile.getProfileDisplayConfs();
        final int nbConfs = 3;
        assertEquals( nbConfs, confs.size() );

        assertEquals( 1, javaProfile.getAnalysisTasks().size() );
        TaskBO nonConfigurableTask = ( (TaskRefBO) javaProfile.getAnalysisTasks().get( 0 ) ).getTask();
        assertEquals( "JCompilingTask", nonConfigurableTask.getName() );
        assertEquals( "com.airfrance.squalix.tools.compiling.java.JCompilingTask", nonConfigurableTask.getClassName() );

        // Pas d'erreur
        assertEquals( 0, errors.length() );
    }

    /**
     * Teste le cas où deux profils portent le même nom
     * 
     * @throws JrafEnterpriseException si erreur
     */
    public void testProfileDuplicate()
        throws JrafEnterpriseException
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream =
            getClass().getClassLoader().getResourceAsStream( "data/config/squalix-config_duplicate_profile.xml" );
        SqualixConfigImport configImport = new SqualixConfigImport();
        SqualixConfigurationBO conf = configImport.importConfig( stream, errors );
        assertTrue( errors.length() > 0 );
    }

    /**
     * Teste le cas où le fichier est incorrecte.
     * 
     * @throws JrafEnterpriseException si erreur
     */
    public void testImportBadFile()
        throws JrafEnterpriseException
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/hibernate.cfg.xml" );
        SqualixConfigImport configImport = new SqualixConfigImport();
        SqualixConfigurationBO conf = configImport.importConfig( stream, errors );
        assertTrue( errors.length() > 0 );
    }

}
