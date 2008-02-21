package com.airfrance.squalecommon.util.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 * Résolution des DTD
 * Les DTD sont le plus souvent embarquées dans les jars,
 * cette classe mémorise l'association entre un id public de DTD
 * et le nom de la ressource
 */
public class XmlResolver implements EntityResolver {
    /** Identificateur public */
    private String mPublicId;
    /** Localisation de la dtd */
    private String mLocation;

    /**
     * Constructeur
     * @param pPublicId identification publique
     * @param pLocation ressource correspondante
     */
    public XmlResolver(String pPublicId, String pLocation) {
        mPublicId = pPublicId;
        mLocation = pLocation;
    }

    /**
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity(String pPublicId, String pSystemId) throws SAXException, IOException {
        if (pPublicId.equals(mPublicId)) {
            InputStream stream = getClass().getResourceAsStream(mLocation);
            if (stream == null) {
                // On indique que la DTD est introuvable
                throw new SAXException(CommonMessages.getString("xml.dtd.missing", new Object[] { mLocation }));
            }
            InputSource result = new InputSource(stream);
            result.setPublicId(pPublicId);
            result.setSystemId(pSystemId);
            return result;
        } else {
            // On refuse toute autre DTD que celle enregistrée
            throw new SAXException(CommonMessages.getString("xml.dtd.unexpected", new Object[] { pPublicId, mPublicId }));
        }
    }

}
