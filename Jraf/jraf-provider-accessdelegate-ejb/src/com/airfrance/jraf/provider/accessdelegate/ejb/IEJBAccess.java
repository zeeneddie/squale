/*
 * Created on Mar 4, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.ejb;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;

/**
 * <p>Title : IEJBAccess.java</p>
 * <p>Description : Comopsant d'acces a un EJB</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public interface IEJBAccess  {

	/**
	 * Fixe le nom JNDI de l'EJB accedee
	 * @param jndiName nom jndi
	 */
	public void setJndiName(String jndiName);
	
	/**
	 * Retourne le nom jndi de l'EJB accede
	 * @return nom jndi de l'EJB accede
	 */
	public String getJndiName();
	
	/**
	 * Rend actif un ejb application component
	 * @throws JrafEnterpriseException
	 */
	public void activate() throws JrafEnterpriseException;
}
