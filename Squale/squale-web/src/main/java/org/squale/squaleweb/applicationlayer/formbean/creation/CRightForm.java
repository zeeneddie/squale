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
/*
 * Créé le 11 août 05, par M400832.
 */
package org.squale.squaleweb.applicationlayer.formbean.creation;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400832
 * @version 1.0
 */
public class CRightForm
    extends RootForm
{

    /**
     * Matricule de l'utilisateur.
     */
    private String mMatricule = null;

    /**
     * Profil de l'utilisateur.
     */
    private String mProfile = null;

    /**
     * @return le matricule de l'utilisateur.
     */
    public String getMatricule()
    {
        return mMatricule;
    }

    /**
     * @return le profil de l'utilisateur.
     */
    public String getProfile()
    {
        return mProfile;
    }

    /**
     * @param pMatricule le matricule de l'utilisateur.
     */
    public void setMatricule( String pMatricule )
    {
        mMatricule = pMatricule;
    }

    /**
     * @param pProfile le profil de l'utilisateur.
     */
    public void setProfile( String pProfile )
    {
        mProfile = pProfile;
    }

}
