package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de référence de pratique
 */
class PracticeRefFactory
    extends FactoryAdapter
{
    /** Pratiques */
    private Hashtable mPractices;

    /**
     * Constructeur
     * 
     * @param pPractices pratiques existantes
     */
    public PracticeRefFactory( Hashtable pPractices )
    {
        mPractices = pPractices;
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
        PracticeRuleBO practice = (PracticeRuleBO) mPractices.get( name );
        if ( practice == null )
        {
            // Détection d'objet inexistant
            throw new Exception( XmlRuleMessages.getString( "practice.unknown", new Object[] { name } ) );
        }
        return practice;
    }

}