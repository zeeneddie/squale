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
package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour la liste des items d'une transgression
 */
public class RuleCheckingItemsListForm
    extends RootForm
{

    /**
     * Nom de la règle transgressée
     */
    private String mRuleName;

    /**
     * Détails concernant la règle
     */
    private Collection mDetails;

    /**
     * Indique si la liste contient au moins un item qui possède un lien vers un composant
     */
    private boolean mComponentLink;

    /**
     * Constructeur par défaut
     */
    public RuleCheckingItemsListForm()
    {
        mDetails = new ArrayList();
    }

    /**
     * @return le nom de la règle
     */
    public String getRuleName()
    {
        return mRuleName;
    }

    /**
     * @return les détails
     */
    public Collection getDetails()
    {
        return mDetails;
    }

    /**
     * @param pRuleName le nom de la règle
     */
    public void setRuleName( String pRuleName )
    {
        mRuleName = pRuleName;
    }

    /**
     * @param pDetails les détails
     */
    public void setDetails( Collection pDetails )
    {
        mDetails = pDetails;
    }

    /**
     * @return true si il y a un lien vers un composant
     */
    public boolean getComponentLink()
    {
        return mComponentLink;
    }

    /**
     * @param pComponentLink lien vers composant
     */
    public void setComponentLink( boolean pComponentLink )
    {
        mComponentLink = pComponentLink;
    }

}
