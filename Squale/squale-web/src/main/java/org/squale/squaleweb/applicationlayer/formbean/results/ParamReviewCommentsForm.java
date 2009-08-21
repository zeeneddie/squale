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
package org.squale.squaleweb.applicationlayer.formbean.results;

import java.util.Date;

import org.squale.welcom.struts.bean.WActionForm;

/***
 * This form represents one manual mark comments
 * 
 * @author xpetitrenaud
 *
 */
public class ParamReviewCommentsForm
    extends WActionForm
{
    /***
     * The value of the manual mark
     */
    private String value;
    
    /***
     * The comments of the manual mark
     */
    private String comments;
    
    /***
     * The date of the manual mark
     */
    private Date manualMarkDate;

    /***
     * Getter method for the property value
     * 
     * @return the value
     */
    public String getValue()
    {
        return value;
    }
    
    /***
     * Setter method for the value
     * 
     * @param pValue the new value
     */
    public void setValue( String pValue )
    {
        this.value = pValue;
    }

    /***
     * Getter method for the property comments
     * 
     * @return the comments
     */
    public String getComments()
    {
        return comments;
    }

    /***
     * Setter method for the property comments 
     * 
     * @param pComments the new comments
     */
    public void setComments( String pComments )
    {
        this.comments = pComments;
    }

    /***
     * Getter method for the property manual mark date
     * 
     * @return the manual mark date
     */
    public Date getManualMarkDate()
    {
        return manualMarkDate;
    }

    /***
     * Setter method for the property manual mark date
     * 
     * @param pManualMarkDate the new manual mark date
     */
    public void setManualMarkDate( Date pManualMarkDate )
    {
        this.manualMarkDate = pManualMarkDate;
    }
}