/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalix.util.parser;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;

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
