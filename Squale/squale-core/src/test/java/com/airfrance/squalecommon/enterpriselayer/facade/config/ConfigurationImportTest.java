package com.airfrance.squalecommon.enterpriselayer.facade.config;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.config.web.AbstractDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.rule.QualityGridDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test l'importation d'une configuration en base de données
 */
public class ConfigurationImportTest
    extends SqualeTestCase
{

    /**
     * Importation nominale de la configuration
     */
    public void testImportNominal()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/squalix-config.xml" );
        SqualixConfigurationDTO conf;
        try
        {
            conf = ConfigurationImport.importConfig( stream, errors );
            assertEquals( 1, conf.getSourceManagements().size() );
            final int nbProfiles = 4;
            assertEquals( nbProfiles, conf.getProfiles().size() );
            assertEquals( 0, errors.length() );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Importation d'une configuration simple Les entités importées sont vérifiées par rapport au contenu du fichier
     */
    /*
     * public void testImportSimple() { StringBuffer errors = new StringBuffer(); InputStream stream =
     * getClass().getClassLoader().getResourceAsStream("data/config/squalix-config_simple.xml"); SqualixConfigurationDTO
     * conf; try { conf = ConfigurationImport.importConfig(stream, errors); assertEquals(1,
     * conf.getSourceManagements().size()); assertEquals(1, conf.getProfiles().size()); assertEquals(0,
     * errors.length()); // Les dates limites: Collection stopTimes = conf.getStopTimes(); assertEquals(1,
     * stopTimes.size()); StopTimeDTO stopTimeDTO = (StopTimeDTO) stopTimes.iterator().next(); assertEquals("monday",
     * stopTimeDTO.getDay()); assertEquals("4:00", stopTimeDTO.getTime()); // Le récupérateur de source:
     * SourceManagementDTO manager = (SourceManagementDTO) conf.getSourceManagements().iterator().next();
     * assertEquals("clearcase", manager.getName()); // Une tâche d'analyse: assertEquals(1,
     * manager.getAnalysisTasks().size()); TaskDTO analysisManager = (TaskDTO)
     * manager.getAnalysisTasks().iterator().next(); assertEquals("ClearCaseTask", analysisManager.getName());
     * assertEquals("com.airfrance.squalix.tools.clearcase.task.ClearCaseTask", analysisManager.getClassName()); // Une
     * tâche de terminaison assertEquals(1, manager.getTerminationTasks().size()); // Le profil: ProjectProfileDTO
     * profile = (ProjectProfileDTO) conf.getProfiles().iterator().next(); assertEquals("java1.4", profile.getName());
     * assertEquals(true, profile.getExportIDE()); // Une tâche d'analyse: assertEquals(1,
     * profile.getAnalysisTasks().size()); TaskDTO analysisProfile = (TaskDTO)
     * profile.getAnalysisTasks().iterator().next(); assertEquals("JCompilingTask", analysisProfile.getName());
     * assertEquals("com.airfrance.squalix.tools.clearcase.task.ClearCaseTask", analysisManager.getClassName()); //
     * Aucune tâche de terminaison assertEquals(0, profile.getTerminationTasks().size()); } catch
     * (JrafEnterpriseException e) { e.printStackTrace(); fail("unexpected exception"); } }
     */

    /**
     * Importation de la configuration avec duplication de profil
     */
    /*
     * public void testProfileDuplicate() { StringBuffer errors = new StringBuffer(); InputStream stream =
     * getClass().getClassLoader().getResourceAsStream("data/config/squalix-config_duplicate_profile.xml");
     * SqualixConfigurationDTO conf; try { conf = ConfigurationImport.importConfig(stream, errors);
     * assertTrue(errors.length() > 0); } catch (JrafEnterpriseException e) { e.printStackTrace(); fail("unexpected
     * exception"); } }
     */

    /**
     * Importation d'une configuration avec un mauvais fichier XML
     */
    /*
     * public void testImportBadFile() { StringBuffer errors = new StringBuffer(); InputStream stream =
     * getClass().getClassLoader().getResourceAsStream("config/hibernate.cfg.xml"); SqualixConfigurationDTO conf; try {
     * conf = ConfigurationImport.importConfig(stream, errors); assertEquals(0, conf.getProfiles().size());
     * assertTrue(errors.length() > 0); } catch (JrafEnterpriseException e) { e.printStackTrace(); fail("unexpected
     * exception"); } }
     */

    /**
     * Test de la création sans données en base
     */
    public void testCreateConfig()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/config/squalix-config_simple.xml" );
        SqualixConfigurationDTO conf;
        try
        {
            // On crée la grille grid
            getComponentFactory().createGrid( getSession() );
            conf = ConfigurationImport.createConfig( stream, errors );
            assertNotNull( conf );
            assertTrue( errors.length() == 0 );
            // 1 profil
            assertEquals( 1, conf.getProfiles().size() );
            ProjectProfileDTO profileDTO = (ProjectProfileDTO) conf.getProfiles().iterator().next();
            // Associé à 1 tâche d'analyse
            assertEquals( 1, profileDTO.getAnalysisTasks().size() );
            // 1 grille associée
            assertEquals( 1, profileDTO.getGrids().size() );
            // qui se nomme "grid"
            assertEquals( "grid", ( (QualityGridDTO) profileDTO.getGrids().get( 0 ) ).getName() );
            // On récupère la grille "grid" qui doit avoir un profil associé
            Collection grids = QualityGridDAOImpl.getInstance().findWhereProfile( getSession(), profileDTO.getId() );
            assertEquals( 1, grids.size() );
            assertEquals( "grid", ( (QualityGridBO) grids.iterator().next() ).getName() );
            // On a aussi crée 3 configurations
            AbstractDisplayConfDAOImpl displayDAO = AbstractDisplayConfDAOImpl.getInstance();
            // 1 bubble
            List bubbleConf = displayDAO.findAllSubclass( getSession(), BubbleConfBO.class );
            assertEquals( 1, bubbleConf.size() );
            // 2 volumétry
            List volConf = displayDAO.findAllSubclass( getSession(), VolumetryConfBO.class );
            assertEquals( 2, volConf.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
