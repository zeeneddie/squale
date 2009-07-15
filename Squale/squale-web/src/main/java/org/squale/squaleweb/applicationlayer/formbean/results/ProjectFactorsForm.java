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

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient les notes des facteurs d'un projet, ainsi que des renseignements sur son parent.
 */
public class ProjectFactorsForm
    extends RootForm
{

    /**
     * ID du projet
     */
    private String mId = "";

    /**
     * Nom du projet
     */
    private String mName = "";

    /**
     * ID de l'application
     */
    private String mAppliId = "";

    /**
     * Nom de l'application
     */
    private String mApplicationName = "";

    /** Liste des facteurs */
    private Collection mFactors = new ArrayList();

    /**
     * Attribute not in use Attribute needed for prevent an error in the console which happen with the tag af:col
     * Example of this case in homepage/resultbyGrid.jsp
     */
    private String value;

    /**
     * Constructeur.
     */
    public ProjectFactorsForm()
    {
    }

    /**
     * Constructeur.
     * 
     * @param pComponent le composant DTO correspondant au projet.
     */
    public ProjectFactorsForm( final ComponentDTO pComponent )
    {
        mName = pComponent.getName();
        mId = Long.toString( pComponent.getID() );
        mAppliId = Long.toString( pComponent.getIDParent() );
    }

    /**
     * @return l'id du projet
     */
    public String getId()
    {
        return mId;
    }

    /**
     * @return le nom du projet
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return l'id de l'application parente
     */
    public String getAppliId()
    {
        return mAppliId;
    }

    /**
     * @return le nom de l'application parente
     */
    public String getApplicationName()
    {
        return mApplicationName;
    }

    /**
     * @param pId l'id du projet
     */
    public void setId( String pId )
    {
        mId = pId;
    }

    /**
     * @param pName le nom du projet
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pApplicationId l'id de l'application parente
     */
    public void setAppliId( String pApplicationId )
    {
        mAppliId = pApplicationId;
    }

    /**
     * @param pApplicationName nom de l'application
     */
    public void setApplicationName( String pApplicationName )
    {
        mApplicationName = pApplicationName;
    }

    /**
     * @return facteurs
     */
    public Collection getFactors()
    {
        return mFactors;
    }

    /**
     * @param pList facteurs
     */
    public void setFactors( Collection pList )
    {
        mFactors = pList;
    }

    /**
     * Getter method for the attribute value
     * 
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Setter method for the attribute value
     * 
     * @param mValue The new value
     */
    public void setValue( String mValue )
    {

        value = mValue;
    }
}
