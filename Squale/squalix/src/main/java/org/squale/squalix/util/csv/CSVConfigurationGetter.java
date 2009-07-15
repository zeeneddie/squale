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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\util\\csv\\CSVConfigurationGetter.java

package org.squale.squalix.util.csv;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import org.squale.squalix.configurationmanager.ConfigUtility;

/**
 * Cette classe pourra utiliser jakarta.commons.configuration pour récupérer la configuration du framework.
 * 
 * @author m400842
 * @version 1.0
 */
public class CSVConfigurationGetter
{

    /**
     * Chemin par défaut du fichier de configuration
     */
    private String mConfigPath = CSVMessages.getString( "configuration.default_file" );

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( CSVConfigurationGetter.class );

    /**
     * Constructeur
     * 
     * @param pFilename fichier contenant la configuration.
     * @roseuid 429C27C60154
     */
    public CSVConfigurationGetter( String pFilename )
    {
        mConfigPath = pFilename;
    }

    /**
     * Constructeur avec la valeur du chemin de configuration par défaut.
     * 
     * @roseuid 429C27F90146
     */
    public CSVConfigurationGetter()
    {
    }

    /**
     * Récupère la configuration liée au modèle passé en paramètre.
     * 
     * @param pTemplateName nom du modèle.
     * @return la configuration du module.
     * @throws CSVException si un probleme de récupération de la configuration apparaît.
     * @roseuid 42942ACA02C1
     */
    public CSVConfiguration getConfiguration( final String pTemplateName )
        throws CSVException
    {
        CSVConfiguration config = new CSVConfiguration( pTemplateName );
        try
        {
            LOGGER.debug( CSVMessages.getString( "configuration.debug.reading_configuration" ) );
            LOGGER.debug( CSVMessages.getString( "configuration.debug.searching_mapping_template" ) );
            Node root = ConfigUtility.getRootNode( mConfigPath, CSVMessages.getString( "configuration.root.name" ) );
            // On récupère la collection des éléments enfants du noeud racine
            Collection templates = ConfigUtility.filterList( root.getChildNodes(), Node.ELEMENT_NODE );
            // Parmi cette liste, on vé récupérer la configuration du modèle de mapping
            Node templateNode = getTemplateNode( templates, pTemplateName );
            if ( null != templateNode )
            {
                // Si on trouve le bon modèle de mapping, alors on récupère sa config
                setConfig( templateNode, config );
            }
            else
            {
                // Sinon on génère une exception
                throw new Exception();
            }
        }
        catch ( Exception e )
        {
            // Si une exception est attrapée, on l'encapsule dans une nouvelle
            CSVException excep = new CSVException( CSVMessages.getString( "exception.configuration.failed" ) );
            excep.initCause( e );
            throw excep;
        }
        return config;
    }

    /**
     * Récupère le noeud contenant la configuration CSV associée au template paramètre.
     * 
     * @param pTemplates la liste des noeuds contenant les templates.
     * @param pTemplateName le nom de template dont on veux récupérer la configuration.
     * @return le noeud correspondant à la configuration du modèle de mapping CSV, ou null.
     */
    private Node getTemplateNode( final Collection pTemplates, final String pTemplateName )
    {
        Node templateNode = null;
        Iterator it = pTemplates.iterator();
        Node node = null;
        // On récupère tous les noeuds, en recherchant celui dont la valeur de l'attribut
        // pointé par la clé "configuration.template.name" correspond à celle passée
        // en paramètre.
        while ( it.hasNext() && null == templateNode )
        {
            node = (Node) it.next();
            if ( ConfigUtility.getAttributeValueByName( node, CSVMessages.getString( "configuration.template.name" ) ).equals(
                                                                                                                               pTemplateName ) )
            {
                templateNode = node;
            }
        }
        return templateNode;
    }

    /**
     * Access method for the mConfigPath property.
     * 
     * @return the current value of the mConfigPath property
     * @roseuid 42CE6C6E01A9
     */
    public String getConfigPath()
    {
        return mConfigPath;
    }

    /**
     * Met en place la configuration a partir du modèle de mapping donné.
     * 
     * @param pNode element contenant le modèle de mapping donné.
     * @param pConfig configuration à remplir.
     * @exception Exception si un problème de configuration apparaît.
     * @roseuid 42D23B3D00C1
     */
    private void setConfig( final Node pNode, final CSVConfiguration pConfig )
        throws Exception
    {
        // recupere la class du bean
        pConfig.setCSVBean( ConfigUtility.getNodeByTagName( pNode, CSVMessages.getString( "configuration.csvbean" ) ).getFirstChild().getNodeValue() );
        Class csvclass = Class.forName( pConfig.getCSVBean() );

        pConfig.setFooterSize( Integer.parseInt( ConfigUtility.getAttributeValueByName(
                                                                                        ConfigUtility.getNodeByTagName(
                                                                                                                        pNode,
                                                                                                                        CSVMessages.getString( "configuration.footer" ) ),
                                                                                        CSVMessages.getString( "configuration.footer.size" ) ) ) );
        pConfig.setHeaderSize( Integer.parseInt( ConfigUtility.getAttributeValueByName(
                                                                                        ConfigUtility.getNodeByTagName(
                                                                                                                        pNode,
                                                                                                                        CSVMessages.getString( "configuration.header" ) ),
                                                                                        CSVMessages.getString( "configuration.header.size" ) ) ) );

        // Liste des filtres sur chaque colonne
        Iterator it =
            ConfigUtility.filterList(
                                      ConfigUtility.getNodeByTagName( pNode,
                                                                      CSVMessages.getString( "configuration.fields" ) ).getChildNodes(),
                                      Node.ELEMENT_NODE ).iterator();
        HashMap methods = new HashMap();
        // Recupere les filtres de chaque colonne
        while ( it.hasNext() )
        {
            Node node = (Node) it.next();
            String fieldName =
                ConfigUtility.getAttributeValueByName( node, CSVMessages.getString( "configuration.field.name" ) );
            String fieldType =
                ConfigUtility.getAttributeValueByName( node, CSVMessages.getString( "configuration.field.type" ) );
            String columnString =
                ConfigUtility.getAttributeValueByName( node, CSVMessages.getString( "configuration.field.column" ) );
            Integer column = new Integer( columnString );
            Method method = getSetter( csvclass, fieldName, fieldType );
            methods.put( column, method );
        }
        pConfig.setMethods( methods );
    }

    /**
     * Retourne le setter de la méthode.
     * 
     * @param pClass la classe à laquelle appartient le champ.
     * @param pFieldName nom du champ modifié par le setter.
     * @param pType le type objet du champ.
     * @return la méthode setter associée.
     * @throws Exception si un probleme de récupération de la méthode apparait.
     * @roseuid 42DFB34802A0
     */
    private Method getSetter( final Class pClass, final String pFieldName, final String pType )
        throws Exception
    {
        String setterName =
            CSVMessages.getString( "setter.prefix" ) + pFieldName.substring( 0, 1 ).toUpperCase()
                + pFieldName.substring( 1 );
        Class type = Class.forName( pType );
        Class types[] = { type };
        Method setter = pClass.getMethod( setterName, types );
        return setter;
    }
}
