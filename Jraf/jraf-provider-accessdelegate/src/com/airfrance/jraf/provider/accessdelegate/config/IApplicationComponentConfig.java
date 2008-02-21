/*
 * Created on Mar 3, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.config;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Title : IApplicationComponentConfig.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public interface IApplicationComponentConfig extends Serializable {

	public String getName();

	public String getDescription();

	public String getImpl();

	public String getJndiName();

	public Map getComponents();

	public boolean isComponent(String name);

}
