package com.airfrance.squaleweb.transformer.component.parameters.external.qc;

import java.text.ParseException;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.externalTask.qc.TestManagerQCForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transform TestManagerQCTask's configuration
 */
public class TestManagerQCTransformer
    implements WITransformer
{

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        TestManagerQCForm form = new TestManagerQCForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        TestManagerQCForm qcForm = (TestManagerQCForm) form;
        MapParameterDTO qcMap =
            (MapParameterDTO) ( (MapParameterDTO) object[0] ).getParameters().get( ParametersConstants.QC );
        if ( null != qcMap )
        {
            // Mandatory parameters
            qcForm.setQCLogin( ( (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_USER ) ).getValue() );
            qcForm.setQCUrl( ( (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_URL ) ).getValue() );
            qcForm.setQCPassword( ( (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_PASSWORD ) ).getValue() );
            qcForm.setDataBaseName( ( (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_DB_NAME ) ).getValue() );
            qcForm.setPrefixDataBaseName( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                            ParametersConstants.QC_PREFIX_DB_NAME ) ).getValue() );
            qcForm.setAdminDataBaseName( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                           ParametersConstants.QC_ADMIN_DB_NAME ) ).getValue() );
            qcForm.setQCClosedDefectStatus( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                              ParametersConstants.QC_CLOSED_DEFECT ) ).getValue() );
            qcForm.setQCCoveredReqStatus( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                            ParametersConstants.QC_REQ_COVERED ) ).getValue() );
            qcForm.setQCOkRunStatus( ( (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_RUN_OK ) ).getValue() );
            qcForm.setQCOpenedReqStatus( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                           ParametersConstants.QC_OPENED_REQ ) ).getValue() );
            qcForm.setQCPassedStepStatus( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                            ParametersConstants.QC_STEP_PASSED ) ).getValue() );
            qcForm.setQCToValidateReqStatus( ( (StringParameterDTO) qcMap.getParameters().get(
                                                                                               ParametersConstants.QC_TO_VALIDATE_REQ ) ).getValue() );

            // Facultative parameters
            StringParameterDTO prevDate =
                (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_PREVIOUS_DATE );
            StringParameterDTO nextDate =
                (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_LAST_DATE );
            try
            {
                if ( null != prevDate )
                {
                    qcForm.setPreviousRelease( ParametersConstants.QC_DATE_FORMAT.parse( prevDate.getValue() ) );
                }
                if ( null != nextDate )
                {
                    qcForm.setNextRelease( ParametersConstants.QC_DATE_FORMAT.parse( nextDate.getValue() ) );
                }
            }
            catch ( ParseException e )
            {
                throw new WTransformerException( e );
            }
            StringParameterDTO includedReqRoot =
                (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_INCLUDED_REQ );
            if ( null != includedReqRoot )
            {
                qcForm.setQCIncludedReq( includedReqRoot.getValue() );
            }
            StringParameterDTO includedTestPlan =
                (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_INCLUDED_TEST_PLAN );
            if ( null != includedTestPlan )
            {
                qcForm.setQCIncludedTestPlan( includedTestPlan.getValue() );
            }
            StringParameterDTO includedTestLab =
                (StringParameterDTO) qcMap.getParameters().get( ParametersConstants.QC_INCLUDED_TEST_LAB );
            if ( null != includedTestLab )
            {
                qcForm.setQCIncludedTestLab( includedTestLab.getValue() );
            }

        }
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        Object[] objet = { new MapParameterDTO() };
        formToObj( form, objet );
        return objet;
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        TestManagerQCForm qcForm = (TestManagerQCForm) form;
        MapParameterDTO projectParams = (MapParameterDTO) object[0];
        MapParameterDTO qcParams = new MapParameterDTO();
        // Mandatory parameters
        qcParams.getParameters().put( ParametersConstants.QC_URL, new StringParameterDTO( qcForm.getQCUrl() ) );
        qcParams.getParameters().put( ParametersConstants.QC_PASSWORD, new StringParameterDTO( qcForm.getQCPassword() ) );
        qcParams.getParameters().put( ParametersConstants.QC_USER, new StringParameterDTO( qcForm.getQCLogin() ) );
        qcParams.getParameters().put( ParametersConstants.QC_DB_NAME, new StringParameterDTO( qcForm.getDataBaseName() ) );
        qcParams.getParameters().put( ParametersConstants.QC_ADMIN_DB_NAME,
                                      new StringParameterDTO( qcForm.getAdminDataBaseName() ) );
        qcParams.getParameters().put( ParametersConstants.QC_PREFIX_DB_NAME,
                                      new StringParameterDTO( qcForm.getPrefixDataBaseName() ) );
        qcParams.getParameters().put( ParametersConstants.QC_CLOSED_DEFECT,
                                      new StringParameterDTO( qcForm.getQCClosedDefectStatus() ) );
        qcParams.getParameters().put( ParametersConstants.QC_REQ_COVERED,
                                      new StringParameterDTO( qcForm.getQCCoveredReqStatus() ) );
        qcParams.getParameters().put( ParametersConstants.QC_RUN_OK, new StringParameterDTO( qcForm.getQCOkRunStatus() ) );
        qcParams.getParameters().put( ParametersConstants.QC_OPENED_REQ,
                                      new StringParameterDTO( qcForm.getQCOpenedReqStatus() ) );
        qcParams.getParameters().put( ParametersConstants.QC_STEP_PASSED,
                                      new StringParameterDTO( qcForm.getQCPassedStepStatus() ) );
        qcParams.getParameters().put( ParametersConstants.QC_TO_VALIDATE_REQ,
                                      new StringParameterDTO( qcForm.getQCToValidateReqStatus() ) );

        // Facultative parameters
        if ( null != qcForm.getPreviousRelease() )
        {
            qcParams.getParameters().put(
                                          ParametersConstants.QC_PREVIOUS_DATE,
                                          new StringParameterDTO(
                                                                  ParametersConstants.QC_DATE_FORMAT.format( qcForm.getPreviousRelease() ) ) );
        }
        if ( null != qcForm.getNextRelease() )
        {
            qcParams.getParameters().put(
                                          ParametersConstants.QC_LAST_DATE,
                                          new StringParameterDTO(
                                                                  ParametersConstants.QC_DATE_FORMAT.format( qcForm.getNextRelease() ) ) );
        }
        if ( qcForm.getQCIncludedReq().length() > 0 )
        {
            qcParams.getParameters().put( ParametersConstants.QC_INCLUDED_REQ,
                                          new StringParameterDTO( qcForm.getQCIncludedReq() ) );
        }
        if ( qcForm.getQCIncludedTestLab().length() > 0 )
        {
            qcParams.getParameters().put( ParametersConstants.QC_INCLUDED_TEST_LAB,
                                          new StringParameterDTO( qcForm.getQCIncludedTestLab() ) );
        }
        if ( qcForm.getQCIncludedTestPlan().length() > 0 )
        {
            qcParams.getParameters().put( ParametersConstants.QC_INCLUDED_TEST_PLAN,
                                          new StringParameterDTO( qcForm.getQCIncludedTestPlan() ) );
        }

        // add to project parameters
        projectParams.getParameters().put( ParametersConstants.QC, qcParams );
    }

}
