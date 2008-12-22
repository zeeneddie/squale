package com.airfrance.squaleweb.homepage;

/**
 * This class represent a line of statistics
 */
public class Stat
{

    /** The name of the application */
    private String applicationName;

    /** The name of the project */
    private String projectName;

    /** Number of line code */
    private String nbLineCode;

    /** Number of comments line */
    private String nbLineComments;

    /** Number of methods */
    private String nbMethods;

    /** number of classes */
    private String nbClasses;

    /**
     * Default constructor
     */
    public Stat()
    {

    }

    /**
     * Complete constructor
     * 
     * @param mApplicationName The name of the application
     * @param mProjectName The name of the project
     * @param mNbLigneCode The number of code lines
     * @param mNbLigneCommentaire The number of comments line
     * @param mNbMethods The number of methods
     * @param mNbClasses The number of classes
     */
    public Stat( String mApplicationName, String mProjectName, String mNbLigneCode, String mNbLigneCommentaire,
                 String mNbMethods, String mNbClasses )
    {
        applicationName = mApplicationName;
        projectName = mProjectName;
        nbLineCode = mNbLigneCode;
        nbLineComments = mNbLigneCommentaire;
        nbMethods = mNbMethods;
        nbClasses = mNbClasses;
    }

    /**
     * Getter method for the attribute applicationName
     * 
     * @return the application name
     */
    public String getApplicationName()
    {
        return applicationName;
    }

    /**
     * Setter methods for the attribute applicationName
     * 
     * @param mApplicationName The new application name
     */
    public void setApplicationName( String mApplicationName )
    {
        applicationName = mApplicationName;
    }

    /**
     * Getter method for the attribute projectName
     * 
     * @return the project name
     */
    public String getProjectName()
    {
        return projectName;
    }

    /**
     * Setter methods for the attribute projectName
     * 
     * @param mProjectName The new project name
     */
    public void setProjectName( String mProjectName )
    {
        projectName = mProjectName;
    }

    /**
     * Getter method for the attribute nbLineCode
     * 
     * @return The number of lines of code
     */
    public String getNbLineCode()
    {
        return nbLineCode;
    }

    /**
     * Setter methods for the attribute nbLineCode
     * 
     * @param mNbLineCode The new number of lines of code
     */
    public void setNbLineCode( String mNbLineCode )
    {
        nbLineCode = mNbLineCode;
    }

    /**
     * Getter method for the attribute nbLineComments
     * 
     * @return The number of lines of comments
     */
    public String getNbLineComments()
    {
        return nbLineComments;
    }

    /**
     * Setter methods for the attribute nbLineComments
     * 
     * @param mNbLineComments The new number of lines of comments
     */
    public void setNbLineComments( String mNbLineComments )
    {
        nbLineComments = mNbLineComments;
    }

    /**
     * Getter method for the attribute nbMethods
     * 
     * @return The number of methods
     */
    public String getNbMethods()
    {
        return nbMethods;
    }

    /**
     * Setter methods for the attribute nbMethods
     * 
     * @param mNbMethods the new number of methods
     */
    public void setNbMethods( String mNbMethods )
    {
        nbMethods = mNbMethods;
    }

    /**
     * Getter method for the attribute nbClasses
     * 
     * @return The number of classes
     */
    public String getNbClasses()
    {
        return nbClasses;
    }

    /**
     * Setter methods for the attribute nbClasses
     * 
     * @param mNbClasses the new number of classes
     */
    public void setNbClasses( String mNbClasses )
    {
        nbClasses = mNbClasses;
    }

}
