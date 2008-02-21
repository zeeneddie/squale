package com.airfrance.squaleweb.util.graph;

import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;

/**
 */
public class RepartitionUrlGenerator extends AbstractRepartitionUrlGenerator implements CategoryURLGenerator {

    /**
     * Constructeur, initialise les champs communs à tout le graphe 
     * @param pProjectId l'id du projet 
     * @param pCurrentAuditId l'id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     * @param pNbSeries le nombre de séries que contient le graph
     */
    public RepartitionUrlGenerator(String pProjectId, String pCurrentAuditId,String pPreviousAuditId, String pPracticeId, String pFactorParentId, int pNbSeries) {
        mProjectId = pProjectId;
        mCurrentAuditId = pCurrentAuditId;
        mPreviousAuditId = pPreviousAuditId;
        practiceId = pPracticeId;
        factorParent = pFactorParentId;
        nbSeries = pNbSeries;
    }

    /** 
     * Implémente la génération d'url
     * @param pDataSet les donneés
     * @param pSerieIndex l'index de la série sur lequel on va définir l'url
     * @param pCategory la catégorie
     * @see org.jfree.chart.urls.CategoryURLGenerator#generateURL(org.jfree.data.category.CategoryDataset, int, int)
     * @return l'url
     */
    public String generateURL(CategoryDataset pDataSet, int pSerieIndex, int pCategory) {
        return super.generateURL(pDataSet, pSerieIndex, pCategory);
    }
}
