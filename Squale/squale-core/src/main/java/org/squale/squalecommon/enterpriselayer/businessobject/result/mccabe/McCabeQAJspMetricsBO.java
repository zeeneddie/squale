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
package org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Métriques McCabe pour une JSP. On regroupe les métriques de niveau méthode avec celles de niveau classe car une jsp
 * n'a qu'une méthode
 * 
 * @hibernate.subclass discriminator-value="JSPMetrics"
 */
public class McCabeQAJspMetricsBO
    extends McCabeQAMetricsBO
{

    /**
     * Nombre de lignes mixtes de la méthode
     */
    private final static String NLMIXED = "nlmixed";

    /**
     * Nombre de ligne de commentaires de la méthode
     */
    private final static String NCLOC = "ncloc";

    /**
     * Nombre de lignes de source pur de la méthode
     */
    private final static String NSLOC = "nsloc";

    /**
     * V(g) = complexité cyclomatique de la méthode
     */
    private final static String VG = "vg";

    /**
     * Complexité cyclomatique essentielle de la méthode
     */
    private final static String EVG = "evg";

    /**
     * Nom du fichier
     */
    private String mFileName;

    /**
     * Constructeur par défaut
     */
    public McCabeQAJspMetricsBO()
    {
        getMetrics().put( NLMIXED, new IntegerMetricBO() );
        getMetrics().put( NCLOC, new IntegerMetricBO() );
        getMetrics().put( NSLOC, new IntegerMetricBO() );
        getMetrics().put( VG, new IntegerMetricBO() );
        getMetrics().put( EVG, new IntegerMetricBO() );
    }

    /**
     * Constructeur récupérant les métriques calculées sur la méthode de la JSP
     * 
     * @param pMethodResults les résulats de la méthode JSP
     */
    public McCabeQAJspMetricsBO( McCabeQAMethodMetricsBO pMethodResults )
    {
        setNlmixed( pMethodResults.getNlmixed() );
        setNcloc( pMethodResults.getNcloc() );
        setNsloc( pMethodResults.getNsloc() );
        setVg( pMethodResults.getVg() );
        setEvg( pMethodResults.getEvg() );
        mFileName = pMethodResults.getFilename();
        mComponentName = pMethodResults.getComponentName();
        mTaskName = pMethodResults.getTaskName();
        mAudit = pMethodResults.getAudit();

    }

    /**
     * Affecte les résultats niveau classe
     * 
     * @param pClassResults les résutats niveau classe
     */
    public void setClassMetrics( McCabeQAClassMetricsBO pClassResults )
    {
        // Voir quelles métriques
    }

    /**
     * Retourne le nom du fichier
     * 
     * @return le nom du fichier
     */
    public String getFileName()
    {
        return mFileName;
    }

    /**
     * Change le nom du fichier
     * 
     * @param pFileName la nouvelle valeur pour
     */
    public void setFileName( String pFileName )
    {
        mFileName = pFileName;
    }

    /**
     * Access method for the mNlmixed property.
     * 
     * @return the current value of the mNloc property
     * @roseuid 42C416B500B8
     */
    public Integer getNlmixed()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NLMIXED ) ).getValue();
    }

    /**
     * Sets the value of the mNlmixed property.
     * 
     * @param pNlmixed the new value of the mNloc property
     */
    public void setNlmixed( Integer pNlmixed )
    {
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setValue( pNlmixed );
        getMetrics().put( NLMIXED, metric );
    }

    /**
     * Access method for the mNcloc property.
     * 
     * @return the current value of the mNcloc property
     */
    public Integer getNcloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NCLOC ) ).getValue();
    }

    /**
     * Sets the value of the mNcloc property.
     * 
     * @param pNcloc the new value of the mNcloc property
     */
    public void setNcloc( Integer pNcloc )
    {
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setValue( pNcloc );
        getMetrics().put( NCLOC, metric );
    }

    /**
     * Access method for the mNsloc property.
     * 
     * @return the current value of the mNsloc property
     */
    public Integer getNsloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NSLOC ) ).getValue();
    }

    /**
     * Sets the value of the mNsloc property.
     * 
     * @param pNsloc the new value of the mNsloc property
     */
    public void setNsloc( Integer pNsloc )
    {
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setValue( pNsloc );
        getMetrics().put( NSLOC, metric );
    }

    /**
     * Access method for the mVg property.
     * 
     * @return the current value of the mVg property
     */
    public Integer getVg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( VG ) ).getValue();
    }

    /**
     * Sets the value of the mVg property.
     * 
     * @param pVg the new value of the mVg property
     */
    public void setVg( Integer pVg )
    {
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setValue( pVg );
        getMetrics().put( VG, metric );
    }

    /**
     * Access method for the mEvg property.
     * 
     * @return the current value of the mEvg property
     */
    public Integer getEvg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( EVG ) ).getValue();
    }

    /**
     * Sets the value of the mEvg property.
     * 
     * @param pEvg the new value of the mEvg property
     */
    public void setEvg( Integer pEvg )
    {
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setValue( pEvg );
        getMetrics().put( EVG, metric );
    }
}
