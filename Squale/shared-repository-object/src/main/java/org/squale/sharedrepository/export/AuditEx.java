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
package org.squale.sharedrepository.export;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents an audit for the exporter 
 *
 */
@XStreamAlias( "audit" )
public class AuditEx
{
   
    /**
     * The audit date 
     */
    @XStreamAsAttribute
    private Date auditDate;
    
    /**
     * The release of the application
     */
    @XStreamAsAttribute
    private String release;
    
    /**
     * The audit technical id in the local Squale
     */
    @XStreamAsAttribute
    private String technicalId;

    
    /**
     * Constructor 
     * 
     * @param pAuditDate The audit date
     * @param pRelease The application release on which the audit has been done
     * @param pTechnicalId the technical id of the audit the local Squale
     */
    public AuditEx( Date pAuditDate, String pRelease, String pTechnicalId )
    {
        auditDate = pAuditDate;
        release = pRelease;
        technicalId = pTechnicalId;
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
     * Getter method for the attribute release
     * 
     * @return The release of the analyzed application
     */
    public String getRelease()
    {
        return release;
    }

    /**
     * Setter method for the attribute release
     * 
     * @param pRelease The new release of the analyzed application
     */
    public void setRelease( String pRelease )
    {
        release = pRelease;
    }

    /**
     * Getter method for the attribute technicalId
     * 
     * @return the technicalId of the audit
     */
    public String getTechnicalId()
    {
        return technicalId;
    }

    /**
     * Setter method for the attribute technicalId
     * 
     * @param pTechnicalId The new technicalId of The audit
     */
    public void setTechnicalId( String pTechnicalId )
    {
        technicalId = pTechnicalId;
    }
    
}
