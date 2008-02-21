package com.airfrance.squaleweb.util.graph;

import java.awt.Color;

/**
 */
public abstract class AbstractRepartitionMaker extends AbstractGraphMaker {

    
    /** Valeur max servant pour l'axe des ordonnées */
    protected double mMaxValue; 

    /**
     * l'id de la pratique
     */
    protected String mPracticeId;

    /**
     * l'id du critère parent
     */
    protected String mFactorParentId;

    /** le nombre de séries pour un graph de répartition de pas = 1 */
    public final static int NB_SERIES_FOR_INT_GRAPH = 3;

    /** le nombre de séries pour un graph de répartition de pas = 0.1 */
    public final static int NB_SERIES_FOR_FLOAT_GRAPH = 30;

    /**
     * Calcule les couleurs du graph
     * @param pNbElements le nombre d'éléments du graph à colorier
     * @return le tableau des couleurs pour le graph
     */
    private Color[] getColors(int pNbElements) {
        Color[] result = new Color[pNbElements];
        // Couleurs + pastels que celles par défault
        // sert aussi pour le dégradé
        final int max = 255;
        final int min = 80;
        final int redUpperBound = 10;
        final int yellowUpperBound = 20;
        final int degCoeff = 5;
        Color red = new Color(max, min, min);
        Color yellow = new Color(max, max, min);
        Color green = new Color(min, max, min);
        if (pNbElements == NB_SERIES_FOR_INT_GRAPH) {
            result[0] = red;
            result[1] = yellow;
            result[2] = green;
        } else { //nbSeries = 30
            Color currentColor = red; // initialisation à rouge
            int counter = 0; // pour le dégradé
            for (int i = 0; i < pNbElements; i++) {
                if (i == redUpperBound) { // passe au jaune
                    currentColor = yellow;
                    counter = 0;
                } else {
                    if (i == yellowUpperBound) { // passe au vert
                        currentColor = green;
                        counter = 0;
                    } else {
                        if (i < redUpperBound) { // Dégradé de rouge
                            currentColor = new Color(max, min + degCoeff * counter, min + degCoeff * counter);
                        } else {
                            if (i < yellowUpperBound) { // Dégradé de jaune
                                currentColor = new Color(max, max, min + degCoeff * counter);
                            } else { // Dégradé de vert
                                currentColor = new Color(min + degCoeff * counter, max, min + degCoeff * counter);
                            }
                        }
                    }
                }
                counter++;
                result[i] = currentColor;
            }
        }
        return result;
    }
    
    /**
     * @param pNbElements le nombre d'éléments du graph à colorier
     */
    protected void manageColor(int pNbElements){
        Color[] tab = getColors(pNbElements);
        for(int i=0;i<tab.length;i++){
            applyColor(i,tab[i]);
        }
    }
    
    /**
     * Applique la couleur a la série en cours
     * Méthode abstraite car la méthode concrète dépend du type du renderer
     * @param pIndex l'index de la série ou de la catégory sur lequel on doit appliquer la couleur
     * @param pColor la couleur à appliquer
     */
    abstract protected void applyColor(int pIndex,Color pColor);

}
