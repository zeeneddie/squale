package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;;


/**
 * Fabrique de profile
 *
 */
public class ProfileFactory extends FactoryAdapter {
    
    /** Les noms des profiles */
    private Hashtable mProfiles;
    
    /**
     * Constructeur
     *
     */
    public ProfileFactory() {
        mProfiles = new Hashtable();
    }
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes attributes) throws Exception {
        String profileName = attributes.getValue("name");
        ProjectProfileBO profile = (ProjectProfileBO) mProfiles.get(profileName);
        if (null == profile) {
            profile = new ProjectProfileBO();
            profile.setName(profileName);
            mProfiles.put(profileName, profile);
        }else {
            throw new Exception(XmlConfigMessages.getString("profile.duplicate", new Object[]{profileName}));
        }
        return profile;
    }
    
    /**
     * @return la liste des différents profiles
     */
    public Hashtable getProfiles() {
        return mProfiles;
    }

}