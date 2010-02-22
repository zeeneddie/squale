/**
 * 
 */
package org.squale.gwt.distributionmap.widget.data;

import java.io.Serializable;

/**
 * @author fabrice
 */
public class Child
    implements Serializable
{

    private static final long serialVersionUID = 3710225568047601168L;

    private long id;

    private String name;

    private float grade;

    public Child()
    {

    }

    public Child( long id, String name, float grade )
    {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public float getGrade()
    {
        return grade;
    }

    @Override
    public String toString()
    {
        return "#" + id + " - " + name;
    }

}
