package com.airfrance.welcom.outils.pdf.advanced;

/*
 * Créé le 3 nov. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfFieldType
{
    /** type */
    private int type = 0;

    /** constante */
    public final static WPdfFieldType TEXT = new WPdfFieldType( 1 );

    /** constante */
    public final static WPdfFieldType MUTILINETEXT = new WPdfFieldType( 2 );

    /** constante */
    public final static WPdfFieldType INCONNU = new WPdfFieldType( 3 );

    /**
     * @param i le type
     */
    private WPdfFieldType( final int i )
    {
        super();
        type = i;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        String theString = "INCONNU";
        if ( this == TEXT )
        {
            theString = "LIGNE SIMPLE";
        }
        else if ( this == MUTILINETEXT )
        {
            theString = "MULTI-LIGNE";
        }
        return theString;
    }

}
