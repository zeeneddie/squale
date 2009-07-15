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

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Form bean for a Struts application.
 * 
 * @version 1.0
 * @author
 */
public class ApplicationRightsForm
    extends RootForm
{
    /**
     * Matricule de l'utilisateur
     */
    private String[] mMatricule = null;

    /**
     * Profil associé de l'utilisateur
     */
    private String[] mRightProfile = null;

    /**
     * @return le matricule
     */
    public String[] getMatricule()
    {
        return mMatricule;
    }

    /**
     * @return le profil
     */
    public String[] getRightProfile()
    {
        return mRightProfile;
    }

    /**
     * @param pMatricule le matricule
     */
    public void setMatricule( String[] pMatricule )
    {
        mMatricule = pMatricule;
    }

    /**
     * @param pRightProfile le profil
     */
    public void setRightProfile( String[] pRightProfile )
    {
        mRightProfile = pRightProfile;
    }

}
