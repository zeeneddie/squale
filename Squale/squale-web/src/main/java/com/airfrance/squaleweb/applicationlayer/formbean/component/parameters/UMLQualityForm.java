package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.UMLQualityConfTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * @author E6400802 Formulaire pour la configuration de la tâche UMLQuality
 */
public class UMLQualityForm
    extends AbstractParameterForm
{

    /** Chemin relatif ou absolu vers le fichier XMI */
    private String mXmiFile;

    /**
     * expressions régulières des composants à exclure de l'analyse
     */
    private String[] mExcludeUMLPatterns;

    /**
     * Constructeur par défaut
     */
    public UMLQualityForm()
    {
        mXmiFile = "";
        mExcludeUMLPatterns = new String[0];
    }

    /**
     * @return le chemin du fichier XMI
     */
    public String getXmiFile()
    {
        return mXmiFile;
    }

    /**
     * Modifie le chemin du fichier XMI
     * 
     * @param pXmiFile le nouveau chemin du fichier XMI
     */
    public void setXmiFile( String pXmiFile )
    {
        mXmiFile = pXmiFile;
    }

    /**
     * @return patterns des classes à exclure de l'analyse
     */
    public String[] getExcludeUMLPatterns()
    {
        return mExcludeUMLPatterns;
    }

    /**
     * @param pExcludePatterns patterns des classes à exclure de l'analyse
     */
    public void setExcludeUMLPatterns( String[] pExcludePatterns )
    {
        mExcludeUMLPatterns = pExcludePatterns;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     *      {@inheritDoc}
     */
    public Class getTransformer()
    {
        return UMLQualityConfTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     *      {@inheritDoc}
     */
    public String getNameInSession()
    {
        return "umlQualityForm";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.UMLQUALITY };
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setXmiFile( "" );
        setExcludeUMLPatterns( new String[0] );
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "UMLQualityTask";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        setExcludeUMLPatterns( SqualeWebActionUtils.cleanValues( getExcludeUMLPatterns() ) );
        getXmiFile().trim();
        if ( getXmiFile().length() == 0 )
        {
            addError( "xmiFile", new ActionError( "error.field.required" ) );
        }
    }

}
