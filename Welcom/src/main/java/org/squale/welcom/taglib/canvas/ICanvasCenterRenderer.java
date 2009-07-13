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
package org.squale.welcom.taglib.canvas;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ICanvasCenterRenderer
{

    /**
     * debut du canvas center
     * 
     * @param hasCanvasLeft si posséde un canvas gauche
     * @return le debuit du canvas
     */
    public abstract String drawStart( boolean hasCanvasLeft );

    /**
     * debut du canvas center
     * 
     * @param soustitre soustitre
     * @param subTitleKey clef de sous titre
     * @param titre titre
     * @return le titre
     */
    public abstract String drawTitre( String titre, String subTitleKey, String soustitre );

    /**
     * le fin du canvas gauche
     * 
     * @return la fin du canvas
     */
    public abstract String drawEnd();

}
