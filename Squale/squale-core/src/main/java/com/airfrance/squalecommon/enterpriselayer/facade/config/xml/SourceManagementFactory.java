package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de type de récupération des sources
 */
public class SourceManagementFactory
    extends FactoryAdapter
{

    /** Les noms des types de récupération des sources */
    private Hashtable mSourceManagements;

    /**
     * Constructeur
     */
    public SourceManagementFactory()
    {
        mSourceManagements = new Hashtable();
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
        SourceManagementBO sourceManagement = (SourceManagementBO) mSourceManagements.get( name );
        if ( null == sourceManagement )
        {
            sourceManagement = new SourceManagementBO();
            sourceManagement.setName( name );
            mSourceManagements.put( name, sourceManagement );
        }
        else
        {
            throw new Exception( XmlConfigMessages.getString( "sourcemanagement.duplicate", new Object[] { name } ) );
        }
        return sourceManagement;
    }

    /**
     * @return la liste des différents types de récupération des sources
     */
    public Hashtable getSourceManagements()
    {
        return mSourceManagements;
    }

}