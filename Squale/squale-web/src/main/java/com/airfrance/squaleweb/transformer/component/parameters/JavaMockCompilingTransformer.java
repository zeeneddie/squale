package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JavaMockCompilingForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformeur de paramètres pour la compilation java dans le cas
 * de projet déjà compilé
 */
public class JavaMockCompilingTransformer implements WITransformer {

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * {@inheritDoc}
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        JavaMockCompilingForm ckjmForm = new JavaMockCompilingForm();
        objToForm(pObject, ckjmForm);
        return ckjmForm;
    }


    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new MapParameterDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     * {@inheritDoc}
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        JavaMockCompilingForm taskForm = (JavaMockCompilingForm) pForm;
        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];
        MapParameterDTO taskParam = new MapParameterDTO();
        ListParameterDTO sources = new ListParameterDTO();
        // Insertion des paramètres dans la map
        // sources compilées:
        fillCompiledSourcesList(sources, taskForm.getCompiledSources());
        taskParam.getParameters().put(ParametersConstants.COMPILED_SOURCES_DIRS, sources);
        // classpath
        StringParameterDTO classpath = new StringParameterDTO();
        classpath.setValue(taskForm.getClasspath());
        taskParam.getParameters().put(ParametersConstants.CLASSPATH, classpath);
        // dialect
        StringParameterDTO dialect = new StringParameterDTO();
        dialect.setValue(taskForm.getDialect());
        projectParams.getParameters().put(ParametersConstants.DIALECT, dialect);
        projectParams.getParameters().put(ParametersConstants.COMPILED, taskParam);
    }

    /**
     * Remplissage d'une liste de sources
     * @param pSourcesList liste à remplir
     * @param pSourcesTab source à y insérer
     */
    protected void fillCompiledSourcesList(ListParameterDTO pSourcesList, String[] pSourcesTab) {
        ArrayList sourcesList = new ArrayList();
        for (int i = 0; i < pSourcesTab.length; i++) {
            StringParameterDTO strParamSource = new StringParameterDTO();
            strParamSource.setValue(pSourcesTab[i]);
            sourcesList.add(strParamSource);
        }
        pSourcesList.setParameters(sourcesList);
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        // on positionne les paramètres du formulaire
        JavaMockCompilingForm taskForm = (JavaMockCompilingForm) pForm;
        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];
        MapParameterDTO taskParam = (MapParameterDTO) projectParams.getParameters().get(ParametersConstants.COMPILED);
        if (taskParam != null) {
            // Liste des sources compilées
            ListParameterDTO sourcesDTO = (ListParameterDTO) taskParam.getParameters().get(ParametersConstants.COMPILED_SOURCES_DIRS);
            if (sourcesDTO != null) {
                taskForm.setCompiledSources(convertList(sourcesDTO));
            }
            // Le classpath
            StringParameterDTO classpathDTO = (StringParameterDTO) taskParam.getParameters().get(ParametersConstants.CLASSPATH);
            if(classpathDTO != null) {
                taskForm.setClasspath(classpathDTO.getValue());
            }
            // le dialect
            StringParameterDTO dialectDTO = (StringParameterDTO) projectParams.getParameters().get(ParametersConstants.DIALECT);
            if(dialectDTO != null) {
                taskForm.setDialect(dialectDTO.getValue());
            }
        }
    }

    /**
     * Conversion d'une liste de sources compilées
     * @param pSourcesList liste de sources compilées
     * @return liste des sources compilées
     */
    protected String [] convertList(ListParameterDTO pSourcesList) {
      List sourcesParam = pSourcesList.getParameters();
      int size = sourcesParam.size();
      String[] sources = new String[size];
      for(int i=0; i<size; i++) {
          StringParameterDTO source = (StringParameterDTO) sourcesParam.get(i);
          sources[i] = source.getValue().trim();

      }
      return sources;  
    }

}
