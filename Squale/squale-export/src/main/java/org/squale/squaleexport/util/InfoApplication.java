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
package org.squale.squaleexport.util;

/**
 * This {@link Enum} list all the application information we want recover in the export
 */
public enum InfoApplication
{

    /**
     * Does the application is in its initial development phase ?
     */
    IS_IN_INITIAL_PHASE,

    /**
     * Global cost of the application
     */
    GLOBAL_COST,

    /**
     * Development cost of the application
     */
    DEV_COST,

    /**
     * Does the quality approach start at the beginning of the projet ?
     */
    QUALITY_APPROACH_AT_BEGINNING

}
