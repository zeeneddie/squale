package com.airfrance.welcom.taglib.onglet;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;
import com.airfrance.welcom.struts.util.WRequestUtils;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSOngletItemTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 7518857343240890444L;

    /** parametre du tag */
    protected String key = "";

    /** parametre du tag */
    protected String isTabSelected = "false";

    /** parametre du tag */
    protected String name = "";

    /** parametre du tag */
    protected String pageInclude = "";

    /** parametre du tag */
    protected String accessKey = "";

    /** parametre du tag */
    protected String nameLogonBean = "logonBean";

    /** parametre du tag */
    protected boolean lazyLoading = true;

    /** parametre du tag */
    protected String onAfterShow = "";

    /**
     * Returns the key.
     * 
     * @return key
     */
    public String getKey()
    {
        return key;
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
     * @return print apres le body
     * @throws JspException exception pouvant etre levee
     */
    private int doPrintAfterBody()
        throws JspException
    {
        final JSOngletItem onglet = new JSOngletItem();

        // Transformation de la key en message
        String message = WRequestUtils.message( super.pageContext, key );

        if ( GenericValidator.isBlankOrNull( message ) )
        {
            message = key;
        }

        onglet.setTitre( message );

        // Recherche si un parent est du bon type
        Tag curParent = findAncestorWithClass( this, JSOngletTag.class );

        if ( curParent == null )
        {
            throw new JspException( "JSOngletItem Tag must be have parents JSOnglet." );
        }

        onglet.setParentName( ( (JSOngletTag) curParent ).getName() );

        if ( !( (JSOngletTag) curParent ).isFirstOngletSelected() && Util.isTrue( isTabSelected ) )
        {
            onglet.setIsOnglet( isTabSelected );
            ( (JSOngletTag) curParent ).setFirstOngletSelected( true );
        }
        else
        {
            onglet.setIsOnglet( "false" );
        }

        onglet.setName( name );
        onglet.setLazyLoading( isLazyLoading() );
        onglet.setPageInclude( pageInclude );
        onglet.setPageContext( pageContext );
        onglet.setOnClickAfterShow( onAfterShow );

        ( (JSOngletTag) curParent ).addOnglet( onglet );

        // Recupere le body evalué
        final BodyContent bodyContent = getBodyContent();

        if ( bodyContent != null )
        {
            final String value = bodyContent.getString().trim();
            bodyContent.clearBody();

            if ( value.length() > 0 )
            {
                onglet.setCorps( value );
            }
        }

        // Continue processing this page
        release();
        return ( EVAL_PAGE );
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final HttpSession session = super.pageContext.getSession();

        if ( session != null )
        {
            final Object logonBean = session.getAttribute( nameLogonBean );

            if ( logonBean != null )
            {
                try
                {
                    String haveAccess = "true";

                    if ( !GenericValidator.isBlankOrNull( accessKey ) )
                    {
                        final String pageAccess =
                            Access.getMultipleSecurityPage( (WILogonBeanSecurity) logonBean, accessKey );
                        haveAccess = new Boolean( Access.checkAccessDroit( pageAccess ) ).toString();
                    }

                    if ( Util.isTrue( haveAccess ) )
                    {
                        return doPrintAfterBody();
                    }
                }
                catch ( final Exception _ex )
                {
                    new JspException(
                                      "Manque m\351thode getVectorProfil() ou getIdIncident() sur bean WConstants.USER_KEY" );
                }
            }
            else
            {
                return doPrintAfterBody();
            }
        }
        release();
        return EVAL_PAGE;
    }

    /**
     * Release any acquired resources.
     */
    public void release()
    {
        super.release();
        onAfterShow = "";
        key = "";
    }

    /**
     * Returns the name.
     * 
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param pName The name to set
     */
    public void setName( final String pName )
    {
        name = pName;
    }

    /**
     * Returns the pageInclude.
     * 
     * @return String
     */
    public String getPageInclude()
    {
        return pageInclude;
    }

    /**
     * Sets the pageInclude.
     * 
     * @param pPageInclude The pageInclude to set
     */
    public void setPageInclude( final String pPageInclude )
    {
        pageInclude = pPageInclude;
    }

    /**
     * @return lazyLoading
     */
    public boolean isLazyLoading()
    {
        return lazyLoading;
    }

    /**
     * @param b le lazyLoading
     */
    public void setLazyLoading( final boolean b )
    {
        lazyLoading = b;
    }

    /**
     * @return isTabSelected
     */
    public String getIsTabSelected()
    {
        return isTabSelected;
    }

    /**
     * @param string le isTabSelected
     */
    public void setIsTabSelected( final String string )
    {
        isTabSelected = string;
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
     * @return onClickAfterShow
     */
    public String getOnAfterShow()
    {
        return onAfterShow;
    }

    /**
     * @param string onClickAfterShow
     */
    public void setOnAfterShow( String string )
    {
        onAfterShow = string;
    }

}