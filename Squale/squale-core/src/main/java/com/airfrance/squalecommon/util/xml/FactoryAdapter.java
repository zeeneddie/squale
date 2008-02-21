package com.airfrance.squalecommon.util.xml;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;

/**
 * Adaptateur de fabrique pour le digester
 * Cette classe est utilisée pour éviter la duplication de code dans les diverses
 * factory utilisées par le digester
 */
public abstract class FactoryAdapter implements ObjectCreationFactory {
    /** Digester */
    private Digester mDigester;

    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#getDigester()
     */
    public Digester getDigester() {
        return mDigester;
    }

    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#setDigester(org.apache.commons.digester.Digester)
     */
    public void setDigester(Digester pDigester) {
        mDigester = pDigester;
    }
}