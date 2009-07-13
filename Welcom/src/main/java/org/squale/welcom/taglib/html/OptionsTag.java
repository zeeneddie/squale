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
package org.squale.welcom.taglib.html;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.iterators.EnumerationIterator;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.outils.Access;
import org.squale.welcom.struts.bean.WComboValueLabel;
import org.squale.welcom.struts.bean.WCouple;


/**
 * OptionsTag
 */
public class OptionsTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -5554427152903429030L;

    /** le messageRessource */
    protected static MessageResources messages =
        MessageResources.getMessageResources( "org.apache.struts.taglib.html.LocalStrings" );

    /** parametre du tag */
    protected String collection;

    /** parametre du tag */
    protected String labelName;

    /** parametre du tag */
    protected String labelProperty;

    /** parametre du tag */
    protected String name;

    /** parametre du tag */
    protected String property;

    /** parametre du tag */
    private String style;

    /** parametre du tag */
    private String styleClass;

    /**
     * Constructeur
     */
    public OptionsTag()
    {
        collection = null;
        labelName = null;
        labelProperty = null;
        name = null;
        property = null;
        style = null;
        styleClass = null;
    }

    /**
     * @return collection
     */
    public String getCollection()
    {
        return collection;
    }

    /**
     * @param pCollection la nouvelle collection
     */
    public void setCollection( final String pCollection )
    {
        collection = pCollection;
    }

    /**
     * @return labelName
     */
    public String getLabelName()
    {
        return labelName;
    }

    /**
     * @param pLabelName le labelName
     */
    public void setLabelName( final String pLabelName )
    {
        labelName = pLabelName;
    }

    /**
     * @return le labelProperty
     */
    public String getLabelProperty()
    {
        return labelProperty;
    }

    /**
     * @param pLabelProperty le labelProperty
     */
    public void setLabelProperty( final String pLabelProperty )
    {
        labelProperty = pLabelProperty;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param pName name
     */
    public void setName( final String pName )
    {
        name = pName;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @param pProperty property
     */
    public void setProperty( final String pProperty )
    {
        property = pProperty;
    }

    /**
     * @return style
     */
    public String getStyle()
    {
        return style;
    }

    /**
     * @param pStyle style
     */
    public void setStyle( final String pStyle )
    {
        style = pStyle;
    }

    /**
     * @return styleClass
     */
    public String getStyleClass()
    {
        return styleClass;
    }

    /**
     * @param pStyleClass styleClass
     */
    public void setStyleClass( final String pStyleClass )
    {
        styleClass = pStyleClass;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        return SKIP_BODY;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        // Recupere le droit sur la page
        final SelectTag selectTag =
            (SelectTag) super.pageContext.getAttribute( "org.squale.welcom.taglib.html.SELECT" );

        if ( selectTag == null )
        {
            throw new JspException( messages.getMessage( "optionsTag.select" ) );
        }
        final String pageAccess = selectTag.getResultAccess();

        if ( ( selectTag.isForceReadWrite() == true )
            || ( ( pageAccess != null ) && pageAccess.equals( Access.READWRITE ) ) || ( pageAccess == null ) )
        {
            final StringBuffer sb = new StringBuffer();

            if ( collection != null )
            {
                Object label;
                String stringValue;

                for ( final Iterator collIterator = getIterator( collection, null ); collIterator.hasNext(); addOption(
                                                                                                                        sb,
                                                                                                                        stringValue,
                                                                                                                        label.toString(),
                                                                                                                        selectTag.isMatched( stringValue ) ) )
                {
                    final Object bean = collIterator.next();
                    Object value = null;
                    label = null;

                    try
                    {
                        if ( bean instanceof WCouple )
                        {
                            value = ( (WCouple) bean ).getValue();
                        }
                        else
                        {
                            value = PropertyUtils.getProperty( bean, property );
                        }

                        if ( value == null )
                        {
                            value = "";
                        }
                    }
                    catch ( final IllegalAccessException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.access", property, collection ) );
                    }
                    catch ( final InvocationTargetException e )
                    {
                        final Throwable t = e.getTargetException();
                        throw new JspException( messages.getMessage( "getter.result", property, t.toString() ) );
                    }
                    catch ( final NoSuchMethodException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.method", property, collection ) );
                    }

                    try
                    {
                        if ( bean instanceof WCouple )
                        {
                            label = ( (WCouple) bean ).getLabel();
                        }

                        if ( labelProperty != null )
                        {
                            label = PropertyUtils.getProperty( bean, labelProperty );
                        }
                        else
                        {
                            if ( GenericValidator.isBlankOrNull( (String) label ) )
                            {
                                label = value;
                            }
                        }

                        if ( label == null )
                        {
                            label = "";
                        }
                    }
                    catch ( final IllegalAccessException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.access", labelProperty, collection ) );
                    }
                    catch ( final InvocationTargetException e )
                    {
                        final Throwable t = e.getTargetException();
                        throw new JspException( messages.getMessage( "getter.result", labelProperty, t.toString() ) );
                    }
                    catch ( final NoSuchMethodException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.method", labelProperty, collection ) );
                    }

                    stringValue = value.toString();
                }
            }
            else
            {
                final Iterator valuesIterator = getIterator( name, property );
                Iterator labelsIterator = null;

                if ( ( labelName == null ) && ( labelProperty == null ) )
                {
                    labelsIterator = getIterator( name, property );
                }
                else
                {
                    labelsIterator = getIterator( labelName, labelProperty );
                }

                String value;
                String label;

                for ( ; valuesIterator.hasNext(); addOption( sb, value, label, selectTag.isMatched( value ) ) )
                {
                    value = valuesIterator.next().toString();
                    label = value;

                    if ( labelsIterator.hasNext() )
                    {
                        label = labelsIterator.next().toString();
                    }
                }
            }

            ResponseUtils.write( super.pageContext, sb.toString() );
        }
        else
        {
            if ( collection != null )
            {
                Object label;
                String stringValue;

                for ( final Iterator collIterator = getIterator( collection, null ); collIterator.hasNext(); )
                {
                    final Object bean = collIterator.next();
                    Object value = null;
                    label = null;

                    try
                    {
                        if ( bean instanceof WCouple )
                        {
                            value = ( (WCouple) bean ).getValue();
                        }
                        else
                        {
                            value = PropertyUtils.getProperty( bean, property );
                        }

                        if ( value == null )
                        {
                            value = "";
                        }
                    }
                    catch ( final IllegalAccessException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.access", property, collection ) );
                    }
                    catch ( final InvocationTargetException e )
                    {
                        final Throwable t = e.getTargetException();
                        throw new JspException( messages.getMessage( "getter.result", property, t.toString() ) );
                    }
                    catch ( final NoSuchMethodException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.method", property, collection ) );
                    }

                    try
                    {
                        if ( bean instanceof WCouple )
                        {
                            label = ( (WCouple) bean ).getLabel();
                        }

                        if ( labelProperty != null )
                        {
                            label = PropertyUtils.getProperty( bean, labelProperty );
                        }
                        else
                        {
                            if ( GenericValidator.isBlankOrNull( (String) label ) )
                            {
                                label = value;
                            }
                        }

                        if ( label == null )
                        {
                            label = "";
                        }
                    }
                    catch ( final IllegalAccessException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.access", labelProperty, collection ) );
                    }
                    catch ( final InvocationTargetException e )
                    {
                        final Throwable t = e.getTargetException();
                        throw new JspException( messages.getMessage( "getter.result", labelProperty, t.toString() ) );
                    }
                    catch ( final NoSuchMethodException _ex )
                    {
                        throw new JspException( messages.getMessage( "getter.method", labelProperty, collection ) );
                    }

                    stringValue = value.toString();

                    if ( selectTag.isMatched( stringValue ) )
                    {
                        final StringBuffer results = new StringBuffer();
                        results.append( "<span class=\"normalBold\">" );
                        results.append( label );
                        results.append( "</span>" );
                        ResponseUtils.write( super.pageContext, results.toString() );
                    }
                }
            }
            else
            {
                final Iterator valuesIterator = getIterator( name, property );
                Iterator labelsIterator = null;

                if ( ( labelName == null ) && ( labelProperty == null ) )
                {
                    labelsIterator = getIterator( name, property );
                }
                else
                {
                    labelsIterator = getIterator( labelName, labelProperty );
                }

                String value;
                String label;

                for ( ; valuesIterator.hasNext(); )
                {
                    value = valuesIterator.next().toString();
                    label = value;

                    if ( labelsIterator.hasNext() )
                    {
                        label = labelsIterator.next().toString();
                    }

                    if ( selectTag.isMatched( value ) )
                    {
                        final StringBuffer results = new StringBuffer();
                        results.append( "<span class=\"normalBold\">" );
                        results.append( label );
                        results.append( "</span>" );
                        ResponseUtils.write( super.pageContext, results.toString() );
                    }
                }
            }
        }

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        collection = null;
        labelName = null;
        labelProperty = null;
        name = null;
        property = null;
        style = null;
        styleClass = null;
    }

    /**
     * @param sb le stringBuffer
     * @param value la value
     * @param label le label
     * @param matched true si ca match
     */
    protected void addOption( final StringBuffer sb, final String value, final String label, final boolean matched )
    {
        sb.append( "<option value=\"" );
        sb.append( value );
        sb.append( "\"" );

        if ( matched )
        {
            sb.append( " selected=\"selected\"" );
        }

        if ( style != null )
        {
            sb.append( " style=\"" );
            sb.append( style );
            sb.append( "\"" );
        }

        if ( styleClass != null )
        {
            sb.append( " class=\"" );
            sb.append( styleClass );
            sb.append( "\"" );
        }

        sb.append( ">" );
        sb.append( ResponseUtils.filter( label ) );
        sb.append( "</option>\r\n" );
    }

    /**
     * @param pName nom du bean
     * @param pProperty la property
     * @return l'interator
     * @throws JspException exception pouvant etre levee
     */
    protected Iterator getIterator( final String pName, final String pProperty )
        throws JspException
    {
        String beanName = pName;

        if ( beanName == null )
        {
            beanName = "org.apache.struts.taglib.html.BEAN";
        }

        final Object bean = super.pageContext.findAttribute( beanName );

        if ( bean == null )
        {
            throw new JspException( messages.getMessage( "getter.bean", beanName ) );
        }

        Object col = bean;

        if ( pProperty != null )
        {
            try
            {
                col = PropertyUtils.getProperty( bean, pProperty );

                if ( col == null )
                {
                    throw new JspException( messages.getMessage( "getter.property", pProperty ) );
                }
            }
            catch ( final IllegalAccessException _ex )
            {
                throw new JspException( messages.getMessage( "getter.access", pProperty, pName ) );
            }
            catch ( final InvocationTargetException e )
            {
                final Throwable t = e.getTargetException();
                throw new JspException( messages.getMessage( "getter.result", pProperty, t.toString() ) );
            }
            catch ( final NoSuchMethodException _ex )
            {
                throw new JspException( messages.getMessage( "getter.method", pProperty, pName ) );
            }
        }

        if ( col.getClass().isArray() )
        {
            col = Arrays.asList( (Object[]) col );
        }

        if ( col instanceof Collection )
        {
            return ( (Collection) col ).iterator();
        }

        if ( col instanceof Iterator )
        {
            return (Iterator) col;
        }

        if ( col instanceof WComboValueLabel )
        {
            return ( (WComboValueLabel) col ).iterator();
        }

        if ( col instanceof Map )
        {
            return ( (Map) col ).entrySet().iterator();
        }

        if ( col instanceof Enumeration )
        {
            return new EnumerationIterator( (Enumeration) col );
        }
        else
        {
            throw new JspException( messages.getMessage( "optionsTag.iterator", col.toString() ) );
        }
    }
}