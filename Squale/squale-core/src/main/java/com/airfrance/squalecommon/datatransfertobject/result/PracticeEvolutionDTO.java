package com.airfrance.squalecommon.datatransfertobject.result;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * Représente l'évolution des pratiques entre deux audits
 */
public class PracticeEvolutionDTO {
    
    /* Les constantes pour le filtre */
    
    /** L'index pour le tableau des filtres */
    public final static int ONLY_UP_OR_DOWN_ID = 0;
    /** L'index pour le tableau des filtres */
    public final static int THRESHOLD_ID = 1;
    /** L'index pour le tableau des filtres */
    public final static int ONLY_PRACTICES_ID = 2;
    
    /** Indique que le filtre se porte sur les composants qui se sont améliorés */
    public final static String ONLY_DOWN = "onlyDown";
    /** Indique que le filtre se porte sur les composants qui se sont dégradés */
    public final static String ONLY_UP = "onlyUp";
    /** Indique que le filtre se porte sur les composants qui ont été supprimés */
    public final static String DELETED = "deleted";

    /** Le composant concerné */
    private ComponentDTO mComponent;
    
    /** La pratique concernée */
    private QualityResultDTO mPractice;
    
    /** La note de l'audit de référence */
    private Float mMark;
    
    /** La note de l'audit de comparaison */
    private Float mPreviousMark;
    
    
    /**
     * @return le composant
     */
    public ComponentDTO getComponent() {
        return mComponent;
    }

    /**
     * @return la note courante
     */
    public Float getMark() {
        return mMark;
    }

    /**
     * @return la note précédente
     */
    public Float getPreviousMark() {
        return mPreviousMark;
    }

    /**
     * @return la pratique
     */
    public QualityResultDTO getPractice() {
        return mPractice;
    }

    /**
     * @param pComponent le composant
     */
    public void setComponent(ComponentDTO pComponent) {
        mComponent = pComponent;
    }

    /**
     * @param pMark la note courante
     */
    public void setMark(Float pMark) {
        mMark = pMark;
    }

    /**
     * @param pPreviousMark la note précédente
     */
    public void setPreviousMark(Float pPreviousMark) {
        mPreviousMark = pPreviousMark;
    }

    /**
     * @param pPractice la pratique concernée
     */
    public void setPractice(QualityResultDTO pPractice) {
        mPractice = pPractice;
    }

}
