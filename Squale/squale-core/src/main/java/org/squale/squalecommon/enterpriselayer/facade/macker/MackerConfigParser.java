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
package org.squale.squalecommon.enterpriselayer.facade.macker;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import org.squale.squalecommon.util.xml.XmlImport;

/**
 * Parser de configuration Macker
 */
public class MackerConfigParser
    extends XmlImport
{

    /** Log */
    private static Log LOG = LogFactory.getLog( MackerConfigParser.class );

    /**
     * Constructeur par défaut
     */
    public MackerConfigParser()
    {
        super( LOG );
    }

    /**
     * Parsing du fichier de configuration Macker
     * 
     * @param pStream flux
     * @param pErrors erreurs rencontrées
     * @return données lues
     */
    public ProjectRuleSetBO parseFile( InputStream pStream, StringBuffer pErrors )
    {
        // Résultat
        ProjectRuleSetBO result = new ProjectRuleSetBO();

        Digester configDigester = preSetupDigester( null, null, pErrors );
        configDigester.push( result );
        // On récupère le nom du ruleSet
        configDigester.addSetProperties( "macker/ruleset" );
        // On crée une règle
        RuleFactory rule = new RuleFactory();
        configDigester.addFactoryCreate( "macker/ruleset/access-rule", rule );
        configDigester.addSetProperties( "macker/ruleset/access-rule" );
        configDigester.addBeanPropertySetter( "macker/ruleset/access-rule/message", "code" );
        configDigester.addCallMethod( "macker/ruleset/access-rule", "setRuleSet", 1,
                                      new Class[] { ProjectRuleSetBO.class } );
        configDigester.addCallParam( "macker/ruleset/access-rule", 0, 1 );
        // On ajoute la règle au ruleSet
        configDigester.addSetNext( "macker/ruleset/access-rule", "addRule" );
        parse( configDigester, pStream, pErrors );
        return result;
    }

}
