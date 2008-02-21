package com.airfrance.squalecommon.datatransfertobject.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Grille qualité
 * Seules les informations de premier niveau de la grille sont disponibles
 */
public class QualityGridDTO implements Serializable, Comparable {
    /** Date de mise à jour */
    private Date mUpdateDate;

    /** Facteurs associés */
    private Collection mFactors;
    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /** Nom de la grille */
    private String mName;

    /**
     * @return id
     */
    public long getId() {
        return mId;
    }

    /**
     * @param pId id
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * @return nom
     */
    public String getName() {
        return mName;
    }

    /**
     * @param pName nom
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * @return facteurs
     */
    public Collection getFactors() {
        return mFactors;
    }
    /**
     * @param pCollection facteurs
     */
    public void setFactors(Collection pCollection) {
        mFactors = pCollection;
    }
    /** 
     * Comparaison sur l'id des objets
     * @param obj le QualityGridDTO à comparer
     * @return le résultat de la comparaison des ids
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof QualityGridDTO) {
            return ((QualityGridDTO) obj).getId() == getId();
        } else {
            return super.equals(obj);
        }
    }

    /** (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return (int) getId();
    }

    /** (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        int result = 0;
        // On fait le test sur l'id ce qui est suffisant
        // pour qualifier la grille
        if (o instanceof QualityGridDTO) {
            QualityGridDTO grid = (QualityGridDTO) o;
            if (getId() > grid.getId()) {
                result = 1;
            } else if (getId() < grid.getId()) {
                result = -1;
            }
        }
        return result;
    }

    /**
     * @return date de mise à jour
     */
    public Date getUpdateDate() {
        return mUpdateDate;
    }

    /**
     * @param pDate date de mise à jour
     */
    public void setUpdateDate(Date pDate) {
        mUpdateDate = pDate;
    }

    /**
     * @return la liste des critères définis pour cette grille
     */
    public Collection listAllCriteria() {
        Collection criteriaColl = new ArrayList(0);
        if (mFactors != null) {
            Iterator factorsIt = mFactors.iterator();
            while (factorsIt.hasNext()) {
                FactorRuleDTO factor = (FactorRuleDTO) factorsIt.next();
                if(factor != null && factor.getCriteria() != null){
                    criteriaColl.addAll(factor.getCriteria().keySet());
                }
            }
        }
        return criteriaColl;
    }

    /**
     * @return la liste des pratiques définies pour cette grille
     */
    public Collection listAllPractices() {
        Collection criteriaColl = listAllCriteria();
        Collection practicesColl = new ArrayList(0);
        Iterator criteriumIt = criteriaColl.iterator();
        while (criteriumIt.hasNext()) {
            CriteriumRuleDTO criterium = (CriteriumRuleDTO) criteriumIt.next();
            if(criterium != null && criterium.getPractices() != null){
                criteriaColl.addAll(criterium.getPractices().keySet());
            }
        }
        return practicesColl;
    }
}
