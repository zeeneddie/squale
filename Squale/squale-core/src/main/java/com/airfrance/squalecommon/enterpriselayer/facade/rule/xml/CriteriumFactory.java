package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de critère
 */
class CriteriumFactory extends FactoryAdapter {
    /** Critères */
    private Hashtable mCriteria;
    
    /**
     * Constructeur
     *
     */
    public CriteriumFactory() {
        mCriteria = new Hashtable();
    }
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes attributes) throws Exception {
        String name = attributes.getValue("name");
        CriteriumRuleBO practice = (CriteriumRuleBO) mCriteria.get(name);
        if (practice == null) {
            practice = new CriteriumRuleBO();
            practice.setName(name);
            mCriteria.put(name, practice);
        } else {
            // Détection d'objet dupliqué
            throw new Exception(XmlRuleMessages.getString("criterium.duplicate", new Object[]{name}));
        }
        return practice;
    }
    /**
     * @return critères
     */
    public Hashtable getCriteria() {
        return mCriteria;
    }

}