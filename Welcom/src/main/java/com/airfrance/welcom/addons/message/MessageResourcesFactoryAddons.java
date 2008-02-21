package com.airfrance.welcom.addons.message;

import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * @author Dirk Bartkowiak
 * @created Jul 21, 2003
 * @version
 *
 * MessageResourcesFactory.java
 *
 */
public class MessageResourcesFactoryAddons extends PropertyMessageResourcesFactory {

    /**
     * 
     */
    private static final long serialVersionUID = 7092695078186643904L;

    /**
     * @see org.apache.struts.util.MessageResourcesFactory#createResources(String)
     *
     * Factory to provide access to MessageResource implementation
     */
    public org.apache.struts.util.MessageResources createResources(final String configuration) {
        return new MessageResourcesAddons(this, configuration, getReturnNull());
    }

}