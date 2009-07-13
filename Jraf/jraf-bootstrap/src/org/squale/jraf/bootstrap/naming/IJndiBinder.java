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
 * Cree le 24 janv. 05
 */
package org.squale.jraf.bootstrap.naming;

/**
 * <p>Project: JRAF 
 * <p>Title : IJndiBinder</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 *  
 */
public interface IJndiBinder {
	public abstract void bind(String key, Object o);
	public abstract Object lookup(String key);
}