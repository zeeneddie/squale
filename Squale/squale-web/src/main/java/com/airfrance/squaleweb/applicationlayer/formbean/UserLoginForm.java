package com.airfrance.squaleweb.applicationlayer.formbean;


/**
 * actionForm associate to the login JSP
 */
public class UserLoginForm
    extends RootForm
{

    /**
     * The login of the user
     */
    private String login;

    /**
     * The password of the user
     */
    private String pass;

    /**
     * Default constructor
     */
    public UserLoginForm()
    {

    }

    /**
     * Getter for the login
     * 
     * @return the login
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * Setter for the login
     * 
     * @param login : the login of the user
     */
    public void setLogin( String login )
    {
        this.login = login;
    }

    /**
     * Getter for the password
     * 
     * @return the password
     */
    public String getPass()
    {
        return pass;
    }

    /**
     * Setter for the password
     * 
     * @param pass : the password of the user
     */
    public void setPass( String pass )
    {
        this.pass = pass;
    }

}
