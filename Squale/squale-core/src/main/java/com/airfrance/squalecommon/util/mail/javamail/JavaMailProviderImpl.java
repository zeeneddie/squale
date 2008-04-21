package com.airfrance.squalecommon.util.mail.javamail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.mail.IMailerProvider;
import com.airfrance.squalecommon.util.mail.MailException;

public class JavaMailProviderImpl
    implements IMailerProvider
{

    /** logger */
    private static final Log LOG = LogFactory.getLog( JavaMailProviderImpl.class );

    /**
     * SMTP server to use for sent mail.
     */
    private static String smtpServer;

    /**
     * Default sender.
     */
    private static String defaultSender;

    static
    {
        try
        {
            Properties props = new Properties();
            InputStream is = JavaMailProviderImpl.class.getClassLoader().getResourceAsStream( "mail.properties" );
            props.load( is );
            smtpServer = props.getProperty( "smtpServer" );
            defaultSender = props.getProperty( "defaultSender" );
        }
        catch ( IOException e )
        {
            LOG.error( "Mailer config error" );
        }
    }

    /**
     * Constructor
     */
    protected JavaMailProviderImpl()
    {
    }

    public void sendMail( String sender, String[] recipients, String subject, String content )
        throws MailException
    {

        // Properties used for sending mails
        Properties props = System.getProperties();
        props.put( "mail.smtp.host", smtpServer );
        LOG.info( "SMTP server : " + smtpServer );

        // Session recovering
        Session session = Session.getDefaultInstance( props, null );

        // Header message definition
        MimeMessage message = new MimeMessage( session );
        try
        {
            if ( sender != null )
            {
                message.setFrom( new InternetAddress( sender ) );
            }
            else
            {
                message.setFrom( new InternetAddress( defaultSender ) );
            }

            for ( int i = 0; i <= recipients.length - 1; i++ )
            {
                String mailAdress = (String) recipients[i];
                LOG.info( "Recipent : " + recipients[i] );
                message.addRecipient( Message.RecipientType.BCC, new InternetAddress( mailAdress ) );
            }

            // Body message definition
            message.setSubject( subject );
            message.setText( content );

            // Send of the mail
            Transport.send( message );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            LOG.error( e.getMessage() );
        }
    }
}
