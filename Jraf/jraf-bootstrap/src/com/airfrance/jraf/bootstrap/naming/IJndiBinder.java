/*
 * Cree le 24 janv. 05
 */
package com.airfrance.jraf.bootstrap.naming;

/**
 * <p>Project: JRAF 
 * <p>Title : IJndiBinder</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE </p>
 */
public interface IJndiBinder {
	public abstract void bind(String key, Object o);
	public abstract Object lookup(String key);
}