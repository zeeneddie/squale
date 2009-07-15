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
package org.squale.squaleweb.util.graph;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;

import org.jfree.data.category.CategoryDataset;

/**
 */
public abstract class AbstractStatsMaker
    extends AbstractGraphMaker
{

    /**
     * @return la hauteur par défaut
     */
    public int getDefaultHeight()
    {
        return HEIGHT;
    }

    /**
     * @return la largeur par défaut
     */
    public int getDefaultWidth()
    {
        return WIDTH;
    }

    /** la hauteur du graph */
    private final static int HEIGHT = 400;

    /** la largeur du graph */
    private final static int WIDTH = 980;

    /** les données */
    protected CategoryDataset mDataSet;

    /** La couleur bleue */
    private final static Paint BLUE = new GradientPaint( 0.0f, 0.0f, Color.BLUE, 0.0f, 0.0f, Color.BLUE );

    /** La couleur bleue */
    private final static Paint CYAN = new GradientPaint( 0.0f, 0.0f, Color.CYAN, 0.0f, 0.0f, Color.CYAN );

    /** La couleur bleue */
    private final static Paint ORANGE = new GradientPaint( 0.0f, 0.0f, Color.ORANGE, 0.0f, 0.0f, Color.ORANGE );

    /** La couleur bleue */
    private final static Paint GRAY = new GradientPaint( 0.0f, 0.0f, Color.GRAY, 0.0f, 0.0f, Color.GRAY );

    /** le tableau regroupant les couleurs */
    final static protected Paint[] COLORS = new Paint[] { BLUE, CYAN, ORANGE, GRAY };

}
