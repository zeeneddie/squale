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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.util.graph.GraphMaker;

/**
 * @author M400843
 */
public class ParamReviewForm
    extends RootForm
{

    /** le nombre de jours du graph d'evolution par défaut */
    private static final int NB_DAYS = 200;

    /** Identificateur du composant */
    private String mComponentId;

    /** Nom du composant */
    private String mComponentName;

    /** type du composant */
    private String mComponentType;

    /** Identificateur de la règle qualité */
    private String mRuleId;

    /** Does the rule is a manual practice ?*/
    private boolean isManualMark;

    /**
     * nombre de jours pour le graph d'évolution
     */
    private int mNbDays;

    /**
     * Type de resultat a afficher
     */
    private String mTre;

    /** Le graph */
    private GraphMaker reviewGraph;

    /**
     * @return le nombre de jours du graph d'evolution
     */
    public int getNbDays()
    {
        return mNbDays;
    }

    /**
     * @param pNbDays le nombre de jours du graph d'evolution
     */
    public void setNbDays( int pNbDays )
    {
        mNbDays = pNbDays;
    }

    /**
     * @return le type de resultat elementaire
     */
    public String getTre()
    {
        return mTre;
    }

    /**
     * @param string type de resultat elementaire
     */
    public void setTre( String string )
    {
        if ( string != null )
        {
            mTre = string;
        }
    }

    /**
     * @return id de la règle
     */
    public String getRuleId()
    {
        return mRuleId;
    }

    /**
     * @param pRuleId id de la règle
     */
    public void setRuleId( String pRuleId )
    {
        mRuleId = pRuleId;
    }

    /**
     * @return id de composant
     */
    public String getComponentId()
    {
        return mComponentId;
    }

    /**
     * @param pString id de composant
     */
    public void setComponentId( String pString )
    {
        mComponentId = pString;
    }

    /**
     * @return le nom du composant
     */
    public String getComponentName()
    {
        return mComponentName;
    }

    /**
     * @return le type du composant
     */
    public String getComponentType()
    {
        return mComponentType;
    }

    /**
     * @param pComponentName le nom du composant
     */
    public void setComponentName( String pComponentName )
    {
        mComponentName = pComponentName;
    }

    /**
     * @param pComponentType le type du composant
     */
    public void setComponentType( String pComponentType )
    {
        mComponentType = pComponentType;
    }

    /**
     * @return le graph
     */
    public GraphMaker getReviewGraph()
    {
        return reviewGraph;
    }

    /**
     * @param pGraph le graph
     */
    public void setReviewGraph( GraphMaker pGraph )
    {
        reviewGraph = pGraph;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void reset( ActionMapping arg0, HttpServletRequest arg1 )
    {
        super.reset( arg0, arg1 );
        setNbDays( NB_DAYS );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        if ( getNbDays() < 1 )
        {
            addError( "nbDays", new ActionError( "review.field.nbdays.error" ) );
        }
    }

    /**
     * Getter for the attribute isManualMark
     * 
     * @return true if the rule is a manual practice
     */
    public boolean isManualMark()
    {
        return isManualMark;
    }

    /**
     * Setter for the attribute isManualMark
     * 
     * @param pIsManualMark The new kind of manual practice
     */
    public void setIsManualMark( boolean pIsManualMark )
    {
        isManualMark = pIsManualMark;
    }
}
