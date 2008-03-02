package com.airfrance.squaleweb.util.graph;

import java.text.NumberFormat;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.ui.RectangleEdge;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 * @author 6370258 Génération du graphe de type Bubble, stocké dans un form et appelé depuis une page Jsp
 */
public class BubbleMaker
    extends AbstractGraphMaker
{

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_HEIGHT = 500;

    /**
     * Hauteur du diagramme par défaut
     */
    public static final int DEFAULT_WIDTH = 550;

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
     * Permet de paramétrer la valeur de l'axe horizontal
     */
    private int mHorizontalAxisPos;

    /**
     * Permet de paramétrer la valeur de l'axe horizontal
     */
    private int mVerticalAxisPos;

    /**
     * Contient <code>true</code> si des données peuvent être ajoutées
     */
    private boolean mCouldAddDatas = true;

    /**
     * Borne inférieure en deçà de laquelle la taille du point est à la taille minimale
     */
    private static final int DEFAULT_LOWER_LIMIT = 1;

    /**
     * Borne supérieure au delà de laquelle la taille du point est à la taille maximale
     */
    private static final int DEFAULT_UPPER_LIMIT = 10;

    /**
     * Taille minimale du point
     */
    private static final double DEFAULT_LOWER_SIZE = 0.05;

    /**
     * Taille maximale du point
     */
    private static final double DEFAULT_UPPER_SIZE = 0.3;

    /**
     * Poucentage des méthodes difficilement maintenables mais structurées
     */
    private double totalTopLeft;

    /**
     * Pourcentage des méthodes difficilement maintenables et mal structurées
     */
    private double totalTopRight;

    /**
     * Pourcentage des méthodes maintenables et bien structurées
     */
    private double totalBottomLeft;

    /**
     * Pourcentage des méthodes maintenables mais mal structurées
     */
    private double totalBottomRight;

    /**
     * La locale
     */
    private Locale locale;

    /**
     * Dataset contenant les valeurs à mettre dans le diagramme
     */
    private DefaultXYZDataset mDataSet;

    /**
     * Constructeur par défaut
     */
    public BubbleMaker()
    {
        mDataSet = new DefaultXYZDataset();
        // Initialisation des pourcentages
        totalTopLeft = 0;
        totalTopRight = 0;
        totalBottomLeft = 0;
        totalBottomRight = 0;
        // Etiquete figurant sur l'axe horizontal (peut etre null) Par défaut : "v(g)"
        mXLabel = WebMessages.getString( "bubble.default.axis.domain" );
        // Etiquete figurant sur l'axe des valeurs (peut etre null) Par défaut : "ev(g)"
        mYLabel = WebMessages.getString( "bubble.default.axis.value" );
        // Initialisation de la valeur des axes aux valeurs par défaut
        mHorizontalAxisPos = DEFAULT_HORIZONTAL_AXIS_POS;
        mVerticalAxisPos = DEFAULT_VERTICAL_AXIS_POS;

    }

    /**
     * Constructeur avec la locale fourni
     * 
     * @param pLocale la locale
     */
    public BubbleMaker( Locale pLocale )
    {
        this();
        locale = pLocale;
    }

    /**
     * Constructeur avec la locale fourni et la position des axes
     * 
     * @param pLocale la locale
     * @param pHorizontalAxisPos la position de l'axe horizontal
     * @param pVerticalAxisPos la position de l'axe vertical
     */
    public BubbleMaker( Locale pLocale, Long pHorizontalAxisPos, Long pVerticalAxisPos )
    {
        this( pHorizontalAxisPos, pVerticalAxisPos );
        locale = pLocale;
    }

    /**
     * Constructeur avec juste la position des axes Permet de factoriser le traitement du cas null
     * 
     * @param pHorizontalAxisPos la position de l'axe horizontal
     * @param pVerticalAxisPos la position de l'axe vertical
     */
    public BubbleMaker( Long pHorizontalAxisPos, Long pVerticalAxisPos )
    {
        this();
        // Si c'est null on garde la valeur par défaut
        if ( pHorizontalAxisPos != null & pHorizontalAxisPos.intValue() != -1 )
        {
            mHorizontalAxisPos = pHorizontalAxisPos.intValue();
        }
        // Si c'est null on garde la valeur par défaut
        if ( pVerticalAxisPos != null && pVerticalAxisPos.intValue() != -1 )
        {
            mVerticalAxisPos = pVerticalAxisPos.intValue();
        }
    }

    /**
     * Constructeur avec le titre du diagramme
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     */
    public BubbleMaker( String pTitle )
    {
        this();
        mTitle = pTitle;
    }

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pDomainAxisLabel titre de l'axe horizontal (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     */
    public BubbleMaker( String pTitle, String pDomainAxisLabel, String pValueAxisLabel )
    {
        this( pTitle );
        mXLabel = pDomainAxisLabel;
        mYLabel = pValueAxisLabel;
    }

    /**
     * Constructeur avec le titre du diagramme et les titres des axes
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pDomainAxisLabel titre de l'axe horizontal (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     * @param pHorizontalAxisPos la position de l'axe des abssices
     * @param pVerticalAxisPos la position de l'axe des ordonées
     */
    public BubbleMaker( String pTitle, String pDomainAxisLabel, String pValueAxisLabel, Long pHorizontalAxisPos,
                        Long pVerticalAxisPos )
    {
        this( pHorizontalAxisPos, pVerticalAxisPos );
        mTitle = pTitle;
        mXLabel = pDomainAxisLabel;
        mYLabel = pValueAxisLabel;
    }

    /**
     * Constructeur complet
     * 
     * @param pTitle titre du diagramme (peut etre <code>null</code>)
     * @param pLocale la locale
     * @param pDomainAxisLabel titre de l'axe horizontal (peut etre <code>null</code>)
     * @param pValueAxisLabel titre de l'axe des valeurs (peut etre <code>null</code>)
     * @param pHorizontalAxisPos la position de l'axe des abssices
     * @param pVerticalAxisPos la position de l'axe des ordonées
     */
    public BubbleMaker( Locale pLocale, String pTitle, String pDomainAxisLabel, String pValueAxisLabel,
                        Long pHorizontalAxisPos, Long pVerticalAxisPos )
    {
        this( pLocale, pHorizontalAxisPos, pVerticalAxisPos );
        mTitle = pTitle;
        mXLabel = pDomainAxisLabel;
        mYLabel = pValueAxisLabel;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultHeight()
     * @return la hauteur par défaut
     */
    protected int getDefaultHeight()
    {
        return DEFAULT_HEIGHT;
    }

    /**
     * @see com.airfrance.squalecommon.util.graph.AbstractGraphMaker#getDefaultWidth()
     * @return la largeur par défaut
     */
    protected int getDefaultWidth()
    {
        return DEFAULT_WIDTH;
    }

    /**
     * Ajoute une series de valeurs (x, y) au graphe de type Bubble <br />
     * <b>Attention : </b>les doublons doivent être évités pour des raisons de performances On doit toujours avoir
     * l'égalité suivante : pHorizontalValues.length == pVerticalValues.length
     * 
     * @param pName le nom de la série (peut etre <code>null</code> si c'est la première série, dans ce cas, seule
     *            cette série sera ajoutéee)
     * @param pHorizontalValues les valeurs de l'axe horizontal
     * @param pVerticalValues les valeurs de l'axe vertical
     * @param pTotal nombre de méthodes ayant une même valeur (vg, evg)
     * @return <code>true</code> si tout s'est bien passé, <code>false</code> sinon
     */
    public boolean addSerie( String pName, double[] pHorizontalValues, double[] pVerticalValues, double[] pTotal )
    {
        boolean ret = false;
        // si les nombres de valeurs horizontales et verticales sont égaux
        // et que l'on peut encore ajouter des valeurs
        if ( ( pHorizontalValues.length == pVerticalValues.length ) && ( mCouldAddDatas ) )
        {
            ret = true; // à partir d'ici on considère que tout va bien se passer
            // si tout s'est bien passé, on ajoute les valeurs
            // La taille du point est proportionnelle aux méthodes ayant les mêmes valeurs (vg,evg)
            double[] pSize = new double[pTotal.length];

            // La taille du point est proportionnelle aux nombres de méthodes ayant les mêmes valeurs (vg, evg)
            for ( int i = 0; i < pTotal.length; i++ )
            {
                double radius = Math.sqrt( pTotal[i] );
                // En deçà de la borne inférieure, la taille du point a la taille minimale
                if ( radius <= DEFAULT_LOWER_LIMIT )
                {
                    pSize[i] = DEFAULT_LOWER_SIZE;
                }
                else
                {
                    // Au delà de la borne supérieure, la taille du point a la taille maximale
                    if ( radius >= DEFAULT_UPPER_LIMIT )
                    {
                        pSize[i] = DEFAULT_UPPER_SIZE;
                    }
                    else
                    {
                        // Entre les bornes inférieure et supérieure, La taille du point grossit
                        // proportionnellement au nombre de méthodes ayant les mêmes vg et evg,
                        // la taille variant entre la taille minimale et la taille maximale
                        pSize[i] =
                            ( (double) ( radius - DEFAULT_LOWER_LIMIT )
                                * (double) ( DEFAULT_UPPER_SIZE - DEFAULT_LOWER_SIZE ) / (double) ( DEFAULT_UPPER_LIMIT - DEFAULT_LOWER_LIMIT ) )
                                + DEFAULT_LOWER_SIZE;
                    }
                }
            }
            addXYSeries( pName, pHorizontalValues, pVerticalValues, pSize );
            addDistribution( pHorizontalValues, pVerticalValues, pTotal );
        }
        return ret;
    }

    /**
     * Crée une XYSeries et l'ajoute.
     * 
     * @param pName le nom de la série (peut etre <code>null</code> si c'est la première série, dans ce cas, seule
     *            cette série sera ajoutéee).
     * @param pHorizontalValues les valeurs de l'axe horizontal.
     * @param pVerticalValues les valeurs de l'axe vertical.
     * @param pSize nombre de méthodes ayant une même valeur (vg, evg)
     */
    private void addXYSeries( String pName, double[] pHorizontalValues, double[] pVerticalValues, double[] pSize )
    {
        String name = pName;
        if ( null == name )
        {
            // si aucun nom n'a été spécifié on affecte le nom par défaut pour la courbe
            name = WebMessages.getString( "bubble.undefined.name" );
        }
        double[][] series = new double[][] { pHorizontalValues, pVerticalValues, pSize };
        mDataSet.addSeries( name, series );
    }

    /**
     * Selon la valeur du couple (vg, evg) calcule la distribution des méthodes dans les quatre coins du graphes en
     * fonction des critères - Maintenable et structuré - Maintenable mais mal structuré - Difficilement maintenable
     * mais structuré - Difficilement maintenable et mal structuré
     * 
     * @param pHorizontalValues les valeurs vg
     * @param pVerticalValues les valeurs evg
     * @param pTotal le nombre de méthodes ayant les mêmes valeurs (vg, evg)
     */
    private void addDistribution( double[] pHorizontalValues, double[] pVerticalValues, double[] pTotal )
    {
        for ( int i = 0; i < pHorizontalValues.length; i++ )
        {
            // Méthode structurée ?
            if ( pHorizontalValues[i] <= mVerticalAxisPos )
            {
                // Méthode structurée et maintenable ?
                if ( pVerticalValues[i] <= mHorizontalAxisPos )
                {
                    totalBottomLeft += pTotal[i];
                    // Méthode structurée mais difficilement maintenable ?
                }
                else
                {
                    totalTopLeft += pTotal[i];
                }
                // Méthode non structurée ?
            }
            else
            {
                // Méthode non structurée mais maintenable ?
                if ( pVerticalValues[i] <= mHorizontalAxisPos )
                {
                    totalBottomRight += pTotal[i];
                    // Méthode non structurée et difficilement maintenable ?
                }
                else
                {
                    totalTopRight += pTotal[i];
                }
            }
        }
    }

    /**
     * Construit (ou reconstruit) le diagramme puis le retourne
     * 
     * @return le diagramme JFreeChart.
     */
    protected JFreeChart getChart()
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            retChart = getCommonChart();
            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * Construit (ou reconstruit) le diagramme puis le retourne
     * 
     * @param pProjectId l'Id du projet
     * @param pCurrentAuditId l'Id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pComponentId les composants du projets
     * @param pVgs tableau des valeurs vg
     * @param pEvgs tableau des valeurs evg
     * @param pTotal nombre de méthodes ayant la même valeur (vg, evg)
     * @return le diagramme JFreeChart.
     */
    public JFreeChart getChart( String pProjectId, String pCurrentAuditId, String pPreviousAuditId,
                                long[] pComponentId, double[] pVgs, double[] pEvgs, double[] pTotal )
    {
        JFreeChart retChart = super.getChart();
        if ( null == retChart )
        {
            // Génération du graphe de type Bubble, directement depuis la page Jsp
            retChart = getCommonChart();
            // initialise les valeurs
            mProjectId = pProjectId;
            mPreviousAuditId = pPreviousAuditId;
            mCurrentAuditId = pCurrentAuditId;
            BubbleUrlGenerator generator =
                new BubbleUrlGenerator( pProjectId, pCurrentAuditId, pPreviousAuditId, pComponentId );
            retChart.getXYPlot().getRenderer().setURLGenerator( generator );

            BubbleToolTipGenerator toolTipGenerator = new BubbleToolTipGenerator( pVgs, pEvgs, pTotal );
            retChart.getXYPlot().getRenderer().setToolTipGenerator( toolTipGenerator );

            super.setChart( retChart );
        }
        return retChart;
    }

    /**
     * Factorisation du code commun à la génération du graphe
     * 
     * @return graphe de type Bubble
     */
    private JFreeChart getCommonChart()
    {
        // Création du graphe de type Bubble
        JFreeChart chart =
            ChartFactory.createBubbleChart( mTitle, mXLabel, mYLabel, mDataSet, PlotOrientation.VERTICAL, mShowLegend,
                                            true, false );

        ValueAxis domainAxis = chart.getXYPlot().getDomainAxis();
        ValueAxis rangeAxis = chart.getXYPlot().getRangeAxis();

        // Détermination des bornes en abscisse et en ordonnée
        double maxDomain = Math.max( domainAxis.getUpperBound(), DEFAULT_VERTICAL_AXIS_POS + DEFAULT_AXIS_MARGIN ) + 1;
        double minDomain = 0;
        double maxRange = Math.max( rangeAxis.getUpperBound(), DEFAULT_HORIZONTAL_AXIS_POS + DEFAULT_AXIS_MARGIN ) + 1;
        double minRange = 0;

        // Mise à l'échelle logarithmique des axes
        LogarithmicAxis newDomainAxis = new LogarithmicAxis( domainAxis.getLabel() );
        LogarithmicAxis newRangeAxis = new LogarithmicAxis( rangeAxis.getLabel() );

        // Affectation des bornes en abscisse et en ordonnée
        newDomainAxis.setLowerBound( minDomain );
        newDomainAxis.setUpperBound( maxDomain );
        newRangeAxis.setLowerBound( minRange );
        newRangeAxis.setUpperBound( maxRange );
        chart.getXYPlot().setDomainAxis( newDomainAxis );
        chart.getXYPlot().setRangeAxis( newRangeAxis );

        // Affichage de la répartition des méthodes selon les critères
        displayRepartitionSubtitles( chart );

        // Annotations
        XYLineAnnotation horizontalAxis =
            new XYLineAnnotation( minDomain, DEFAULT_HORIZONTAL_AXIS_POS, maxDomain, DEFAULT_HORIZONTAL_AXIS_POS );
        XYLineAnnotation verticalAxis =
            new XYLineAnnotation( DEFAULT_VERTICAL_AXIS_POS, minRange, DEFAULT_VERTICAL_AXIS_POS, maxRange );

        chart.getXYPlot().addAnnotation( horizontalAxis );
        chart.getXYPlot().addAnnotation( verticalAxis );
        return chart;
    }

    /**
     * Affichage sous la forme de sous-titres de la répartition des méthodes - Maintenable et structuré, - Maintenable
     * mais mal structuré - Difficilement maintenable mais structuré - Difficilement maintenable et mal structuré
     * 
     * @param pChart graphe de type Bubble
     */
    private void displayRepartitionSubtitles( JFreeChart pChart )
    {
        double percentTopLeft = 0;
        double percentTopRight = 0;
        double percentBottomLeft = 0;
        double percentBottomRight = 0;
        int totalMethods = (int) ( totalTopLeft + totalTopRight + totalBottomLeft + totalBottomRight );
        if ( totalMethods > 0 )
        {
            final int oneHundred = 100;
            // haut gauche : difficilement maintenable mais structuré
            percentTopLeft = totalTopLeft * oneHundred / totalMethods;
            // haut droit : difficilement maintenable et mal structuré
            percentTopRight = totalTopRight * oneHundred / totalMethods;
            // Bas gauche : maintenable et structuré
            percentBottomLeft = totalBottomLeft * oneHundred / totalMethods;
            // Bas droit : maintenable mais mal structuré
            percentBottomRight = totalBottomRight * oneHundred / totalMethods;
        }
        // Formatage des zones d'affichage
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits( 1 );
        numberFormat.setMinimumFractionDigits( 1 );
        // Ajout du sous-titre de répartition des méthodes avec le pourcentage correspondant : partie supérieure, coin
        // gauche et droit
        StringBuffer stringBufferTop = new StringBuffer();
        stringBufferTop.append( WebMessages.getString( locale, "bubble.project.subtitle.topLeft" ) + " "
            + numberFormat.format( percentTopLeft ) + "%     " );
        stringBufferTop.append( WebMessages.getString( locale, "bubble.project.subtitle.topRight" ) + " "
            + numberFormat.format( percentTopRight ) + "%" );
        TextTitle subtitleTop = new TextTitle( stringBufferTop.toString() );

        // Ajout du sous-titre de répartition des méthodes avec le pourcentage correspondant : partie inférieure, coin
        // gauche et droit
        StringBuffer stringBufferBottom = new StringBuffer();
        stringBufferBottom.append( WebMessages.getString( locale, "bubble.project.subtitle.bottomLeft" ) + " "
            + numberFormat.format( percentBottomLeft ) + "%     " );
        stringBufferBottom.append( WebMessages.getString( locale, "bubble.project.subtitle.bottomRight" ) + " "
            + numberFormat.format( percentBottomRight ) + "%" );
        TextTitle subtitleBottom = new TextTitle( stringBufferBottom.toString() );

        // Les répartitions sont ajoutées sous la forme de sous-menus
        subtitleBottom.setPosition( RectangleEdge.BOTTOM );
        pChart.addSubtitle( subtitleBottom );
        subtitleTop.setPosition( RectangleEdge.BOTTOM );
        pChart.addSubtitle( subtitleTop );
    }
}
