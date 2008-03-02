/*
 * Créé le 16 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

package com.airfrance.squaleweb.util.graph;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * @author M400843
 */
public class PieChartMaker
    extends AbstractGraphMaker
{
    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 400;

    /**
     * dataset contenant les valeurs à mettre dans le diagramme
     */
    private DefaultPieDataset mDataSet;

    /**
     * constructeur par défaut
     */
    private PieChartMaker()
    {
        mDataSet = new DefaultPieDataset();
    }

    /**
     * Constructeur avec le titre du diagramme
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pCurrentAuditId l'id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     */
    public PieChartMaker( String pTitle, String pCurrentAuditId, String pPreviousAuditId )
    {
        mDataSet = new DefaultPieDataset();
        mTitle = pTitle;
        mCurrentAuditId = pCurrentAuditId;
        mPreviousAuditId = pPreviousAuditId;
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

    /**
     * Ajoute une valeur au camembert
     * 
     * @param pTitle le titre correspondant à la part.
     * @param pValue la valeur
     */
    public void addValue( String pTitle, double pValue )
    {
        Number number = new Double( pValue );
        addValue( pTitle, number );
    }

    /**
     * Ajoute une valeur au camembert
     * 
     * @param pTitle le titre correspondant à la part.
     * @param pValue la valeur
     */
    public void addValue( String pTitle, Number pValue )
    {
        mDataSet.setValue( pTitle, pValue );
    }

    /**
     * Affecte les valeurs du camembert (les anciennes valeurs sont effacées.
     * 
     * @param pValues Map contenant en clé les titres (String) des part du camembert et en valeurs les valeurs (Number)
     *            correspondantes
     */
    public void setValues( Map pValues )
    {
        mDataSet = new DefaultPieDataset();
        Iterator it = pValues.keySet().iterator();
        while ( it.hasNext() )
        {
            String title = (String) it.next();
            addValue( title, (Long) pValues.get( title ) );
        }
    }

    /**
     * @param pRequest la requête
     * @return le diagramme JFreeChart
     */
    protected JFreeChart getChart( HttpServletRequest pRequest )
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            retChart = getCommonChart( pRequest );
            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * Restitution du diagramme de type Pie Chart
     * 
     * @param pUrls les sections du graphe (Id et libellé projet)
     * @param pRequest la requête
     * @return le diagramme JFreeChart
     */
    public JFreeChart getChart( Map pUrls, HttpServletRequest pRequest )
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            retChart = getCommonChart( pRequest );
            PieChartUrlGenerator generator = new PieChartUrlGenerator( pUrls, mCurrentAuditId, mPreviousAuditId );
            ( (PiePlot3D) retChart.getPlot() ).setURLGenerator( generator );
            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * Factorisation du code commun à la génération du graphe
     * 
     * @param pRequest la requête
     * @return graphe de type Bubble
     */
    private JFreeChart getCommonChart( HttpServletRequest pRequest )
    {
        JFreeChart chart = ChartFactory.createPieChart3D( mTitle, mDataSet, false, false, false );
        PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
        final double startAngle = 290D;
        pieplot3d.setStartAngle( startAngle );
        final float depthFactor = 0.1f;
        pieplot3d.setDepthFactor( depthFactor );
        pieplot3d.setDirection( Rotation.CLOCKWISE );
        final float foregroundAlpha = 0.5f;
        pieplot3d.setForegroundAlpha( foregroundAlpha );
        // Si on est en anglais, on laisse les nombre formatées avec des "," pour séparer les milliers
        // Si on est en francais on remplace les "," par des points
        NumberFormat numberFormat = SqualeWebActionUtils.getNumberFormat( pRequest );
        // Pour le formatter de %, on laisse celui par défaut
        pieplot3d.setLabelGenerator( new StandardPieSectionLabelGenerator( "{0} = {1} loc ({2})", numberFormat,
                                                                           NumberFormat.getPercentInstance() ) );
        return chart;
    }
}
