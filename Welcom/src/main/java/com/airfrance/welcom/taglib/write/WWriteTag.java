/*
 * Créé le 5 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.write;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.taglib.bean.WriteTag;
import org.apache.struts.util.MessageResources;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WWriteTag
    extends WriteTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 4719717981435342467L;

    /** parametre du tag */
    private String dateFormat;

    /** parametre du tag */
    private String dateFormatKey;

    /**
     * @see org.apache.struts.taglib.bean.WriteTag#formatValue(java.lang.Object)
     */
    protected String formatValue( final Object valueToFormat )
        throws JspException
    {
        if ( GenericValidator.isBlankOrNull( dateFormat ) && GenericValidator.isBlankOrNull( dateFormatKey ) )
        {
            return super.formatValue( valueToFormat );
        }
        else
        {
            // On retrouve le simpleDateFormat
            SimpleDateFormat simpleDateFormat;
            final MessageResources resources =
                (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
            final Locale localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );
            if ( !GenericValidator.isBlankOrNull( dateFormatKey ) )
            {
                simpleDateFormat =
                    new SimpleDateFormat( resources.getMessage( localeRequest, dateFormatKey ), localeRequest );
            }
            else
            {
                simpleDateFormat = new SimpleDateFormat( dateFormat, localeRequest );
            }
            // on formate la date
            String result = "";
            if ( valueToFormat instanceof Date )
            {
                result = simpleDateFormat.format( (Date) valueToFormat );
            }
            else if ( valueToFormat instanceof String && !GenericValidator.isBlankOrNull( (String) valueToFormat ) )
            {
                result = simpleDateFormat.format( new Date( Long.parseLong( (String) valueToFormat ) ) );
            }
            return result;
        }
    }

    /**
     * @return dateFormat
     */
    public String getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @return dateFormatKey
     */
    public String getDateFormatKey()
    {
        return dateFormatKey;
    }

    /**
     * @param string dateFormat
     */
    public void setDateFormat( final String string )
    {
        dateFormat = string;
    }

    /**
     * @param string dateFormatKey
     */
    public void setDateFormatKey( final String string )
    {
        dateFormatKey = string;
    }

}
