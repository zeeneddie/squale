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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\mccabe\\McCabeMethodMetricsBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * @hibernate.subclass discriminator-value="MethodMetrics"
 */
public final class McCabeQAMethodMetricsBO
    extends McCabeQAMetricsBO
{

    /**
     * Nombre de lignes mixtes
     */
    private final static String NLMIXED = "nlmixed";

    /**
     * Nombre de ligne de commentaires
     */
    private final static String NCLOC = "ncloc";

    /**
     * Nombre de lignes de source pur
     */
    private final static String NSLOC = "nsloc";

    /**
     * V(g) = complexité cyclomatique
     */
    private final static String VG = "vg";

    /**
     * Complexité cyclomatique essentielle
     */
    private final static String EVG = "evg";

    /**
     * Complexité de conception modulaire
     */
    private final static String IVG = "ivg";

    /**
     * Nombre d'appels à d'autres méthodes
     */
    private final static String FANOUT = "fanout";

    /**
     * Nombre de lignes total.
     */
    private final static String NL = "nl";
    
    /**
     * Boolean Indicator for Dead Code (added for COBOL)
     */
    private final static String DEADCODE = "deadCode";

    /**
     * Nom du fichier
     */
    private String mFilename;

    /**
     * Numero de ligne de la méthode
     */
    private String mStartLine;

    /**
     * Constructeur
     * 
     * @roseuid 42B97434034A
     */
    public McCabeQAMethodMetricsBO()
    {
        super();
        getMetrics().put( NLMIXED, new IntegerMetricBO() );
        getMetrics().put( NCLOC, new IntegerMetricBO() );
        getMetrics().put( NSLOC, new IntegerMetricBO() );
        getMetrics().put( VG, new IntegerMetricBO() );
        getMetrics().put( EVG, new IntegerMetricBO() );
        getMetrics().put( IVG, new IntegerMetricBO() );
        getMetrics().put( FANOUT, new IntegerMetricBO() );
        getMetrics().put( NL, new IntegerMetricBO() );
        getMetrics().put( DEADCODE, new IntegerMetricBO() );
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
     * @roseuid 42C416B500D7
     */
    public void setNlmixed( Integer pNlmixed )
    {
        ( (IntegerMetricBO) getMetrics().get( NLMIXED ) ).setValue( pNlmixed );
    }

    /**
     * Access method for the mNcloc property.
     * 
     * @return the current value of the mNcloc property
     * @roseuid 42C416B50145
     */
    public Integer getNcloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NCLOC ) ).getValue();
    }

    /**
     * Sets the value of the mNcloc property.
     * 
     * @param pNcloc the new value of the mNcloc property
     * @roseuid 42C416B50173
     */
    public void setNcloc( Integer pNcloc )
    {
        ( (IntegerMetricBO) getMetrics().get( NCLOC ) ).setValue( pNcloc );
    }

    /**
     * Access method for the mNsloc property.
     * 
     * @return the current value of the mNsloc property
     * @roseuid 42C416B501E1
     */
    public Integer getNsloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NSLOC ) ).getValue();
    }

    /**
     * Sets the value of the mNsloc property.
     * 
     * @param pNsloc the new value of the mNsloc property
     * @roseuid 42C416B50200
     */
    public void setNsloc( Integer pNsloc )
    {
        ( (IntegerMetricBO) getMetrics().get( NSLOC ) ).setValue( pNsloc );
    }

    /**
     * Access method for the mVg property.
     * 
     * @return the current value of the mVg property
     * @roseuid 42C416B502EA
     */
    public Integer getVg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( VG ) ).getValue();
    }

    /**
     * Sets the value of the mVg property.
     * 
     * @param pVg the new value of the mVg property
     * @roseuid 42C416B50329
     */
    public void setVg( Integer pVg )
    {
        ( (IntegerMetricBO) getMetrics().get( VG ) ).setValue( pVg );
    }

    /**
     * Access method for the mEvg property.
     * 
     * @return the current value of the mEvg property
     * @roseuid 42C416B50358
     */
    public Integer getEvg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( EVG ) ).getValue();
    }

    /**
     * Sets the value of the mEvg property.
     * 
     * @param pEvg the new value of the mEvg property
     * @roseuid 42C416B50396
     */
    public void setEvg( Integer pEvg )
    {
        ( (IntegerMetricBO) getMetrics().get( EVG ) ).setValue( pEvg );
    }

    /**
     * Access method for the mIvg property.
     * 
     * @return the current value of the mIvg property
     * @roseuid 42C416B6000C
     */
    public Integer getIvg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( IVG ) ).getValue();
    }

    /**
     * Sets the value of the mIvg property.
     * 
     * @param pIvg the new value of the mIvg property
     * @roseuid 42C416B6003B
     */
    public void setIvg( Integer pIvg )
    {
        ( (IntegerMetricBO) getMetrics().get( IVG ) ).setValue( pIvg );
    }

    /**
     * Access method for the mFanout property.
     * 
     * @return the current value of the mFanout property
     * @roseuid 42C416B600B8
     */
    public Integer getFanout()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( FANOUT ) ).getValue();
    }

    /**
     * Sets the value of the mFanout property.
     * 
     * @param pFanout the new value of the mFanout property
     * @roseuid 42C416B60116
     */
    public void setFanout( Integer pFanout )
    {
        ( (IntegerMetricBO) getMetrics().get( FANOUT ) ).setValue( pFanout );
    }

    /**
     * Access method for the mFilename property.
     * 
     * @return the current value of the mFilename property
     */
    public String getFilename()
    {
        return mFilename;
    }

    /**
     * Sets the value of the mFilename property.
     * 
     * @param pFilename the new value of the mFilename property
     */
    public void setFilename( String pFilename )
    {
        mFilename = pFilename;
    }

    /**
     * Access method for the mNl property.
     * 
     * @return the current value of the mNl property
     * @roseuid 42E623BF0036
     */
    public Integer getNl()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NL ) ).getValue();
    }

    /**
     * Sets the value of the mNl property.
     * 
     * @param pNl the new value of the mNl property
     * @roseuid 42E623BF0046
     */
    public void setNl( Integer pNl )
    {
        ( (IntegerMetricBO) getMetrics().get( NL ) ).setValue( pNl );
    }

    /**
     * Access method for the mDeadCode property.
     * 
     * @return the current value of the mDeadCode property
     */
    public Integer getDeadCode()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEADCODE ) ).getValue();
    }

    /**
     * Sets the value of the mDeadCode property.
     * 
     * @param pNl the new value of the mDeadCode property
     */
    public void setDeadCode( Integer pDeadCode )
    {
        ( (IntegerMetricBO) getMetrics().get( DEADCODE ) ).setValue( pDeadCode );
    }
    
    /**
     * @return le numéro de ligne de la méthode
     */
    public String getStartLine()
    {
        return mStartLine;
    }

    /**
     * @param pLine le numéro de ligne de la méthode
     */
    public void setStartLine( Integer pLine )
    {
        mStartLine = pLine.toString();
    }

}
