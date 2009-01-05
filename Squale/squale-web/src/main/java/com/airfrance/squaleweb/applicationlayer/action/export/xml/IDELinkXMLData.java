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
package com.airfrance.squaleweb.applicationlayer.action.export.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.export.ActionPlanDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleCheckingItemDTO;
import com.airfrance.squaleweb.resources.WebMessages;

/**
 * XML data pour l'export IDE
 */
public class IDELinkXMLData
    extends XMLData
{

    /** Nom racine du fichier XML */
    public static final String ROOT_NAME = "squaleLink-config";

    /** La date de l'audit */
    private String auditDate;

    /** Le label de l'audit si il s'agit d'un audit de jalon */
    private String label;

    /** Nom de l'application */
    private String applicationName;

    /** Nom du workspace du projet */
    private String workspace;

    /** La collection des ActionPlanDTO */
    private Collection actions;

    /**
     * @param pRequest la requête
     * @param pAuditDate la date de l'audit
     * @param pAppliName le nom de l'application
     * @param pWorkspace le workspace du projet
     * @param pActions la liste des pratiques à corriger sous forme d'ActionPlanDTO
     * @throws ParserConfigurationException si erreur
     */
    public IDELinkXMLData( HttpServletRequest pRequest, String pAuditDate, String pAppliName, String pWorkspace,
                           Collection pActions )
        throws ParserConfigurationException
    {
        super( pRequest );
        auditDate = pAuditDate;
        label = "";
        applicationName = pAppliName;
        workspace = pWorkspace;
        actions = pActions;
    }

    /**
     * {@inheritDoc}
     * 
     * @param xmlGenerator
     * @see com.airfrance.squaleweb.util.xml.XMLData#fill(com.airfrance.squaleweb.util.xml.XMLGenerator)
     */
    public void fill()
    {
        // On crée la racine du document avec l'en-tête
        Element root = getDocument().createElement( ROOT_NAME );
        root.appendChild( getDocument().createComment( WebMessages.getString( getRequest(), "export.ide.comment" ) ) );
        root.setAttribute( "applicationName", applicationName );
        root.setAttribute( "workspace", workspace );
        root.setAttribute( "auditDate", auditDate );
        root.setAttribute( "label", label );
        // On ajoute la racine au document
        getDocument().appendChild( root );
        // On crée les informations du plan d'action comme noeud de la racine
        ActionPlanDTO actionPlan;
        QualityRuleDTO currentRule;
        for ( Iterator it = actions.iterator(); it.hasNext(); )
        {
            actionPlan = (ActionPlanDTO) it.next();
            if ( null != actionPlan.getTransgressions() )
            {
                // il s'agit d'une règle de type rulechecking
                fillWithTransgressions( root, actionPlan );
            }
            else
            {
                fillWithPracticeResults( root, actionPlan );
            }
        }
    }

    /**
     * Construit le fichier XML avec une pratique contenant une liste de transgressions de règles à corriger
     * 
     * @param root la racine du fichier XML
     * @param actionPlan la pratique à corriger
     */
    private void fillWithTransgressions( Element root, ActionPlanDTO actionPlan )
    {
        for ( Iterator it = actionPlan.getTransgressions().iterator(); it.hasNext(); )
        {
            RuleCheckingItemDTO item = (RuleCheckingItemDTO) it.next();
            Element transgression = getDocument().createElement( "transgression" );
            // La sévérité de la règle
            transgression.setAttribute( "severity", item.getRuleSeverity() );
            // Le nom de la règle
            String ruleName = item.getRuleCode();
            String ruleNameKey = "metric." + ruleName;
            String dbMessage = WebMessages.getString( request, ruleNameKey );
            // l'intitulé de la règle est affiché à la place du nom lorsqu'il existe
            if ( dbMessage != null )
            {
                ruleName = dbMessage;
            }
            transgression.setAttribute( "description", ruleName );
            // On découpe le nom du fichier du composant dans lequel se trouve le composant concerné
            // pour modifier les attributs "resource" et "folder"
            String fileName = "";
            String componentName = "";
            if ( null != item.getComponent() )
            {
                fileName = item.getComponent().getFileName();
                componentName = item.getComponent().getFullName();
            }
            if ( null == fileName || fileName.length() == 0 )
            {
                fileName = item.getComponentFile();
            }
            cutFileName( componentName, fileName, transgression );
            // La ligne
            transgression.setAttribute( "location", "" + item.getLine() );
            root.appendChild( transgression );
        }
    }

    /**
     * Construit le fichier XML avec une pratique contenant une liste de composants à corriger
     * 
     * @param root la racine du fichier XML
     * @param actionPlan la pratique à corriger
     */
    private void fillWithPracticeResults( Element root, ActionPlanDTO actionPlan )
    {
        Set components = actionPlan.getResultsDTO().getResultMap().keySet();
        components.remove( null );
        for ( Iterator it = components.iterator(); it.hasNext(); )
        {
            ComponentDTO component = (ComponentDTO) it.next();
            Element transgression = getDocument().createElement( "transgression" );
            // Le nom de la règle
            String ruleName = WebMessages.getString( request, actionPlan.getQualityResultDTO().getRule().getName() );
            transgression.setAttribute( "description", ruleName );
            // On découpe le nom du fichier du composant dans lequel se trouve le composant concerné
            // pour modifier les attributs "resource" et "folder"
            cutFileName( component.getFullName(), component.getFileName(), transgression );
            // La ligne
            transgression.setAttribute( "location", component.getStartLine() );
            root.appendChild( transgression );
        }
    }

    /**
     * Découpe un nom de fichier en deux éléments : le premier est le répertoire du fichier par rapport au workspace du
     * projet et le deuxième le nom du fichier
     * 
     * @param pComponentName le nom du composant
     * @param pFileName le nom du fichier à découper
     * @param pTransgression le noeud du fichier XML à modifier
     */
    private void cutFileName( String pComponentName, String pFileName, Element pTransgression )
    {
        // On uniformise la rechercher
        String path = ( null == pFileName ) ? "" : pFileName; // le nom du fichier peut être nul
        path.replaceAll( "\\\\*", "/" );
        // Le nom du fichier dans lequel se trouve le composant
        // Si le fichier n'existe pas, on met le nom du composant
        String componentName = pComponentName;
        // Le nom du dossier dans lequel se trouve le fichier par rapport au projet
        String folder = "";
        int index = path.lastIndexOf( "/" );
        if ( index > 0 )
        {
            componentName = path.substring( index + 1 );
            folder = path.substring( 0, index + 1 );
        }
        pTransgression.setAttribute( "inFolder", folder );
        pTransgression.setAttribute( "resource", componentName );
    }

    /**
     * @return le nom de l'application
     */
    public String getApplicationName()
    {
        return applicationName;
    }

    /**
     * @return la date de l'audit
     */
    public String getAuditDate()
    {
        return auditDate;
    }

    /**
     * @return le label de l'audit
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * @return le workspace du projet
     */
    public String getWorkspace()
    {
        return workspace;
    }

    /**
     * @param pLabel la label de l'audit
     */
    public void setLabel( String pLabel )
    {
        label = pLabel;
    }
}
