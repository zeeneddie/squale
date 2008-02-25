/*
 * Cree le 24 janv. 05
 */
package com.airfrance.jraf.bootstrap.naming;

import javax.naming.Context;

import com.airfrance.jraf.spi.initializer.IInitializableBean;

/**
 * <p>Project: JRAF 
 * <p>Title : JndiBinder</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE </p>
 */
public class JndiBinder implements IInitializableBean, IJndiBinder {

	/** classe jndi */
	private String jndiClass;

	/** url de l'annuaire jndi */
	private String jndiUrl;

	/** contexte jndi */
	private Context context;

	/**
	 * Constructeur vide type IOC2
	 */
	public JndiBinder() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param jndiClass classe d'acces a l'annuaire jndi
	 * @param jndiUrl url du serveur jndi
	 */
	public JndiBinder(String jndiClass, String jndiUrl) {
		super();
		setJndiClass(jndiClass);
		setJndiUrl(jndiUrl);
		afterPropertiesSet();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param context contexte jndi
	 */
	public JndiBinder(Context context) {
		super();
		setContext(context);
		afterPropertiesSet();

	}

	/**
	 * Retourne la classe d'acces a l'annuaire jndi
	 * @return classe d'acces a l'annuaire jndi
	 */
	public String getJndiClass() {
		return jndiClass;
	}

	/**
	 * Retourne l'adresse de l'annuaire jndi
	 * @return adresse de l'annuaire jndi
	 */
	public String getJndiUrl() {
		return jndiUrl;
	}

	/**
	 * Fixe la classe d'accces a l'annuaire jndi
	 * @param string classe d'acces a l'annuaire jndi
	 */
	public void setJndiClass(String string) {
		jndiClass = string;
	}

	/**
	 * Fixe l'url de l'annuaire jndi
	 * @param string fixe l'url de l'annuaire JNDI
	 */
	public void setJndiUrl(String string) {
		jndiUrl = string;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		// cas ou le contexte n'est pas renseigne	
		if (context == null) {

			setContext(
				NamingHelper.getInitialContext(getJndiUrl(), getJndiClass()));

		}

	}

	/**
	 * Bind d'un objet dans JNDI
	 * @param key cle de l'objet
	 * @param o objet
	 */
	public void bind(String key, Object o) {
		NamingHelper.bind(getContext(), key, o);
	}

	/**
	 * Recherche d'un objet dans JNDI
	 * @param key cle
	 */
	public Object lookup(String key) {
		return NamingHelper.lookup(getContext(), key);
	}

	/**
	 * Retourne le context jndi
	 * @return contexte jndi
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Fixe le contexte jndi
	 * @param context context jndi
	 */
	public void setContext(Context context) {
		this.context = context;
	}

}