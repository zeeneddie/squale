package com.airfrance.squaleweb.util.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Fabrique pour un histogramme
 */
public class BarMaker extends AbstractGraphMaker {

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 400;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 400;

    /**
     * dataset contenant les valeurs à mettre dans le diagramme
     */
    private DefaultCategoryDataset mDataSet;

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

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * @param pTitle titre du diagramme
     * @param pXLabel titre de l'axe des abscisses
     * @param pYLabel titre de l'axe des ordonnées
     */
    public BarMaker(String pTitle, String pXLabel, String pYLabel) {
        mDataSet = new DefaultCategoryDataset();
        mTitle = pTitle;
        mXLabel = pXLabel;
        mYLabel = pYLabel;
    }

    /**
     * Ajoute une valeur à l'histogramme 
     * @param pValue la valeur
     * @param pTitle1 le titre correspondant à l'abscisse.
     * @param pTitle2 le titre correspondant à l'ordonnée.
     */
    public void addValue(Number pValue, String pTitle1, String pTitle2) {
        mDataSet.setValue(pValue, pTitle1, pTitle2);

    }

    /**
     * @return le diagramme JFreeChart
     */
    protected JFreeChart getChart() {
        JFreeChart retChart = super.getChart();
        if (null == retChart) {
            retChart = ChartFactory.createBarChart3D(mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.VERTICAL, false, false, false);
            
            CategoryPlot plot = retChart.getCategoryPlot();
            
            // Les valeurs sont des entiers
            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            
            // On effectue une rotation de 90° pour afficher le titre des abscisses à la verticale
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
            super.setChart(retChart);
        }
        return retChart;
    }

}
