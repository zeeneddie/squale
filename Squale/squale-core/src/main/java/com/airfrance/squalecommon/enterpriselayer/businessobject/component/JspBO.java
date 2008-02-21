package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;

/**
 * Représente une page JSP
 * 
 * @hibernate.subclass
 * lazy="true"
 * discriminator-value="Jsp"
 */
public class JspBO extends AbstractComponentBO {

    /**
     * Chemin du fichier à partir du projet
     */
    private String mFileName;
    
    /**
     * Instancie un nouveau composant JSP.
     * @param pName Nom de la page JSP.
     */
    public JspBO(final String pName) {
        super();
        setName(pName);
    }
    
    /**
     * Constructeur par défaut.
     */
    public JspBO() {
        super();
    }
    
    /**
     * Constructeur complet.
     * @param pName nom du composant
     * @param pParent Composant parent
     * @throws UnexpectedRelationException si la relation ne peut etre ajoutée
     */
    public JspBO(String pName, AbstractComplexComponentBO pParent) throws UnexpectedRelationException {
        super(pName, pParent);
    }

    /**
     * @return le chemin du fichier à partir du projet
     * 
     * @hibernate.property 
     * name="fileName" 
     * column="LongFileName" 
     * type="string" 
     * length="2048"
     * not-null="false" 
     * unique="false"
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * @param pFileName le nouveau chemin du fichier
     */
    public void setFileName(String pFileName) {
        mFileName = pFileName;
    }

    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor, java.lang.Object)
     */
    public Object accept(ComponentVisitor pVisitor, Object pArgument) {
        return pVisitor.visit(this, pArgument);
    }
}

