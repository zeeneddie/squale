package org.squale.squalerest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.squale.squalerest.client.SqualeRestHttpClient;
import org.squale.squalerest.exception.SqualeRestException;
import org.squale.squalerest.root.Applications;
import org.squale.squalerest.root.ByApplication;
import org.squale.squalerest.root.ByAudit;

/**
 * Test class of {@link SqualeRestHttpClient} These tests needs :
 * <ul>
 * <li>A started squale server</li>
 * <li>A server.properties file (src/teste/ressources) correctly filed.</li>
 * </ul>
 * 
 * @author bfranchet
 */
public class SqualeRestHttpClientTest
    extends TestCase
{

    /**
     * The http client
     */
    private SqualeRestHttpClient httpClient;

    /**
     * The application id to recover. That suppose it exists an application with this id in the started server and that
     * application is accessible to the user given in the properties file.
     */
    private int applicationId;

    /**
     * The audit id to recover. That suppose it exists an audit with this id in the started server and that application
     * linked to this audit is accessible to the user given in the properties file.
     */
    private int auditId;

    /**
     * Initialization method
     * 
     * @throws IOException Exception occurs during the initialization
     */
    @Before
    public void setUp()
        throws IOException
    {
        httpClient = new SqualeRestHttpClient();
        Properties prop = new Properties();
        InputStream is = getClass().getResourceAsStream( "/server.properties" );
        prop.load( is );
        String login = prop.getProperty( "login" );
        String password = prop.getProperty( "password" );
        String host = prop.getProperty( "host" );
        int port = Integer.parseInt( prop.getProperty( "port" ) );
        String contextRoot = prop.getProperty( "contextroot" );
        httpClient.initialize( login, password, host, port, contextRoot );
        applicationId = Integer.parseInt( prop.getProperty( "application.id" ) );
        auditId = Integer.parseInt( prop.getProperty( "audit.id" ) );
        if ( is != null )
        {
            is.close();
        }
    }

    /**
     * Try to recover the application available for the current user
     * 
     * @throws SqualeRestException Errors occurs
     */
    @Test
    public void testGetApplications()
        throws SqualeRestException
    {
        Applications availableApplications = httpClient.getApplications();
        assertNotNull( availableApplications );
    }

    /**
     * Try to recover the data linked to the application id
     * 
     * @throws SqualeRestException Errors occurs
     */
    @Test
    public void testGetApplication()
        throws SqualeRestException
    {
        ByApplication application = httpClient.getApplication( applicationId );
        assertNotNull( application );
    }

    /**
     * Try to recover the data linked to the audit
     * 
     * @throws SqualeRestException error occurs
     */
    @Test
    public void testGetAudit()
        throws SqualeRestException
    {
        ByAudit audit = httpClient.getAudit( auditId );
        assertNotNull( audit );
    }

}
