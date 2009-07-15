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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\mccabe\\McCabePersistor.java

package com.airfrance.squalix.tools.mccabe;

import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.core.TaskData;

/**
 * Objet chargé de faire persister les composants identifiés par McCabe ainsi que les résultats.
 */
public class McCabePersistor
{
    /**
     * Session Persistance
     */
    protected ISession mSession;

    /** les paramètres temporaires */
    protected TaskData mDatas;

    /** le nom de la tache réel, pour différencier java et cpp */
    protected String mTaskName;

    /**
     * Audit durant lequel l'analyse est effectuée
     */
    protected AuditBO mAudit;

    /**
     * Configuration
     */
    protected McCabeConfiguration mConfiguration;

    /**
     * Projet sur lequel est réalisée l'analyse.
     */
    protected ProjectBO mProject;

    /**
     * Constructeur.
     * @param pSession la session de persistance utilisée par la tâche.
     * @param pDatas la liste des paramètres temporaires du projet
     * @param pTaskName le nom de la tache (pour différencier java et cpp)
     * @param pAudit audit encadrant l'exécution.
     * @param pConfiguration configuration du framework.
     */
    public McCabePersistor(final ISession pSession, final TaskData pDatas, final String pTaskName,
                           final AuditBO pAudit, final McCabeConfiguration pConfiguration) {
        mSession = pSession;
        mDatas = pDatas;
        mTaskName = pTaskName;
        mAudit = pAudit;
        mConfiguration = pConfiguration;
        mProject = pConfiguration.getProject();
    }
    
}
