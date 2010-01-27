/**
 * 
 */
package org.squale.test.gwt.client.widget.data;

import java.io.Serializable;

/**
 * @author fabrice
 * 
 */
public class Child implements Serializable {
	
	private static final long serialVersionUID = 3710225568047601168L;

	private String name;

	private float grade;

	public Child() {
		
	}
	
	public Child(String name, float grade) {
		this.name = name;
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
