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
package org.squale.squalecommon.enterpriselayer.facade.config.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import junit.framework.TestCase;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SqualixConfigurationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalecommon.util.initialisor.JRafConfigurator;

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
        final int nbStopTimes = 5;
        assertEquals( nbStopTimes, conf.getStopTimes().size() );
        StopTimeBO stopTimeBO = (StopTimeBO) conf.getStopTimes().iterator().next();
        assertEquals( "monday", stopTimeBO.getDay() );
        assertEquals( "4:00", stopTimeBO.getTime() );
        final int nbFrequencies = 3;
        assertEquals( nbFrequencies, conf.getFrequencies().size() );
        AuditFrequencyBO frequencyBO = (AuditFrequencyBO) conf.getFrequencies().iterator().next();
        assertEquals( 5, frequencyBO.getDays() );
        assertEquals( 5, frequencyBO.getFrequency() );
        assertEquals( 2, conf.getSourceManagements().size() );
        ArrayList sourceM = (ArrayList) conf.getSourceManagements();
        SourceManagementBO cc = (SourceManagementBO) sourceM.get( 0 );
        assertEquals( "Local", cc.getName() );
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
        assertEquals( "grid", ( (QualityGridBO) grids.iterator().next() ).getName() );

        Set confs = javaProfile.getProfileDisplayConfs();
        final int nbConfs = 3;
        assertEquals( nbConfs, confs.size() );

        assertEquals( 8, javaProfile.getAnalysisTasks().size() );
        TaskBO nonConfigurableTask = ( (TaskRefBO) javaProfile.getAnalysisTasks().get( 0 ) ).getTask();
        assertEquals( "JCompilingTask", nonConfigurableTask.getName() );
        assertEquals( "org.squale.squalix.tools.compiling.java.JCompilingTask", nonConfigurableTask.getClassName() );

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
