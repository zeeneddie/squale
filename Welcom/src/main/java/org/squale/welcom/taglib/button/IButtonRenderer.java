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
package org.squale.welcom.taglib.button;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface IButtonRenderer
{

    /**
     * Rendu : pour un lien (rendu par defaut)
     * 
     * @param pageContext : Contexte
     * @param parentTag : tag parent (recherche si boutonbar)
     * @param name : nom du tag
     * @param rawValue : valeur du texte du bouton
     * @param target : cible
     * @param onclick : onclick
     * @param toolTip : bulle d'aide
     * @param styleId : styleId
     * @return render
     */
    public String drawRenderHRefTag( PageContext pageContext, Tag parentTag, String name, String rawValue,
                                     String target, String href, String onclick, String toolTip, String styleId );

    /**
     * Rendu : pour un menu (dans le canvas left)
     * 
     * @param pageContext : Contexte
     * @param parentTag : tag parent (recherche si boutonbar)
     * @param name : nom du tag
     * @param rawValue : valeur du texte du bouton
     * @param target : cible
     * @param onclick : onclick
     * @param toolTip : bulle d'aide
     * @param styleId : styleId
     * @return render
     */
    public String drawRenderMenuHRefTag( PageContext pageContext, Tag parentTag, String name, String rawValue,
                                         String target, String href, String onclick, String toolTip, String styleId );

    /**
     * Rendu : pour un form, dans une ButtonBar, pas de validation
     * 
     * @param pageContext : Contexte
     * @param parentTag : tag parent (recherche si boutonbar)
     * @param name : nom du tag
     * @param rawValue : valeur du texte du bouton
     * @param target : cible
     * @param onclick : onclick
     * @param toolTip : bulle d'aide
     * @param styleId : styleId
     * @return render
     */
    public String drawRenderFormHRefTag( PageContext pageContext, Tag parentTag, String name, String rawValue,
                                         String target, String onclick, String toolTip, String styleId );

    /**
     * Rendu : pour un form, dans une ButtonBar, avec validation
     * 
     * @param pageContext : Contexte
     * @param parentTag : tag parent (recherche si boutonbar)
     * @param name : nom du tag
     * @param rawValue : valeur du texte du bouton
     * @param target : cible
     * @param onclick : onclick
     * @param toolTip : bulle d'aide
     * @param styleId : styleId
     * @return render
     */
    public String drawRenderFormInputTag( PageContext pageContext, Tag parentTag, String name, String rawValue,
                                          String target, String onclick, String toolTip, String styleId );

}