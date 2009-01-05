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
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient une règle qualité.
 * 
 * @version 1.0
 * @author m400842
 */
public class QualityRuleForm
    extends RootForm
{

    /**
     * Type de la règle (facteur, critere, pratique)
     */
    private String mType = "";

    /**
     * Nom du facteur
     */
    private String mName = "";

    /**
     * Constructeur par défaut
     */
    public QualityRuleForm()
    {
    }

    /**
     * Constructeur complet.
     * 
     * @param pName le nom de la règle.
     * @param pType le type de la règle.
     */
    public QualityRuleForm( final String pName, final String pType )
    {
        mName = pName;
        mType = pType;
    }

    /**
     * @return le nom de la règle
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return le type de la règle
     */
    public String getType()
    {
        return mType;
    }

    /**
     * @param pName le nom de la règle
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pType le type de la règle
     */
    public void setType( String pType )
    {
        mType = pType;
    }

}
