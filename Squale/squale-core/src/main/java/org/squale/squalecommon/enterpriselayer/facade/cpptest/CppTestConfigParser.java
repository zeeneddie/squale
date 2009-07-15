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
package org.squale.squalecommon.enterpriselayer.facade.cpptest;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest.CppTestRuleSetBO;
import org.squale.squalecommon.util.xml.XmlImport;

/**
 * Parser de configuration de CppTest
 */
public class CppTestConfigParser
    extends XmlImport
{

    /** Log */
    private static Log LOG = LogFactory.getLog( CppTestConfigParser.class );

    /** Nom publique de la DTD */
    final static String PUBLIC_DTD = "-//Squale//DTD CppTest Configuration 1.0//EN";

    /** Localisation de la DTD */
    final static String DTD_LOCATION = "/org/squale/squalecommon/dtd/cpptest-1.0.dtd";

    /**
     * Constructeur
     */
    public CppTestConfigParser()
    {
        super( LOG );
    }

    /**
     * Parsing du fichier de configuration CppTest
     * 
     * @param pStream flux
     * @param pErrors erreurs rencontrées
     * @return données lues
     */
    public CppTestRuleSetBO parseFile( InputStream pStream, StringBuffer pErrors )
    {
        // Résultat
        CppTestRuleSetBO result = new CppTestRuleSetBO();

        Digester configDigester = preSetupDigester( PUBLIC_DTD, DTD_LOCATION, pErrors );
        configDigester.push( result );
        configDigester.addSetProperties( "ruleset" );
        configDigester.addObjectCreate( "ruleset/rule", RuleBO.class );
        configDigester.addSetProperties( "ruleset/rule" );
        configDigester.addCallMethod( "ruleset/rule", "setRuleSet", 1, new Class[] { RuleSetBO.class } );
        configDigester.addCallParam( "ruleset/rule", 0, 1 );
        configDigester.addSetNext( "ruleset/rule", "addRule" );
        parse( configDigester, pStream, pErrors );
        return result;
    }

}
