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
package com.airfrance.squalecommon.enterpriselayer.businessobject.config.web;

/**
 * Configurations pour l'affichage Web dépendants des outils utilisés pour le calcul d'un audit
 * 
 * @hibernate.class table="displayConf" mutable="true" lazy="true" discriminator-value="AbstractDisplayConfBO"
 * @hibernate.discriminator column="subclass"
 */
public abstract class AbstractDisplayConfBO
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /**
     * Méthode d'accès pour mId
     * 
     * @return la l'identifiant (au sens technique) de l'objet
     * @hibernate.id generator-class="native" type="long" column="ConfId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="displayconf_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pVisitor visiteur
     * @param pArgument argument
     * @return objet
     */
    public abstract Object accept( DisplayConfVisitor pVisitor, Object pArgument );
}
