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
 * Created on Mar 3, 2004
 */
package org.squale.jraf.provider.accessdelegate.config;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Title : IApplicationComponentConfig.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
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
