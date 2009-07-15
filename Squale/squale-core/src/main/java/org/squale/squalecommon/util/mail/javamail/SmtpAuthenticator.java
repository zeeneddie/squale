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
package org.squale.squalecommon.util.mail.javamail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * This give PasswordAuthentication needed for the SMTPs authentication 
 */
class SmtpAuthenticator
    extends Authenticator
{

    /**
     * The user name for the SMTP authentication
     */
    private String username;

    /**
     * The password for the smtp authentication
     */
    private String password;

    /**
     * Constructor
     * 
     * @param pUsername The user name for the SMTP authentication
     * @param pPassword The password for the SMTP authentication
     */
    public SmtpAuthenticator( String pUsername, String pPassword )
    {
        username = pUsername;
        password = pPassword;
    }

    /**
     * This method return an object PasswordAuthencication for the smtp authentication
     * 
     * @return a PasswordAuthencication object
     */
    protected PasswordAuthentication getPasswordAuthentication()
    {

        return new PasswordAuthentication( username, password );
    }
};