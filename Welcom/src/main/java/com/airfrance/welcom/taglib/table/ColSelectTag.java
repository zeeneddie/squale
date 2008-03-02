/*
 * Créé le 4 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.Access;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColSelectTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 4409913627129484124L;

    /** parametre du tag */
    private String property = "";

    /** parametre du tag */
    private String key = "";

    /** parametre du tag */
    private String width = "24px";

    /** parametre du tag */
    private ColsTag colsTag;

    /** parametre du tag */
    private String toolTipKey = "";

    /** parametre du tag */
    private boolean forceReadWrite = false;

    /** singleSelect */
    private boolean enableSingleSelect = false;

    /** onclick */
    private String onclick = "";

    /** parametre du tag */
    private String disabledProperty = "";

    /** parametre du tag */
    private boolean showSelectAllHeader = false;

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && !forceReadWrite )
        {
            return SKIP_BODY;
        }

        // Recherche si un parent est du bon type
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof ColsTag ); )
        {
            curParent = curParent.getParent();
        }

        if ( curParent == null )
        {
            throw new JspException( "ColTag  must be used between Cols Tag." );
        }

        colsTag = (ColsTag) curParent;

        if ( !GenericValidator.isBlankOrNull( colsTag.getEmptyKey() )
            && ( pageContext.getAttribute( colsTag.getId() ) == null ) )
        {
            return SKIP_BODY;
        }
        else
        {
            return EVAL_PAGE;
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && !forceReadWrite )
        {
            return SKIP_BODY;
        }

        // Recupere la locale de la page
        final Locale localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        // Recuperer le fichier des Bundle
        final MessageResources resources =
            (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );

        final ColSelect c = new ColSelect();

        if ( key == null )
        {
            key = "selected";
        }

        c.setKey( key );
        c.setProperty( property );
        c.setWidth( width );
        c.setPageContext( pageContext );
        c.setEnableSingleSelect( enableSingleSelect );
        c.setWriteTD( false );
        c.setOnclick( onclick );
        c.setDisabledProperty( disabledProperty );
        c.setSpecialHeader( showSelectAllHeader );
        c.setSpecialHeaderTitle( resources.getMessage( localeRequest, "welcom.internal.selectAll.tootip" ) );
        c.setToolTip( resources.getMessage( localeRequest, toolTipKey ) );
        if ( GenericValidator.isBlankOrNull( c.getToolTip() ) )
        {
            c.setToolTip( toolTipKey );
        }
        if ( getBodyContent() != null )
        {
            c.setCurrentValue( getBodyContent().getString().trim() );
        }

        colsTag.addCellule( c );

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        colsTag = null;
        property = "";
        key = "";
        width = "";
        toolTipKey = "";
        forceReadWrite = false;
        enableSingleSelect = false;
        disabledProperty = "";
        onclick = "";
        super.release();
    }

    /**
     * @return colsTag
     */
    public ColsTag getColsTag()
    {
        return colsTag;
    }

    /**
     * @return key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param tag le nouveau colsTag
     */
    public void setColsTag( final ColsTag tag )
    {
        colsTag = tag;
    }

    /**
     * @param string le nouveau key
     */
    public void setKey( final String string )
    {
        key = string;
    }

    /**
     * @param string le nouveau property
     */
    public void setProperty( final String string )
    {
        property = string;
    }

    /**
     * @param string le nouveau width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return forceReadWrite
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * @param b le nouveau forceReadWrite
     */
    public void setForceReadWrite( final boolean b )
    {
        forceReadWrite = b;
    }

    /**
     * @return accesseur
     */
    public String getToolTipKey()
    {
        return toolTipKey;
    }

    /**
     * @param string accesseur
     */
    public void setToolTipKey( final String string )
    {
        toolTipKey = string;
    }

    /**
     * @return accesseur
     */
    public boolean isEnableSingleSelect()
    {
        return enableSingleSelect;
    }

    /**
     * @param b accesseur
     */
    public void setEnableSingleSelect( final boolean b )
    {
        enableSingleSelect = b;
    }

    /**
     * @return accesseur
     */
    public String getOnclick()
    {
        return onclick;
    }

    /**
     * @param string accesseur
     */
    public void setOnclick( final String string )
    {
        onclick = string;
    }

    /**
     * @return disabledProperty
     */
    public String getDisabledProperty()
    {
        return disabledProperty;
    }

    /**
     * @param string disabledProperty
     */
    public void setDisabledProperty( final String string )
    {
        disabledProperty = string;
    }

    /**
     * @return showSelectAllHeader
     */
    public boolean isShowSelectAllHeader()
    {
        return showSelectAllHeader;
    }

    /**
     * @param b showSelectAllHeader
     */
    public void setShowSelectAllHeader( final boolean b )
    {
        showSelectAllHeader = b;
    }

}