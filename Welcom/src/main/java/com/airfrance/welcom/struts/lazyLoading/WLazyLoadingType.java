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
 * Créé le 18 avr. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.lazyLoading;

import java.io.Serializable;

/**
 * @author M327837 Type de persitance du LazyLoading
 */
public class WLazyLoadingType
    implements Serializable
{

    /** ID pour la serialization */
    static final long serialVersionUID = -782162519563806393L;

    /** Constante */
    public final static WLazyLoadingType ONGLET = new WLazyLoadingType( "ONGLET" );

    /** Constante */
    public final static WLazyLoadingType COMBO = new WLazyLoadingType( "COMBO" );

    /** Constante */
    public final static WLazyLoadingType DROPDOWNPANEL = new WLazyLoadingType( "DROPDOWNPANEL" );

    /** Constante */
    public final static WLazyLoadingType ZONE = new WLazyLoadingType( "ZONE" );

    /** Type d'objet */
    private String s;

    /**
     * Constructeur pS
     * 
     * @param pS le type
     */
    private WLazyLoadingType( final String pS )
    {
        s = pS;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return s;
    }
}