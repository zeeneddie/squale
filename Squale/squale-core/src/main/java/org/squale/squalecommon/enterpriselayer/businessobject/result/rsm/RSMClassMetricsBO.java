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
package org.squale.squalecommon.enterpriselayer.businessobject.result.rsm;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;

/**
 * @hibernate.subclass discriminator-value="RSMClassMetrics"
 */
public class RSMClassMetricsBO
    extends RSMMetricsBO
{

    /**
     * Le nombre de lignes de codes
     */
    private final static String SLOC = "sloc";

    /**
     * Les commentaires
     */
    private final static String COMMENTS = "comments";

    /**
     * Le nombre de données publiques
     */
    private final static String PUBLIC_DATA = "public";

    /**
     * Le nombre de données protégées
     */
    private final static String PROTECTED_DATA = "protectedData";

    /**
     * Le nombre de données privées
     */
    private final static String PRIVATE_DATA = "private";

    /**
     * Constructeur par défaut.
     */
    public RSMClassMetricsBO()
    {
        // super();
        getMetrics().put( SLOC, new IntegerMetricBO() );
        getMetrics().put( COMMENTS, new IntegerMetricBO() );
        getMetrics().put( PUBLIC_DATA, new IntegerMetricBO() );
        getMetrics().put( PROTECTED_DATA, new IntegerMetricBO() );
        getMetrics().put( PRIVATE_DATA, new IntegerMetricBO() );
    }

    /**
     * Méthode d'accès à la métrique SLOC
     * 
     * @return la valeur du sloc
     */
    public Integer getSloc()
    {
        return (Integer) ( (MetricBO) getMetrics().get( SLOC ) ).getValue();
    }

    /**
     * Change la valeur de la métrique SLOC
     * 
     * @param pSLOC la nouvelle valeur du SLOC
     */
    public void setSloc( Integer pSLOC )
    {
        ( (IntegerMetricBO) getMetrics().get( SLOC ) ).setValue( pSLOC );
    }

    /**
     * Méthode d'accès à la métrique SLOC
     * 
     * @return la valeur du nombre de données publiques
     */
    public Integer getPublicData()
    {
        return (Integer) ( (MetricBO) getMetrics().get( PUBLIC_DATA ) ).getValue();
    }

    /**
     * Change la valeur de la métrique données publiques
     * 
     * @param pPublic la nouvelle valeur du nombre de données publiques
     */
    public void setPublicData( Integer pPublic )
    {
        ( (IntegerMetricBO) getMetrics().get( PUBLIC_DATA ) ).setValue( pPublic );
    }

    /**
     * Méthode d'accès à la métrique SLOC
     * 
     * @return la valeur du nombre de données publiques
     */
    public Integer getProtectedData()
    {
        return (Integer) ( (MetricBO) getMetrics().get( PROTECTED_DATA ) ).getValue();
    }

    /**
     * Change la valeur de la métrique données protégées
     * 
     * @param pProtected la nouvelle valeur du nombre de données protégées
     */
    public void setProtectedData( Integer pProtected )
    {
        ( (IntegerMetricBO) getMetrics().get( PROTECTED_DATA ) ).setValue( pProtected );
    }

    /**
     * Méthode d'accès à la métrique nombre de données privées
     * 
     * @return la valeur du nombre de données privées
     */
    public Integer getPrivateData()
    {
        return (Integer) ( (MetricBO) getMetrics().get( PRIVATE_DATA ) ).getValue();
    }

    /**
     * Change la valeur de la métrique nombre de données privées
     * 
     * @param pPrivateData la nouvelle valeur du nombre de données privées
     */
    public void setPrivateData( Integer pPrivateData )
    {
        ( (IntegerMetricBO) getMetrics().get( PRIVATE_DATA ) ).setValue( pPrivateData );
    }

    /**
     * Méthode d'accès à la métrique commentaires
     * 
     * @return la valeur sur le nombre de lignes de commentaires
     */
    public Integer getComments()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( COMMENTS ) ).getValue();
    }

    /**
     * Change la valeur de la métrique commentaires
     * 
     * @param pComments la nouvelle valeur du nombre de commentaires
     */
    public void setComments( Integer pComments )
    {
        ( (IntegerMetricBO) getMetrics().get( COMMENTS ) ).setValue( pComments );
    }

}
