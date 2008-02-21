package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.bean.WIEditable;

/**
 * Contient les données d'une application.
 * 
 * @author M400842
 */
public class AuditForm extends ActionIdFormSelectable implements WIEditable {

    /** Indique si l'audit a été modifié */
    private boolean mChanged;
    /** Nom de l'audit.*/
    private String mName = "";
    /** Date de l'audit.*/
    private Date mDate = null;
    /** Type de l'audit.*/
    private String mType = "";
    /** Status de l'audit */
    private int mStatus;
    /** le status sous la forme d'une string */
    private String mStringStatus;
    /** Date de version de l'audit dans le cas d'un audit de jalon.*/
    private Date mHistoricalDate;
    /** L'ensemble des clés pour les status sous forme de string */
    private final String[] mStringStatusTab = { "audit.status.notattempted", "audit.status.terminated", "audit.status.failed", "audit.status.deleted","audit.status.partial","audit.status.running" };
    /** Pour savoir si l'audit est édité ou non */
    private boolean mEdited;
    /** La version de SQUALE */
    private double mSqualeVersion = AuditDTO.getCurrentSqualeVersion();
    /** Le nom du serveur */
    private String mServerName;
    
    /**
     * Constructeur par défaut.
     */
    public AuditForm() {
    }

    /**
     * Constructeur.
     * @param pAudit le DTO.
     */
    public AuditForm(final AuditDTO pAudit) {
        setId(pAudit.getID());
        if (null != pAudit.getName()) {
            mName = pAudit.getName();
        }
        mDate = pAudit.getDate();
        mType = pAudit.getType();
        setApplicationId("" + pAudit.getApplicationId());
        mStatus = pAudit.getStatus();
        mStringStatus = mStringStatusTab[mStatus];
        if (null != pAudit.getHistoricalDate()) {
            setHistoricalDate(pAudit.getHistoricalDate());
        }
        mSqualeVersion = pAudit.getSqualeVersion();
    }

    /**
     * @return la date.
     */
    public Date getDate() {
        return mDate;
    }

    /**
     * @return le nom.
     */
    public String getName() {
        return mName;
    }
    /**
     * @return le type (suivi ou jalon).
     */
    public String getType() {
        return mType;
    }

    /**
     * @param pDate la date.
     */
    public void setDate(Date pDate) {
        mDate = pDate;
    }

    /**
     * @param pName le nom.
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * @param pString le type de l'application (jalon ou suivi).
     */
    public void setType(String pString) {
        mType = pString;
    }

    /**
     * @param lang locale d'affichage
     * @return la date formatée pour l'affichage.
     */
    public String getFormattedDate(Locale lang) {
        return SqualeWebActionUtils.getFormattedDate(lang, mDate, "date.format");
    }

    /**
     * @return le status de l'audit
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * @param pAuditStatus le nouveau status de l'audit
     */
    public void setStatus(int pAuditStatus) {
        mStatus = pAuditStatus;
        setStringStatus(pAuditStatus);
    }

    /**
     * @return le status de l'audit sous la forme d'une string
     */
    public String getStringStatus() {
        return mStringStatus;
    }

    /**
     * @param pStatus le status sous la forme d'une chaine
     */
    public void setStringStatus(int pStatus) {
        mStringStatus = mStringStatusTab[pStatus];
    }

    /**
     * @return la date de verion des sources
     */
    public Date getHistoricalDate() {
        return mHistoricalDate;
    }

    /**
     * @param pHistoricalDate la date de version des sources
     */
    public void setHistoricalDate(Date pHistoricalDate) {
        mHistoricalDate = pHistoricalDate;
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * 
     * @param mapping le mapping
     * @param request la requête
     */
    public void wValidate(ActionMapping mapping, HttpServletRequest request) {
        // validation dans le cas d'un audit de jalon
        setName(getName().trim());
        if (getType().equals(AuditBO.MILESTONE)) {
            if (getName().length() == 0) {
                addError("name", new ActionError("error.field.required"));
            }
            if (null == getHistoricalDate()) {
                addError("historicalDate", new ActionError("error.date.uncorrectFormat", WebMessages.getString(request, "date.format.simple")));
            } else {
                // La date historique doit être dans le passé
                Calendar today = Calendar.getInstance();
                if(getHistoricalDate().after(today.getTime())) {
                    addError("historicalDate", new ActionError("error.audit.milestone.historical_date_in_the_future"));
                }
            }
        } else {
        // validation dans le cas d'un audit de suivi
            if (getDate() == null) {
                addError("date", new ActionError("error.date.uncorrectFormat", WebMessages.getString(request, "date.format.simple")));
            }
        }
    }

    /**
     * Récupère la date d'exécution de l'audit ou dans le cas d'un audit
     * de jalon, sa date de version pour avoir une cohérence dans l'ordre
     * des audits par rapport au version du composant.
     * @return la date "réelle" de l'audit
     */
    public Date getRealDate() {
        Date real = mDate;
        if (null != mHistoricalDate) {
            real = mHistoricalDate;
        }
        return real;
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WIEditable#isEdited()
     * 
     * @return true si le form est édité
     */
    public boolean isEdited() {
        return mEdited;
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WIEditable#setEdited(boolean)
     * 
     * @param pEdited indique si le form est édité
     */
    public void setEdited(boolean pEdited) {
        mEdited = pEdited;
    }

    /**
     * @return si l'audit a été modifié
     */
    public boolean isChanged() {
        return mChanged;
    }

    /**
     * @param pChanged indique si l'audit a été modifié
     */
    public void setChanged(boolean pChanged) {
        mChanged = pChanged;
    }
    
     //  Stats pour admins

    /**
     * La date réelle de commencement avec l'heure
     */
    private Date mRealBeginningDate;

    /**
     * la date à laquelle l'audit s'est terminé
     */
    private Date mEndDate;

    /** 
     * la durée de l'audit sous forme XXhYYmZZs
     */
    private String mDuration;

    /**
     * La taille maximum du file system prise par l'audit
     */
    private Long mMaxFileSystemSize;

    /**
     * @return la durée de l'audit
     */
    public String getDuration() {
        return mDuration;
    }

    /**
     * @return la date de fin de l'audit
     */
    public Date getEndDate() {
        return mEndDate;
    }

    /**
     * @return la taille max du filesystem
     */
    public Long getMaxFileSystemSize() {
        return mMaxFileSystemSize;
    }

    /**
     * @return la date de début
     */
    public Date getRealBeginningDate() {
        return mRealBeginningDate;
    }

    /**
     * @param pDuration la durée de l'audit
     */
    public void setDuration(String pDuration) {
        mDuration = pDuration;
    }

    /**
     * @param pEndDate la date de fin 
     */
    public void setEndDate(Date pEndDate) {
        mEndDate = pEndDate;
    }

    /**
     * @param pSize la taille du file system
     */
    public void setMaxFileSystemSize(Long pSize) {
        mMaxFileSystemSize = pSize;
    }

    /**
     * @param pRealBeginningDate la date réelle de début
     */
    public void setRealBeginningDate(Date pRealBeginningDate) {
        mRealBeginningDate = pRealBeginningDate;
    }

    /**
     * Permet de reporter un audit programmé
     * @param pRestartDelay le délai de reprogrammation paramétrée de l'application
     * associée à l'audit
     */
    public void report(int pRestartDelay) {
        // Si il n'est pas en status programmé on ne fait rien
        // et on ne devrait normalement pas appeler cette méthode
        if(mStatus == AuditBO.NOT_ATTEMPTED){
            GregorianCalendar date = new GregorianCalendar();
            date.setTime(mDate);
            date.add(GregorianCalendar.DAY_OF_MONTH,pRestartDelay);
            mDate = date.getTime();
        }
    }

    /**
     * @return la version de SQUALE
     */
    public double getSqualeVersion() {
        return mSqualeVersion;
    }

    /**
     * @param pVersion la version de SQUALE
     */
    public void setSqualeVersion(double pVersion) {
        mSqualeVersion = pVersion;
    }

    /**
     * @return le nom du serveur
     */
    public String getServerName() {
        return mServerName;
    }

    /**
     * @param pServerName le nom du serveur
     */
    public void setServerName(String pServerName) {
        mServerName = pServerName;
    }

}
