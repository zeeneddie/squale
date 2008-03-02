/*
 * Créé le 26 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Access
    implements Serializable
{

    /** ID pour la serialization */
    static final long serialVersionUID = -986211949189598424L;

    /** Droit READWRITE */
    public static final String READWRITE = "READWRITE";

    /** Droit READONLY */
    public static final String READONLY = "READONLY";

    /** Droit NONE */
    public static final String NONE = "NONE";

    /** Droit YES */
    public static final String YES = "YES";

    /** Droit NO */
    public static final String NO = "NO";

    /** Poids des droits */
    private static HashMap poids = null;

    /** poid 0 */
    public static final Integer POIDS_0 = new Integer( 0 );

    /** poid 1 */
    public static final Integer POIDS_1 = new Integer( 1 );

    /** poid 2 */
    public static final Integer POIDS_2 = new Integer( 2 );

    /** poid 3 */
    public static final Integer POIDS_3 = new Integer( 3 );

    /** poid 4 */
    public static final Integer POIDS_4 = new Integer( 4 );

    /** poid 5 */
    public static final Integer POIDS_5 = new Integer( 5 );

    /**
     * Retourne le droit le plus fort
     * 
     * @param access1 : Droit d'accés 1 (READWRITE,READONLY,NONE,YES,NO)
     * @param access2 : Droit d'accés 2 (READWRITE,READONLY,NONE,YES,NO)
     * @return Droit le plus fort !
     */
    public static String getMaxAccess( final String access1, final String access2 )
    {
        final int poid1 = getAccessToPoids( access1 );
        final int poid2 = getAccessToPoids( access2 );
        if ( poid1 > poid2 )
        {
            return getPoidsToAccess( poid1 );
        }
        else
        {
            return getPoidsToAccess( poid2 );
        }

    }

    /**
     * Convertie la chaine en poid
     * 
     * @param value : access
     * @return : poid correspondant
     */
    private static int getAccessToPoids( final String value )
    {
        if ( poids == null )
        {
            initPoids();
        }
        return ( (Integer) poids.get( value ) ).intValue();
    }

    /**
     * Convetie le poid en access
     * 
     * @param poid : poid
     * @return : l'accés
     */
    private static String getPoidsToAccess( final int poid )
    {
        if ( poids == null )
        {
            initPoids();
        }
        return ( (String) poids.get( new Integer( poid ) ) );
    }

    /**
     * Initialise la table de correspondance poid access
     */
    private static synchronized void initPoids()
    {
        poids = new HashMap();
        poids.put( READWRITE, POIDS_5 );
        poids.put( READONLY, POIDS_3 );
        poids.put( NONE, POIDS_1 );
        poids.put( YES, POIDS_4 );
        poids.put( NO, POIDS_2 );
        poids.put( "", POIDS_0 );
        poids.put( POIDS_5, READWRITE );
        poids.put( POIDS_3, READONLY );
        poids.put( POIDS_1, NONE );
        poids.put( POIDS_4, YES );
        poids.put( POIDS_2, NO );
        poids.put( POIDS_0, "" );
    }

    /**
     * Verifie le droit d'accé
     * 
     * @param pageAccess : Droit passé en parametre
     * @param modeATester : A tester vace ...
     * @return Vrais si OK
     */
    public static boolean checkAccessDroit( final String pageAccess, final String modeATester )
    {
        boolean returnVal = false;

        if ( !GenericValidator.isBlankOrNull( pageAccess ) )
        {
            returnVal = pageAccess.equalsIgnoreCase( modeATester.toUpperCase() );
        }

        return returnVal;
    }

    /**
     * Vefifie s'il doit a afficher la page (READONLY ou READWRITE)
     * 
     * @param pageAccess : Droit
     * @return : Vrais si READONLY ou READWRITE
     */
    public static boolean checkAccessDroit( final String pageAccess )
    {
        return ( checkAccessDroit( pageAccess, Access.READONLY ) || checkAccessDroit( pageAccess, Access.YES ) || checkAccessDroit(
                                                                                                                                    pageAccess,
                                                                                                                                    Access.READWRITE ) );
    }

    /**
     * Parse le token || pour la mise en place du ou
     * 
     * @param logonbean : Le bien de logon pour la verifieation de la clef
     * @param accessKey : Valeur a tester
     * @return Vrai si OK
     */
    public static String getMultipleSecurityPage( final WILogonBeanSecurity logonbean, final String accessKey )
    {

        if ( logonbean == null )
        {
            return READWRITE;
        }

        final StringTokenizer droitSplitter = new StringTokenizer( accessKey, "||" );
        String access = NONE;

        while ( droitSplitter.hasMoreElements() )
        {
            final String item = (String) droitSplitter.nextElement();

            final String acc = logonbean.getAccess( item );

            if ( ( ( checkAccessDroit( access, NONE ) || checkAccessDroit( access, NO ) ) && checkAccessDroit( acc ) )
                || ( checkAccessDroit( access, READONLY ) && checkAccessDroit( acc, READWRITE ) ) )
            {
                access = acc;
            }
        }

        return access;
    }

    /**
     * Calcule le droit du tag ...
     * 
     * @param pageContext contexte
     * @param accessKey valeur de l'accessKey du tag
     * @param forceReadWrite si on est on forcage
     * @param overridePageAccess sur on surchage le page page
     * @return le droit calculé
     * @throws JspException leve une execption si le droit retouné n'est pas NONE, READWRITE, READONLY
     */
    public static String computeTagReadWriteAccess( PageContext pageContext, String accessKey, boolean forceReadWrite,
                                                    boolean overridePageAccess )
        throws JspException
    {
        // Recupere le droit sur la page
        String pageAccess = (String) pageContext.getAttribute( "access" );

        // Par defaut met un droit Lecture/Ecriture
        String accessTag = Access.READWRITE;

        // Retourne un accés /lecture ecriture si on force le flag en lecture
        // ecriture.
        // l'attribut accessKey est ignoré sur le pageaccess est null
        if ( forceReadWrite )
        {
            return accessTag;
        }

        if ( overridePageAccess == false )
        {
            if ( pageAccess == null )
            {
                overridePageAccess = true;
            }
            else
            {
                if ( isNotPageAccess( pageAccess ) )
                {
                    throw new JspException(
                                            "L'attribut accessKey doit retourner une valeur READWRITE ou READONLY ou NONE (cf getSecuritePage()) : "
                                                + pageAccess );
                }
                accessTag = pageAccess;
            }
        }
        // Si on a definit un access sur le tag !
        if ( !GenericValidator.isBlankOrNull( accessKey ) )
        {

            String accessTagMerge =
                getAccessTagMergeWithPageAccess( pageContext, overridePageAccess, pageAccess, accessKey );
            if ( accessTagMerge != null )
            {
                accessTag = accessTagMerge;
            }
        }

        return accessTag;
    }

    /**
     * Retourne le droit merge entre le pageAccess, et l'accés du tag
     * 
     * @param overridePageAccess si on autorise la surcharge
     * @param pageAccess l'accées de la page
     * @param accessKey du tag courant
     * @param pageContext page context
     * @return Retourne le droit merge entre le pageAccess, et l'accés du tag
     */
    private static String getAccessTagMergeWithPageAccess( PageContext pageContext, boolean overridePageAccess,
                                                           String pageAccess, final String accessKey )
    {
        String accessTag = null;

        // recherche le logonbean
        final Object o = pageContext.getSession().getAttribute( WConstants.USER_KEY );

        if ( o != null )
        {
            // Verifie qi on est en mode sécurisé
            if ( o instanceof WILogonBeanSecurity )
            {
                final WILogonBeanSecurity lb = (WILogonBeanSecurity) o;
                final String computedAccessTag = Access.getMultipleSecurityPage( lb, accessKey );
                if ( overridePageAccess )
                {
                    accessTag = computedAccessTag;
                }
                else
                // si mis a false explicitement
                if ( Util.isEquals( pageAccess, Access.READONLY )
                    && Util.isEquals( computedAccessTag, Access.READWRITE ) )
                {
                    accessTag = Access.READONLY;
                }
                else
                {
                    accessTag = computedAccessTag;
                }
            }
        }
        return accessTag;
    }

    /**
     * @param access Accées NONE / NO / YES / READONLY / READWRITE
     * @return vrais si du type NONE / READONLY / READWRITE
     */
    private static boolean isNotPageAccess( String access )
    {
        return ( access != null )
            && !( Util.isEquals( access, Access.READONLY ) || Util.isEquals( access, Access.READWRITE ) || Util.isEquals(
                                                                                                                          access,
                                                                                                                          Access.NONE ) );
    }

}