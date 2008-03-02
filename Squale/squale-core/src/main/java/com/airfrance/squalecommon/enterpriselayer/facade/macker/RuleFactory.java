package com.airfrance.squalecommon.enterpriselayer.facade.macker;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Factory pour les règles
 */
public class RuleFactory
    extends FactoryAdapter
{

    /** La catégorie de la règle par défaut */
    public static final String CATEGORY = "layerrespect";

    /** Le code de la règle par défaut */
    public static final String CODE = "Illegal reference";

    /** La sévérité de la règle par défaut */
    public static final String SEVERITY = "error";

    /**
     * Crée une règle en lui attribuant une catégorie, un code et une sévérité par défaut.
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes arg0 )
        throws Exception
    {
        RuleBO rule = new RuleBO();
        rule.setCategory( CATEGORY );
        rule.setCode( CODE );
        rule.setSeverity( SEVERITY );
        return rule;
    }

}
