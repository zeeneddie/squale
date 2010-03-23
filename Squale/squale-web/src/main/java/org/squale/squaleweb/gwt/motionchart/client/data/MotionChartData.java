/**
 * 
 */
package org.squale.squaleweb.gwt.motionchart.client.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fabrice BELLINGARD
 */
public class MotionChartData
    implements Serializable
{
    private static final long serialVersionUID = -5132070224930028701L;

    private HashMap<String, String> factors = new HashMap<String, String>();

    private ArrayList<Application> applications = new ArrayList<Application>();

    public void addFactor( String factorId, String factorName )
    {
        factors.put( factorId, factorName );
    }

    public Application createApplication( String applicationName )
    {
        Application app = new Application( applicationName );
        applications.add( app );
        return app;
    }

    public Collection<Application> getApplications()
    {
        return applications;
    }

    /**
     * @return the factors
     */
    public Map<String, String> getFactors()
    {
        return factors;
    }
}
