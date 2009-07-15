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
package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient les paramètres de compilation Java pour une tâche ANT ou un workspace WSAD.
 */
public class JavaCompilationForm
    extends RootForm
{

    /**
     * Type de la tâche (Ant, WSAD ou RSA)
     */
    private String mKindOfTask = "";

    /*
     * ***************************************************** Dans le cas d'un workspace WSAD
     */
    /**
     * Chemin du workspace depuis la racine
     */
    private String mWorkspacePath = "";

    /*
     * Configuration supplémentaire dans le cas d'un projet RSA
     */

    /** Le chemin vers le manifest */
    private String mManifestPath = "";

    /** Le nom de l'EAR associé */
    private String mEarName = "";

    /*
     * ***************************************************** Dans le cas d'un fichier ANT
     */
    /**
     * Chemin du fichier ANT
     */
    private String mAntFile = "";

    /**
     * Tâche ANT à lancer
     */
    private String mAntTaskName = "";

    /**
     * @return Chemin du fichier ANT
     */
    public String getAntFile()
    {
        return mAntFile;
    }

    /**
     * @return Tâche ANT à lancer
     */
    public String getAntTaskName()
    {
        return mAntTaskName;
    }

    /**
     * @return Type de la tâche (Ant ou WSAD)
     */
    public String getKindOfTask()
    {
        return mKindOfTask;
    }

    /**
     * @return Chemin du workspace depuis la racine
     */
    public String getWorkspacePath()
    {
        return mWorkspacePath;
    }

    /**
     * @param pAntFile Chemin du fichier ANT
     */
    public void setAntFile( String pAntFile )
    {
        mAntFile = pAntFile;
    }

    /**
     * @param pAntTaskName Tâche ANT à lancer
     */
    public void setAntTaskName( String pAntTaskName )
    {
        mAntTaskName = pAntTaskName;
    }

    /**
     * @param pKindOfTask Nom du fichier classpath dans le projet
     */
    public void setKindOfTask( String pKindOfTask )
    {
        mKindOfTask = pKindOfTask;
    }

    /**
     * @param pWorkspacePath Chemin du workspace depuis la racine
     */
    public void setWorkspacePath( String pWorkspacePath )
    {
        mWorkspacePath = pWorkspacePath;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        this.mWorkspacePath = "";
        this.mAntFile = "";
        this.mAntTaskName = "";
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        if ( ParametersConstants.WSAD.equals( getKindOfTask() ) || ParametersConstants.RSA.equals( getKindOfTask() ) )
        {
            // Il faut que le workspace soit renseigné
            // Le chemin va être récupérer par Squalix sous unix donc on remplace
            // éventuellement les séparateurs
            setWorkspacePath( getWorkspacePath().trim().replace( '\\', '/' ) );
            if ( getWorkspacePath().length() == 0 )
            {
                addError( "workspacePath", new ActionError( "error.field.required" ) );
            }
        }
        else if ( ParametersConstants.ANT.equals( getKindOfTask() ) )
        { // Il s'agit d'une règle ant
            // Il faut que le fichier ant soit renseigné
            setAntTaskName( getAntTaskName().trim() );
            setAntFile( getAntFile().trim() );
            if ( getAntFile().length() == 0 )
            {
                addError( "antFile", new ActionError( "error.field.required" ) );
            }
        }
        // On ajoute le nom de la tâche à la requête pour indiquer au dropPanel
        // qu'il faut qu'il soit ouvert
        request.setAttribute( "tool", "JCompilingTask" );
    }

    /**
     * @return le nom de l'EAR associé
     */
    public String getEarName()
    {
        return mEarName;
    }

    /**
     * @return le chemin vers le manifest
     */
    public String getManifestPath()
    {
        return mManifestPath;
    }

    /**
     * @param pEarName nom de l'EAR
     */
    public void setEarName( String pEarName )
    {
        mEarName = ( pEarName != null ) ? pEarName.trim() : "";
    }

    /**
     * @param pManifestPath le chemin du manifest
     */
    public void setManifestPath( String pManifestPath )
    {
        mManifestPath = ( pManifestPath != null ) ? pManifestPath.trim() : "";
    }

}
