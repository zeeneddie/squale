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
package org.squale.welcom.outils.jdbc;

import java.io.ByteArrayInputStream;

/**
 * @author user
 * @deprecated
 */
public class WClob
{

    /** Represtation interne du clob */
    private String clob = null;

    /** Constreur privé inacessible */
    private WClob()
    {
    }

    /**
     * Constructeur d'un clob
     * 
     * @param pClob Chaine du clob : permet de depassé la limite de 4000 Char
     */
    public WClob( final String pClob )
    {
        this.clob = pClob;
    }

    /**
     * @return la longuer de la chaine
     */
    public int getLength()
    {
        return clob.length();
    }

    /**
     * @return Transforme la chaine en une inputStream
     */
    public ByteArrayInputStream getInputStream()
    {
        return new ByteArrayInputStream( clob.getBytes() );
    }
}