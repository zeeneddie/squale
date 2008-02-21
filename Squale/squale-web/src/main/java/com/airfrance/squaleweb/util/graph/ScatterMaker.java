/*
 * Créé le 17 août 05
 */

package com.airfrance.squaleweb.util.graph;

import java.awt.Rectangle;
import java.awt.Shape;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 * @author M400843
 * Cette classe est responsable de la fabrication d'un scatter. 
 * 
 * @see AbstractGraphMaker
 */
public class ScatterMaker extends AbstractGraphMaker {
    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 400;
    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 400;
    /**
     * Taille d'un point (carré) du graphique
     */
    private static final int DEFAULT_POINT_SIZE = 3;
    /**
     * Position par défaut de l'axe horizontal
     */
    private static final int DEFAULT_HORIZONTAL_AXIS_POS = 7;
    /**
     * Position par défaut de l'axe vertical
     */
    private static final int DEFAULT_VERTICAL_AXIS_POS = 10;
    /**
     * Marge par défaut avec les axes. 
     */
    private static final double DEFAULT_AXIS_MARGIN = 2;

    /**
     * Contient <code>true</code> si des données peuvent être ajoutées
     */
    private boolean mCouldAddDatas = true;

    /**
     * Dataset contenant les valeurs à mettre dans le diagramme
     */
    private XYSeriesCollection mDataSet;

    /**
     * Logger.
     */
    private final static Log LOG = LogFactory.getLog(ScatterMaker.class);

    /**
     * Constructeur par défaut
     */
    public ScatterMaker() {
        mDataSet = new XYSeriesCollection();
        // Etiquete figurant sur l'axe horizontal (peut etre null) Par défaut : "v(g)"
        mXLabel = WebMessages.getString("scatter.default.axis.domain");
        // Etiquete figurant sur l'axe des valeurs (peut etre null) Par défaut : "ev(g)"
        mYLabel = WebMessages.getString("scatter.default.axis.value");

    }

    /**
     * Constructeur avec le titre du diagramme
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public ScatterMaker(String pTitle) {
        this();
        mTitle = pTitle;
    }

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pDomainAxisLabel titre de l'axe horizontal (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     */
    public ScatterMaker(String pTitle, String pDomainAxisLabel, String pValueAxisLabel) {
        mDataSet = new XYSeriesCollection();
        mTitle = pTitle;
        mXLabel = pDomainAxisLabel;
        mYLabel = pValueAxisLabel;
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

    /**
     * Ajoute une series de valeurs (x, y) au ScatterPlot. <br />
     * <b>Attention : </b>les doublons doivent être évités pour des raisons de performances
     * On doit toujours avoir l'égalité suivante : pHorizontalValues.length == pVerticalValues.length
     * @param pName le nom de la série (peut etre <code>null</code> si c'est la première série, dans ce cas, 
     * seule cette série sera ajoutéee)
     * @param pHorizontalValues les valeurs de l'axe horizontal
     * @param pVerticalValues les valeurs de l'axe vertical
     * @return <code>true</code> si tout s'est bien passé, <code>false</code> sinon
     */
    public boolean addSerie(String pName, Number[] pHorizontalValues, Number[] pVerticalValues) {
        boolean ret = false;
        // si les nombres de valeurs horizontales et verticales sont égaux
        // et que l'on peut encore ajouter des valeurs
        if ((pHorizontalValues.length == pVerticalValues.length) && (mCouldAddDatas)) {
            ret = true; // à partir d'ici on considère que tout va bien se passer

            if (null == pName) {
                // si aucun nom n'a été passé en paramètre, on ne peut mettre qu'une et une seule courbe
                if (mDataSet.getSeries().size() > 0) {
                    // si des courbes sont déjà dans le dataset, on arrete tout, 
                    // on ne peut pas ajouter une courbe sans nom
                    ret = false;
                } else {
                    mCouldAddDatas = false;
                    mShowLegend = false;
                }
            }

            if (true == ret) {
                // si tout s'est bien passé, on ajoute les valeurs 
                addXYSeries(pName, pHorizontalValues, pVerticalValues);
            }
        }

        return ret;
    }

    /**
     * Crée une XYSeries et l'ajoute.
     * @param pName le nom de la série (peut etre <code>null</code> si c'est la première série, dans ce cas, 
     * seule cette série sera ajoutéee).
     * @param pHorizontalValues les valeurs de l'axe horizontal.
     * @param pVerticalValues les valeurs de l'axe vertical.
     */
    private void addXYSeries(String pName, Number[] pHorizontalValues, Number[] pVerticalValues) {
        String name = pName;
        if (null == name) {
            // si aucun nom n'a été spécifié on affecte le nom par défaut pour la courbe
            name = WebMessages.getString("scatter.undefined.name");
        }
        XYSeries series = new XYSeries(name);
        // pour toutes les abscisses/ordonnées
        for (int i = 0; i < pVerticalValues.length; i++) {
            if ((null != pHorizontalValues[i]) && (null != pVerticalValues[i])) {
                // si on a bien une valeur en abscisse et une en ordonnée, on l'ajoute à la courbe
                series.add(pHorizontalValues[i], pVerticalValues[i]);
            }
        }
        mDataSet.addSeries(series);

        String[] tab = { Integer.toString(pVerticalValues.length)};
        LOG.debug(WebMessages.getString("scatter.debug.addseries", tab));
    }

    /**
     * Construit (ou reconstruit) le diagramme puis le retourne
     * @return le diagramme JFreeChart.
     */
    protected JFreeChart getChart() {
        LOG.debug(WebMessages.getString("method.entry"));
        JFreeChart retChart = super.getChart();
        if (null == retChart) {
            // Génération du Scatterplot
            retChart = ChartFactory.createScatterPlot(mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.VERTICAL, mShowLegend, false, false);
            ValueAxis domainAxis = retChart.getXYPlot().getDomainAxis();
            ValueAxis rangeAxis = retChart.getXYPlot().getRangeAxis();

            // Détermination des bornes en abscisse et en ordonnée
            double maxDomain = Math.max(domainAxis.getUpperBound(), DEFAULT_VERTICAL_AXIS_POS + DEFAULT_AXIS_MARGIN);
            double minDomain = Math.min(domainAxis.getLowerBound(), DEFAULT_VERTICAL_AXIS_POS - DEFAULT_AXIS_MARGIN);
            double maxRange = Math.max(rangeAxis.getUpperBound(), DEFAULT_HORIZONTAL_AXIS_POS + DEFAULT_AXIS_MARGIN);
            double minRange = Math.min(rangeAxis.getLowerBound(), DEFAULT_HORIZONTAL_AXIS_POS - DEFAULT_AXIS_MARGIN);

            // Mise à l'échelle logarithmique des axes
            LogarithmicAxis newDomainAxis = new LogarithmicAxis(domainAxis.getLabel());
            LogarithmicAxis newRangeAxis = new LogarithmicAxis(rangeAxis.getLabel());

            // Affectation des bornes en abscisse et en ordonnée            
            newDomainAxis.setLowerBound(minDomain);
            newDomainAxis.setUpperBound(maxDomain);
            newRangeAxis.setLowerBound(minRange);
            newRangeAxis.setUpperBound(maxRange);
            retChart.getXYPlot().setDomainAxis(newDomainAxis);
            retChart.getXYPlot().setRangeAxis(newRangeAxis);

            // Le point a une taille fixe
            Shape shape = new Rectangle(DEFAULT_POINT_SIZE, DEFAULT_POINT_SIZE);
            retChart.getXYPlot().getRenderer().setShape(shape);

            // Annotations
            XYLineAnnotation horizontalAxis = new XYLineAnnotation(minDomain, DEFAULT_HORIZONTAL_AXIS_POS, maxDomain, DEFAULT_HORIZONTAL_AXIS_POS);
            XYLineAnnotation verticalAxis = new XYLineAnnotation(DEFAULT_VERTICAL_AXIS_POS, minRange, DEFAULT_VERTICAL_AXIS_POS, maxRange);
            retChart.getXYPlot().addAnnotation(horizontalAxis);
            retChart.getXYPlot().addAnnotation(verticalAxis);

            super.setChart(retChart);
        }
        LOG.debug(WebMessages.getString("method.exit"));
        return retChart;
    }
}
