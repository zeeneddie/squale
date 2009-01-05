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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 */
public class AuditsSizeMaker
    extends AbstractStatsMaker
{

    /**
     * Constructeur par défaut
     */
    public AuditsSizeMaker()
    {
        mDataSet = new DefaultCategoryDataset();
    }

    /**
     * clé pour le titre du diagramme par défaut
     */
    private static final String DEFAULT_TITLE_KEY = "stats.size.title";

    /**
     * clé pour l'axe des abscisses du diagramme par défaut
     */
    private static final String DEFAULT_XAXIS_LABEL_KEY = "stats.size.xlabel";

    /**
     * clé pour l'axe des ordonnées du diagramme par défaut
     */
    private static final String DEFAULT_YAXIS_LABEL_KEY = "stats.size.ylabel";

    /**
     * Constructeur avec les paramètres servant pour le graph cliquable
     * 
     * @param pRequest la requete pour avoir des clés internationalisés
     */
    public AuditsSizeMaker( HttpServletRequest pRequest )
    {
        this( WebMessages.getString( pRequest, DEFAULT_TITLE_KEY ), WebMessages.getString( pRequest,
                                                                                           DEFAULT_XAXIS_LABEL_KEY ),
              WebMessages.getString( pRequest, DEFAULT_YAXIS_LABEL_KEY ) );
    }

    /**
     * Constructeur avec les différents labels
     * 
     * @param pYAxisLabel le label sur l'axe des Y
     * @param pTitle le titre
     * @param pXAxisLabel le label sur l'axe des X
     */
    public AuditsSizeMaker( String pTitle, String pXAxisLabel, String pYAxisLabel )
    {
        this();
        mYLabel = pYAxisLabel;
        mXLabel = pXAxisLabel;
        mTitle = pTitle;
    }

    /**
     * @return le diagramme JFreeChart
     */
    public JFreeChart getChart()
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            retChart =
                ChartFactory.createBarChart( mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.VERTICAL, true, false,
                                             false );
            final CategoryPlot plot = retChart.getCategoryPlot();
            // Formate l'axe des X
            final CategoryAxis axis = (CategoryAxis) plot.getDomainAxis();
            // définit des unités entières pour l'axe de gauche
            plot.getRangeAxis().setStandardTickUnits( NumberAxis.createIntegerTickUnits() );
            // gere les couleurs du graph
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            // la panel au dimension par défaut
            final ChartPanel chartPanel = new ChartPanel( retChart );
            chartPanel.setPreferredSize( new java.awt.Dimension( getDefaultHeight(), getDefaultWidth() ) );
            retChart.setBackgroundPaint( Color.WHITE );
            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * Sert à savoir combien on a de series, afin de les mettre toutes à la meme couleur pour éviter des graphiques avec
     * des couleurs excentriques
     */
    private int nbSeries = 0;

    /**
     * Ajoute les valeurs d'une courbe
     * 
     * @param pValues liste des tableaux des données nécessaires pour la construction du graphe Les tableaux sont de la
     *            forme : {date de l'audit, valeur max du file system, nom de l'application}
     */
    public void addCurve( List pValues )
    {
        List list = getSortedList( pValues );
        for ( int i = 0; i < list.size(); i++ )
        {
            Object[] combo = (Object[]) list.get( i );
            String date = (String) combo[0];
            Number size = (Number) combo[1];
            Integer counterObject = new Integer( i );
            ( (DefaultCategoryDataset) mDataSet ).addValue( size, (String) combo[2], date );
        }
        // Met à jour la valeur du nombre de séries
        nbSeries = list.size();
    }

    /**
     * Effectue les pré-traitements nécessaires à l'affichage 1)efface les doublons en ne récupérant que le maximum 2)
     * tri par ordre chronologique
     * 
     * @param pValues le tableau des valeurs
     * @return la list trié sans doublons avec la valeur maximum (sous forme d'un tableau d'objets à 2 cases, la
     *         première la date (String) la deuxième la taille max du FS sur les audits de la journée).
     */
    private List getSortedList( List pValues )
    {
        List result = new ArrayList( 0 );
        for ( int i = 0; i < pValues.size(); i++ )
        {
            Object[] combo = (Object[]) pValues.get( i );
            String date = (String) combo[0];
            Number size = (Number) combo[1];
            int j;
            for ( j = 0; j < result.size(); j++ )
            {
                // on est sur la meme journée, on prend le maximum
                if ( ( (String) ( (Object[]) result.get( j ) )[0] ).equals( date ) )
                {
                    Long currentStoredSize = (Long) ( (Object[]) result.get( j ) )[1];
                    // Si la valeur actuellement stockée est null, on insère de toute facon la nouvelle
                    // Si la nouvelle est null, ça ne change rien sinon on insère une bonne valeur
                    if ( currentStoredSize == null )
                    {
                        ( (Object[]) result.get( j ) )[1] = size;
                    }
                    else
                    { // La valeur actuelle n'est pas null, on doit gérer l'ajout éventuel
                        if ( size != null )
                        { // 2 valeurs, on prend le maximum
                            ( (Object[]) result.get( j ) )[1] =
                                new Long( Math.max( currentStoredSize.longValue(), size.longValue() ) );
                        } // sinon on garde la valeur actuellement stockée
                    }
                    // coupe la boucle
                    j = result.size() + 1;
                }
            }
            // on ne l'a pas trouvée, nouvelle valeur on insère le tableau tel quel
            if ( j == result.size() )
            {
                result.add( combo );
            }
        }
        return result;
    }

}
