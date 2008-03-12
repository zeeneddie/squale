package com.airfrance.squalecommon.util.mail;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.util.SqualeCommonUtils;

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
