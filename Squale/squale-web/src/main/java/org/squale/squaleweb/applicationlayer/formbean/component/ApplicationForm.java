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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Contient les données indispensables relatives à une application
 * 
 * @author M400842
 */
public class ApplicationForm
    extends ActionIdFormSelectable
{

    /** l'éventuelle justification associée au composant */
    private String justification;

    /** un booléen permettant de savoir si le composant est à exclure du plan d'aciton */
    private boolean excludedFromActionPlan;

    /** indique si le composant a des résultats */
    private boolean mHasResults;

    /** Date de la dernière modification */
    private Date mLastUpdate;

    /** L'utilisateur ayant fait la dernière modification */
    private String mLastUser;

    /**
     * @return true si le composant est exclu du plan d'action
     */
    public boolean getExcludedFromActionPlan()
    {
        return excludedFromActionPlan;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest A implémenter sinon on ne peut pas décocher la checkBox
     * @param mapping le mapping
     * @param request la requête
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        super.reset( mapping, request );
        // Reinitialisation du checkbox
        excludedFromActionPlan = false;
    }

    /**
     * @return la justification du composant
     */
    public String getJustification()
    {
        return justification;
    }

    /**
     * @param pExcluded le booléen indiquant si il faut exclure le composant ou pas
     */
    public void setExcludedFromActionPlan( boolean pExcluded )
    {
        excludedFromActionPlan = pExcluded;
    }

    /**
     * @param pJustification la nouvelle valeur de la justification
     */
    public void setJustification( String pJustification )
    {
        justification = pJustification;
    }

    /**
     * Redefinition of the hashCode method {@inheritDoc}
     * 
     * @return return the hash number of the object
     */
    public int hashCode()
    {
        return super.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj l'objet à comparer
     * @return true si obj=this, false sinon
     */
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj instanceof ApplicationForm )
        {
            ApplicationForm compare = (ApplicationForm) obj;
            if ( null != this.getApplicationName() )
            {
                result = this.getApplicationName().equals( compare.getApplicationName() );
            }
        }
        return result;
    }

    /**
     * Access method for the mHasResults property.
     * 
     * @return true si le composant a des résultats
     */
    public boolean getHasResults()
    {
        return mHasResults;
    }

    /**
     * Sets the value of the mHasResults property.
     * 
     * @param pHasResults indique si le composant a des résultats
     */
    public void setHasResults( boolean pHasResults )
    {
        mHasResults = pHasResults;
    }

    /**
     * Access method for the mLastUpdate property.
     * 
     * @return la date de la dernière modification
     */
    public Date getLastUpdate()
    {
        return mLastUpdate;
    }

    /**
     * Sets the value of the mLastUpdate property.
     * 
     * @param pDate la date de la dernière modification
     */
    public void setLastUpdate( Date pDate )
    {
        mLastUpdate = pDate;
    }

    /**
     * Access method for the mLastUser property.
     * 
     * @return l'utilisateur ayant fait la dernière modification
     */
    public String getLastUser()
    {
        return mLastUser;
    }

    /**
     * Sets the value of the mLastUser property.
     * 
     * @param pMatricule l'utilisateur ayant fait la dernière modification
     */
    public void setLastUser( String pMatricule )
    {
        mLastUser = pMatricule;
    }
}
