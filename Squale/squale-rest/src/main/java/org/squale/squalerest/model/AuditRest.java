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
package org.squale.squalerest.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents an audit for the exporter 
 *
 */
@XStreamAlias( "audit" )
public class AuditRest
{
   
    /**
     * The audit date 
     */
    @XStreamAsAttribute
    private Date auditDate;
    
    /**
     * The audit technical id in the local Squale
     */
    @XStreamAsAttribute
    private String id;

    
    /**
     * Constructor 
     * 
     * @param pAuditDate The audit date
     * @param pId the technical id of the audit the local Squale
     */
    public AuditRest( Date pAuditDate, String pId )
    {
        auditDate = pAuditDate;
        id = pId;
    }

    /**
     * Getter method for the attribute audit date
     * 
     * @return The audit date
     */
    public Date getAuditDate()
    {
        return auditDate;
    }

    /**
     * Setter method for the attribute auditDate
     * 
     * @param pAuditDate the new attribute for the audit date 
     */
    public void setAuditDate( Date pAuditDate )
    {
        auditDate = pAuditDate;
    }

    /**
     * Getter method for the attribute technicalId
     * 
     * @return the technicalId of the audit
     */
    public String getId()
    {
        return id;
    }

    /**
     * Setter method for the attribute technicalId
     * 
     * @param pId The new technicalId of The audit
     */
    public void setId( String pId )
    {
        id = pId;
    }
    
}
