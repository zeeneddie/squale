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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The asynchronous interface of the DataService RPC service.
 * 
 * @author Fabrice BELLINGARD
 */
public interface DataServiceAsync
{
    /**
     * @see org.squale.squaleweb.gwt.distributionmap.client.DataService#getData(long, long)
     */
    void getData( long auditId, long projectId, long practiceId, AsyncCallback<ArrayList<Parent>> callback );

}
