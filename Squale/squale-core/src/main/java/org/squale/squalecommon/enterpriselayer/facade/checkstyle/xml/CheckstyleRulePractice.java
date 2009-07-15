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
 * Créé le 2 nov. 06
 *
 * Lors du parcours du fichier de configuration Checkstyle via le Digester,
 * mémorisation sur la balise "metadata" de la pratique associée à la règle
 */
package org.squale.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;

import org.squale.squalecommon.util.xml.FactoryAdapter;

/**
 * Mémorisation de la catégorie lors de l'instanciation
 * 
 * @param pAttributes attributs code et valeur
 * @return null
 * @throws Exception erreur de paramétrage
 */
public class CheckstyleRulePractice
    extends FactoryAdapter
{
    /** catégorie de la règle */
    private String practice;

    /** Constructeur */
    public CheckstyleRulePractice()
    {
    }

    /**
     * Mémorisation de la catégorie lors de l'instanciation
     * 
     * @param pAttributes code et valeur de la balise metadata
     * @return null
     * @throws Exception balise metadata mal paramétrée
     */
    public Object createObject( Attributes pAttributes )
        throws Exception
    {
        String name = pAttributes.getValue( "name" );
        String value = pAttributes.getValue( "value" );

        // Test si le pattern metadata est en rapport avec le nom de la règle
        if ( name.trim().equals( XmlCheckstyleParsingMessages.getString( "checkstyle.rule.practice" ) ) )
        {
            practice = value;
        }
        else
        {
            throw new Exception( XmlCheckstyleParsingMessages.getString( "checkstyle.pattern.reserved",
                                                                         new Object[] { "\n<module>\n\t"
                                                                             + "<module>\n\t\t" + "<metadata>\n" } ) );
        }
        return null;
    }

    /**
     * @return category
     */
    public String getPractice()
    {
        return practice;
    }

}
