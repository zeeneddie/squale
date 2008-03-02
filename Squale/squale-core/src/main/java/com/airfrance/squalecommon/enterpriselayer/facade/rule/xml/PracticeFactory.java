package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de pratique
 */
class PracticeFactory
    extends FactoryAdapter
{
    /** Pratiques */
    private Hashtable mPractices;

    /**
     * Constructeur
     */
    public PracticeFactory()
    {
        mPractices = new Hashtable();
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
            practice = new PracticeRuleBO();
            practice.setName( name );
            mPractices.put( name, practice );
        }
        else
        {
            // Détection d'objet dupliqué
            throw new Exception( XmlRuleMessages.getString( "practice.duplicate", new Object[] { name } ) );
        }
        return practice;
    }

    /**
     * @return pratiques
     */
    public Hashtable getPractices()
    {
        return mPractices;
    }

}