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
package org.squale.squalecommon.enterpriselayer.businessobject.component;

import org.squale.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;

/**
 * Représente une méthode au sens Java et C++
 * 
 * @hibernate.subclass lazy="true" discriminator-value="Method"
 */
public class MethodBO
    extends AbstractComponentBO
{

    /**
     * Chemin complet du fichier à partir du projet
     */
    private String mLongFileName;

    /**
     * Le numéro de ligne de la méthode dans le fichier
     */
    private int mStartLine;

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     */
    public MethodBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Constructeur par défaut.
     */
    public MethodBO()
    {
        super();
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pParent Composant parent
     * @throws UnexpectedRelationException si la relation ne peut etre ajoutée
     */
    public MethodBO( String pName, AbstractComplexComponentBO pParent )
        throws UnexpectedRelationException
    {
        super( pName, pParent );
    }

    /**
     * @return le chemin complet du fichier au projet
     * @hibernate.property name="longFileName" column="LongFileName" type="string" length="2048" not-null="false"
     *                     unique="false" update="true" insert="true"
     */
    public String getLongFileName()
    {
        return mLongFileName;
    }

    /**
     * @return le numéro de ligne de la méthode dans le fichier
     * @hibernate.property name="startLine" column="StartLine" type="integer" length="10" not-null="false"
     *                     unique="false" update="true" insert="true"
     */
    public int getStartLine()
    {
        return mStartLine;
    }

    /**
     * @param pLongFileName le nouveau chemin
     */
    public void setLongFileName( String pLongFileName )
    {
        mLongFileName = pLongFileName;
    }

    /**
     * @param pLine le numéro de ligne de la méthode dans le fichier
     */
    public void setStartLine( int pLine )
    {
        mStartLine = pLine;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO#accept(org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor,
     *      java.lang.Object)
     */
    public Object accept( ComponentVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

}
