/*
 * Créé le 14 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.canvas;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.struts.util.WRequestUtils;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * Tag CanvasCenterTag
 */
public class CanvasCenterTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -5344130165170979156L;

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
    private String subTitleKey = "";

    /** parametre du tag */
    private String subTitleKeyArg0 = "";

    /** parametre du tag */
    private String subTitleKeyArg1 = "";

    /** parametre du tag */
    private String subTitleKeyArg2 = "";

    /** parametre du tag */
    private String subTitleKeyArg3 = "";

    /** render */
    private static ICanvasCenterRenderer render =
        (ICanvasCenterRenderer) RendererFactory.getRenderer( RendererFactory.CANVAS_CENTER );

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {

        final StringBuffer sb = new StringBuffer();
        sb.append( render.drawStart( pageContext.getRequest().getAttribute( CanvasLeftMenuTag.KEY_CANVASLEFT ) != null ) );

        // Recupere le ttle definit du parent
        if ( GenericValidator.isBlankOrNull( titleKey ) )
        {

            // Recherche si un parent est du bon type
            final PageTag pageTag = (PageTag) findAncestorWithClass( this, PageTag.class );

            if ( pageTag != null )
            {
                this.setTitleKey( pageTag.getTitleKey() );
                this.setTitleKeyArg0( pageTag.getTitleKeyArg0() );
                this.setTitleKeyArg1( pageTag.getTitleKeyArg1() );
                this.setTitleKeyArg2( pageTag.getTitleKeyArg2() );
                this.setTitleKeyArg3( pageTag.getTitleKeyArg3() );

                if ( GenericValidator.isBlankOrNull( subTitleKey ) )
                {
                    this.setSubTitleKey( pageTag.getSubTitleKey() );
                    this.setSubTitleKeyArg0( pageTag.getSubTitleKeyArg0() );
                    this.setSubTitleKeyArg1( pageTag.getSubTitleKeyArg1() );
                    this.setSubTitleKeyArg2( pageTag.getSubTitleKeyArg2() );
                    this.setSubTitleKeyArg3( pageTag.getSubTitleKeyArg3() );
                }
            }
        }

        if ( !GenericValidator.isBlankOrNull( titleKey ) )
        {
            drawTitreBar( sb );
        }

        ResponseUtils.write( pageContext, sb.toString() );

        return EVAL_PAGE;
    }

    /**
     * Methode charge de dessiner le titre
     * 
     * @param sb le stringbuffer dans lequel elle ecrit le html
     * @throws JspException exception pouvant etre levee
     */
    private void drawTitreBar( final StringBuffer sb )
        throws JspException
    {

        // construit le titre
        final String titlekeyArgs[] = { titleKeyArg0, titleKeyArg1, titleKeyArg2, titleKeyArg3 };
        String messageTitre = WRequestUtils.message( super.pageContext, titleKey, titlekeyArgs );
        if ( GenericValidator.isBlankOrNull( messageTitre ) )
        {
            messageTitre = titleKey;
        }

        // construit le soustitre
        final String subTitlekeyArgs[] = { subTitleKeyArg0, subTitleKeyArg1, subTitleKeyArg2, subTitleKeyArg3 };
        String messageSousTitre = WRequestUtils.message( super.pageContext, subTitleKey, subTitlekeyArgs );

        if ( GenericValidator.isBlankOrNull( messageSousTitre ) )
        {
            messageSousTitre = subTitleKey;
        }

        sb.append( render.drawTitre( messageTitre, subTitleKey, messageSousTitre ) );
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        ResponseUtils.write( pageContext, render.drawEnd() );

        return EVAL_PAGE;
    }

    /**
     * Accesseur
     * 
     * @return le sous titre
     */
    public String getSubTitleKey()
    {
        return subTitleKey;
    }

    /**
     * Accesseur
     * 
     * @return string le 1er argument du sous titre (messageFormat)
     */
    public String getSubTitleKeyArg0()
    {
        return subTitleKeyArg0;
    }

    /**
     * Accesseur
     * 
     * @return string le 2eme argument du sous titre (messageFormat)
     */
    public String getSubTitleKeyArg1()
    {
        return subTitleKeyArg1;
    }

    /**
     * Accesseur
     * 
     * @return string le 3eme argument du sous titre (messageFormat)
     */
    public String getSubTitleKeyArg2()
    {
        return subTitleKeyArg2;
    }

    /**
     * Accesseur
     * 
     * @return string le 4eme argument du sous titre (messageFormat)
     */
    public String getSubTitleKeyArg3()
    {
        return subTitleKeyArg3;
    }

    /**
     * Accesseur
     * 
     * @return le titre
     */
    public String getTitleKey()
    {
        return titleKey;
    }

    /**
     * Accesseur
     * 
     * @return string le 1er argument du titre (messageFormat)
     */
    public String getTitleKeyArg0()
    {
        return titleKeyArg0;
    }

    /**
     * Accesseur
     * 
     * @return string le 2eme argument du titre (messageFormat)
     */
    public String getTitleKeyArg1()
    {
        return titleKeyArg1;
    }

    /**
     * Accesseur
     * 
     * @return string le 3eme argument du titre (messageFormat)
     */
    public String getTitleKeyArg2()
    {
        return titleKeyArg2;
    }

    /**
     * Accesseur
     * 
     * @return string le 4eme argument du titre (messageFormat)
     */
    public String getTitleKeyArg3()
    {
        return titleKeyArg3;
    }

    /**
     * Accesseur
     * 
     * @param string le sous titre
     */
    public void setSubTitleKey( final String string )
    {
        subTitleKey = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 1er argument du sous titre (messageFormat)
     */
    public void setSubTitleKeyArg0( final String string )
    {
        subTitleKeyArg0 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 2eme argument du sous titre (messageFormat)
     */
    public void setSubTitleKeyArg1( final String string )
    {
        subTitleKeyArg1 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 3eme argument du sous titre (messageFormat)
     */
    public void setSubTitleKeyArg2( final String string )
    {
        subTitleKeyArg2 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 4eme argument du sous titre (messageFormat)
     */
    public void setSubTitleKeyArg3( final String string )
    {
        subTitleKeyArg3 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le titre
     */
    public void setTitleKey( final String string )
    {
        titleKey = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 1er argument du title key (messageFormat)
     */
    public void setTitleKeyArg0( final String string )
    {
        titleKeyArg0 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 2eme argument du title key (messageFormat)
     */
    public void setTitleKeyArg1( final String string )
    {
        titleKeyArg1 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 3eme argument du title key (messageFormat)
     */
    public void setTitleKeyArg2( final String string )
    {
        titleKeyArg2 = string;
    }

    /**
     * Accesseur
     * 
     * @param string le 4eme argument du title key (messageFormat)
     */
    public void setTitleKeyArg3( final String string )
    {
        titleKeyArg3 = string;
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
        subTitleKey = "";
        subTitleKeyArg0 = "";
        subTitleKeyArg1 = "";
        subTitleKeyArg2 = "";
        subTitleKeyArg3 = "";
    }

}