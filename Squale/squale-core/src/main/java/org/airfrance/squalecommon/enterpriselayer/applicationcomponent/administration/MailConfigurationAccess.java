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
