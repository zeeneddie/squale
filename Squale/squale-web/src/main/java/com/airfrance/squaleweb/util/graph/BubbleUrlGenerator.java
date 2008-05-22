/*
 * Créé le 24 janv. 07
 *
 * Personnalisation des liens URL du graphe de type Bubble
 */
package com.airfrance.squaleweb.util.graph;

import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.XYDataset;

import com.airfrance.squaleweb.applicationlayer.action.results.project.TopAction;

/**
 * @author 6370258 Classe de génération de liens spécifiques à chaque point du graphe de type Bubble
 */
public class BubbleUrlGenerator
    extends AbstractURLGenerator
    implements XYURLGenerator
{
    /**
     * Array of vg values
     */
    private double[] tbVgs;

    /**
     * Array of evg values
     */
    private double[] tbEvgs;

    /** vg metrics tres */
    private String mVgTre;

    /** evg metrics tres */
    private String mEvgTre;

    /**
     * Constructeur par défaut
     */
    public BubbleUrlGenerator()
    {
    }

    /**
     * Constructeur
     * 
     * @param pProjectId Id du projet
     * @param pAuditId Id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pVgs array of vg values
     * @param pEvgs array of evg values
     * @param vgTre vg tre
     * @param evgTre evg tre
     */
    public BubbleUrlGenerator( String pProjectId, String pAuditId, String pPreviousAuditId, double[] pVgs,
                               double[] pEvgs, String vgTre, String evgTre)
    {
        // Initialisation des attributs
        mProjectId = pProjectId;
        mCurrentAuditId = pAuditId;
        mPreviousAuditId = pPreviousAuditId;
        mVgTre = vgTre;
        mEvgTre = evgTre;
        tbVgs = pVgs;
        tbEvgs = pEvgs;

    }

    /**
     * Génération du lien URL
     * 
     * @param pDataset le DataSet du graphe
     * @param pSeries la série du graphe
     * @param pItem le point du graphe
     * @return url l'url de la portion du Scatterplott
     */
    public String generateURL( XYDataset pDataset, int pSeries, int pItem )
    {
        String url =
            "component_tres.do?action=displayComponents&projectId=" + mProjectId + "&currentAuditId=" + mCurrentAuditId
                + "&" + TopAction.TRE_KEYS_KEYWORD + "=" + mVgTre + "," + mEvgTre + "&" + TopAction.TRE_VALUES_KEYWORD + "=" + tbVgs[pItem] + "," + tbEvgs[pItem];
        // On ne rajout l'audit précédent à l'url que si il est valide
        if ( mPreviousAuditId != null && !"-1".equals( mPreviousAuditId ) )
        {
            url += "&previousAuditId=" + mPreviousAuditId;
        }
        return url;
    }
}
