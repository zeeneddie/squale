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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\QualityResultBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

import java.io.Serializable;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;

/**
 * Représente un résultat "qualité", donc possédant une note.
 * 
 * @author m400842
 * @version 1.0
 * @hibernate.class table="QualityResult" mutable="true" discriminator-value="QualityResult"
 * @hibernate.discriminator column="subclass"
 */
public abstract class QualityResultBO
    implements Serializable
{

    /** Constante pour indiquer que la note appartient [2,3] */
    public final static String ACCEPTED = "accepted";

    /** Constante pour indiquer que la note appartient [1,2[ */
    public final static String RESERVED = "reserved";

    /** Constante pour indiquer que la note appartient [0,1[ */
    public final static String REFUSED = "refused";

    /**
     * Note moyenne du résultat qualité
     */
    protected float mMeanMark;

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Projet sur lequel est calculé le résultat qualité.
     */
    protected ProjectBO mProject;

    /**
     * Audit durant lequel a été généré le résultat
     */
    protected AuditBO mAudit;

    /**
     * Règle de calcul du facteur
     */
    protected QualityRuleBO mRule;

    /**
     * Access method for the mRule property.
     * 
     * @return the current value of the mRule property
     * @hibernate.many-to-one column="QualityRuleId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE300E1
     */
    public QualityRuleBO getRule()
    {
        return mRule;
    }

    /**
     * Sets the value of the mRule property.
     * 
     * @param pRule the new value of the mRule property
     * @roseuid 42BACEE300E2
     */
    public void setRule( QualityRuleBO pRule )
    {
        mRule = pRule;
    }

    /**
     * Access method for the mMeanMark property.
     * 
     * @return the current value of the mMeanMark property
     * @hibernate.property name="meanMark" column="MeanMark" type="float" not-null="true" unique="false" update="true"
     *                     insert="true"
     * @roseuid 42BACEE40006
     */
    public float getMeanMark()
    {
        return mMeanMark;
    }

    /**
     * Sets the value of the mMeanMark property.
     * 
     * @param pMeanMark the new value of the mMeanMark property
     * @roseuid 42BACEE40007
     */
    public void setMeanMark( float pMeanMark )
    {
        mMeanMark = pMeanMark;
    }

    /**
     * Access method for the mProject property.
     * 
     * @return the current value of the mProject property
     * @hibernate.many-to-one column="ProjectId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE40016
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * Sets the value of the mProject property.
     * 
     * @param pProject the new value of the mProject property
     * @roseuid 42BACEE40017
     */
    public void setProject( ProjectBO pProject )
    {
        mProject = pProject;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     * @hibernate.many-to-one column="AuditId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE40019
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * Sets the value of the mAudit property.
     * 
     * @param pAudit the new value of the mAudit property
     * @roseuid 42BACEE4001A
     */
    public void setAudit( AuditBO pAudit )
    {
        mAudit = pAudit;
    }

    /**
     * Récupère l'id de l'objet.
     * 
     * @return l'id de l'objet Note: unsaved-value An identifier property value that indicates that an instance is newly
     *         instantiated (unsaved), distinguishing it from transient instances that were saved or loaded in a
     *         previous session. If not specified you will get an exception like this: another object associated with
     *         the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="QualityResultId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="qualityres_sequence"
     * @roseuid 42C91BA4004B
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Affecte l'id de l'objet.
     * 
     * @param pId l'id à affecter
     * @roseuid 42C91BA400D7
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42C91E470231
     */
    public QualityResultBO()
    {
        mId = -1;
        mMeanMark = -1;
    }

    /**
     * Constructeur complet
     * 
     * @param pMeanMark la note moyenne
     * @param pProject le projet correspondant
     * @param pAudit l'audit correspondant
     * @roseuid 42C91E4702AE
     */
    public QualityResultBO( float pMeanMark, ProjectBO pProject, AuditBO pAudit )
    {
        mId = -1;
        mMeanMark = pMeanMark;
        mProject = pProject;
        mAudit = pAudit;
    }
}
