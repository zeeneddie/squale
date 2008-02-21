package com.airfrance.squalecommon.datatransfertobject.config;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tâche Squalix
 * Les paramètres de la tâche squalix sont placés dans ce DTO :
 * le taskDTO est en fait un merge entre les classes TaskRefBO et TaskBO
 */
public class TaskDTO {

    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /** Le nom de la tâche */
    private String mName;

    /** La classe correspondante à la tâche */
    private String mClassName;

    /** Configuration possible de la tâche*/
    private boolean mConfigurable;

    /** Configuration standard ou non */
    private boolean mStandard;

    /** indique si la tâche est obligatoire */
    private boolean mMandatory;

    /** Paramètres de la tâche sous la forme de TaskParameterDTO */
    private Collection mParameters = new ArrayList();

    /**
     * Méthode d'accès pour mId
     * 
     * @return la l'identifiant (au sens technique) de l'objet
     * 
     */
    public long getId() {
        return mId;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * Méthode d'accès à mName
     * 
     * @return le nom de la tâche
     * 
     */
    public String getName() {
        return mName;
    }

    /**
     * Change la valeur de mName
     * 
     * @param pName le nouveau nom de la tâche
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * Méthode d'accès à mClassName
     * 
     * @return le nom de la classe associée à la tâche
     * 
     */
    public String getClassName() {
        return mClassName;
    }

    /**
     * Change la valeur de mClassName
     * 
     * @param pClassName la nouvelle classe associée
     */
    public void setClassName(String pClassName) {
        mClassName = pClassName;
    }

    /**
     * Méthode d'accès à mConfigurable
     * @return true si la tâche est configurable
     */
    public boolean isConfigurable() {
        return mConfigurable;
    }

    /**
     * Change la valeur de mConfigurable
     * @param pConfigurable la nouvelle valeur de mConfigurable
     */
    public void setConfigurable(boolean pConfigurable) {
        mConfigurable = pConfigurable;
    }

    /**
     * Ajout d'un paramètre
     * @param pParameter paramètre
     */
    public void addParameter(TaskParameterDTO pParameter) {
        mParameters.add(pParameter);
    }

    /**
     * 
     * @return paramètres
     */
    public Collection getParameters() {
        return mParameters;
    }

    /**
     * @return true si la configuration est standard
     */
    public boolean isStandard() {
        return mStandard;
    }

    /**
     * @param pStandard true si la configuration est standard
     */
    public void setStandard(boolean pStandard) {
        mStandard = pStandard;
    }

    /**
     * @return true si la tâche est obligatoire
     */
    public boolean isMandatory() {
        return mMandatory;
    }

    /**
     * @param pMandatory true si la tâche est obligatoire
     */
    public void setMandatory(boolean pMandatory) {
        mMandatory = pMandatory;
    }

}
