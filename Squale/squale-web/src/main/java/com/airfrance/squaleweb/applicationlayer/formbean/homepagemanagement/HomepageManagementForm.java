package com.airfrance.squaleweb.applicationlayer.formbean.homepagemanagement;

import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Form for the homepageManagement.jsp
 */
public class HomepageManagementForm
    extends RootForm
{

    // The introduction part
    /** State of the checkbox for the introduction part */
    private boolean introCheck;

    /** Position of the introduction */
    private int positionIntro;

    // The news part
    /** State of the checkbox for the news part */
    private boolean newsCheck;

    /** Position of the news */
    private int positionNews;

    // The audit part
    /** State of the checkbox of the audit part */
    private boolean auditCheck;

    /** Position of the audits */
    private int positionAudit;

    /**
     * State of the checkbox audit scheduled
     */
    private boolean auditScheduledCheck;

    /**
     * State of the checkbox audit done
     */
    private boolean auditDoneCheck;

    /**
     * State of the checkbox audit done succesfull
     */
    private boolean auditSuccessfullCheck;

    /**
     * State of the checkbox audit done partial
     */
    private boolean auditPartialCheck;

    /**
     * State of the checkbox audit done failed
     */
    private boolean auditFailedCheck;

    /**
     * Audits of the XXX last days
     */
    private String auditNbJours;

    /**
     * State of the checkbox audit show separetley
     */
    private boolean auditShowSeparatelyCheck;

    // The result part

    /**
     * State of the checkbox of the result part
     */
    private boolean resultCheck;

    /** Position of the result */
    private int positionResult;

    /**
     * State of the checkbox of the result by grid
     */
    private boolean resultByGridCheck;

    /**
     * State of the checkBox result Kiviat
     */
    private boolean resultKiviatCheck;

    /** The width of one Kiviat */
    private String kiviatWidth;

    // The stats part
    /**
     * State of the checkbox of the statistic part
     */
    private boolean statisticCheck;

    /** Position of the statistics */
    private int positionStat;

    /** List for the order of the part */
    private List<String> jspOrder;

    /**
     * Constructor
     */
    public HomepageManagementForm()
    {
        super();

    }

    /**
     * Getter method for the statisticCheck attribute
     * 
     * @return true if the corresponding checkbox is checked
     */
    public boolean isStatisticCheck()
    {
        return statisticCheck;
    }

    /** 
     * Setter method for the statisticCheck attribute 
     * 
     * @param mStatisticCheck The new state of the corresponding checkbox 
     */
    public void setStatisticCheck( boolean mStatisticCheck )
    {
        statisticCheck = mStatisticCheck;
    }

    /**
     * Getter method for auditSuccessfullCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditSuccessfullCheck()
    {
        return auditSuccessfullCheck;
    }

    /**
     * Setter method for auditSuccessfullCheck
     * 
     * @param mAuditSuccessfullCheck The new state of the related checkox
     */
    public void setAuditSuccessfullCheck( boolean mAuditSuccessfullCheck )
    {
        auditSuccessfullCheck = mAuditSuccessfullCheck;
    }

    /**
     * Getter method for auditPartialCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditPartialCheck()
    {
        return auditPartialCheck;
    }

    /**
     * Setter method for auditPartialCheck
     * 
     * @param mAuditPartialCheck The new state of the related checkox
     */
    public void setAuditPartialCheck( boolean mAuditPartialCheck )
    {
        auditPartialCheck = mAuditPartialCheck;
    }

    /**
     * Getter method for auditFailedCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditFailedCheck()
    {
        return auditFailedCheck;
    }

    /**
     * Setter method for auditFailedCheck
     * 
     * @param mAuditFailedCheck The new state of the related checkox
     */
    public void setAuditFailedCheck( boolean mAuditFailedCheck )
    {
        auditFailedCheck = mAuditFailedCheck;
    }

    /**
     * Getter method for introCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isIntroCheck()
    {
        return introCheck;
    }

    /**
     * Setter method for introCheck
     * 
     * @param mIntroCheck The new state of the related checkox
     */
    public void setIntroCheck( boolean mIntroCheck )
    {
        introCheck = mIntroCheck;
    }

    /**
     * Getter method for newsCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isNewsCheck()
    {
        return newsCheck;
    }

    /**
     * Setter method for newsCheck
     * 
     * @param mNewsCheck The new state of the related checkox
     */
    public void setNewsCheck( boolean mNewsCheck )
    {
        newsCheck = mNewsCheck;
    }

    /**
     * Getter method for auditCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditCheck()
    {
        return auditCheck;
    }

    /**
     * Setter method for auditCheck
     * 
     * @param mAuditCheck The new state of the related checkox
     */
    public void setAuditCheck( boolean mAuditCheck )
    {
        auditCheck = mAuditCheck;
    }

    /**
     * Getter method for auditScheduledCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditScheduledCheck()
    {
        return auditScheduledCheck;
    }

    /**
     * Setter method for auditScheduledCheck
     * 
     * @param mAuditScheduledCheck The new state of the related checkox
     */
    public void setAuditScheduledCheck( boolean mAuditScheduledCheck )
    {
        auditScheduledCheck = mAuditScheduledCheck;
    }

    /**
     * Getter method for auditDoneCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditDoneCheck()
    {
        return auditDoneCheck;
    }

    /**
     * Setter method for auditDoneCheck
     * 
     * @param mAuditDoneCheck The new state of the related checkox
     */
    public void setAuditDoneCheck( boolean mAuditDoneCheck )
    {
        auditDoneCheck = mAuditDoneCheck;
    }

    /**
     * Getter method for auditShowSeparately
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isAuditShowSeparatelyCheck()
    {
        return auditShowSeparatelyCheck;
    }

    /**
     * Setter method for auditShowSeparately
     * 
     * @param auditShowSeparately The new state of the related checkox
     */
    public void setAuditShowSeparatelyCheck( boolean auditShowSeparately )
    {
        auditShowSeparatelyCheck = auditShowSeparately;
    }

    /**
     * Getter method for resultCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isResultCheck()
    {
        return resultCheck;
    }

    /**
     * Setter method for resultCheck
     * 
     * @param mResultCheck The new state of the related checkox
     */
    public void setResultCheck( boolean mResultCheck )
    {
        resultCheck = mResultCheck;
    }

    /**
     * Getter method for resultByGridCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isResultByGridCheck()
    {
        return resultByGridCheck;
    }

    /**
     * Setter method for resultByGridCheck
     * 
     * @param mResultByGridCheck The new state of the related checkox
     */
    public void setResultByGridCheck( boolean mResultByGridCheck )
    {
        resultByGridCheck = mResultByGridCheck;
    }

    /**
     * Getter method for resultKiviatCheck
     * 
     * @return true if the linked checkbox is checked
     */
    public boolean isResultKiviatCheck()
    {
        return resultKiviatCheck;
    }

    /**
     * Setter method for resultKiviatCheck
     * 
     * @param mResultKiviatCheck The new state of the related checkox
     */
    public void setResultKiviatCheck( boolean mResultKiviatCheck )
    {
        resultKiviatCheck = mResultKiviatCheck;
    }

    /**
     * Getter method for positionIntro
     * 
     * @return the position of the introduction bloc
     */
    public int getPositionIntro()
    {
        return positionIntro;
    }

    /**
     * Setter method for positionIntro
     * 
     * @param mPositionIntro The new position for the introduction bloc
     */
    public void setPositionIntro( int mPositionIntro )
    {
        positionIntro = mPositionIntro;
    }

    /**
     * Getter method for positionNews
     * 
     * @return the position of the news bloc
     */
    public int getPositionNews()
    {
        return positionNews;
    }

    /**
     * Setter method for positionNews
     * 
     * @param mPositionNews The new position for the news bloc
     */
    public void setPositionNews( int mPositionNews )
    {
        positionNews = mPositionNews;
    }

    /**
     * Getter method for positionAudit
     * 
     * @return the position of the audit bloc
     */
    public int getPositionAudit()
    {
        return positionAudit;
    }

    /**
     * Setter method for positionAudit
     * 
     * @param mPositionAudit The new position for the audit bloc
     */
    public void setPositionAudit( int mPositionAudit )
    {
        positionAudit = mPositionAudit;
    }

    /**
     * Getter method for positionResult
     * 
     * @return the position of the result bloc
     */
    public int getPositionResult()
    {
        return positionResult;
    }

    /**
     * Setter method for positionResult
     * 
     * @param mPositionResult The new position for the result bloc
     */
    public void setPositionResult( int mPositionResult )
    {
        positionResult = mPositionResult;
    }

    /**
     * Getter method for positionStat
     * 
     * @return the position of the statistics bloc
     */
    public int getPositionStat()
    {
        return positionStat;
    }

    /**
     * Setter method for positionStat
     * 
     * @param mPositionStat The new position for the statistics bloc
     */
    public void setPositionStat( int mPositionStat )
    {
        positionStat = mPositionStat;
    }

    /**
     * Getter method for auditNbJours
     * 
     * @return the number of day for the search of audit
     */
    public String getAuditNbJours()
    {
        return auditNbJours;
    }

    /**
     * Setter method for mAuditNbJours
     * 
     * @param mAuditNbJours The new number of day
     */
    public void setAuditNbJours( String mAuditNbJours )
    {
        auditNbJours = mAuditNbJours;
    }

    /**
     * Getter method for jspOrder
     * 
     * @return The ordered list of the name of the jsp to include in the homepage
     */
    public List<String> getJspOrder()
    {
        return jspOrder;
    }

    /**
     * Setter method for jspOrder
     * 
     * @param mJspOrder The new ordered list 
     */
    public void setJspOrder( List<String> mJspOrder )
    {
        jspOrder = mJspOrder;
    }

    /**
     * Getter method for the kiviatWidth attribute
     * 
     * @return The width of one kiviat  
     */
    public String getKiviatWidth()
    {
        return kiviatWidth;
    }

    /**
     * Setter method for the kiviatWidth attribute
     * 
     * @param mKiviatWidth The new width of one kiviat
     */
    public void setKiviatWidth( String mKiviatWidth )
    {
        kiviatWidth = mKiviatWidth;
    }

}
