/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.externalTask.qc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm;
import com.airfrance.squaleweb.transformer.component.parameters.external.qc.TestManagerQCTransformer;

/**
 * Bean for TestManagerQCTask
 */
public class TestManagerQCForm
    extends AbstractParameterForm
{

    /* Default value */
    /** covered requirements satus */
    private static final String COVERED_REQ = "No Run";

    /** opened requirements satus */
    private static final String OPENED_REQ = "Failed,N/A,Not Completed,Not Covered";

    /** to validate requirements satus */
    private static final String TO_VALIDATE_REQ = "Passed";

    /** passed step satus */
    private static final String PASSED_STEP = "Passed";

    /** ok run satus */
    private static final String OK_RUN = "Passed";

    /** closed defect satus */
    private static final String CLOSED_DEFECT = "Closed,Fixed,Corrected,Clos,Rejeté";

    /** Login */
    private String mQCLogin = "";

    /** Password */
    private String mQCPassword = "";

    /** URL */
    private String mQCUrl = "";

    /** Database name */
    private String mDataBaseName = "";

    /** Admin database name */
    private String mAdminDataBaseName = "";

    /** Prefix database name */
    private String mPrefixDataBaseName = "";

    /** List of status for covered requirements */
    private String mQCCoveredReqStatus = COVERED_REQ;

    /** List of status for opened requirements */
    private String mQCOpenedReqStatus = OPENED_REQ;

    /** List of status for requirements to validate */
    private String mQCToValidateReqStatus = TO_VALIDATE_REQ;

    /** List of status for passed steps */
    private String mQCPassedStepStatus = PASSED_STEP;

    /** List of status for ok run */
    private String mQCOkRunStatus = OK_RUN;

    /** List of status for closed defects */
    private String mQCClosedDefectStatus = CLOSED_DEFECT;

    /** Date of previous version */
    private Date previousRelease;

    /** Date of next version */
    private Date nextRelease;

    /** included requirements */
    private String mQCIncludedReq = "";

    /** included test plan */
    private String mQCIncludedTestPlan = "";

    /** included test lab */
    private String mQCIncludedTestLab = "";

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     */
    public Class getTransformer()
    {
        return TestManagerQCTransformer.class;
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.QC };
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     */
    public String getNameInSession()
    {
        return "testManagerQCForm";
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     */
    public String getTaskName()
    {
        return "TestManagerQCTask";
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // Database info
        setQCLogin( getQCLogin().trim() );
        if ( getQCLogin().length() == 0 )
        {
            addError( "QCLogin", new ActionError( "project_creation.QC.testmanager.error.login" ) );
        }
        setQCPassword( getQCPassword().trim() );
        if ( getQCPassword().length() == 0 )
        {
            addError( "QCPassword", new ActionError( "project_creation.QC.testmanager.error.pwd" ) );
        }
        setQCUrl( getQCUrl().trim() );
        if ( getQCUrl().length() == 0 )
        {
            addError( "QCUrl", new ActionError( "project_creation.QC.testmanager.error.url" ) );
        }
        setDataBaseName( getDataBaseName().trim() );
        if ( getDataBaseName().length() == 0 )
        {
            addError( "dataBaseName", new ActionError( "project_creation.QC.testmanager.error.databaseName" ) );
        }
        setAdminDataBaseName( getAdminDataBaseName().trim() );
        if ( getAdminDataBaseName().length() == 0 )
        {
            addError( "adminDataBaseName", new ActionError( "project_creation.QC.testmanager.error.adminDatabaseName" ) );
        }
        setPrefixDataBaseName( getPrefixDataBaseName().trim() );
        if ( getPrefixDataBaseName().length() == 0 )
        {
            addError( "prefixDataBaseName",
                      new ActionError( "project_creation.QC.testmanager.error.prefixDatabaseName" ) );
        }
        // List of status
        validateListOfStatus( pMapping, pRequest );
    }

    /**
     * Validate mandatory list of status
     * 
     * @param pMapping mapping
     * @param pRequest request
     */
    private void validateListOfStatus( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        setQCClosedDefectStatus( getQCClosedDefectStatus().trim() );
        if ( getQCClosedDefectStatus().length() == 0 )
        {
            addError( "QCClosedDefectStatus", new ActionError( "project_creation.QC.testmanager.error.closedDefects" ) );
        }
        setQCCoveredReqStatus( getQCCoveredReqStatus().trim() );
        if ( getQCCoveredReqStatus().length() == 0 )
        {
            addError( "QCCoveredReqStatus", new ActionError( "project_creation.QC.testmanager.error.coveredReq" ) );
        }
        setQCOkRunStatus( getQCOkRunStatus().trim() );
        if ( getQCOkRunStatus().length() == 0 )
        {
            addError( "QCOkRunStatus", new ActionError( "project_creation.QC.testmanager.error.okRun" ) );
        }
        setQCOpenedReqStatus( getQCOpenedReqStatus().trim() );
        if ( getQCOpenedReqStatus().length() == 0 )
        {
            addError( "QCOpenedReqStatus", new ActionError( "project_creation.QC.testmanager.error.openedReq" ) );
        }
        setQCPassedStepStatus( getQCPassedStepStatus().trim() );
        if ( getQCPassedStepStatus().length() == 0 )
        {
            addError( "QCPassedStepStatus", new ActionError( "project_creation.QC.testmanager.error.passedStep" ) );
        }
        setQCToValidateReqStatus( getQCToValidateReqStatus().trim() );
        if ( getQCToValidateReqStatus().length() == 0 )
        {
            addError( "QCToValidateReqStatus", new ActionError( "project_creation.QC.testmanager.error.toValidateReq" ) );
        }
    }

    /**
     * @return the database name
     */
    public String getDataBaseName()
    {
        return mDataBaseName;
    }

    /**
     * @return the admin database name
     */
    public String getAdminDataBaseName()
    {
        return mAdminDataBaseName;
    }

    /**
     * @return the prefix database name
     */
    public String getPrefixDataBaseName()
    {
        return mPrefixDataBaseName;
    }

    /**
     * @return list of status for closed defects
     */
    public String getQCClosedDefectStatus()
    {
        return mQCClosedDefectStatus;
    }

    /**
     * @return list of status for covered requirements
     */
    public String getQCCoveredReqStatus()
    {
        return mQCCoveredReqStatus;
    }

    /**
     * @return login for database
     */
    public String getQCLogin()
    {
        return mQCLogin;
    }

    /**
     * @return list of status for passed runs
     */
    public String getQCOkRunStatus()
    {
        return mQCOkRunStatus;
    }

    /**
     * @return list of status for opened requirements
     */
    public String getQCOpenedReqStatus()
    {
        return mQCOpenedReqStatus;
    }

    /**
     * @return list of status for passed steps
     */
    public String getQCPassedStepStatus()
    {
        return mQCPassedStepStatus;
    }

    /**
     * @return password for database
     */
    public String getQCPassword()
    {
        return mQCPassword;
    }

    /**
     * @return list of status for requirements to validate
     */
    public String getQCToValidateReqStatus()
    {
        return mQCToValidateReqStatus;
    }

    /**
     * @return database url
     */
    public String getQCUrl()
    {
        return mQCUrl;
    }

    /**
     * @return date of next release
     */
    public Date getNextRelease()
    {
        return nextRelease;
    }

    /**
     * @return date of previous release
     */
    public Date getPreviousRelease()
    {
        return previousRelease;
    }

    /**
     * @param pDataBaseName name of database
     */
    public void setDataBaseName( String pDataBaseName )
    {
        mDataBaseName = pDataBaseName;
    }

    /**
     * @param pAdminDataBaseName name of admin database
     */
    public void setAdminDataBaseName( String pAdminDataBaseName )
    {
        mAdminDataBaseName = pAdminDataBaseName;
    }

    /**
     * @param pPrefixDataBaseName name of prefix database
     */
    public void setPrefixDataBaseName( String pPrefixDataBaseName )
    {
        mPrefixDataBaseName = pPrefixDataBaseName;
    }

    /**
     * @param pQCClosedDefectStatus status for closed defects
     */
    public void setQCClosedDefectStatus( String pQCClosedDefectStatus )
    {
        mQCClosedDefectStatus = pQCClosedDefectStatus;
    }

    /**
     * @param pQCCoveredReqStatus status for covered requirements status
     */
    public void setQCCoveredReqStatus( String pQCCoveredReqStatus )
    {
        mQCCoveredReqStatus = pQCCoveredReqStatus;
    }

    /**
     * @param pLogin login
     */
    public void setQCLogin( String pLogin )
    {
        mQCLogin = pLogin;
    }

    /**
     * @param pQCOkRunStatus status for ok run
     */
    public void setQCOkRunStatus( String pQCOkRunStatus )
    {
        mQCOkRunStatus = pQCOkRunStatus;
    }

    /**
     * @param pQCOpenedReqStatus status for opened requirements
     */
    public void setQCOpenedReqStatus( String pQCOpenedReqStatus )
    {
        mQCOpenedReqStatus = pQCOpenedReqStatus;
    }

    /**
     * @param pQCPassedStepStatus status for passed step
     */
    public void setQCPassedStepStatus( String pQCPassedStepStatus )
    {
        mQCPassedStepStatus = pQCPassedStepStatus;
    }

    /**
     * @param pPassword password
     */
    public void setQCPassword( String pPassword )
    {
        mQCPassword = pPassword;
    }

    /**
     * @param pQCToValidateReqStatus status for validated requirements
     */
    public void setQCToValidateReqStatus( String pQCToValidateReqStatus )
    {
        mQCToValidateReqStatus = pQCToValidateReqStatus;
    }

    /**
     * @param pUrl database url
     */
    public void setQCUrl( String pUrl )
    {
        mQCUrl = pUrl;
    }

    /**
     * @param pNextDate date of next release
     */
    public void setNextRelease( Date pNextDate )
    {
        nextRelease = pNextDate;
    }

    /**
     * @param pPrevDate date of previous release
     */
    public void setPreviousRelease( Date pPrevDate )
    {
        previousRelease = pPrevDate;
    }

    /**
     * @return included requirements
     */
    public String getQCIncludedReq()
    {
        return mQCIncludedReq;
    }

    /**
     * @return included TestLab
     */
    public String getQCIncludedTestLab()
    {
        return mQCIncludedTestLab;
    }

    /**
     * @return included TestPlan
     */
    public String getQCIncludedTestPlan()
    {
        return mQCIncludedTestPlan;
    }

    /**
     * @param pReqRoot included requirements
     */
    public void setQCIncludedReq( String pReqRoot )
    {
        mQCIncludedReq = pReqRoot;
    }

    /**
     * @param pTestLabRoot included TestLab
     */
    public void setQCIncludedTestLab( String pTestLabRoot )
    {
        mQCIncludedTestLab = pTestLabRoot;
    }

    /**
     * @param pTestPlan included TestPlan
     */
    public void setQCIncludedTestPlan( String pTestPlan )
    {
        mQCIncludedTestPlan = pTestPlan;
    }

}
