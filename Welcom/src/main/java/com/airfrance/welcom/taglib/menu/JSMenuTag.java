package com.airfrance.welcom.taglib.menu;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.CanvasLeftMenuTag;
import com.airfrance.welcom.taglib.canvas.CanvasTag;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSMenuTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -5552765115634935874L;

    /** parametre du tag */
    private JSMenu menu;

    /** parametre du tag */
    private String bayadere;

    /** parametre du tag */
    private String name;

    /** parametre du tag */
    private static String MENU_KEY = "com.airfrance.welcom.taglib.menu";

    /** render */
    private IMenuRender render = (IMenuRender) RendererFactory.getRenderer( RendererFactory.MENU );

    /**
     * Constructor for JSMenuTag.
     */
    public JSMenuTag()
    {
        super();
    }

    /**
     * @param item ajoute un JSMenuItem
     */
    public void addItem( final JSMenuItem item )
    {
        if ( menu == null )
        {
            menu = new JSMenu();
        }

        // ajoute l'element
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
            menu = new JSMenu();
        }

        // Sette l'orientation du menu
        menu.setOrientation( getOrientation() );

        // sette le bayadere
        menu.setBayadere( getBayadere() );

        // sette le nom du menu
        menu.setName( getMenuName() );

        // Affiche le menu que seulement s'il a des items
        if ( menu.hasChild() )
        {
            final StringBuffer sb = new StringBuffer();
            sb.append( doPrintHeader() );
            sb.append( doPrintBody() );
            sb.append( doPrintFooter() );

            // Recuperele bodytag pour verifie si on
            // est bien dans un body pour generer le menu
            final CanvasTag bodyTag = (CanvasTag) pageContext.getRequest().getAttribute( CanvasTag.CANVASTAG_KEY );
            // Verifie si c'est un menu light
            if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_MENU_LIGHT ) ) )
            {
                if ( bodyTag == null )
                {
                    throw new JspException( "Le menu doit etre dans un af:body" );
                }
                if ( WelcomConfigurator.getCharte() == Charte.V2_001 )
                {
                    throw new JspException(
                                            "Le menu light n'est pas utilisable avec la charte 001, mettre la cle welcom.menu.light a false" );
                }
                bodyTag.pushBufMenu( sb.toString() );
            }
            else
            {
                if ( WelcomConfigurator.getCharte() == Charte.V3_001 )
                {
                    if ( bodyTag == null )
                    {
                        throw new JspException( "Le menu doit etre dans un af:body" );
                    }
                    bodyTag.pushBufMenu( sb.toString() );
                }
                else
                {
                    ResponseUtils.write( pageContext, sb.toString() );
                }
            }

        }
        else
        {
            // ecrit le flag menu vide, permet de savoir si la generation a été effectuée
            ResponseUtils.write( pageContext, "<!-- Menu Vide -->" );
        }

        menu = null;
        release();
        return EVAL_PAGE;
    }

    /**
     * Retourne le nom du menu si un nom est spécifie alors retoune le nom si un menu a deja été spécifié retourne "m2"
     * et qu'il n'y a pas de nom spécifié sinon retoune "m"
     * 
     * @return le nom du menu
     */
    private String getMenuName()
    {
        String nameString = "m";
        if ( !GenericValidator.isBlankOrNull( getName() ) )
        {
            nameString = getName();
        }
        else if ( pageContext.getRequest().getAttribute( MENU_KEY ) != null )
        {
            nameString = "m2";
        }
        return nameString;
    }

    /**
     * Retourne l'orientayion du menu
     * 
     * @return L'orienation du menu 1 vertial, 0 horizontal
     */
    private String getOrientation()
    {
        // Recherche si on a un parent CanevasLeft;
        // Recherche si un parent est du bon type
        Tag curParent = findAncestorWithClass( this, CanvasLeftMenuTag.class );

        if ( curParent instanceof CanvasLeftMenuTag )
        {
            ( (CanvasLeftMenuTag) curParent ).setContainsMenu( true );
            return "1";
        }
        else
        {
            return "0";
        }
    }

    /**
     * @return le body
     */
    public String doPrintBody()
    {
        // Impression du Body
        return menu.doPrint( 0 );
    }

    /**
     * @return le header
     * @throws JspException exception pouvant etre levee
     */
    public String doPrintHeader()
        throws JspException
    {
        return render.doPrintHeader( this, pageContext );
    }

    /**
     * @return le footer
     */
    public String doPrintFooter()
    {
        return render.doPrintFooter();
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        return EVAL_BODY_INCLUDE;
        // return SKIP_BODY;
    }

    /**
     * Returns the bayadere.
     * 
     * @return String
     */
    public String getBayadere()
    {
        return bayadere;
    }

    /**
     * Sets the bayadere.
     * 
     * @param pBayadere The bayadere to set
     */
    public void setBayadere( final String pBayadere )
    {
        bayadere = pBayadere;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string le name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * Relache le tag
     */
    public void release()
    {

        menu = null;
        bayadere = "";
        name = "";
        MENU_KEY = "com.airfrance.welcom.taglib.menu";
    }

}