package com.airfrance.squaleweb.tagslib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 */
public class TrendTag
    extends TagSupport
{

    /** le paramètre "nom" du tag */
    private String name;

    /** le paramètre "current" du tag qui désigne la note actuelle */
    private String current;

    /** le paramètre "predecessor" du tag qui désigne la note précédente */
    private String predecessor;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        ResponseUtils.write( pageContext, getImageForTrend( (String) RequestUtils.lookup( pageContext, name, current,
                                                                                          null ),
                                                            (String) RequestUtils.lookup( pageContext, name,
                                                                                          predecessor, null ) ) );
        return SKIP_BODY;
    }

    /**
     * Obtention de l'image associée à une tendance
     * 
     * @param pCurrentMark la note actuelle
     * @param pPreviousMark l'ancienne note
     * @return l'url avec l'image correspondant à l'évolution
     */
    private String getImageForTrend( String pCurrentMark, String pPreviousMark )
    {
        String result = SqualeWebActionUtils.getImageForTrend( pCurrentMark, pPreviousMark );
        return "<img src=\"" + result + "\" border=\"0\" />";
    }

    /**
     * @return la note courante
     */
    public String getCurrent()
    {
        return current;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return la note précédente
     */
    public String getPredecessor()
    {
        return predecessor;
    }

    /**
     * change la note courante
     * 
     * @param newCurrent la nouvelle note
     */
    public void setCurrent( String newCurrent )
    {
        current = newCurrent;
    }

    /**
     * change le nom
     * 
     * @param newName la nouvelle note
     */
    public void setName( String newName )
    {
        name = newName;
    }

    /**
     * change la note courante
     * 
     * @param newPredecessor la nouvelle note
     */
    public void setPredecessor( String newPredecessor )
    {
        predecessor = newPredecessor;
    }

}
