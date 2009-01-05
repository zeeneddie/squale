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
 * Créé le 18 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell.bean;

import org.apache.struts.action.ActionForm;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellCheckerBean
    extends ActionForm
{

    /**
     * 
     */
    private static final long serialVersionUID = -7590830545513474300L;

    /** Liste des champs pour la verification othogrpahique */
    private WSpellFields fields = new WSpellFields();

    /**
     * @return accesseur
     */
    public WSpellFields getFields()
    {
        return fields;
    }

    /**
     * @param pFields accesseur
     */
    public void setFields( final WSpellFields pFields )
    {
        this.fields = pFields;
    }

}
