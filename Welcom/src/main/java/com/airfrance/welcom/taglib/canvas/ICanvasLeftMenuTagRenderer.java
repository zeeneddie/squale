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
package com.airfrance.welcom.taglib.canvas;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ICanvasLeftMenuTagRenderer
{

    /**
     * Le debut du contenu de canvas gauche
     * 
     * @return debut du contenu de canvas gauche
     */
    public abstract String drawStart();

    /**
     * le fin du canvas gauche
     * 
     * @param body body
     * @param width taille
     * @param containsBouton si contient des boutons
     * @param containsMenu si contien un menu
     * @return fin du canvas gauche
     */
    public abstract String drawEnd( String body, int width, boolean containsMenu, boolean containsBouton );

}
