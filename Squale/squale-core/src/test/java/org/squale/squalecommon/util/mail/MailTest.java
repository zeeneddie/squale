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

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.util.SqualeCommonUtils;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class MailTest
    extends SqualeTestCase
{

    /**
     * Test d'envoi de mail
     */
    public void testMail()
    {
        // Obtention du mailer
        IMailerProvider mail = MailerHelper.getMailerProvider();
        // Vérification de la présence du mail
        assertNotNull( "Vérifier le fichier de configuration providers-config.xml", mail );
        // Test d'envoi de mail avec une adresse en dur
        String title = "Test unitaire de mail";
        String content = "Test unitaire de mail";
        SqualeCommonUtils.notifyByEmail( mail, "bfranchet@qualixo.com", null, title, content, false );
        // avec un expéditeur
        SqualeCommonUtils.notifyByEmail( mail, "bfranchet@qualixo.com", null, title + "_expediteur", content, false );
    }

}
