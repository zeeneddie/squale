package com.airfrance.squalecommon.datatransfertobject.export;

import java.util.Collection;

import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;

/**
 * Représente une pratique à corriger pour le plan d'action
 */
public class ActionPlanDTO {

    /** le résultat qualité */
    private QualityResultDTO mQualityResultDTO;

    /** les résultats des composants */
    private ResultsDTO mResultsDTO;

    /** les transgressions */
    private Collection mTransgressions;
    
    /** Le nombre total de correction a effectuer */
    private int mNbCorrections;

    /**
     * Constructeur par défaut
     */
    public ActionPlanDTO() {
        this(null, null, null);
        mNbCorrections = 0;
    }

    /**
     * Constructeur pour un plan d'action synthétique
     * @param pQualityResult le résultat qualité
     * @param pNbCorrections le total des corrections
     */
    public ActionPlanDTO(QualityResultDTO pQualityResult, int pNbCorrections) {
        this(pQualityResult, null, null);
        mNbCorrections = pNbCorrections;
    }

    /**
     * Constructeur pour un plan d'action détaillé
     * @param pQualityResult le résultat qualité
     * @param pResultsDTO les résultats des composants (peut être nul)
     * @param pTransgressions les transgressions (peut être nul)
     */
    public ActionPlanDTO(QualityResultDTO pQualityResult, ResultsDTO pResultsDTO, Collection pTransgressions) {
        mQualityResultDTO = pQualityResult;
        mResultsDTO = pResultsDTO;
        mTransgressions = pTransgressions;
    }

    /**
     * @return le résultat qualité
     */
    public QualityResultDTO getQualityResultDTO() {
        return mQualityResultDTO;
    }

    /**
     * @return les résultats des composants
     */
    public ResultsDTO getResultsDTO() {
        return mResultsDTO;
    }

    /**
     * @return les transgressions
     */
    public Collection getTransgressions() {
        return mTransgressions;
    }

    /**
     * @param pResultDTO le résultat qualité
     */
    public void setQualityResultDTO(QualityResultDTO pResultDTO) {
        mQualityResultDTO = pResultDTO;
    }

    /**
     * @param pResultsDTO les résultats des composants
     */
    public void setResultsDTO(ResultsDTO pResultsDTO) {
        mResultsDTO = pResultsDTO;
    }

    /**
     * @param pCollection les transgressions
     */
    public void setTransgressions(Collection pCollection) {
        mTransgressions = pCollection;
    }

    /**
     * @return le nombre total de correction a effectuer
     */
    public int getNbCorrections() {
        return mNbCorrections;
    }

    /**
     * @param pTotal le nombre total de correction a effectuer
     */
    public void setNbCorrections(int pTotal) {
        mNbCorrections = pTotal;
    }

}
