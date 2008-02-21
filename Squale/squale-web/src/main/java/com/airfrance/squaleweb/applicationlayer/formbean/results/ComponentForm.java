package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdForm;

/**
 * Représente un composant (package, classe, fichier, méthode)
 * 
 * @author M400842
 *
 */
public class ComponentForm extends ActionIdForm {

    /**
     * Nom du composant
     */
    private String mName = null;

    /**
     * Nom complet du composant
     */
    private String mFullName = null;

    /**
     * Type du composant (package, classe, méthode, ...)
     */
    private String mType = null;
    
    /**
     * Le filtre sur le nom du composant
     */
    private String mFilter = "";

    /**
     * Nom du fichier du composant
     */
    private String mFileName = null;

    /**
     * identifiant du composant parent
     */
    private long mParentId = -1;

    /**
     * 
     */
    private LinkedList mMetrics = new LinkedList();

    /**
     * Résultats du composant
     */
    private ResultListForm mResults = new ResultListForm();

    /**
     * Résultats du composant sur les pratiques
     */
    private ResultListForm mPractices = new ResultListForm();

    /**
     * Liste des composants enfants
     */
    private ComponentListForm mChildren = new ComponentListForm();

    /** l'éventuelle justification associée au composant */
    private String justification = "";

    /** un booléen permettant de savoir si le composant est à exclure du plan d'aciton */
    private boolean excludedFromActionPlan = false;

    /**
     * Constructeur par défaut
     */
    public ComponentForm() {
        this(-1, "");
    }

    /**
     * Constructeur
     * @param pId l'id
     * @param pName le nom
     */
    public ComponentForm(long pId, String pName) {
        mId = pId;
        mName = pName;
    }

    /**
     * @return true si le composant est exclu du plan d'action
     */
    public boolean getExcludedFromActionPlan() {
        return excludedFromActionPlan;
    }

    /**
     * @return la justification du composant
     */
    public String getJustification() {
        return justification;
    }

    /**
     * @param pExcluded le booléen indiquant si il faut exclure le composant ou pas
     */
    public void setExcludedFromActionPlan(boolean pExcluded) {
        excludedFromActionPlan = pExcluded;
    }

    /**
     * @param pJustification la nouvelle valeur de la justification
     */
    public void setJustification(String pJustification) {
        justification = pJustification;
    }

    /**
     * Access method for the mName property.
     * 
     * @return   the current value of the mName property
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * Access method for the mType property.
     * 
     * @return   the current value of the mType property
     */
    public String getType() {
        return mType;
    }

    /**
     * Sets the value of the mType property.
     * 
     * @param pType the new value of the mType property
     */
    public void setType(String pType) {
        mType = pType;
    }

    /**
     * Access method for the mFileName property.
     * 
     * @return   the current value of the mFileName property
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * Sets the value of the mFileName property.
     * 
     * @param pFileName the new value of the mFileName property
     */
    public void setFileName(String pFileName) {
        mFileName = pFileName;
    }

    /**
     * Access method for the mIDParent property.
     * 
     * @return   the current value of the mIDParent property
     */
    public long getParentId() {
        return mParentId;
    }

    /**
     * Sets the value of the mIDParent property.
     * 
     * @param pParentId the new value of the mIDParent property
     */
    public void setParentId(long pParentId) {
        mParentId = pParentId;
    }

    /**
     * @return la liste des metriques du composant.
     */
    public LinkedList getMetrics() {
        return mMetrics;
    }

    /**
     * @param pList la liste des métriques du composant.
     */
    public void setMetrics(LinkedList pList) {
        mMetrics = pList;
    }

    /**
     * @return la liste des enfants
     */
    public ComponentListForm getChildren() {
        return mChildren;
    }

    /**
     * @return la liste des résultats sur les pratiques
     */
    public ResultListForm getPractices() {
        return mPractices;
    }

    /**
     * @return la liste des métriques
     */
    public ResultListForm getResults() {
        return mResults;
    }

    /**
     * @param pChildren la liste des enfants
     */
    public void setChildren(ComponentListForm pChildren) {
        mChildren = pChildren;
    }

    /**
     * @param pPractices la liste des résultats sur les pratiques
     */
    public void setPractices(ResultListForm pPractices) {
        mPractices = pPractices;
    }

    /**
     * @param pResults la liste des métriques
     */
    public void setResults(ResultListForm pResults) {
        mResults = pResults;
    }

    /** 
     * petite méthode pour ajuster du texte à la justification
     * @param pJustification le texte à ajouter justification
     */
    public void addJustification(String pJustification) {
        justification += pJustification;
    }

    /**
     * @return le nom complet
     */
    public String getFullName() {
        return mFullName;
    }

    /**
     * @param pFullName le nom complet
     */
    public void setFullName(String pFullName) {
        mFullName = pFullName;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest
     * A implémenter sinon on ne peut pas décocher la checkBox
     * 
     * @param mapping le mapping
     * @param request la requête
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        // Reinitialisation du checkbox
        excludedFromActionPlan = false;
        // Reinitialisation du filtre
        setFilter("");
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * 
     * @param mapping le mapping
     * @param request la requête
     */
    public void wValidate(ActionMapping mapping, HttpServletRequest request) {
        if (excludedFromActionPlan && (justification == null || justification.trim().equals(""))) {
            addError("justification", new ActionError("error.justification.mandatory"));
        }
        setFilter(getFilter().trim());
        if(getFilter().matches(".*['\"` ].*")) {
            addError("filter", new ActionError("error.invalid.characters"));
        }
            
    }

    /** 
     * Comparaison sur l'id
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj l'objet à comparer
     * @return true si this=obj
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof ComponentForm && null != obj) {
            ComponentForm comp = (ComponentForm) obj;
            result = (comp.getId() == mId);
        }
        return result;
    }

    /** 
     * @see java.lang.Object#hashCode()
     * 
     * @return le hashcode
     */
    public int hashCode() {
        int result;
        if (getName() != null) {
            result = getName().hashCode();
        } else {
            result = super.hashCode();
        }
        return result;
    }

    /**
     * @return le filtre sur le nom
     */
    public String getFilter() {
        return mFilter;
    }

    /**
     * @param pFilter le filtre sur le nom
     */
    public void setFilter(String pFilter) {
        mFilter = pFilter;
    }

}
