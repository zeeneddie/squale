package com.airfrance.squaleweb.applicationlayer.action.component.parameters;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;

/**
 * Action de paramétrage des paramètres de la tâche Java/Jsp PMD Une même action permet le paramétrage de la tâche PMD
 * dans un contexte java seul ou avec le contexte jsp
 */
public class CreatePmdParametersAction
    extends CreateParametersAction
{

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.component.parameters.CreateParametersAction#getTransformerParameters(com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public Object[] getTransformerParameters( CreateProjectForm pProject, HttpServletRequest pRequest )
        throws Exception
    {
        // Obtention des configurations disponibles
        IApplicationComponent acPmdAdmin = AccessDelegateHelper.getInstance( "PmdAdmin" );
        // on récupère la liste des configurations disponibles
        Collection configurationsDTO = (Collection) acPmdAdmin.execute( "getAllConfigurations" );
        Object[] params = { pProject.getParameters(), pProject.getConfigurableTask( "PmdTask" ), configurationsDTO };

        return params;
    }

}
