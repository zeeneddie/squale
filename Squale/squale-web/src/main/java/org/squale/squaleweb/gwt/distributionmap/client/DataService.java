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
package org.squale.squaleweb.gwt.distributionmap.client;

import java.util.ArrayList;

import org.squale.gwt.distributionmap.widget.data.Parent;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 * 
 * @author Fabrice BELLINGARD
 */
@RemoteServiceRelativePath( "data" )
public interface DataService
    extends RemoteService
{
    /**
     * Returns a list of Parent objects used by the Distribution Map widget to populate its content.
     * 
     * @param auditId the audit for which the DMap will display the results
     * @param projectId the project for which the DMap will display the results
     * @param practiceId the practice for which the DMap will display the results
     * @return the list of Parent objects used by the DMap
     */
    ArrayList<Parent> getData( long auditId, long projectId, long practiceId );
}
