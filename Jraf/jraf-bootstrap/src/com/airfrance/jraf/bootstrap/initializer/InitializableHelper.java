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
 * Created on Mar 5, 2004
 */
package com.airfrance.jraf.bootstrap.initializer;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.initializer.IInitializable;

/**
 * <p>Title : InitializableHelper.java</p>
 * <p>Description : Helper pour le bootstrap</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class InitializableHelper {

	/** logger */
	private final static Log log = LogFactory.getLog(InitializableHelper.class);

	/**
	 * Instancie une classe d'initialisatoin
	 * @param in_className nom de la classe d'initialisation
	 * @return instance de la classe d'initialisation
	 * @throws JrafConfigException
	 */
	public static IInitializable instanciateInitializable(
		String in_className,
		Object[] parameters)
		throws JrafConfigException {

		IInitializable lc_initialize = null;
		try {

			// instancie la classe d'initialisation
			// changement du chargement de la classe depuis JRAFv2.1 - FSD0311790
			lc_initialize =
				(IInitializable) ConstructorUtils.invokeConstructor(
					Class.forName(
						in_className,
						true,
						Thread.currentThread().getContextClassLoader()),
					parameters);

		} catch (NoSuchMethodException e) {
			String message =
				"Erreur d'initialisation: le constructeur de la classe d'initialisation n'existe pas.";
			log.error(message, e);
			throw new JrafConfigException(message, e);
		} catch (IllegalAccessException e) {
			String message =
				"Erreur d'initialisation: le constructeur de la classe d'initialisation n'est pas visible.";
			log.error(message, e);
			throw new JrafConfigException(message, e);
		} catch (InvocationTargetException e) {
			String message =
				"Erreur d'initialisation: impossible d'invoquer le constructeur de la classe d'initialisation.";
			log.error(message, e);
			throw new JrafConfigException(message, e);
		} catch (InstantiationException e) {
			String message =
				"Erreur d'initialisation: impossible d'instancier la classe d'initialisation introuvable.";
			log.error(message, e);
			throw new JrafConfigException(message, e);
		} catch (ClassNotFoundException e) {
			String message =
				"Erreur d'initialisation: classe d'initialisation introuvable.";
			log.error(message, e);
			throw new JrafConfigException(message, e);
		}
		return lc_initialize;
	}

	/**
	 * Instancie une classe d'initialisatoin
	 * @param in_className nom de la classe d'initialisation
	 * @return instance de la classe d'initialisation
	 * @throws JrafConfigException
	 */
	public static IInitializable instanciateInitializable(String in_className)
		throws JrafConfigException {

		return instanciateInitializable(in_className, null);
	}

}
