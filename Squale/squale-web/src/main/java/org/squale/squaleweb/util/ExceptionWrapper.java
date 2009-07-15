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
package com.airfrance.squaleweb.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

import com.airfrance.jraf.commons.exception.JrafException;

/**
 * Traitement des exceptions Cette classe permet de remonter des exceptions au niveau de l'utilisateur via une JSP Une
 * clef de configuration permet d'activer la génération du StackTrace au niveau de la réponse renvoyée par la JSP.
 */
public class ExceptionWrapper
{
    /** Logger */
    // On n'utilise pas de logger JRAF car cette classe
    // peut être appelée pendant la phase d'initialisation
    private static Log logger = LogFactory.getLog( ExceptionWrapper.class );

    /** Nom du bundle pour les exceptions */
    private static final String EXCEPTION_BUNDLE_NAME = "com.airfrance.squaleweb.util.exception";

    /** Nom de la clef pour l'activation du dump */
    private static final String DUMP_EXCEPTION_KEY = "dumpExceptionInJsp";

    /** Booléen d'état de dump des exceptions */
    static private Boolean dumpException = null;

    /**
     * @return true si le dump est activé
     */
    static private boolean isDumpEnabled()
    {
        // Init lazy
        if ( dumpException == null )
        {
            try
            {
                // Lecture du fichier de ressources contenant la valeur
                ResourceBundle bundle = PropertyResourceBundle.getBundle( EXCEPTION_BUNDLE_NAME );
                String conf = bundle.getString( DUMP_EXCEPTION_KEY );
                if ( ( conf != null ) && ( conf.equals( "true" ) ) )
                {
                    dumpException = Boolean.TRUE;
                }
                else
                {
                    dumpException = Boolean.FALSE;
                }
            }
            catch ( MissingResourceException e )
            {
                logger.fatal( "Impossible d'obtenir " + EXCEPTION_BUNDLE_NAME, e );
                // Valeur par défaut
                dumpException = Boolean.FALSE;
            }
        }
        return dumpException.booleanValue();
    }

    /**
     * Sauvegarde de l'exception
     * 
     * @param pRequest requête
     * @param pException exception
     */
    static public void saveException( HttpServletRequest pRequest, Exception pException )
    {
        pRequest.setAttribute( Globals.EXCEPTION_KEY, pException );
    }

    /**
     * Récupération des détails de l'exception
     * 
     * @param pRequest requête
     * @return détails de l'exception
     */
    static public String getExceptionDetail( HttpServletRequest pRequest )
    {
        // Obtention de l'exception
        Exception exception = (Exception) pRequest.getAttribute( Globals.EXCEPTION_KEY );
        return getExceptionDetail( exception );
    }

    /**
     * Obtention du détail d'une exception
     * 
     * @param pException exception à détailler
     * @return détail de l'exception (au format HTML) en fonction de la configuration (@see #isDumpEnabled())
     */
    static public String getExceptionDetail( Throwable pException )
    {
        // On log l'exception au cas où
        logger.error( "Exception survenue ", pException );
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter( sw );
        if ( isDumpEnabled() )
        {
            fillExceptionTrace( pException, pw );
        }
        try
        {
            sw.close();
        }
        catch ( IOException e )
        {
            // Impossible à obtenir sur un StringWriter.close
        }
        pw.close();
        return sw.toString();
    }

    /**
     * Récupération des détails de l'exception
     * 
     * @param pRequest requête
     * @param pException exception
     * @return détails de l'exception
     */
    static public String getExceptionDetail( HttpServletRequest pRequest, Throwable pException )
    {
        Throwable exception = pException;
        if ( exception == null )
        {
            // Obtention de l'exception
            exception = (Exception) pRequest.getAttribute( Globals.EXCEPTION_KEY );
        }
        return getExceptionDetail( exception );
    }

    /**
     * Extraction du détail de l'exception
     * 
     * @param pException exception
     * @param pw writer
     */
    private static void fillExceptionTrace( Throwable pException, PrintWriter pw )
    {
        // Certains contextes d'appel peuvent déclencher le test suivant
        if ( pException == null )
        {
            return;
        }
        // Extraction des informations sur l'exception elle-même
        dumpException( pException, pw );
        // Extraction du chaînage d'exception en fonction de son type
        if ( pException instanceof ServletException )
        {
            fillExceptionTrace( ( (ServletException) pException ).getRootCause(), pw );
        }
        else if ( pException instanceof JspException )
        {
            fillExceptionTrace( ( (JspException) pException ).getRootCause(), pw );
        }
        else if ( pException instanceof JrafException )
        {
            fillExceptionTrace( ( (JrafException) pException ).getCause(), pw );
        }
        else
        {
            // Rappel récursif sur la cause elle-même
            // Certaines exceptions remontées par websphere
            // ont la particularité de boucler sur la cause
            if ( ( pException.getCause() != null ) && ( pException.getCause() != pException ) )
            {
                fillExceptionTrace( pException.getCause(), pw );
            }
        }
    }

    /**
     * Extraction des informations d'une exception
     * 
     * @param pException exception
     * @param pw writer
     */
    private static void dumpException( Throwable pException, PrintWriter pw )
    {
        pw.println( "<BR><BR><B>Error Message: </B>" + pException.getMessage() + "<BR>" );
        pw.println( "<B>Error Stack: </B><BR>" );
        pw.println( pException.getClass().getName() + ": " + pException.getMessage() );
        StackTraceElement[] elements = pException.getStackTrace();
        for ( int i = 0; i < elements.length; i++ )
        {
            pw.println( "<BR>&nbsp;&nbsp;&nbsp;&nbsp;" + elements[i] );
        }
    }
}
