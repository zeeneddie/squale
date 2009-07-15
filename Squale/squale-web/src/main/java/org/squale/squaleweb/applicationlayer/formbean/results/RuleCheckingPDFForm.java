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
package org.squale.squaleweb.applicationlayer.formbean.results;

/**
 * Bean représentant les transgressions pour l'export PDF
 */
public class RuleCheckingPDFForm
    extends RulesCheckingForm
{

    /** Détails de la règles */
    private RuleCheckingItemsListForm mDetails;

    /**
     * Constructeur par défaut
     */
    public RuleCheckingPDFForm()
    {
        this( "", "", 0, new RuleCheckingItemsListForm() );
    }

    /**
     * @param pNameRule le nom de la règle
     * @param pSeverity la sévérité de la règle
     * @param pTransgressionsNumber le nombre de transgressions
     * @param pItemsForm les détails de la règles
     */
    public RuleCheckingPDFForm( String pNameRule, String pSeverity, int pTransgressionsNumber,
                                RuleCheckingItemsListForm pItemsForm )
    {
        mNameRule = pNameRule;
        mSeverity = pSeverity;
        mTransgressionsNumber = pTransgressionsNumber;
        mDetails = pItemsForm;
    }

    /**
     * @return les détails de la règles
     */
    public RuleCheckingItemsListForm getDetails()
    {
        return mDetails;
    }

    /**
     * @param pItems les détails de la règles
     */
    public void setDetails( RuleCheckingItemsListForm pItems )
    {
        mDetails = pItems;
    }

}
