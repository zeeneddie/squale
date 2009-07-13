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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating and executing JDBC statements.
 * 
 * @version $Id: StatementAssembly.java,v 1.7 2001/05/02 14:15:17 hship Exp $
 */
public class StatementAssembly
{
    /** StringBUffer pour la generation de la requete sql */
    protected StringBuffer buffer = new StringBuffer();

    /** Liste de parametres de la requetes sql */
    protected List parameters;

    /** Longueur d'un ligne */
    private int lineLength;

    /** Lonngeur maximale d'une Ligne */
    private static final int MAX_LINE_LENGTH = 80;

    /** Indentation de la chaine pour l'affichage de la requete */
    private static final int INDENT = 5;

    /** Parametre modifiable pour la longuer d'un ligne a la construction */
    private int maxLineLength = MAX_LINE_LENGTH;

    /** Parametre modifiable pour le nombre de tabulation à la contuction */
    private int indent = INDENT;

    /**
     * Default constructor; uses a maximum line length of 80 and an indent of 5.
     */
    public StatementAssembly()
    {
    }

    /**
     * Contructeur d'un statement
     * 
     * @param pMaxLineLength Lnngueur de ligne max
     * @param pIndent Nombre de tabludation
     */
    public StatementAssembly( final int pMaxLineLength, final int pIndent )
    {
        this.maxLineLength = pMaxLineLength;
        this.indent = pIndent;
    }

    /**
     * @since 0.2.10
     * @param value Construit la chaine dynamiquement avec un double
     */
    public void add( final double value )
    {
        add( Double.toString( value ) );
    }

    /**
     * @since 0.2.10
     * @param value Construit la chaine dynamiquement avec un float
     */
    public void add( final float value )
    {
        add( Float.toString( value ) );
    }

    /**
     * @since 0.2.10
     * @param value Construit la chaine dynamiquement avec un int
     */
    public void add( final int value )
    {
        add( Integer.toString( value ) );
    }

    /**
     * @since 0.2.10
     * @param value Construit la chaine dynamiquement avec un long
     */
    public void add( final long value )
    {
        add( Long.toString( value ) );
    }

    /**
     * Adds an arbitrary object to the SQL by invoking {@link Object#toString()}. This is typically used with
     * {@link Integer}, {@link Double}, etc. Note that this will not work well with {@link Boolean} ... invoke
     * {@link #add(boolean)} instead.
     * 
     * @param value Construit la chaine dynamiquement avec un le tostring de l'objet
     * @since 0.2.10
     */
    public void add( final Object value )
    {
        add( value.toString() );
    }

    /**
     * Adds text to the current line, unless that would make the line too long, in which case a new line is started (and
     * indented) before adding the text.
     * <p>
     * Text is added as-is, with no concept of quoting. To add arbitrary strings (such as in a where clause), use
     * addParameter().
     * 
     * @param text , chaine a concatener
     */
    public void add( String text )
    {
        int textLength;

        text = text.trim() + " ";

        textLength = text.length();

        if ( ( lineLength + textLength ) > maxLineLength )
        {
            buffer.append( '\n' );

            for ( int i = 0; i < indent; i++ )
            {
                buffer.append( ' ' );
            }

            lineLength = indent;
        }

        buffer.append( text );
        lineLength += textLength;
    }

    /**
     * @since 0.2.10
     * @param value Construit la chaine dynamiquement avec un short
     */
    public void add( final short value )
    {
        add( Short.toString( value ) );
    }

    /**
     * Adds a boolean value as either '0' or '1'.
     * 
     * @since 0.2.10
     * @param value Construit la chaine dynamiquement avec un value
     */
    public void add( final boolean value )
    {
        add( value ? "1" : "0" );
    }

    /**
     * @since 0.2.10
     */

    /**
     * Permet de contruire rapidement une liste de valeur 1,2,3,4 (separateur ',' et tableau de int)
     * 
     * @param items : Tableau de int
     * @param seperator : separateur entre
     */
    public void addList( final int items[], final String seperator )
    {
        for ( int i = 0; i < items.length; i++ )
        {
            if ( i > 0 )
            {
                addSep( seperator );
            }

            add( items[i] );
        }
    }

    /**
     * @since 0.2.10 Permet de contruire rapidement une liste de valeur "1","2","3","4" (separateur ',' et tableau de
     *        object)
     * @param items : Tableau d'Object (toString)
     * @param seperator : separateur entre
     */
    public void addList( final Object items[], final String seperator )
    {
        for ( int i = 0; i < items.length; i++ )
        {
            if ( i > 0 )
            {
                addSep( seperator );
            }

            add( items[i].toString() );
        }
    }

    /**
     * Permet de contruire rapidement une liste de valeur "1","2","3","4" (separateur ',' et tableau de String)
     * 
     * @param items : Tableau de String (toString)
     * @param seperator : separateur entre
     */
    public void addList( final String items[], final String seperator )
    {
        for ( int i = 0; i < items.length; i++ )
        {
            if ( i > 0 )
            {
                addSep( seperator );
            }

            add( items[i] );
        }
    }

    /**
     * Adds a parameter to the statement. Adds a question mark to the SQL and stores the value for later.
     * 
     * @param value : Ajoute un object en parametres sql
     */
    public void addParameter( final Object value )
    {
        if ( parameters == null )
        {
            parameters = new ArrayList();
        }

        parameters.add( value );

        add( "?" );
    }

    /**
     * Adds a parameter with some associated text, which should include the question mark used to represent the
     * parameter in the SQL.
     * 
     * @param text : Chaine a mettre dans la requette sql
     * @param value : Ajoute un object en parametres sql
     */
    public void addParameter( final String text, final Object value )
    {
        if ( parameters == null )
        {
            parameters = new ArrayList();
        }

        parameters.add( value );

        add( text );
    }

    /**
     * Adds a seperator (usually a comma and a space) to the current line, regardless of line length. "this is purely
     * aesthetic ... it just looks odd if a seperator gets wrapped to a new line by itself.
     * 
     * @param text : Adds a seperator (usually a comma and a space) to the current line,
     */
    public void addSep( final String text )
    {
        buffer.append( text );
        lineLength += text.length();
    }

    /**
     * @param connection donne la connection JDBC sur laquelle ouvrir le statement
     * @return Creates and returns a {@link IStatement} based on the SQL and parameters acquired.
     * @throws SQLException : Probleme lors de la creation du Statement (@link ParameterizedStatement ou
     * @link SimpleStatement)
     */
    public IStatement createStatement( final Connection connection )
        throws SQLException
    {
        String sql;
        IStatement result;

        sql = buffer.toString();

        if ( parameters == null )
        {
            result = new SimpleStatement( sql, connection );
        }
        else
        {
            result = new ParameterizedStatement( sql, connection, parameters );
        }

        return result;
    }

    /**
     * @return Number of spaces to indent continuation lines by.
     */
    public int getIndent()
    {
        return indent;
    }

    /**
     * @return Maximum length of a line.
     */
    public int getMaxLineLength()
    {
        return maxLineLength;
    }

    /**
     * Starts a new line, without indenting.
     */
    public void newLine()
    {
        if ( buffer.length() != 0 )
        {
            buffer.append( '\n' );
        }

        lineLength = 0;
    }

    /**
     * Starts a new line, then adds the given text.
     * 
     * @param text : Ajoute le texte
     */
    public void newLine( final String text )
    {
        if ( buffer.length() != 0 )
        {
            buffer.append( '\n' );
        }

        buffer.append( text );

        lineLength = text.length();
    }

    /**
     * @return Retoune un visu de la chaine sql produite
     */
    public String toString()
    {
        StringBuffer description;

        description = new StringBuffer( super.toString() );

        description.append( "[SQL=\n<" );
        description.append( buffer );
        description.append( "\n>" );

        if ( parameters != null )
        {
            final int count = parameters.size();

            for ( int i = 0; i < count; i++ )
            {
                final Object parameter = parameters.get( i );

                description.append( " ?" );
                description.append( i + 1 );
                description.append( '=' );

                if ( parameter == null )
                {
                    description.append( "null" );
                }
                else
                {
                    description.append( '(' );
                    description.append( parameter.getClass().getName() );
                    description.append( ") " );
                    description.append( parameter );
                }
            }
        }

        description.append( ']' );

        return description.toString();
    }
}