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
package org.squale.squalecommon.enterpriselayer.businessobject.job;

/**
 * Possible job name
 */
public enum JobName
{
    /** Job for create an export for the shared repository */
    APPLICATION_EXPORT( "applicationExport" );

    /** Enum label */
    protected String label;

    /**
     * Constructor
     * 
     * @param pLabel The label of the enum
     */
    private JobName( String pLabel )
    {
        this.label = pLabel;
    }

    /**
     * Getter method for the label of the enum
     * 
     * @return The label of the enum
     */
    public String getLabel()
    {
        return label;
    }

}
