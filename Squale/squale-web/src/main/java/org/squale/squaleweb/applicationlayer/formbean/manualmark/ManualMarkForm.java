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
package org.squale.squaleweb.applicationlayer.formbean.manualmark;

import java.util.ArrayList;
import java.util.Date;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * This class is the form linked to the jsp manaualMark
 */
public class ManualMarkForm
    extends RootForm
{
    
    /** The out of date message according the local*/
    private String outOfDate;
    
    /** The application id. It's the same id than mApplicationId in the root form, but this one won't be reset */
    private String canModify;
    
    /** The project id. It's the same id than mProjectId in the root form, but this one won't be reset */
    private String projectIdSafe;

    /** List of the manual practice rule and result of the quality grid linked to the project */
    private ArrayList<ManualMarkElementForm> manualPracticeList;

    /** The search param. Used for keep the search during the transaction client-server */
    private String search;

    /** Keep a creation date during the exchange client-server */
    private Date temporDate;

    /** Keep a mark during the exchange client-server */
    private String temporValue;
    
    /** Keep a comment during the exchange client-server */
    private String temporComments;
    
    /** Number of the line edited (if one is edit) */
    private String editLine;

    /**
     * Getter for the attribute editline
     * 
     * @return The line edited
     */
    public String getEditLine()
    {
        return editLine;
    }

    /**
     * Setter for the attribute editLine 
     * 
     * @param pEditLine The new line edited
     */
    public void setEditLine( String pEditLine )
    {
        editLine = pEditLine;
    }

    /**
     * Getter for the attribute temporDate
     * 
     * @return the kept date
     */
    public Date getTemporDate()
    {
        return temporDate;
    }

    /**
     * Setter for the attribute temporDate
     * 
     * @param pTemporDate The new date to keep
     */
    public void setTemporDate( Date pTemporDate )
    {
        temporDate = pTemporDate;
    }

    /**
     * Getter for the attribute temporValue
     * 
     * @return The kept mark
     */
    public String getTemporValue()
    {
        return temporValue;
    }

    /**
     * Setter for the attribute temporValue 
     * 
     * @param pTemporValue The new mark to keep
     */
    public void setTemporValue( String pTemporValue )
    {
        temporValue = pTemporValue;
    }

    /**
     * Getter for the attribute search
     * 
     * @return The string to search
     */
    public String getSearch()
    {
        return search;
    }

    /**
     * Setter for the attribute search 
     * 
     * @param pSearch the new String to search
     */
    public void setSearch( String pSearch )
    {
        search = pSearch;
    }

    /**
     * Getter method for the properties manualPracticeList
     * 
     * @return The list of manual practice of the quality grid linked to the project
     */
    public ArrayList<ManualMarkElementForm> getManualPracticeList()
    {
        return manualPracticeList;
    }

    /**
     * Setter method for the properties manualPracticeList
     * 
     * @param pManualPraticeList The new list of manual practice
     */
    public void setManualPracticeList( ArrayList<ManualMarkElementForm> pManualPraticeList )
    {
        manualPracticeList = pManualPraticeList;
    }

    /**
     * Add the element given in argument to the manualPracticeList property
     * 
     * @param element The ManualMarkElement to add
     */
    public void add( ManualMarkElementForm element )
    {
        manualPracticeList.add( element );
    }

    /**
     * Getter for the attribute projectIdSafe
     * 
     * @return The kept project id 
     */
    public String getProjectIdSafe()
    {
        return projectIdSafe;
    }

    /**
     * Setter for the attribute projectIdSafe 
     * 
     * @param pProjectIdSafe The new project id to kept
     */
    public void setProjectIdSafe( String pProjectIdSafe )
    {
        projectIdSafe = pProjectIdSafe;
    }

    /**
     * Getter for attribute canModify
     * 
     * @return The modification right 
     */
    public String getCanModify()
    {
        return canModify;
    }

    /**
     * Setter for the attribute canModify
     * 
     * @param pCanModify The new modification right
     */
    public void setCanModify( String pCanModify )
    {
        canModify = pCanModify;
    }

    /**
     * Getter for the attribute outOfDate
     * 
     * @return The value for out of date
     */
    public String getOutOfDate()
    {
        return outOfDate;
    }

    /**
     * Setter for the attribute outOfDate
     * 
     * @param pOutOfDate The new value for out of date
     */
    public void setOutOfDate( String pOutOfDate )
    {
        outOfDate = pOutOfDate;
    }

    /***
     * Getter for the attribute temporComments
     * 
     * @return The kept comments
     */
    public String getTemporComments()
    {
        return temporComments;
    }
    
    /***
     * Setter for the attribute temporComments
     * 
     * @param pTemporComments The new value for the kept comments
     */
    public void setTemporComments( String pTemporComments )
    {
        this.temporComments = pTemporComments;
    }
}
