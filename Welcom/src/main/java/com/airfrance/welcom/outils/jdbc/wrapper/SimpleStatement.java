package com.airfrance.welcom.outils.jdbc.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  A wrapper around {@link Statement}.
 *
 *  @version $Id: SimpleStatement.java,v 1.4 2001/05/02 14:15:17 hship Exp $
 *
 */
public class SimpleStatement implements IStatement {

    /** Chaine sql en interne */
    private String sql;
    
    /** Statement ouvert pour execution de la requete */ 
    private Statement statement;

    /**
     * @param pSQL : Chaine SQL
     * @param connection : Connexion BD
     * @throws SQLException : Probleme SQL dansle requete
     */
    public SimpleStatement(final String pSQL, final Connection connection) throws SQLException {
        sql = pSQL;
        this.statement = connection.createStatement();
    }

    /**
     * @return Returns the SQL associated with this statement.
     * 
     */
    public String getSQL() {
        return sql;
    }

    /**
     *  @return Returns the underlying {@link Statement}.
     *
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Closes the underlying statement, and nulls the reference to it.
     * @throws SQLException : Probleme SQL dansle requete
     *
     */
    public void close() throws SQLException {
        statement.close();

        statement = null;
        sql = null;
    }

    /**
     *  Executes the statement as a query,
     * @return returning a {@link ResultSet}.
     * @throws SQLException : Probleme SQL dansle requete
     *
     */
    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery(sql);
    }

    /**
     *  Executes the statement as an update, 
     * @return returning the number of rows affected.
     * @throws SQLException : Probleme SQL dansle requete
     *
     */
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate(sql);
    }

    /**
     * @return retourne la chaine sql
     */
    public String toString() {
        StringBuffer buffer;

        buffer = new StringBuffer(super.toString());

        buffer.append("[SQL=<\n");
        buffer.append(sql);
        buffer.append("\n>]");

        return buffer.toString();
    }
}