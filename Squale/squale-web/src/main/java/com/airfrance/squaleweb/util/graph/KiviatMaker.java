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
 * @author M400843
 *
 * @see AbstractGraphMaker
 * {@inheritDoc}
 */
public class KiviatMaker extends AbstractGraphMaker {

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
     * Constructeur par défaur
     */
    public KiviatMaker() {
    }

    /**
     * Constructeur avec le titre du diagramme
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public KiviatMaker(String pTitle) {
        mTitle = pTitle;
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
     * Ajoute les d'un composant. <br />
     * <b>Attention : </b> pour assurer la cohérences des données, si cette méthode est appellée plusieurs fois, 
     * pValues doit avoir à chaque fois la meme taille et les même clés.
     * @param pName nom associé à la future courbe.
     * @param pValues SortedMap contenant en clé (trié!) des facteurs sous forme de String et en valeur des nombres (Double).
     * @param pRequest pour avoir le nom des facteurs internationalisés
     */
    public void addValues(String pName, SortedMap pValues, HttpServletRequest pRequest) {
        Set keys = pValues.keySet();

        // Ajoute les données
        Iterator it = keys.iterator();
        // Pour chaque axe, on crée l'axe avec son titre et on ajoute les valeurs
        while (it.hasNext()) {
            // Internationalisation du nom
            // On a besoin d'un tokenizer pour ne pas prendre en compte la partie entre ()
            String key = (String) it.next();
            StringTokenizer st = new StringTokenizer(key, "(");
            String axis = WebMessages.getString(pRequest, ("factor.internationalise.name." + st.nextToken()).trim());
            // le facteur peut ne pas avoir de note, dans ce cas il n'y a plus de tokens
            // le premier token contient tout
            if (st.hasMoreTokens()) {
                axis += "(" + st.nextToken();
            }
            mDataset.addValue(1.0D, "1.0", axis);
            mDataset.addValue(2.0D, "2.0", axis);
            final double thirdValue =  3.0D;
            mDataset.addValue(thirdValue, "3.0", axis);
            Number number = ((Number) pValues.get(key));
            if (number != null && number.doubleValue() >= 0.0) {
                mDataset.addValue(number.doubleValue(), pName, axis);
            }
        }
    }

    /**
     * @return le diagramme JFreeChart
     */
    public JFreeChart getChart() {
        JFreeChart retChart = super.getChart();
        // si le graph n'est pas encore construit, il faut le construire
        if (null == retChart) {

            SpiderWebPlot plot = new SpiderWebPlot(mDataset);

            retChart = new JFreeChart(mTitle, TextTitle.DEFAULT_FONT, plot, false);
            LegendTitle legendtitle = new LegendTitle(plot);
            legendtitle.setPosition(RectangleEdge.BOTTOM);
            retChart.addSubtitle(legendtitle);

            plot.setBaseSeriesOutlineStroke(new BasicStroke(2.0f));

            // Ajoute les couleurs des 3 notes 1,2,3 (manque l'echelle !)
            final float miterLimit = 10.0f;
            final float[] dashPattern = { 5.0f, 3.0f };
                BasicStroke dash = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, // End cap
        BasicStroke.JOIN_MITER, // Join style
        miterLimit, // Miter limit
        dashPattern, // Dash pattern
    0.0f);

            plot.setSeriesPaint(0, new Color(WebMessages.getInt("kiviat.color.1")));
            plot.setSeriesOutlineStroke(0, dash);
            plot.setSeriesPaint(1, new Color(WebMessages.getInt("kiviat.color.2")));
            plot.setSeriesOutlineStroke(1, dash);
            plot.setSeriesPaint(2, new Color(WebMessages.getInt("kiviat.color.3")));
            plot.setSeriesOutlineStroke(2, dash);

            plot.setMaxValue(SCALE_MAX_VALUE);
            plot.setWebFilled(FILL_RADAR);

            super.setChart(retChart);
        }
        return retChart;
    }

}
