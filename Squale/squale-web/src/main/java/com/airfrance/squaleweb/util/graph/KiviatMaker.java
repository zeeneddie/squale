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

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 * Classe responsable de la création d'un kiviat
 * 
 * @author M400843
 * @see AbstractGraphMaker {@inheritDoc}
 */
public class KiviatMaker
    extends AbstractGraphMaker
{

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 600;

    /**
     * Valeur maximale sur les axes
     */
    private static final double SCALE_MAX_VALUE = 3.0;

    /**
     * Remplissage des zone du radar
     */
    private static final boolean FILL_RADAR = true;

    /**
     * Valeurs à mettre dans le diagramme
     */
    private DefaultCategoryDataset mDataset = new DefaultCategoryDataset();

    /**
     * The default gap for the plot. it's the gap between the border of the plot and what is draw in it. It's near same
     * than padding
     */
    public static final double DEFAULT_GAP = 0.3;

    /**
     * Constructeur par défaur
     */
    public KiviatMaker()
    {
    }

    /**
     * Constructeur avec le titre du diagramme
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public KiviatMaker( String pTitle )
    {
        mTitle = pTitle;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultHeight() 
     */
    protected int getDefaultHeight()
    {
        return DEFAULT_HEIGHT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultWidth() 
     */
    protected int getDefaultWidth()
    {
        return DEFAULT_WIDTH;
    }

    /**
     * Ajoute les d'un composant. <br />
     * <b>Attention : </b> pour assurer la cohérences des données, si cette méthode est appellée plusieurs fois, pValues
     * doit avoir à chaque fois la meme taille et les même clés.
     * 
     * @param pName nom associé à la future courbe.
     * @param pValues SortedMap contenant en clé (trié!) des facteurs sous forme de String et en valeur des nombres
     *            (Double).
     * @param pRequest pour avoir le nom des facteurs internationalisés
     */
    public void addValues( String pName, SortedMap pValues, HttpServletRequest pRequest )
    {
        Set keys = pValues.keySet();

        // Ajoute les données
        Iterator it = keys.iterator();
        // Pour chaque axe, on crée l'axe avec son titre et on ajoute les valeurs
        while ( it.hasNext() )
        {
            // Internationalisation du nom
            // On a besoin d'un tokenizer pour ne pas prendre en compte la partie entre ()
            String key = (String) it.next();
            StringTokenizer st = new StringTokenizer( key, "(" );
            String axis = WebMessages.getString( pRequest, ( "factor.internationalise.name." + st.nextToken() ).trim() );
            // le facteur peut ne pas avoir de note, dans ce cas il n'y a plus de tokens
            // le premier token contient tout
            if ( st.hasMoreTokens() )
            {
                axis += "(" + st.nextToken();
            }
            mDataset.addValue( 1.0D, "1.0", axis );
            mDataset.addValue( 2.0D, "2.0", axis );
            final double thirdValue = 3.0D;
            mDataset.addValue( thirdValue, "3.0", axis );
            Number number = ( (Number) pValues.get( key ) );
            if ( number != null && number.doubleValue() >= 0.0 )
            {
                mDataset.addValue( number.doubleValue(), pName, axis );
            }
        }
    }

    /**
     * Create the JreeChart object. This method assume that we want display the legend
     * 
     * @return The JreeChart object
     */
    public JFreeChart getChart()
    {
        return getChart( true, true );
    }

    /**
     * Create the JreeChart object
     * 
     * @param showLegend indicate if it should display the legend or not
     * @param showBackground indicate if we want showBackground
     * @return The JreeChart object
     */
    public JFreeChart getChart( boolean showLegend, boolean showBackground )
    {
        JFreeChart retChart = super.getChart();

        // Creation of the graph if it not already exist
        if ( null == retChart )
        {

            // Creation of the plot
            SpiderWebPlot plot = new SpiderWebPlot( mDataset );

            // Creation of the picture. The plot is inside the picture
            retChart = new JFreeChart( mTitle, TextTitle.DEFAULT_FONT, plot, false );

            // Display of the legend
            if ( showLegend )
            {
                LegendTitle legendtitle = new LegendTitle( plot );
                legendtitle.setPosition( RectangleEdge.BOTTOM );
                retChart.addSubtitle( legendtitle );
            }

            // Definition of the style of the three first draw in the spiderWEbPlot.
            // This three first draw represent the scale for the mark 1.0, 2.0 and 3.0 in the plot
            // First we define the style
            final float miterLimit = 10.0f;
            final float[] dashPattern = { 5.0f, 3.0f };
            BasicStroke dash = new BasicStroke( 1.0f, BasicStroke.CAP_SQUARE, // End cap
                                                BasicStroke.JOIN_MITER, // Join style
                                                miterLimit, // Miter limit
                                                dashPattern, // Dash pattern
                                                0.0f );
            // We associate this style to the draw
            plot.setSeriesPaint( 0, new Color( WebMessages.getInt( "kiviat.color.1" ) ) );
            plot.setSeriesOutlineStroke( 0, dash );
            plot.setSeriesPaint( 1, new Color( WebMessages.getInt( "kiviat.color.2" ) ) );
            plot.setSeriesOutlineStroke( 1, dash );
            plot.setSeriesPaint( 2, new Color( WebMessages.getInt( "kiviat.color.3" ) ) );
            plot.setSeriesOutlineStroke( 2, dash );

            // Define the gap what is draw and the border of the plot
            plot.setInteriorGap( DEFAULT_GAP );

            // Define the style of the line stroke
            plot.setBaseSeriesOutlineStroke( new BasicStroke( 2.0f ) );

            // The max value put in the plot (for the scale)
            plot.setMaxValue( SCALE_MAX_VALUE );

            // Indicate if we want fill the inner of the draw
            plot.setWebFilled( FILL_RADAR );

            if ( !showBackground )
            {
                // Set the background of the picture to white
                retChart.setBackgroundPaint( Color.WHITE );

                // Set the border of the plot to white
                plot.setOutlinePaint( Color.WHITE );
            }
            super.setChart( retChart );
        }
        return retChart;
    }

}
