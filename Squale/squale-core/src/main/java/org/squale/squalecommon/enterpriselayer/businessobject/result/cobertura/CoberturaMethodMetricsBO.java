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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

/**
 * <p>
 * Instance of this class stores values regarding the method level metrics
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaMethodMetrics"
 */
public class CoberturaMethodMetricsBO
    extends AbstractCoberturaMetricsBO
{
    /** The signature of the method as it is needed later to identify the methods */
    private String signature;

    /** The default constructor */
    public CoberturaMethodMetricsBO()
    {
        super();
        signature = "";
    }

    /**
     * Getter of the signature of the method
     * 
     * @return the signature of the method
     */
    public String getSignature()
    {
        return signature;
    }

    /**
     * Setter of the signature of the method
     * 
     * @param pSignature the value of the signature
     */
    public void setSignature( String pSignature )
    {
        this.signature = pSignature;
    }

}
