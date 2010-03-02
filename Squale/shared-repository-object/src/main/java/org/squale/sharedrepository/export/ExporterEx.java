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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This method represent an export file. This class correspond to the root node of an export file 
 */
@XStreamAlias( "exporter" )
public class ExporterEx
{

    /**
     * The exporter version
     */
    @XStreamAsAttribute
    private String exporterVersion;
    
    /**
     * The exported company
     */
    private CompanyEx company;

    /**
     * Constructor
     * 
     * @param pExporterVersion The exporter version
     * @param pCompany The exported company
     */
    public ExporterEx( String pExporterVersion, CompanyEx pCompany)
    {
        exporterVersion = pExporterVersion;
        company = pCompany;
    }

    /**
     * Getter method for the attribute exporterVersion 
     * 
     * @return The exporter version
     */
    public String getExporterVersion()
    {
        return exporterVersion;
    }

    /**
     * Setter method for the attribute exporterVersion
     * 
     * @param pExportVersion The new exporter version
     */
    public void setExporterVersion( String pExportVersion )
    {
        exporterVersion = pExportVersion;
    }

    /**
     * Getter method for the attribute company
     * 
     * @return The companyEx
     */
    public CompanyEx getCompany()
    {
        return company;
    }

    /**
     * Setter method for the attribute company
     *
     * @param pCompany the new companyEx
     */
    public void setCompany( CompanyEx pCompany )
    {
        company = pCompany;
    }
    
    
    
}
