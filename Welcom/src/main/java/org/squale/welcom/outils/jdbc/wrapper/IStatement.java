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
package org.squale.welcom.outils.jdbc.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A wrapper around {@link Statement} or {@link PreparedStatement}.
 * 
 * @version $Id: IStatement.java,v 1.4 2001/05/02 14:15:17 hship Exp $
 */
public interface IStatement
{
    /**
     * @return Returns the SQL associated with this statement.
     */
    public String getSQL();

    /**
     * @return Returns the underlying {@link Statement} (or {@link PreparedStatement}).
     */
    public Statement getStatement();

    /**
     * Closes the underlying statement, and nulls the reference to it.
     * 
     * @throws SQLException ProblemeSQL sur fermeture
     */
    public void close()
        throws SQLException;

    /**
     * Executes the statement as a query, returning a {@link ResultSet}.
     * 
     * @throws SQLException ProblemeSQL sur fermeture
     * @return The Resultset of the query
     */
    public ResultSet executeQuery()
        throws SQLException;

    /**
     * Executes the statement as an update, returning the number of rows affected.
     * 
     * @throws SQLException ProblemeSQL sur fermeture
     * @return The Resultset of the query
     */
    public int executeUpdate()
        throws SQLException;
}