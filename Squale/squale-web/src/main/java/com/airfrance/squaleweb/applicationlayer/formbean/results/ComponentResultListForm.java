package com.airfrance.squaleweb.applicationlayer.formbean.results;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * List of components which have some values for some tres
 */
public class ComponentResultListForm
    extends RootForm
{

    /**
     * Serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * list of components
     */
    private ComponentListForm componentListForm = new ComponentListForm();

    /** keys for tre */
    private String[] treKeys = new String[0];

    /** values of tres */
    private String[] treValues = new String[0];

    /**
     * Getter for components list
     * 
     * @return list of components
     */
    public ComponentListForm getComponentListForm()
    {
        return componentListForm;
    }

    /**
     * Setter for components list
     * 
     * @param pComponentListForm new components
     */
    public void setComponentListForm( ComponentListForm pComponentListForm )
    {
        componentListForm = pComponentListForm;
    }

    /**
     * Getter for keys
     * 
     * @return keys
     */
    public String[] getTreKeys()
    {
        return treKeys;
    }

    /**
     * Setter for keys
     * 
     * @param pTreKeys new keys
     */
    public void setTreKeys( String[] pTreKeys )
    {
        this.treKeys = pTreKeys;
    }

    /**
     * Getter for values
     * 
     * @return values of tres
     */
    public String[] getTreValues()
    {
        return treValues;
    }

    /**
     * Setter for values
     * 
     * @param pTreValues new values
     */
    public void setTreValues( String[] pTreValues )
    {
        this.treValues = pTreValues;
    }

}
