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
package org.squale.squaleweb.applicationlayer.formbean.creation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Form bean for a Struts application.
 * 
 * @version 1.0
 * @author
 */
public class ConfigApplicationForm
    extends RootForm
{
    /**
     * Délai de purge par défaut
     */
    private static final int DEFAULT_PURGE_DELAY = 180;

    /**
     * Délai entre deux purges de l'application
     */
    private int mPurgeDelay = DEFAULT_PURGE_DELAY;

    /**
     * Liste des matricules
     */
    private String[] mMatricules = null;

    /**
     * Liste des profils associés
     */
    private int[] mProfiles = null;

    /**
     * Réinitialise le formulaire.
     * 
     * @param pMapping le mapping.
     * @param pRequest la requête.
     */
    public void reset( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        mPurgeDelay = DEFAULT_PURGE_DELAY;
        mMatricules = null;
        mProfiles = null;
    }

    /**
     * @return la liste des matricules
     */
    public String[] getMatricules()
    {
        return mMatricules;
    }

    /**
     * @return la liste des profils
     */
    public int[] getProfiles()
    {
        return mProfiles;
    }

    /**
     * @return le délai de purge
     */
    public int getPurgeDelay()
    {
        return mPurgeDelay;
    }

    /**
     * @param pMatricules la liste des matricules
     */
    public void setMatricules( String[] pMatricules )
    {
        mMatricules = pMatricules;
    }

    /**
     * @param pProfiles la liste des profils
     */
    public void setProfiles( int[] pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * @param pPurgeDelay le délai de purge (en jour)
     */
    public void setPurgeDelay( int pPurgeDelay )
    {
        mPurgeDelay = pPurgeDelay;
    }
}
