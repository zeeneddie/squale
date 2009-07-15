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
 * Créé le 3 août 05
 *
 */
package org.squale.squalecommon.enterpriselayer.businessobject.result;

/**
 * @author m400841
 * @version 1.0
 * @hibernate.subclass discriminator-value="Graph"
 */
public abstract class GraphBO
    extends MeasureBO
{
    /**
     * Image sous forme de chaine de bytes
     */
    private final static String IMAGE = "Image";

    /**
     * Constructeur publique Initialise les "pseudo" attributs de la HashTable
     */
    public GraphBO()
    {
        super();
        getMetrics().put( IMAGE, new BinaryMetricBO() );
    }

    /**
     * @return recupere l'image sous forme de byte[]
     */
    public byte[] getImage()
    {
        return (byte[]) ( (MetricBO) getMetrics().get( IMAGE ) ).getValue();
    }

    /**
     * @param pImage image sous forme de byte[]
     */
    public void setImage( byte[] pImage )
    {
        ( (MetricBO) getMetrics().get( IMAGE ) ).setValue( pImage );
    }

}
