/*
 * Créé le 20 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.poll;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class PollTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 4356247165916857351L;

    /** la durée entre 2 refresh */
    private String interval = "";

    /** l'url pour recuperer le contenu */
    private String href = "";

    /** l'id du div généré */
    private String id = "";

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        final StringBuffer buffer = new StringBuffer();
        buffer.append( "<div id=\"" );
        buffer.append( id );
        buffer.append( "\">" );
        buffer.append( "</div>" );
        buffer.append( "<script>" );
        buffer.append( "poll('" );
        buffer.append( id );
        buffer.append( "','" );
        buffer.append( href );
        buffer.append( "'," );
        buffer.append( Integer.parseInt( interval ) * 1000 );
        buffer.append( ");" );
        buffer.append( "</script>" );
        ResponseUtils.write( pageContext, buffer.toString() );
        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        interval = "";
        href = "";
    }

    /**
     * @return href
     */
    public String getHref()
    {
        return href;
    }

    /**
     * @return interval
     */
    public String getInterval()
    {
        return interval;
    }

    /**
     * @param string href
     */
    public void setHref( final String string )
    {
        href = string;
    }

    /**
     * @param string interval
     */
    public void setInterval( final String string )
    {
        interval = string;
    }

    /**
     * @return id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param string id
     */
    public void setId( final String string )
    {
        id = string;
    }

}
