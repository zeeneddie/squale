package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de grille qualité
 */
class GridFactory
    extends FactoryAdapter
{
    /** Grilles qualité */
    private Hashtable mGrids;

    /**
     * Constructeur
     */
    public GridFactory()
    {
        mGrids = new Hashtable();
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
        QualityGridBO grid = (QualityGridBO) mGrids.get( name );
        if ( grid == null )
        {
            grid = new QualityGridBO();
            grid.setName( name );
            mGrids.put( name, grid );
        }
        else
        {
            throw new Exception( XmlRuleMessages.getString( "grid.duplicate", new Object[] { name } ) );
        }
        return grid;
    }

    /**
     * @return grilles
     */
    public Hashtable getGrids()
    {
        return mGrids;
    }

}