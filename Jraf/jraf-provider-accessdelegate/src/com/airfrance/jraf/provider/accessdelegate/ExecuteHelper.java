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
 * Created on Mar 30, 2004
 */
package org.squale.jraf.provider.accessdelegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.accessdelegate.IAccessDelegateProvider;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;

/**
 * <p>Title : ExecuteHelper.java</p>
 * <p>Description : Helper pour aider la delegation des appels de methodes
 * dans les application component</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ExecuteHelper {

	private final static int DEPTH = 3;

	/** logger */
	private static final Log log = LogFactory.getLog(ExecuteHelper.class);

	/**
	 * Methode execute pour les application component
	 * @param in_applicationComponent application component
	 * @param in_method methode a invoquer
	 * @param in_parameters parametres de la methode
	 * @return retour de la methode
	 * @throws JrafEnterpriseException
	 */
	public final static Object execute(
		IApplicationComponent in_applicationComponent,
		String in_method,
		Object[] in_parameters)
		throws JrafEnterpriseException {

		Object lc_returnedObject = null;

		Method lc_method = null;

		// recuperation de l'access delegate provider
		IAccessDelegateProvider lc_adp = null;

		lc_adp = in_applicationComponent.getAccessDelegateProvider();

		// validation de l'existance du service
		if (log.isDebugEnabled()) {
			log.debug(
				"Validation du composant "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method);
		}

		// valide l'existance du service
		if (!lc_adp
			.validateService(
				in_applicationComponent.getApplicationComponent(),
				in_method)) {

			// le service n'existe pas
			log.error(
				"Le composant "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method
					+ " n'est pas defini dans le contrat de l'application component");

			throw new JrafEnterpriseException(
				"Le composant "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method
					+ " n'est pas defini dans le contrat de l'application component");

		}

		// le service existe
		if (log.isDebugEnabled()) {
			log.debug(
				"Le composant "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method
					+ " existe");
		}

		// delegue l'appel de methode sur l'ejb local
		try {

			lc_method =
				internalFindMethod(
					in_applicationComponent.getClass(),
					in_method,
					in_parameters);

			if (lc_method == null) {
				log.error(
					"Le composant "
						+ in_applicationComponent.getApplicationComponent()
						+ "."
						+ in_method
						+ " n'est pas defini (introuvable dans la classe Java).");

				throw new JrafEnterpriseException(
					"Le composant "
						+ in_applicationComponent.getApplicationComponent()
						+ "."
						+ in_method
						+ " n'est pas defini (introuvable dans la classe Java).");
			}

			lc_returnedObject =
				lc_method.invoke(in_applicationComponent, in_parameters);

		} catch (IllegalAccessException e) {
			log.error(
				"Le composant "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method
					+ " n'est pas accessible",
				e);
			throw new JrafEnterpriseException(
				"Le composant "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method
					+ " n'est pas accessible");
		} catch (InvocationTargetException e) {
			// convertie l'exception
			convertInvocationTargetException(
				in_applicationComponent,
				in_method,
				e);
		}

		// le service existe
		if (log.isDebugEnabled()) {
			log.debug(
				"La delegation de l'appel "
					+ in_applicationComponent.getApplicationComponent()
					+ "."
					+ in_method
					+ " s'est deroule avec succes");
		}
		// retourne l'objet retourne par l'appel de methode
		return lc_returnedObject;
	}

	/**
	 * Valide la concordance entre les parametres d'entree 
	 * et les parametres de la methode candidate.
	 * Recherche la validite de l'heritage jusqu'au niveau DEPTH
	 * @param in_argCount nombre d'arguments
	 * @param in_parameters parametres d'entree
	 * @param in_parameterTypes parametres de la methode trouvee
	 * @return true si les parametres sont en concordance, false sinon.
	 */
	protected final static boolean checkParametersMatch(
		int in_argCount,
		Object[] in_parameters,
		Class[] in_parameterTypes) {

		Class cla = null;
		Class[] ifcs = null;

		// on parcours les parametres
		for (int j = 0; j < in_argCount; j++) {

			// si le parametre est null on le passe
			if (in_parameters[j] == null) {
				continue;
			}

			// si les parametres sont différents
			if (!in_parameterTypes[j]
				.getName()
				.equals(in_parameters[j].getClass().getName())) {

				// Type de l'arborescence à étudier.
				//				if (in_parameterTypes[j].isInterface())
				//					ifcs = in_parameters[j].getClass().getInterfaces();
				//				else
				//					ifcs = in_parameters[j].getClass().getClasses();

				// Paramètre candidat
				//				if ((ifcs.length != 0)
				//					&& ifcs[0].isAssignableFrom(in_parameterTypes[j]))
				// Le parametre de la methode utilisateur est-il assignable a la methode candidate
				if (in_parameterTypes[j]
					.isAssignableFrom(in_parameters[j].getClass()))
					continue;
				else {
					return false;
				}
			} else
				continue;
		}
		return true;
	}

	/**
	 * Recherche une methode a partir de ce nom et de ces parametres.
	 * La methode est recherchee dans toute la hierarchie du type et de ces parametres.
	 * @param in_class classe 
	 * @param in_methodName nom de la methode
	 * @param in_parameters parametres
	 * @return methode trouvee
	 */
	public final static Method internalFindMethod(
		Class in_class,
		String in_methodName,
		Object[] in_parameters) {

		Method[] lc_methods = null;
		Method lc_method = null;
		Class[] lc_param = null;
		boolean paramHok = false;

		int lc_argCount = 0;

		if (in_parameters != null)
			lc_argCount = in_parameters.length;

		for (Class cl = in_class; cl != null; cl = cl.getSuperclass()) {

			lc_methods = cl.getMethods();

			lc_method = null;
			for (int i = 0; i < lc_methods.length; i++) {
				lc_method = lc_methods[i];

				if (lc_method == null)
					continue;
				int mods = lc_method.getModifiers();
				if (Modifier.isStatic(mods))
					continue;

				if (lc_method.getName().equals(in_methodName)
					&& lc_method.getParameterTypes().length == lc_argCount) {
					//					System.out.println("Methode candidate: " + method.getName() + "  param length: " + method.getParameterTypes().length);
					//					System.out.println("Methode recherchee: " + methodName + "  parameter length: " + argCount);
					lc_param = lc_method.getParameterTypes();

					//					for (int j = 0; j < argCount; j++)
					//						   System.out.println("pfind =  " + param[j].getName());
					//					   for (int j = 0; j < argCount; j++)
					//						   System.out.println("psearch =  " + parameter[j] ); // parameter[j].getClass().getName());
					paramHok =
						checkParametersMatch(
							lc_argCount,
							in_parameters,
							lc_param);
					if (paramHok) {
						return lc_method;
					}
				}
			}
		}

		// cas pour la recherche dans des interfaces parentes
		Class[] ifcs = in_class.getInterfaces();
		for (int i = 0; i < ifcs.length; i++) {
			lc_method =
				internalFindMethod(ifcs[i], in_methodName, in_parameters);
			if (lc_method != null) {
				return lc_method;
			}
		}
		return null;
	}

	/**
	 * Convertie une InvocationTargetException en JrafEnterpriseException
	 * @param in_ac application component 
	 * @param in_method method
	 * @param in_ite exception
	 * @throws JrafEnterpriseException
	 */
	public final static void convertInvocationTargetException(
		IApplicationComponent in_ac,
		String in_method,
		InvocationTargetException in_ite)
		throws JrafEnterpriseException {

		Throwable t = in_ite.getTargetException();
		if (t != null) {
			if (log.isDebugEnabled()) {
				log.debug(
					"Probleme lors de l'invocation du composant "
						+ in_ac.getApplicationComponent()
						+ "."
						+ in_method,
					t);
			}
			if (t instanceof JrafEnterpriseException) {
				// deja une JRAFEnterpriseException
				throw (JrafEnterpriseException) t;

			} else {
				// on transforme l'exception contenu en JRAFEnterpriseException
				throw new JrafEnterpriseException(
					"Probleme lors de l'invocation du composant "
						+ in_ac.getApplicationComponent()
						+ "."
						+ in_method,
					t);

			}

		} else {
			// cas anormal: L' InvocationTargetException ne contient pas
			// d'exception.
			// ne doit pas se produire
			if (log.isDebugEnabled()) {
				log.debug(
					"Probleme lors de l'invocation du composant : l'exception recuperee n'est pas conforme. "
						+ in_ac.getApplicationComponent()
						+ "."
						+ in_method,
					in_ite);
			}

			throw new JrafEnterpriseException(
				"Probleme lors de l'invocation du composant : l'exception recuperee n'est pas conforme. "
					+ in_ac.getApplicationComponent()
					+ "."
					+ in_method,
				in_ite);
		}

	}

}
