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
package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractGenericTaskForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * This class is a model for instance of a transformer methods of which convert object to form and vis versa.<br />
 * Instances of this class also have methods to fill or to convert to a <b>{@link ListParameterDTO}</b>. Please refer
 * to the javadoc of each method.
 */
public class AbstractGenericTaskConfTransformer
    implements WITransformer
{

    /** The separator */
    private static final String REGEX = System.getProperties().getProperty( "line.separator" );

    /** Replacement String */
    private static final String SPACE = " ";

    /**
     * Method which transforms an object from the presentation layer into an object of the application layer
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * @param pFrom an object from the presentation layer
     * @return object (or collection of) for the application layer (BusinessObject or DataTransferObject)
     * @throws WTransformerException if an error occurs while transforming
     */
    public Object[] formToObj( WActionForm pFrom )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pFrom, obj );
        return obj;
    }

    /**
     * Method which transforms an object from the presentation layer into an object of the application layer.<br />
     * Objects could then transit between layers.
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * @param pFrom an object from the presentation layer (PL)
     * @param pObject object (or collection of) for the application layer (BusinessObject or DataTransferObject)
     * @throws WTransformerException if an error occurs while transforming
     */
    @SuppressWarnings( "unchecked" )
    public void formToObj( WActionForm pFrom, Object[] pObject )
        throws WTransformerException
    {

        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];

        /* Casting the form received as parameter into a genericTaskForm */
        AbstractGenericTaskForm genericTaskForm = (AbstractGenericTaskForm) pFrom;

        /* Declaring a Map which will contain all the parameters required for the generic task */
        Map<String, Object> genericTaskParams = new HashMap<String, Object>();

        /*
         * For all object of type StringParameterDTO the process to convert from the PL to DTO is as follow :
         * 1 -> Instantiate an object of type StringParameterDTO by calling the constructor and passing in the value of the
         * field (from the form) as parameter. 
         * 
         * 2 -> Put the StringParameterDTO object into the map instantiated formerly
         * by passing in the related constant value as value for the key.
         */

        // Getting the location of the tool which has to be executed
        StringParameterDTO toolLocation = new StringParameterDTO( genericTaskForm.getToolLocation() );
        genericTaskParams.put( ParametersConstants.GENERICTASK_TOOLDIR, toolLocation );

        // Getting the working directory
        StringParameterDTO workingDir = new StringParameterDTO( genericTaskForm.getWorkingDirectory() );
        genericTaskParams.put( ParametersConstants.GENERICTASK_WORKDIR, workingDir );

        // Getting the arguments/commands see method bellow
        ListParameterDTO commands = new ListParameterDTO();
        fillListFromArray( commands, genericTaskForm.getCommands() );
        genericTaskParams.put( ParametersConstants.GENERICTASK_COMMANDS, commands );

        // Getting the result files location see method bellow
        ListParameterDTO resultsLocation = new ListParameterDTO();
        /* Modif temp en attente de résolution */
        resultsLocation.getParameters().add( new StringParameterDTO( genericTaskForm.getResultsLocation()[1] ) );
        // fillListFromArray( resultsLocation, genericTaskForm.getResultsLocation() );
        genericTaskParams.put( ParametersConstants.GENERICTASK_RESULTSDIR, resultsLocation );

        // Finalising the map for the task
        MapParameterDTO genericTaskMap = new MapParameterDTO();
        genericTaskMap.setParameters( genericTaskParams );
        projectParams.getParameters().put( ParametersConstants.GENERICTASK, genericTaskMap );

    }

    /**
     * Method which transforms an object from the application layer into an object of the presentation layer
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * @param pObject object (or collection of) from the application layer (BusinessObject or DataTransferObject)
     * @return a form containing all informations of the object (presentation layer)
     * @throws WTransformerException if any error occurs while transforming
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        AbstractGenericTaskForm genericTaskForm = new AbstractGenericTaskForm();
        objToForm( pObject, genericTaskForm );
        return genericTaskForm;
    }

    /**
     * Method which transforms an object from the application layer into an object of the presentation layer
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm)
     * @param pObject object (or collection of) from the application layer (BusinessObject or DataTransferObject)
     * @param pForm a form containing all informations of the object (presentation layer)
     * @throws WTransformerException if any error occurs while transforming
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {

        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];

        // Retrieves params of the task
        MapParameterDTO params = (MapParameterDTO) projectParams.getParameters().get( ParametersConstants.GENERICTASK );

        // Checking if there are some parameters
        if ( null != params )
        {
            // Filling the form and casting it into GenericTaskForm type
            AbstractGenericTaskForm genericTaskForm = (AbstractGenericTaskForm) pForm;

            // Creating a map to retrieve the constants
            Map constantsMap = params.getParameters();

            // Getting the location of the tool which has to be executed
            StringParameterDTO toolLocation =
                (StringParameterDTO) constantsMap.get( ParametersConstants.GENERICTASK_TOOLDIR );
            genericTaskForm.setToolLocation( toolLocation.getValue() );

            // Getting the working directory
            StringParameterDTO workingDir =
                (StringParameterDTO) constantsMap.get( ParametersConstants.GENERICTASK_WORKDIR );
            genericTaskForm.setWorkingDirectory( workingDir.getValue() );

            // Getting the commands
            ListParameterDTO argsDTO = (ListParameterDTO) constantsMap.get( ParametersConstants.GENERICTASK_COMMANDS );
            if ( null != argsDTO )
            {
                genericTaskForm.setCommands( convertFromListToArray( argsDTO ) );
            }

            // Getting the ResultsLocation
            ListParameterDTO resultsLocationDTO =
                (ListParameterDTO) constantsMap.get( ParametersConstants.GENERICTASK_RESULTSDIR );
            if ( null != resultsLocationDTO )
            {
                genericTaskForm.setResultsLocation( convertFromListToArray( resultsLocationDTO ) );
            }
        }
    }

    /**
     * This method fills in a list (a collection of the application layer) with an array from the presentation layer.
     * 
     * @param pList The list that has to be filled (DTO)
     * @param pArray The resultsLocation array which represents the user inputs (PL)
     */
    private void fillListFromArray( ListParameterDTO pList, String[] pArray )
    {
        List<StringParameterDTO> listDTO = new ArrayList<StringParameterDTO>();
        for ( int i = 0; i < pArray.length; i++ )
        {
            StringParameterDTO strParamSource = new StringParameterDTO();
            strParamSource.setValue( pArray[i] );
            listDTO.add( strParamSource );
        }
        pList.setParameters( listDTO );
    }

    /**
     * <p>
     * This method converts a list (DTO Layer) into an array for the presentation layer. So as to factorize the methods
     * of this class any arrays of the presentation layer linked to an AbstractGenericTask are treated here. As some
     * informations are inputed by the user in a text-areas it could be useful to check if any CR or LF are present in
     * the array and to replace them by space(s) if needed.
     * </p>
     * 
     * @param pList The list of parameters which has to be converted (DTO)
     * @return The user informations (location(s) of the results/commands line parameters) in an array
     */

    // WORKAROUND : Works good but in case of double CR in the JSP, will replace by double space !
    private String[] convertFromListToArray( ListParameterDTO pList )
    {
        /* Getting the StringParameter of the passed in ListParameterDTO */
        List<StringParameterDTO> list = pList.getParameters();

        /* Array that will store the values */
        String[] resultsArray = new String[list.size()];

        /* The CR and LF are system dependent, getting the property instead of hard-coding it */
        /* Converting the constant into a pattern */
        Pattern cariageReturn = Pattern.compile( REGEX );

        /* Iterating the list to get the values */
        for ( int i = 0; i < list.size(); i++ )
        {
            StringParameterDTO result = (StringParameterDTO) list.get( i );
            /* if result is not null */
            if ( null != result )
            {
                /* Getting the value of it */
                String tmp = result.getValue();
                /* Instance of Matcher that will be used */
                Matcher inputMatcher = cariageReturn.matcher( tmp );
                /* If there are any carriage return or line feed */
                if ( inputMatcher.find() )
                {
                    /* Replacing by space(s) */
                    String tmpReplaced = inputMatcher.replaceAll( SPACE );
                    /* Storing in the array */
                    resultsArray[i] = tmpReplaced;
                }
                else
                {
                    /* Storing the String as is */
                    resultsArray[i] = tmp;
                }
            }
        }
        return resultsArray;
    }
}
