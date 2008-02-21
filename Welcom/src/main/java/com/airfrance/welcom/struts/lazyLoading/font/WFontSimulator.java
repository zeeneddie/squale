/*
 * Créé le 24 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.lazyLoading.font;

import java.io.IOException;
import java.io.InputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Properties;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837
 * 
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WFontSimulator {

	/** Font Verdana */
	public static final String FONT_VERDANA = "verdana";

	/** Liste des font utlisable et intanciés */
	private static HashMap fontSimulators = new HashMap();

	/** Fichier de configuration de parametres de la font */
	private final Properties properties = new Properties();

	/** Clef pour valeur par default dans le fichie de font */
	private static final String DEFAULT_SIZE_KEY = "default";

	/**
	 * Creer un nouvelle onft pour simuler sa taille
	 * 
	 * @param font :
	 *            Nom de la font
	 * @throws IOException :
	 *             Erreur lors du chargement du fichier properties
	 */
	private WFontSimulator(final String font) throws IOException {
		loadFont(font);
	}

	/**
	 * Chargelment du fichier descriptif de font
	 * 
	 * @param font :
	 *            Nom de la font
	 * @throws IOException :
	 *             Probleme sur l'ouverture du fichier
	 */
	private void loadFont(final String font) throws IOException {
		final String fileName = "/com/airfrance/welcom/struts/lazyLoading/font/"
				+ font + ".properties";
		final InputStream is = getClass().getResourceAsStream(fileName);
		if (is == null) {
			throw new IOException("Impossible de charger le fichier : "
					+ fileName);
		}
		properties.load(is);
	}

	/**
	 * Calcule la taille de la chaine en tenant compte des chaines découpées
	 * sur plusieurs lignes avec les chr : \n et \012.
	 * Dans ce cas seul le resultat est la taille de la sous-chaine la plus longue. 
	 * @param s
	 *            Chaine a calculer
	 * @return retourne la taille
	 */
	public int computeSize(final String s) {
		int size = 0;

		// Chaine avec retour à la ligne 
		// On garde la taille du segment le + grand
		if (s != null){		
			String[] sousChaines = s.split("\n\012");
			int tailleSegment = 0;
			for (int i = 0; i < sousChaines.length; i++) {
				tailleSegment = computeSegmentSize(sousChaines[i]);
				if (tailleSegment > size){
					size = tailleSegment;
				}
			}
		}
		return size;
	}

	/**
	 * Calcule la taille de la chaine. 
	 * @param s
	 *            Chaine a calculer
	 * @return retourne la taille
	 */
	private int computeSegmentSize(final String s) {
		int size = 0;
		final StringCharacterIterator iter = new StringCharacterIterator(s);
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter
				.next()) {
			size += computeSizeChar(c);
		}
		return size;
	}

	/**
	 * Retourne la taille d'un caractere
	 * 
	 * @param c :
	 *            Charactere a retrouver la taille
	 * @return : Taille du char
	 */
	public int computeSizeChar(final char c) {
		String unicode = Integer.toHexString(c).toUpperCase();
		if (unicode.length() == 2) {
			unicode = "00" + unicode;
		}

		String sizeFound = "";
		if (properties.containsKey(unicode)) {
			sizeFound = properties.getProperty(unicode);
		} else {
			sizeFound = properties.getProperty(DEFAULT_SIZE_KEY);
		}

		int size;
		if ((sizeFound != null) && (sizeFound.length() != 0)) {
			size = Integer.parseInt(sizeFound);
		} else {
			size = 0;
		}

		return size;
	}

	/**
	 * Retourne la taille estimé en fonction de la font
	 * 
	 * @param font :
	 *            Nom de la font
	 * @param s
	 *            Chaine a calculer sa longeur
	 * @return : Longuer de la chaine avec cette police
	 */

	public static int getSize(final String font, final String s) {
		try {
			if (!fontSimulators.containsKey(font)) {
				fontSimulators.put(font, new WFontSimulator(font));
			}
			return ((WFontSimulator) (fontSimulators.get(font))).computeSize(s);
		} catch (final IOException e) {
			return 0;
		}
	}

	/**
	 * Retourne la taille estimé en fonction de la font
	 * 
	 * @param s
	 *            Chaine a calculer sa longeur
	 * @return : Longuer de la chaine avec cette police
	 */

	public static int getSize(final String s) {
		return getSize(WelcomConfigurator
				.getMessageWithCfgChartePrefix(".combo.default.font"), s);
	}

}
