package com.airfrance.squaleweb.tagslib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 */
public class PictoTag
    extends TagSupport
{

    /** le paramètre "nom" du tag */
    private String name;

    /** le paramètre "propriété" du tag */
    private String property;

    /** permet de récupérer la note si on la connait sert pour la page mark */
    private String mark;

    /**
     * Affiche l'image.
     * 
     * @param pNote la note ou l'index.
     * @param pRequest la requête
     * @return le chemin de l'image
     */
    private String generatePicto( String pNote, HttpServletRequest pRequest )
    {
        String imgTag = "";
        imgTag = SqualeWebActionUtils.generatePictoWithTooltip( pNote, pRequest );
        return imgTag;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        if ( mark != null )
        {
            ResponseUtils.write( pageContext, generatePicto( mark, (HttpServletRequest) pageContext.getRequest() ) );
        }
        else
        {
            ResponseUtils.write( pageContext, generatePicto( (String) RequestUtils.lookup( pageContext, name, property,
                                                                                           null ),
                                                             (HttpServletRequest) pageContext.getRequest() ) );
        }
        return SKIP_BODY;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doEndTag()
        throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return la propriété
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * change le nom
     * 
     * @param newName le nouveau nom
     */
    public void setName( String newName )
    {
        name = newName;
    }

    /**
     * change la propriété
     * 
     * @param newProperty la nouvelle propriété
     */
    public void setProperty( String newProperty )
    {
        property = newProperty;
    }

    /**
     * @return la note
     */
    public String getMark()
    {
        return mark;
    }

    /**
     * @param newMark la nouvelle note
     */
    public void setMark( String newMark )
    {
        mark = newMark;
    }
}
