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
