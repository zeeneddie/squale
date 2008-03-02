package com.airfrance.squalix.tools.cpd;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;

import net.sourceforge.pmd.cpd.AnyLanguage;
import net.sourceforge.pmd.cpd.Language;

/**
 * Détection de copier/coller en jsp
 */
public class JspCpdProcessing
    extends AbstractCpdProcessing
{
    /** Seuil de détection de copier/coller */
    private static final int JSP_THRESHOLD = 100;

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getLanguage()
     */
    protected Language getLanguage()
    {
        return new AnyLanguage( getExtensions()[0] );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getTokenThreshold()
     */
    protected int getTokenThreshold()
    {
        return JSP_THRESHOLD;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getExtension()
     */
    protected String[] getExtensions()
    {
        return new String[] { ".jsp" };
    }

    /**
     * Obtention des répertoires exclus de la compilation
     * 
     * @param pProjectParams paramètres du projet
     * @return répertories exclus sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getExcludedDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.JSP_EXCLUDED_DIRS );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getSourcesDirs(com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO)
     */
    protected ListParameterBO getSourcesDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.JSP );
    }

}
