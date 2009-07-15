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
package org.squale.squalecommon.util.mail;

import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.bootstrap.locator.SpringLocator;

/**
 * Accès au provider de mail
 */
public class MailerHelper
{

    /**
     * Retourne un provider d'envoi de mail.
     * 
     * @return provider de mail
     */
    public final static IMailerProvider getMailerProvider()
    {
        IMailerProvider mailer = null;
        // Pour SPRING
        SpringLocator springLocator = SpringLocator.getInstance();
        if ( springLocator != null )
        {
            mailer = (IMailerProvider) springLocator.getBean( IMailerProvider.MAILER_PROVIDER_KEY );
        }
        else
        {
            mailer = (IMailerProvider) ProviderLocator.getProvider( IMailerProvider.MAILER_PROVIDER_KEY );
        }
        return mailer;
    }
}
