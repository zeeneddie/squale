/*
 * Cree le 20 janv. 05
 */
package com.airfrance.jraf.provider.accessdelegate.ejb;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.LookUpComponent;
import com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfig;
import com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;

/**
 * <p>Project: JRAF 
 * <p>Title : EJBLookupComponent</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE </p>
 */
public class EJBLookupComponent extends LookUpComponent {

	/**
	 * 
	 */
	public EJBLookupComponent() {
		super();
	}

	/**
	 * @param applicationConfigReader
	 */
	public EJBLookupComponent(IApplicationComponentConfigReader applicationConfigReader) {
		super(applicationConfigReader);
	}

	/**
	 * @param appCompConfigs
	 */
	public EJBLookupComponent(Map appCompConfigs) {
		super(appCompConfigs);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.accessdelegate.ILookupComponent#findApplicationComponent(java.lang.String)
	 */
	public IApplicationComponent findApplicationComponent(String in_applicationComponentName)
		throws JrafEnterpriseException {

		IEJBAccess ejbAccess = null;
		// recuperation d'un application component
		IApplicationComponent applicationComponent =
			super.findApplicationComponent(in_applicationComponentName);

		IApplicationComponentConfig config = null;

		if (applicationComponent != null) {

			config =
				(IApplicationComponentConfig) getAppCompConfigs().get(
					in_applicationComponentName);

			// si ejb application component, on fixe le nom jndi
			if (applicationComponent instanceof IEJBAccess) {

				// transtype en ejb access
				ejbAccess = (IEJBAccess) applicationComponent;

				// recuperation du nom jndi
				ejbAccess.setJndiName(config.getJndiName());

			}
		}

		return applicationComponent;

	}

}
