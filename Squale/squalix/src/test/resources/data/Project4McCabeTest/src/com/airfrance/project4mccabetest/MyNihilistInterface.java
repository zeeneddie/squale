package com.airfrance.project4mccabetest;

/**
 * Classe de test permettant de tester l'exécution de la tâche McCabe
 * sous Unix.
 * 
 * Cette interface ne fait et ne sert à rien d'autre qu'un héritage.
 * 
 * @author M400842
 */
public interface MyNihilistInterface {

    /**
     * Méthode à surcharger pour ne rien faire.
     * 
     * @param pObject l'objet ne servant à rien.
     * @return la valeur booleénne du vide.
     */
    abstract boolean doNothing(final Object pObject);

}
