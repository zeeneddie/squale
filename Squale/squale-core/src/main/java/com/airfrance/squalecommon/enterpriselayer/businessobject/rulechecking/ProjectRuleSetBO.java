package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Ensemble de r�gles li�es � un outil et sp�cifique � un projet
 * 
 * @hibernate.subclass discriminator-value="ProjectRuleSet"
 */
public class ProjectRuleSetBO
    extends RuleSetBO
{

    /** Projet sur lequel s'appliquent les r�gles */
    private ProjectBO mProject;

    /**
     * @return le projet sur lequel s'appliquent les r�gles
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                        column="ProjectId" cascade="all" outer-join="auto" update="true" insert="true"
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * Modifie mProject
     * 
     * @param pProject le projet sur lequel s'appliquent les r�gles
     */
    public void setProject( ProjectBO pProject )
    {
        mProject = pProject;
    }

}
