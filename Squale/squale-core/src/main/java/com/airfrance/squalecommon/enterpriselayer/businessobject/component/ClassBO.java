//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\component\\ClassBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.Collection;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;

/**
 * Représente une classe au sens Java et C++
 * 
 * @author m400842
 * @hibernate.subclass lazy="true" discriminator-value="Class"
 */
public class ClassBO
    extends AbstractComplexComponentBO
{

    /**
     * Chemin du fichier à partir du projet
     */
    private String mFileName;

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     * @roseuid 42AFF04102A0
     */
    public ClassBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CBA49F0100
     */
    public ClassBO()
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
    public ClassBO( String pName, Collection pChildren, AbstractComplexComponentBO pParent )
        throws UnexpectedRelationException
    {
        super( pName, pChildren, pParent );
    }

    /**
     * @return le chemin du fichier à partir du projet
     * @hibernate.property name="fileName" column="LongFileName" type="string" length="2048"
     */
    public String getFileName()
    {
        return mFileName;
    }

    /**
     * @param pFileName le nouveau chemin du fichier
     */
    public void setFileName( String pFileName )
    {
        mFileName = pFileName;
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
