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
package org.squale.welcom.outils.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.welcom.outils.jdbc.wrapper.ParameterizedStatement;


/**
 * @author M327837 Surcouche du
 * @link ParametrizedStatement Effectue le Tracing SQL dans l'appender SQLLoggin Dump au format Oracle Memorise si la
 *       connexion est deja fermé
 */
public class WParameterizedStatement
    extends ParameterizedStatement
{
    /** logger */
    private static Log log = LogFactory.getLog( "SQLLogging" );

    /** Taille max pour l'affichage du nom de l'utlisateur */
    private static final int MAX_LENGTH_USER = 10;

    /** Nom de l'utilisateur */
    private String user = WStatement.DEFAULTUSER;

    /** Memorise si le statement est clos */
    private boolean isclose = true;

    /** Paramtres passé dans la requete */
    private List myparameters = null;

    /**
     * Constructeur
     * 
     * @param pSql : Requete SQL
     * @param connection : Connexion sur laquelle elle est effectué
     * @param parameters : liste des parametres a (remplamecement de ?)
     * @throws SQLException : Probleme SQL
     */
    public WParameterizedStatement( final String pSql, final Connection connection, final List parameters )
        throws SQLException
    {
        super( pSql, connection, parameters );
        this.myparameters = parameters;
        isclose = false;
    }

    /**
     * Constructeur
     * 
     * @param pSQL : Requete SQL
     * @param pConnection : Connexion sur laquelle elle est effectué
     * @param pParameters : liste des parametres a (remplamecement de ?)
     * @param pUser : nom de l'utlisateur
     * @throws SQLException : Probleme SQL
     */
    public WParameterizedStatement( final String pSQL, final Connection pConnection, final List pParameters,
                                    final String pUser )
        throws SQLException
    {
        super( pSQL, pConnection, pParameters );

        if ( user != null )
        {
            this.user = pUser;
        }

        this.myparameters = pParameters;
        isclose = false;
    }

    /**
     * Execute la requete de consultation
     * 
     * @throws SQLException : Probleme SQL
     * @return ResultSet : Resultset de la requete
     */
    public ResultSet executeQuery()
        throws SQLException
    {
        LocationInfo info;
        info = new LocationInfo( new Throwable(), "WStatement" );

        final String className = info.getClassName();
        final String lineNumber = info.getLineNumber();
        String status = "Ok";
        final long start = new Date().getTime();

        try
        {
            final ResultSet rs = super.executeQuery();

            return rs;
        }
        catch ( final SQLException sqle )
        {
            status = "Erreur [" + sqle.getMessage() + "]";
            throw sqle;
        }
        finally
        {
            final long end = new Date().getTime();

            if ( WJdbc.isEnabledTrace() )
            {
                if ( ( end - start ) < WStatement.LIMITSPEED )
                {
                    log.info( "-> QUERY (" + ( end - start ) + " ms )  :" + getUser() + ": " + getSQL() + " - ("
                        + className + "," + lineNumber + ") - (" + status + ")" );
                }
                else
                {
                    log.warn( "-> QUERY (" + ( end - start ) + " ms )  :" + getUser() + ": " + getSQL() + " - ("
                        + className + "," + lineNumber + ") - (" + status + ")" );
                }
            }
        }
    }

    /**
     * Execute la requete de modification
     * 
     * @throws SQLException : Probleme SQL
     * @return int : Nombre d'element modifiés
     */
    public int executeUpdate()
        throws SQLException
    {
        LocationInfo info;
        info = new LocationInfo( new Throwable(), "WStatement" );

        final String className = info.getClassName();
        final String lineNumber = info.getLineNumber();
        String status = "Ok";

        final long start = new Date().getTime();

        try
        {
            return super.executeUpdate();
        }
        catch ( final SQLException sqle )
        {
            status = "Erreur [" + sqle.getMessage() + "]";
            throw sqle;
        }
        finally
        {
            final long end = new Date().getTime();

            if ( WJdbc.isEnabledTrace() )
            {
                if ( ( end - start ) < WStatement.LIMITSPEED )
                {
                    log.info( "-> UPDATE (" + ( end - start ) + " ms )  :" + getUser() + ": " + getSQL() + " - ("
                        + className + "," + lineNumber + ") - (" + status + ")" );
                }
                else
                {
                    log.warn( "-> UPDATE (" + ( end - start ) + " ms )  :" + getUser() + ": " + getSQL() + " - ("
                        + className + "," + lineNumber + ") - (" + status + ")" );
                }
            }
        }
    }

    /**
     * Trace et retourn le resultSet
     * 
     * @return : resuturn le resusultSet du stament
     * @throws SQLException : Probleme SQL
     */
    public ResultSet getResultSet()
        throws SQLException
    {
        LocationInfo info;
        info = new LocationInfo( new Throwable(), "WParameterizedStatement" );

        final String className = info.getClassName();
        final String lineNumber = info.getLineNumber();

        log.info( "-> UPDATE :" + getUser() + ": " + getSQL() + " - (" + className + "," + lineNumber + ")" );

        return super.getStatement().getResultSet();
    }

    /**
     * Fermeture de la connexion
     * 
     * @throws SQLException : Probleme SQL
     */
    public void close()
        throws SQLException
    {
        if ( !isClose() )
        {
            user = WStatement.DEFAULTUSER;
            super.close();
            isclose = true;
        }
    }

    /**
     * @return Retourn Vrai Si la connexion est deja fermé
     */
    public boolean isClose()
    {
        return isclose;
    }

    /**
     * Gets the user
     * 
     * @return Returns a String
     */
    public String getUser()
    {
        if ( user.length() < MAX_LENGTH_USER )
        {
            while ( user.length() < MAX_LENGTH_USER )
            {
                user += " ";
            }
        }

        return user;
    }

    /**
     * Sets the user
     * 
     * @param pUser The user to set
     */
    public void setUser( final String pUser )
    {
        this.user = pUser;
    }

    /**
     * Formate la requete SQL comme elle devrait être vue par Oracle
     * 
     * @return : chaine de caracteres representant la chaine Oracle
     */
    public String getSQL()
    {
        return getSQLOracle( super.getSQL(), myparameters );
    }

    /**
     * Formate la requete SQL comme elle devrait être vue par Oracle
     * 
     * @param mysql : Requete SQL a transformer
     * @param myparameters : Liste de parametres a remplacer
     * @return : chaine de caracteres representant la chaine Oracle
     */
    public static String getSQLOracle( final String mysql, final List myparameters )
    {
        Object parameter = null;
        int i = 0;
        final Object parameters[] = myparameters.toArray();
        final StringTokenizer stringTokenizer = new StringTokenizer( mysql, "?" );
        final StringBuffer sb = new StringBuffer();
        while ( stringTokenizer.hasMoreTokens() )
        {
            sb.append( stringTokenizer.nextToken() );
            if ( i < parameters.length )
            {
                parameter = parameters[i++];
                if ( parameter == null )
                {
                    sb.append( "null" );
                }
                else if ( parameter instanceof String )
                {
                    sb.append( "'" + formatQuote( parameter.toString() ) + "'" );
                }
                else if ( parameter instanceof InputStream )
                {
                    sb.append( " " );
                }
                else
                {
                    sb.append( parameter.toString() );
                }
            }
        }

        return sb.toString();
    }

    /**
     * Double les quotes pour la requetes SQL
     * 
     * @param st : Chiane d'origine
     * @return : Chaine requoté
     */
    private final static String formatQuote( final String st )
    {
        if ( st == null )
        {
            return "";
        }

        String newString = "";
        String tempo = "";

        // Doubler la ' pour les requetes SQL
        final java.util.StringTokenizer token = new java.util.StringTokenizer( st, "'", true );

        while ( token.hasMoreTokens() )
        {
            tempo = token.nextToken();

            if ( tempo.equals( "'" ) == true )
            {
                newString = newString + "''";
            }
            else
            {
                newString = newString + tempo;
            }
        }

        return newString;
    }
}