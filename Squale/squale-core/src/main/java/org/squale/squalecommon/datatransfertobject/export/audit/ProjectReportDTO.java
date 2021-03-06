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
package org.squale.squalecommon.datatransfertobject.export.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data needed to generate audit report for one project
 */
public class ProjectReportDTO
{
    /** Project name */
    private String name;

    /** Profile name */
    private String profileName;

    /**
     * Volumetry of the project Key : tre of the metric value : value of the tre
     */
    private Map volumetryMeasures;

    /**
     * Measures for building bubble chart
     */
    private Object[] scatterplotMeasures;

    /** List of FactorReportDTO */
    private List qualityResults = new ArrayList();

    /** Number of line for the application */
    private int nbLines;

    /**
     * Getter for the volumetry
     * 
     * @return measures of volumetry
     */
    public Map getVolumetryMeasures()
    {
        return volumetryMeasures;
    }

    /**
     * Modify volumetry
     * 
     * @param pVolumetryMeasures new volumetry
     */
    public void setVolumetryMeasures( Map pVolumetryMeasures )
    {
        volumetryMeasures = pVolumetryMeasures;
    }

    /**
     * Getter for scatterplot measures
     * 
     * @return scatterplot measures
     */
    public Object[] getScatterplotMeasures()
    {
        return scatterplotMeasures;
    }

    /**
     * Modify scatterplot measures
     * 
     * @param pScatterplotMeasures
     */
    public void setScatterplotMeasures( Object[] pScatterplotMeasures )
    {
        scatterplotMeasures = pScatterplotMeasures;
    }

    /**
     * Getter for factors results
     * 
     * @return factors
     */
    public List getQualityResults()
    {
        return qualityResults;
    }

    /**
     * Modify results
     * 
     * @param pQualityResults new factors
     */
    public void setQualityResults( List pQualityResults )
    {
        qualityResults = pQualityResults;
    }

    /**
     * Add a factor result
     * 
     * @param factor new factor
     */
    public void addQualityResult( QualityReportDTO factor )
    {
        qualityResults.add( factor );
    }

    /**
     * Getter for the number of lines of the project
     * 
     * @return number of lines
     */
    public int getNbLines()
    {
        return nbLines;
    }

    /**
     * Modify number of lines
     * 
     * @param nbLines new value for the number of lines
     */
    public void setNbLines( int nbLines )
    {
        this.nbLines = nbLines;
    }

    /**
     * Getter for project's name
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Modify project's name
     * 
     * @param name new name
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Getter for project's profile
     * 
     * @return the profile
     */
    public String getProfileName()
    {
        return profileName;
    }

    /**
     * Modify project's profile
     * 
     * @param profileName new profile
     */
    public void setProfileName( String profileName )
    {
        this.profileName = profileName;
    }
}