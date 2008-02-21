package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;

/**
 * 
 * Interface au sens UML
 * @hibernate.subclass
 * lazy="true"
 * discriminator-value="UmlInterface"
 */
public class UmlInterfaceBO extends AbstractComponentBO{
    /**
     * Constructeur par défaut.
     * 
     */
    public UmlInterfaceBO(){
        super();
    }
    /**
     * Instancie un nouveau composant.
     * @param pName Nom du composant.
     * 
     */
    public UmlInterfaceBO(final String pName){
        super();
        setName(pName);
    }
    /**
     * Constructeur complet.
     * @param pName nom du composant
     * @param pParent Composant parent
     * @throws UnexpectedRelationException si la relation ne peut etre ajouté
     * 
     */
    public UmlInterfaceBO(final String pName,AbstractComplexComponentBO pParent) throws UnexpectedRelationException{
        super(pName,pParent);
    }

    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor, java.lang.Object)
     */
    public Object accept(ComponentVisitor pVisitor, Object pArgument) {
        return pVisitor.visit(this, pArgument);
    }
}
