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
package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.EclipseUserLibForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.EclipseVarForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JCompilingForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JavaCompilationForm;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres de compilation java
 */
public class JCompilingConfTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc}
     * @return le formulaire de la compilation Java
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        JCompilingForm mackerForm = new JCompilingForm();
        objToForm( pObject, mackerForm );
        return mackerForm;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc} - les paramètres
     * @param pForm {@inheritDoc} - le formulaire de compilation Java
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        JCompilingForm jCompilingForm = (JCompilingForm) pForm;
        // On remplit le form
        StringParameterDTO dialectParam = (StringParameterDTO) params.getParameters().get( ParametersConstants.DIALECT );
        if ( dialectParam != null )
        {
            // dialect
            jCompilingForm.setDialect( dialectParam.getValue() );
        }
        // On remplit les règles de compilation de façon différente si il s'agit
        // de wsad, ant ou rsa
        List rules = fillCompilationRules( params );
        jCompilingForm.setCompilationRules( rules );
        // excludedDirs
        jCompilingForm.setExcludeDirectories( new String[0] );
        ListParameterDTO dirsList = (ListParameterDTO) params.getParameters().get( ParametersConstants.EXCLUDED_DIRS );
        if ( dirsList != null )
        {
            List dirs = dirsList.getParameters();
            Iterator it = dirs.iterator();
            String[] excludedDirs = new String[dirs.size()];
            int index = 0;
            // On parcours la liste des répertoires exclus afin de remplir
            // le tableau concerné du form
            while ( it.hasNext() )
            {
                StringParameterDTO dir = (StringParameterDTO) it.next();
                excludedDirs[index] = dir.getValue();
                index++;
            }
            jCompilingForm.setExcludeDirectories( excludedDirs );
        }
        // Le chemin vers le bundle eclipse
        StringParameterDTO bundlePath =
            (StringParameterDTO) params.getParameters().get( ParametersConstants.BUNDLE_PATH );
        if ( null != bundlePath )
        {
            // On modifie les deux valeurs représentant les versions d'eclipse car on ne sait pas
            // lequel il faut prendre
            // On le saura à l'affichage de la jsp et le champs needBundle sera modifié en fonction
            jCompilingForm.setNeedBundle( true );
            jCompilingForm.setBundlePath( bundlePath.getValue() );
            jCompilingForm.setBundlePathDefault( bundlePath.getValue() );
        }
        // Paramètres Eclipse
        MapParameterDTO eclipseParams = (MapParameterDTO) params.getParameters().get( ParametersConstants.ECLIPSE );

        if ( null != eclipseParams )
        {
            eclipseParamsObjToForm( eclipseParams, jCompilingForm );
        }
    }

    /**
     * Set eclipse parameters in form PRECONDITION : <code>eclipseParams</code> is not null
     * 
     * @param eclipseParams eclipse parameters
     * @param jCompilingForm form
     */
    private void eclipseParamsObjToForm( MapParameterDTO eclipseParams, JCompilingForm jCompilingForm )
    {
        StringParameterDTO isEclipseCompilation =
            (StringParameterDTO) eclipseParams.getParameters().get( ParametersConstants.ECLIPSE_COMPILATION );
        if ( null != isEclipseCompilation && isEclipseCompilation.getValue().equals( "false" ) )
        {
            jCompilingForm.setEclipseCompilation( false );
        }
        else
        {
            // On compilation avec Eclipse
            // Les variables eclipse
            MapParameterDTO eclipseVars =
                (MapParameterDTO) eclipseParams.getParameters().get( ParametersConstants.ECLIPSE_VARS );
            List vars = new ArrayList();
            if ( null != eclipseVars )
            {
                for ( Iterator it = eclipseVars.getParameters().keySet().iterator(); it.hasNext(); )
                {
                    String varName = (String) it.next();
                    vars.add( new EclipseVarForm(
                                                  varName,
                                                  ( (StringParameterDTO) eclipseVars.getParameters().get( varName ) ).getValue() ) );
                }
            }
            jCompilingForm.setEclipseVars( vars );
            // Les librairies eclipse
            MapParameterDTO eclipseLibs =
                (MapParameterDTO) eclipseParams.getParameters().get( ParametersConstants.ECLIPSE_LIBS );
            List libs = new ArrayList();
            if ( null != eclipseLibs )
            {
                for ( Iterator it = eclipseLibs.getParameters().keySet().iterator(); it.hasNext(); )
                {
                    String libName = (String) it.next();
                    ListParameterDTO libsParams = ( (ListParameterDTO) eclipseLibs.getParameters().get( libName ) );
                    String[] libsTab = new String[libsParams.getParameters().size()];
                    for ( int l = 0; l < libsParams.getParameters().size(); l++ )
                    {
                        libsTab[l] = ( (StringParameterDTO) libsParams.getParameters().get( l ) ).getValue();
                    }
                    libs.add( new EclipseUserLibForm( libName, libsTab ) );
                }
                jCompilingForm.setEclipseLibs( libs );
            }
            // Advanced options
            StringParameterDTO advancedOptions =
                (StringParameterDTO) eclipseParams.getParameters().get( ParametersConstants.ECLIPSE_ADVANCED_OPTIONS );
            if ( null != advancedOptions )
            {
                jCompilingForm.setAdvancedOptions( advancedOptions.getValue() );
            }
        }
    }

    /**
     * Retourne la liste des règles de compilation remplie avec les règles ant, wsad ou RSA
     * 
     * @param params les paramètres du projet
     * @return la liste des règles de compilation
     */
    private List fillCompilationRules( MapParameterDTO params )
    {
        // compilationRules
        ListParameterDTO antParams = (ListParameterDTO) params.getParameters().get( ParametersConstants.ANT );
        ListParameterDTO wsadParams = (ListParameterDTO) params.getParameters().get( ParametersConstants.WSAD );
        ListParameterDTO rsaParams = (ListParameterDTO) params.getParameters().get( ParametersConstants.RSA );

        List rules = new ArrayList();
        if ( antParams != null )
        {
            rules = fillAntCompilationRules( antParams );
        }
        else if ( wsadParams != null )
        {
            rules = fillWsadCompilationRules( params, wsadParams );
        }
        else if ( rsaParams != null )
        {
            rules = fillRsaCompilationRules( rsaParams );
        }
        return rules;
    }

    /**
     * @param pAntParams les paramètres ANT
     * @return la liste des règles de compilation ANT
     */
    private List fillAntCompilationRules( ListParameterDTO pAntParams )
    {
        ArrayList rules = new ArrayList();
        // On récupère les paramètres ant
        List antList = pAntParams.getParameters();
        Iterator antIt = antList.iterator();
        while ( antIt.hasNext() )
        {
            // On crée les formulaires de compilation avec
            // les paramètres récupérés
            MapParameterDTO antParameterMap = (MapParameterDTO) antIt.next();
            JavaCompilationForm ruleForm = new JavaCompilationForm();
            // le type de la règle
            ruleForm.setKindOfTask( ParametersConstants.ANT );
            Map antMap = antParameterMap.getParameters();
            // le fichier ant
            ruleForm.setAntFile( ( (StringParameterDTO) antMap.get( ParametersConstants.ANT_BUILD_FILE ) ).getValue() );
            StringParameterDTO targetParam = (StringParameterDTO) antMap.get( ParametersConstants.ANT_TARGET );
            // Si le target est rensigné on modifie le form en conséquence
            if ( targetParam != null )
            {
                ruleForm.setAntTaskName( targetParam.getValue() );
            }
            // On ajout le formulaire à la liste des règles de compilation
            rules.add( ruleForm );
        }
        return rules;
    }

    /**
     * @param params les paramètres du projet
     * @param pWsadParams les paramètres WSAD
     * @return les règles de compilation WSAD
     */
    private List fillWsadCompilationRules( MapParameterDTO params, ListParameterDTO pWsadParams )
    {
        ArrayList rules = new ArrayList();
        // On récupère les paramètres wsad
        List wsadList = pWsadParams.getParameters();
        Iterator wsadIt = wsadList.iterator();
        while ( wsadIt.hasNext() )
        {
            // On crée et on ajoute les formulaires de compilation
            // wsad à la liste des règles de compilation.
            StringParameterDTO pathParam = (StringParameterDTO) wsadIt.next();
            JavaCompilationForm ruleForm = new JavaCompilationForm();
            ruleForm.setKindOfTask( ParametersConstants.WSAD );
            ruleForm.setWorkspacePath( pathParam.getValue() );
            // Il se peut que le chemin vers le manifest soit renseigné
            MapParameterDTO projects_params =
                (MapParameterDTO) params.getParameters().get( ParametersConstants.WSAD_PROJECT_PARAM );
            if ( projects_params != null )
            {
                MapParameterDTO project_param =
                    (MapParameterDTO) projects_params.getParameters().get( ruleForm.getWorkspacePath() );
                if ( project_param != null )
                {
                    // On récupère le manifest
                    StringParameterDTO manifestParam =
                        (StringParameterDTO) project_param.getParameters().get( ParametersConstants.MANIFEST_PATH );
                    // Ne doit pas être nul si la map existe
                    ruleForm.setManifestPath( manifestParam.getValue() );
                }
            }
            rules.add( ruleForm );
        }
        return rules;
    }

    /**
     * @param pRsaParams les paramètres RSA
     * @return les règles de compilation RSA
     */
    private List fillRsaCompilationRules( ListParameterDTO pRsaParams )
    {
        ArrayList rules = new ArrayList();
        List rsaList = pRsaParams.getParameters();
        Iterator rsaIt = rsaList.iterator();
        while ( rsaIt.hasNext() )
        {
            JavaCompilationForm ruleForm = new JavaCompilationForm();
            ListParameterDTO projectList = (ListParameterDTO) rsaIt.next();
            StringParameterDTO path =
                (StringParameterDTO) projectList.getParameters().get( ParametersConstants.WORKSPACE_ID );
            ruleForm.setWorkspacePath( path.getValue() );
            ruleForm.setKindOfTask( ParametersConstants.RSA );
            if ( projectList.getParameters().size() > ParametersConstants.EAR_NAME_ID )
            {
                StringParameterDTO earNameParam =
                    (StringParameterDTO) projectList.getParameters().get( ParametersConstants.EAR_NAME_ID );
                ruleForm.setEarName( earNameParam.getValue() );
            }
            if ( projectList.getParameters().size() > ParametersConstants.MANIFEST_PATH_ID )
            {
                StringParameterDTO manifestParam =
                    (StringParameterDTO) projectList.getParameters().get( ParametersConstants.MANIFEST_PATH_ID );
                ruleForm.setManifestPath( manifestParam.getValue() );
            }
            rules.add( ruleForm );
        }
        return rules;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pForm {@inheritDoc} - le formulaire de compilation Java
     * @return le tableau des objets transformés
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pForm {@inheritDoc} - le formulaire de compilation Java
     * @param pObject {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO mapParameter = (MapParameterDTO) pObject[0];
        JCompilingForm jCompilingForm = (JCompilingForm) pForm;
        // Insertion des paramètres dans la map
        // Nettoyage des noms des répertoires à exclure
        SqualeWebActionUtils.cleanValues( jCompilingForm.getExcludeDirectories() );
        // dialect
        StringParameterDTO dialectParam = new StringParameterDTO();
        dialectParam.setValue( jCompilingForm.getDialect() );
        mapParameter.getParameters().put( ParametersConstants.DIALECT, dialectParam );
        // excludedDirs
        excludeDirsFormToObj( SqualeWebActionUtils.cleanValues( jCompilingForm.getExcludeDirectories() ), mapParameter );
        // wsad ou ant
        List compilationRules = jCompilingForm.getCompilationRules();
        boolean isAnt = false;
        if ( compilationRules.size() > 0 )
        {
            JavaCompilationForm javaForm = (JavaCompilationForm) compilationRules.get( 0 );
            if ( javaForm.getKindOfTask().compareTo( ParametersConstants.ANT ) == 0 )
            {
                // On récupère les paramètres ant
                isAnt = true;
                ListParameterDTO antList = addAntParameters( compilationRules );
                mapParameter.getParameters().put( ParametersConstants.ANT, antList );
            }
            else if ( javaForm.getKindOfTask().compareTo( ParametersConstants.WSAD ) == 0 )
            {
                // On ajoute les paramètres wsad
                addWSADParameters( mapParameter, compilationRules );
            }
            else
            {
                // On récupère les paramètres rsa
                ListParameterDTO rsaList = addRSAParameters( compilationRules );
                mapParameter.getParameters().put( ParametersConstants.RSA, rsaList );
            }
        }
        else
        {
            // On supprime le paramètre wsad ou ant
            mapParameter.getParameters().remove( ParametersConstants.WSAD );
            mapParameter.getParameters().remove( ParametersConstants.WSAD_PROJECT_PARAM );
            mapParameter.getParameters().remove( ParametersConstants.ANT );
            mapParameter.getParameters().remove( ParametersConstants.RSA );
        }
        // le chemin vers le bundle
        if ( 0 == jCompilingForm.getBundlePathPrior().length() )
        {
            // On supprime le paramètre
            mapParameter.getParameters().remove( ParametersConstants.BUNDLE_PATH );
        }
        else
        {
            // On l'ajoute aux paramètres
            StringParameterDTO bundleParam = new StringParameterDTO();
            // getBundlePathPrior retourne le bundle par défaut si le champs n'a pas été rempli
            bundleParam.setValue( jCompilingForm.getBundlePathPrior() );
            mapParameter.getParameters().put( ParametersConstants.BUNDLE_PATH, bundleParam );
        }
        // Paramètres eclipse si il ne s'agit pas d'un projet ANT
        if ( !isAnt )
        {
            setEclipseParams( jCompilingForm, mapParameter );
        }
    }

    /**
     * Set excludedDirs in the parameters map
     * 
     * @param excludedDirsTab excluded directories paths
     * @param mapParameter project parameters
     */
    private void excludeDirsFormToObj( String[] excludedDirsTab, MapParameterDTO mapParameter )
    {
        ListParameterDTO excludedDirsList = new ListParameterDTO();
        List paramsList = new ArrayList();
        // On remplit la liste avec les données du tableau
        for ( int i = 0; i < excludedDirsTab.length; i++ )
        {
            StringParameterDTO strParam = new StringParameterDTO();
            strParam.setValue( excludedDirsTab[i] );
            paramsList.add( strParam );
        }
        excludedDirsList.setParameters( paramsList );
        mapParameter.getParameters().put( ParametersConstants.EXCLUDED_DIRS, excludedDirsList );
        // si il n'y a plus de répertoires exclus, on supprime le paramètre correspondant
        if ( excludedDirsTab.length == 0 )
        {
            mapParameter.getParameters().remove( ParametersConstants.EXCLUDED_DIRS );
        }
    }

    /**
     * Modifie les paramètres eclipse
     * 
     * @param jCompilingForm le formulaire
     * @param mapParameter les paramètres du projet
     */
    private void setEclipseParams( JCompilingForm jCompilingForm, MapParameterDTO mapParameter )
    {
        // Compilation avec Eclipse
        MapParameterDTO eclipseParams = new MapParameterDTO();
        StringParameterDTO isEclipseCompilation = new StringParameterDTO( "false" );
        if ( jCompilingForm.getEclipseCompilation() )
        {
            isEclipseCompilation.setValue( "true" );
            // Les variables eclipse
            if ( jCompilingForm.getEclipseVars().size() > 0 )
            {
                MapParameterDTO varsMap = new MapParameterDTO();
                EclipseVarForm varForm;
                for ( int i = 0; i < jCompilingForm.getEclipseVars().size(); i++ )
                {
                    varForm = (EclipseVarForm) jCompilingForm.getEclipseVars().get( i );
                    varsMap.getParameters().put( varForm.getName(), new StringParameterDTO( varForm.getLib() ) );
                }
                eclipseParams.getParameters().put( ParametersConstants.ECLIPSE_VARS, varsMap );
            }
            // Les librairies utilisateur eclipse
            if ( jCompilingForm.getEclipseLibs().size() > 0 )
            {
                MapParameterDTO libsMap = new MapParameterDTO();
                EclipseUserLibForm userLibForm;
                for ( int i = 0; i < jCompilingForm.getEclipseLibs().size(); i++ )
                {
                    userLibForm = (EclipseUserLibForm) jCompilingForm.getEclipseLibs().get( i );
                    ListParameterDTO libsDTO = new ListParameterDTO();
                    List libsParam = new ArrayList( userLibForm.getLibs().length );
                    for ( int l = 0; l < userLibForm.getLibs().length; l++ )
                    {
                        libsParam.add( new StringParameterDTO( userLibForm.getLibs()[l] ) );
                    }
                    libsDTO.setParameters( libsParam );
                    libsMap.getParameters().put( userLibForm.getName(), libsDTO );
                }
                eclipseParams.getParameters().put( ParametersConstants.ECLIPSE_LIBS, libsMap );
            }
            // Advanced options
            if ( jCompilingForm.getAdvancedOptions().length() > 0 )
            {
                StringParameterDTO advancedOptions = new StringParameterDTO();
                advancedOptions.setValue( jCompilingForm.getAdvancedOptions().trim() );
                eclipseParams.getParameters().put( ParametersConstants.ECLIPSE_ADVANCED_OPTIONS, advancedOptions );
            }
        }
        eclipseParams.getParameters().put( ParametersConstants.ECLIPSE_COMPILATION, isEclipseCompilation );
        mapParameter.getParameters().put( ParametersConstants.ECLIPSE, eclipseParams );
    }

    /**
     * Une liste de liste à 3 éléments (nom ws, ear, manifest)
     * 
     * @param pCompilationRules les règles de compilation
     * @return les règles sous forme de paramètres
     */
    private ListParameterDTO addRSAParameters( List pCompilationRules )
    {
        // On crée la liste rsa
        ListParameterDTO rsaMap = new ListParameterDTO();
        for ( int i = 0; i < pCompilationRules.size(); i++ )
        {
            JavaCompilationForm javaForm = (JavaCompilationForm) pCompilationRules.get( i );
            ListParameterDTO projectList = new ListParameterDTO();
            StringParameterDTO wsPath = new StringParameterDTO();
            wsPath.setValue( javaForm.getWorkspacePath() );
            projectList.getParameters().add( wsPath );
            StringParameterDTO earName = new StringParameterDTO();
            earName.setValue( javaForm.getEarName() );
            projectList.getParameters().add( ParametersConstants.EAR_NAME_ID, earName );
            StringParameterDTO manifestPath = new StringParameterDTO();
            manifestPath.setValue( javaForm.getManifestPath() );
            projectList.getParameters().add( ParametersConstants.MANIFEST_PATH_ID, manifestPath );
            rsaMap.getParameters().add( projectList );
        }
        return rsaMap;
    }

    /**
     * Ajoute la liste des paramètres wsad au projet
     * 
     * @param pProjectParams les paramètres du projet
     * @param pCompilationRules les règles de compilation
     */
    private void addWSADParameters( MapParameterDTO pProjectParams, List pCompilationRules )
    {
        // On crée la liste wsad
        ListParameterDTO wsadList = new ListParameterDTO();
        // On crée les paramètres des projets de la liste wsad
        MapParameterDTO wsadMap = new MapParameterDTO();
        for ( int i = 0; i < pCompilationRules.size(); i++ )
        {
            JavaCompilationForm ruleForm = (JavaCompilationForm) pCompilationRules.get( i );
            StringParameterDTO wsPath = new StringParameterDTO();
            wsPath.setValue( ruleForm.getWorkspacePath() );
            wsadList.getParameters().add( wsPath );
            if ( ruleForm.getManifestPath().length() > 0 )
            {
                MapParameterDTO projectMap = new MapParameterDTO();
                StringParameterDTO manifest = new StringParameterDTO();
                manifest.setValue( ruleForm.getManifestPath() );
                projectMap.getParameters().put( ParametersConstants.MANIFEST_PATH, manifest );
                wsadMap.getParameters().put( ruleForm.getWorkspacePath(), projectMap );
            }
        }
        pProjectParams.getParameters().put( ParametersConstants.WSAD, wsadList );
        if ( wsadMap.getParameters().size() > 0 )
        {
            pProjectParams.getParameters().put( ParametersConstants.WSAD_PROJECT_PARAM, wsadMap );
        }
        else
        {
            // On la supprime
            pProjectParams.getParameters().remove( ParametersConstants.WSAD_PROJECT_PARAM );
        }
    }

    /**
     * @param pCompilationRules les règles de compilation
     * @return la liste des paramètres ant a ajouter au projet courant
     */
    private ListParameterDTO addAntParameters( List pCompilationRules )
    {
        ListParameterDTO antList = new ListParameterDTO();
        for ( int i = 0; i < pCompilationRules.size(); i++ )
        {
            MapParameterDTO antMap = new MapParameterDTO();
            Map antParams = new HashMap();
            StringParameterDTO pathParam = new StringParameterDTO();
            StringParameterDTO targetParam = new StringParameterDTO();
            JavaCompilationForm javaForm = (JavaCompilationForm) pCompilationRules.get( i );
            pathParam.setValue( javaForm.getAntFile() );
            antParams.put( ParametersConstants.ANT_BUILD_FILE, pathParam );
            // Si la target n'est pas vide, on l'enregistre aussi
            String target = javaForm.getAntTaskName();
            if ( target.length() > 0 )
            {
                targetParam.setValue( target );
                antParams.put( ParametersConstants.ANT_TARGET, targetParam );
            }
            antMap.setParameters( antParams );
            antList.getParameters().add( antMap );
        }
        return antList;
    }

}
