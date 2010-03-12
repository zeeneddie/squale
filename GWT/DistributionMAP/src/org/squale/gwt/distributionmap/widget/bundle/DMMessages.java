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
package org.squale.gwt.distributionmap.widget.bundle;

import com.google.gwt.i18n.client.Constants;

/**
 * I18n interface for the DMap
 * 
 * @author Fabrice BELLINGARD
 */
public interface DMMessages
    extends Constants
{
    @DefaultStringValue("Grade")
    String grade();

    @DefaultStringValue("Error while trying to display the Distribution Map...")
    String errorDisplayingDMap();
}
