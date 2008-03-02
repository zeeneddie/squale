package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.HashMap;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AnalyserForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transforme la configuration de l'analyseur de source
 */
public class AnalyserTransformer
    implements WITransformer
{

    /**
     * @param pObject le tableau des paramètres
     * @return le formulaire transformé
     * @throws WTransformerException si erreur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        AnalyserForm form = new AnalyserForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject les paramètres
     * @param pForm le formulaire
     * @throws WTransformerException si erreur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO projectParam = (MapParameterDTO) pObject[0];
        AnalyserForm analyserForm = (AnalyserForm) pForm;
        MapParameterDTO taskParam = (MapParameterDTO) projectParam.getParameters().get( ParametersConstants.ANALYSER );
        // Il se peut que les paramètres soient nuls
        if ( taskParam != null )
        {
            StringParameterDTO pathParam =
                (StringParameterDTO) taskParam.getParameters().get( ParametersConstants.PATH );
            if ( pathParam != null )
            {
                analyserForm.setPath( pathParam.getValue() );
            }
        }
    }

    /**
     * @param pForm le formulaire
     * @return le tableau des objets transformés à partir du formulaire
     * @throws WTransformerException si erreur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pForm le formulaire
     * @param pObject le tableau des paramètres
     * @throws WTransformerException si erreur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        AnalyserForm analyserForm = (AnalyserForm) pForm;
        // On ajoute les paramètres de la tâche aux paramètres
        // généraux du projet
        Map analyserParams = new HashMap();
        // path:
        StringParameterDTO path = new StringParameterDTO();
        path.setValue( analyserForm.getPath() );
        analyserParams.put( ParametersConstants.PATH, path );
        MapParameterDTO aMap = new MapParameterDTO();
        aMap.setParameters( analyserParams );
        params.getParameters().put( ParametersConstants.ANALYSER, aMap );
    }

}
