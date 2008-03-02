/*
 * Créé le 11 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalix.util.csv;

import java.lang.reflect.Field;

/**
 * Bean destiné à être utilisé dans les tests de mapping CSV - Object.
 * 
 * @author M400842
 */
public class CSVBeanTest
{
    /** Champ de test */
    private String name = null;

    /** Champ de test */
    private Float val1 = null;

    /** Champ de test */
    private Float val2 = null;

    /** Champ de test */
    private Float val3 = null;

    /** Champ de test */
    private Float val4 = null;

    /** Champ de test */
    private Float val5 = null;

    /** Champ de test */
    private Float val6 = null;

    /** Champ de test */
    private Float val7 = null;

    /** Champ de test */
    private Float val8 = null;

    /** Champ de test */
    private Float val9 = null;

    /** Champ de test */
    private Float val10 = null;

    /** Champ de test */
    private Float val11 = null;

    /** Champ de test */
    private Float val12 = null;

    /** Champ de test */
    private Float val13 = null;

    /** Champ de test */
    private Float val14 = null;

    /**
     * @param string valeur
     */
    public void setName( String string )
    {
        name = string;
    }

    /**
     * @param float1 valeur
     */
    public void setVal1( Float float1 )
    {
        val1 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal10( Float float1 )
    {
        val10 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal11( Float float1 )
    {
        val11 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal12( Float float1 )
    {
        val12 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal13( Float float1 )
    {
        val13 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal14( Float float1 )
    {
        val14 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal2( Float float1 )
    {
        val2 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal3( Float float1 )
    {
        val3 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal4( Float float1 )
    {
        val4 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal5( Float float1 )
    {
        val5 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal6( Float float1 )
    {
        val6 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal7( Float float1 )
    {
        val7 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal8( Float float1 )
    {
        val8 = float1;
    }

    /**
     * @param float1 valeur
     */
    public void setVal9( Float float1 )
    {
        val9 = float1;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        Field fields[] = this.getClass().getFields();
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < fields.length; i++ )
        {
            try
            {
                sb.append( fields[i].get( this ) );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
            if ( i < fields.length - 1 )
            {
                sb.append( "," );
            }
        }
        return sb.toString();
    }
}
