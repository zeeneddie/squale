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
package com.airfrance.squalix.tools.cpptest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Parseur de résultat cppTest CppTest génère ses résultats sous la forme de fichier XML, cette classe permet de lire le
 * contenu d'un tel fichier. Le fichier de rapport est organisé en deux sections : la première donne la liste des
 * règles, la seconde la liste des violations rencontrées
 */
public class ReportParser
    extends XmlImport
{
    /** Préfixe de fichier */
    private String mFilePrefix;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( ReportParser.class );

    /**
     * Constructeur
     */
    public ReportParser()
    {
        super( LOGGER );
    }

    /**
     * Parsing d'un fichier de résultat
     * 
     * @param pStream stream
     * @param pFilePrefix préfixe de fichier
     * @return collection de résultats sous la forme d'une map avec en clef le nom de la règle et en valeur une
     *         collection qui donne le détail de chaque violation sous la forme d'une String
     * @throws ConfigurationException si erreur
     */
    public Map parse( InputStream pStream, String pFilePrefix )
        throws ConfigurationException
    {
        Map result = null;
        mFilePrefix = pFilePrefix;
        StringBuffer errors = new StringBuffer();
        Digester digester = preSetupDigester( null, null, errors );
        // Traitement de la liste des règles
        digester.addCallMethod( "Log/Group/Property", "setRule", 1, new Class[] { String.class } );
        digester.addCallParam( "Log/Group/Property", 0, "name" );
        // Traitement du script de compilation
        digester.addCallMethod( "Log/Group/Test/Message/Property", "setMessage", 2, new Class[] { String.class,
            String.class } );
        digester.addCallParam( "Log/Group/Test/Message/Property", 0, "name" );
        digester.addCallParam( "Log/Group/Test/Message/Property", 1, "value" );
        digester.addCallMethod( "Log/Group/Test", "addItem", 1, new Class[] { String.class } );
        digester.addCallParam( "Log/Group/Test", 0, "status" );
        result = new HashMap();
        digester.push( new ResultWrapper( result ) );
        parse( digester, pStream, errors );
        if ( errors.length() != 0 )
        {
            // Erreurs rencontrées pendant le parsing
            throw new ConfigurationException( CppTestMessages.getString( "error.report.parsing", errors ) );
        }
        return result;
    }

    /**
     * Interface de résultat
     */
    class ResultWrapper
    {
        /** RuleId */
        private String mRuleId;

        /** File */
        private String mFile;

        /** Line */
        private String mLine;

        /** Description */
        private String mDescription;

        /** Résultat */
        private Map mResult;

        /**
         * Constructeur
         * 
         * @param pResult résultat
         */
        ResultWrapper( Map pResult )
        {
            mResult = pResult;
        }

        /**
         * @param pName nom
         */
        public void setRule( String pName )
        {
            final String rulePrefix = "Rule:";
            if ( pName.startsWith( rulePrefix ) )
            {
                String rule = pName.substring( rulePrefix.length() );
                mResult.put( rule, new ArrayList() );
            }
        }

        /**
         * Modification des attributs en fonction de <code>pName</code> et formatage de la valeur si besoin
         * 
         * @param pName nom
         * @param pValue valeur
         */
        public void setMessage( String pName, String pValue )
        {
            if ( pName.equals( "rule_id" ) )
            {
                // l'id de la règle
                mRuleId = pValue;
            }
            else if ( pName.equals( "file" ) )
            {
                // le fichier concerné
                if ( pValue.startsWith( mFilePrefix ) )
                {
                    // On ne prend pas le préfixe du fichier qui n'appartient pas à l'utilisateur
                    mFile = pValue.substring( mFilePrefix.length() );
                }else{
                mFile = pValue;
                }
            }
            else if ( pName.equals( "line" ) )
            {
                // Affectation du numéro de ligne
                mLine = pValue;
            }
            else if ( pName.equals( "description" ) )
            {
                // description de la transgression
                mDescription = pValue;
            }
        }

        /**
         * Ajout d'un message
         * 
         * @param pStatus status de la vérification
         */
        public void addMessage( String pStatus )
        {
            // On compose le message à ajouter
            if ( pStatus.equals( "failure" ) )
            {
                ArrayList list = (ArrayList) mResult.get( mRuleId );
                list.add( CppTestMessages.getString( "transgression.detail",
                                                     new Object[] { mFile, mLine, mDescription } ) );
            }
        }

        /**
         * Ajout d'un item
         * 
         * @param pStatus status de la vérification
         */
        public void addItem( String pStatus )
        {
            // On crée l'item à ajouter
            if ( pStatus.equals( "failure" ) )
            {
                ArrayList list = (ArrayList) mResult.get( mRuleId );
                RuleCheckingTransgressionItemBO item = new RuleCheckingTransgressionItemBO();
                item.setComponentFile( mFile );
                item.setLine( Integer.parseInt( mLine ) );
                item.setMessage( CppTestMessages.getString( "transgression.detail", new Object[] {
                    RuleCheckingTransgressionItemBO.PATH_KEY, RuleCheckingTransgressionItemBO.LINE_KEY, mDescription } ) );
                list.add( item );
            }
        }
    }
}
