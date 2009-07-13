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
 * Créé le 25 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.transformer;

import org.squale.welcom.struts.bean.WActionForm;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface WITransformer
{
    /**
     * Transforme un object ou une collection de la couche metier a la couche presentation
     * 
     * @param object object de la couche metier (BO/DTO ou collection de BO/DTO)
     * @return object de la couche presentation
     * @throws WTransformerException Probleme a la transformation
     */
    public WActionForm objToForm( Object object[] )
        throws WTransformerException;

    /**
     * Transforme un object ou une collection de la couche metier a la couche presentation
     * 
     * @param object object de la couche metier (BO/DTO ou collection de BO/DTO)
     * @param form object de la couche presentation
     * @throws WTransformerException Probleme a la transformation
     */
    public void objToForm( Object object[], WActionForm form )
        throws WTransformerException;

    /**
     * Transforme un object ou une collection de la couche presentation a la couche metier
     * 
     * @param form object de la couche presentation
     * @return object de la couche metier (BO/DTO ou collection de BO/DTO)
     * @throws WTransformerException Probleme a la transformation
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException;

    /**
     * Transforme un object ou une collection de la couche presentation a la couche metier
     * 
     * @param form object de la couche presentation
     * @param object object de la couche metier (BO/DTO ou collection de BO/DTO)
     * @throws WTransformerException Probleme a la transformation
     */
    public void formToObj( WActionForm form, Object object[] )
        throws WTransformerException;
}