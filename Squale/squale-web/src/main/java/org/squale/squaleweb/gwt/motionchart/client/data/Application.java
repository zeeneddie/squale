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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Fabrice BELLINGARD
 */
public class Application
    implements Serializable
{
    private static final long serialVersionUID = 5889015086352096098L;

    private String name;

    private HashMap<Long, AuditValues> auditValues = new HashMap<Long, AuditValues>();

    // for GWT
    public Application()
    {
    }

    public Application( String name )
    {
        this.name = name;
    }

    public AuditValues getAudit( long auditId, Date auditDate )
    {
        AuditValues audit = auditValues.get( auditId );
        if ( audit == null )
        {
            audit = new AuditValues( auditDate );
            auditValues.put( auditId, audit );
        }
        return audit;
    }

    public AuditValues getAudit( long auditId )
    {
        return auditValues.get( auditId );
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public Collection<AuditValues> getAuditValues()
    {
        return auditValues.values();
    }
}
