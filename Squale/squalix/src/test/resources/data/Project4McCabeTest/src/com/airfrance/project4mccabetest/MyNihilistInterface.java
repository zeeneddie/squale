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
package org.squale.project4mccabetest;

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
