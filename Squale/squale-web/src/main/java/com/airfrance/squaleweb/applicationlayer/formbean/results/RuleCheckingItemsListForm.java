package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour la liste des items d'une transgression
 */
public class RuleCheckingItemsListForm extends RootForm {
    
    /**
     * Nom de la règle transgressée
     */
    private String mRuleName;

    /**
     * Détails concernant la règle
     */
    private Collection mDetails;
    
    /**
     * Indique si la liste contient au moins
     * un item qui possède un lien vers un composant
     */
    private boolean mComponentLink;
    
    /**
     * Constructeur par défaut
     */
    public RuleCheckingItemsListForm() {
        mDetails = new ArrayList();
    }

    /**
     * @return le nom de la règle
     */
    public String getRuleName() {
        return mRuleName;
    }

    /**
     * @return les détails
     */
    public Collection getDetails() {
        return mDetails;
    }

    /**
     * @param pRuleName le nom de la règle
     */
    public void setRuleName(String pRuleName) {
        mRuleName = pRuleName;
    }

    /**
     * @param pDetails les détails
     */
    public void setDetails(Collection pDetails) {
        mDetails = pDetails;
    }
    /**
     * @return true si il y a un lien vers un composant
     */
    public boolean getComponentLink() {
        return mComponentLink;
    }

    /**
     * @param pComponentLink lien vers composant
     */
    public void setComponentLink(boolean pComponentLink) {
        mComponentLink = pComponentLink;
    }

}
