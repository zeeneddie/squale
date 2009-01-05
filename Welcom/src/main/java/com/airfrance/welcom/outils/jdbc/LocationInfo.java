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
package com.airfrance.welcom.outils.jdbc;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Recuperation de LocationInfo de log4j
 */
public class LocationInfo
    implements java.io.Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 6111845511791831389L;

    /** Ligne spérator */
    public final static String LINE_SEP = System.getProperty( "line.separator" );

    /** longeur du line separator */
    public final static int LINE_SEP_LEN = LINE_SEP.length();

    /**
     * Caller's line number.
     */
    protected transient String lineNumber;

    /**
     * Caller's file name.
     */
    protected transient String fileName;

    /**
     * Caller's fully qualified class name.
     */
    protected transient String className;

    /**
     * Caller's method name.
     */
    protected transient String methodName;

    /**
     * All available caller information, in the format
     * <code>fully.qualified.classname.of.caller.methodName(Filename.java:line)</code>
     */
    private String fullInfo;

    /** writer */
    private static StringWriter sw = new StringWriter();

    /** writer */
    private static PrintWriter pw = new PrintWriter( sw );

    /**
     * When location information is not available the constant <code>NA</code> is returned. Current value of this
     * string constant is <b>?</b>.
     */
    public final static String NA = "?";

    /**
     * Instantiate location information based on a Throwable. We expect the Throwable <code>t</code>, to be in the
     * format
     * 
     * <pre>
     *           java.lang.Throwable
     *           ...
     *             at org.apache.log4j.PatternLayout.format(PatternLayout.java:413)
     *             at org.apache.log4j.FileAppender.doAppend(FileAppender.java:183)
     *           at org.apache.log4j.Category.callAppenders(Category.java:131)
     *           at org.apache.log4j.Category.log(Category.java:512)
     *           at callers.fully.qualified.className.methodName(FileName.java:74)
     *             ...
     * </pre>
     * 
     * <p>
     * However, we can also deal with JIT compilers that "lose" the location information, especially between the
     * parentheses.
     * 
     * @param t le Throwable
     * @param fqnOfCallingClass la classe sur la quelle on veux l'info
     */
    public LocationInfo( final Throwable t, final String fqnOfCallingClass )
    {
        if ( t == null )
        {
            return;
        }

        String s;
        // Protect against multiple access to sw.
        synchronized ( sw )
        {
            t.printStackTrace( pw );
            s = sw.toString();
            sw.getBuffer().setLength( 0 );
        }
        int ibegin, iend;

        // Given the current structure of the package, the line
        // containing "org.apache.log4j.Category." should be printed just
        // before the caller.

        // This method of searching may not be fastest but it's safer
        // than counting the stack depth which is not guaranteed to be
        // constant across JVM implementations.
        ibegin = s.lastIndexOf( fqnOfCallingClass );
        if ( ibegin == -1 )
        {
            return;
        }

        ibegin = s.indexOf( LINE_SEP, ibegin );
        if ( ibegin == -1 )
        {
            return;
        }
        ibegin += LINE_SEP_LEN;

        // determine end of line
        iend = s.indexOf( LINE_SEP, ibegin );
        if ( iend == -1 )
        {
            return;
        }

        // back up to first blank character
        ibegin = s.lastIndexOf( "at ", iend );
        if ( ibegin == -1 )
        {
            return;
        }
        // Add 3 to skip "at ";
        ibegin += 3;
        // everything between is the requested stack item
        this.fullInfo = s.substring( ibegin, iend );
    }

    /**
     * Return the fully qualified class name of the caller making the logging request.
     * 
     * @return the fully qualified class name of the caller making the logging request.
     */
    public String getClassName()
    {
        if ( fullInfo == null )
        {
            return NA;
        }
        if ( className == null )
        {
            // Starting the search from '(' is safer because there is
            // potentially a dot between the parentheses.
            int iend = fullInfo.lastIndexOf( '(' );
            if ( iend == -1 )
            {
                className = NA;
            }
            else
            {
                iend = fullInfo.lastIndexOf( '.', iend );

                // This is because a stack trace in VisualAge looks like:

                // java.lang.RuntimeException
                // java.lang.Throwable()
                // java.lang.Exception()
                // java.lang.RuntimeException()
                // void test.test.B.print()
                // void test.test.A.printIndirect()
                // void test.test.Run.main(java.lang.String [])
                final int ibegin = 0;
                if ( iend == -1 )
                {
                    className = NA;
                }
                else
                {
                    className = this.fullInfo.substring( ibegin, iend );
                }
            }
        }
        return className;
    }

    /**
     * Return the file name of the caller.
     * <p>
     * This information is not always available.
     * 
     * @return the file name of the caller.
     */
    public String getFileName()
    {
        if ( fullInfo == null )
        {
            return NA;
        }

        if ( fileName == null )
        {
            final int iend = fullInfo.lastIndexOf( ':' );
            if ( iend == -1 )
            {
                fileName = NA;
            }
            else
            {
                final int ibegin = fullInfo.lastIndexOf( '(', iend - 1 );
                fileName = this.fullInfo.substring( ibegin + 1, iend );
            }
        }
        return fileName;
    }

    /**
     * Returns the line number of the caller.
     * <p>
     * This information is not always available.
     * 
     * @return the line number of the caller.
     */
    public String getLineNumber()
    {
        if ( fullInfo == null )
        {
            return NA;
        }

        if ( lineNumber == null )
        {
            final int iend = fullInfo.lastIndexOf( ')' );
            final int ibegin = fullInfo.lastIndexOf( ':', iend - 1 );
            if ( ibegin == -1 )
            {
                lineNumber = NA;
            }
            else
            {
                lineNumber = this.fullInfo.substring( ibegin + 1, iend );
            }
        }
        return lineNumber;
    }

    /**
     * Returns the method name of the caller.
     * 
     * @return the method name of the caller.
     */
    public String getMethodName()
    {
        if ( fullInfo == null )
        {
            return NA;
        }
        if ( methodName == null )
        {
            final int iend = fullInfo.lastIndexOf( '(' );
            final int ibegin = fullInfo.lastIndexOf( '.', iend );
            if ( ibegin == -1 )
            {
                methodName = NA;
            }
            else
            {
                methodName = this.fullInfo.substring( ibegin + 1, iend );
            }
        }
        return methodName;
    }
}
