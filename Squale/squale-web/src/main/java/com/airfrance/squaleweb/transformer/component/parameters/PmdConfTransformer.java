package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.PmdForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres PMD Les paramètres PMD sont communs dans un contexte java pur ou java avec jsp, le
 * transformer tient compte du contexte
 */
public class PmdConfTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        PmdForm cpdForm = new PmdForm();
        objToForm( pObject, cpdForm );
        return cpdForm;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        PmdForm pmdForm = (PmdForm) pForm;
        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];
        MapParameterDTO pmdParams = new MapParameterDTO();
        projectParams.getParameters().put( ParametersConstants.PMD, pmdParams );
        StringParameterDTO param = new StringParameterDTO();
        param.setValue( pmdForm.getSelectedJavaRuleSet() );
        pmdParams.getParameters().put( ParametersConstants.PMD_JAVA_RULESET_NAME, param );
        if ( pmdForm.isJspSourcesRequired() )
        {
            param = new StringParameterDTO();
            param.setValue( pmdForm.getSelectedJspRuleSet() );
            pmdParams.getParameters().put( ParametersConstants.PMD_JSP_RULESET_NAME, param );
        }
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        PmdForm pmdForm = (PmdForm) pForm;
        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];
        MapParameterDTO pmdParams = (MapParameterDTO) projectParams.getParameters().get( ParametersConstants.PMD );
        TaskDTO task = (TaskDTO) pObject[1];
        Collection versions = (Collection) pObject[2];
        // On construit la liste des rulesets pour le java et le jsp
        HashSet javaSet = new HashSet();
        HashSet jspSet = new HashSet();
        for ( Iterator it = versions.iterator(); it.hasNext(); )
        {
            PmdRuleSetDTO dto = (PmdRuleSetDTO) it.next();
            if ( dto.getLanguage().equals( "java" ) )
            {
                javaSet.add( dto.getName() );
            }
            else
            { // Il s'agit forcement de jsp
                jspSet.add( dto.getName() );
            }
        }
        // Placement du ruleset pour java
        pmdForm.setJavaRuleSets( (String[]) javaSet.toArray( new String[] {} ) );
        // Les paramètre PMD peuvent ne pas être présents
        if ( pmdParams != null )
        {
            StringParameterDTO param =
                (StringParameterDTO) pmdParams.getParameters().get( ParametersConstants.PMD_JAVA_RULESET_NAME );
            // Le paramètre peut ne pas être renseigné
            if ( param != null )
            {
                pmdForm.setSelectedJavaRuleSet( param.getValue() );
            }
        }
        // On récupère les paramètres pour savoir s'il s'agit d'un profil JSP ou pas
        if ( jspRequired( task ) )
        {
            pmdForm.setJspSourcesRequired( true );
            pmdForm.setJspRuleSets( (String[]) jspSet.toArray( new String[] {} ) );
            // Les paramètre PMD peuvent ne pas être présents
            if ( pmdParams != null )
            {
                StringParameterDTO param =
                    (StringParameterDTO) pmdParams.getParameters().get( ParametersConstants.PMD_JSP_RULESET_NAME );
                // Le paramètre peut ne pas être renseigné
                if ( param != null )
                {
                    pmdForm.setSelectedJspRuleSet( param.getValue() );
                }
            }
        }
    }

    /**
     * @param pTask tâche
     * @return true si la tâche requiert le JSP
     */
    private boolean jspRequired( TaskDTO pTask )
    {
        boolean result = false;
        for ( Iterator it = pTask.getParameters().iterator(); it.hasNext() && ( result == false ); )
        {
            TaskParameterDTO param = (TaskParameterDTO) it.next();
            result = param.getName().equals( "language" ) && param.getValue().equals( "jsp" );
        }
        return result;
    }

}
