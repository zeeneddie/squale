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
package com.airfrance.squaleweb.tagslib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.squalecommon.datatransfertobject.tag.TagDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Construit le début de la page de résultat d'une/un application/projet
 */
public class ResultsHeaderTag
    extends TagSupport
{
    /** Page attribute to tell if the application has tags or not */
    static final String APPLICATION_HAS_TAGS = "app_has_tags";

    /** Page attribute to tell if the project has tags or not */
    static final String PROJECT_HAS_TAGS = "proj_has_tags";

    /** Le nom du formulaire */
    private String mName;

    /**
     * Indique si il faut afficher l'information sur la compararaison des audits La comparaison entre audits n'est pas
     * possible si la grille sur le projet n'est pas là même (i.e. pas le même id en base) ou si les audits n'ont pas
     * été fait sur les même projets.
     */
    private boolean mDisplayComparable;

    /**
     * doStartTag
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag() {@inheritDoc}
     * @return EVAL_BODY_INCLUDE
     * @throws JspException si une erreur apparait
     */
    @Override
    public int doStartTag()
        throws JspException
    {
        // On récupère la requête pour la locale
        Locale locale = pageContext.getRequest().getLocale();
        // Début du blockquote
        StringBuffer blockquote = new StringBuffer( "<div id =\"resultheader\">" );
        // On récupère les différents attributs
        String appliId = (String) RequestUtils.lookup( pageContext, mName, "applicationId", null );
        String projectId = (String) RequestUtils.lookup( pageContext, mName, "projectId", null );
        String auditId = (String) RequestUtils.lookup( pageContext, mName, "currentAuditId", null );
        String previousAuditId = (String) RequestUtils.lookup( pageContext, mName, "previousAuditId", null );
        Collection<TagDTO> tagsApplication = getTagsComponent( ComponentType.APPLICATION );
        if ( tagsApplication != null )
        {
            pageContext.setAttribute( APPLICATION_HAS_TAGS, !tagsApplication.isEmpty() );
        }
        Collection<TagDTO> tagsProjet = getTagsComponent( ComponentType.PROJECT );
        if ( tagsProjet != null )
        {
            pageContext.setAttribute( PROJECT_HAS_TAGS, !tagsProjet.isEmpty() );
        }

        String paramsLink = "&currentAuditId=" + auditId + "&previousAuditId=" + previousAuditId;
        String[] param = new String[1];
        // le nom de l'application :
        // lien comme si on venait du menu application->"nom de l'application"
        param[0] =
            "<a href='application.do?action=summary&applicationId=" + appliId + "'>"
                + (String) RequestUtils.lookup( pageContext, mName, "applicationName", null ) + "</a>";
        // On affiche le nom de l'application en gras

        blockquote.append( "<div id=\"components\">" );
        blockquote.append( "<div class=\"component\">" );
        blockquote.append( "<div class=\"applicationName\"><b>" );
        blockquote.append( WebMessages.getString( locale, "description.name.application", param ) );
        // fin de la div "applicationName"
        blockquote.append( "</b></div>" );

        blockquote.append( componentTags( ComponentType.APPLICATION, tagsApplication, locale ) );
        // fin de la div "component"
        blockquote.append( "</div>" );
        // Le nom du projet
        param[0] = (String) RequestUtils.lookup( pageContext, mName, "projectName", null );
        // On affiche le nom du projet si l'attribut est défini
        // lien comme si on venait du menu_application->projets->"nom du projet"
        if ( null != param[0] && param[0].length() > 0 )
        {
            param[0] =
                "<a href='project.do?action=select&projectId=" + projectId + paramsLink + "'>" + param[0] + "</a>";
            blockquote.append( "<div class=\"component\">" );
            blockquote.append( "<div class=\"projectName\"><b>" );
            blockquote.append( WebMessages.getString( locale, "description.name.project", param ) );
            // fin de la div "componentprojectName"
            blockquote.append( "</b></div>" );
        }

        if ( !"".equals( (String) RequestUtils.lookup( pageContext, mName, "projectId", null ) ) )
        {
            blockquote.append( componentTags( ComponentType.PROJECT, tagsProjet, locale ) );
        }
        if ( null != param[0] && param[0].length() > 0 )
        {
            // fin de la div "component"
            blockquote.append( "</div>" );
        }

        // fin de la div "components"
        blockquote.append( "</div>" );

        // On écrit le début du block
        ResponseUtils.write( pageContext, blockquote.toString() );
        return EVAL_BODY_INCLUDE;
    }

    /**
     * method that will generate the code to associate and remove tags to the current component
     * 
     * @param pType the type of the wanted component
     * @param pTags the component's tag collection
     * @param pLocale the contexte of the page
     * @return The string with the code
     * @throws JspException if an error occurs in the retrieval of the parameter from the request
     */
    private String componentTags( String pType, Collection<TagDTO> pTags, Locale pLocale )
        throws JspException
    {
        StringBuffer retour = new StringBuffer();

        if ( pTags != null && pTags.size() > 0 )
        {
            String[] param = new String[] { writeTags( pType, pTags, ";" ) };
            retour.append( "<div class=\"tags\"><b>" );
            retour.append( WebMessages.getString( pLocale, "description.name.tag", param ) );
            retour.append( "</b></div>" );
        }

        if ( isTaggingEnabled() )
        {
            String buttonType = pType.equals( ComponentType.APPLICATION ) ? "app" : "proj";
            retour.append( "<div id=\"tag-button-" + buttonType + "\">" );
            // The plus button that will show the field to add tags
            retour.append( plusButton( pType ) );
            // The minus button that will show the field to delete tags
            retour.append( minusButton( pType ) );
            retour.append( "</div>" );
        }

        return retour.toString();
    }

    /**
     * Method that will generate the code to show the "plus" Button
     * 
     * @param pType the type of the wanted component
     * @return The string with the code to show the "plus" button
     */
    private String plusButton( String pType )
    {
        StringBuffer retour = new StringBuffer();

        String idPlus = "tagPlus";
        if ( pType.compareTo( ComponentType.APPLICATION ) == 0 )
        {
            idPlus = idPlus + "App";
        }
        else if ( pType.compareTo( ComponentType.PROJECT ) == 0 )
        {
            idPlus = idPlus + "Pro";
        }

        retour.append( "<div id=\"" + idPlus + "\" class=\"addTag\">" );
        if ( pType.compareTo( ComponentType.APPLICATION ) == 0 )
        {
            // The "+" button on the application line calls the following javascript actions
            // shows the div displaying the textField to add a tag
            retour.append( "<a href=\"javascript:addAppTagButtonClicked();\"><img src=\"theme/charte_v03_001/img/ico/enabled/plus.gif\" title=\"Add a tag\"> </a>" );
        }
        else if ( pType.compareTo( ComponentType.PROJECT ) == 0 )
        {
            // The "+" button on the project line calls the following javascript actions
            // shows the div displaying the textField to add a tag
            retour.append( "<a href=\"javascript:addProjTagButtonClicked();\"><img src=\"theme/charte_v03_001/img/ico/enabled/plus.gif\" title=\"Add a tag\"> </a>" );
        }
        retour.append( "</div>" );

        return retour.toString();
    }

    /**
     * Method that will generate the code to show the "minus" Button
     * 
     * @param pType the type of the wanted component
     * @return The string with the code to show the "minus" button
     */
    private String minusButton( String pType )
    {
        StringBuffer retour = new StringBuffer();

        String idMinus = "tagMinus";
        if ( pType.compareTo( ComponentType.APPLICATION ) == 0 )
        {
            idMinus = idMinus + "App";
        }
        else if ( pType.compareTo( ComponentType.PROJECT ) == 0 )
        {
            idMinus = idMinus + "Pro";
        }

        retour.append( "<div id=\"" + idMinus + "\" class=\"delTag\">" );
        if ( pType.compareTo( ComponentType.APPLICATION ) == 0
            && Boolean.TRUE.equals( pageContext.getAttribute( APPLICATION_HAS_TAGS ) ) )
        {
            // The "+" button on the application line calls the following javascript actions
            // shows the div displaying the textField to add a tag
            retour.append( "<a href=\"javascript:delAppTagButtonClicked();\"><img src=\"theme/charte_v03_001/img/ico/enabled/minus.gif\" title=\"Delete a tag\"> </a>" );
        }
        else if ( pType.compareTo( ComponentType.PROJECT ) == 0
            && Boolean.TRUE.equals( pageContext.getAttribute( PROJECT_HAS_TAGS ) ) )
        {
            // The "+" button on the project line calls the following javascript actions
            // shows the div displaying the textField to add a tag
            retour.append( "<a href=\"javascript:delProjTagButtonClicked();\"><img src=\"theme/charte_v03_001/img/ico/enabled/minus.gif\" title=\"Delete a tag\"> </a>" );
        }
        retour.append( "</div>" );

        return retour.toString();
    }

    /**
     * Méthode qui va regarder les droits de l'utilisateur, si ce dernier est gestionnaire ou admin, la méthode renvoie
     * vrai, elle renvoie faux sinon
     * 
     * @return vrai ou faux suivant les droits de l'utilisateur
     * @throws JspException si une erreur est levé pendant la récupération des paramètres de la session
     */
    private boolean isTaggingEnabled()
        throws JspException
    {
        // String access = Access.computeTagReadWriteAccess( pageContext, "admin", false, true );
        LogonBean logonBean = (LogonBean) pageContext.getSession().getAttribute( WConstants.USER_KEY );
        String profile;
        String applicationId = (String) RequestUtils.lookup( pageContext, mName, "applicationId", null );
        if ( applicationId != null && !"".equals( applicationId ) )
        {
            profile = (String) logonBean.getProfile( applicationId );
        }
        else
        {
            profile = "";
        }

        return ProfileBO.ADMIN_PROFILE_NAME.equals( profile ) || ProfileBO.MANAGER_PROFILE_NAME.equals( profile );
    }

    /**
     * métode qui renvoie les tags en fonction du component demandé
     * 
     * @param pType le type du component voulu
     * @return la collection de tags du component voulu
     * @throws JspException si une erreur est levé pendant la récupération des paramètres de la requete
     */
    @SuppressWarnings( "unchecked" )
    private Collection<TagDTO> getTagsComponent( String pType )
        throws JspException
    {

        Collection<TagDTO> tagsApplication = new ArrayList<TagDTO>();
        Collection<TagDTO> tagsProjet = new ArrayList<TagDTO>();

        if ( mName.contains( "application" ) || mName.contains( "resultListForm" ) )
        {
            tagsApplication = (Collection<TagDTO>) RequestUtils.lookup( pageContext, mName, "tags", null );
            tagsProjet = new ArrayList<TagDTO>();
        }
        else if ( mName.contains( "project" ) )
        {
            tagsProjet = (Collection<TagDTO>) RequestUtils.lookup( pageContext, mName, "tags", null );
            tagsApplication = (Collection<TagDTO>) RequestUtils.lookup( pageContext, mName, "tagsAppli", null );
        }

        if ( pType.compareTo( ComponentType.APPLICATION ) == 0 )
        {
            return tagsApplication;
        }
        else if ( pType.compareTo( ComponentType.PROJECT ) == 0 )
        {
            return tagsProjet;
        }
        else
        {
            return new ArrayList<TagDTO>();
        }
    }

    /**
     * Métode qui va écrire les tag d'un component particulier
     * 
     * @param pType type de component (project ou application)
     * @param pTags collection de TagDTO pour en générer la liste
     * @param pSep caractère séparateur pour séparer les différents tags de la liste
     * @return la chaine de libellés de tags séparés par un point virgule
     */
    private String writeTags( String pType, Collection<TagDTO> pTags, String pSep )
    {
        Iterator<TagDTO> it = pTags.iterator();
        int nbTags = 0;
        StringBuffer chaineTags = new StringBuffer();
        while ( it.hasNext() )
        {
            if ( nbTags > 0 )
            {
                chaineTags.append( pSep + "&nbsp;" );
            }
            nbTags++;
            TagDTO tag = (TagDTO) it.next();
            if ( pType.compareTo( ComponentType.APPLICATION ) == 0 )
            {
                chaineTags.append( "<a href='all_tagged_applications.do?action=listSameTagAplications&tag="
                    + tag.getId() + "'>" + tag.getName() + "</a>" );
            }
            else if ( pType.compareTo( ComponentType.PROJECT ) == 0 )
            {
                chaineTags.append( "<a href='all_tagged_projects.do?action=listSameTagProjects&tag=" + tag.getId()
                    + "'>" + tag.getName() + "</a>" );
            }

        }

        return chaineTags.toString();
    }

    /**
     * The final information of the blockquote are added here to allow the evluation of the body included
     * 
     * @return EVAL_PAGE
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de terminaison du tag
     * @throws JspException if an error occurs
     */
    public int doEndTag()
        throws JspException
    {
        StringBuffer blockquote = new StringBuffer();
        String[] param = new String[1];

        // On récupère la requête pour la locale
        Locale locale = pageContext.getRequest().getLocale();

        // On récupère les différents attributs
        String auditId = (String) RequestUtils.lookup( pageContext, mName, "currentAuditId", null );
        String previousAuditId = (String) RequestUtils.lookup( pageContext, mName, "previousAuditId", null );

        // let's start
        blockquote.append( "<div id=\"resultsheader-audits\">" );
        // Le nom de l'audit courant
        param[0] =
            "<a href='audits.do?action=select&currentAuditId=" + auditId + "'>"
                + (String) RequestUtils.lookup( pageContext, mName, "auditName", null ) + "</a>";
        // On affiche le nom de l'audit courant
        // lien idem à la sélection de l'audit
        blockquote.append( "<div class=\"audits\"><br/><b>" );
        blockquote.append( WebMessages.getString( locale, "description.name.audit", param ) );
        blockquote.append( "</b></div>" );
        // Le nom de l'audit précédent
        param[0] = (String) RequestUtils.lookup( pageContext, mName, "previousAuditName", null );
        // On affiche le nom de l'audit précédent ou la chaîne indiquant qu'il n'y en a pas
        if ( null == param[0] || param[0].length() == 0 )
        {
            param[0] =
                WebMessages.getString( ( (HttpServletRequest) pageContext.getRequest() ),
                                       "project.practices.list.previousAudit.unknown" );
            mDisplayComparable = false; // il n'y a pas d'audit précédent
        }
        else
        {
            // On ajoute un lien
            // lien idem à la sélection de l'audit
            param[0] = "<a href='audits.do?action=select&currentAuditId=" + previousAuditId + "'>" + param[0] + "</a>";
        }
        blockquote.append( "<div class=\"audits\"><b>" );
        blockquote.append( WebMessages.getString( locale, "description.name.previous.audit", param ) );
        blockquote.append( "</b></div>" );
        // On informe sur la disponibilité de comparaison des audits dans le cas où
        // l'attribut "displayComparable" serait à true
        if ( mDisplayComparable )
        {
            // On récupère l'information dans le formulaire
            Boolean isComparable = (Boolean) RequestUtils.lookup( pageContext, mName, "comparableAudits", null );
            // On affiche le message seulement si la comparaison n'est pas disponible
            if ( !isComparable.booleanValue() )
            {
                blockquote.append( WebMessages.getString( locale, "description.audits.no.comparison" ) );
            }
        }
        // end the audits part
        blockquote.append( "</div>" );
        // Fin du div principal
        blockquote.append( "</div>" );

        // On écrit la fin du block
        ResponseUtils.write( pageContext, blockquote.toString() );
        return EVAL_PAGE;
    }

    /**
     * Returns the name of the form.
     * 
     * @return le nom du formulaire
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Returns true if audit comparison display is neeed.
     * 
     * @return true si il faut afficher l'information sur la comparaison des audits
     */
    public boolean isDisplayComparable()
    {
        return mDisplayComparable;
    }

    /**
     * Set the name of the form.
     * 
     * @param pName le du formulaire
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Sets if audit comparison display is neeed.
     * 
     * @param pDisplayComparable true si il faut afficher l'information sur la comparaison des audits
     */
    public void setDisplayComparable( boolean pDisplayComparable )
    {
        mDisplayComparable = pDisplayComparable;
    }

}
