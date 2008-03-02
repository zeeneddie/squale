package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de régles internes
 */
public class CheckstyleRuleSetFactory
    extends FactoryAdapter
{

    /**
     * Checkstyle Rules Set
     */
    private CheckstyleRuleSetBO mCheckstyleRuleSet = null;

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes pAttributes )
        throws Exception
    {
        mCheckstyleRuleSet = new CheckstyleRuleSetBO();
        String name = pAttributes.getValue( "name" );
        String value = pAttributes.getValue( "value" );
        if ( name.trim().equals( XmlCheckstyleParsingMessages.getString( "checkstyle.version" ) ) )
        {
            mCheckstyleRuleSet.setName( value );
        }
        else
        {
            throw new Exception( XmlCheckstyleParsingMessages.getString( "checkstyle.pattern.forgeted",
                                                                         new Object[] { "\n<module>\n\t"
                                                                             + "<metadata>\n" } ) );
        }
        return mCheckstyleRuleSet;
    }

    /**
     * @return rulesSet checkstyle
     */
    public CheckstyleRuleSetBO getCheckstyleRuleSets()
    {
        return mCheckstyleRuleSet;
    }

}
