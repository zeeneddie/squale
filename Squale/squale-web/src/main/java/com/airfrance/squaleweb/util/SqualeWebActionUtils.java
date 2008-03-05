package com.airfrance.squaleweb.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.util.ConstantRulesChecking;
import com.airfrance.squaleweb.resources.WebMessages;

/**
 * Fournit des fonctionnalités utiles à plusieurs actions.
 * 
 * @author M400842
 */
public class SqualeWebActionUtils
{
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( SqualeWebActionUtils.class );

    /**
     * Images en fonction de la note 0..3
     */
    public static final String[] IMG =
        { "images/pictos/bad.png", "images/pictos/good.png", "images/pictos/best.png",
            "images/pictos/super.png", "images/pictos/na.gif" };

    /**
     * Constante "-" pour l'affichage
     */
    public static final String DASH = "-";

    /**
     * défini une valeur de seuil significatif pour une variation de la tendance de constante à un peux mieux Tant que
     * la variation de la note est en dessous de ce seuil, on considère qu'il n'y a pas eu d'évolution
     */
    private final static float BETTER = 0.05f;

    /**
     * défini une valeur de seuil significatif pour une variation de la tendance de constante à beaucoup mieux
     */
    private final static float MUCH_BETTER = 0.3f;

    /**
     * @param currentMark la note courante
     * @param predecessorMark la note précédente
     * @return l'image représentant l'évolution entre currentMark et predecessorMark
     */
    public static String getImageForTrend( String currentMark, String predecessorMark )
    {
        String result = "images/pictos/na.gif";
        // Dans ce cas, c'est simple: il n'y a pas eu d'évolution
        // la note précédente peut etre null ou initialisée à la chaine vide (pour le form)
        if ( isValidMark( currentMark ) && isValidMark( predecessorMark ) )
        {
            float diff =
                Float.parseFloat( currentMark.replace( ',', '.' ) )
                    - Float.parseFloat( predecessorMark.replace( ',', '.' ) );
            if ( Math.abs( diff ) < BETTER )
            {
                // l'évolution n'est pas significative, flèche constante
                result = "images/pictos/ar_blueAF.gif";
            }
            else if ( Math.abs( diff ) < MUCH_BETTER )
            {
                // changement peu significatif
                if ( diff < 0 )
                { // légère dégradation
                    result = "images/pictos/ar_blueAF_RD.gif";
                }
                else
                { // légère amélioration
                    result = "images/pictos/ar_blueAF_RU.gif";
                }
            }
            else
            {
                if ( diff < 0 )
                { // dégradation significative
                    result = "images/pictos/ar_blueAF_D.gif";
                }
                else
                { // amélioration significative
                    result = "images/pictos/ar_blueAF_U.gif";
                }
            }
        }
        return result;
    }

    /**
     * Affiche l'image.
     * 
     * @param pNote la note.
     * @param pRequest la requête
     * @return le chemin de l'image
     */
    public static String generatePictoWithTooltip( String pNote, HttpServletRequest pRequest )
    {
        // On récupère l'index en fonction de la note tronquée.
        int imgIndex = generatePicto( pNote );
        String pictureHelp = WebMessages.getString( pRequest, "project.results.mark.status_" + imgIndex );
        if ( imgIndex != IMG.length - 1 )
        {
            int imgIndexRound = Integer.parseInt( formatFloat( pNote ).substring( 0, 1 ) );
            if ( imgIndexRound > imgIndex )
            {
                pictureHelp +=
                    " (" + WebMessages.getString( pRequest, "project.results.mark.nearly.status_" + imgIndexRound )
                        + ")";
            }
        }
        // on remplace ' par \' pour le javascript
        return "<img src=\"" + IMG[imgIndex] + "\"title=\"" + pictureHelp.replaceAll( "'", "\\\\'" )
            + "\" border=\"0\" />";
    }

    /**
     * Récupère le pictogramme associé à une note
     * 
     * @param pNote la note.
     * @return le chemin de l'image
     */
    public static int generatePicto( String pNote )
    {
        // initialisation à l'image Na par défaut
        int i = IMG.length - 1;
        // Vérification simpliste de la note pour éviter des exceptions
        // lors de sa conversion
        Float currentMark;
        if ( isValidMark( pNote ) )
        {
            i = Integer.parseInt( truncFloat( pNote ).substring( 0, 1 ) );
            i = ( i > IMG.length - 1 ? IMG.length - 1 : i );
        }
        return i;
    }

    /**
     * @param aFloat la note sous forme de String à tronquer
     * @return la note sous sa forme flottante en la tronquant à un chiffre après la virgule
     */
    private static String truncFloat( String aFloat )
    {
        final int toTrunc = 10;
        String result = DASH;
        float mark = stringToFloat( aFloat );
        if ( mark != MarkBO.NOT_NOTED_VALUE )
        {
            // on tronque et garde que un chiffre après la virgule.
            mark = mark * toTrunc;
            mark = (int) mark;
            result = "" + mark / toTrunc;
        }
        return result;
    }

    /**
     * @param pNote La note de 0 à 3 ou non valide (i.e vide (?))
     * @return un boolean indiquant que la chaine est bien une note (non vide)
     */
    public static boolean isValidMark( String pNote )
    {
        return pNote != null && pNote.length() > 0 && Character.isDigit( pNote.charAt( 0 ) );
    }

    /**
     * @param aFloat le float à formatter
     * @return le float arrondi à un chiffre après la virgule ou "-" si la chaine ne peut pas etre correctement formatée
     */
    public static String formatFloat( String aFloat )
    {
        String result = DASH;
        float mark = stringToFloat( aFloat );
        if ( mark != MarkBO.NOT_NOTED_VALUE )
        {
            // on ne garde que un chiffre après la virgule.
            result = formatFloat( mark );
        }
        return result;
    }

    /**
     * @param aFloat la note sous forme de String
     * @return la note sous sa forme flottante en l'arrondissant à un chiffre après la virgule
     */
    private static float stringToFloat( String aFloat )
    {
        float result = MarkBO.NOT_NOTED_VALUE;
        if ( isValidMark( aFloat ) )
        {
            // Remplacement du "." par une ","
            result = Float.parseFloat( aFloat.replace( ',', '.' ) );
        }
        return result;
    }

    /**
     * Factorisation de code, aucun test n'est fait
     * 
     * @param pFloat le float à formater
     * @return la chaine associée au float
     */
    private static String formatFloat( float pFloat )
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 1 );
        nf.setMinimumFractionDigits( 1 );
        String result = nf.format( pFloat );
        return result;
    }

    /**
     * @param aFloat le nombre à formatter
     * @return le float formatté en String avec un chiffre après la virgule
     */
    public static String formatFloat( Float aFloat )
    {
        String result = DASH;
        if ( aFloat != null && aFloat.floatValue() != MarkBO.NOT_NOTED_VALUE )
        {
            result = formatFloat( aFloat.floatValue() );
        }
        return result;
    }

    /**
     * Donne une valeur à un attribut de l'objet.<br>
     * La classe de l'objet doit avoir le setter associé au nom de l'attribut : setMonAttribut et doit prendre une
     * String en paramètre.
     * 
     * @param pObject l'objet à remplir.
     * @param pSetterName le nom du setter de l'attribut.
     * @param pValue la nouvelle valeur de l'attribut.
     */
    public static void setValue( final Object pObject, final String pSetterName, final String pValue )
    {
        Class[] paramsType = { String.class };
        try
        {
            // Obtention de la méthode
            Method setter = pObject.getClass().getMethod( pSetterName, paramsType );
            Object[] params = { null };
            // Vérification du type de chaîne
            // TODO voir si le test sur un nombre est pertinent - redondance entre la regexp et le parseInt
            if ( null != pValue
                && ( ( pValue.matches( "-?[0-9]*" ) && Integer.parseInt( pValue ) != -1 ) || !pValue.matches( "-?[0-9]*" ) ) )
            {
                params[0] = pValue;
            }
            setter.invoke( pObject, params );
        }
        catch ( Exception e )
        {
            log.error( e, e );
        }
    }

    /**
     * Remplit le bean passé en paramètre avec les valeurs données.
     * 
     * @param pBean le bean à remplir.
     * @param pAttributes la liste ordonnée des attributs (String).
     * @param pValues la liste ordonnée des valeurs (String).
     * @param pMessages précise si les nom des attributs sont des clés à résoudre, ils sont alors suffixés de
     *            ".attribute".
     */
    public static void fullFillBean( final Object pBean, final List pAttributes, final List pValues,
                                     final boolean pMessages )
    {
        String attributeName = null;
        String setterName = null;
        for ( int i = 0; null != pBean && i < pAttributes.size(); i++ )
        {
            if ( pMessages )
            {
                attributeName = WebMessages.getString( (String) pAttributes.get( i ) + ".attribute" );
            }
            else
            {
                attributeName = (String) pAttributes.get( i );
            }
            setterName = "set" + attributeName.substring( 0, 1 ).toUpperCase() + attributeName.substring( 1 );
            setValue( pBean, setterName, (String) pValues.get( i ) );
        }
    }

    /**
     * Retourne une liste hétérogène sous la forme d'une liste de String.
     * 
     * @param pList la liste à transformer.
     * @return une liste de String.
     */
    public static List getAsStringsList( final List pList )
    {
        LinkedList result = null;
        if ( null != pList )
        {
            result = new LinkedList();
            Iterator it = pList.iterator();
            Object value = null;
            // Parcours de la collection et conversion de chaque valeur
            while ( it.hasNext() )
            {
                value = it.next();
                if ( null != value )
                {
                    // Conversion spécifique pour un nombre flottant
                    if ( value.getClass().equals( Float.class ) )
                    {
                        result.addLast( formatFloat( (Float) value ) );
                    }
                    else
                    {
                        result.addLast( value.toString() );
                    }
                }
                else
                {
                    // On conserve la valeur null initiale
                    result.addLast( null );
                }
            }
        }
        return result;
    }

    /**
     * Equivalent de la fonction sprintf du C.<br>
     * Ne fonctionne que pour les %s, puisque les paramètres sont des String.
     * 
     * @param pString la chaine à compléter.
     * @param pValue la liste des valeurs à affecter.
     * @return la chaîne complétée.
     */
    public static String sprintf( final String pString, final String[] pValue )
    {
        String result = pString;
        for ( int i = 0; i < pValue.length; i++ )
        {
            result.replaceFirst( "%s", pValue[i] );
        }
        return result;
    }

    /**
     * Retourne une chaine formattant la date.
     * 
     * @param pDate la date à formatter.
     * @param lang langue d'affichage
     * @return une chaine représantant le date.
     */
    public static String getFormattedDate( Date pDate, Locale lang )
    {
        DateFormat df = DateFormat.getDateInstance( DateFormat.LONG, lang );
        return df.format( pDate );
    }

    /**
     * Retourne la date d'aujourd'hui selon le format défini par la clé.
     * 
     * @param lang langue d'affichage
     * @param formatKey la clé du fichier de properties définissant le format de la date
     * @return une chaine représantant le date.
     */
    public static String getTodayDate( Locale lang, String formatKey )
    {
        Calendar today = Calendar.getInstance();
        return getFormattedDate( lang, today.getTime(), formatKey );
    }

    /**
     * Retourne la date formatée selon le format défini par la clé.
     * 
     * @param lang langue d'affichage
     * @param pDate la date à formater
     * @param formatKey la clé du fichier de properties définissant le format de la date
     * @return une chaine représantant le date.
     */
    public static String getFormattedDate( Locale lang, Date pDate, String formatKey )
    {
        SimpleDateFormat sdf = new SimpleDateFormat( WebMessages.getString( lang, formatKey ), lang );
        return sdf.format( pDate );
    }

    /**
     * Suffixe toutes les valeurs du tableau.
     * 
     * @param pArray le tableau à transformer.
     * @param pSuffix la suffixe à ajouter.
     * @param pKey précise si les valeurs suffixés sont des clés, auquel cas elles sont remplacées par les valeurs
     *            associées.
     * @return un tableau de la même taille avec les valeurs suffixés.
     */
    public static String[] suffixString( final String[] pArray, final String pSuffix, boolean pKey )
    {
        String[] result = new String[pArray.length];
        for ( int i = 0; i < pArray.length; i++ )
        {
            if ( pKey )
            {
                result[i] = WebMessages.getString( pArray[i] + pSuffix );
            }
            else
            {
                result[i] = pArray[i] + pSuffix;
            }
        }
        return result;
    }

    /**
     * Obtention de l'image associée à un niveau d'erreur
     * 
     * @param pErrorLevel le niveau d'erreur
     * @return image correspondante au niveau d'erreur
     */
    public static String getImageForErrorLevel( String pErrorLevel )
    {
        String result;
        if ( pErrorLevel.equals( ErrorBO.CRITICITY_FATAL ) )
        {
            result = "images/pictos/error.png";
        }
        else if ( pErrorLevel.equals( ErrorBO.CRITICITY_WARNING ) )
        {
            result = "images/pictos/warning.png";
        }
        else
        {
            result = "images/pictos/info.png";
        }
        return result;
    }

    /**
     * Obtention de l'image associée à la sévérité de la règle
     * 
     * @param pSeverity la sévérité
     * @return image correspondante à la sévérité
     */
    public static String getImageForRuleSeverity( String pSeverity )
    {
        String result;
        if ( pSeverity.equals( ConstantRulesChecking.ERROR_LABEL ) )
        {
            result = "images/pictos/error.png";
        }
        else if ( pSeverity.equals( ConstantRulesChecking.WARNING_LABEL ) )
        {
            result = "images/pictos/warning.png";
        }
        else
        {
            result = "images/pictos/info.png";
        }
        return result;
    }

    /**
     * Retourne le contenu de pValues sans les valeurs vides.
     * 
     * @param pValues la tableau des valeurs à nettoyer
     * @return un tableau de String propre
     */
    public static String[] cleanValues( final String[] pValues )
    {
        String[] result = null;
        // Nettoyage des noms
        ArrayList temp = new ArrayList();
        for ( int i = 0; i < pValues.length; i++ )
        {
            if ( pValues[i].trim().length() > 0 )
            {
                temp.add( pValues[i] );
            }
        }
        String[] type = new String[0];
        result = (String[]) temp.toArray( type );
        return result;
    }

    /**
     * @param pRequest la requete http
     * @return le formatter de nombre correspondant à la locale
     */
    public static NumberFormat getNumberFormat( HttpServletRequest pRequest )
    {
        NumberFormat formatter = null;
        try
        {
            // En anglais par défaut
            formatter = (DecimalFormat) DecimalFormat.getInstance();
            // Si c'est en francais , on n'affiche pas les "," pour séparer les chiffres
            if ( pRequest.getLocale().getLanguage().equals( Locale.FRENCH.toString() ) )
            {
                ( (DecimalFormat) formatter ).setGroupingUsed( false );
            }
        }
        catch ( NumberFormatException nfe )
        {
            // en cas de problème rencontré, on renvoie le formatter par défaut
            formatter = NumberFormat.getInstance();
        }
        return formatter;
    }

    /**
     * @param pStrings la liste de String
     * @param pSeparator le séparateur
     * @return la string représentant le tableau dont les élement sont séparés pas <code>separator</code>
     */
    public static String formatArray( Collection pStrings, String pSeparator )
    {
        String result = "";
        for ( Iterator it = pStrings.iterator(); it.hasNext(); )
        {
            result += (String) it.next();
            if ( it.hasNext() )
            {
                result += pSeparator;
            }
        }
        return result;
    }

    /**
     * Permet d'aller à la ligne dans un menu pour pouvoir afficher le nom en entier au lieu qu'il soit tronqué car la
     * charte graphique d'Air France impose une taille fixe
     * 
     * @param pItem l'item a couper
     * @return l'item avec des '\n' tous les n caractères
     */
    public static String formatStringForMenuItem( String pItem )
    {
        final int MAX_CHAR = 26; // Nombre de caractères qu'on suppose affichable
        StringBuffer result = new StringBuffer( pItem );
        int nbInsertion = result.length() / MAX_CHAR;
        for ( int i = 1; i <= nbInsertion; i++ )
        {
            result.insert( i * MAX_CHAR, "... " ); // On décale de 3 caractères
        }
        return result.toString();
    }

    /**
     * Récupére la clé de configuration représentant le nombre minimum d'applications que doit avoir le menu pour être
     * groupé
     * 
     * @param request la requête
     * @return le nombre minimum d'applications que doit avoir le menu pour être groupé (10 par défaut)
     */
    public static int getApplicationMenuKey( HttpServletRequest request )
    {
        // On groupe les applications si on en a plus de 10 dans le menu
        final int MIN_APPLIS = 10;
        // On récupère le nombre minimum d'applications que doit avoir le menu pour être groupé
        String minApplisForGrouping = WebMessages.getString( request, "nbApplisForGrouping" );
        int minApplis;
        try
        {
            minApplis = Integer.parseInt( minApplisForGrouping );
        }
        catch ( NumberFormatException nfe )
        {
            // Si erreur de clé, on met le nombre par défaut
            minApplis = MIN_APPLIS;
        }
        return minApplis;
    }

    /**
     * @param pRequest la requête
     * @param pTres la liste des tres
     * @return la légende précisant la signification des tres
     */
    public static String getLegendForTres( HttpServletRequest pRequest, List pTres )
    {
        String legend = "<ul>";
        for ( int i = 0; i < pTres.size(); i++ )
        {
            String tre = (String) pTres.get( i );
            String acronyme = tre;
            int lastDot = tre.lastIndexOf( "." );
            if ( lastDot > 0 )
            {
                acronyme = tre.substring( lastDot + 1 );
            }
            legend += "<li>" + acronyme + " = " + WebMessages.getString( pRequest, tre ) + "</li>";
        }
        return legend + "</ul>";
    }

    /**
     * @param pStatus le statut de l'audit
     * @return le type servant à la clé représentant le statut de l'audit.
     */
    public static String getAuditKind( int pStatus )
    {
        // TODO : Il faudrait exploiter le statut de l'audit
        // --> faire une passe sur la clé "kind" pour la remplacer par
        // un int et récupérer la string par les fichiers de properties
        String result = "terminated";
        if ( pStatus == AuditBO.FAILED )
        {
            result = "failed";
        }
        else if ( pStatus == AuditBO.PARTIAL )
        {
            result = "partial";
        }
        return result;
    }
}
