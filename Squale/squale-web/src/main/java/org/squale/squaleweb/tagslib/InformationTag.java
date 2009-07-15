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
package org.squale.squaleweb.tagslib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import org.squale.squaleweb.resources.WebMessages;

/**
 * pour tout ce qui concerne les infos sur les différent(e)s facteurs/critères/pratiques
 */
public class InformationTag
    extends TagSupport
{

    /** le nom permettant de définir la variable utilisée dans le tag */
    private String name;

    /** le nom de la pratique */
    private String practiceName;

    /**
     * l'id de l'élément qualimétrique
     */
    private String ruleId;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        ResponseUtils.write( pageContext, makeResult( (String) RequestUtils.lookup( pageContext, name, practiceName,
                                                                                    null ),
                                                      (String) RequestUtils.lookup( pageContext, name, ruleId, null ),
                                                      (HttpServletRequest) pageContext.getRequest() ) );
        return SKIP_BODY;
    }

    /**
     * @return le code pour l'image permettant d'afficher les informations sur le facteur/critère/pratique
     * @param practiceKey le nom (sous forme de clé) de la pratique
     * @param pRequest la requete http
     * @param ruleIdValue l'id de la règle
     */
    private String makeResult( String practiceKey, String ruleIdValue, HttpServletRequest pRequest )
    {
        String realName = WebMessages.getString( pRequest, practiceKey );
        // determine si un identifiant a été indiqué
        // si oui on le prend sinon on utilise le nom comme repère
        String pictureHelp =
            ( WebMessages.getString( pRequest, "project.results.factors.information" ) + ": " + realName ).replaceAll(
                                                                                                                       "\"",
                                                                                                                       "" );
        // il ne faut pas passer le nom réel mais le nom sous forme de clé (rule.xxx ....)
        String result =
            "<a href=\"javascript:display_popup('information.do?action=retrieveInformation&elementName=" + practiceKey
                + "&ruleId=" + ruleIdValue + "','popup_1');\" class=\"nobottom\"> " + "<img src=\""
                + "images/pictos/icon_catalog.gif\" title=\""
                // pour échapper les quotes, il est necessaire de les remplacer par " \' "
                + pictureHelp.replaceAll( "'", "\\\\'" ) + "\" border=\"0\" /> </a>";
        return result;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doEndTag()
        throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * @return le nom utilisé dans le tag
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param newUsedName le nouveau nom
     */
    public void setName( String newUsedName )
    {
        name = newUsedName;
    }

    /**
     * @return l'id de l'élément qualimétrique
     */
    public String getRuleId()
    {
        return ruleId;
    }

    /**
     * @param newId le nouvel id du composant
     */
    public void setRuleId( String newId )
    {
        ruleId = newId;
    }

    /**
     * @return le nom de la pratique
     */
    public String getPracticeName()
    {
        return practiceName;
    }

    /**
     * @param newPracticeName le nouveau nom
     */
    public void setPracticeName( String newPracticeName )
    {
        practiceName = newPracticeName;
    }

}
