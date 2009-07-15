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

import java.util.Date;
import java.util.Locale;

import org.squale.squaleweb.resources.WebMessages;
import org.squale.welcom.struts.bean.WActionForm;

/**
 * This class represent one manual mark with the element needed for add / modify manual mark
 */
public class ManualMarkElementForm
    extends WActionForm
    implements Comparable
{
    /** Id of the practice result when it already exist */
    private long resultId;

    /** RuleId */
    private long ruleId;

    /** Name of the rule */
    private String name;

    /** Value of the manual mark */
    private String value;

    /** Date of creation of the mark */
    private Date creationDate;

    /** Timeleft for the validity of the mark */
    private String timeleft;

    /** Duration of the validity period */
    private String timeLimitation;

    /** The validity period parse and translate */
    private String timeLimitationParse;
    
    /**
     * Getter method for the property timeLimitation
     * 
     * @return The time limitation
     */
    public String getTimelimitation()
    {
        return timeLimitation;
    }

    /**
     * Setter method for the property timeLimitation
     * 
     * @param mTimeLimitation The new timeLimitation
     */
    public void setTimelimitation( String mTimeLimitation )
    {
        timeLimitation = mTimeLimitation;
    }

    /**
     * Getter for the attribute resultId
     *  
     * @return The resultId
     */
    public long getResultId()
    {
        return resultId;
    }

    /**
     * Setter for the attribute resultId
     * 
     * @param id The new resultId
     */
    public void setResultId( long id )
    {
        resultId = id;
    }

    /**
     * Getter for the attribute name 
     *  
     * @return The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for the attribute name
     * 
     * @param pName The new name
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * This method fill the attribute name according to the local 
     * 
     * @param local The current local
     * @param mName the new name
     */
    public void setName( Locale local, String mName )
    {
        name = WebMessages.getString( local, mName );
    }

    /**
     * Getter for the attribute value
     * 
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Setter for the attribute value
     * 
     * @param pValue The new value
     */
    public void setValue( String pValue )
    {
        value = pValue;
    }

    /**
     * Getter for the attribute creationDate
     * 
     * @return The creation date
     */
    public Date getCreationDate()
    {
        return creationDate;
    }

    /**
     * Setter for the attribute creationDate
     * 
     * @param pCreationDate The new creation date
     */
    public void setCreationDate( Date pCreationDate )
    {
        creationDate = pCreationDate;

    }

    /**
     * Getter for the attribute timeleft
     * 
     * @return The timeleft
     */
    public String getTimeleft()
    {
        return timeleft;
    }

    /**
     * Setter for the attribute timeleft
     * 
     * @param pTimeleft the new timeleft
     */
    public void setTimeLeft( String pTimeleft )
    {
        timeleft = pTimeleft;
    }

    /**
     * Getter for the attribute ruleId
     * 
     * @return The ruleId
     */
    public long getRuleId()
    {
        return ruleId;
    }

    /**
     * Setter for the attribute ruleId
     * 
     * @param mRuleId the new ruleId
     */
    public void setRuleId( long mRuleId )
    {
        ruleId = mRuleId;
    }

    /**
     * Getter for the attribute timeLimitationParse
     * 
     * @return The timeLimitation parsed
     */
    public String getTimeLimitationParse()
    {
        return timeLimitationParse;
    }

    /**
     * Setter for the attribute timeLimitationParse
     * 
     * @param pTimeLimitationParse The new timeLimitation parsed
     */
    public void setTimeLimitationParse( String pTimeLimitationParse )
    {
        timeLimitationParse = pTimeLimitationParse;
    }
    
    /**
     * {@inheritDoc}
     */
    public int compareTo( Object o )
    {
        int result = 0;
        if ( o instanceof ManualMarkElementForm )
        {
            ManualMarkElementForm elt = (ManualMarkElementForm) o;
            if ( ( elt.getName() != null ) && ( getName() != null ) )
            {
                result = getName().compareTo( elt.getName() );
            }
        }
        return result;
    }

}
