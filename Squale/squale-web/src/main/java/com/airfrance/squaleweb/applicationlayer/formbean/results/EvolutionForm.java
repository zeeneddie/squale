package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.datatransfertobject.result.PracticeEvolutionDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour la comparaion détaillée de deux audits
 */
public class EvolutionForm  extends RootForm {
    
    /** Le nombre de filtre possible */
    public static final int NB_FILTER = 3;
    
    /** La table des résulats aura un ComponentForm en clé */
    public final static String COMPONENT_FOR_KEY = "byComponents";
    
    /** La table des résulats aura un ResultForm en clé */
    public final static String PRACTICE_FOR_KEY = "byPractices";
    
    /** le tableau indiquant les filtres à utiliser */
    private boolean[] mFilters = new boolean[NB_FILTER];
    
    /** Valeur de l'item sélectionné pour les composants améliorés ou dégradés */
    private String mFilterOnlyUpOrDown = "";
    
    /** Le seuil (vide si on n'en a pas ) */
    private String mThreshold = "";
    
    /** La liste des pratiques disponibles */
    private String[] mAvailablePractices = new String[0];
    
    /** La liste des pratiques à conserver */
    private String[] mPractices = new String[0];
    
    /** La comparaison a effectuer pour le seuil */
    private String mComparisonSign = "<";
    
    /** Le tri à appliquer pour obtenir la Map */
    private String mSortType = PRACTICE_FOR_KEY;
    
    /** La table contenant les résultats */
    private Map mResults = new HashMap();
    
    /**
     * Constructeur par défaut
     */
    public EvolutionForm() {
    }
    
    /**
     * @return le seuil
     */
    public String getThreshold() {
        return mThreshold;
    }
    /**
     * @param pThreshold le seuil
     */
    public void setThreshold(String pThreshold) {
        mThreshold = pThreshold;
    }
    
    /** 
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
   public void wValidate(ActionMapping mapping, HttpServletRequest request) {
        if(mFilters[PracticeEvolutionDTO.THRESHOLD_ID]) {
            // On vérifie que l'utilisateur a rentré un seuil
            setThreshold(getThreshold().trim());
            if(getThreshold().length() == 0) {
                // Erreur
                addError("threshold", new ActionError("error.field.required"));
            }
        } else if(mFilters[PracticeEvolutionDTO.ONLY_PRACTICES_ID]) {
            // On vérifie que l'utilisateur a sélectionné au moins une pratique
            if(getPractices().length == 0) {
                // Sinon on affiche une erreur
                addError("practices", new ActionError("error.field.zero_selected_pratcice"));
            }
        }
    }

    /** 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setThreshold("");
        setPractices(new String[0]);
        setFilters(new boolean[NB_FILTER]);
    }

    /**
     * @return le signe de comparaison
     */
    public String getComparisonSign() {
        return mComparisonSign;
    }

    /**
     * @param pComparisonSign le signe de comparaison
     */
    public void setComparisonSign(String pComparisonSign) {
        mComparisonSign = pComparisonSign;
    }

    /**
     * @return les résultats
     */
    public Map getResults() {
        return mResults;
    }

    /**
     * @return le tri
     */
    public String getSortType() {
        return mSortType;
    }

    /**
     * @param pMap les résultats
     */
    public void setResults(Map pMap) {
        mResults = pMap;
    }

    /**
     * @param pSortType la type de tri
     */
    public void setSortType(String pSortType) {
        mSortType = pSortType;
    }
    
    /** 
     * @return le nombre de résultats 
     */
    public Set getKeys() {
        return mResults.keySet();
    }
    /**
     * @return les pratiques à garder
     */
    public String[] getPractices() {
        return mPractices;
    }

    /**
     * @param pPractices les pratiques à garder
     */
    public void setPractices(String[] pPractices) {
        mPractices = pPractices;
    }

    /**
     * @return la liste des pratiques disponibles
     */
    public String[] getAvailablePractices() {
        return mAvailablePractices;
    }

    /**
     * @param pAvailablePractices la liste des pratiques disponibles
     */
    public void setAvailablePractices(String[] pAvailablePractices) {
        mAvailablePractices = pAvailablePractices;
    }

    /**
     * @return le type de composants à voir (dégradés ou améliorés)
     */
    public String getFilterOnlyUpOrDown() {
        return mFilterOnlyUpOrDown;
    }

    /**
     * @param pFilterOnlyUpOrDown le type de composants à voir (dégradés ou améliorés)
     */
    public void setFilterOnlyUpOrDown(String pFilterOnlyUpOrDown) {
        mFilterOnlyUpOrDown = pFilterOnlyUpOrDown;
    }

    /**
     * @return le tableau indiquant les filtres à utiliser
     */
    public boolean[] getFilters() {
        return mFilters;
    }

    /**
     * @param pFilters le tableau indiquant les filtres à utiliser
     */
    public void setFilters(boolean[] pFilters) {
        mFilters = pFilters;
    }
    
    /**
     * @return true si le filtre est utilisé
     */
    public boolean getIsFilter() {
        boolean isFilter = false;
        for(int i=0; i<mFilters.length && !isFilter; i++) {
            isFilter = mFilters[i];
        }
        return isFilter;
    }

}
