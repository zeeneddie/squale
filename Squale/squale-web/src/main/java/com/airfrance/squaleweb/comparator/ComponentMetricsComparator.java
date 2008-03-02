package com.airfrance.squaleweb.comparator;

import java.util.Comparator;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;

/**
 * Compare deux ComponentForm en fonction d'une métrique indexée.
 * 
 * @author M400842
 */
public class ComponentMetricsComparator
    implements Comparator
{

    /**
     * Index de la métrique à comparer
     */
    private int mIndex = -1;

    /**
     * Constructeur.<br />
     * Si l'index de la métrique est égal à -1, alors les composants sont comparés par rapport à leurs noms.
     * 
     * @param pIndex l'index de la metrique à comparer.
     */
    public ComponentMetricsComparator( int pIndex )
    {
        mIndex = pIndex;
    }

    /**
     * Compare deux objets composants (instances de ComponentForm)
     * 
     * @param pO1 le premier composant à comparer.
     * @param pO2 le second composant à comparer.
     * @return la valeur de la comparaison.
     */
    public int compare( Object pO1, Object pO2 )
    {
        int value = 0;
        if ( -1 == mIndex )
        {
            value = ( (ComponentForm) pO1 ).getName().compareTo( ( (ComponentForm) pO2 ).getName() );
        }
        else
        {
            value = compare( (ComponentForm) pO1, (ComponentForm) pO2 );
        }
        return value;
    }

    /**
     * Compare les métriques indexés par mIndex des formulaires.
     * 
     * @param pForm1 le premier composant à comparer.
     * @param pForm2 le second composant à comparer.
     * @return la valeur de la comparaison.
     */
    private int compare( final ComponentForm pForm1, final ComponentForm pForm2 )
    {
        Number value1 = (Number) pForm1.getMetrics().get( mIndex );
        Number value2 = (Number) pForm2.getMetrics().get( mIndex );
        // System.err.println("Comparaison de : " + value1.floatValue() + " et " + value2.floatValue() + " donne : " +
        // (int)(value1.floatValue() - value2.floatValue()));
        return (int) ( value1.floatValue() - value2.floatValue() );
    }

}
