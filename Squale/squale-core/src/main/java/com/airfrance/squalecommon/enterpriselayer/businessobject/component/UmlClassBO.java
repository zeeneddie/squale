//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\component\\ClassBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.Collection;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;

/**
 * Représente une classe au sens UML
 * 
 * @author m400842
 * @hibernate.subclass lazy="true" discriminator-value="UmlClass"
 */
public class UmlClassBO
    extends AbstractComplexComponentBO
{

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     * @roseuid 42AFF04102A0
     */
    public UmlClassBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CBA49F0100
     */
    public UmlClassBO()
    {
        super();
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pChildren les enfants
     * @param pParent Composant parent
     * @throws UnexpectedRelationException si la relation ne peut etre ajouté
     * @roseuid 42CBA49F010F
     */
    public UmlClassBO( String pName, Collection pChildren, AbstractComplexComponentBO pParent )
        throws UnexpectedRelationException
    {
        super( pName, pChildren, pParent );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor,
     *      java.lang.Object)
     */
    public Object accept( ComponentVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }
}
