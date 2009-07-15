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
/*
 * Créé le 24 janv. 07
 *
 * Personnalisation des liens URL du Pie Chart
 */
package org.squale.squaleweb.util.graph;

import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.data.general.PieDataset;

/**
 * @author 6370258 Classe de génération de liens spécifiques à chaque portion du Pie Chart
 */
public class PieChartUrlGenerator
    extends AbstractURLGenerator
    implements PieURLGenerator
{
    /**
     * Map des projets libellé et Id
     */
    private Map urlMap;

    /**
     * Constructeur par défaut
     */
    public PieChartUrlGenerator()
    {
    }

    /**
     * Constructeur
     * 
     * @param pUrlMap Map des projets
     * @param pCurrentAuditId l'id de l'audit courant
     * @param pPreviousAuditId l'id de l'audit précédent
     */
    public PieChartUrlGenerator( Map pUrlMap, String pCurrentAuditId, String pPreviousAuditId )
    {
        urlMap = new HashMap();
        urlMap.putAll( pUrlMap );
        mCurrentAuditId = pCurrentAuditId;
        mPreviousAuditId = pPreviousAuditId;
    }

    /**
     * Génération du lien URL
     * 
     * @param pDataset le DataSet du graphe
     * @param pKey le libellé de la portion du graphe
     * @param pIndex la série (inutilisée)
     * @return url l'url de la portion du Pie Chart
     */
    public String generateURL( PieDataset pDataset, Comparable pKey, int pIndex )
    {
        String key = (String) pKey;
        String url =
            "project.do?action=select&projectId=" + (Long) urlMap.get( key ) + "&currentAuditId=" + mCurrentAuditId;
        if ( mPreviousAuditId != null )
        {
            url += "&previousAuditId=" + mPreviousAuditId;
        }
        return url;
    }

}
