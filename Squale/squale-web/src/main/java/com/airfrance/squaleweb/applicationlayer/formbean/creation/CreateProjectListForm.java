package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400841
 */
public class CreateProjectListForm
    extends RootForm
{

    /**
     * Liste de CreateProjectForm
     */
    private List mProjectList = new ArrayList();

    /**
     * objet CreateProjectForm courant en cours de manipulation
     */
    private CreateProjectForm mCurrentProject = null;

    /**
     * @return projet courant
     */
    public CreateProjectForm getCurrentProject()
    {
        return mCurrentProject;
    }

    /**
     * @return liste des projets relatifs à une application
     */
    public List getProjectList()
    {
        return mProjectList;
    }

    /**
     * @param pCurrentProject projet courant
     */
    public void setCurrentProject( CreateProjectForm pCurrentProject )
    {
        mCurrentProject = pCurrentProject;
    }

    /**
     * @param pProjectList liste des projets relatifs a une application
     */
    public void setProjectList( List pProjectList )
    {
        mProjectList = pProjectList;
    }

}
