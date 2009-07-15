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
package org.squale.squaleweb.util.graph;

import org.jfree.chart.labels.XYZToolTipGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * @author 6370258 Chaque point du graphe a un Tooltip personnalisé
 */
public class BubbleToolTipGenerator
    implements XYZToolTipGenerator
{

    /**
     * Nombre de méthodes ayant le même couple (vg, evg
     */
    private double[] tbTotalMethods;

    /**
     * Tableau des valeurs vg
     */
    private double[] tbVgs;

    /**
     * Tableau des valeurs evg
     */
    private double[] tbEvgs;

    /**
     * Constructeur par défaut
     */
    public BubbleToolTipGenerator()
    {
    }

    /**
     * Constructeur
     * 
     * @param pVgs tableau des valeurs vg
     * @param pEvgs tableau des valeurs evg
     * @param pTotalMethods nombre de composants (i.e méthodes) ayant le même couple (vg, evg)
     */
    public BubbleToolTipGenerator( double[] pVgs, double[] pEvgs, double[] pTotalMethods )
    {
        // Initialisation des attributs
        tbVgs = new double[pVgs.length];
        System.arraycopy( pVgs, 0, tbVgs, 0, pVgs.length );

        tbEvgs = new double[pEvgs.length];
        System.arraycopy( pEvgs, 0, tbEvgs, 0, pEvgs.length );

        tbTotalMethods = new double[pTotalMethods.length];
        System.arraycopy( pTotalMethods, 0, tbTotalMethods, 0, pTotalMethods.length );
    }

    /**
     * Personnalisation des Tooltips
     * 
     * @param pDataset le Dataset du graphe
     * @param pSeries la série du graphe
     * @param pItem le point du graphe
     * @return le Tooltip affecté au point du graphe
     */
    public String generateToolTip( XYZDataset pDataset, int pSeries, int pItem )
    {
        return null;
    }

    /**
     * Personnalisation des Tooltips
     * 
     * @param pDataset le Dataset du graphe
     * @param pSeries la série du graphe
     * @param pItem le point du graphe
     * @return le Tooltip affecté au point du graphe
     */
    public String generateToolTip( XYDataset pDataset, int pSeries, int pItem )
    {
        String itemTooltip =
            "vg= " + tbVgs[pItem] + " evg= " + tbEvgs[pItem] + " total= " + (int) tbTotalMethods[pItem];
        return itemTooltip;
    }

}
