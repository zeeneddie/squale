/*
 * Créé le 9 janv. 2007
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.bootstrap.locator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author m312645
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class SpringLocator implements ApplicationContextAware {

	static private SpringLocator instance = null;
	/** Spring context */
	private ApplicationContext applicationContext = null;

	/**
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param context
	 */
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public SpringLocator() {
		instance = this;
	}

	static public SpringLocator getInstance() {
		return instance;
	}
	
	public Object getBean(String id) {
		if(getApplicationContext() == null) return null;
		return getApplicationContext().getBean(id);
	}
}
