/*
 * Created on Mar 12, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.ejb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.accessdelegate.DefaultCMTExecuteComponent;
import com.airfrance.jraf.provider.accessdelegate.ILookupComponent;
import com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;

/**
 * <p>Title : EJBAccessDelegateProviderImpl.java</p>
 * <p>Description : Access delegate provider EJB.
 * Fournit le même service que l'access delegate tradionnel avec en plus l'initialisation des EJB access.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * <p>@deprecated : Depuis JRAFv2.1 le provider access delegate ejb n'est plus utilise: 
 * C'est le provider access delegate par defaut qui est utilise pour initialiser des composants EJB.
 * L'initialiseur n'initialise plus cette classe mais celle du provider access delegate par defaut.
 * </p>
 */
public class EJBAccessDelegateProviderImpl
	implements IAccessDelegateProvider, IInitializableBean {

	/** logger */
	private static final Log log =
		LogFactory.getLog(EJBAccessDelegateProviderImpl.class);

	/** composant de lookup */
	private ILookupComponent lookupComponent;

	private IPersistenceProvider persistenceProvider;

	/**
	 * Constructeur vide type IOC2
	 */
	public EJBAccessDelegateProviderImpl() {
		super();
	}

	/**
	 * Constructeur avec parametres de type IOC3
	 * @param lookupComponent lookup de composant
	 */
	public EJBAccessDelegateProviderImpl(ILookupComponent lookupComponent) {
		this(lookupComponent, null);
	}

	/**
	 * Constructeur avec parametres de type IOC3
	 * @param lookupComponent lookup de composant
	 * @param persistenceProvider provider de persistance
	 */
	public EJBAccessDelegateProviderImpl(
		ILookupComponent lookupComponent,
		IPersistenceProvider persistenceProvider) {
		super();
		setLookupComponent(lookupComponent);
		setPersistenceProvider(persistenceProvider);
		afterPropertiesSet();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider#getInstance(java.lang.String)
	 */
	public IApplicationComponent getInstance(String in_applicationComponentName)
		throws JrafEnterpriseException {

		IApplicationComponent instanceAppComponent = null;
		DefaultCMTExecuteComponent cmtApplicationComponent = null;
		IInitializableBean ibean = null;

		try {
			instanceAppComponent =
				lookupComponent.findApplicationComponent(
					in_applicationComponentName);

			// on fixe le provider dans l'application component
			instanceAppComponent.setAccessDelegateProvider(this);

			// cas du CMTApplicationComponent
			if (instanceAppComponent instanceof DefaultCMTExecuteComponent) {
				cmtApplicationComponent =
					(DefaultCMTExecuteComponent) instanceAppComponent;

				// si le provider de persistance est 'null' on le renseigne 
				// en faisant appel PersistenceHelper.
				if (getPersistenceProvider() == null) {
					setPersistenceProvider(
						PersistenceHelper.getPersistenceProvider());
				}

				// on fixe le provider de persistance
				cmtApplicationComponent.setPersistenceProvider(
					getPersistenceProvider());

				// le composant est initiateur de transaction
				cmtApplicationComponent.setTransactionInitiator(true);

			}

			// on valide l'initialisation
			if (instanceAppComponent instanceof IInitializableBean) {
				ibean = (IInitializableBean) instanceAppComponent;
				ibean.afterPropertiesSet();

			}

		} catch (JrafEnterpriseException e) {
			throw new JrafEnterpriseException(
				"Echec de la construction du applicationComponentdelegate, problème d'implémentation de la classe du domaine de component :"
					+ in_applicationComponentName,
				e);
		}
		return instanceAppComponent;

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider#validateService(java.lang.String, java.lang.String)
	 */
	public boolean validateService(
		String in_applicationcomponent,
		String in_nameService) {

		return getLookupComponent().validateService(
			in_applicationcomponent,
			in_nameService);
	}

	/**
	 * Composant de lookup
	 * @return composant de lookup
	 */
	public ILookupComponent getLookupComponent() {
		return lookupComponent;
	}

	/**
	 * Fixe le composant de lookup
	 * @param component composant de lookup
	 */
	public void setLookupComponent(ILookupComponent component) {
		lookupComponent = component;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (getLookupComponent() == null) {
			String message = "Pas de lookup component fourni.";
			log.fatal(message);
			throw new JrafConfigException("Pas de lookup component fourni.");

		}

	}

	/**
	 * Retourne le provider de persistance
	 * @return provider de persistance
	 */
	public IPersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	/**
	 * Fixe le provider de persistance
	 * @param provider provider de persistance
	 */
	public void setPersistenceProvider(IPersistenceProvider provider) {
		persistenceProvider = provider;
	}

}
