/*
 * Cree le 21 janv. 05
 */
package com.airfrance.jraf.bootstrap.locator;

import java.io.Serializable;

import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Title : ILocator</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE </p>
 */
public interface ILocator extends Serializable {

	public IProvider get(String name);
	public void put(String name, IProvider provider);
	public void remove(String name);

}
