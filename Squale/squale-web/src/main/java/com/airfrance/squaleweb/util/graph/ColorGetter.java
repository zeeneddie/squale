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
