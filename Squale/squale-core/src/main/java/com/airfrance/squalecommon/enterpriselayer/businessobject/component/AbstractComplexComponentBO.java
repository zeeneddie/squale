//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\component\\AbstractComplexComponentBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Représente les composants susceptibles d'en contenir d'autres
 * 
 * @author m400842
 * @version 1.0
 * @hibernate.subclass lazy="true" discriminator-value="AbstractComplexComponent"
 */
public abstract class AbstractComplexComponentBO
    extends AbstractComponentBO
{

    /**
     * Les composants enfant du composant
     */
    protected Collection mChildren;

    /**
     * Ajoute un composant enfant.
     * 
     * @param pComponent Composant enfant à ajouter.
     * @roseuid 42AD38F8023E
     */
    public void addComponent( AbstractComponentBO pComponent )
    {
        if ( null == mChildren )
        {
            mChildren = new LinkedList();
        }
        mChildren.add( pComponent );
    }

    /**
     * Retire un élément enfant.
     * 
     * @param pComponent Composant à retirer.
     * @roseuid 42AD3A2E0002
     */
    public void removeComponent( AbstractComponentBO pComponent )
    {
        if ( null != mChildren )
        {
            mChildren.remove( pComponent );
        }
    }

    /**
     * Récupère la collection de composants enfants.
     * 
     * @return les enfants
     * @hibernate.bag table="AbstractComplexComponent_children" lazy="true" // * cascade="save-update" inverse="true"
     * @hibernate.collection-key column="Parent"
     * @hibernate.collection-one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     * @roseuid 42CB92FF008C
     */
    public Collection getChildren()
    {
        return mChildren;
    }

    /**
     * Affecte la collection de composants enfants
     * 
     * @param pCollection les enfants
     * @roseuid 42CB92FF0119
     */
    public void setChildren( Collection pCollection )
    {
        mChildren = pCollection;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CB92FF01F4
     */
    public AbstractComplexComponentBO()
    {
        super();
        mChildren = new ArrayList();
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pChildren les enfants
     * @param pParent Composant parent
     * @roseuid 42CB92FF0261
     */
    public AbstractComplexComponentBO( String pName, Collection pChildren, AbstractComplexComponentBO pParent )
    {
        super( pName, pParent );
        setChildren( pChildren );
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @roseuid 42E617A90071
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        if ( pObj instanceof AbstractComplexComponentBO )
        {
            ret = super.equals( pObj );
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @roseuid 42E617A90073
     */
    public int hashCode()
    {
        return super.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     * @roseuid 42E617A90074
     */
    public String toString()
    {
        return super.toString();
    }
}
