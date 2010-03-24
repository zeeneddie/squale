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
/**
 * 
 */
package org.squale.squaleweb.gwt.motionchart.client.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Fabrice BELLINGARD
 */
public class AuditValues
    implements Serializable
{
    private static final long serialVersionUID = 3943402574981815987L;

    private Date date;

    private int linesOfCode;

    private int complexity;

    private HashMap<String, FactorValue> factorValues = new HashMap<String, FactorValue>();

    // for GWT
    public AuditValues()
    {
    }

    public AuditValues( Date auditDate )
    {
        this.date = auditDate;
    }

    public void addLinesOfCode( int metricValue )
    {
        linesOfCode += metricValue;
    }

    public void addVg( int metricValue )
    {
        complexity += metricValue;
    }

    public void addFactorValue( String factorId, float factorValue )
    {
        FactorValue factor = factorValues.get( factorId );
        if ( factor == null )
        {
            factor = new FactorValue();
            factorValues.put( factorId, factor );
        }
        factor.addValue( factorValue );
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @return the linesOfCode
     */
    public int getLinesOfCode()
    {
        return linesOfCode;
    }

    /**
     * @return the complexity
     */
    public int getComplexity()
    {
        return complexity;
    }

    /**
     * @return the factorValues
     */
    public float getFactorValue( String factorId )
    {
        return factorValues.get( factorId ).getValue();
    }
}
