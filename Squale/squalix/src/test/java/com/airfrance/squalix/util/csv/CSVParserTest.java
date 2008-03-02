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
