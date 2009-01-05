/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
