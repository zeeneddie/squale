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

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 */
public class RepartitionBarMaker
    extends AbstractRepartitionMaker
{

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 100;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 800;

    /**
     * clé pour le titre du diagramme par défaut
     */
    private static final String DEFAULT_TITLE_KEY = "repartitionbar.default.title";

    /**
     * clé pour l'axe des abscisses du diagramme par défaut
     */
    private static final String DEFAULT_XAXIS_LABEL_KEY = "repartitionbar.xaxis.default.label";

    // Rien sur l'axe des ordonnées

    /**
     * Les valeurs
     */
    private CategoryDataset mDataSet;

    /**
     * Constructeur avec les paramètres servant pour le graph cliquable
     * 
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     */
    public RepartitionBarMaker( String pProjectId, String pCurrentAuditId, String pPreviousAuditId, String pPracticeId,
                                String pFactorParentId )
    {
        this( "", "", "", pProjectId, pCurrentAuditId, pPreviousAuditId, pPracticeId, pFactorParentId );
    }

    /**
     * Constructeur avec les paramètres servant pour le graph cliquable
     * 
     * @param pRequest la requete pour avoir des clés internationalisés
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     */
    public RepartitionBarMaker( HttpServletRequest pRequest, String pProjectId, String pCurrentAuditId,
                                String pPreviousAuditId, String pPracticeId, String pFactorParentId )
    {
        this( WebMessages.getString( pRequest, DEFAULT_TITLE_KEY ), WebMessages.getString( pRequest,
                                                                                           DEFAULT_XAXIS_LABEL_KEY ),
              "", pProjectId, pCurrentAuditId, pPreviousAuditId, pPracticeId, pFactorParentId );
    }

    /**
     * Constructeur
     * 
     * @param pTitle le titre
     * @param pXLabel le label des abscisses
     * @param pYLabel le label des ordonnées
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     */
    public RepartitionBarMaker( String pTitle, String pXLabel, String pYLabel, String pProjectId,
                                String pCurrentAuditId, String pPreviousAuditId, String pPracticeId,
                                String pFactorParentId )
    {
        mTitle = pTitle;
        mXLabel = pXLabel;
        mYLabel = pYLabel;
        mDataSet = new DefaultCategoryDataset();
        mProjectId = pProjectId;
        mCurrentAuditId = pCurrentAuditId;
        mPreviousAuditId = pPreviousAuditId;
        mPracticeId = pPracticeId;
        mFactorParentId = pFactorParentId;
    }

    /** le renderer du graph */
    private StackedBarRenderer mRenderer;

    /**
     * @return le diagramme JFreeChart
     */
    public JFreeChart getChart()
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            retChart =
                ChartFactory.createStackedBarChart( mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.HORIZONTAL,
                                                    true, false, true );
            CategoryPlot plot = retChart.getCategoryPlot();
            ValueAxis xAxis = plot.getRangeAxis();
            final double LOWER_BOUND = 0;
            final double UPPER_BOUND = 100;
            xAxis.setRange( LOWER_BOUND, UPPER_BOUND );
            plot.setRangeAxisLocation( AxisLocation.BOTTOM_OR_LEFT );
            mRenderer = (StackedBarRenderer) plot.getRenderer();
            // Positionne les couleurs el les liens
            RepartitionBarUrlGenerator generator =
                new RepartitionBarUrlGenerator( mProjectId, mCurrentAuditId, mPreviousAuditId, mPracticeId,
                                                mFactorParentId, NB_SERIES_FOR_INT_GRAPH );
            mRenderer.setItemURLGenerator( generator );

            manageColor( NB_SERIES_FOR_INT_GRAPH );
            retChart.setBackgroundPaint( Color.WHITE );
        }
        return retChart;
    }

    /**
     * Affecte les valeurs du camembert (les anciennes valeurs sont effacées.
     * 
     * @param pValues Map contenant en clé les titres (String) des part du camembert et en valeurs les valeurs (Number)
     *            correspondantes
     */
    public void setValues( double[] pValues )
    {
        // Pour convertir en pourcentage
        int total = 0;
        for ( int i = 0; i <= NB_SERIES_FOR_INT_GRAPH; i++ )
        {
            total += pValues[i];
        }
        for ( int i = 0; i < NB_SERIES_FOR_INT_GRAPH; i++ )
        {
            // Cumul des 2 dernieres valeurs
            if ( i == NB_SERIES_FOR_INT_GRAPH - 1 )
            {
                ( (DefaultCategoryDataset) mDataSet ).addValue( ( pValues[i] + pValues[i + 1] ) * 100 / total, "[" + i
                    + "," + ( i + 1 ) + "]", "" );
            }
            else
            {
                ( (DefaultCategoryDataset) mDataSet ).addValue( pValues[i] * 100 / total, "[" + i + "," + ( i + 1 )
                    + "[", "" );
            }
        }
    }

    /**
     * @see com.airfrance.squaleweb.util.graph.AbstractGraphMaker#getDefaultHeight() {@inheritDoc}
     */
    protected int getDefaultHeight()
    {
        return DEFAULT_HEIGHT;
    }

    /**
     * @see com.airfrance.squaleweb.util.graph.AbstractGraphMaker#getDefaultWidth() {@inheritDoc}
     */
    protected int getDefaultWidth()
    {
        return DEFAULT_WIDTH;
    }

    /**
     * @see com.airfrance.squaleweb.util.graph.AbstractRepartitionMaker#applyColor(int, java.awt.Color) {@inheritDoc}
     */
    protected void applyColor( int pIndex, Color pColor )
    {
        mRenderer.setSeriesPaint( pIndex, pColor );
    }

}
