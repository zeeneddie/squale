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

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

public class SharedRepoStatForm
    extends RootForm
{

    /** The mean for this factor on the segmentation linked to the application */
    private String referenceMean;

    /** The max for this factor on the segmentation linked to the application */
    private String referenceMax;

    /** The min for this factor on the segmentation linked to the application */
    private String referenceMin;

    /** The deviation for this factor on the segmentation linked to the application */
    private String referenceDeviation;

    /**
     * Getter method for the attribute referenceMean
     * 
     * @return The mean value
     */
    public String getReferenceMean()
    {
        return referenceMean;
    }

    /**
     * Setter method for the attribute referenceMean
     * 
     * @param pReferenceMean The new mean value
     */
    public void setReferenceMean( String pReferenceMean )
    {
        referenceMean = pReferenceMean;
    }

    /**
     * Getter method for the attribute referenceMax
     * 
     * @return The max value
     */
    public String getReferenceMax()
    {
        return referenceMax;
    }

    /**
     * Setter method for the attribute referenceMax
     * 
     * @param pReferenceMax The new max value
     */
    public void setReferenceMax( String pReferenceMax )
    {
        referenceMax = pReferenceMax;
    }

    /**
     * Getter method for the attribute referenceMin
     * 
     * @return The min value
     */
    public String getReferenceMin()
    {
        return referenceMin;
    }

    /**
     * Setter method for the attribute referenceMin
     * 
     * @param pReferenceMin The new min value
     */
    public void setReferenceMin( String pReferenceMin )
    {
        referenceMin = pReferenceMin;
    }

    /**
     * Getter method for the attribute referenceDeviation
     * 
     * @return The deviation value
     */
    public String getReferenceDeviation()
    {
        return referenceDeviation;
    }

    /**
     * Setter method for the attribute referenceDeviation
     * 
     * @param pReferenceDeviation The new deviation value
     */
    public void setReferenceDeviation( String pReferenceDeviation )
    {
        referenceDeviation = pReferenceDeviation;
    }

}
