/*
 * Created on Apr 2, 2004
 */
package com.airfrance.jraf.bootstrap.test;

import java.util.List;
import java.util.Map;

import com.airfrance.jraf.spi.bootstrap.IBootstrapProvider;

/**
 * <p>Title : BootstrapProviderMock.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class BootstrapMockProvider implements IBootstrapProvider {

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.IBootstrapProvider#getParameters()
	 */
	public Map getParameters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.IBootstrapProvider#getProviders()
	 */
	public List getProviders() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.IBootstrapProvider#setProviders(java.util.List)
	 */
	public void setProviders(List in_providers) {

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.bootstrap.IBootstrapProvider#setParameters(java.util.Map)
	 */
	public void setParameters(Map in_parameters) {

	}
}
