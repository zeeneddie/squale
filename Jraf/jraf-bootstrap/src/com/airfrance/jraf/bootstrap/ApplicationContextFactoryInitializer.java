/*
 * Créé le 16 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.bootstrap;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.airfrance.jraf.commons.exception.JrafIllegalStateException;

/**
 * @author DIKITE Tidiani
 * Cette classe a pour rôle d'initialiser le context spring.
 * L'initialisation du context n'est effectuée qu'une fois, sauf 
 * dans le cadre de l'exécution de tests unitaires JUNIT.
 * Voir les commentaires sur des méthodes pour plus d'informations.
 * */
public class ApplicationContextFactoryInitializer {

	/** logger */
	private static final Log log =
		LogFactory.getLog(ApplicationContextFactoryInitializer.class);
		
	/**Objet à initialiser.*/
	private static Object initObject = null;
	
	/**La limite d'init. Une seule initialisation est permise dans un contexte 
	 * autre que cleui des tests unitaires. */
	private static int limite = 0;
	
	/**
	 * Initialise l'application contexte.
	 * @param object - Object
	 */
	public static void init(Object object) {
		// Initialisation du contexte dans le cadre de tests unitaires.
		// Dans ce cas de figure, le contexte peut être initialisé
		// plusieurs fois.
		if(object.equals("applicationTest.xml")) {
			log.debug("Initialisation du context spring dans un cas de tests unitaires ...");
			initObject = object;
		}
		else 
		{// Cas de figure où l'application est exécutée dans un contexte autre 
			// que celui des tests unitaires.
			if (limite > 0) {
			throw new JrafIllegalStateException("Impossible d'initialiser l'Application context une seconde fois. ELle est déjà initialisée !");
			}
			initObject = object;
			limite++;
		}
	}

	/**
	 * Retourne l'application context selon le type d'environnement
	 * d'exécution.
	 * @return ApplicationCOntext.
	 */
	public static ApplicationContext getApplicationContext() {
		if (initObject == null) {
			throw new JrafIllegalStateException("Application context n'est pas initialisée ...");
		// Test si l'instance de l'objet passé en paramètre est 
		// un ServletContext.
		} else if (initObject instanceof ServletContext) {
			log.debug("Début d'initialisation du fichier de configuration spring en mode servlet.");
			ServletContext servletContext = (ServletContext) initObject;
			return WebApplicationContextUtils.getRequiredWebApplicationContext(
				servletContext);
		// Test si l'instance de l'objet est une chaine de caractère.
		} else if (initObject instanceof String) {
			log.debug("Initialisation du fichier de configuration spring : " + initObject);
			String contextResourceLocation = (String) initObject;
			ApplicationContext applicationContext = null;
			try {
				applicationContext = new ClassPathXmlApplicationContext(contextResourceLocation);
			} catch (BeansException e) {
				e.printStackTrace();
				throw e;
			}
			
			return applicationContext;
		} else {
			// Lancement de l'exception.
			throw new JrafIllegalStateException("Le contexte doit être initialisé avec un String ou un ServletContext .");
		}
	}
}
