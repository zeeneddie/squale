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
package com.airfrance.squalix.util.csv;

import java.util.Collection;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 * Teste le parser CSV permettant le mapping CSV - objet.
 * 
 * @author M400842
 */
public class CSVParserTest
    extends SqualeTestCase
{

    /**
     * Vérifie que la création du parser ne dépend pas de l'existance du fichier de configuration, car le chargement de
     * celle-ci est fait en lazy (id lorsqu'on en a besoin)
     */
    public void testCSVParserString()
    {
        /*
         * Le fichier de config n'est pas bon.
         */
        CSVParser csvp = null;
        csvp = new CSVParser( "toto.xml" );
        assertNotNull( csvp );
        /**
         * Le fichier de config est bon.
         */
        csvp = null;
        csvp = new CSVParser( "config/csv-config.xml" );
        assertNotNull( csvp );
    }

    /**
     * Test sur le parsing d'un fichier XML avec un CSVParser
     */
    public void testParse()
    {
        CSVParser csvp = null;
        csvp = new CSVParser( "config/csv-config.xml" );
        assertNotNull( csvp );
        /*
         * Si le fichier à parser n'est pas bon
         */
        Collection beans = null;
        try
        {
            beans = csvp.parse( "mes_classes", "data/toto.csv" );
        }
        catch ( CSVException e )
        {
            beans = null;
        }
        assertNull( beans );
        /*
         * Si le nom du modèle n'est pas bon, mais celui du fichier l'est
         */
        beans = null;
        try
        {
            beans = csvp.parse( "toto", "data/csv-test.csv" );
        }
        catch ( CSVException e )
        {
            beans = null;
        }
        assertNull( beans );
        /*
         * Si tout est correct
         */
        beans = null;
        try
        {
            beans = csvp.parse( "mes_classes", "data/csv/csv-test.csv" );
        }
        catch ( CSVException e )
        {
            beans = null;
        }
        assertNotNull( beans );
        assertEquals( 1560, beans.size() );

    }

}
