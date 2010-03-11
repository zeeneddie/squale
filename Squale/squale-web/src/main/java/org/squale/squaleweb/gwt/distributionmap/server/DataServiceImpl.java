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
package org.squale.squaleweb.gwt.distributionmap.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.gwt.distributionmap.widget.data.Child;
import org.squale.gwt.distributionmap.widget.data.Parent;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.enterpriselayer.facade.quality.QualityResultFacade;
import org.squale.squaleweb.gwt.distributionmap.client.DataService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service that provides data to the distribution map.
 * 
 * @author Fabrice BELLINGARD
 */
@SuppressWarnings( "serial" )
public class DataServiceImpl
    extends RemoteServiceServlet
    implements DataService
{
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( DataServiceImpl.class );

    /**
     * Date used for logging purposes
     */
    private Date initialDate;

    /**
     * {@inheritDoc}
     */
    public ArrayList<Parent> getData( long auditId, long projectId, long practiceId )
    {
        handleTime();

        Map<Long, Parent> parentMap = new HashMap<Long, Parent>();

        try
        {
            List<Object[]> componentInformationList =
                QualityResultFacade.getMarkDistribution( auditId, projectId, practiceId );

            // Iterate through the result to create the parent-child tree
            for ( Object[] componentInfo : componentInformationList )
            {
                long componentId = (Long) componentInfo[0];
                String componentName = (String) componentInfo[1];
                long parentId = (Long) componentInfo[2];
                String parentName = (String) componentInfo[3];
                float mark = (Float) componentInfo[4];

                Child child = new Child( componentId, componentName, mark );
                // attach the child to its parent
                Parent parent = parentMap.get( parentId );
                if ( parent == null )
                {

                    parent = new Parent( parentName );
                    parentMap.put( parentId, parent );

                }
                parent.addChild( child );
            }
        }
        catch ( JrafEnterpriseException e )
        {
            log.error( "Error while building the data needed to display the Dsitribution Map", e );
            // we do not need to do more, we return an empty array
        }

        handleTime();

        return new ArrayList<Parent>( parentMap.values() );
    }

    /**
     * Used for logging purposes only: prints the execution time
     */
    private void handleTime()
    {
        if ( log.isDebugEnabled() )
        {
            if ( initialDate == null )
            {
                initialDate = new Date();
            }
            else
            {
                Date current = new Date();
                log.debug( "Distribution Map data service execution time: "
                    + ( current.getTime() - initialDate.getTime() ) );
                initialDate = null;
            }
        }
    }
}
