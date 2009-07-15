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
 * Représente une page JSP
 * 
 * @hibernate.subclass lazy="true" discriminator-value="Jsp"
 */
public class JspBO
    extends AbstractComponentBO
{

    /**
     * Chemin du fichier à partir du projet
     */
    private String mFileName;

    /**
     * Instancie un nouveau composant JSP.
     * 
     * @param pName Nom de la page JSP.
     */
    public JspBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Constructeur par défaut.
     */
    public JspBO()
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
    public JspBO( String pName, AbstractComplexComponentBO pParent )
        throws UnexpectedRelationException
    {
        super( pName, pParent );
    }

    /**
     * @return le chemin du fichier à partir du projet
     * @hibernate.property name="fileName" column="LongFileName" type="string" length="2048" not-null="false"
     *                     unique="false" update="true" insert="true"
     */
    public String getFileName()
    {
        return mFileName;
    }

    /**
     * @param pFileName le nouveau chemin du fichier
     */
    public void setFileName( String pFileName )
    {
        mFileName = pFileName;
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
