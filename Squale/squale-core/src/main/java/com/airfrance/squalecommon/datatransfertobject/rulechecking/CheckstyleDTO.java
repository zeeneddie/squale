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
package com.airfrance.squalecommon.datatransfertobject.rulechecking;

import java.io.Serializable;

/**
 * @author S Porto-Rico
 */
public class CheckstyleDTO
    extends RuleSetDTO
    implements Serializable
{
    /**
     * Contenu au format binaire du fichier de confuguration
     */
    protected byte[] mBytes;

    /**
     * Access method for the mBytes property.
     * 
     * @return the current value of the FileName property
     */
    public byte[] getBytes()
    {
        return mBytes;
    }

    /**
     * Sets the value of the mBytes property.
     * 
     * @param pBytes the new value of the mByte property
     */
    public void setBytes( byte[] pBytes )
    {
        mBytes = pBytes;
    }
}
