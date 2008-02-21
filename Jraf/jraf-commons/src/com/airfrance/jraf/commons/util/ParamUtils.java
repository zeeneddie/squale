/*
 * Created on Dec 27, 2004
 */
package com.airfrance.jraf.commons.util;

/**
 * <p>Title : ParamUtils.java</p>
 * <p>Description : Permet la création de tableaux de parametres</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ParamUtils {

	/**
	 * Cree un tableau d'objet avec 1 element
	 * @param o1
	 * @return
	 */
	public final static Object[] buildParameters(Object o1) {

		Object[] params = new Object[1];
		params[0] = o1;
		return params;
	}

	/**
	 * Cree un tableau d'objet avec 2 elements
	 * @param o1
	 * @param o2
	 * @return
	 */
	public final static Object[] buildParameters(Object o1, Object o2) {

		Object[] params = new Object[2];
		params[0] = o1;
		params[1] = o2;
		return params;
	}

	/**
	 * Cree un tableau d'objet avec 3 elements
	 * @param o1
	 * @param o2
	 * @param o3
	 * @return
	 */
	public final static Object[] buildParameters(
		Object o1,
		Object o2,
		Object o3) {

		Object[] params = new Object[3];
		params[0] = o1;
		params[1] = o2;
		params[2] = o3;
		return params;
	}

	/**
	 * Cree un tableau d'objet avec 4 elements
	 * @param o1
	 * @param o2
	 * @param o3
	 * @param o4
	 * @return
	 */
	public final static Object[] buildParameters(
		Object o1,
		Object o2,
		Object o3,
		Object o4) {

		Object[] params = new Object[4];
		params[0] = o1;
		params[1] = o2;
		params[2] = o3;
		params[3] = o4;
		return params;
	}

	/**
	 * Cree un tableau d'objet avec 5 elements
	 * @param o1
	 * @param o2
	 * @param o3
	 * @param o4
	 * @param o5
	 * @return
	 */
	public final static Object[] buildParameters(
		Object o1,
		Object o2,
		Object o3,
		Object o4,
		Object o5) {

		Object[] params = new Object[5];
		params[0] = o1;
		params[1] = o2;
		params[2] = o3;
		params[3] = o4;
		params[4] = o5;
		return params;
	}

	/**
	 * Cree un tableau d'objet avec 5 elements
	 * @param o1
	 * @param o2
	 * @param o3
	 * @param o4
	 * @param o5
	 * @param o6
	 * @return
	 */
	public final static Object[] buildParameters(
		Object o1,
		Object o2,
		Object o3,
		Object o4,
		Object o5,
		Object o6) {

		Object[] params = new Object[6];
		params[0] = o1;
		params[1] = o2;
		params[2] = o3;
		params[3] = o4;
		params[4] = o5;
		params[5] = o6;
		return params;
	}

}
