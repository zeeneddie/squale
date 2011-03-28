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

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Application class used for the export
 */
@XStreamAlias( "application" )
public class ApplicationRest
{

    /**
     * Id of the application
     */
    @XStreamAsAttribute
    private String id;

    /**
     * Name of the application
     */
    @XStreamAsAttribute
    private String name;
    
    /**
     * Is a public application
     */
    @XStreamAlias("public")
    @XStreamAsAttribute
    private Boolean publicApplication;

    /**
     * The audit exported
     */
    private AuditRest audit;

    /**
     * List of modules ({@link ModuleRest}) linked to the application
     */
    @XStreamImplicit 
    private List<ModuleRest> modules;

    /**
     * The list of tag linked to the application
     */
    private List<TagRest> tags;

    /**
     * The list of volumetry measure linked to the application
     */
    @XStreamImplicit
    private List<VolumetryRest> volumetry;
    
    /**
     * List of successfull audits
     */
    @XStreamAlias("successful-audits")
    private List<AuditRest> successfulAudits;

    
    /**
     * List of successfull audits
     */
    @XStreamAlias("partial-audits")
    private List<AuditRest> partialAudits;

    
    /**
     * List of successfull audits
     */
    @XStreamAlias("failed-audits")
    private List<AuditRest> failedAudits;
    
    
    /**
     * Constructor
     */
    public ApplicationRest()
    {
        modules = new ArrayList<ModuleRest>();
        tags = new ArrayList<TagRest>();
        volumetry = new ArrayList<VolumetryRest>();
    }

    /**
     * Full constructor
     * 
     * @param pId Id of the application
     * @param pName Name of the application
     * @param pPublic The public status of the application
     */
    public ApplicationRest( String pId, String pName, Boolean pPublic )
    {
        id = pId;
        name = pName;
        publicApplication = pPublic;
    }

    /**
     * Getter method for the attribute id
     * 
     * @return The id of the application
     */
    public String getId()
    {
        return id;
    }

    /**
     * Setter method for the attribute id
     * 
     * @param pId The new id of the application
     */
    public void setId( String pId )
    {
        id = pId;
    }

    /**
     * Add a new module({@link ModuleRest} to the application
     * 
     * @param module The new module to add
     */
    public void addModule( ModuleRest module )
    {
        if(modules == null)
        {
            modules = new ArrayList<ModuleRest>();
        }
        modules.add( module );
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The application name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the application
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute modules
     * 
     * @return The list of moduleEx
     */
    public List<ModuleRest> getModules()
    {
        return modules;
    }

    /**
     * Setter method for the attribute modules
     * 
     * @param pModules The new list of moduleEx
     */
    public void setModules( List<ModuleRest> pModules )
    {
        modules = pModules;
    }

    /**
     * Getter method for the attribute audit
     * 
     * @return The audit
     */
    public AuditRest getAudit()
    {
        return audit;
    }

    /**
     * Setter method for the attribute audit
     * 
     * @param pAudit The audit to add
     */
    public void setAudit( AuditRest pAudit )
    {
        audit = pAudit;
    }

    /**
     * Getter method for the attribute tags
     * 
     * @return The list of tags linked to application
     */
    public List<TagRest> getTags()
    {
        return tags;
    }

    /**
     * Setter method for the attribute tags
     * 
     * @param pTags The new list of tags
     */
    public void setTags( List<TagRest> pTags )
    {
        tags = pTags;
    }

    /**
     * Getter method for the attribute volumetry
     * 
     * @return The volumetry The list of volumetry informations
     */
    public List<VolumetryRest> getVolumetry()
    {
        return volumetry;
    }

    /**
     * Setter method for the attribute volumetry
     * 
     * @param pVolumetry The volumetry The new list of volumetry informations 
     */
    public void setVolumetry( List<VolumetryRest> pVolumetry )
    {
        volumetry = pVolumetry;
    }
    
    /**
     * Setter method for the audit attribute
     * 
     * @param pAuditList The new audit list
     */
    public void setSuccessfulAudits( List<AuditRest> pAuditList )
    {
        successfulAudits=pAuditList;
    }
    
    /**
     * Getter method for the attribute audits
     * 
     * @return The list of audit
     */
    public List<AuditRest> getSuccessfulAudits()
    {
        return successfulAudits;
    }

    /**
     * Getter method for the attribute partialAudits
     * 
     * @return The list of partial audits 
     */
    public List<AuditRest> getPartialAudits()
    {
        return partialAudits;
    }

    /**
     * Setter method for the attribute  partialAudits
     * 
     * @param pPartialAudits The new list of partial audits
     */
    public void setPartialAudits( List<AuditRest> pPartialAudits )
    {
        this.partialAudits = pPartialAudits;
    }

    /**
     * Getter method for the attribute failedAudits
     * 
     * @return The list of failed audits
     */
    public List<AuditRest> getFailedAudits()
    {
        return failedAudits;
    }

    /**
     * Setter method for the attribute failedAudits
     * 
     * @param pFailedAudits The new list of failed audits
     */
    public void setFailedAudits( List<AuditRest> pFailedAudits )
    {
        this.failedAudits = pFailedAudits;
    }

    /**
     * Getter method for the attribute publicApplication
     * 
     * @return True if the application is public
     */
    public Boolean getPublicApplication()
    {
        return publicApplication;
    }

    /**
     * Setter method for the attribute publicApplication
     * 
     * @param pPublicApplication The new public status of the application
     */
    public void setPublicApplication( Boolean pPublicApplication )
    {
        this.publicApplication = pPublicApplication;
    }

}