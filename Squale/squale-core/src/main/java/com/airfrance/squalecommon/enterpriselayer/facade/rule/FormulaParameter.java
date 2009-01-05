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
package com.airfrance.squalecommon.enterpriselayer.facade.rule;

/**
 * Paramètre d'une formule Une formule utilise des paramètres avec la syntaxe xxx.yyy où xxx représente un type de
 * mesure et yyy l'attribut de la mesure Par exemple mccabe.maxvg
 */
public class FormulaParameter
{
    /** Type de mesure */
    private String mMeasureKind;

    /** Attribut de la mesure */
    private String mMeasureAttribute;

    /**
     * @return attribut de la mesure
     */
    public String getMeasureAttribute()
    {
        return mMeasureAttribute;
    }

    /**
     * @return type de mesure
     */
    public String getMeasureKind()
    {
        return mMeasureKind;
    }

    /**
     * @param pMeasureAttribute attribut de la mesure
     */
    public void setMeasureAttribute( String pMeasureAttribute )
    {
        mMeasureAttribute = pMeasureAttribute;
    }

    /**
     * @param pMeasureKind type de mesure
     */
    public void setMeasureKind( String pMeasureKind )
    {
        mMeasureKind = pMeasureKind;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object obj )
    {
        boolean result;
        if ( obj instanceof FormulaParameter )
        {
            FormulaParameter parameter = (FormulaParameter) obj;
            result =
                parameter.getMeasureKind().equals( getMeasureKind() )
                    && parameter.getMeasureAttribute().equals( getMeasureAttribute() );
        }
        else
        {
            result = super.equals( obj );
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getMeasureAttribute().hashCode();
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getMeasureKind() + "." + getMeasureAttribute();
    }
}
