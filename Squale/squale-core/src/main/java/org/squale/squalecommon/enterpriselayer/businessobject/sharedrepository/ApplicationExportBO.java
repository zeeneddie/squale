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
package org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository;

import java.io.Serializable;
import java.util.Date;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * This class represent the last export date for the linked application
 * 
 * @hibernate.class table="ApplicationLastExport"
 */
public class ApplicationExportBO
    implements Serializable
{
    /**
     * Version UID
     */
    private static final long serialVersionUID = -305087241745476212L;

    /**
     * Thecnical id
     */
    private long id=-1;

    /**
     * The application
     */
    private ApplicationBO application;

    /**
     * The date of the last export for the application
     */
    private Date lastExportDate;
    
    /**
     * Does the application should be export
     */
    private boolean toExport;

    /**
     * Getter for the thecnical id
     * 
     * @return the id of this ApplicationLastExport
     * @hibernate.id generator-class="native" type="long" column="LastExportId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="lastExport_sequence"
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter for the technical id
     * 
     * @param pId The new technical id of this ApplicationLastExport
     */
    public void setId( long pId )
    {
        id = pId;
    }

    /**
     * Getter for the attribute application
     * 
     * @return the application concern by the export
     * @hibernate.many-to-one column="ComponentId"
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO"
     *                        cascade="none" unique="true"
     */
    public ApplicationBO getApplication()
    {
        return application;
    }

    /**
     * Setter for the attribute application
     * 
     * @param pApplication The new application
     */
    public void setApplication( ApplicationBO pApplication )
    {
        application = pApplication;
    }

    /**
     * Getter for the attribute date
     * 
     * @return The date of the last export for the application
     * @hibernate.property name="date" column="LastExportDate" type="timestamp" update="true" insert="true"
     */
    public Date getLastExportDate()
    {
        return lastExportDate;
    }

    /**
     * Setter for the attribute date
     * 
     * @param pLastExportDate The new date of the last export
     */
    public void setLastExportDate( Date pLastExportDate )
    {
        lastExportDate = pLastExportDate;
    }
    
    /**
     * Getter for the attribute toExport 
     * 
     * @return  true if this application should be export
     * @hibernate.property name="export" column="ToExport" type="boolean" update="true" insert="true"
     */
    public boolean getToExport()
    {
        return toExport;
    }

    /**
     * Setter for the attribute toExport
     * 
     * @param pToExport The new export status for the application 
     */
    public void setToExport( boolean pToExport )
    {
        toExport = pToExport;
    }

}
