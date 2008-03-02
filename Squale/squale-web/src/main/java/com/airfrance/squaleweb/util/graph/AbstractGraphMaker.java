package com.airfrance.squaleweb.util.graph;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.ImageEncoder;
import org.jfree.chart.encoders.ImageEncoderFactory;
import org.jfree.chart.encoders.ImageFormat;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 * @author M400843
 */
public abstract class AbstractGraphMaker
{
    /**
     * Titre du graph (peut etre <code>null</code>)
     */
    protected String mTitle;

    /**
     * <code>true</code> pour afficher la légende (valeur par défaut)
     */
    protected boolean mShowLegend = WebMessages.getBool( "default.abstract.show_legend" ); // true; // Valeur par
                                                                                            // défaut

    /**
     * Hauteur de l'image
     */
    protected int mHeight = getDefaultHeight(); // Valeur par défaut

    /**
     * Largeur de l'image
     */
    protected int mWidth = getDefaultWidth(); // Valeur par défaut

    /**
     * Format de l'imgae (png par défaut)
     */
    protected String mFormat = ImageFormat.PNG; // Valeur par défaut

    /**
     * Objet JFreeChart contenant le graph
     */
    private JFreeChart mChart;

    /**
     * Id du projet
     */
    protected String mProjectId;

    /**
     * Id de l'audit courant
     */
    protected String mCurrentAuditId;

    /**
     * Id de l'audit précédent
     */
    protected String mPreviousAuditId;

    /**
     * Etiquete figurant sur l'axe des abscisses
     */
    protected String mXLabel;

    /**
     * Etiquete figurant sur l'axe des ordonnées
     */
    protected String mYLabel;

    /**
     * Permet de fabriquer l'image sous forme d'un tableau de byte <b>Attention : </b> la méthode de la classe fille
     * doit vérifier que mChart est initialisé
     * 
     * @return un tableau de byte contenant une BufferedImage
     * @throws IOException si une erreur a lieu
     */
    public byte[] getImageInBytes()
        throws IOException
    {
        BufferedImage bufImage = getChart().createBufferedImage( mWidth, mHeight );
        ImageEncoder encoder = ImageEncoderFactory.newInstance( mFormat );

        return encoder.encode( bufImage );
    }

    /**
     * Cette méthode retourne le diagramme. Dans les classes concrétes, cette méthode est responsable de la construction
     * du diagramme s'il n'est pas encore construit.
     * 
     * @return le diagramme JFreeChart
     */
    protected JFreeChart getChart()
    {
        return mChart;
    }

    /**
     * mettre <code>null</code> pour que le diagramme soit recalculé
     * 
     * @param pChart le diagramme JFreeChart
     */
    protected void setChart( JFreeChart pChart )
    {
        mChart = pChart;
    }

    /**
     * Pour deboguer
     * 
     * @param pTitle un titre de frame
     * @return une frame (ChartFrame) contenant le diagramme
     */
    public ChartFrame getImageInFrame( String pTitle )
    {
        return new ChartFrame( pTitle, getChart() );
    }

    /**
     * Permet de définir la hauteur et la largeur de l'image souhaitée
     * 
     * @param pHeight hauteur du diagramme
     * @param pWidth largeur du diagramme
     */
    public void setSize( int pHeight, int pWidth )
    {
        mHeight = pHeight;
        mWidth = pWidth;
        // Pour être sur de recalculer le diagramme (s'il a deja été calculé) :
        setChart( null );
    }

    /**
     * @return la hauteur par défaut
     */
    protected abstract int getDefaultHeight();

    /**
     * @return la largeur par défaut
     */
    protected abstract int getDefaultWidth();

    /**
     * @return l'id de l'audit courant
     */
    public String getCurrentAuditId()
    {
        return mCurrentAuditId;
    }

    /**
     * @return l'id de l'audit précédent
     */
    public String getPreviousAuditId()
    {
        return mPreviousAuditId;
    }

    /**
     * @return l'id du projet
     */
    public String getProjectId()
    {
        return mProjectId;
    }
}
