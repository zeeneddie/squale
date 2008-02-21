package com.airfrance.squaleweb.util.graph;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * Fabrique un graphe de secteur
 */
public class AreaMaker extends AbstractGraphMaker {
    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 480;
    /**
     * Largeur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 720;
    /**
     * Format des dates par défaut
     */
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    /**
     * dataset contenant les valeurs à mettre dans le diagramme
     */
    private TimeSeriesCollection mDataSet;
    
    /**
     * format de date à afficher sur le diagramme
     */
    private DateFormat mDateFormat;

    /**
     * Constructeur avec le titre du diagramme
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public AreaMaker(String pTitle) {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
    }

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pTimeAxisLabel titre de l'axe temporel (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     */
    public AreaMaker(String pTitle, String pTimeAxisLabel, String pValueAxisLabel) {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
        mXLabel = pTimeAxisLabel;
        mYLabel = pValueAxisLabel;
        mDateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    /**
     * Constructeur avec le titre du diagramme, les titres des axes et le format de la date
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pTimeAxisLabel titre de l'axe temporel (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     * @param pLocale la locale pour le format des dates
     */
    public AreaMaker(String pTitle, String pTimeAxisLabel, String pValueAxisLabel, Locale pLocale) {
        mDataSet = new TimeSeriesCollection();
        mTitle = pTitle;
        mXLabel = pTimeAxisLabel;
        mYLabel = pValueAxisLabel;
        mDateFormat = new SimpleDateFormat(DATE_FORMAT, pLocale);
    }

    /**
     * constructeur par défaut
     */
    private AreaMaker() {
        mDataSet = new TimeSeriesCollection();
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
        double acc = 0;
        while (it.hasNext()) {
            Date date = (Date) it.next();
            Day day = new Day(date);
            // On cumule les résultats
            acc += ((Number) pValues.get(date)).doubleValue();
            timeSeries.addOrUpdate(day, new Double(acc));
        }
        mDataSet.addSeries(timeSeries);
    }

    /**
     * @return le diagramme JFreeChart
     */
    protected JFreeChart getChart() {
        JFreeChart retChart = super.getChart();
        if (null == retChart) {
            retChart = ChartFactory.createXYAreaChart(mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.VERTICAL, false, false, false);
            final XYPlot plot = retChart.getXYPlot();

            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setNumberFormatOverride(new DecimalFormat("########0.0"));
            //rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
            
            // Format de l'axe des dates
            final ValueAxis domainAxis = new DateAxis(mXLabel);
            domainAxis.setLowerMargin(0.0);
            domainAxis.setUpperMargin(0.0);
            ((DateAxis)domainAxis).setDateFormatOverride(mDateFormat);
            plot.setDomainAxis(domainAxis);
            
            super.setChart(retChart);
        }
        return retChart;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultHeight()
     * 
     * @return la hauteur par défaut
     */
    protected int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultWidth()
     * 
     * @return la largeur par défaut
     */
    protected int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

}
