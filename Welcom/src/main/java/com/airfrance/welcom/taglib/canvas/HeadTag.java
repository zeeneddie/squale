/*
 * Créé le 9 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.canvas;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.util.WRequestUtils;

/**
 * HeadTag
 */
public class HeadTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 438578370941997639L;

    /** Constante */
    public static final String KEY_TAG = "com.airfrance.welcom.taglib.HEAD";

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

    /**
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        pageContext.getRequest().setAttribute( KEY_TAG, this );

        ResponseUtils.write( pageContext, "<HTML>" );

        ResponseUtils.write( pageContext, "<HEAD>" );

        final String charset = WelcomConfigurator.getMessage( WelcomConfigurator.ENCODING_CHARSET );
        ResponseUtils.write( pageContext, "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + charset
            + "\">" );

        doTitle();

        CanvasUtil.appendCssJs( this, pageContext );
        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        ResponseUtils.write( pageContext, "</HEAD>" ); 
        this.release();
        return super.doEndTag();
    }

    /**
     * Gere le titre
     * 
     * @throws JspException exception pouvant etre levee
     */
    protected void doTitle()
        throws JspException
    {
        // Recupere le ttle definit du parent
        if ( GenericValidator.isBlankOrNull( titleKey ) )
        {
            Tag curParent = null;

            // Recherche si un parent est du bon type
            for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof PageTag ); curParent =
                curParent.getParent() )
            {
                ;
            }

            if ( curParent != null )
            {
                final PageTag pageTag = (PageTag) curParent;
                this.setTitleKey( pageTag.getTitleKey() );
                this.setTitleKeyArg0( pageTag.getTitleKeyArg0() );
                this.setTitleKeyArg1( pageTag.getTitleKeyArg1() );
                this.setTitleKeyArg2( pageTag.getTitleKeyArg2() );
                this.setTitleKeyArg3( pageTag.getTitleKeyArg3() );
            }
        }

        if ( !GenericValidator.isBlankOrNull( titleKey ) )
        {
            final String titlekeyArgs[] = { titleKeyArg0, titleKeyArg1, titleKeyArg2, titleKeyArg3 };

            String message = WRequestUtils.message( super.pageContext, titleKey, titlekeyArgs );

            if ( GenericValidator.isBlankOrNull( message ) )
            {
                message = titleKey;
            }

            ResponseUtils.write( pageContext, "<TITLE>" + message + "</TITLE>" );
        }
    }

    /**
     * @return titleKey
     */
    public String getTitleKey()
    {
        return titleKey;
    }

    /**
     * @return titleKey
     */
    public String getTitleKeyArg0()
    {
        return titleKeyArg0;
    }

    /**
     * @return titleKey
     */
    public String getTitleKeyArg1()
    {
        return titleKeyArg1;
    }

    /**
     * @return titleKey
     */
    public String getTitleKeyArg2()
    {
        return titleKeyArg2;
    }

    /**
     * @return titleKey
     */
    public String getTitleKeyArg3()
    {
        return titleKeyArg3;
    }

    /**
     * @param string le titleKey
     */
    public void setTitleKey( final String string )
    {
        titleKey = string;
    }

    /**
     * @param string le titleKeyArg0
     */
    public void setTitleKeyArg0( final String string )
    {
        titleKeyArg0 = string;
    }

    /**
     * @param string le titleKeyArg1
     */
    public void setTitleKeyArg1( final String string )
    {
        titleKeyArg1 = string;
    }

    /**
     * @param string titleKeyArg2
     */
    public void setTitleKeyArg2( final String string )
    {
        titleKeyArg2 = string;
    }

    /**
     * @param string le titleKeyArg3
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
    }

}