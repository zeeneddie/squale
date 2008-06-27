package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Ensemble de règles liées à un outil et spécifique à un projet
 * 
 * @hibernate.subclass discriminator-value="ProjectRuleSet"
 */
public class ProjectRuleSetBO
    extends RuleSetBO
{

    /** Projet sur lequel s'appliquent les règles */
    private ProjectBO mProject;

    /**
     * @return le projet sur lequel s'appliquent les règles
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
     * @param pProject le projet sur lequel s'appliquent les règles
     */
    public void setProject( ProjectBO pProject )
    {
        mProject = pProject;
    }

}
