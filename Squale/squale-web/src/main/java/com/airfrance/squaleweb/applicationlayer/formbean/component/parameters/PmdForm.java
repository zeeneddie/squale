package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.PmdConfTransformer;

/**
 * Formulaire de configuration de PMD
 */
public class PmdForm
    extends AbstractParameterForm
{

    /** Indication de la nécessite des sources JSP */
    private boolean mJspSourcesRequired;

    /**
     * Les rulesets disponibles en java
     */
    private String[] mJavaRuleSets;

    /**
     * Les rulesets disponibles en jsp
     */
    private String[] mJspRuleSets;

    /**
     * La version selectionnée en java
     */
    private String mSelectedJavaRuleSet;

    /**
     * La version selectionnée en jsp
     */
    private String mSelectedJspRuleSet;

    /**
     * Constructeur par défaut
     */
    public PmdForm()
    {
    }

    /**
     * @return les ruleset disponibles en java
     */
    public String[] getJavaRuleSets()
    {
        return mJavaRuleSets;
    }

    /**
     * @param pSelectedJavaRuleSet la version sélectionnée
     */
    public void setSelectedJavaRuleSet( String pSelectedJavaRuleSet )
    {
        mSelectedJavaRuleSet = pSelectedJavaRuleSet;
    }

    /**
     * @return ruleset java sélectionné
     */
    public String getSelectedJavaRuleSet()
    {
        return mSelectedJavaRuleSet;
    }

    /**
     * @param pJavaRuleSets les versions
     */
    public void setJavaRuleSets( String[] pJavaRuleSets )
    {
        mJavaRuleSets = pJavaRuleSets;
    }

    /**
     * @return les ruleset disponibles en jsp
     */
    public String[] getJspRuleSets()
    {
        return mJspRuleSets;
    }

    /**
     * @param pSelectedJspRuleSet la version sélectionnée
     */
    public void setSelectedJspRuleSet( String pSelectedJspRuleSet )
    {
        mSelectedJspRuleSet = pSelectedJspRuleSet;
    }

    /**
     * @return ruleset jsp sélectionné
     */
    public String getSelectedJspRuleSet()
    {
        return mSelectedJspRuleSet;
    }

    /**
     * @param pJspRuleSets les versions
     */
    public void setJspRuleSets( String[] pJspRuleSets )
    {
        mJspRuleSets = pJspRuleSets;
    }

    /**
     * @return true si les sources JSP sont requises
     */
    public boolean isJspSourcesRequired()
    {
        return mJspSourcesRequired;
    }

    /**
     * @param pJspSourcesRequired indicateur de sources JSP requises
     */
    public void setJspSourcesRequired( boolean pJspSourcesRequired )
    {
        mJspSourcesRequired = pJspSourcesRequired;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     *      {@inheritDoc}
     */
    public Class getTransformer()
    {
        return PmdConfTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     *      {@inheritDoc}
     */
    public String getNameInSession()
    {
        return "pmdForm";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.PMD };
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setSelectedJavaRuleSet( "" );
        setSelectedJspRuleSet( "" );
        setJavaRuleSets( new String[0] );
        setJspRuleSets( new String[0] );
        setJspSourcesRequired( false );
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "PmdTask";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // Vérification de la version java choisie
        setSelectedJavaRuleSet( getSelectedJavaRuleSet().trim() );
        if ( getSelectedJavaRuleSet().length() == 0 )
        {
            addError( "selectedJavaRuleSet", new ActionError( "error.field.required" ) );
        }
        // Vérification de la version jsp choisie
        if ( isJspSourcesRequired() )
        {
            setSelectedJspRuleSet( getSelectedJspRuleSet().trim() );
            if ( getSelectedJspRuleSet().length() == 0 )
            {
                addError( "selectedJspRuleSet", new ActionError( "error.field.required" ) );
            }
        }
    }
}
