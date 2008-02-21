/*
 * Créé le 13 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TrimStringBuffer {
    /** logger */
    private static Log log = LogFactory.getLog(TrimStringBuffer.class);

    /** Par default on effectue le trim */
    private static boolean trim = true;
    /** Surchargé ? */
    private static boolean override = false;
    
    /** Buffer */
    private StringBuffer sb;
    
    /** Optimize */
    private boolean optimize = TrimStringBuffer.trim;

    /**
     * Constructeur
     */
    public TrimStringBuffer() {
        this(getDefaultOptimization());
    }

    /**
     * Constructeur 
     * @param pOptimize true si on est en optimise
     */
    public TrimStringBuffer(final boolean pOptimize) {
        this(new StringBuffer(), pOptimize);
    }

    /**
     * Constructeur
     * @param pSb StringBuffer
     * @param pOptimize : optimise
     */
    public TrimStringBuffer(final StringBuffer pSb, final boolean pOptimize) {
        this.sb = pSb;
        this.optimize = pOptimize;
    }

    /**
     * 
     * @return L'optimization dans les Welcomresources
     */
    public static boolean getDefaultOptimization() {
        if (!override) {
            final String s = WelcomConfigurator.getMessage("optiflux.cleanspaces");
            return Util.isTrue(s);
        }

        return TrimStringBuffer.trim;
    }

    /**
     * Trim la chaine
     * @param b : boolean 
     */
    public static void setTrim(final boolean b) {
        override = true;
        trim = b;
    }

    /**
     * Ajoute un valeur
     * @param value : Valeur
     */
    public void append(final int value) {
        sb.append(value);
    }

    /**
     * Ajoute un valeur
     * @param value : Valeur
     */
    public void append(final boolean value) {
        sb.append(value);
    }

    /**
     * Ajoute un valeur
     * @param value : Valeur
     */
    public void append(final float value) {
        sb.append(value);
    }

    /**
     * Ajoute un valeur
     * @param value : Valeur
     */
    public void append(final String value) {
        sb.append(value);
    }

    /**
     * Ajoute un valeur
     * @param value : Valeur
     */
    public void append(final Object value) {
        sb.append(value);
    }

    /**
     * @return si on aptimize ou pas
     */
    public boolean optimize() {
        return optimize;
    }

    /** 
     * Renvoi le string buffer optmié si necessaire
     *  @return le string buffer optmié si necessaire
     */
    public String toString() {
        if (optimize) {
            final String value = sb.toString();

            try {
                final Pattern reg = Pattern.compile("<\\s*(textarea|pre)[^>]*>([^<]*)<\\/\\s*\\1\\s*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                final StringBuffer sbtmp = new StringBuffer();

                final Matcher match = reg.matcher(value);

                int start = 0;
                int end = 0;

                while (match.find()) {
                    final String chaineANePasRempacer = match.group();
                    end = match.start();

                    final String chaineARemplacer = value.substring(start, end);
                    final String chainePurge = removeSpaces(chaineARemplacer);
                    sbtmp.append(chainePurge);
                    sbtmp.append(chaineANePasRempacer);
                    start = match.end();
                }

                sbtmp.append(removeSpaces(value.substring(start, value.length())));

                return sbtmp.toString();
            } catch (final Exception ex) {
                log.error(ex, ex);

                return sb.toString();
            }
        } else {
            return sb.toString();
        }
    }

    /**
     * Suppressiondes espaces
     * @param value : valeurs d'origine
     * @return chaine sans espaces
     */
    private String removeSpaces(String value) {
		try {
			value = value.replaceAll("[\n\r\t\f]", "");

			//value = regexp.subst(value,"",RE.REPLACE_ALL);
			//Suppression des espaces entre les balises
			//regexp = new RE("\\s*>\\s*");
			//value = regexp.subst(value,">",RE.REPLACE_ALL);
			value = value.replaceAll("\\s*>\\s*", ">");

			//regexp = new RE("\\s*<\\s*");
			//value = regexp.subst(value,"<",RE.REPLACE_ALL);
			value = value.replaceAll("\\s*<\\s*", "<");


			// Suppression des espaces multiples
			//regexp = new RE("\\s+\\s");
			//value = regexp.subst(value," ",RE.REPLACE_ALL);
			value = value.replaceAll("\\s+\\s", " ");
		} catch (final Exception ex) {
			log.error(ex, ex);
		}

		return value;
    }

    /**
     * Retourne la chaine su stringbuffer
     * @param optimize : si on optimize
     * @param s : chaine a optimiser
     * @return : chaine finale
     */
    public static String toString(final boolean optimize, final String s) {
        final TrimStringBuffer sb = new TrimStringBuffer(optimize);
        sb.append(s);

        return sb.toString();
    }

    /**
     * Retourne la chaine su stringbuffer
     * @param s : chaine a optimiser
     * @return chaine optimisée
     */
    public static String toString(final String s) {
        return TrimStringBuffer.toString(TrimStringBuffer.trim, s);
    }
}