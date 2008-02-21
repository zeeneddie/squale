package com.airfrance.squaleweb.applicationlayer.formbean.config;

import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Configuration du portail par le choix des types de récupération des sources
 * disponibles (CVS, FTP, ClearCase,...) et par le choix des profiles possibles
 * pour les projets (Java, C++,...) 
 * 
 */
public class ConfigPortailForm extends RootForm {
    
    /**
     * Liste des types de récupération des sources
     */
    private List mSourceManagements;
    
    /**
     * Liste des profiles disponibles
     */
    private List mProfiles;
    
    
    /**
     * @return la liste des profiles
     */
    public List getProfiles() {
        return mProfiles;
    }

    /**
     * @param pProfiles la liste des profiles
     */
    public void setProfiles(List pProfiles) {
        mProfiles = pProfiles;
    }

    /**
     * @return la liste des types de récupération des sources
     */
    public List getSourceManagements() {
        return mSourceManagements;
    }

    /**
     * @param pSourceManagements la liste des types de récupération des sources
     */
    public void setSourceManagements(List pSourceManagements) {
        mSourceManagements = pSourceManagements;
    }

}
