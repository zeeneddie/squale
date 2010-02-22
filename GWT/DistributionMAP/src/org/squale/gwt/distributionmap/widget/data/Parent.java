/**
 * 
 */
package org.squale.gwt.distributionmap.widget.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author fabrice
 */
public class Parent
    implements Serializable
{

    private static final long serialVersionUID = 1691475443808497741L;

    private String name;

    private ArrayList<Child> children;

    public Parent()
    {
    }

    public Parent( String name )
    {
        this.name = name;
        children = new ArrayList<Child>();
    }

    public void addChild( Child child )
    {
        children.add( child );
    }

    public Collection<Child> getChildren()
    {
        return children;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
