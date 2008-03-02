/*
 * Créé le 1 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.iterators.EnumerationIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.ajax.WHttpEasyCompleteResponse;
import com.airfrance.welcom.struts.bean.WComboValueLabel;
import com.airfrance.welcom.struts.bean.WCouple;
import com.airfrance.welcom.struts.easycomplete.WEasyCompleteUtil;

/**
 * @author M327836 Action par défaut pour l'auto completion
 */
public class WEasyCompleteAction
    extends Action
{
    /** logger */
    private static Log log = LogFactory.getLog( WEasyCompleteAction.class );

    /** Nombre max d'élément à retourner. */
    private static final int MAX_RETURNED_ELEMENTS = 10;

    /**
     * @see (org.apache.struts.action.ActionForm.execute())
     */
    public ActionForward execute( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
                                  final HttpServletResponse response )
        throws IOException, ServletException
    {
        String ch = request.getParameter( "ch" );
        final String name = request.getParameter( "name" );
        final String property = request.getParameter( "property" );
        final String valuename = request.getParameter( "value" );
        final String labelname = request.getParameter( "label" );

        int maxReturnedElements = MAX_RETURNED_ELEMENTS;
        try
        {
            maxReturnedElements =
                Integer.parseInt( WelcomConfigurator.getMessage( WelcomConfigurator.EASY_COMPLETE_DEFAULT_MAXIMUM_RETURNED_ELEMENTS ) );
        }
        catch ( final NumberFormatException e )
        {
        }
        ch = WEasyCompleteUtil.filter( ch );

        if ( name != null )
        {
            final WHttpEasyCompleteResponse easyComplete = new WHttpEasyCompleteResponse( response );

            int i = 0;

            for ( final Iterator collIterator = getIterator( name, property, request ); collIterator.hasNext()
                && ( i < maxReturnedElements ); )
            {
                final Object bean = collIterator.next();
                String value = null;
                String label = null;

                try
                {
                    if ( bean instanceof WCouple )
                    {
                        value = ( (WCouple) bean ).getValue();
                        label = ( (WCouple) bean ).getLabel();
                    }
                    else
                    {
                        if ( !GenericValidator.isBlankOrNull( valuename ) )
                        {
                            value = (String) PropertyUtils.getProperty( bean, valuename );
                        }

                        if ( !GenericValidator.isBlankOrNull( labelname ) )
                        {
                            label = (String) PropertyUtils.getProperty( bean, labelname );
                        }
                    }

                    if ( value == null )
                    {
                        value = "";
                    }

                    if ( label == null )
                    {
                        label = "";
                    }

                    final Pattern p = Pattern.compile( "\\b" + ch, Pattern.CASE_INSENSITIVE );
                    final Matcher mv = p.matcher( value );
                    final Matcher ml = p.matcher( label );

                    if ( mv.find() || ml.find() )
                    {
                        easyComplete.addValueLabel( value, label );
                        i++;
                    }
                }
                catch ( final IllegalAccessException _ex )
                {
                    log.error( _ex, _ex );
                    throw new ServletException( _ex );
                }
                catch ( final InvocationTargetException e )
                {
                    log.error( e, e );
                    throw new ServletException( e );
                }
                catch ( final NoSuchMethodException _ex )
                {
                    log.error( _ex, _ex );
                    throw new ServletException( _ex );
                }
            }

            easyComplete.close();
        }

        return null;
    }

    /**
     * Retourne un iterator quelque soit le types de collection
     * 
     * @param name nom du bean
     * @param property property contenant la collection
     * @param request requette http
     * @return un itérator
     * @throws ServletException ServletException
     */
    protected Iterator getIterator( final String name, final String property, final HttpServletRequest request )
        throws ServletException
    {
        String beanName = name;

        if ( beanName == null )
        {
            beanName = "org.apache.struts.taglib.html.BEAN";
        }

        final Object bean = lookUp( beanName, request );

        if ( bean == null )
        {
            System.err.println( "impossible de trouver le bean" + beanName );
            throw new ServletException( "impossible de trouver le bean" + beanName );
        }

        Object collection = bean;

        if ( property != null )
        {
            try
            {
                collection = PropertyUtils.getProperty( bean, property );

                if ( collection == null )
                {
                    System.err.println( "impossible de trouver la propriété " + property + " dans le bean " + beanName );
                    throw new ServletException( "impossible de trouver la propriété " + property + " dans le bean "
                        + beanName );
                }
            }
            catch ( final IllegalAccessException _ex )
            {
                log.error( _ex, _ex );
            }
            catch ( final InvocationTargetException e )
            {
                log.error( e, e );
            }
            catch ( final NoSuchMethodException _ex )
            {
                log.error( _ex, _ex );
            }
        }

        if ( collection.getClass().isArray() )
        {
            collection = Arrays.asList( (Object[]) collection );
        }

        if ( collection instanceof Collection )
        {
            return ( (Collection) collection ).iterator();
        }

        if ( collection instanceof Iterator )
        {
            return (Iterator) collection;
        }

        if ( collection instanceof WComboValueLabel )
        {
            return ( (WComboValueLabel) collection ).iterator();
        }

        if ( collection instanceof Map )
        {
            return ( (Map) collection ).entrySet().iterator();
        }

        if ( collection instanceof Enumeration )
        {
            return new EnumerationIterator( (Enumeration) collection );
        }
        else
        {
            throw new ServletException( "impossible de trouver un iterator" );
        }
    }

    /**
     * Recherche l'objet
     * 
     * @param name : nom
     * @param request request
     * @return :lobjet
     */
    protected Object lookUp( final String name, final HttpServletRequest request )
    {
        Object o = null;
        o = request.getAttribute( name );

        if ( o == null )
        {
            o = request.getSession().getAttribute( name );
        }

        if ( o == null )
        {
            o = servlet.getServletContext().getAttribute( name );
        }

        return o;
    }
}