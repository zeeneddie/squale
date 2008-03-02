package com.airfrance.squalix.util.parser;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;

/**
 * Parse les noms entièremement qualifié et les remplace par les objets correspondants.
 */
public interface LanguageParser
{

    /* ################ Décomposition et transformation en objet correspondant ################ */

    /**
     * Décompose la méthode pour construire l'objet MethodBO avec ses parents.
     * 
     * @param pAbsoluteMethodName le nom absolu de la méthode
     * @param pFileName le nom absolu du fichier à partir du projet
     * @return la méthode correspondant aux paramètres
     */
    public MethodBO getMethod( String pAbsoluteMethodName, String pFileName );

    /**
     * Décompose la classe pour construire l'objet ClassBO avec ses parents.
     * 
     * @param pAbsoluteClassName le nom entièrement qualifié d'une classe
     * @return la classe sous forme de ClassBO
     */
    public ClassBO getClass( String pAbsoluteClassName );

    /**
     * Retourne la chaîne pAbsoluteName avant le dernier séparateur ou null si il n'y a pas de séparateur.
     * 
     * @param pAbsoluteName le nom absolu du fils
     * @return le nom absolu du parent
     */
    public String getParentName( String pAbsoluteName );
}
