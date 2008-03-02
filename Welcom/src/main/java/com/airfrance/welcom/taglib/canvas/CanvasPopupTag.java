/*
 * Créé le 8 nov. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.canvas;

import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.util.WRequestUtils;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * CanvasPopupTag
 */
public class CanvasPopupTag
    extends CanvasBodyEventHandler
{
    /**
     * 
     */
    private static final long serialVersionUID = 7245029038764559940L;

    /** Constante CLOSE_KEY */
    private static final String CLOSE_KEY = "popup.fermer";

    /** parametre du tag */
    private String titleKey = "";

    /** parametre du tag */
    private String titleKeyArg0 = "";

    /** parametre du tag */
    private String titleKeyArg1 = "";

    /** parametre du tag */
    private String titleKeyArg2 = "";

    /** parametre du tag */
    private String titleKeyArg3 = "";

    /** parametre du tag */
    private String closeKey = "";

    /** le message ressource */
    private MessageResources resources = null;

    /** la locale */
    protected Locale localeRequest = Locale.FRENCH;

    /** render */
    private static ICanvasPopupRenderer render =
        (ICanvasPopupRenderer) RendererFactory.getRenderer( RendererFactory.CANVAS_POPUP );

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {

        // Ajoute la fenetre d'erreur de popup
        final String onload = CanvasTag.addMessagePopupOnOnLoad( pageContext, null );

        // Recuperer le fichier des Bundle
        resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );

        // Recupere la locale de la page
        localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        String titreBar = render.drawTitre( getTitreMessage() );

        ResponseUtils.write( pageContext, render.drawStartHead( getTitreMessage() ) );

        CanvasUtil.appendCssJs( getParent(), pageContext );

        ResponseUtils.write( pageContext, render.drawEndHead() );

        ResponseUtils.write( pageContext, render.drawStartBody( prepareEventHandlers(), titreBar ) );

        return EVAL_PAGE;
    }

    /**
     * Gere la barre de titre
     * 
     * @throws JspException exception pouvant etre levee
     * @return tritre du message
     */
    private String getTitreMessage()
        throws JspException
    {
        // Titre
        final String titlekeyArgs[] = { titleKeyArg0, titleKeyArg1, titleKeyArg2, titleKeyArg3 };
        String message = WRequestUtils.message( super.pageContext, titleKey, titlekeyArgs );

        if ( GenericValidator.isBlankOrNull( message ) )
        {
            message = titleKey;
        }

        return message;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final StringBuffer sb = new StringBuffer();
        String close = "";

        if ( !GenericValidator.isBlankOrNull( closeKey ) )
        {
            close = resources.getMessage( localeRequest, closeKey );

            if ( close == null )
            {
                close = WelcomConfigurator.getMessage( CLOSE_KEY );
            }
        }
        else
        {
            close = WelcomConfigurator.getMessage( CLOSE_KEY );
        }

        ResponseUtils.write( pageContext, render.drawEndBody( close ) );

        return EVAL_PAGE;
    }

    /**
     * Accesseur
     * 
     * @return titleKey
     */
    public String getTitleKey()
    {
        return titleKey;
    }

    /**
     * Accesseur
     * 
     * @return titleKeyArg0
     */
    public String getTitleKeyArg0()
    {
        return titleKeyArg0;
    }

    /**
     * Accesseur
     * 
     * @return titleKeyArg1
     */
    public String getTitleKeyArg1()
    {
        return titleKeyArg1;
    }

    /**
     * Accesseur
     * 
     * @return titleKeyArg2
     */
    public String getTitleKeyArg2()
    {
        return titleKeyArg2;
    }

    /**
     * Accesseur
     * 
     * @return titleKeyArg3
     */
    public String getTitleKeyArg3()
    {
        return titleKeyArg3;
    }

    /**
     * Accesseur
     * 
     * @param string le titleKey
     */
    public void setTitleKey( final String string )
    {
        titleKey = string;
    }

    /**
     * Accesseur
     * 
     * @param string le titleKeyArg0
     */
    public void setTitleKeyArg0( final String string )
    {
        titleKeyArg0 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le titleKeyArg1
     */
    public void setTitleKeyArg1( final String string )
    {
        titleKeyArg1 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le titleKeyArg2
     */
    public void setTitleKeyArg2( final String string )
    {
        titleKeyArg2 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le titleKeyArg3
     */
    public void setTitleKeyArg3( final String string )
    {
        titleKeyArg3 = string;
    }

    /**
     * Accesseur
     * 
     * @return closeKey
     */
    public String getCloseKey()
    {
        return closeKey;
    }

    /**
     * Accesseur
     * 
     * @param string le closeKey
     */
    public void setCloseKey( final String string )
    {
        closeKey = string;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release()
    {
        super.release();
        titleKey = "";
        titleKeyArg0 = "";
        titleKeyArg1 = "";
        titleKeyArg2 = "";
        titleKeyArg3 = "";
        closeKey = "";
        resources = null;
        localeRequest = Locale.FRENCH;
    }

}