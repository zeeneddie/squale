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
 * Created on Jan 6, 2005
 */
package org.squale.jraf.spi.initializer;

/**
 * <p>Title : IInitializableBean.java</p>
 * <p>Description : Interface utilise pour l'initialisation d'un bean.
 * Lorsque les proprietes du bean ont ete renseignes, la methode afterPropertiesSet() est appelee.
 * Cette methode verifie la bonne initialisation du bean.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public interface IInitializableBean {
	
	/**
	 * Methode appelee apres que les proprietes d'un bean soient renseignees. 
	 * Elle permet la finalisation de l'initialisation.
	 * Cette methode ne lance pas d'exception autre que de type Runtime (non gerees).
	 */
	public void afterPropertiesSet();
}
