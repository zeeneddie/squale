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
public interface ICanvasRenderer
{

    /**
     * Le debut du contenu de canvas
     * 
     * @param event : event
     * @return debut du contenu de canvas
     */
    public String drawStart( String event );

    /**
     * le footer canvas
     * 
     * @param menuBuffer menu
     * @return le footer
     */
    public String drawFooter( String menuBuffer );

    /**
     * le fin du canvas
     * 
     * @return le fin du canvas
     */
    public String drawEnd();

    /**
     * le traceur
     * 
     * @param menuName nom du menu
     * @param itemName nom de l'item
     * @return le traceur
     */
    public String drawTraceur( String menuName, String itemName );

}
