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
package com.airfrance.squaleweb.util.graph;

import java.awt.Color;
import java.util.Arrays;

/**
 * @author M400843
 */
public class ColorGetter
{
    /**
     * tableaux contenant des couleurs différentes
     */
    private Color[] mColors = { Color.red, Color.green, Color.blue, Color.yellow, Color.cyan, Color.orange };

    /**
     * @param pSize nombre de couleurs souhaités
     * @return pSize couleurs
     */
    public Color[] getColors( int pSize )
    {
        Color[] colors = new Color[pSize];

        if ( pSize > mColors.length )
        {
            Arrays.fill( colors, Color.gray );
            System.arraycopy( mColors, 0, colors, 0, mColors.length );
        }
        else
        {
            System.arraycopy( mColors, 0, colors, 0, pSize );
        }
        return colors;
    }

}
