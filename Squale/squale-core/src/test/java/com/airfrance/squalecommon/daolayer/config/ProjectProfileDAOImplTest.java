package com.airfrance.squalecommon.daolayer.config;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

import junit.framework.TestCase;

/**
 * 
 */
public class ProjectProfileDAOImplTest
    extends TestCase
{
    /** provider de persistence */
    private static IPersistenceProvider PERSISTENT_PROVIDER;

    /** log */
    private static Log LOG;

    /**
     * Constructor for SourceManagementDAOImplTest.
     * 
     * @param arg0 nom
     */
    public ProjectProfileDAOImplTest( String arg0 )
    {
        super( arg0 );
        JRafConfigurator.initialize();
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( ProjectProfileDAOImplTest.class );
        }
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown()
        throws Exception
    {
        super.tearDown();
    }

    /**
     * @see SourceManagementDAOImpl#findWhereName(ISession, String)
     */
    public void testFindWhereName()
    {
        JRafConfigurator.initialize();
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            ProjectProfileDAOImpl daoImpl = ProjectProfileDAOImpl.getInstance();
            ProjectProfileBO manager = new ProjectProfileBO();
            manager.setName( "manager" );
            TaskBO aTask = new TaskBO();
            aTask.setName( "analysisTask" );
            TaskRefBO aRefTask = new TaskRefBO();
            aRefTask.setTask( aTask );
            aRefTask.addParameter( "arg0", "value0" );
            aRefTask.addParameter( "arg1", "value1" );
            manager.addAnalysisTask( aRefTask );
            TaskBO tTask = new TaskBO();
            tTask.setName( "terminationTask" );
            TaskRefBO tRefTask = new TaskRefBO();
            tRefTask.setTask( tTask );
            manager.addTerminationTask( tRefTask );
            LOG.info( "On crée le manager dans la base:\n" );
            daoImpl.create( session, manager );
            LOG.info( "Appel de findWhereName (3 fois):\n" );
            ProjectProfileBO found = daoImpl.findWhereName( session, manager.getName() );
            assertEquals( manager.getId(), found.getId() );
            assertEquals( 1, found.getAnalysisTasks().size() );
            assertNull( daoImpl.findWhereName( session, "none" ) );
            daoImpl.remove( session, manager );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * @see SourceManagementDAOImpl#removeOthers(ISession, Collection)
     */
    public void testRemoveOthers()
        throws JrafPersistenceException
    {

        JRafConfigurator.initialize();
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            ProjectProfileDAOImpl daoImpl = ProjectProfileDAOImpl.getInstance();
            Collection managers = new ArrayList();
            ProjectProfileBO manager1 = new ProjectProfileBO();
            manager1.setName( "manager1" );
            managers.add( manager1 );
            daoImpl.create( session, manager1 );
            ProjectProfileBO managerToRemove = new ProjectProfileBO();
            managerToRemove.setName( "managerToRemove" );
            LOG.info( "On crée les 2 managers dans la base:\n" );
            daoImpl.create( session, manager1 );
            daoImpl.create( session, managerToRemove );
            LOG.info( "Appelle de la méthode removeOthers:\n" );
            daoImpl.removeOthers( session, managers );
            assertEquals( 1, daoImpl.findAll( session ).size() );
            LOG.info( "On supprime le dernier sourceManager:\n" );
            daoImpl.remove( session, manager1 );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

}
