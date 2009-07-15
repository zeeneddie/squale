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
package org.squale.squaleweb.applicationlayer.formbean;


/**
 * actionForm associate to the login JSP
 */
public class UserLoginForm
    extends RootForm
{

    /**
     * The login of the user
     */
    private String login;

    /**
     * The password of the user
     */
    private String pass;

    /**
     * Default constructor
     */
    public UserLoginForm()
    {

    }

    /**
     * Getter for the login
     * 
     * @return the login
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * Setter for the login
     * 
     * @param login : the login of the user
     */
    public void setLogin( String login )
    {
        this.login = login;
    }

    /**
     * Getter for the password
     * 
     * @return the password
     */
    public String getPass()
    {
        return pass;
    }

    /**
     * Setter for the password
     * 
     * @param pass : the password of the user
     */
    public void setPass( String pass )
    {
        this.pass = pass;
    }

}
