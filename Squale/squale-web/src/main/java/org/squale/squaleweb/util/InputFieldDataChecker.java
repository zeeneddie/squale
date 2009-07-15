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
package org.squale.squaleweb.util;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Utility class used to check input field data against specific regular expressions.
 */
public enum InputFieldDataChecker
{
    /**
     * Regexp for user IDs.
     */
    USER_ID( "user.id" ),

    /**
     * Regexp for tag and tag category names.
     */
    TAG_NAME( "tag.name" );

    /**
     * The pattern used to check the input field data.
     */
    private final Pattern patternToCheck;

    /**
     * The property file that actually stores the regexp.
     */
    private static final String PROPERTY_FILENAME = "org.squale.squaleweb.util.input-field-data-checker";

    /**
     * Private constructor with the property used to retrieve the regexp needed to construct the pattern. The property
     * is looked for in the "input-field-data-checker.properties" file of this package.
     * 
     * @param patternProperty the property
     */
    private InputFieldDataChecker( String patternProperty )
    {
        String pattern = ResourceBundle.getBundle( PROPERTY_FILENAME ).getString( patternProperty ).trim();
        this.patternToCheck = Pattern.compile( pattern );
    }

    /**
     * Checks the given string with the pattern of the class.
     * 
     * @param stringToCheck an input field data string to check
     * @return true if it matches the pattern of the class.
     */
    public boolean check( String stringToCheck )
    {
        return patternToCheck.matcher( stringToCheck ).matches();
    }

}
