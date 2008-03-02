package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.Collection;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;

/**
 * Modèle UML
 * 
 * @hibernate.subclass lazy="true" discriminator-value="UmlModel"
 */
public class UmlModelBO
    extends AbstractComplexComponentBO
{

    /**
     * Constructeur par défaut.
     */
    public UmlModelBO()
    {
        super();
    }

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     */
    public UmlModelBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pChildren les enfants
     * @param pParent Composant parent
     * @throws UnexpectedRelationException si la relation ne peut etre ajouté
     */
    public UmlModelBO( String pName, Collection pChildren, AbstractComplexComponentBO pParent )
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
