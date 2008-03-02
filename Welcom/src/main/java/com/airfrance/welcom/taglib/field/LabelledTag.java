/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.airfrance.welcom.taglib.field;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * Base class for struts layout tags, as quite a lof of tags have a label
 * 
 * @author: Jean-Noel Ribette
 */
public abstract class LabelledTag
    extends EventHandlerTag
{
    /** le message ressource */
    protected static MessageResources messages =
        MessageResources.getMessageResources( Constants.Package + ".LocalStrings" );

    /** le parametre du tag */
    protected String arg0;

    /** le parametre du tag */
    protected String arg1;

    /** le parametre du tag */
    protected String arg2;

    /** le parametre du tag */
    protected String arg3;

    /** le parametre du tag */
    protected String arg4;

    /** le parametre du tag */
    protected boolean writeTD = true;

    /** le parametre du tag */
    protected String key;

    /** le parametre du tag */
    protected String bundle = Globals.MESSAGES_KEY;

    /** le parametre du tag */
    protected String localeKey = Globals.LOCALE_KEY;

    /** le parametre du tag */
    protected String name;

    /** le parametre du tag */
    protected String property;

    /** le parametre du tag */
    protected String styleClass;

    /** le parametre du tag */
    protected String width = null;

    /** le parametre du tag */
    protected String widthLabel = null;

    /** le parametre du tag */
    protected java.lang.String colspan = null;

    /** le parametre du tag */
    protected java.lang.String styleClassLabel = null;

    /** le parametre du tag */
    protected java.lang.String colspanLabel;

    /** le parametre du tag */
    protected boolean li;

    /** le parametre du tag */
    protected java.lang.String autoComplete;

    /**
     * Insérez la description de la méthode ici. Date de création : (09/09/2002 15:06:02)
     * 
     * @return java.lang.String
     */
    public java.lang.String getAutoComplete()
    {
        return autoComplete;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (05/12/2001 10:24:23)
     * 
     * @return java.lang.String
     */
    public java.lang.String getColspan()
    {
        return colspan;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (09/04/2002 12:06:13)
     * 
     * @return java.lang.String
     */
    public java.lang.String getColspanLabel()
    {
        return colspanLabel;
    }

    /**
     * @return le label
     * @throws JspException exception pouvant etre levee
     */
    protected String getLabel()
        throws JspException
    {
        try
        {
            if ( ( key == null ) && ( name != null ) )
            {
                return LayoutUtils.getBeanFromPageContext( pageContext, name, property ).toString();
            }
        }
        catch ( final ClassCastException l_cce )
        {
            throw new JspException( "oups" );
        }

        final String args[] = { arg0, arg1, arg2, arg4, arg4 };

        return LayoutUtils.getLabel( pageContext, key, args );
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return pageContext
     */
    public PageContext getPageContext()
    {
        return pageContext;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @return styleClass
     */
    public String getStyleClass()
    {
        return styleClass;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (05/12/2001 13:39:07)
     * 
     * @return java.lang.String
     */
    public java.lang.String getStyleClassLabel()
    {
        return styleClassLabel;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (30/08/2002 14:45:29)
     * 
     * @return boolean
     */
    public boolean isLi()
    {
        return li;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        key = null;
        bundle = Globals.MESSAGES_KEY;
        localeKey = Globals.LOCALE_KEY;
        arg0 = null;
        arg1 = null;
        arg2 = null;
        arg3 = null;
        arg4 = null;
        name = null;
        property = null;
        styleClass = null;
        styleClassLabel = null;
        colspan = null;
    }

    /**
     * @param pArg0 arg0
     */
    public void setArg0( final String pArg0 )
    {
        arg0 = pArg0;
    }

    /**
     * @param pArg1 arg1
     */
    public void setArg1( final String pArg1 )
    {
        arg1 = pArg1;
    }

    /**
     * @param pArg2 arg2
     */
    public void setArg2( final String pArg2 )
    {
        arg2 = pArg2;
    }

    /**
     * @param pArg3 arg3
     */
    public void setArg3( final String pArg3 )
    {
        arg3 = pArg3;
    }

    /**
     * @param pArg4 arg4
     */
    public void setArg4( final String pArg4 )
    {
        arg4 = pArg4;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (09/09/2002 15:06:02)
     * 
     * @param newAutoComplete java.lang.String
     */
    public void setAutoComplete( final java.lang.String newAutoComplete )
    {
        autoComplete = newAutoComplete;
    }

    /**
     * @param pBundle bundle
     */
    public void setBundle( final String pBundle )
    {
        bundle = pBundle;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (05/12/2001 10:24:23)
     * 
     * @param newColspan java.lang.String
     */
    public void setColspan( final java.lang.String newColspan )
    {
        colspan = newColspan;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (09/04/2002 12:06:13)
     * 
     * @param newColspanLabel java.lang.String
     */
    public void setColspanLabel( final java.lang.String newColspanLabel )
    {
        colspanLabel = newColspanLabel;
    }

    /**
     * @param pKey key
     */
    public void setKey( final String pKey )
    {
        key = pKey;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (30/08/2002 14:45:29)
     * 
     * @param newLi boolean
     */
    public void setLi( final boolean newLi )
    {
        li = newLi;
    }

    /**
     * @param pLocaleKey localeKey
     */
    public void setLocale( final String pLocaleKey )
    {
        localeKey = pLocaleKey;
    }

    /**
     * @param pName name
     */
    public void setName( final String pName )
    {
        name = pName;
    }

    /**
     * @param pProperty property
     */
    public void setProperty( final String pProperty )
    {
        property = pProperty;
    }

    /**
     * @param pStyleClass styleClass
     */
    public void setStyleClass( final String pStyleClass )
    {
        styleClass = pStyleClass;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (05/12/2001 13:39:07)
     * 
     * @param newStyleClassLabel java.lang.String
     */
    public void setStyleClassLabel( final java.lang.String newStyleClassLabel )
    {
        styleClassLabel = newStyleClassLabel;
    }

    /**
     * @return writeTD
     */
    public boolean isWriteTD()
    {
        return writeTD;
    }

    /**
     * @param b writeTD
     */
    public void setWriteTD( final boolean b )
    {
        writeTD = b;
    }

    /**
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param string width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return widthLabel
     */
    public String getWidthLabel()
    {
        return widthLabel;
    }

    /**
     * @param string widthLabel
     */
    public void setWidthLabel( final String string )
    {
        widthLabel = string;
    }
}