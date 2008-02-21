package com.airfrance.squaleweb.applicationlayer.formbean.stats;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour les statistiques par application
 */
public class ApplicationStatsForm extends RootForm {
    /** Indique si l'application est validée */
    private boolean mValidatedApplication;
    /** Indique si l'application est validée sous forme de chaîne */
    private String mValidatedApplicationStr;
    /** Ok si le derniers audits exécutés est réussis, En échec sinon */
    private String mLastAuditIsTerminated;
    /** o si aucun audit réussis présent dans les n derniers jours (90 par défaut), X sinon */
    private String mActivatedApplication;
    /** date du dernier audit réussis */
    private String mLastTerminatedAuditDate;
    /** la durée en hh:mm du dernier audit */
    private String mLastAuditDuration;
    /** le nombre d'audit ( quelque soit leur état ) réalisés dutant les n derniers jours (10 par défaut) */
    private int mNbAudits;
    /** le nombre audits en réussis */
    private int mNbTerminatedAudits;
    /** le nombre audits partiel ou en échec */
    private int mNbPartialOrFaliedAudits;
    /** la date du dernier audit en échec */
    private String mLastFailedAuditDate;
    /** la date du plus ancien audit réussis */
    private String mFirstTerminatedAuditDate;
    /** le nom du serveur de l'application */
    private String mServerName;
    /** la fréquence de purge */
    private String mPurgeFrequency;
    /** Date du dernier accès utilisateur */
    private String mLastAccess;

    /**
     * @return X si aucun audit réussis présent dans les n derniers jours
     */
    public String getActivatedApplication() {
        return mActivatedApplication;
    }

    /**
     * @return la date du plus ancien audit réussis
     */
    public String getFirstTerminatedAuditDate() {
        return mFirstTerminatedAuditDate;
    }

    /**
     * @return la date du dernier accès utilisateur
     */
    public String getLastAccess() {
        return mLastAccess;
    }

    /**
     * @return la durée en hh:mm du dernier audit
     */
    public String getLastAuditDuration() {
        return mLastAuditDuration;
    }

    /**
     * @return la date du dernier audit en échec
     */
    public String getLastFailedAuditDate() {
        return mLastFailedAuditDate;
    }

    /**
     * @return la date du dernier audit réussis
     */
    public String getLastTerminatedAuditDate() {
        return mLastTerminatedAuditDate;
    }

    /**
     * @return le nombre d'audit ( quelque soit leur état ) réalisés dutant les n derniers jours
     */
    public int getNbAudits() {
        return mNbAudits;
    }

    /**
     * @return le nombre audits partiel ou en échec
     */
    public int getNbPartialOrFaliedAudits() {
        return mNbPartialOrFaliedAudits;
    }

    /**
     * @return le nombre audits en réussis
     */
    public int getNbTerminatedAudits() {
        return mNbTerminatedAudits;
    }

    /**
     * @return le nom du serveur de l'application
     */
    public String getServerName() {
        return mServerName;
    }

    /**
     * @return Ok si le derniers audits exécutés est réussis
     */
    public String getLastAuditIsTerminated() {
        return mLastAuditIsTerminated;
    }

    /**
     * @return true si l'application est validée
     */
    public boolean getValidatedApplication() {
        return mValidatedApplication;
    }

    /**
     * @return la chaîne correspondant à l'affichage d'une application validée ou non
     */
    public String getValidatedApplicationStr() {
        return mValidatedApplicationStr;
    }

    /**
     * @param pActivated X si aucun audit réussis présent dans les n derniers jours
     */
    public void setActivatedApplication(String pActivated) {
        mActivatedApplication = pActivated;
    }

    /**
     * @param pDate la date du plus ancien audit réussis
     */
    public void setFirstTerminatedAuditDate(String pDate) {
        mFirstTerminatedAuditDate = pDate;
    }

    /**
     * @param pDate la date du dernier accès utilisateur
     */
    public void setLastAccess(String pDate) {
        mLastAccess = pDate;
    }

    /**
     * @param pDuration la durée en hh:mm du dernier audit
     */
    public void setLastAuditDuration(String pDuration) {
        mLastAuditDuration = pDuration;
    }

    /**
     * @param pDate la date du dernier audit en échec
     */
    public void setLastFailedAuditDate(String pDate) {
        mLastFailedAuditDate = pDate;
    }

    /**
     * @param pDate la date du dernier audit réussis
     */
    public void setLastTerminatedAuditDate(String pDate) {
        mLastTerminatedAuditDate = pDate;
    }

    /**
     * @param pNbAudits le nombre d'audit ( quelque soit leur état ) réalisés dutant les n derniers jours
     */
    public void setNbAudits(int pNbAudits) {
        mNbAudits = pNbAudits;
    }

    /**
     * @param pNbAudits le nombre audits partiel ou en échec
     */
    public void setNbPartialOrFaliedAudits(int pNbAudits) {
        mNbPartialOrFaliedAudits = pNbAudits;
    }

    /**
     * @param pNbAudits le nombre audits en réussis
     */
    public void setNbTerminatedAudits(int pNbAudits) {
        mNbTerminatedAudits = pNbAudits;
    }

    /**
     * @param pServerName le nom du serveur de l'application
     */
    public void setServerName(String pServerName) {
        mServerName = pServerName;
    }

    /**
     * @param pTerminated Ok si le derniers audit exécuté est réussi
     */
    public void setLastAuditIsTerminated(String pTerminated) {
        mLastAuditIsTerminated = pTerminated;
    }

    /**
     * @param pValidated true si l'application est validée
     */
    public void setValidatedApplication(boolean pValidated) {
        mValidatedApplication = pValidated;
    }

    /**
     * modifie la chaîne correspondant à l'affichage d'une application validée ou non
     * @param pValidatedApplicationStr la nouvelle chaîne
     */
    public void setValidatedApplicationStr(String pValidatedApplicationStr) {
        mValidatedApplicationStr = pValidatedApplicationStr;
    }
    /**
     * @return la fréquence de purge
     */
    public String getPurgeFrequency() {
        return mPurgeFrequency;
    }

    /**
     * @param pFreq la fréquence de purge
     */
    public void setPurgeFrequency(String pFreq) {
        mPurgeFrequency = "" + pFreq;
    }
}
