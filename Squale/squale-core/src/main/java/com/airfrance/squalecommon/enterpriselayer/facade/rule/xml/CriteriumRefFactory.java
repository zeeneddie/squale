package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de références de critère
 *
 */
class CriteriumRefFactory extends FactoryAdapter {
    /** Critères */
    private Hashtable mCriteria;
    /**
     * Constructeur
     * @param pCriteria critères existants
     */
    public CriteriumRefFactory(Hashtable pCriteria) {
        mCriteria = pCriteria;
    }
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes attributes) throws Exception {
        String name = attributes.getValue("name");
        CriteriumRuleBO practice = (CriteriumRuleBO) mCriteria.get(name);
        if (practice == null) {
            // Détection d'objet inexistant
            throw new Exception(XmlRuleMessages.getString("criterium.unknown", new Object[]{name}));
        }
        return practice;
    }
}