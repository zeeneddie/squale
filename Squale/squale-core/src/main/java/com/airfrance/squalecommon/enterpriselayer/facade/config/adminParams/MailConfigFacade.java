package com.airfrance.squalecommon.enterpriselayer.facade.config.adminParams;

import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.config.AdminParamsDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import com.airfrance.squalecommon.enterpriselayer.facade.FacadeMessages;
import com.airfrance.squalecommon.util.mail.MailException;

/**
 * Facade for the mail configuration
 */
public final class MailConfigFacade
    implements IFacade
{
    /**
     * Persistent provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Private constructor
     */
    private MailConfigFacade()
    {

    }

    /**
     * This method give the Squale's administrator mailing list
     * 
     * @return The Squale's administrator mailing list
     * @throws JrafDaoException Exception happened during the search
     * @throws MailException Problem in mail configuration
     */
    public static String getAdminMailingList()
        throws JrafDaoException, MailException
    {
        String mailingList = "";
        AdminParamsDAOImpl dao = AdminParamsDAOImpl.getInstance();
        ISession session = PERSISTENTPROVIDER.getSession();
        List<AdminParamsBO> adminParamsBOCollection = dao.findByKey( session, AdminParamsBO.MAIL_ADMIN_MAILING_LIST );
        if ( adminParamsBOCollection.size() > 1 )
        {
            String message = FacadeMessages.getString( "facade.exception.mail.manyMatch" );
            throw new MailException( message );
        }
        else if (adminParamsBOCollection.size() == 1)
        {
            mailingList = adminParamsBOCollection.get( 0 ).getParamValue();
        }
        return mailingList;
    }
}
