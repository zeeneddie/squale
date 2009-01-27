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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.util.graph.GraphMaker;

/**
 * Contient une liste de r�sultats
 * 
 * @author M400842
 */
public class ResultListForm
    extends RootForm
{

    /**
     * Liste des r�sultats
     */
    private List mList = new ArrayList();

    /** le kiviat de niveau application */
    private GraphMaker kiviat;

    /** le pieChart de niveau application */
    private GraphMaker pieChart;

    /**
     * bool�en conditionnant l'affichage de la case � cocher "tous les facteurs"
     */
    private Boolean mDisplayCheckBoxFactors = new Boolean( true );

    /**
     * Affichage de tous les facteurs
     */
    private boolean mAllFactors = true;
    
    /**
     * @return la liste des r�sultats
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des r�sultats
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
}
