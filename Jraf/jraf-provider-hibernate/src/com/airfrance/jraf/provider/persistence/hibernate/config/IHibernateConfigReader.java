/*
 * Created on Dec 29, 2004
 */
package com.airfrance.jraf.provider.persistence.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : IHibernateConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IHibernateConfigReader {
	
	
	public SessionFactory readConfig(Configuration configuration) throws JrafConfigException;

}
