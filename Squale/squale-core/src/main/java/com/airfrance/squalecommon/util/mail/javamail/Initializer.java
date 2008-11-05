package com.airfrance.squalecommon.util.mail.javamail;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * Initializer for the javamail provider
 */
public class Initializer
    implements IInitializable, IInitializableBean
{

    /**
     * Constructor
     */
    public Initializer()
    {
        super();
    }

    /**
     * This method return an instance of the javamail provider
     * 
     * @param objectInitialize List of parameter (key-value)
     * @return The return an instance of JavaMailProviderImpl
     * @throws JrafConfigException Exception happened during the initialization
     */
    public IProvider initialize( Map objectInitialize )
        throws JrafConfigException
    {

        return initialize();
    }

    /**
     * This method return an instance of the javamail provider
     * 
     * @return The return an instance of JavaMailProviderImpl
     * @throws JrafConfigException Exception happened during the initialization
     */
    public IProvider initialize()
        throws JrafConfigException
    {
        IProvider mailer = new JavaMailProviderImpl();
        return mailer;
    }

    /**
     * Method used for control the good initialization
     */
    public void afterPropertiesSet()
    {

    }

}
