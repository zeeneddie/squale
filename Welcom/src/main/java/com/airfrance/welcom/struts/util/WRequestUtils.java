/*
 * Créé le 6 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.util.RequestUtils;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WRequestUtils
{

    /** Logger */
    private final static Log logger = LogFactory.getLog( WRequestUtils.class );

    /**
     * Look up and return a message string, based on the specified parameters.
     * 
     * @param pageContext The PageContext associated with this request
     * @param key Message key to be looked up and returned
     * @return message string
     */
    public static String message( final PageContext pageContext, final String key )
    {
        return ( message( pageContext, key, null ) );
    }

    /**
     * Look up and return a message string, based on the specified parameters.
     * 
     * @param pageContext The PageContext associated with this request
     * @param key Message key to be looked up and returned
     * @param args Replacement parameters for this message
     * @return message string saved in the request already)
     */
    public static String message( final PageContext pageContext, final String key, final Object args[] )
    {
        try
        {
            logger.debug( "Message : "
                + RequestUtils.message( pageContext, Globals.MESSAGES_KEY, Globals.LOCALE_KEY, key, args ) );
            logger.debug( "Message locale : " + pageContext.getSession().getAttribute( Globals.LOCALE_KEY ) );
            return RequestUtils.message( pageContext, Globals.MESSAGES_KEY, Globals.LOCALE_KEY, key, args );
        }
        catch ( final JspException e )
        {
            return "";
        }
    }
}