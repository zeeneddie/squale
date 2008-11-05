package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.enterpriselayer.facade.config.adminParams.MailConfigFacade;
import com.airfrance.squalecommon.util.mail.MailException;

/**
 * Access component for mail configuration information
 *
 */
public class MailConfigurationAccess
    extends DefaultExecuteComponent
{

    /**
     * Give the Squale's administrator mailing list
     * 
     * @return the Squale's administrator mailing list
     * @throws JrafDaoException Exception happened during the search
     * @throws MailException Mail configuration problem
     */
    public String getAdminMailingList()
        throws JrafDaoException, MailException
    {
        return MailConfigFacade.getAdminMailingList();
    }

}
