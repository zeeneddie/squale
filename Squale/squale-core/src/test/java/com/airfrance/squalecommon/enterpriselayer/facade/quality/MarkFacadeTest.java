package com.airfrance.squalecommon.enterpriselayer.facade.quality;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

/**
 * @author M400841
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class MarkFacadeTest extends TestCase {

    /**
     * log
     */
    private static Log LOG;
        

    /**
     * provider de persistence
     */
    private static IPersistenceProvider PERSISTENTPROVIDER;


    /**
     * Constructor for MarkFacadeTest.
     * @param arg0 le nom
     */
    public MarkFacadeTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        JRafConfigurator.initialize();
        LOG = LogFactory.getLog(MarkFacadeTest.class);
        PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
