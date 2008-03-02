package com.airfrance.welcom.taglib.field.wrap;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.taglib.html.HiddenTag;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.field.CheckBoxTag;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;
import com.airfrance.welcom.taglib.field.util.TagUtils;

public class WCheckBoxTag
    extends CheckBoxTag
    implements IWelcomInputTag
{

    /**
     * serial verison
     */
    private static final long serialVersionUID = 1L;

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
            setDisabled( true );
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
