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
 * Créé le 13 sept. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.menu;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 */
public interface IMenuRender
{

    /** The RecursiceNumber */
    public static final int RECUSIVE_MAGIC_NUMBER = 100;

    /**
     * @param menu le menu
     * @param level le niveau
     * @return le resultat du print
     */
    public String doPrintBase( JSMenu menu, int level );

    /**
     * @param menuItem le menuItem
     * @param parent le parent
     * @param menuName le nom du menu
     * @param level le niveau
     * @param tab le tab
     * @return le js resultant associe
     */
    public String doPrint( JSMenuItem menuItem, JSMenuBase parent, String menuName, int level, int tab );

    /**
     * @param tag le tag
     * @param pageContext le pageContext
     * @return le header
     * @throws JspException exception pouvant etre levee
     */
    public String doPrintHeader( Tag tag, PageContext pageContext )
        throws JspException;

    /**
     * @return le footer
     */
    public String doPrintFooter();

    /**
     * @param action l'action
     * @return l'action
     */
    public String getAction( String action );
}
