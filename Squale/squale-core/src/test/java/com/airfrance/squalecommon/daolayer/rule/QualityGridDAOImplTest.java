package com.airfrance.squalecommon.daolayer.rule;

import java.util.ArrayList;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test de la couche DAO pour la grille qualité
 */
public class QualityGridDAOImplTest extends SqualeTestCase {

    /**
     * Test de création de grille
     *
     */
    public void testCreateGrid() {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try {
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl daoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName("grid");
            daoImpl.createGrid(session, grid);
            assertEquals(1, daoImpl.count(session).intValue());
            daoImpl.remove(session, grid);
            FacadeHelper.closeSession(session,"");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue");
        }
    }

    /**
     * Test du find
     *
     */
    public void testFindWhereName() {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try {
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl daoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName("grid");
            daoImpl.createGrid(session, grid);
            assertEquals(grid.getId(), daoImpl.findWhereName(session, grid.getName()).getId());
            assertNull(daoImpl.findWhereName(session,"none"));
            daoImpl.remove(session, grid);
            FacadeHelper.closeSession(session,"");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue");
        }
    }

    /**
     * Test de suppression de grille
     *
     */
    public void testRemoveGrids() {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try {
            session = PERSISTENTPROVIDER.getSession();
            QualityGridDAOImpl daoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName("grid");
            daoImpl.createGrid(session, grid);
            assertEquals(1, daoImpl.count(session).intValue());
            ArrayList l = new ArrayList();
            l.add(new Long(grid.getId()));
            daoImpl.removeGrids(session, l);
            assertEquals(0, daoImpl.count(session).intValue());
            FacadeHelper.closeSession(session,"");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue");
        }
    }

    /**
     * Test du delete en cascade
     *
     */
    public void testDeleteCascade() {
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try {
            session = PERSISTENTPROVIDER.getSession();
            // Création du facteur
            QualityRuleDAOImpl ruleDaoImpl = QualityRuleDAOImpl.getInstance();
            FactorRuleBO factor = new FactorRuleBO();
            assertEquals(0, ruleDaoImpl.count(session).intValue());
            ruleDaoImpl.create(session, factor);
            assertEquals(1, ruleDaoImpl.count(session).intValue());
            // Création de la grille
            QualityGridDAOImpl gridDaoImpl = QualityGridDAOImpl.getInstance();
            QualityGridBO grid = new QualityGridBO();
            grid.setName("grid");
            grid.addFactor(factor);
            gridDaoImpl.createGrid(session, grid);
            assertEquals(1, gridDaoImpl.count(session).intValue());
            // Suppression de la grille
            gridDaoImpl.remove(session, grid);
            // Vérification de la suppression en cascade
            assertEquals(0, gridDaoImpl.count(session).intValue());
            assertEquals(0, ruleDaoImpl.count(session).intValue());
            FacadeHelper.closeSession(session,"");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue");
        }
    }
}
