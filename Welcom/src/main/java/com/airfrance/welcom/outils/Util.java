package com.airfrance.welcom.outils;

import java.io.UnsupportedEncodingException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

/**
 * Insérez la description du type ici.
 * Date de création : (30/11/2001 11:22:34)
 * @author: Fabienne Madaule
 */
public class Util {
    
	/** logger */
    private static Log log = LogFactory.getLog(Util.class);

    
    public final static String SERVEPATH = "tableForward.do";
    public static java.text.SimpleDateFormat formatDtHr;
    public static java.text.SimpleDateFormat formatDt;
    public static java.text.SimpleDateFormat formatHr;
    public final static java.lang.String sqlFormatDtHr24 = "DD/MM/YYYY HH24:MI";
    public final static java.lang.String sqlFormatDt = "DD/MM/YYYY";
    public final static java.lang.String sqlFormatHr24 = "HH24:MI";
    public final static java.lang.String adhesionFormatDtHr = "yyyyMMddhhmm";
    public final static java.lang.String adhesionFormatDt = "yyyyMMdd";
    public final static java.lang.String S04493FormatDtHr = "ddMMyyyyHHmm";
    public final static java.lang.String stringFormatDtHr = "dd/MM/yyyy HH:mm";
    public final static java.lang.String stringFormatDt = "dd/MM/yyyy";
    public final static java.lang.String stringFormatHr = "HH:mm";
    public final static java.lang.String PROFILS_KEY = "profils";
    
    
    /**
    * Insérez la description de la méthode ici.
    *  Date de création : (24/06/2002 15:01:50)
    * @param : Chaine a encoder (pour URL)
    * @return java.lang.String
    */
    public static final String encode(final String param) {
        // teste si la chanine a encoder n'est pas null
        if (param != null) {
            try {
                // Recupere l'encodage dans le welcomresources
                // sous la clef encoding.charset
                return java.net.URLEncoder.encode(param, WelcomConfigurator.getMessage(WelcomConfigurator.ENCODING_CHARSET));
            } catch (final UnsupportedEncodingException e) {
                try {
                    // encode en utilisant l'encodage par defaut de la plateforme
                    return java.net.URLEncoder.encode(param, null);
                } catch (final UnsupportedEncodingException e1) {
                    // Ne supporte pas l'encodage demandé
                    log.error(e1,e1);
                }
            }
        }
        // si la chaine a encodé est nulle alors on retourn null
        return null;
    }

    /**
     * Un entier est deja encodé, mais on creer la fonction pour quelle existe (comptilité)
     * @param i : entier a encode (pour URL)
     * @return : entier encodé
     */
    public static final String encode(final int i) {
        return Integer.toString(i);
    }

    /**
     * Un long est deja encodé, mais on creer la fonction pour quelle existe (comptilité)
     * @param i : long a encode (pour URL)
     * @return : long encodé
     */
    public static final String encode(final long i) {
        return Long.toString(i);
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (16/10/2002 10:00:00)
     * @param st : String a formaté sans accents
     * @return st : chaine sans accents
     */
    public static final String formatAccent(final String st) {
        if (st == null) {
            return "";
        }

        // Remplacer les caracteres accentues par leur caractere de base
        return st.replaceAll("[âäàáåæÄÂÃÀÁÆ]","a").
               replaceAll("[éêëèÊËÈÉ]","e").
               replaceAll("[ïîÏÎÌÍ]","i").
               replaceAll("[ôöòóÔÖÒÓÕ]","o").
               replaceAll("[üùúûÜÛÙÚ]","u").
               replaceAll("[çÇ]","c").
               replaceAll("[ñÑ]","n").
               replaceAll("[ÿýÝ]","y");
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (16/10/2002 10:00:00)
     */
    public static final String formatDecimal(final String st) {
        if (st == null) {
            return "";
        }

        String newString = "";
        String tempo = "";

        // Remplacer le . Adhesion par une ,
        final java.util.StringTokenizer token = new java.util.StringTokenizer(st, ".", true);

        while (token.hasMoreTokens()) {
            tempo = token.nextToken();

            if (tempo.equals(".") == true) {
                newString = newString + ",";
            } else {
                newString = newString + tempo;
            }
        }

        return newString;
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (12/12/2001 11:18:30)
     * @param st java.lang.String
     */
    public final static String formatEtoile(final String st) {
        if (st == null) {
            return "";
        }

        return st.replace('*', '%');
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (12/12/2001 11:18:30)
     * @param st java.lang.String
     */
    public final static String formatQuote(final String st) {
        if (st == null) {
            return "";
        }

        String newString = "";
        String tempo = "";

        // Doubler la ' pour les requetes SQL
        final java.util.StringTokenizer token = new java.util.StringTokenizer(st, "'", true);

        while (token.hasMoreTokens()) {
            tempo = token.nextToken();

            if (tempo.equals("'") == true) {
                newString = newString + "''";
            } else {
                newString = newString + tempo;
            }
        }

        return newString;
    }

    public final static String formatFile(String st) {
        // Transforme les lettres accentuées
        st = Util.formatAccent(st);

        final StringCharacterIterator iter = new StringCharacterIterator(st);
        final StringBuffer sb = new StringBuffer();

        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (!(((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || (((c >= 'A') && (c <= 'Z')) || (c == '.')))) {
                sb.append('_');
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Commentaire relatif au constructeur Util.
     */
    public static void initialize() {

        formatDtHr = new java.text.SimpleDateFormat(stringFormatDtHr, java.util.Locale.getDefault());
        formatDtHr.setTimeZone(java.util.TimeZone.getDefault());

        formatDt = new java.text.SimpleDateFormat(stringFormatDt, java.util.Locale.getDefault());
        formatDt.setTimeZone(java.util.TimeZone.getDefault());

        formatHr = new java.text.SimpleDateFormat(stringFormatHr);
        formatHr.setTimeZone(java.util.TimeZone.getDefault());
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (05/06/2002 11:39:06)
     * @return boolean
     * @param param java.lang.String
     */
    public final static boolean isNumber(final String param) {
        if (param == null) {
            return false;
        }

        if (param.length() == 0) {
            return true;
        }

        try {
            Integer.parseInt(param);
        } catch (final NumberFormatException ex) {
            return false;
        }

        return true;
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (21/02/2003 15:38:46)
     * @return java.lang.String
     * @param month java.lang.String
     */
    public static String monthAdhToNumber(final String month) {
        if (month.equals("JAN")) {
            return "01";
        }

        if (month.equals("FEB")) {
            return "02";
        }

        if (month.equals("MAR")) {
            return "03";
        }

        if (month.equals("APR")) {
            return "04";
        }

        if (month.equals("MAY")) {
            return "05";
        }

        if (month.equals("JUN")) {
            return "06";
        }

        if (month.equals("JUL")) {
            return "07";
        }

        if (month.equals("AUG")) {
            return "08";
        }

        if (month.equals("SEP")) {
            return "09";
        }

        if (month.equals("OCT")) {
            return "10";
        }

        if (month.equals("NOV")) {
            return "11";
        }

        if (month.equals("DEC")) {
            return "12";
        }

        return null;
    }

    public static String formatJavaScript(String st) {
        if (st == null) {
            return "";
        }

        st = st.replaceAll("\r\n", "\\n");
        final StringBuffer sb = new StringBuffer();
        final StringCharacterIterator iter = new StringCharacterIterator(st);

        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            switch (c) {
                case '\t' :
                    sb.append("\\t");

                    break;

                case '\n' :
                    sb.append("\\n");

                    break;

                case '\'' :
                    sb.append("\\&#39;");

                    break;

                case '\"' :
                    sb.append("\\&quot;");

                    break;

                case '&' :
                    sb.append("&amp;");

                    break;

                case '>' :
                    sb.append("&gt;");

                    break;

                case '<' :
                    sb.append("&lt;");

                    break;

                default :
                    sb.append(c);

                    break;
            }
        }

        return sb.toString();
    }

    // Suppresion des retour chariot de la chaine
    public static String formatEmailList(final String st) {
        final StringBuffer sb = new StringBuffer();
        final StringCharacterIterator iter = new StringCharacterIterator(st);

        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c != ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static boolean isNonVide(final String texte) {
        return ((texte != null) && (texte.length() > 0));
    }

    public static boolean isTrimNonVide(final String texte) {
        return ((texte != null) && isNonVide(texte.trim()));
    }

    public static boolean isVide(final String texte) {
        return !isNonVide(texte);
    }

    public static boolean isNonVideEtTous(final String texte) {
        return ((texte != null) && texte.toLowerCase().equals("tous"));
    }

    public static boolean isNonVideEtNonTous(final String texte) {
        return (isNonVide(texte) && !texte.toLowerCase().equals("tous"));
    }

    public static boolean isFalse(final String texte) {
        return ((texte != null) && texte.toLowerCase().equals("false"));
    }

    public static boolean isTrue(final String texte) {
        return ((texte != null) && texte.toLowerCase().equals("true"));
    }

    public static boolean isEquals(final String s1, final String s2) {
        return ((s1 == null) && (s2 == null)) || ((s1 != null) && (s2 != null) && s1.equals(s2));
    }

    public static boolean isEqualsIgnoreCase(final String s1, final String s2) {
        return ((s1 == null) && (s2 == null)) || ((s1 != null) && (s2 != null) && s1.equalsIgnoreCase(s2));
    }

    public static boolean isNonEquals(final String s1, final String s2) {
        return !isEquals(s1, s2);
    }

    public static boolean isNonEqualsIgnoreCase(final String s1, final String s2) {
        return !isEqualsIgnoreCase(s1, s2);
    }

    public static boolean isDecalageHoraire(final String texte) {
        // On verifie que le string est de la forme "+hh:mm" ou "-hh:mm"
        if (isVide(texte)) {
            return false;
        }

        int indexOfSigne = texte.indexOf("+");

        if (indexOfSigne == -1) {
            indexOfSigne = texte.indexOf("-");
        }

        if (indexOfSigne == -1) {
            return false;
        }

        final int indexDeuxPoints = texte.indexOf(":");

        if (indexDeuxPoints == -1) {
            return false;
        }

        if (!isNumber(texte.substring(indexOfSigne + 1, indexDeuxPoints))) {
            return false;
        }

        if (!isNumber(texte.substring(indexDeuxPoints + 1, texte.length()))) {
            return false;
        }

        return true;
    }

    public static Date getLastMonday() {
        java.util.Date jour = new java.util.Date();
        final Calendar cal = new GregorianCalendar();
        cal.setTime(jour);

        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            jour = new java.util.Date(jour.getTime() - (24 * 60 * 60));
            cal.setTime(jour);
        }

        return jour;
    }

    public static Date getNextMonday() {
        java.util.Date jour = new java.util.Date();
        final Calendar cal = new GregorianCalendar();
        cal.setTime(new java.util.Date(jour.getTime() + (24 * 60 * 60)));

        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            jour = new java.util.Date(jour.getTime() + (24 * 60 * 60));
            cal.setTime(jour);
        }

        return jour;
    }

    //fonction des substitustion de caractères dans les chaines de caractères

    /*public static String replace(String string,String regexp,String subString,int flags) throws RESyntaxException
    {
      return new RE(regexp).subst(string,subString,flags);
    }*/
    public static String replace(final String string, final String regexp, final String subString) {
        return string.replaceAll(regexp, subString);

        //return new RE(regexp).subst(string,subString,RE.REPLACE_ALL);
    }

    public static boolean isEmail(final String email) {
        final Pattern pattern = Pattern.compile("^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
        final Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    public static String smartTruncate(final String ch, final int t) {
        if (ch != null) {
            if (ch.length() > t) {
                return ch.substring(0, t - 3) + "...";
            }

            return ch;
        }

        return "";
    }

    /**
     * Suppressions des apostrophes et des guillemets dans une chaine
     * @param text
     * @return
     */
    public static String removeQuotes(final String text) {
        return replace(text, "[\"']", "");
    }
}