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

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour les items de transgression.
 */
public class RuleCheckingItemForm
    extends RootForm
{

    /** L'id du composant concerné par la transgression */
    private long mComponentId = -1;

    /** Nom du composant concerné par la transgression */
    private String mComponentName = "-";

    /** L'id du composant en relation avec la transgression */
    private long mComponentInvolvedId = -1;

    /** Nom du composant en relation avec la transgression */
    private String mComponentInvolvedName = "-";

    /** Détail de la transgression */
    private String mMessage;

    /**
     * @return l'id du composant concerné par la transgression
     */
    public long getComponentId()
    {
        return mComponentId;
    }

    /**
     * @return le nom du composant concerné par la transgression
     */
    public String getComponentName()
    {
        return mComponentName;
    }

    /**
     * @return l'id du composant en relation avec la transgression
     */
    public long getComponentInvolvedId()
    {
        return mComponentInvolvedId;
    }

    /**
     * @return le nom du composant en relation avec la transgression
     */
    public String getComponentInvolvedName()
    {
        return mComponentInvolvedName;
    }

    /**
     * @return le détail de la transgression
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * @param pComponentName le nom du composant concerné par la transgression
     */
    public void setComponentName( String pComponentName )
    {
        mComponentName = pComponentName;
    }

    /**
     * @param pComponentId l'id du composant concerné par la transgression
     */
    public void setComponentId( long pComponentId )
    {
        mComponentId = pComponentId;
    }

    /**
     * @param pComponentInvolvedId l'id du composant en relation avec la transgression
     */
    public void setComponentInvolvedId( long pComponentInvolvedId )
    {
        mComponentInvolvedId = pComponentInvolvedId;
    }

    /**
     * @param pComponentInvolvedName le nom du composant en relation avec la transgression
     */
    public void setComponentInvolvedName( String pComponentInvolvedName )
    {
        mComponentInvolvedName = pComponentInvolvedName;
    }

    /**
     * @param pMessage le détail de la transgression
     */
    public void setMessage( String pMessage )
    {
        mMessage = pMessage;
    }
}
