package com.airfrance.squaleweb.util.graph;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;

/**
 */
public class AbstractRepartitionUrlGenerator
    extends AbstractURLGenerator
{

    /**
     * l'id de la pratique
     */
    protected String practiceId;

    /**
     * l'id du critère parent
     */
    protected String factorParent;

    /**
     * Le nombre de batonnets, varie en fonction de la formule associée à la pratique
     */
    protected int nbSeries;

    /**
     * Implémente la génération d'url
     * 
     * @param pDataSet les donneés
     * @param pSerieIndex l'index de la série sur lequel on va définir l'url
     * @param pCategory la catégorie
     * @see org.jfree.chart.urls.CategoryURLGenerator#generateURL(org.jfree.data.category.CategoryDataset, int, int)
     * @return l'url
     */
    public String generateURL( Object pDataSet, int pSerieIndex, int pCategory )
    {
        // Définit le min et le max de l'intervalle de note
        String url = "";
        final double coeff = 10;
        double minMark =
            ( (int) ( coeff * ( (double) pSerieIndex ) / ( (double) nbSeries / (double) PracticeResultBO.EXCELLENT ) ) )
                / coeff;
        double maxMark =
            Math.round( (float) coeff * ( minMark + ( (double) PracticeResultBO.EXCELLENT / nbSeries ) ) ) / coeff;
        url =
            "mark.do?action=mark&projectId=" + mProjectId + "&currentAuditId=" + mCurrentAuditId + "&previousAuditId="
                + mPreviousAuditId + "&tre=" + practiceId + "&minMark=" + minMark + "&maxMark=" + maxMark;
        if ( factorParent != null && !factorParent.equals( "" ) )
        {
            url += "&factorParent=" + factorParent;
        }
        return url;
    }

}
