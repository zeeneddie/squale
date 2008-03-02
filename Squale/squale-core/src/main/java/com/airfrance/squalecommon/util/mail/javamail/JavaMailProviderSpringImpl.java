package com.airfrance.squalecommon.util.mail.javamail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class JavaMailProviderSpringImpl
    extends JavaMailProviderImpl
    implements ApplicationContextAware
{

    /** logger */
    private static final Log LOG = LogFactory.getLog( JavaMailProviderSpringImpl.class );

    /** Spring context */
    private ApplicationContext applicationContext = null;

    /**
     * @return the spring context
     */
    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * @param context the spring context
     */
    public void setApplicationContext( ApplicationContext context )
    {
        applicationContext = context;
    }

}
