package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.AbstractGenericTaskConfTransformer;

/**
 * This class instantiate a form-bean which stores data from a submitted client request. This form-bean is directly
 * connected to the GenericTask.
 */
public class AbstractGenericTaskForm
    extends AbstractParameterForm
{

    /**
     * This String stores the arguments and parameters the user has inputed in the JSP (presentation layer)
     */
    private String[] commands;

    /**
     * This String stores the location of the tool
     */
    private String toolLocation;

    /**
     * This String stores the location the results file are going to be saved
     */
    private String[] resultsLocation;
    
    /**
     * This String stores the working directory
     */
    private String workingDirectory;

    /**
     * This String stores any former configuration saved in the database and selected by the user
     */
    private String selectedGenericTask;

    /**
     * Public default constructor
     */
    public AbstractGenericTaskForm()
    {
        commands = new String[0];
        toolLocation = "";
        resultsLocation = new String[0];
        workingDirectory = "";
    }

    /**
     * This method return the default bean name stored in session {@inheritDoc}
     * 
     * @return name of the bean <b>stored in session</b>
     */
    @Override
    public String getNameInSession()
    {
        return "genericTaskForm";
    }

    /**
     * This method retrieves the constant parameters of the generic task {@inheritDoc}
     * 
     * @return the name of the generic task stored in a class of constants
     */
    @Override
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.GENERICTASK };
    }

    /**
     * Getter for the name of the task {@inheritDoc}
     * 
     * @return the name of the task
     */
    @Override
    public String getTaskName()
    {
        return "GenericTask";
    }

    /**
     * Getter of the transformer which will be used to convert from an object to a form and vis versa {@inheritDoc}
     * 
     * @return the class used to proceed to the transformation
     */
    @Override
    public Class<AbstractGenericTaskConfTransformer> getTransformer()
    {
        return AbstractGenericTaskConfTransformer.class;
    }

    /**
     * Resets the instance of our formBean
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setCommands( new String[0] );
        setToolLocation( "" );
        setResultsLocation( new String[0] );
        setWorkingDirectory( "" );
    }

    /**
     * This method is used to verify required fields and validate the form {@inheritDoc}
     * 
     * @param mapping
     * @param request
     */
    @Override
    protected void validateConf( ActionMapping mapping, HttpServletRequest request )
    {
        /* Validation is done in task inheriting from this GenericTaskForm */
    }

    /*--------------------------------------- ACCESSORS ---------------------------------*/
    /**
     * Getter of the commands
     * 
     * @return a String containing the user inputs (commands and arguments)
     */
    public String[] getCommands()
    {
        return commands;
    }

    /**
     * Setter of the commands
     * 
     * @param args : the input of the user
     */
    public void setCommands( String[] args )
    {
        this.commands = args;
    }

    /**
     * Getter of the tooLocation
     * 
     * @return a String containing the location of the tool
     */
    public String getToolLocation()
    {
        return toolLocation;
    }

    /**
     * Setter of the toolLocation
     * 
     * @param toolPath : Location where the tool is stored
     */
    public void setToolLocation( String toolPath )
    {
        this.toolLocation = toolPath;
    }

    /**
     * Getter of the resultsLocation
     * 
     * @return an array of String : location from which the result files are going to be retrieved
     */
    public String[] getResultsLocation()
    {
        return resultsLocation;
    }

    /**
     * Setter of the resultsLocation
     * 
     * @param resultsPath : String containing the result files location
     */
    public void setResultsLocation( String[] resultsPath )
    {
        this.resultsLocation = resultsPath;
    }

    /**
     * Getter of the working directory in which the tool will be executed
     * 
     * @return the working directory
     */
    public String getWorkingDirectory()
    {
        return workingDirectory;
    }

    /**
     * Setter of the working directory in which the tool will be executed
     * 
     * @param workingDir : String containing the path to the working directory
     */
    public void setWorkingDirectory( String workingDir )
    {
        this.workingDirectory = workingDir;
    }

    /**
     * Getter of the selected GenericTask if any
     * 
     * @return the selected genericTool if any
     */
    public String getSelectedGenericTask()
    {
        return selectedGenericTask;
    }

    /**
     * Setter of the selected GenericTask
     * 
     * @param pSelectedGenericTask the generic task which is selected
     */
    public void setSelectedGenericTask( String pSelectedGenericTask )
    {
        this.selectedGenericTask = pSelectedGenericTask;
    }

}
