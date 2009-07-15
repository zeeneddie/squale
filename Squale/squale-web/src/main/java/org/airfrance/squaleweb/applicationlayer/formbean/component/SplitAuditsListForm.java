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
package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.util.graph.GraphMaker;

/**
 * Sépare les audits des applications publiques par rapport à celles qui appartiennent à l'utilisateur
 */
public class SplitAuditsListForm
    extends AuditListForm
{

    /**
     * Liste des audits sur les applications publiques
     */
    private List mPublicAudits = new ArrayList();

    /** le graph pour les stats sur la durée des audits terminés */
    private GraphMaker mTimeMaker;

    /** le graph pour les stats sur la taille occupée */
    private GraphMaker mSizeMaker;

    /**
     * @return la maker
     */
    public GraphMaker getTimeMaker()
    {
        return mTimeMaker;
    }

    /**
     * Change le maker
     * 
     * @param pMaker le nouveau maker
     */
    public void setTimeMaker( GraphMaker pMaker )
    {
        mTimeMaker = pMaker;
    }

    /**
     * @return la maker
     */
    public GraphMaker getSizeMaker()
    {
        return mSizeMaker;
    }

    /**
     * Change le maker
     * 
     * @param pMaker le nouveau maker
     */
    public void setSizeMaker( GraphMaker pMaker )
    {
        mSizeMaker = pMaker;
    }

    /**
     * @return la liste des audits publiques
     */
    public List getPublicAudits()
    {
        return mPublicAudits;
    }

    /**
     * @param pPublicAudits la liste des audits publiques
     */
    public void setPublicAudits( List pPublicAudits )
    {
        mPublicAudits = pPublicAudits;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        super.reset( mapping, request );
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // On valide tous les audits
        for ( int i = 0; i < getAudits().size(); i++ )
        {
            AuditForm current = (AuditForm) getAudits().get( i );
            // La date ne doit pas être nulle
            // Sauf dans le cas d'un audit de jalon programmé
            if ( null == current.getDate()
                && !( AuditBO.MILESTONE.equals( current.getType() ) && AuditBO.NOT_ATTEMPTED == current.getStatus() ) )
            {
                addError( "audits[" + i + "].date", new ActionError( "error.date_format",
                                                                     WebMessages.getString( pRequest,
                                                                                            "datetime.format.simple" ) ) );
            }
        }
    }

}
