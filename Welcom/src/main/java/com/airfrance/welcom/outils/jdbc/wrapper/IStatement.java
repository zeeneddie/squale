/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.airfrance.welcom.outils.jdbc.wrapper;

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