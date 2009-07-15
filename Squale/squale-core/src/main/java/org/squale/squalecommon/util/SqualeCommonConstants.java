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
package org.squale.squalecommon.util;

/**
 * 
 */
public class SqualeCommonConstants
{

    // Clés pour la gestion des mails

    /** Les destinataires sont seulement les admins */
    public final static String ONLY_ADMINS = "onlyAdmins";

    /** Les destinataires sont seulement les Managers */
    public final static String ONLY_MANAGERS = "onlyManagers";

    /** Les destinataires sont les managers de l'appli et les lecteurs */
    public final static String MANAGERS_AND_READERS = "managersAndReaders";

    /** Les destinataires sont seulement les admins */
    public final static String MANAGERS_AND_ADMINS = "managersAndAdmins";

    // Constantes pour gérer les différents status des composants par rapport au plan d'action
    /**
     * Tous les composants
     */
    public final static String ALL_COMPONENTS = "components.all";

    /**
     * Les composants exclus du plan d'action
     */
    public final static String EXCLUDED_FROM_PLAN_COMPONENTS = "components.excluded";

    /**
     * les composants inclus dans le plan d'action
     */
    public final static String INCLUDED_FROM_PLAN_COMPONENTS = "components.included";
}
