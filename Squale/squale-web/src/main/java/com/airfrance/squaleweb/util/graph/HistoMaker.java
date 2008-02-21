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
 * @author M400843
 */
public class HistoMaker extends AbstractGraphMaker {
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
    private String mDateFormat = WebMessages.getString("histo.default.dateformat");

    /**
     * constructeur par défaut
     */
    public HistoMaker() {
        mDataSet = new TimeSeriesCollection();
    }

    /**
     * Constructeur avec le titre du diagramme
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public HistoMaker(String pTitle) {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
    }

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pTimeAxisLabel titre de l'axe temporel (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     */
    public HistoMaker(String pTitle, String pTimeAxisLabel, String pValueAxisLabel) {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
        mXLabel = pTimeAxisLabel;
        mYLabel = pValueAxisLabel;
    }

    /**
     * Ajoute les valeurs d'une courbe
     * @param pName nom associé à la future courbe
     * @param pValues Map contenant en clé des date (java.util.Date) et en valeurs des nombres (Number)
     */
    public void addCurve(String pName, Map pValues) {
        TimeSeries timeSeries = new TimeSeries(pName);

        Set keys = pValues.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            Date date = (Date) it.next();
            Day day = new Day(date);
            timeSeries.addOrUpdate(day, (Number) pValues.get(date));
        }

        mDataSet.addSeries(timeSeries);
    }

    /**
     * @return le diagramme JFreeChart
     */
    public JFreeChart getChart() {
        JFreeChart retChart = super.getChart();
        if (null == retChart) {
            retChart = ChartFactory.createTimeSeriesChart(mTitle, mXLabel, mYLabel, mDataSet, mShowLegend, false, false);
            XYPlot plot = retChart.getXYPlot();
            XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
            xylineandshaperenderer.setBaseShapesVisible(true);
            plot.setRenderer(xylineandshaperenderer);
            SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat);
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setDateFormatOverride(sdf);
            ValueAxis yAxis = plot.getRangeAxis();
            yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            yAxis.setAutoRangeMinimumSize(2.0);
            super.setChart(retChart);
        }
        return retChart;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultHeight()
     * {@inheritDoc}
     */
    protected int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultWidth()
     * {@inheritDoc}
     */
    protected int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }
}
