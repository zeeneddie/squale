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
package org.squale.squaleweb.applicationlayer.formbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.squale.squaleweb.applicationlayer.formbean.component.AuditForm;
import org.squale.squaleweb.applicationlayer.formbean.results.ResultListForm;
import org.squale.squaleweb.homepage.Stat;
import org.squale.squaleweb.util.graph.GraphMaker;

/**
 * Form for the homepage jsp
 */
public class HomepageForm
    extends ResultListForm
{
    
    /** Does we use the default configuration of homepage*/
    private boolean isDefault;
    
    /** The list of elementToDisplay*/
    private String[] elementToDisplay; 
    
    
    /** The list of volumetric measure (for the statistics)*/
    private List<Stat> volumetrie = new ArrayList<Stat>();

    /**
     * List of GraphMaker
     */
    private HashMap<Long, GraphMaker> graphMakerMap = new HashMap<Long, GraphMaker>();

    /**
     * List of audits
     */
    private List<AuditForm> audits = new ArrayList<AuditForm>();

    /**
     * List of scheduled audits (if audit done and audit scheduled should be display separately)
     */
    private List<AuditForm> scheduledAudits = new ArrayList<AuditForm>();

    
    /**
     * Getter method for the parameter audits
     * 
     * @return the list of audits
     */
    public List<AuditForm> getAudits()
    {
        return audits;
    }

    /**
     * Setter method for the parameter audits
     * 
     * @param mAudits The new list of audits
     */
    public void setAudits( List<AuditForm> mAudits )
    {
        audits = mAudits;
    }

    /**
     * Getter method for the parameter scheduledAudits
     * 
     * @return the list of scheduled audits
     */
    public List<AuditForm> getScheduledAudits()
    {
        return scheduledAudits;
    }

    /**
     * Setter method for the parameter ScheduledAudits
     * 
     * @param mScheduledAudits The new list of scheduled Audits
     */
    public void setScheduledAudits( List<AuditForm> mScheduledAudits )
    {
        scheduledAudits = mScheduledAudits;
    }

    /**
     * Getter method for the parameter graphMakermap
     * 
     * @return The map of GraphMaker
     */
    public HashMap<Long, GraphMaker> getGraphMakerMap()
    {
        return graphMakerMap;
    }

    /**
     * Setter method for the parameter GraphMaker
     * 
     * @param mGraphMakerMap The new GraphMakerMap
     */
    public void setGraphMakerMap( HashMap<Long, GraphMaker> mGraphMakerMap )
    {
        graphMakerMap = mGraphMakerMap;
    }

    /**
     * Getter methods for the parameter volumetrie
     * 
     * @return The list of volumetric measure
     */
    public List<Stat> getVolumetrie()
    {
        return volumetrie;
    }

    /**
     * Setter method for the parameter volumetrie 
     * 
     * @param pVolumetrie The new list of volumetric measure
     */
    public void setVolumetrie( List<Stat> pVolumetrie )
    {
        volumetrie = pVolumetrie;
    }

    /**
     * Getter method for the attribute elementToDisplay 
     * 
     * @return The list of element to display
     */
    public String[] getElementToDisplay()
    {
        return elementToDisplay;
    }

    /**
     * Setter method for the attribute elementToDisplay
     * 
     * @param mElementToDisplay The new list of element to display
     */
    public void setElementToDisplay( String[] mElementToDisplay )
    {
        elementToDisplay = mElementToDisplay;
    }

    /**
     * Setter method for the attribute default 
     * 
     * @return True if we use the default configuration of homepage 
     */
    public boolean isDefault()
    {
        return isDefault;
    }

    /**
     * Getter method for the attribute default
     * 
     * @param mIsDefault The new value for default 
     */
    public void setDefault( boolean mIsDefault )
    {
        isDefault = mIsDefault;
    }

}
