package com.airfrance.welcom.outils.jdbc.wrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  A wrapper around {@link Statement} or {@link PreparedStatement}.
 *
 *  @version $Id: IStatement.java,v 1.4 2001/05/02 14:15:17 hship Exp $
 */
public interface IStatement {
    /**
     * @return Returns the SQL associated with this statement.
     *
     */
    public String getSQL();

    /**
     *  @return Returns the underlying {@link Statement} (or {@link PreparedStatement}).
     *
     */
    public Statement getStatement();

    /**
     *  Closes the underlying statement, and nulls the reference to it.
     *  @throws SQLException ProblemeSQL sur fermeture
     *
     */
    public void close() throws SQLException;

    /**
     *  Executes the statement as a query, returning a {@link ResultSet}.
     * @throws SQLException ProblemeSQL sur fermeture
     * @return The Resultset of the query
     */
    public ResultSet executeQuery() throws SQLException;

    /**
     *  Executes the statement as an update, returning the number of rows
     *  affected.
     * @throws SQLException ProblemeSQL sur fermeture
     * @return The Resultset of the query
     */
    public int executeUpdate() throws SQLException;
}   