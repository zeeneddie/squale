package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de facteur
 */
class FactorFactory
    extends FactoryAdapter
{
    /** Facteurs */
    private Hashtable mFactors;

    /**
     * Constructeur
     */
    public FactorFactory()
    {
        mFactors = new Hashtable();
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes attributes )
        throws Exception
    {
        String name = attributes.getValue( "name" );
        FactorRuleBO factor = (FactorRuleBO) mFactors.get( name );
        if ( factor == null )
        {
            factor = new FactorRuleBO();
            factor.setName( name );
            mFactors.put( name, factor );
        }
        else
        {
            // Détection d'objet dupliqué
            throw new Exception( XmlRuleMessages.getString( "factor.duplicate", new Object[] { name } ) );
        }
        return factor;
    }

    /**
     * @return facteurs
     */
    public Hashtable getFactors()
    {
        return mFactors;
    }

}