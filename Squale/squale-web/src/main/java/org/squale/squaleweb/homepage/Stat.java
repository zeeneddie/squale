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
package org.squale.squaleweb.homepage;

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
