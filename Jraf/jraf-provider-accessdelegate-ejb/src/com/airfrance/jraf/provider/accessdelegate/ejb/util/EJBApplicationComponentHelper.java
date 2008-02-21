/*
 * Created on Mar 2, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.ejb.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.ExecuteHelper;
import com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;

/**
 * <p>Title : EJBApplicationComponentHelper.java</p>
 * <p>Description : Class helper l'utilisation des EJBs</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class EJBApplicationComponentHelper {

	/** methode de creation d'un EJB local */
	private final static String CREATE_METHOD = "create";

	/** logger */
	private final static Log log =
		LogFactory.getLog(EJBApplicationComponentHelper.class);

	/**
	 * Cree un ejb local a partir du nom jndi de son local home.
	 * @param jndiName nom jndi du local home
	 * @return ejb local
	 * Modification de la signature depuis JRAFv2.1 : remplacement de l'exception JRAFEnterpriseException
	 * par JrafConfigException
	 */
	public final static EJBLocalObject createLocalObject(String jndiName)
		throws JrafConfigException {

		// local object
		EJBLocalObject _localObject = null;

		//local home
		EJBLocalHome _localHome = null;

		if (log.isDebugEnabled()) {
			log.debug("recuperation d'un ejb local home");
		}

		// recuperation de l'ejb local home
		_localHome =
			(EJBLocalHome) JNDIHelper.singleton().lookupLocalHome(jndiName);

		try {
			if (log.isDebugEnabled()) {
				log.debug("creation d'un ejb local");
			}
			_localObject =
				(EJBLocalObject) MethodUtils.invokeMethod(
					_localHome,
					CREATE_METHOD,
					null);
			if (log.isDebugEnabled()) {
				log.debug("ejb local cree");
			}
		} catch (Exception e1) {
			log.error("Probleme survenu lors de la creation d'un ejb", e1);
			throw new JrafConfigException(
				"Probleme survenu lors de la creation d'un ejb",
				e1);
		}

		// retourne l'objet local
		return _localObject;
	}

	/**
	 * Execute une methode sur l'ejb local
	 * @param appComponent application component
	 * @param ejbLocalObject ejb local object
	 * @param method methode a executer
	 * @param parameters parametres d'execution
	 * @return retour de la methode
	 */
	public static Object execute(
		IApplicationComponent appComponent,
		EJBLocalObject ejbLocalObject,
		String method,
		Object[] parameters)
		throws JrafEnterpriseException {

		// retour de l'appel de methode
		Object returnedObject = null;

		// methode a appeler
		Method lc_method = null;

		// recuperation de l'access delegate
		IAccessDelegateProvider lc_adp =
			appComponent.getAccessDelegateProvider();

		// validation de l'existance du service
		if (log.isDebugEnabled()) {
			log.debug(
				"Validation du composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method);
		}
		// valide l'existance du service
		if (!lc_adp
			.validateService(appComponent.getApplicationComponent(), method)) {

			// le service n'existe pas
			log.error(
				"Le composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method
					+ " n'existe pas dans le contrat de l'application component");

			throw new JrafEnterpriseException(
				"Le composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method
					+ " n'existe pas dans le contrat de l'application component");

		}

		// le service existe
		if (log.isDebugEnabled()) {
			log.debug(
				"Le composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method
					+ " existe");
		}
		// delegue l'appel de methode sur l'ejb local
		try {

			lc_method =
				ExecuteHelper.internalFindMethod(
					ejbLocalObject.getClass(),
					method,
					parameters);

			returnedObject = lc_method.invoke(ejbLocalObject, parameters);

		} catch (IllegalAccessException e) {
			log.error(
				"Le composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method
					+ " n'est pas accessible",
				e);
			throw new JrafEnterpriseException(
				"Le composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method
					+ " n'est pas accessible");
		} catch (InvocationTargetException e) {
			log.error(
				"Probleme lors de l'invocation du composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method,
				e);
			throw new JrafEnterpriseException(
				"Probleme lors de l'invocation du composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method,
				e);

		} catch (EJBException e) {
			log.error(
				"Probleme lors de l'invocation du composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method,
				e);
			throw new JrafEnterpriseException(
				"Probleme lors de l'invocation du composant "
					+ appComponent.getApplicationComponent()
					+ "."
					+ method,
				e);
		}

		// retourne l'objet retourne par l'ejb
		return returnedObject;

	}
}