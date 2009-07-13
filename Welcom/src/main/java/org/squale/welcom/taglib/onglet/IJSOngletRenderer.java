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
 * Créé le 22 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.onglet;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface IJSOngletRenderer
{

    /**
     * Rendu : pour l'ecriture de l'enpied d'un onglet
     * 
     * @param bottomValue Vleur de la barre du bas
     * @return rendu
     */
    public String drawTableBottom( String bottomValue );

    /**
     * Rendu : pour l'ecriture de l'enpied de table
     * 
     * @param titles titre
     * @return rendu
     */
    public String drawTitleBar( String titles );

    /**
     * Rendu : le titre d'un onglet
     * 
     * @param indice indice
     * @param name name
     * @param ongletSelected ongletselected
     * @param parentName nom du parent
     * @param titre titre
     * @param onClickAfterShow onClickAfterShow
     * @return rendu
     */
    public String drawTitle( final String name, final String titre, final String parentName, final int indice,
                             boolean ongletSelected, final String onClickAfterShow );

    /**
     * Rendu : le debut du corp de l'onglet
     * 
     * @param name : nom
     * @param ongletSelected ongletSelected
     * @param lazyLoading lazyLoading
     * @return rendu
     */
    public String drawBodyStart( String name, boolean ongletSelected, boolean lazyLoading );

    /**
     * Rendu : la fin du corps
     * 
     * @return rendu
     */
    public String drawBodyEnd();

}