/*
 * Created on Jan 6, 2005
 */
package com.airfrance.jraf.spi.initializer;

/**
 * <p>Title : IInitializableBean.java</p>
 * <p>Description : Interface utilise pour l'initialisation d'un bean.
 * Lorsque les proprietes du bean ont ete renseignes, la methode afterPropertiesSet() est appelee.
 * Cette methode verifie la bonne initialisation du bean.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IInitializableBean {
	
	/**
	 * Methode appelee apres que les proprietes d'un bean soient renseignees. 
	 * Elle permet la finalisation de l'initialisation.
	 * Cette methode ne lance pas d'exception autre que de type Runtime (non gerees).
	 */
	public void afterPropertiesSet();
}
