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
package org.squale.squaleweb.applicationlayer.formbean.component;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Données synthétiques d'un facteur
 */
public class FormulaForm
    extends RootForm
{
    /** l'id */
    private long mId;

    /** Conditions de trigger */
    private String mTriggerCondition;

    /** Mesures */
    private String mMeasures;

    /** Niveau de composant */
    private String mComponentLevel;

    /** Conditions */
    private String[] mConditions;

    /**
     * @return componentn level
     */
    public String getComponentLevel()
    {
        return mComponentLevel;
    }

    /**
     * @return conditions
     */
    public String[] getConditions()
    {
        return mConditions;
    }

    /**
     * @return mesures
     */
    public String getMeasures()
    {
        return mMeasures;
    }

    /**
     * @return rigger
     */
    public String getTriggerCondition()
    {
        return mTriggerCondition;
    }

    /**
     * @param pString component level
     */
    public void setComponentLevel( String pString )
    {
        mComponentLevel = pString;
    }

    /**
     * @param pStrings conditions
     */
    public void setConditions( String[] pStrings )
    {
        mConditions = pStrings;
    }

    /**
     * @param pString mesures
     */
    public void setMeasures( String pString )
    {
        mMeasures = pString;
    }

    /**
     * @param pString trigger
     */
    public void setTriggerCondition( String pString )
    {
        mTriggerCondition = pString;
    }

    /**
     * @return l'id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId l'id de la formule
     */
    public void setId( long pId )
    {
        mId = pId;
    }

}
