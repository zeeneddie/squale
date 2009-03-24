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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Objet métier contenant les sous-projets sous référentiel
 * 
 * @author m400842 (by rose)
 * @version 1.0
 * @hibernate.class table="SqualeReference" mutable="true"
 */
public class SqualeReferenceBO
    implements Serializable
{
    /**
     * Grille qualité applicables pour ce sous-projet.
     */
    private QualityGridBO mQualityGrid;

    /** le type de l'audit */
    private String mAuditType;

    /**
     * Access method for the mQualityGrid property.
     * 
     * @return the current value of the mQualityRules property
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO"
     *                        column="QualityGrid" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public QualityGridBO getQualityGrid()
    {
        return mQualityGrid;
    }

    /**
     * Sets the value of the mQualityGrid property.
     * 
     * @param pQualityGrid the new value of the mQualityGrid property
     */
    public void setQualityGrid( QualityGridBO pQualityGrid )
    {
        mQualityGrid = pQualityGrid;
    }

    /**
     * Facteurs
     */
    private SortedMap mFactors = new TreeMap();

    /**
     * Nomber de ligne de code
     */
    private int mCodeLineNumber;

    /**
     * Nombre de méthodes.
     */
    private int mMethodNumber;

    /**
     * Nombre de classes
     */
    private int mClassNumber;

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * <code>true</code> si cette référence à été validée par l'administrateur du portail.
     */
    private boolean mHidden;

    /**
     * Le nom du projet.
     */
    private String mApplicationName;

    /**
     * Le nom sous-projet.
     */
    private String mProjectName;

    /**
     * Le langage du sous projet qui est relatif à la technologie (Java,J2EE)
     */
    private String mLanguage;
    
    /**
     * Le langage de programmation (JAVA,CPP,COBOL)
     */
    private String mProgrammingLanguage;

    /**
     * La version du sous-projet.
     */
    private String mVersion;

    /**
     * Jour où les données ont été générées.
     */
    private Date mDate;

    /**
     * Projet public
     */
    private boolean mPublic;

    /**
     * Récupère l'attribut mPublic
     * 
     * @return projet est-il public
     * @hibernate.property name="public" column="PublicApplication" type="boolean" unique="false" update="true"
     *                     insert="true"
     */
    public boolean getPublic()
    {
        return mPublic;
    }

    /**
     * Affecte pPublic à l'attribut mPublic.
     * 
     * @param pPublic projet est-il publique
     * @roseuid 42CE36C203DF
     */
    public void setPublic( boolean pPublic )
    {
        mPublic = pPublic;
    }

    /**
     * Access method for the mApplicationName property.
     * 
     * @return the current value of the mApplicationName property
     * @hibernate.property name="projectName" column="ApplicationName" type="string" update="true" insert="true"
     * @roseuid 42CA31D902B7
     */
    public String getApplicationName()
    {
        return mApplicationName;
    }

    /**
     * Sets the value of the mApplicationName property.
     * 
     * @param pApplicationName the new value of the mApplicationName property
     * @roseuid 42CA31D902C6
     */
    public void setApplicationName( String pApplicationName )
    {
        mApplicationName = pApplicationName;
    }

    /**
     * Access method for the mProjectName property.
     * 
     * @return the current value of the mProjectName property
     * @hibernate.property name="projectName" column="ProjectName" type="string" update="true" insert="true"
     * @roseuid 42CA31D90324
     */
    public String getProjectName()
    {
        return mProjectName;
    }

    /**
     * Sets the value of the mProjectName property.
     * 
     * @param pProjectName the new value of the mProjectName property
     * @roseuid 42CA31D90372
     */
    public void setProjectName( String pProjectName )
    {
        mProjectName = pProjectName;
    }

    /**
     * Access method for the mLanguage property.
     * 
     * @return the current value of the mLanguage property
     * @hibernate.property name="language" column="ProjectLanguage" type="string" update="true" insert="true"
     * @roseuid 42CA31DA0046
     */
    public String getLanguage()
    {
        return mLanguage;
    }

    /**
     * Sets the value of the mLanguage property.
     * 
     * @param pLanguage the new value of the mLanguage property
     * @roseuid 42CA31DA0055
     */
    public void setLanguage( String pLanguage )
    {
        mLanguage = pLanguage;
    }
    
    /**
     * Access method for the mProgrammingLanguage property
     * @return le langage de programmation
     */
    public String getProgrammingLanguage()
    {
    	return mProgrammingLanguage;
    }
    
    /**
     * Sets the value of the pProgrammingLanguage property
     * @param pProgrammingLanguage the new value for mProgrammingLanguage property
     */
    public void setProgrammingLanguage( String pProgrammingLanguage)
    {
    	mProgrammingLanguage = pProgrammingLanguage;
    }
    

    /**
     * Access method for the mVersion property.
     * 
     * @return the current value of the mVersion property
     * @hibernate.property name="version" column="Version" type="string" update="true" insert="true"
     * @roseuid 42CA31DA0130
     */
    public String getVersion()
    {
        return mVersion;
    }

    /**
     * Sets the value of the mVersion property.
     * 
     * @param pVersion the new value of the mVersion property
     * @roseuid 42CA31DA018E
     */
    public void setVersion( String pVersion )
    {
        mVersion = pVersion;
    }

    /**
     * Access method for the mDate property.
     * 
     * @return the current value of the mDate property
     * @hibernate.property name="date" column="AuditDate" type="timestamp" update="true" insert="true"
     * @roseuid 42CA31DA0298
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * Sets the value of the mDate property.
     * 
     * @param pDate the new value of the mDate property
     * @roseuid 42CA31DA02E6
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

    /**
     * Access method for the Factors property.
     * 
     * @return the current value of the mFactors property
     * @hibernate.map table="FactorRef" lazy="false" cascade="all" sort="natural"
     * @hibernate.index-many-to-many column="Rule"
     *                               class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO"
     * @hibernate.key column="ReferencielId"
     * @hibernate.element column="Factor_Value" type="float" not-null="false" unique="false"
     */
    public SortedMap getFactors()
    {
        return mFactors;
    }

    /**
     * Sets the value of the mFactors property.
     * 
     * @param pFactors the new value of the mFactors property
     */
    public void setFactors( SortedMap pFactors )
    {
        mFactors = pFactors;
    }

    /**
     * Access method for the mCodeLineNumber property.
     * 
     * @return the current value of the mCodeLineNumber property
     * @hibernate.property name="codeLineNumber" column="CodeLineNumber" type="integer" length="10" not-null="false"
     *                     unique="false" update="true" insert="true"
     * @roseuid 42CA31DE00C3
     */
    public int getCodeLineNumber()
    {
        return mCodeLineNumber;
    }

    /**
     * Sets the value of the mCodeLineNumber property.
     * 
     * @param pCodeLineNumber the new value of the mCodeLineNumber property
     * @roseuid 42CA31DE00F2
     */
    public void setCodeLineNumber( int pCodeLineNumber )
    {
        mCodeLineNumber = pCodeLineNumber;
    }

    /**
     * Access method for the mMethodNumber property.
     * 
     * @return the current value of the mMethodNumber property
     * @hibernate.property name="methodNumber" column="MethodNumber" type="integer" length="10" not-null="false"
     *                     unique="false" update="true" insert="true"
     * @roseuid 42CA31DE017E
     */
    public int getMethodNumber()
    {
        return mMethodNumber;
    }

    /**
     * Sets the value of the mMethodNumber property.
     * 
     * @param pMethodNumber the new value of the mMethodNumber property
     * @roseuid 42CA31DE01DC
     */
    public void setMethodNumber( int pMethodNumber )
    {
        mMethodNumber = pMethodNumber;
    }

    /**
     * Access method for the mClassNumber property.
     * 
     * @return the current value of the mClassNumber property
     * @hibernate.property name="classNumber" column="ClassNumber" type="integer" length="10" not-null="false"
     *                     unique="false" update="true" insert="true"
     * @roseuid 42CA31DE022A
     */
    public int getClassNumber()
    {
        return mClassNumber;
    }

    /**
     * Sets the value of the mClassNumber property.
     * 
     * @param pClassNumber the new value of the mClassNumber property
     * @roseuid 42CA31DE0278
     */
    public void setClassNumber( int pClassNumber )
    {
        mClassNumber = pClassNumber;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="ReferencielId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="reference_sequence"
     * @roseuid 42CA32200151
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42CA32200161
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Determines if the mValidated property is true.
     * 
     * @return <code>true<code> if the mValidated property is true
     * 
     * @hibernate.property name="hidden" column="HIDDEN" type="boolean" not-null="true" unique="false" update="true" insert="true"
     * 
     * @roseuid 42CA3F8901C8
     */
    public boolean getHidden()
    {
        return mHidden;
    }

    /**
     * Sets the value of the mDisplayed property.
     * 
     * @param pDisplayed the new value of the mDisplayed property
     * @roseuid 42CA3F8901D8
     */
    public void setHidden( boolean pDisplayed )
    {
        mHidden = pDisplayed;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CA594C0233
     */
    public SqualeReferenceBO()
    {
        mId = -1;
    }

    /**
     * @return le type de l'audit
     * @hibernate.property name="auditType" column="AUDIT_TYPE" type="string" length="50" not-null="true" unique="false"
     *                     update="true" insert="true"
     */
    public String getAuditType()
    {
        return mAuditType;
    }

    /**
     * @param pType le type de l'audit
     */
    public void setAuditType( String pType )
    {
        mAuditType = pType;
    }

}
