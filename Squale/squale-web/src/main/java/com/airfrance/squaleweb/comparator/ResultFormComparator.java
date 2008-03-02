package com.airfrance.squaleweb.comparator;

import java.text.NumberFormat;
import java.util.Comparator;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm;

/**
 * 
 */
public class ResultFormComparator
    implements Comparator
{

    /**
     * Compare les résultats suivant la valeur de la note courante
     * 
     * @param pResult1 le premier résultat
     * @param pResult2 le second
     * @return la comparaison des valeurs des notes.
     */
    public int compare( Object pResult1, Object pResult2 )
    {
        int result = 1;
        String mark1 = null;
        String mark2 = null;
        // Vérification du type des objets
        if ( pResult1 instanceof ResultForm )
        {
            mark1 = ( (ResultForm) pResult1 ).getCurrentMark();
            mark2 = ( (ResultForm) pResult2 ).getCurrentMark();
            if ( mark1 != null )
            {
                if ( mark2 != null )
                {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits( 2 );
                    nf.setMaximumFractionDigits( 2 );
                    mark1 = nf.format( Float.parseFloat( mark1 ) );
                    mark2 = nf.format( Float.parseFloat( mark2 ) );
                    result = mark1.compareTo( mark2 );
                }
                else
                {
                    result = -1;
                }
            }
        }
        return result;
    }
}
