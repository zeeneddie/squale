package com.airfrance.welcom.struts.bean;

import java.util.Collections;
import java.util.Vector;

/**
 * Insérez la description du type ici. Date de création : (31/10/2001 15:40:32)
 * 
 * @author: Fabienne Madaule
 */
public class WComboValue
{
    /** la liste */
    private java.util.Vector liste;

    /**
     * Commentaire relatif au constructeur TypeProcedureListe.
     */
    public WComboValue()
    {
        super();
        liste = new Vector();
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (31/10/2001 15:47:30)
     * 
     * @return java.lang.String[]
     */
    public java.util.Vector getListe()
    {
        return liste;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (31/10/2001 16:49:02)
     * 
     * @return la taille de la liste
     */
    public int getSize()
    {
        return liste.size();
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (31/10/2001 15:47:30)
     * 
     * @param i l'index
     * @return la chaine de caractere a l'index i
     */
    public java.lang.String getValue( final int i )
    {
        return (java.lang.String) liste.elementAt( i );
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (31/10/2001 15:47:30)
     * 
     * @param newListe java.lang.String[]
     */
    public void setListe( final java.util.Vector newListe )
    {
        liste = new Vector( newListe );
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (31/10/2001 15:47:30)
     * 
     * @param newValue la nouvelle value
     */
    public void setValue( final java.lang.String newValue )
    {
        liste.add( newValue );
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (29/11/2001 10:13:37)
     */
    public void sort()
    {
        Collections.sort( liste );
    }
}