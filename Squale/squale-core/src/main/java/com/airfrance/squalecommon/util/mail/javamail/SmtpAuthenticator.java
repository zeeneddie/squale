package com.airfrance.squalecommon.util.mail.javamail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * This give PasswordAuthentication needed for the SMTPs authentication 
 */
class SmtpAuthenticator
    extends Authenticator
{

    /**
     * The user name for the SMTP authentication
     */
    private String username;

    /**
     * The password for the smtp authentication
     */
    private String password;

    /**
     * Constructor
     * 
     * @param pUsername The user name for the SMTP authentication
     * @param pPassword The password for the SMTP authentication
     */
    public SmtpAuthenticator( String pUsername, String pPassword )
    {
        username = pUsername;
        password = pPassword;
    }

    /**
     * This method return an object PasswordAuthencication for the smtp authentication
     * 
     * @return a PasswordAuthencication object
     */
    protected PasswordAuthentication getPasswordAuthentication()
    {

        return new PasswordAuthentication( username, password );
    }
};