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

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.util.graph.GraphMaker;
import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * Contient les listes de résultats d'un projet
 * 
 * @author M400842
 */
public class ProjectSummaryForm
    extends RootForm
{

    /**
     * Notes des facteurs sur le projet
     */
    private ProjectFactorsForm mFactors = new ProjectFactorsForm();

    /**
     * Volumétrie du projet
     */
    private ResultListForm mVolumetry = new ResultListForm();

    /**
     * booléen indiquant si le projet a provoqué des erreurs
     */
    private Boolean mHaveErrors = new Boolean( false );

    /**
     * Affichage de tous les facteurs
     */
    private boolean mAllFactors = true;

    /** Indique si le projet peut être exporter sous Eclipse */
    private Boolean mExportIDE = new Boolean( true );

    /**
     * booléen conditionnant l'affichage de la case à cocher "tous les facteurs"
     */
    private Boolean mDisplayCheckBoxFactors = new Boolean( true );

    /** le kiviat de niveau projet */
    private GraphMaker kiviat;

    /** le kiviat de niveau projet */
    private GraphMaker barGraph;

    /** le bubble de niveau projet */
    private GraphMaker histoBarGraph;

    /** Les résultats du projet */
    private WActionForm results;

    /** La liste de Tags */
    private Collection mTags;

    /** La liste de Tags de l'application parente*/
    private Collection mTagsAppli;

    /** The tag that will be added */
    private String mTagSupp;
    
    /** The tag that will be removed */
    private String mTagDel;
    
    /** The tag that will be removed from the application*/
    private String mTagDelAppli;

    /**
     * @return le kiviat
     */
    public GraphMaker getKiviat()
    {
        return kiviat;
    }

    /**
     * @param pKiviat le nouveau kiviat
     */
    public void setKiviat( GraphMaker pKiviat )
    {
        kiviat = pKiviat;
    }

    /**
     * @return le barGraph
     */
    public GraphMaker getBarGraph()
    {
        return barGraph;
    }

    /**
     * @param pBarGraph le nouveau BarGraph
     */
    public void setBarGraph( GraphMaker pBarGraph )
    {
        barGraph = pBarGraph;
    }

    /**
     * @return le Graphe en barre pour des pas de 0,1
     */
    public GraphMaker getHistoBarGraph()
    {
        return histoBarGraph;
    }

    /**
     * @param pHisto le nouveau graph pour des pas de 0,1
     */
    public void setHistoBarGraph( GraphMaker pHisto )
    {
        histoBarGraph = pHisto;
    }

    /**
     * @return les facteurs
     */
    public ProjectFactorsForm getFactors()
    {
        return mFactors;
    }

    /**
     * @return la volumétrie
     */
    public ResultListForm getVolumetry()
    {
        return mVolumetry;
    }

    /**
     * @param pFactors les notes des facteurs
     */
    public void setFactors( ProjectFactorsForm pFactors )
    {
        mFactors = pFactors;
    }

    /**
     * @param pVolumetry volumétrie
     */
    public void setVolumetry( ResultListForm pVolumetry )
    {
        mVolumetry = pVolumetry;

    }

    /**
     * @return mHaveErrors
     */
    public Boolean getHaveErrors()
    {
        return mHaveErrors;
    }

    /**
     * @param pHaveErrors la nouvelle valeur
     */
    public void setHaveErrors( Boolean pHaveErrors )
    {
        mHaveErrors = pHaveErrors;
    }

    /**
     * @param pAllFactors affichage de tous les facteurs
     */
    public void setAllFactors( boolean pAllFactors )
    {
        mAllFactors = pAllFactors;
    }

    /**
     * @return true si tous les facteurs sont affichés
     */
    public boolean isAllFactors()
    {
        return mAllFactors;
    }

    /**
     * @return mDisplayCheckBoxFactors affichage de la case à cocher "Tous les facteurs"
     */
    public Boolean getDisplayCheckBoxFactors()
    {
        return mDisplayCheckBoxFactors;
    }

    /**
     * @param pDisplayCheckBoxFactors affichage de la case à cocher "Tous les facteurs"
     */
    public void setDisplayCheckBoxFactors( Boolean pDisplayCheckBoxFactors )
    {
        mDisplayCheckBoxFactors = pDisplayCheckBoxFactors;
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
     * @return les résultats
     */
    public WActionForm getResults()
    {
        return results;
    }

    /**
     * @param pForm les résultats
     */
    public void setResults( WActionForm pForm )
    {
        results = pForm;
    }

    /**
     * @return true si le projet peut être exporter sous eclipse
     */
    public Boolean getExportIDE()
    {
        return mExportIDE;
    }

    /**
     * @param pExportIDE true si le projet peut être exporter sous eclipse
     */
    public void setExportIDE( Boolean pExportIDE )
    {
        mExportIDE = pExportIDE;
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
     * Assigner la valeur de l'attribut mTagsAppli
     * @param pTagsAppli la liste de tags à assigner
     */
    public void setTagsAppli( Collection pTagsAppli )
    {
        mTagsAppli = pTagsAppli;
    }

    /**
     * Récupère la valeur de l'attribut mTagsAppli
     * @return La collection de Tags liés au composant en cours
     */
    public Collection getTagsAppli()
    {
        return mTagsAppli;
    }

    /**
     * Assign the value for the mTagSupp property
     * @param pTagSupp the tag to add
     */
    public void setTagSupp( String pTagSupp )
    {
        mTagSupp = pTagSupp;
    }

    /**
     * Retrieves the value of the mTagSupp property
     * @return The tag added to the current component
     */
    public String getTagSupp()
    {
        return mTagSupp;
    }
    
    /**
     * Assign the value for the mTagDel property
     * @param pTagDel the tag to remove
     */
    public void setTagDel( String pTagDel )
    {
        mTagDel = pTagDel;
    }

    /**
     * Retrieves the value of the mTagDel property
     * @return The tag removed from the current component
     */
    public String getTagDel()
    {
        return mTagDel;
    }
    
    /**
     * Assign the value for the mTagDelAppli property
     * @param pTagDelAppli the tag to remove from the current application
     */
    public void setTagDelAppli( String pTagDelAppli )
    {
        mTagDelAppli = pTagDelAppli;
    }

    /**
     * Retrieves the value of the mTagDelAppli property
     * @return The tag removed from the current application
     */
    public String getTagDelAppli()
    {
        return mTagDelAppli;
    }
}
