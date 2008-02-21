package com.airfrance.squaleweb.resources;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * Fabrique de lecture de message
 */
public class MessageResourcesFactory extends PropertyMessageResourcesFactory {

    /** 
     * @see org.apache.struts.util.MessageResourcesFactory#createResources(java.lang.String)
     * {@inheritDoc}
     */
    public MessageResources createResources(String config) {
        MessageResources mes = super.createResources(config);
        return new MessageResourcesProxy(this, config, mes);
    }

}
