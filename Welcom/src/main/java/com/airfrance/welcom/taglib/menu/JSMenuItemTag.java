package com.airfrance.welcom.taglib.menu;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.struts.util.WConstants;
import com.airfrance.welcom.struts.util.WRequestUtils;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSMenuItemTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 6186001810376428018L;

    /** parametre du tag */
    private JSMenuItem menu;

    /** parametre du tag */
    private String key;

    /** parametre du tag */
    private String color;

    /** parametre du tag */
    private String action;

    /** parametre du tag */
    protected String name = WConstants.USER_KEY;

    /** parametre du tag */
    protected String accessKey = null;

    /** parametre du tag */
    protected String haveAccess;

    /** parametre du tag */
    private String width = "120";

    /**
     * Constructor for JSMenuItemTag.
     */
    public JSMenuItemTag()
    {
        super();
    }

    /**
     * @param item le JSMenuItem a rajouté
     */
    public void addItem( final JSMenuItem item )
    {
        if ( menu == null )
        {
            menu = new JSMenuItem();
        }

        menu.addMenuItem( item );
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        if ( menu == null )
        {
            menu = new JSMenuItem();
        }

        if ( Util.isTrue( haveAccess ) )
        {
            try
            {
                // Retourne le libelle
                menu.setLibelle( getLibelle() );
                // retourne la couleur
                menu.setColor( JSMenuColor.getJSMenuColorById( color ) );
                // retourne l'action a executé
                menu.setAction( action );
                // retourne la taille
                menu.setWidth( width );
                // Ajoute le menu a son parent
                addJSItem( menu );

            }
            catch ( final JSMenuColorNotFound e )
            {
                throw new JspException( e.getMessage() );
            }
        }
        menu = null;
        key = null;
        return EVAL_PAGE;
    }

    /**
     * @param menu le jsmenu
     * @throws JspException exception sur la recherche de classe
     */
    private void addJSItem( JSMenuItem menu )
        throws JspException
    {
        // Recherche si un parent est du bon type
        JSMenuItemTag jsMenuItemTag = (JSMenuItemTag) findAncestorWithClass( this, JSMenuItemTag.class );
        JSMenuTag jsMenuTag = (JSMenuTag) findAncestorWithClass( this, JSMenuTag.class );

        // ajoute l'item a son parrent
        if ( jsMenuItemTag != null )
        {
            jsMenuItemTag.addItem( menu );
        }
        else if ( jsMenuTag != null )
        {
            jsMenuTag.addItem( menu );
        }
        else
        {
            throw new JspException( "menuItem Tag must be used between menu,menuItem Tag." );
        }
    }

    /**
     * Retourne le libellé du menu
     * 
     * @return le libéllé du menu
     */
    private String getLibelle()
    {
        final String message = WRequestUtils.message( super.pageContext, key );
        if ( !GenericValidator.isBlankOrNull( message ) )
        {
            return message;
        }
        else
        {
            return key;
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {

        String access = Access.computeTagReadWriteAccess( pageContext, accessKey, false, true );
        haveAccess = new Boolean( Access.checkAccessDroit( access ) ).toString();

        return EVAL_BODY_INCLUDE;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        key = null;
        action = null;
        menu = null;
        color = null;
        action = null;
        name = WConstants.USER_KEY;
        accessKey = null;
        haveAccess = null;
        width = "120";
    }

    /**
     * @param newName le nouveau name
     */
    public void setName( final String newName )
    {
        name = newName;
    }

    /**
     * Returns the action.
     * 
     * @return String
     */
    public String getAction()
    {
        return action;
    }

    /**
     * Returns the key.
     * 
     * @return String
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Sets the action.
     * 
     * @param pAction The action to set
     */
    public void setAction( final String pAction )
    {
        action = pAction;
    }

    /**
     * Sets the key.
     * 
     * @param pKey The key to set
     */
    public void setKey( final String pKey )
    {
        key = pKey;
    }

    /**
     * Returns the width.
     * 
     * @return String
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * Sets the width.
     * 
     * @param pWidth The width to set
     */
    public void setWidth( final String pWidth )
    {
        width = pWidth;
    }

    /**
     * @return accessKey
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * @param string le accessKey
     */
    public void setAccessKey( final String string )
    {
        accessKey = string;
    }

    /**
     * @return color
     */
    public String getColor()
    {
        return color;
    }

    /**
     * @param string la color
     */
    public void setColor( final String string )
    {
        color = string;
    }

}