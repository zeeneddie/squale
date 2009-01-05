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

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;

/**
 */
public class RepartitionToolTipGenerator
    implements CategoryToolTipGenerator
{

    /**
     * @see org.jfree.chart.labels.CategoryToolTipGenerator#generateToolTip(org.jfree.data.category.CategoryDataset,
     *      int, int) {@inheritDoc}
     */
    public String generateToolTip( CategoryDataset pCategory, int pRowIndex, int pColumIndex )
    {
        String result = "";
        if ( pRowIndex < AbstractRepartitionMaker.NB_SERIES_FOR_FLOAT_GRAPH - 1 )
        {
            result =
                "(" + pCategory.getRowKey( pRowIndex ) + "," + pCategory.getRowKey( pRowIndex + 1 ) + ")="
                    + pCategory.getValue( pRowIndex, pColumIndex );
        }
        else
        {
            result =
                "(" + pCategory.getRowKey( pRowIndex ) + ",3.0" + ")=" + pCategory.getValue( pRowIndex, pColumIndex );
        }
        return result;
    }
}
