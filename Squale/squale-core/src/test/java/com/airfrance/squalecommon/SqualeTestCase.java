package com.airfrance.squalecommon;

import java.sql.Connection;
import java.sql.Statement;

import junit.framework.TestCase;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

/**
 * Test unitaire de squale
 * Cette classe permet d'initialiser la couche JRAF
 * pour la bonne exécution des tests unitaires
 */
abstract public class SqualeTestCase extends TestCase {
    /** Session */
    private ISession mSession;

    /** Fabrique de composant */
    private ComponentFactory mComponentFactory;

    static {
        // Initialisation de la couche JRAF
        JRafConfigurator.initialize();
    }
    /**
     * 
     */
    public SqualeTestCase() {
        super();
    }

    /**
     * @param arg0 nom
     */
    public SqualeTestCase(String arg0) {
        super(arg0);
        JRafConfigurator.initialize();
    }

    /**
     * @return fabrique
     */
    public ComponentFactory getComponentFactory() {
        return mComponentFactory;
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

        // Initialisation
        ISession session = null; // session Hibernate
        session = PERSISTENTPROVIDER.getSession();
        session.beginTransaction();
        Connection conn = ((SessionImpl) session).getSession().connection();
        Statement stm = conn.createStatement();
        stm.execute("SET REFERENTIAL_INTEGRITY FALSE");
        IDatabaseConnection dc = new DatabaseConnection(((SessionImpl) session).getSession().connection());
        DatabaseOperation.DELETE_ALL.execute(dc, dc.createDataSet());
        stm.execute("SET REFERENTIAL_INTEGRITY TRUE");
        session.commitTransaction();
        session.closeSession();

        // Utilisation d'une autre session
        mSession = PERSISTENTPROVIDER.getSession();
        mComponentFactory = new ComponentFactory(mSession);
    }

    /**
     * Obtention de la session
     * @return session
     */
    protected ISession getSession() {
        return mSession;
    }

    /** (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        getSession().closeSession();
    }

}
