package com.airfrance.squalecommon.util.mapping;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;

/**
 * Test de la classe de mapping
 */
public class MappingTest
    extends SqualeTestCase
{

    /**
     * Test de getComponentClass
     */
    public void testGetComponentClass()
    {
        // Composant de type classe
        assertNotNull( Mapping.getComponentClass( ComponentType.CLASS ) );
        assertEquals( ClassBO.class, Mapping.getComponentClass( ComponentType.CLASS ) );

        // Composant inconnu
        assertNull( Mapping.getComponentClass( "component.unknown" ) );
    }

    /**
     * Test de getComponentName
     */
    public void testGetComponentName()
    {
        // Classe connue
        assertEquals( ComponentType.CLASS, Mapping.getComponentName( ClassBO.class ) );
        // Classe inconnue
        assertNull( Mapping.getComponentName( this.getClass() ) );
    }

    /**
     * Test de getMeasureClass
     */
    public void testGetMeasureClass()
    {
        // Mesure connue
        assertEquals( McCabeQAClassMetricsBO.class, Mapping.getMeasureClass( "mccabe.class" ) );
        // Mesure inconnue
        assertNull( Mapping.getMeasureClass( "mccabe.unknown" ) );
    }

    /**
     * Test de getMeasureName
     */
    public void testGetMeasureName()
    {
        // Mesure connue
        assertEquals( "mccabe.class", Mapping.getMeasureName( McCabeQAClassMetricsBO.class ) );
        // Mesure inconnue
        assertNull( Mapping.getMeasureName( this.getClass() ) );
    }

    /**
     * Test de GetMetricClass
     */
    public void testGetMetricClass()
    {
        // Métrique connue
        assertEquals( McCabeQAClassMetricsBO.class, Mapping.getMetricClass( "mccabe.class.dit" ) );
        // Métrique inconnue
        assertNull( Mapping.getMetricClass( "mccabe.unknown.dit" ) );
        // Cas d'erreur
        try
        {
            Mapping.getMetricClass( "unknown" );
            fail( "exception expected" );
        }
        catch ( IllegalArgumentException e )
        {
            assertTrue( "exception expected", true );
        }
    }

    /**
     * Test de GetMetricGetter
     */
    public void testGetMetricGetter()
    {
        // Métrique connue
        assertNotNull( Mapping.getMetricGetter( "mccabe.class.dit" ) );
        assertEquals( McCabeQAClassMetricsBO.class, Mapping.getMetricGetter( "mccabe.class.dit" ).getDeclaringClass() );
        // Métrique inconnue
        assertNull( Mapping.getMetricGetter( "mccabe.class.unknown" ) );
        // Cas d'erreur
        try
        {
            Mapping.getMetricGetter( "unknown" );
            fail( "exception expected" );
        }
        catch ( IllegalArgumentException e )
        {
            assertTrue( "exception expected", true );
        }
    }
}
