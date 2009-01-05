/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Créé le 8 janv. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.provider.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.persistence.IMetaData;

/**
 *  Classe d'accès au modèle de mapping. 
 * <br>Elle est instanciée
 *  par la classe SessionManagerImpl lors de la création du mapping.
 * @author G.Courtial
 */
public class MetaDataImpl
	implements IMetaData, Serializable, IInitializableBean {

	/** session factory hibernate */
	private SessionFactory sessions = null;
	
	/** Configuration hibernate */
	Configuration configuration = null;

	/** 
	 * Constructeur vide type IOC2
	 */
	public MetaDataImpl() {
		super();
	}

	/**
	 * Constructeur avec parametre type IOC3
	 * @param sessions session factory hibernate
	 */
	public MetaDataImpl(SessionFactory sessions, Configuration cfg) {
		setSessions(sessions);
		setConfiguration(cfg);
		afterPropertiesSet();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.daolayer.itface.IMetaData#getIdentifierName(java.lang.Class)
	 */
	public String getIdentifierName(Class clazz) throws JrafDaoException {
		String id = null;
		try {
			id = sessions.getClassMetadata(clazz).getIdentifierPropertyName();
		} catch (Exception e) {
			throw new JrafDaoException(
				"Erreur MetaDataImpl méthode : getIdentifierName("
					+ clazz
					+ ")");
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (getSessions() == null) {
			throw new JrafConfigException("La classe n'est pas initialisee correctement: la session factory Hibernate est null");
		}
	}

	/**
	 * Retourne la session factory
	 * @return session factory
	 */
	public SessionFactory getSessions() {
		return sessions;
	}

	/**
	 * Renseigne la session factory
	 * @param factory session factory
	 */
	public void setSessions(SessionFactory factory) {
		sessions = factory;
	}

	/**
	 * @return
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 */
	public void setConfiguration(Configuration config) {
		this.configuration = config;
	}

	/**
	 * Retourne la longueur de la colonne ou sera stocke columnName de la classe clazz
	 * @param clazz Classe voulue
	 * @param columnName nom de la  colonne
	 * @return la longueur de la colonne si elle existe
	 * @throws JrafConfigException si la colonne est pas touvé ou si le mode introspection n'est pas actif
	 */
	public int getColumnMaxSize(Class clazz, String columnName) throws JrafConfigException {
		if(getConfiguration()==null) {
			throw new JrafConfigException("L'introspection n'est pas active");
		}
		//Cette méthode ne prend plus en paramètre un type Class plutôt un String.
		PersistentClass persistentClass = configuration.getClassMapping(clazz.getName());
		Table table = persistentClass.getTable();
		Column column = getColumn(table, columnName);

		if(column == null) throw new JrafConfigException("Column non trouve : " + columnName);
		
		return column.getLength();
	}
	
	/**
	 * Retourne la colonne columnName de la table table
	 * @param table Table sur laquelle il faut chercher
	 * @param columnName Nm de la colonne a chercher
	 * @return La colonne ColumnName de la table table
	 */
	public Column getColumn(Table table, String columnName) {
		Column column = null;
		Iterator iter = table.getColumnIterator();
		while (iter.hasNext()) {
			column = (Column) iter.next();
			if (column.getName().equals(columnName)) {
				return column;

			}
		}
		return null;
	}
}
