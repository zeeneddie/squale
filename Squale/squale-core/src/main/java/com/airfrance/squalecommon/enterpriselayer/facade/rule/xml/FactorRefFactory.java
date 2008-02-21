package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de références de facteur
 *
 */
class FactorRefFactory extends FactoryAdapter {
    /** Facteurs existants */
    private Hashtable mFactors;
    /**
     * Constructeur
     * @param pFactors facteurs existants
     */
    public FactorRefFactory(Hashtable pFactors) {
        mFactors = pFactors;
    }
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes attributes) throws Exception {
        String name = attributes.getValue("name");
        FactorRuleBO factor = (FactorRuleBO) mFactors.get(name);
        if (factor == null) {
            // Détection d'objet inexistant
            throw new Exception(XmlRuleMessages.getString("factor.unknown", new Object[]{name}));
        }
        return factor;
    }
}