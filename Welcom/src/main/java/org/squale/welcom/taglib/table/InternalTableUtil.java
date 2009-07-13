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
package org.squale.welcom.taglib.table;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.field.util.LayoutUtils;


/*
 * Créé le 23 janv. 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class InternalTableUtil
{
    /** logger */
    private static Log log = LogFactory.getLog( InternalTableUtil.class );

    /** Constante */
    private static final String TABLE_TAG_KEY_SORT = "org.squale.welcom.tableTag";

    /**
     * Supprime le trie d'un table
     * 
     * @param session : Session en cour
     * @param name : Nom du bean
     * @param property : property
     */
    public static void resetSortOfTable( final HttpSession session, final String name, final String property )
    {

        ListColumnSort.resetKeySortOfTable( session, name, property );

    }

    /**
     * Retourne la collection trié
     * 
     * @param session : Session en cour
     * @param name : Nom du bean
     * @return la collection triée
     */
    public static Collection getSortedTable( final HttpSession session, final String name )
    {
        return getSortedTable( session, name, null );
    }

    /**
     * @param request la request
     * @return true si on a conserve le pagenumber
     */
    public static boolean rememberPageNumber( final ServletRequest request )
    {
        final String buttons = WelcomConfigurator.getMessage( WelcomConfigurator.TABLES_RELOAD_BUTTONS );
        final String submit = request.getParameter( "Submit" );
        if ( ( submit != null ) && ( buttons.toUpperCase().indexOf( submit.toUpperCase() ) >= 0 ) )
        {
            return false;
        }
        return true;
    }

    /**
     * Retourne la collection trié
     * 
     * @param session : Session en cour
     * @param name : Nom du bean
     * @param property : Nom de la property du bean
     * @return la collection triée
     */
    public static Collection getSortedTable( final HttpSession session, final String name, final String property )
    {

        Collection tableDisplay = getTable( session, name, property );

        if ( tableDisplay != null )
        {
            ListColumnSort listColumnSort = ListColumnSort.getKeySortOfTable( session, name, property );
            if ( !listColumnSort.isEmpty() )
            {
                Collections.sort( (List) tableDisplay, new ColComparator( listColumnSort ) );
            }
        }

        return tableDisplay;
    }

    /**
     * Retourne la collection non triée
     * 
     * @param session : Session en cour
     * @param name : Nom du bean
     * @param property : Nom de la property du bean
     * @return la collection triée
     */
    private static Collection getTable( final HttpSession session, final String name, final String property )
    {
        Collection tableDisplay = null;

        final Object o = session.getAttribute( name );
        if ( !GenericValidator.isBlankOrNull( property ) )
        {
            try
            {
                tableDisplay = (Collection) LayoutUtils.getProperty( o, property );
            }
            catch ( final Exception ex )
            {
                log.error( ex, ex );
            }
        }
        else
        {
            tableDisplay = (Collection) o;
        }
        return tableDisplay;
    }

    /**
     * Remet a zero les cases a cocher
     * 
     * @param request la request
     * @param form le form
     */
    public static void razCheckBoxListe( final HttpServletRequest request, final ActionForm form )
    {
        final String collections[] = request.getParameterValues( "checkName" );

        if ( collections != null )
        {
            for ( int i = 0; i < collections.length; i++ )
            {
                final String attribute = collections[i];

                if ( request.getParameter( attribute ) == null )
                {
                    razTheCheckBoxAttribute( request, form, attribute );
                }
            }
        }
    }

    /**
     * Remiez a zero de l'attribut case a coché passé en parametre
     * 
     * @param request : requets
     * @param form formulaire
     * @param attribute attribut
     */
    private static void razTheCheckBoxAttribute( final HttpServletRequest request, final ActionForm form,
                                                 final String attribute )
    {
        try
        {
            // Recupere le type de l'attribut
            Class propertyType = PropertyUtils.getPropertyType( form, attribute );
            if ( String.class.equals( propertyType ) )
            {
                BeanUtils.setProperty( form, attribute, "false" );
            }
            else if ( Boolean.class.equals( propertyType ) )
            {
                BeanUtils.setProperty( form, attribute, Boolean.FALSE );
            }
            else if ( Boolean.TYPE.equals( propertyType ) )
            {
                BeanUtils.setProperty( form, attribute, "false" );
            }
            else
            {
                log.warn( "Impossible de reinitialiser la checkbox de la liste, type de l'attribut : " + attribute
                    + "=" + propertyType );
            }
        }
        catch ( IllegalAccessException e )
        {
            log.error( e, e );
        }
        catch ( InvocationTargetException e )
        {
            log.error( e, e );
        }
        catch ( NoSuchMethodException e )
        {
            log.error( e, e );
        }
    }

    /**
     * @param label Chaine à tronquer
     * @param truncate la chaine contenant les indications de tronquature.
     * @param type le type
     * @return la chaine tronquée
     */
    public static String getTruncatedString( final String label, final String truncate, final String type )
    {
        if ( isTruncated( label, truncate, type ) )
        {
            return label.substring( 0, parseTruncate( truncate ) ) + "...";
        }
        return label;
    }

    /**
     * @param label label de chaine à tronquer
     * @param truncate la chaine contenant les indications de tronquature.
     * @param type le type de la chaine
     * @return vrai si la chaine dot être tronquée
     */
    public static boolean isTruncated( final String label, final String truncate, final String type )
    {
        if ( !GenericValidator.isBlankOrNull( truncate )
            && ( GenericValidator.isBlankOrNull( type ) || type.equalsIgnoreCase( "STRING" ) )
            && ( label.length() > parseTruncate( truncate ) ) )
        {
            return true;
        }
        return false;
    }

    /**
     * @param truncate les chanps truncate
     * @return le nombre
     */
    private static int parseTruncate( final String truncate )
    {

        try
        {
            final int ret = Integer.parseInt( truncate );
            return ret;
        }
        catch ( final NumberFormatException e )
        {
            if ( log.isDebugEnabled() )
            {
                log.debug( "Troncature non valide : " + truncate );
            }
            return 0;
        }

    }
}
