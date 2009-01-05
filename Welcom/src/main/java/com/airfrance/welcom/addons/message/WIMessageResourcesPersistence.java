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
package com.airfrance.welcom.addons.message;

import java.util.Vector;

/**
 * @created Jul 22, 2003
 * @version InterFacePersistence.java
 */
public interface WIMessageResourcesPersistence
{
    /**
     * Method getMessagesByLanguage. reads all Messages for given locale from persistence store
     * 
     * @param locale : locale
     * @return Vector : Tous les message dispo pour cette locale
     */
    public Vector getAppMessagesByLocale( String locale );

    /**
     * Liste des locales utilisables
     * 
     * @return Liste des locales
     */
    public Vector getavailableLocales();
}