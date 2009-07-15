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
package org.squale.squalecommon.datatransfertobject.rule;

import java.util.ArrayList;
import java.util.Collection;

/**
 * DTO d'une formule abstraite
 */
public abstract class AbstractFormulaDTO
{

    /** L'id de l'objet */
    private long mId;

    /**
     * Contient le niveau de la formule (Classe, méthode, fichier, package ou projet)
     */
    private String mComponentLevel;

    /**
     * Mesures composant la formules Par exemple une mesure de type McCabe et une mesure de type SonarJ
     */
    private Collection mMeasureKinds = new ArrayList();

    /** Condition d'activation de la formule */
    private String mTriggerCondition;

    /**
     * @return niveau de composant
     */
    public String getComponentLevel()
    {
        return mComponentLevel;
    }

    /**
     * @param pStrings mesures
     */
    public void setMeasureKinds( Collection pStrings )
    {
        mMeasureKinds = pStrings;
    }

    /**
     * @return niveau de composant
     */
    public Collection getMeasureKinds()
    {
        return mMeasureKinds;
    }

    /**
     * @return condition de trigger
     */
    public String getTriggerCondition()
    {
        return mTriggerCondition;
    }

    /**
     * @param pString niveau de composant
     */
    public void setComponentLevel( String pString )
    {
        mComponentLevel = pString;
    }

    /**
     * @param pString condition de trigger
     */
    public void setTriggerCondition( String pString )
    {
        mTriggerCondition = pString;
    }

    /**
     * @return l'id de l'objet
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId l'id de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pVisitor visiteur
     * @return objet
     */
    public abstract Object accept( FormulaDTOVisitor pVisitor );

    /** type de la formule */
    private String mType;

    /**
     * @return le type de la formule
     */
    public String getType()
    {
        return mType;
    }

    /**
     * @param pType le nouveau type
     */
    public void setType( String pType )
    {
        mType = pType;
    }

}
