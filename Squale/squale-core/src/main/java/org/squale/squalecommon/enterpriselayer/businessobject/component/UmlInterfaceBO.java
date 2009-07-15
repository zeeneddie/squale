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
 * Interface au sens UML
 * 
 * @hibernate.subclass lazy="true" discriminator-value="UmlInterface"
 */
public class UmlInterfaceBO
    extends AbstractComponentBO
{
    /**
     * Constructeur par défaut.
     */
    public UmlInterfaceBO()
    {
        super();
    }

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     */
    public UmlInterfaceBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pParent Composant parent
     * @throws UnexpectedRelationException si la relation ne peut etre ajouté
     */
    public UmlInterfaceBO( final String pName, AbstractComplexComponentBO pParent )
        throws UnexpectedRelationException
    {
        super( pName, pParent );
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
