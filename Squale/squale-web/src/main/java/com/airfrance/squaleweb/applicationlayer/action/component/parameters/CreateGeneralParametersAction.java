package com.airfrance.squaleweb.applicationlayer.action.component.parameters;

import javax.servlet.http.HttpServletRequest;

import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;

/**
 * Configuration des paramètres généraux d'un projet
 */
public class CreateGeneralParametersAction
    extends CreateParametersAction
{
    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.component.parameters.CreateParametersAction#getTransformerParameters(com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public Object[] getTransformerParameters( CreateProjectForm pProject, HttpServletRequest pRequest )
        throws Exception
    {
        return new Object[] { pProject.getParameters(), pProject.getProfile() };
    }

}
