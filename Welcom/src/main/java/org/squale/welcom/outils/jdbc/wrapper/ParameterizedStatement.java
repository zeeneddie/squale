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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * A wrapper around {@link PreparedStatement}.
 * 
 * @version $Id: ParameterizedStatement.java,v 1.4 2001/05/02 14:15:17 hship Exp $
 */
public class ParameterizedStatement
    implements IStatement
{

    /** Chaine SQL en interne */
    private String sql;

    /** Statement en interne */
    private PreparedStatement statement;

    /** Liste de parmatres */
    private Object parameters[];

    /**
     * Cree un nouveau prepareStatement
     * 
     * @param pSQL Chaine SQL
     * @param connection Connexion JDBC
     * @param pParameters List des parametres
     * @throws SQLException Problem SQL
     */
    public ParameterizedStatement( final String pSQL, final Connection connection, final List pParameters )
        throws SQLException
    {
        sql = pSQL;

        statement = connection.prepareStatement( sql );

        setupParameters( pParameters );
    }

    /**
     * Document la liste des parametres directment
     * 
     * @param list : Liste des parametres
     * @throws SQLException Problem SQL
     */
    private void setupParameters( final List list )
        throws SQLException
    {
        int i;

        parameters = list.toArray();

        for ( i = 0; i < parameters.length; i++ )
        {
            if ( parameters[i] != null )
            {
                statement.setObject( i + 1, parameters[i] );
            }
            else
            {
                statement.setNull( i + 1, java.sql.Types.VARCHAR );
            }
        }
    }

    /**
     * @return Returns the SQL associated with this statement.
     */
    public String getSQL()
    {
        return sql;
    }

    /**
     * @return Returns the underlying or {@link PreparedStatement}.
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Closes the underlying statement, and nulls the reference to it.
     * 
     * @throws SQLException Problem SQL
     */
    public void close()
        throws SQLException
    {
        statement.close();

        statement = null;
        sql = null;
    }

    /**
     * Executes the statement as a query, returning a {@link ResultSet}.
     * 
     * @return returning a {@link ResultSet}
     * @throws SQLException Problem SQL
     */
    public ResultSet executeQuery()
        throws SQLException
    {
        return statement.executeQuery();
    }

    /**
     * Executes the statement as an update,
     * 
     * @return returning the number of rows affected.
     * @throws SQLException Problem SQL
     */
    public int executeUpdate()
        throws SQLException
    {
        return statement.executeUpdate();
    }

    /**
     * Affiche la requete SQL !
     * 
     * @return trace de la requete SQL
     */
    public String toString()
    {
        StringBuffer buffer;
        int i;
        Object parameter;

        buffer = new StringBuffer( super.toString() );

        buffer.append( "[SQL=\n<" );
        buffer.append( sql );
        buffer.append( "\n>" );

        for ( i = 0; i < parameters.length; i++ )
        {
            parameter = parameters[i];

            buffer.append( " ?" );
            buffer.append( i + 1 );
            buffer.append( '=' );

            if ( parameter == null )
            {
                buffer.append( "null" );
            }
            else
            {
                buffer.append( '(' );
                buffer.append( parameter.getClass().getName() );
                buffer.append( ") " );
                buffer.append( parameter );
            }
        }

        buffer.append( ']' );

        return buffer.toString();
    }
}