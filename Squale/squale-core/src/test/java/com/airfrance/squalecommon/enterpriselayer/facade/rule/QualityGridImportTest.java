package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;

/**
 * Test d'importation de grille qualité
 */
public class QualityGridImportTest extends SqualeTestCase {

    /**
     * Importation nominale d'une grille
     */
    public void testImportNominal()  {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config/grid.xml");
        Collection grids;
        try {
            grids = QualityGridImport.importGrid(stream, errors);
            assertEquals(1, grids.size());
            assertEquals(0, errors.length());
        } catch (JrafEnterpriseException e) {
            e.printStackTrace();
            fail("unexpected exception");
        }
    }

    /**
     * Importation d'une grille simple
     * Les entités importées sont vérifiées par rapport au contenu du fichier
     */
    public void testImportSimple()  {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("data/grid/grid_simple.xml");
        Collection grids;
        try {
            grids = QualityGridImport.importGrid(stream, errors);
            assertEquals(1, grids.size());
            assertTrue(errors.length()==0);
            QualityGridDTO grid = (QualityGridDTO) grids.iterator().next();
            assertEquals("grid1", grid.getName());
            // La grille contient un facteur
            assertEquals(1, grid.getFactors().size());
        } catch (JrafEnterpriseException e) {
            e.printStackTrace();
            fail("unexpected exception");
        }
    }

    /**
     * Importation d'une grille avec duplication de pratique
     *
     */
    public void testPracticeDuplicate()  {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("data/grid/grid_duplicate_practice.xml");
        Collection grids;
        try {
            grids = QualityGridImport.importGrid(stream, errors);
            assertEquals(0, grids.size());
            assertTrue(errors.length()>0);
        } catch (JrafEnterpriseException e) {
            e.printStackTrace();
            fail("unexpected exception");
        }
    }

    /**
     * Importation d'une grille avec un mauvais fichier XML
     */
    public void testImportBadFile()  {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config/hibernate.cfg.xml");
        Collection grids;
        try {
            grids = QualityGridImport.importGrid(stream, errors);
            assertEquals(0, grids.size());
            assertTrue(errors.length()>0);
        } catch (JrafEnterpriseException e) {
            e.printStackTrace();
            fail("unexpected exception");
        }
    }

    /**
     * Importation d'une grille avec un fichier XML comportant de mauvaises formules
     */
    public void testImportFileWithBadFormulas()  {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("data/grid/grid_bad_formula.xml");
        Collection grids;
        try {
            grids = QualityGridImport.importGrid(stream, errors);
            assertEquals(1, grids.size());
            assertTrue(errors.length()>0);
        } catch (JrafEnterpriseException e) {
            e.printStackTrace();
            fail("unexpected exception");
        }
    }
}
