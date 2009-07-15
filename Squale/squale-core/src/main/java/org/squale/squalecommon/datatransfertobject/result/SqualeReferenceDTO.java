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
package org.squale.squalecommon.datatransfertobject.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;

/**
 * @author m400841 (by Rose)
 */
public class SqualeReferenceDTO
    implements Serializable
{
    /**
     * Grille qualité
     */
    private QualityGridDTO mGrid;

    /**
     * @return grille
     */
    public QualityGridDTO getGrid()
    {
        return mGrid;
    }

    /**
     * @param pGridDTO grille
     */
    public void setGrid( QualityGridDTO pGridDTO )
    {
        mGrid = pGridDTO;
    }

    /**
     * Caractère public
     */
    private boolean mPublic;

    /**
     * Facteurs.
     */
    private Collection mFactorValues = new ArrayList();

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
     * <code>true</code> si cette référence est masquée à un utilisateur non-admin
     */
    private boolean mHidden;

    /**
     * Le nom de l'application.
     */
    private String mApplicationName;

    /**
     * Le nom du projet.
     */
    private String mProjectName;

    /**
     * Le langage du projet relatif à sa technologie (Java,J2EE).
     */
    private String mLanguage;
    
    /**
     * Le langage de programmation du projet (JAVA,CPP,COBOL)
     */
    private String mProgrammingLanguage;

    /**
     * La version du projet.
     */
    private String mVersion;

    /**
     * Jour où les données ont été générées.
     */
    private Date mDate;

    /** le type de l'audit */
    private String mAuditType;

    /**
     * Constructeur vide
     * 
     * @roseuid 42CD3C1B0034
     */
    public SqualeReferenceDTO()
    {
    }

    /**
     * Access method for the mCodeLineNumber property.
     * 
     * @return the current value of the mCodeLineNumber property
     * @roseuid 42CD3C1F02ED
     */
    public int getCodeLineNumber()
    {
        return mCodeLineNumber;
    }

    /**
     * Sets the value of the mCodeLineNumber property.
     * 
     * @param pCodeLineNumber the new value of the mCodeLineNumber property
     * @roseuid 42CD3C1F0333
     */
    public void setCodeLineNumber( int pCodeLineNumber )
    {
        mCodeLineNumber = pCodeLineNumber;
    }

    /**
     * Access method for the mMethodNumber property.
     * 
     * @return the current value of the mMethodNumber property
     * @roseuid 42CD3C1F03B5
     */
    public int getMethodNumber()
    {
        return mMethodNumber;
    }

    /**
     * Sets the value of the mMethodNumber property.
     * 
     * @param pMethodNumber the new value of the mMethodNumber property
     * @roseuid 42CD3C200027
     */
    public void setMethodNumber( int pMethodNumber )
    {
        mMethodNumber = pMethodNumber;
    }

    /**
     * Access method for the mClassNumber property.
     * 
     * @return the current value of the mClassNumber property
     * @roseuid 42CD3C20009F
     */
    public int getClassNumber()
    {
        return mClassNumber;
    }

    /**
     * Sets the value of the mClassNumber property.
     * 
     * @param pClassNumber the new value of the mClassNumber property
     * @roseuid 42CD3C2000DB
     */
    public void setClassNumber( int pClassNumber )
    {
        mClassNumber = pClassNumber;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
     * @roseuid 42CD3C2001C2
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42CD3C2001EA
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Determines if the mValidated property is true.
     * 
     * @return <code>true<code> if the mValidated property is true
     @roseuid 42CD3C200280
     */
    public boolean getHidden()
    {
        return mHidden;
    }

    /**
     * Sets the value of the mHidden property.
     * 
     * @param pHidden the new value of the mHidden property
     * @roseuid 42CD3C200281
     */
    public void setHidden( boolean pHidden )
    {
        mHidden = pHidden;
    }

    /**
     * Access method for the mApplicationName property.
     * 
     * @return the current value of the mApplicationName property
     */
    public String getApplicationName()
    {
        return mApplicationName;
    }

    /**
     * Sets the value of the mApplicationName property.
     * 
     * @param pApplicationName the new value of the mApplicationName property
     */
    public void setApplicationName( String pApplicationName )
    {
        mApplicationName = pApplicationName;
    }

    /**
     * Access method for the mProjectName property.
     * 
     * @return the current value of the mProjectName property
     */
    public String getProjectName()
    {
        return mProjectName;
    }

    /**
     * Sets the value of the mProjectName property.
     * 
     * @param pProjectName the new value of the mProjectName property
     */
    public void setProjectName( String pProjectName )
    {
        mProjectName = pProjectName;
    }

    /**
     * Access method for the mLanguage property.
     * 
     * @return the current value of the mLanguage property
     */
    public String getLanguage()
    {
        return mLanguage;
    }

    /**
     * Sets the value of the mLanguage property.
     * 
     * @param pLanguage the new value of the mLanguage property
     */
    public void setLanguage( String pLanguage )
    {
        mLanguage = pLanguage;
    }
    
    /**
     * Access method for the mProgrammingLanguage property.
     * 
     * @return the current value of the mProgrammingLanguage property
     */
    public String getProgrammingLanguage()
    {
        return mProgrammingLanguage;
    }

    /**
     * Sets the value of the mProgrammingLanguage property.
     * 
     * @param pLanguage the new value of the mProgrammingLanguage property
     */
    public void setProgrammingLanguage( String pProgrammingLanguage )
    {
        mProgrammingLanguage = pProgrammingLanguage;
    }

    /**
     * Access method for the mVersion property.
     * 
     * @return the current value of the mVersion property
     */
    public String getVersion()
    {
        return mVersion;
    }

    /**
     * Sets the value of the mVersion property.
     * 
     * @param pVersion the new value of the mVersion property
     */
    public void setVersion( String pVersion )
    {
        mVersion = pVersion;
    }

    /**
     * Access method for the mDate property.
     * 
     * @return the current value of the mDate property
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * Sets the value of the mDate property.
     * 
     * @param pDate the new value of the mDate property
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

    /**
     * Access method for the mFactors property.
     * 
     * @return the Hashmap of factor
     */
    public Collection getFactorValues()
    {
        return mFactorValues;
    }

    /**
     * Sets the value of the mFactors property.
     * 
     * @param pFactorValue valeur du facteur
     */
    public void addFactorValue( ReferenceFactorDTO pFactorValue )
    {
        mFactorValues.add( pFactorValue );
    }

    /**
     * @param pPublic caractère public
     */
    public void setPublic( boolean pPublic )
    {
        mPublic = pPublic;
    }

    /**
     * @return caractère public
     */
    public boolean isPublic()
    {
        return mPublic;
    }

    /**
     * @return le type de l'audit
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
