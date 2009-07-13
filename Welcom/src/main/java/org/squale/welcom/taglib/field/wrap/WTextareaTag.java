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
package org.squale.welcom.taglib.field.wrap;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.taglib.html.HiddenTag;
import org.squale.welcom.outils.Charte;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.field.util.LayoutUtils;
import org.squale.welcom.taglib.field.util.TagUtils;


public class WTextareaTag
    extends WBaseTextareaTag
    implements IWelcomInputTag
{

    /**
     * Spécifie le mode d'accée du composant
     */
    private String access = READWRITE;

    /**
     * {@inheritDoc}
     */
    public int doStartTag()
        throws JspException
    {
        // Adapte le style de la case a cohé sir on est en charte v2.002
        if ( WelcomConfigurator.getCharte() == Charte.V2_002 && GenericValidator.isBlankOrNull( this.getStyleClass() ) )
        {
            this.setStyleClass( "normal" );
        }

        if ( READWRITE.equals( access ) )
        {
            return super.doStartTag();
        }
        else
        {
            this.setReadonly( true );
            if ( READSEND.equals( access ) )
            {
                HiddenTag hiddenTag = new HiddenTag();
                // initialize the tag
                LayoutUtils.copyProperties( hiddenTag, this );
                hiddenTag.doStartTag();
                hiddenTag.doEndTag();
            }
            return super.doStartTag();
        }
    }

    /**
     * Methode preparant les index on rajout le possibilité sur le TableTag
     */
    protected void prepareIndex( StringBuffer sb, String name )
        throws JspException
    {
        TagUtils.prepareIndex( pageContext, this, messages, sb, name );
    }

    /**
     * @return access attribut
     */
    public String getAccess()
    {
        return access;
    }

    /**
     * @param access access attribut
     */
    public void setAccess( String access )
    {
        this.access = access;
    }

}
