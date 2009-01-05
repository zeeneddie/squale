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
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squaleweb.resources.WebMessages;

/**
 */
public class RepartitionMaker
    extends AbstractRepartitionMaker
{

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 900;

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

    /**
     * clé pour le titre du diagramme par défaut
     */
    private static final String DEFAULT_TITLE_KEY = "repartitionhisto.default.title";

    /**
     * clé pour l'axe des abscisses du diagramme par défaut
     */
    private static final String DEFAULT_XAXIS_LABEL_KEY = "repartitionhisto.xaxis.default.label";

    /**
     * clé pour l'axe des abscisses du diagramme par défaut
     */
    private static final String DEFAULT_YAXIS_LABEL_KEY = "repartitionhisto.yaxis.default.label";

    /** le renderer du graph */
    private CategoryItemRenderer mRenderer;

    /**
     * Les valeurs
     */
    private DefaultCategoryDataset mDataSet;

    /**
     * Constructeur avec les paramètres servant pour le graph cliquable
     * 
     * @param pRequest la requete pour avoir des clés internationalisés
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     */
    public RepartitionMaker( HttpServletRequest pRequest, String pProjectId, String pCurrentAuditId,
                             String pPreviousAuditId, String pPracticeId, String pFactorParentId )
    {
        this( WebMessages.getString( pRequest, DEFAULT_TITLE_KEY ), WebMessages.getString( pRequest,
                                                                                           DEFAULT_XAXIS_LABEL_KEY ),
              WebMessages.getString( pRequest, DEFAULT_YAXIS_LABEL_KEY ), pProjectId, pCurrentAuditId,
              pPreviousAuditId, pPracticeId, pFactorParentId );
    }

    /**
     * Constructeur avec les paramètres servant pour le graph cliquable
     * 
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     * @param pTitle le titre
     * @param pXLabel le label des abscisses
     * @param pYLabel le label des ordonnées
     */
    public RepartitionMaker( String pTitle, String pXLabel, String pYLabel, String pProjectId, String pCurrentAuditId,
                             String pPreviousAuditId, String pPracticeId, String pFactorParentId )
    {
        mProjectId = pProjectId;
        mDataSet = new DefaultCategoryDataset();
        mCurrentAuditId = pCurrentAuditId;
        mPreviousAuditId = pPreviousAuditId;
        mPracticeId = pPracticeId;
        mFactorParentId = pFactorParentId;
        mTitle = pTitle;
        mXLabel = pXLabel;
        mYLabel = pYLabel;
    }

    /**
     * Constructeur avec les paramètres servant pour le graph cliquable
     * 
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     */
    public RepartitionMaker( String pProjectId, String pCurrentAuditId, String pPreviousAuditId, String pPracticeId,
                             String pFactorParentId )
    {
        this( "", "", "", pProjectId, pCurrentAuditId, pPreviousAuditId, pPracticeId, pFactorParentId );
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
                ChartFactory.createBarChart3D( mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.VERTICAL, false,
                                               true, true );
            CategoryPlot plot = retChart.getCategoryPlot();
            CategoryAxis xAxis = plot.getDomainAxis();
            xAxis.setAxisLineVisible( true );
            xAxis.setVisible( true );
            ValueAxis yAxis = plot.getRangeAxis();
            yAxis.setStandardTickUnits( NumberAxis.createIntegerTickUnits() );
            // On rajoute 5% pour avoir une meilleure lisibilité
            final double YAxisCoeff = 1.05;
            yAxis.setRange( PracticeResultBO.REFUSED_MIN, Math.max( mMaxValue * YAxisCoeff, 1 ) );

            // Positionne les couleurs et les liens
            mRenderer = (CategoryItemRenderer) plot.getRenderer();
            RepartitionUrlGenerator generator =
                new RepartitionUrlGenerator( mProjectId, mCurrentAuditId, mPreviousAuditId, mPracticeId,
                                             mFactorParentId, NB_SERIES_FOR_FLOAT_GRAPH );
            mRenderer.setItemURLGenerator( generator );
            BarRenderer barRenderer = (BarRenderer) mRenderer;
            barRenderer.setBaseSeriesVisible( true );
            RepartitionToolTipGenerator toolTipGenerator = new RepartitionToolTipGenerator();
            barRenderer.setToolTipGenerator( toolTipGenerator );
            manageColor( NB_SERIES_FOR_FLOAT_GRAPH );
            retChart.setBackgroundPaint( Color.WHITE );
            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * @param pValues les valeurs
     */
    public void build( double[] pValues )
    {
        // on ne veut pas de la derniere valeur, car ce sont les éléments non notés
        // on s'arrete sur l'avant avant derniere car pour la derniere colonne
        // du graph on fait un cumul des 2 dernieres notes
        mMaxValue = pValues[0];
        int value = (int) pValues[0];
        double min = 0.0;
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits( 1 );
        final double pas = 0.1;
        for ( int i = 0; i < NB_SERIES_FOR_FLOAT_GRAPH; i++ )
        {
            if ( i == ( NB_SERIES_FOR_FLOAT_GRAPH - 1 ) )
            {
                value = (int) ( pValues[NB_SERIES_FOR_FLOAT_GRAPH - 1] + pValues[NB_SERIES_FOR_FLOAT_GRAPH] );
            }
            else
            {
                value = (int) pValues[i];
            } // positionne le max
            if ( mMaxValue < value )
            {
                mMaxValue = value;
            }
            if ( value == 0 )
            {
                mDataSet.addValue( null, new String( format.format( min ) ), "" );
            }
            else
            {
                mDataSet.addValue( value, new String( format.format( min ) ), "" );
            }
            min += pas;
        }
    }

    /**
     * @see com.airfrance.squaleweb.util.graph.AbstractRepartitionMaker#applyColor(int, java.awt.Color) {@inheritDoc}
     */
    protected void applyColor( int pIndex, Color pColor )
    {
        mRenderer.setSeriesPaint( pIndex, pColor );

    }
}
