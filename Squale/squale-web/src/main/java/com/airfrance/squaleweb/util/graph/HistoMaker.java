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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 * Help class for create historic graph
 */
public class HistoMaker
    extends AbstractGraphMaker
{
    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 500;

    /**
     * dataset contenant les valeurs à mettre dans le diagramme
     */
    private TimeSeriesCollection mDataSet;

    /**
     * format de date à afficher sur le diagramme
     */
    private String mDateFormat = WebMessages.getString( "histo.default.dateformat" );

    /**
     * constructeur par défaut
     */
    public HistoMaker()
    {
        mDataSet = new TimeSeriesCollection();
    }

    /**
     * Constructeur avec le titre du diagramme
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public HistoMaker( String pTitle )
    {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
    }

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pTimeAxisLabel titre de l'axe temporel (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     */
    public HistoMaker( String pTitle, String pTimeAxisLabel, String pValueAxisLabel )
    {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
        mXLabel = pTimeAxisLabel;
        mYLabel = pValueAxisLabel;
    }

    /**
     * Ajoute les valeurs d'une courbe
     * 
     * @param pName nom associé à la future courbe
     * @param pValues Map contenant en clé des date (java.util.Date) et en valeurs des nombres (Number)
     */
    public void addCurve( String pName, Map pValues )
    {
        TimeSeries timeSeries = new TimeSeries( pName );

        Set keys = pValues.keySet();
        Iterator it = keys.iterator();
        while ( it.hasNext() )
        {
            Date date = (Date) it.next();
            Day day = new Day( date );
            timeSeries.addOrUpdate( day, (Number) pValues.get( date ) );
        }

        mDataSet.addSeries( timeSeries );
    }

    /**
     * This method create the JFreechart chart. By default The date axis is limited by the series of information insert
     * 
     * @return A JFreeChart graph
     */
    public JFreeChart getChart()
    {
        return getChart( false );
    }

    /**
     * This method create the JFreechart chart
     * 
     * @param maxAxisToday Does the max for the date axis should be set to today ?
     * @return A JFreeChart chart
     */
    public JFreeChart getChart( boolean maxAxisToday )
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            retChart =
                ChartFactory.createTimeSeriesChart( mTitle, mXLabel, mYLabel, mDataSet, mShowLegend, false, false );
            XYPlot plot = retChart.getXYPlot();
            XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
            xylineandshaperenderer.setBaseShapesVisible( true );
            plot.setRenderer( xylineandshaperenderer );
            SimpleDateFormat sdf = new SimpleDateFormat( mDateFormat );
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            if ( maxAxisToday )
            {
                axis.setMaximumDate( new Date() );
            }
            axis.setDateFormatOverride( sdf );
            ValueAxis yAxis = plot.getRangeAxis();
            yAxis.setStandardTickUnits( NumberAxis.createIntegerTickUnits() );
            yAxis.setAutoRangeMinimumSize( 2.0 );
            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultHeight() {@inheritDoc}
     */
    protected int getDefaultHeight()
    {
        return DEFAULT_HEIGHT;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultWidth() {@inheritDoc}
     */
    protected int getDefaultWidth()
    {
        return DEFAULT_WIDTH;
    }
}
