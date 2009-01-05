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
package com.airfrance.squalecommon.util.mail;

import com.airfrance.jraf.spi.provider.IProvider;

/**
 * Fournisseur de mail
 */
public interface IMailerProvider
    extends IProvider
{

    /**
     * Clé pour retrouver le provider de mail Cette clé correspond à l'id du bean de provider de mail dans le fichier de
     * configuration SPRING
     */
    public final static String MAILER_PROVIDER_KEY = "mailer";

    /**
     * Envoi de mail
     * 
     * @param pSender l'expéditeur - nul si récupéré par configuration
     * @param pRecipients les destinataires
     * @param pSubject sujet du mail
     * @param pContent contenu du mail
     * @throws MailException en cas d'erreur lors de l'envoi du mail
     */
    public void sendMail( String pSender, String[] pRecipients, String pSubject, String pContent )
        throws MailException;
}
