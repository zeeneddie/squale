package com.airfrance.welcom.taglib.onglet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.lazyLoading.WLazyLoadingPersistance;
import com.airfrance.welcom.struts.lazyLoading.WLazyLoadingType;
import com.airfrance.welcom.struts.lazyLoading.WLazyUtil;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSOngletItem
{
    /** logger */
    private static Log log = LogFactory.getLog( JSOngletItem.class );

    /** attribut */
    protected String titre = "";

    /** attribut */
    protected String corps = "";

    /** attribut */
    protected String isOngletSelected = "";

    /** attribut */
    protected String name = "";

    /** attribut */
    protected String pageInclude = "";

    /** attribut */
    protected PageContext pageContext = null;

    /** attribut */
    protected String parentName = "";

    /** attribut */
    protected boolean lazyLoading = true;

    /** parametre du tag */
    protected String onClickAfterShow = "";

    /** render */
    private static IJSOngletRenderer render = (IJSOngletRenderer) RendererFactory.getRenderer( RendererFactory.ONGLET );

    /**
     * @param indice indice de l'onglet
     * @return le titre
     */
    public String doPrintTitle( final int indice )
    {

        return render.drawTitle( name, titre, parentName, indice, isOngletSelected.equals( "true" ), onClickAfterShow );

    }

    /**
     * @param indice indice de l'onglet
     * @return le corps
     * @throws JspException exception pouvant etre levee
     */
    public String doPrintCorps( final int indice )
        throws JspException
    {
        StringBuffer buf = new StringBuffer();

        if ( Util.isTrue( getIsOnglet() ) || !GenericValidator.isBlankOrNull( pageInclude ) )
        {
            setLazyLoading( false );
        }

        // Ecrit le demarrage du corps !
        buf.append( render.drawBodyStart(
                                          name,
                                          isOngletSelected.equals( "true" ) || isOngletSelected.equals( "" ),
                                          WLazyUtil.isLazy( lazyLoading )
                                              && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_ONGLETS ) ) ) );

        ResponseUtils.write( pageContext, buf.toString() );

        // Inclus la page spécifié dans l'attribut pageInclude
        includePageIfNecessary();

        buf = new StringBuffer();

        if ( !Util.isTrue( getIsOnglet() ) && WLazyUtil.isLazy( lazyLoading )
            && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_ONGLETS ) ) )
        {
            final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            final ActionMapping mappingGenerique = (ActionMapping) request.getAttribute( Globals.MAPPING_KEY );
            final String scope = mappingGenerique.getScope();

            if ( Util.isEqualsIgnoreCase( scope, "session" ) )
            {
                buf.append( WLazyUtil.getSuperLightBody( corps ) );
            }
            else
            {
                buf.append( WLazyUtil.getLightBody( corps ) );
            }

            final String key = parentName + "." + name + ".content";
            WLazyLoadingPersistance.find( pageContext.getSession() ).add( WLazyLoadingType.ONGLET, key, corps );
        }
        else
        {
            buf.append( corps );
        }

        buf.append( render.drawBodyEnd() );

        ResponseUtils.write( pageContext, buf.toString() );

        return buf.toString();
    }

    /**
     * Inclusion de la page si necessaire
     * 
     * @throws JspException Si la page d'inclusion n'est pas trouvé on levele l'execption
     */
    private void includePageIfNecessary()
        throws JspException
    {
        try
        {
            if ( !GenericValidator.isBlankOrNull( pageInclude ) )
            {
                pageContext.include( pageInclude );
            }
        }
        catch ( final Exception e )
        {
            log.error( e, e );
            throw new JspException( "Erreur lors du page include de l'onglet : " + name );
        }
    }

    /**
     * Returns the corps.
     * 
     * @return String
     */
    public String getCorps()
    {
        return corps;
    }

    /**
     * Sets the corps.
     * 
     * @param pCorps The corps to set
     */
    public void setCorps( final String pCorps )
    {
        corps = pCorps;
    }

    /**
     * Returns the titre.
     * 
     * @return String
     */
    public String getTitre()
    {
        return titre;
    }

    /**
     * Sets the titre.
     * 
     * @param pTitre The titre to set
     */
    public void setTitre( final String pTitre )
    {
        titre = pTitre;
    }

    /**
     * Returns the isOnglet.
     * 
     * @return String
     */
    public String getIsOnglet()
    {
        return isOngletSelected;
    }

    /**
     * Sets the isOnglet.
     * 
     * @param pIsOnglet The isOnglet to set
     */
    public void setIsOnglet( final String pIsOnglet )
    {
        isOngletSelected = pIsOnglet;
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
     * Returns the pageContext.
     * 
     * @return PageContext
     */
    public PageContext getPageContext()
    {
        return pageContext;
    }

    /**
     * Sets the pageContext.
     * 
     * @param pPageContext The pageContext to set
     */
    public void setPageContext( final PageContext pPageContext )
    {
        pageContext = pPageContext;
    }

    /**
     * @return parentName
     */
    public String getParentName()
    {
        return parentName;
    }

    /**
     * @param string le parentName
     */
    public void setParentName( final String string )
    {
        parentName = string;
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
     * @return onClickAfterShow
     */
    public String getOnClickAfterShow()
    {
        return onClickAfterShow;
    }

    /**
     * @param string onClickAfterShow
     */
    public void setOnClickAfterShow( String string )
    {
        onClickAfterShow = string;
    }

}