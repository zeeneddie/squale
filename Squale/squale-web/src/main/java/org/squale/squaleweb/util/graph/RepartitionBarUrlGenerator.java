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

import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;

/**
 */
public class RepartitionBarUrlGenerator
    extends AbstractRepartitionUrlGenerator
    implements CategoryURLGenerator
{

    /**
     * Constructeur, initialise les champs communs à tout le graphe
     * 
     * @param pProjectId l'id du projet
     * @param pCurrentAuditId l'id de l'audit
     * @param pPreviousAuditId pPreviousAuditId
     * @param pPracticeId l'id de la pratique
     * @param pFactorParentId l'id du facteur parent
     * @param pNbSeries le nombre de séries que contient le graph
     */
    public RepartitionBarUrlGenerator( String pProjectId, String pCurrentAuditId, String pPreviousAuditId,
                                       String pPracticeId, String pFactorParentId, int pNbSeries )
    {
        mProjectId = pProjectId;
        mCurrentAuditId = pCurrentAuditId;
        mPreviousAuditId = pPreviousAuditId;
        practiceId = pPracticeId;
        factorParent = pFactorParentId;
        nbSeries = pNbSeries;
    }

    /**
     * Implémente la génération d'url
     * 
     * @param pDataSet les donneés
     * @param pSerieIndex l'index de la série sur lequel on va définir l'url
     * @param pCategory la catégorie
     * @see org.jfree.chart.urls.CategoryURLGenerator#generateURL(org.jfree.data.category.CategoryDataset, int, int)
     * @return l'url
     */
    public String generateURL( CategoryDataset pDataSet, int pSerieIndex, int pCategory )
    {
        return super.generateURL( pDataSet, pSerieIndex, pCategory );
    }

}
