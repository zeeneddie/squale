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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;
import org.squale.squaleweb.util.graph.GraphMaker;

/**
 * Contient une liste de résultats
 * 
 * @author M400842
 */
public class ResultListForm
    extends RootForm
{

    /**
     * Liste des résultats
     */
    private List mList = new ArrayList();

    /** le kiviat de niveau application */
    private GraphMaker kiviat;

    /** le pieChart de niveau application */
    private GraphMaker pieChart;

    /**
     * booléen conditionnant l'affichage de la case à cocher "tous les facteurs"
     */
    private Boolean mDisplayCheckBoxFactors = new Boolean( true );

    /**
     * Affichage de tous les facteurs
     */
    private boolean mAllFactors = true;

    /** The list of the associated tags */
    private Collection mTags;

    /** The Tag that will be added */
    private String mTagSupp;
    
    /** The Tag that will be deleted */
    private String mTagDel;

    /**
     * @return la liste des résultats
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des résultats
     */
    public void setList( List pList )
    {
        mList = pList;
    }

    /**
     * @return le kiviat
     */
    public GraphMaker getKiviat()
    {
        return kiviat;
    }

    /**
     * @return le pieChart
     */
    public GraphMaker getPieChart()
    {
        return pieChart;
    }

    /**
     * @param pKiviat le nouveau kiviat
     */
    public void setKiviat( GraphMaker pKiviat )
    {
        kiviat = pKiviat;
    }

    /**
     * @param pPieChart le nouveau pieChart
     */
    public void setPieChart( GraphMaker pPieChart )
    {
        pieChart = pPieChart;
    }

    /**
     * @return la valeur du display check box factors
     */
    public Boolean getDisplayCheckBoxFactors()
    {
        return mDisplayCheckBoxFactors;
    }

    /**
     * @param pDisplayCheckBoxFactors La valeur pour savoir si on doit afficher la checkbox
     */
    public void setDisplayCheckBoxFactors( Boolean pDisplayCheckBoxFactors )
    {
        mDisplayCheckBoxFactors = pDisplayCheckBoxFactors;
    }

    /**
     * @return si on veut faire apparaitre tous les facteurs du kiviat ou juste les non nuls
     */
    public boolean isAllFactors()
    {
        return mAllFactors;
    }

    /**
     * @param pAllFactors indique si le kiviat doit faire apparaitre tous les facteurs ou juste les non nuls
     */
    public void setAllFactors( boolean pAllFactors )
    {
        mAllFactors = pAllFactors;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        super.reset( mapping, request );
        // Reinitialisation du checkbox
        mAllFactors = false;
    }

    /**
     * Assigner la valeur de l'attribut mTags
     * @param pTags la liste de tags à assigner
     */
    public void setTags( Collection pTags )
    {
        mTags = pTags;
    }

    /**
     * Récupère la valeur de l'attribut mTags
     * @return La collection de Tags liés au composant en cours
     */
    public Collection getTags()
    {
        return mTags;
    }

    /**
     * sets the value of the mTagSupp property
     * @param pTagSupp the Tag to add
     */
    public void setTagSupp( String pTagSupp )
    {
        mTagSupp = pTagSupp;
    }

    /**
     * Retrieves the value of the mTagSupp property
     * @return The tag that will be added to the current component
     */
    public String getTagSupp()
    {
        return mTagSupp;
    }
    
    /**
     * sets the value of the mTagDel property
     * @param pTagDel the Tag to delete
     */
    public void setTagDel( String pTagDel )
    {
        mTagDel = pTagDel;
    }

    /**
     * Retrieves the value of the mTagDel property
     * @return The tag that will be deleted from the current component
     */
    public String getTagDel()
    {
        return mTagDel;
    }
}
