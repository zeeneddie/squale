/*
 * Créé le 8 juin 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.field.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.logic.IterateTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

/**
 * Classe utilitaire pour les tags Date de création : (21/12/2001 14:19:20)
 */
public class TagUtils
{
    /**
     * Commentaire relatif au constructeur TagUtils.
     */
    public TagUtils()
    {
        super();
    }

    /**
     * @param request la request
     * @param property la property
     * @return la chaine representant la date
     */
    public static String getDateHeureFromDateTag( final javax.servlet.http.HttpServletRequest request,
                                                  final String property )
    {
        final String date = request.getParameter( property + "WDate" );
        final String heure = request.getParameter( property + "WHour" );

        if ( ( ( date == null ) || ( date.length() < 1 ) ) && ( ( heure == null ) || ( heure.length() < 1 ) ) )
        {
            return null;
        }
        else if ( ( ( date == null ) || ( date.length() < 1 ) ) && ( heure != null ) && ( heure.length() > 0 ) )
        {
            return null;
        }
        else if ( ( date != null ) && ( date.length() > 0 ) && ( ( heure == null ) || ( heure.length() < 1 ) ) )
        {
            return ( date );
        }

        return date + " " + heure;
    }

    /**
     * Ajout l'attribut avec sa valeur au stringbuffer
     * 
     * @param sb stringbuffer
     * @param name nom
     * @param value valeur
     */
    public static void addParam( StringBuffer sb, String name, String value )
    {

        if ( value != null )
        {
            if ( sb.length() > 1 && sb.charAt( sb.length() - 1 ) != ' ' )
            {
                sb.append( " " );
            }
            sb.append( name + "=\"" );
            sb.append( value );
            sb.append( "\"" );
        }
    }

    /**
     * Appends bean name with index in brackets for tags with 'true' value in 'indexed' attribute.
     * 
     * @param handlers The StringBuffer that output will be appended to.
     * @exception JspException if 'indexed' tag used outside of iterate tag.
     */
    public static void prepareIndex( PageContext pageContext, TagSupport tag, MessageResources messages,
                                     StringBuffer handlers, String name )
        throws JspException
    {
        // look for outer iterate tag
        IterateTag iterateTag = (IterateTag) TagSupport.findAncestorWithClass( tag, IterateTag.class );
        if ( iterateTag == null )
        {
            // this tag should only be nested in iteratetag, if it's not, throw exception
            JspException e = new JspException( messages.getMessage( "indexed.noEnclosingIterate" ) );
            RequestUtils.saveException( pageContext, e );
            throw e;
        }
        if ( name != null )
            handlers.append( name );
        handlers.append( "[" );
        handlers.append( iterateTag.getIndex() );
        handlers.append( "]" );
        if ( name != null )
            handlers.append( "." );
    }

    /**
     * Retourne la fonction javascript de validation
     * 
     * @param property property
     * @param type type TEXTAREA/NUMBER/EMAIL/ etc ...
     * @param isRequired isRequired
     * @param isUpperCase Majuscule
     * @param isFirstUpperCase premiere lettre en majuscule
     * @param accent accepte ou pas les accent
     * @return la fonction javascript correspondante
     */
    public static String getJavascriptCheckValue( String property, String type, boolean isRequired,
                                                  boolean isUpperCase, boolean isFirstUpperCase, boolean accent )
    {

        return "checkValue(this, '" + property + "','" + type + "'," + isRequired + "," + isUpperCase + ","
            + isFirstUpperCase + "," + accent + ");";

    }

    /**
     * Retourne la fonction javascript de validation
     * 
     * @param property property
     * @param type type TEXTAREA/NUMBER/EMAIL/ etc ...
     * @param isRequired isRequired
     * @param isUpperCase Majuscule
     * @param isFirstUpperCase premiere lettre en majuscule
     * @param accent accepte ou pas les accent
     * @param pattern date
     * @return la fonction javascript correspondante
     */
    public static String getJavascriptCheckValue( String property, String type, boolean isRequired,
                                                  boolean isUpperCase, boolean isFirstUpperCase, boolean accent,
                                                  String datepattern )
    {

        return "checkValue(this, '" + property + "','" + type + "'," + isRequired + "," + isUpperCase + ","
            + isFirstUpperCase + "," + accent + ",'" + datepattern + "');";

    }

    /**
     * Retourne la fonction javascript pour verifie si on contient des caracter spéciaux
     * 
     * @param property property
     * @return le fonction javascirpt correspondant
     */
    public static String getJavascriptForCheckSpecialChar( String property )
    {
        return "checkCaractereSpecial(this, '" + property + "');";
    }

}