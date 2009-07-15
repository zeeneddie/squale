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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

import org.squale.squaleweb.applicationlayer.tracker.TrackerStructure;
import org.squale.squaleweb.resources.WebMessages;
import org.squale.squaleweb.util.SqualeWebConstants;

/**
 */
public class TrackerTag
    extends TagSupport
{

    /**
     * Constante indiquant le nombre d'éléments du traceur qui sera affiché
     */
    public final static int TRACKER_ELEMENTS_NUMBER = 5;

    /** pour indiquer si on vient directement d'une vue composant ou pas */
    private String directWay;

    /** la clé de la pratique */
    private String practiceKey;

    /** la clé du facteur */
    private String factorKey;

    /** le nom du projet */
    private String projectName;

    // un des 2 ids est obligatoire

    /**
     * l'id du projet
     */
    private String projectId;

    /**
     * l'id de l'application
     */
    private String applicationId;

    /** le nom du composant */
    private String componentName;

    /** id de l'audit courant */
    private String currentAuditId;

    /** id de l'audit précédent */
    private String previousAuditId;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        ResponseUtils.write( pageContext, getHtmlCode() );
        return SKIP_BODY;
    }

    /** sert pour mettre les ids concernant les audits */
    private String mAuditLink = "";

    /** sert pour mettre les ids concernant les projets et applis */
    private String mProjectOrApplicationIdLink = "";

    /**
     * @return le code html
     */
    private String getHtmlCode()
    {
        if ( projectId == null && applicationId != null )
        {
            mProjectOrApplicationIdLink = "&applicationId=" + applicationId;
        }
        else
        {
            mProjectOrApplicationIdLink = "&projectId=" + projectId;
        }
        if ( currentAuditId != null )
        {
            mAuditLink += "&currentAuditId=" + currentAuditId;
        }
        if ( previousAuditId != null )
        {
            mAuditLink += "&previousAuditId=" + previousAuditId;
        }
        String result = " <div id=\"traceur\"> ";
        // la session nécessaire pour pouvoir les récupérer
        HttpSession session = pageContext.getSession();
        // dans ces cas on ne se pose pas de questions
        // on affiche directement le traceur historique
        if ( directWay == null || directWay.equals( "false" ) )
        {
            result += buildHtmlStringForHistTracker( (List) session.getAttribute( SqualeWebConstants.TRACKER_HIST ) );
        }
        else
        {
            // on teste ce cas car l'affichage de toutes les pages
            // jsp est presque identique sauf dans ce cas particulier.
            if ( practiceKey != null )
            {
                String practiceName =
                    WebMessages.getString( (HttpServletRequest) pageContext.getRequest(), practiceKey );
                String link =
                    "project.do?action=practice&which=" + practiceKey + mProjectOrApplicationIdLink
                        + "&whichParent=" + factorKey + mAuditLink;
                String linkFactor =
                    "project.do?action=factor&which=" + factorKey + mProjectOrApplicationIdLink + mAuditLink;
                String linkName = WebMessages.getString( (HttpServletRequest) pageContext.getRequest(), factorKey );
                result += "<a href=\"project.do?action=summary\" > < " + projectName + " > </a> &gt";
                result += "<a href=\"" + linkFactor + "\"> < " + linkName + "> </a> &gt";
                result += "<a href=\"" + link + "\" > <" + practiceName + "> </a> ";
            }
            else
            { // sinon c'est forcément le traceur pour les composants
                result +=
                    buildHtmlStringForComponentTracker( (List) session.getAttribute( SqualeWebConstants.TRACKER_COMPONENT ) );
            }
        }
        result += "</div> ";
        return result;
    }

    /**
     * @param list la liste des composants à tracer
     * @return le code html correspondant au traceur
     */
    private String buildHtmlStringForComponentTracker( List list )
    {
        String result = "";
        String name = "";
        int i;
        // on doit afficher au maximum TRACKER_ELEMENTS_NUMBER elements, et le dernier en non cliquable
        int startIndex = Math.max( 0, list.size() - ( 1 + TRACKER_ELEMENTS_NUMBER ) );
        // (list.size -1) - startIndex < (TRACKER_ELEMENTS_NUMBER)
        for ( i = startIndex; i < list.size() - 1; i++ )
        {
            TrackerStructure structure = (TrackerStructure) list.get( i );
            name = structure.getDisplayName();
            result += structure.getLink() + mProjectOrApplicationIdLink + mAuditLink + "\" />" + name + "</a> &gt; ";
        }
        // le dernier en non cliquable
        if ( i == list.size() - 1 )
        {
            TrackerStructure structure = (TrackerStructure) list.get( i );
            // sans les "("
            StringTokenizer st = new StringTokenizer( structure.getDisplayName(), "(" );
            if ( st.hasMoreTokens() )
            {
                String last = st.nextToken();
                if ( !last.equals( name ) )
                {
                    result += last;
                }
            }
        }
        return result;
    }

    /**
     * Code factorisé car la construction du code
     * 
     * @param list les éléments du traceur
     * @return le code html
     */
    private String buildHtmlStringForHistTracker( List list )
    {
        String result = "";
        if ( list == null )
        {
            //CHECKSTYLE:OFF
            list = new ArrayList();
            //CHECKSTYLE:ON
        }
        TrackerStructure ts;
        // on limite l'affichage aux TRACKER_ELEMENTS_NUMBER
        // premiers éléments
        for ( int i = Math.max( 0, list.size() - TRACKER_ELEMENTS_NUMBER ); i < list.size() - 1; i++ )
        {
            ts = (TrackerStructure) list.get( i );
            if ( !"".equals( ts.getDisplayName() ) )
            {
                result += "<a href=\"" + ts.getLink() + mAuditLink + "\" > " + ts.getDisplayName() + " </a> &gt;";
            }
        }
        // gestion du dernier élément
        ts = (TrackerStructure) list.get( list.size() - 1 );
        // dans ce cas il n'y a pas de nom de composant donc
        // le dernier élément sans ">" à la fin et non cliquable
        result += ts.getDisplayName();
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
     * @return l'élément indiquant si on vient directement d'une vue composant ou pas
     */
    public String getDirectWay()
    {
        return directWay;
    }

    /**
     * @return la clé du facteur
     */
    public String getFactorKey()
    {
        return factorKey;
    }

    /**
     * @return la clé de la pratique
     */
    public String getPracticeKey()
    {
        return practiceKey;
    }

    /**
     * @return le nom du projet
     */
    public String getProjectName()
    {
        return projectName;
    }

    /**
     * @param newWay la nouvelle valeur du chemin
     */
    public void setDirectWay( String newWay )
    {
        directWay = newWay;
    }

    /**
     * @param newFactorKey la nouvelle clé de facteur
     */
    public void setFactorKey( String newFactorKey )
    {
        factorKey = newFactorKey;
    }

    /**
     * @param newPracticeKey la nouvelle clé de facteur
     */
    public void setPracticeKey( String newPracticeKey )
    {
        practiceKey = newPracticeKey;
    }

    /**
     * @param newName le nouveau nom
     */
    public void setProjectName( String newName )
    {
        projectName = newName;
    }

    /**
     * @return le nom du composant
     */
    public String getComponentName()
    {
        return componentName;
    }

    /**
     * @param newName le nouveau nom du composant
     */
    public void setComponentName( String newName )
    {
        componentName = newName;
    }

    /**
     * @return l'id de l'application
     */
    public String getApplicationId()
    {
        return applicationId;
    }

    /**
     * @return l'id du projet
     */
    public String getProjectId()
    {
        return projectId;
    }

    /**
     * @param newApplicationId le nouvel id pour l'application
     */
    public void setApplicationId( String newApplicationId )
    {
        applicationId = newApplicationId;
    }

    /**
     * @param newProjectId le nouvel id du projet
     */
    public void setProjectId( String newProjectId )
    {
        projectId = newProjectId;
    }

    /**
     * @return l'id de l'audit courant
     */
    public String getCurrentAuditId()
    {
        return currentAuditId;
    }

    /**
     * @return l'id de l'audit précédent
     */
    public String getPreviousAuditId()
    {
        return previousAuditId;
    }

    /**
     * @param pCurrentAuditId l'id de l'audit courant
     */
    public void setCurrentAuditId( String pCurrentAuditId )
    {
        currentAuditId = pCurrentAuditId;
    }

    /**
     * @param pPreviousAuditId l'id de l'audit précédent
     */
    public void setPreviousAuditId( String pPreviousAuditId )
    {
        previousAuditId = pPreviousAuditId;
    }

}
