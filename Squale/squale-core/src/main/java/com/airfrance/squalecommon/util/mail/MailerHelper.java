package com.airfrance.squalecommon.util.mail;

import com.airfrance.jraf.bootstrap.locator.ProviderLocator;
import com.airfrance.jraf.bootstrap.locator.SpringLocator;

/**
 * Accès au provider de mail
 *
 */
public class MailerHelper {

    /**
     * Retourne un provider d'envoi de mail.
     * @return provider de mail
     */
    public final static IMailerProvider getMailerProvider() {
        IMailerProvider mailer = null;
        // Pour SPRING
        SpringLocator springLocator = SpringLocator.getInstance();
        if(springLocator != null) {
            mailer = (IMailerProvider) springLocator.getBean(IMailerProvider.MAILER_PROVIDER_KEY);
        } else {
            mailer = (IMailerProvider) ProviderLocator.getProvider(IMailerProvider.MAILER_PROVIDER_KEY);
        }
        return mailer;
    }
}
