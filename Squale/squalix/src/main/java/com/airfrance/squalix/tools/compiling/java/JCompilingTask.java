//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\JavaCompilingTask.java

package com.airfrance.squalix.tools.compiling.java;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.java.adapter.JComponent;
import com.airfrance.squalix.tools.compiling.java.beans.JRSAProject;
import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;
import com.airfrance.squalix.tools.compiling.java.beans.JXMLProject;
import com.airfrance.squalix.tools.compiling.java.compiler.impl.EclipseCompilerImpl;
import com.airfrance.squalix.tools.compiling.java.compiler.impl.JWSADCompilerImpl;
import com.airfrance.squalix.tools.compiling.java.compiler.impl.JXMLCompilerImpl;
import com.airfrance.squalix.tools.compiling.java.configuration.JCompilingConfiguration;
import com.airfrance.squalix.tools.compiling.java.parser.impl.JRSAParserImpl;
import com.airfrance.squalix.tools.compiling.java.parser.impl.JWSADParserImpl;
import com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADDotProjectParser;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Tâche de compilation JAVA.<br />
 * Compile à partir d'environnements WSAD 5.x et/ou de fichiers XML ANT.
 * Hérite de la classe mère <code>CompilingTask</code>.
 * @author m400832
 * @version 1.0
 */
public class JCompilingTask extends AbstractTask implements BuildListener {

    /**
     * le message associé à une erreur de compil
     * utilisé pour mettre les messages sous une bonne forme pour le web
     */
    private String mCompilErrorMessage;

    /**
     * Instance de configuration.
     */
    private JCompilingConfiguration mConfiguration = null;

    /**
     * Séparateur entre répertoires dans la HashMap de paramètres du 
     * <code>ProjectBO</code>.
     */
    private static final String SEPDIRS = ";";

    /**
     * Liste de JComponent.
     */
    private ArrayList mComponents = null;

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog(JCompilingTask.class);

    /**
     * Constructeur par défaut.
     */
    public JCompilingTask() {
        mName = "JCompilingTask";
        mCompilErrorMessage = "";
    }

    /**
     * Méthode de lancement de la tâche.
     * @throws TaskException en cas d'échec
     */
    public void execute() throws TaskException {
        try {
            mConfiguration = new JCompilingConfiguration();
            mComponents = new ArrayList();
            LOGGER.trace(CompilingMessages.getString("java.logs.task.initialized") + getProject().getName());
            /* création des composants */
            buildComponents();
            /* traitement des composants, compilation */
            process();
            /* modification des paramètres de sortie */
            modifyParameters();
            /* affichage des paramètres en mode debug */
            printParameters();
            // positionne les données sur la taille du file System
            affectFileSystemSize(mData.getData(TaskData.CLASSES_DIRS), true);

        } catch (Exception e) {
            throw new TaskException(e);
        }
    }

    /**
     * Cette méthode influe sur le <code>ProjectBO</code> en modifiant la 
     * <code>HashMap</code> de paramètres.
     * @throws Exception Exception lors de la modification.
     */
    private void modifyParameters() throws Exception {
        modifyClasspathInHashMap();
        modifyClassesDirInHashMap();
    }

    /**
     * 
     */
    private void modifyClassesDirInHashMap() {
        List classesDirs = new ArrayList();
        Iterator it = mComponents.iterator();
        /* si l'itérateur possède des éléments */
        if (null != it && it.hasNext()) {
            /* initialisation des variables utilisées par la boucle */
            JComponent jComp = null;

            /* tant que l'itérateur possède des éléments */
            while (it.hasNext()) {
                /* on crée un JComponent */
                jComp = (JComponent) it.next();
                /* si le JComponent est de type JWSADParserImpl 
                 * i.e. projet WSAD ou RSA avec compilation avec le plugin Eclipse */
                if (jComp.getComponent() instanceof EclipseCompilerImpl) {
                    // On ajoute la liste des répertoires
                    classesDirs.addAll(((EclipseCompilerImpl)jComp.getComponent()).getEclipseCompiler().getOutputDirs());
                    mData.putData(TaskData.CLASSES_DIRS, classesDirs);
                }
            }
        }
    }

    /**
     * Positionne la variable donnant le chemin du répertoire 
     * racine contenant les .class du projet
     * @param classesDir le chemin du répertoire contenant les .class
     * Paramètre fourni par les méthodes create***Components
     */
    private void modifyClassesDirInHashMap(String classesDir) {
        List classesDirs = new ArrayList();
        classesDirs.add(classesDir);
        mData.putData(TaskData.CLASSES_DIRS, classesDirs);
    }

    /**
     * Cette méthode influe sur le <code>ProjectBO</code> en modifiant la 
     * <code>HashMap</code> de paramètres, pour le paramètre <code>
     * project.parameter.classpath</code>.
     * @throws Exception Exception lors de la modification.
     */
    private void modifyClasspathInHashMap() throws Exception {
        //TODO : revoir cette méthode pour éviter de faire des instanceof!
        Iterator it = mComponents.iterator();

        /* si l'itérateur possède des éléments */
        if (null != it && it.hasNext()) {
            /* initialisation des variables utilisées par la boucle */
            JComponent jComp = null;
            StringBuffer buf = new StringBuffer();

            /* tant que l'itérateur possède des éléments */
            while (it.hasNext()) {
                /* on crée un JComponent */
                jComp = (JComponent) it.next();

                /* si le JComponent est de type JWSADParserImpl 
                 * i.e. projet WSAD */
                if (jComp.getComponent() instanceof JWSADParserImpl) {
                    /* alors on modifie le classpath pour McCabe */
                    buf.append(modifyClasspathInHashMapWSAD(((JWSADParserImpl) (jComp.getComponent())).getProjectList()));
                    /* on ajoute le séparateur de répertoires, i.e. ";" */
                    buf.append(SEPDIRS);

                    /* si le composant est de type JXMLCompilerImpl
                     * i.e. projet ANT */
                } else if ((jComp.getComponent()) instanceof JXMLCompilerImpl) {
                    /* alors on modifie le classpath pour McCabe */
                    buf.append((((JXMLCompilerImpl) (jComp.getComponent())).getXMLProject()));
                    /* on ajoute le séparateur de répertoires, i.e. ";" */
                    buf.append(SEPDIRS);
                } else if (jComp.getComponent() instanceof JRSAParserImpl) {
                    /* alors on modifie le classpath pour McCabe */
                    buf.append(modifyClasspathInHashMapWSAD(((JRSAParserImpl) (jComp.getComponent())).getProjectList()));
                    /* on ajoute le séparateur de répertoires, i.e. ";" */
                    buf.append(SEPDIRS);
                } else if (jComp.getComponent() instanceof EclipseCompilerImpl) {
                    /* alors on modifie le classpath pour McCabe */
                    buf.append(((EclipseCompilerImpl) (jComp.getComponent())).getEclipseCompiler().getClasspath());
                    /* on ajoute le séparateur de répertoires, i.e. ";" */
                    buf.append(SEPDIRS);
                }
            }
            jComp = null;

            /* on récupère la clé de la map de paramètres du projet 
             * dont la valeur associée sera le classpath */
            String key = TaskData.CLASSPATH;

            String value = (String) mData.getData(key);
            if (value == null) {
                /* sinon on crée une chaîne vide */
                value = "";
            }

            /* on met au propre le classpath */
            value = generateProperClasspath(value, buf.toString());

            buf = null;

            /* on modifie la hashmap de paramètres du projet en 
             * conséquence. */
            getData().putData(key, value);
        }
        it = null;
    }

    /**
     * Cette méthode influe sur le <code>ProjectBO</code> en modifiant la 
     * <code>HashMap</code> de paramètres, pour les projets WSAD.
     * @param pList liste de projets WSAD.
     * @throws Exception exception lors du traitement.
     * @return String valeur à ajouter à la clé.
     */
    private String modifyClasspathInHashMapWSAD(List pList) throws Exception {
        String value = null;

        /* si la liste de projets fournie n'est pas vide */
        if (null != pList) {
            Iterator it = pList.iterator();

            /* si l'itérateur possède des éléments */
            if (null != it && it.hasNext()) {

                /* on instancie les variables utilisées dans la boucle */
                JWSADProject project = null;
                StringBuffer cp = new StringBuffer();

                /* tant que l'itérateur a des éléments */
                while (it.hasNext()) {
                    /* on crée le bean JWSADProject */
                    project = (JWSADProject) it.next();
                    /* on ajoute le classpath du bean au buffer 
                     * NB : pas besoin d'ajouter de SEPDIRS car le classpath 
                     * retourné se termine toujours par un ";" */
                    cp.append(project.getClasspath());
                    /* on ajoute au buffer le chemin du répertoire contenant 
                     * les classes compilées */
                    cp.append(project.getDestPath());
                    /* on ajoute un séparateur de répertoires, i.e. ";" */
                    cp.append(SEPDIRS);
                }

                /* on affecte la valeur retournée*/
                value = cp.toString();

                /* on fait le ménage */
                cp = null;
                project = null;
            }
            it = null;
        }
        return value;
    }

    /**
     * Cette méthode influe sur le <code>ProjectBO</code> en modifiant la 
     * <code>HashMap</code> de paramètres, pour les projets WSAD.
     * @param pProject JXMLProject.
     * @throws Exception exception lors du traitement.
     * @return valeur à ajouter à la clé.
     */
    private String modifyClasspathInHashMapXML(JXMLProject pProject) throws Exception {
        String value = null;
        /* si le projet n'est pas nulle */
        if (null != pProject && null != pProject.getPath()) {

            /* on récupère la portion du chemin du projet XML contenu entre le 
             * début de ladite chaîne et le dernier "/" rencontré.
             * i.e. on récupère "/app/SQUALE" si le chemin est 
             * "/app/SQUALE/build.xml".
             */
            String path = pProject.getPath();
            value = path.substring(0, path.lastIndexOf(JCompilingConfiguration.UNIX_SEPARATOR));
        }
        return value;
    }

    /**
     * Méthode qui lance l'exécution des parseurs et compilateurs.
     * @throws Exception exception lors de l'exécution.
     */
    private void process() throws Exception {
        Iterator it = mComponents.iterator();

        /* si l'itérateur a des éléments */
        if (it != null && it.hasNext()) {

            /* tant qu'il en possède */
            while (it.hasNext()) {
                /* on active le composant */
                JComponent comp = (JComponent) (it.next());
                comp.getComponent().execute();
                // On récupère les éventuelles erreurs de traitement
                for (Iterator itErr = comp.getComponent().getErrors().iterator(); itErr.hasNext();) {
                    ErrorBO err = (ErrorBO) itErr.next();
                    initError(err.getInitialMessage(), err.getLevel());
                }
            }
        }
        it = null;
    }

    /**
     * Cette méthode lance la création des composants.
     * @throws Exception exception lors de la création.
     */
    private void buildComponents() throws Exception {
        // Le projet contient soit une définition de projet WSAD
        // soit une définition ANT, soit une définition RSA
        ListParameterBO wsadList = (ListParameterBO) mProject.getParameters().getParameters().get(ParametersConstants.WSAD);
        if (wsadList != null) {
            createWSADOrRSAComponents(mProject.getParameters(), wsadList.getParameters().iterator(), false);
        } else {
            ListParameterBO antList = (ListParameterBO) mProject.getParameters().getParameters().get(ParametersConstants.ANT);
            if (antList != null) {
                for (int i = 0; i < antList.getParameters().size(); i++) {
                    createXMLComponent((MapParameterBO) antList.getParameters().get(i));
                }
            } else {
                ListParameterBO rsaParams = (ListParameterBO) mProject.getParameters().getParameters().get(ParametersConstants.RSA);
                if (null != rsaParams) {
                    createWSADOrRSAComponents(mProject.getParameters(), rsaParams.getParameters().iterator(), true);
                } else {
                    /*  en cas d'exception */
                    throw new Exception(CompilingMessages.getString("java.exception.task.unsupported_component"));
                }
            }
        }
    }

    /**
     * Cette méthode crée les composants nécessaires à la compilation des 
     * projects de type WSAD.
     * @param pMap MapParameterBO contenant les 
     * informations nécessaires à la création des composants.
     * @param pProjectsIt l'itérateur sur les projets
     * @param pIsRSA si il s'agit d'une liste de projet type RSA
     * @throws Exception exception.
     */
    private void createWSADOrRSAComponents(MapParameterBO pMap, Iterator pProjectsIt, boolean pIsRSA) throws Exception {
        /* on initialise les variables utilisées par la boucle */
        ListParameterBO exludedDirs = (ListParameterBO) pMap.getParameters().get(ParametersConstants.EXCLUDED_DIRS);
        // On récupère le bundle eclipse
        String bundleDir = getEclipseBundleFile((StringParameterBO) pMap.getParameters().get(ParametersConstants.BUNDLE_PATH));
        // La liste des JWSADprojects
        List JWSADProjectList = new ArrayList(0);
        JWSADProject myProject = null;
        List projectParamsList = null;
        for (; pProjectsIt.hasNext();) {
            myProject = new JWSADProject();
            // Ajout du listener
            myProject.setListener(this);
            // On change le fichier du bundle
            myProject.setBundleDir(bundleDir);
            /* on affecte les attributs */
            String dirName = "";
            if (!pIsRSA) {
                StringParameterBO stringBO = (StringParameterBO) pProjectsIt.next();
                dirName = stringBO.getValue();
                // On récupère le manifest (dans le cas par exemple des plugins RCP)
                MapParameterBO projectsParams = (MapParameterBO) pMap.getParameters().get(ParametersConstants.WSAD_PROJECT_PARAM);
                if (null != projectsParams) {
                    MapParameterBO projectParamsMap = (MapParameterBO) projectsParams.getParameters().get(dirName);
                    if (null != projectParamsMap) {
                        StringParameterBO manifest = (StringParameterBO) projectParamsMap.getParameters().get(ParametersConstants.MANIFEST_PATH);
                        myProject.setManifestPath(manifest.getValue());
                    }
                }
                // On modifie les paramètres communs aux projets RSA ou WSAD
                setProjectParams(pMap, myProject, dirName);
                /* on ajoute le projet à la liste des projets */
                JWSADProjectList.add(myProject);
            } else {
                projectParamsList = ((ListParameterBO) pProjectsIt.next()).getParameters();
                dirName = ((StringParameterBO) projectParamsList.get(ParametersConstants.WORKSPACE_ID)).getValue();
                setProjectParams(pMap, myProject, dirName);
                // Si le projet est un projet RSA7, on parse avec le parser RSA
                // car il y a des petites différences au niveau de la construction du .classpath
                // On ajoute les informations nécessaires au projet pour le parsing RSA
                JRSAProject rsaProject = new JRSAProject(myProject);
                if (projectParamsList.size() > ParametersConstants.EAR_NAME_ID) {
                    String earName = ((StringParameterBO) projectParamsList.get(ParametersConstants.EAR_NAME_ID)).getValue();
                    rsaProject.setEARProjectName(earName);
                }
                if (projectParamsList.size() > ParametersConstants.MANIFEST_PATH_ID) {
                    String manifestPath = ((StringParameterBO) projectParamsList.get(ParametersConstants.MANIFEST_PATH_ID)).getValue();
                    rsaProject.setManifestPath(manifestPath);
                }
                JWSADProjectList.add(rsaProject);
            }
            if (!myProject.isWSAD()) {
                /* ce n'est pas un projet WSAD */
                throw new Exception(CompilingMessages.getString("java.exception.task.malformed_wsad_project_in_hashmap") + myProject.getPath());
            }
        }
        addEclipseOrWSADCompilerToComponents(pMap, JWSADProjectList, pIsRSA);

    }

    /**
     * @param pMap les paramètres de la tâche
     * @param pJWSADProjectList la liste des projets WSAD
     * @param pIsRSA si les projets sont de type RSA
     * @throws Exception si erreur lors de la création
     */
    private void addEclipseOrWSADCompilerToComponents(MapParameterBO pMap, List pJWSADProjectList, boolean pIsRSA) throws Exception {
        MapParameterBO eclipseMap = (MapParameterBO) pMap.getParameters().get(ParametersConstants.ECLIPSE);
        StringParameterBO isEclipse = null;
        MapParameterBO eclipseVars = null;
        MapParameterBO eclipseLibs = null;
        if (null != eclipseMap) {
            isEclipse = (StringParameterBO) eclipseMap.getParameters().get(ParametersConstants.ECLIPSE_COMPILATION);
            eclipseVars = (MapParameterBO) eclipseMap.getParameters().get(ParametersConstants.ECLIPSE_VARS);
            eclipseLibs = (MapParameterBO) eclipseMap.getParameters().get(ParametersConstants.ECLIPSE_LIBS);

        }
        if (null != isEclipse && isEclipse.getValue().equals("false")) {
            // On va parser les .classpath puis compiler avec javac
            /* on crée les composants : pour compiler les projets WSAD, il faut d'abord 
             * parser les fichiers ".classpath" puis compiler. D'où le recours à 
             * deux composants */
            JComponent j1 = new JComponent(new JWSADParserImpl(pJWSADProjectList));
            if (pIsRSA) {
                j1 = new JComponent(new JRSAParserImpl(pJWSADProjectList));
            }
            JWSADCompilerImpl jcawi = new JWSADCompilerImpl(pJWSADProjectList);
            JComponent j2 = new JComponent(jcawi);
            addToComponents(j1);
            addToComponents(j2);
        } else {
            // On compile avec le plugin eclipse
            // On récupère les variables et librairies définies par l'utilisateur
            EclipseCompilerImpl eclipseCompiler = new EclipseCompilerImpl(pJWSADProjectList, (String) mData.getData(TaskData.VIEW_PATH), eclipseVars, eclipseLibs);
            JComponent component = new JComponent(eclipseCompiler);
            addToComponents(component);
        }
    }

    /**
     * @param bundlePath le paramètre définissant le chemin vers le bundle eclipse
     * @return le chemin définitif et vérifié vers la distribution d'eclipse pour la compilation RCP
     */
    private String getEclipseBundleFile(StringParameterBO bundlePath) {
        // On récupère la version de distribution d'eclipse à utiliser
        // Si il s'agit d'un répertoire, on le dézippe sinon on utilise le chemin tel quel
        String bundleDir = "";
        if (null != bundlePath) {
            try {
                File bundleFile = FileUtility.getAbsoluteFile((String) mData.getData(TaskData.VIEW_PATH), new File(bundlePath.getValue()));
                if (bundleFile.isDirectory()) {
                    bundleDir = bundleFile.getAbsolutePath();
                } else {
                    // On extrait l'archive
                    FileUtility.copyOrExtractInto(bundleFile, new File(mConfiguration.getEclipseBundleDir()));
                    // on affecte le chemin donné dans la configuration
                    bundleDir = mConfiguration.getEclipseBundleDir();
                }
            } catch (Exception e) {
                // On va juste lancer un warning en affichant l'erreur car la compilation peut-être 
                // réussir sans le bundle
                String exceptionMsg = (e.getMessage() == null) ? e.getMessage() : e.toString();
                String message = CompilingMessages.getString("java.warning.eclipse_bundle_not_correct", new String[] { bundlePath.getValue(), exceptionMsg });
                LOGGER.warn(message);
                initError(message);
            }
        }
        return bundleDir;
    }

    /**
     * Modifie les paramètres communs d'un projet wsad ou rsa
     * @param pParams les paramètres
     * @param pProject le projet
     * @param pDirName le chemin du workspace
     */
    private void setProjectParams(MapParameterBO pParams, JWSADProject pProject, String pDirName) {
        // Ajout du listener
        pProject.setListener(this);
        /* on affecte les attributs */
        pProject.setName((new JWSADDotProjectParser(((String) mData.getData(TaskData.VIEW_PATH)) + pDirName)).retrieveName());
        pProject.setJavaVersion(((StringParameterBO) pParams.getParameters().get(ParametersConstants.DIALECT)).getValue());
        String viewPath = (String) mData.getData(TaskData.VIEW_PATH) + "/";
        String destDir = viewPath.replaceAll("//", "/");
        pProject.setPath((destDir + pDirName + "/").replaceAll("//", "/"));

        destDir += mConfiguration.getDestDir();

        pProject.setDestPath(destDir);
        this.modifyClassesDirInHashMap(destDir);
        // Prise en compte des répertoires exclus 
        addExcludedDirs(pProject, (ListParameterBO) pParams.getParameters().get(ParametersConstants.EXCLUDED_DIRS), viewPath);
        // On ajoute le paramètre relatif à la RAM dédiée qui 
        // sera utilisée lors de la compilation du projet
        pProject.setRequiredMemory(mConfiguration.getRequiredMemory());
        // Le classpath de l'API java a utiliser
        pProject.setBootClasspath(mConfiguration.getBootclasspath(pProject.getJavaVersion()));
    }

    /**
     * @param pProject le projet
     * @param pExcludedDirs les répertoires à exclure
     * @param pViewPath le chemin de la vue
     */
    private void addExcludedDirs(JWSADProject pProject, ListParameterBO pExcludedDirs, String pViewPath) {
        if (null != pExcludedDirs) {
            /* on ajoute au projet la liste des répertoires 
             * à exclure */
            for (Iterator it = pExcludedDirs.getParameters().iterator(); it.hasNext();) {
                // On vérifie que le répertoire appartient bien au projet
                String currentEx = (pViewPath + ((StringParameterBO) it.next()).getValue()).replaceAll("//", "/");
                if (currentEx.matches(pProject.getPath() + ".*")) {
                    pProject.addToExcludeDirs(currentEx);
                }
            }
        }

    }

    /**
     * Cette méthode crée un composant XML.
     * @param pAntMap le MapParameterBO contenant les 
     * informations nécessaires à la création du composant.
     * @throws Exception exception.
     */
    private void createXMLComponent(MapParameterBO pAntMap) throws Exception {

        /* on crée un bean project XML */
        JXMLProject project = new JXMLProject();
        StringParameterBO buildFile = (StringParameterBO) pAntMap.getParameters().get(ParametersConstants.ANT_BUILD_FILE);
        StringParameterBO antTarget = (StringParameterBO) pAntMap.getParameters().get(ParametersConstants.ANT_TARGET);

        if (buildFile == null) {
            /* on lance une exception en rapport */
            throw new Exception(CompilingMessages.getString("java.exception.task.malformed_xml_project_in_hashmap"));
        }
        project.setPath(makePath(buildFile.getValue()));
        if (antTarget != null) {
            String targ = antTarget.getValue();
            project.setTarget(targ);
        }

        // Ajout du listener
        project.setListener(this);
        /* on crée le composant */
        JXMLCompilerImpl jxci = new JXMLCompilerImpl(project);
        JComponent jc = new JComponent(jxci);

        /* et on l'ajoute à la liste */
        addToComponents(jc);
    }

    /**
     * Méthode d'ajout d'un <code>JComponent</code> à la liste 
     * <code>mComponents</code>.
     * @param pComponent <code>JComponent</code> à ajouter.
     */
    private void addToComponents(JComponent pComponent) {
        mComponents.add(pComponent);
    }

    /**
     * Cette méthode reforme une chemin complet
     * @param pName : * WSAD = nom du répertoire contenant le projet /
     * *XML : chemin vers le fichier XML.
     * @return le chemin complété.
     * @throws Exception exception lors du traitement.
     */
    private String makePath(String pName) throws Exception {
        /* on récupère le view_path depuis la hashmap */
        String sTmp = (String) getData().getData(TaskData.VIEW_PATH);

        /* on crée le buffer */
        StringBuffer value = new StringBuffer(sTmp);

        /* si il manque le "/" */
        if (!sTmp.endsWith("/") && !pName.startsWith("/")) {
            /* alors on l'ajoute */
            value.append(JCompilingConfiguration.UNIX_SEPARATOR);
        }

        /* on ajoute le pName */
        value.append(pName);

        /* si le source_path ne se termine pas par un "/" */
        if (!pName.endsWith("/")) {
            /* alors on l'ajoute */
            value.append(JCompilingConfiguration.UNIX_SEPARATOR);
        }

        return value.toString();
    }

    /**
     * Cette méthode est utilisée par la méthode 
     * <code>modifyClasspathInHashMap()</code> afin d'éviter les 
     * doublons dans la chaîne de caractères contenant le classpath.
     * @param pString1 1ère chaîne à tester.
     * @param pString2 2nde chaîne à tester.
     * @return le résultat du test.
     * @throws Exception exception lors du traitement.
     * @see #modifyClasspathInHashMap()
     */
    private String generateProperClasspath(String pString1, String pString2) throws Exception {
        /* CODE HISTORIQUE : à modifier en utilisant un HashSet */

        StringBuffer result = new StringBuffer();

        /* si l'une et / ou l'autre des chaînes fournies est nulle*/
        if (null == pString1) {
            /* alors on l'affecte vide */
            pString1 = "";
        }
        if (null == pString2) {
            /* alors on l'affecte vide */
            pString2 = "";
        }

        /* on crée une 1ère liste en cassant la 1ère chaîne selon 
         * le séparateur de classpath */
        ArrayList list1 = new ArrayList((List) Arrays.asList((String[]) (pString1.split(mConfiguration.getClasspathSeparator()))));

        /* on crée une 2nde liste en cassant la 2nde chaîne selon 
        * le séparateur de classpath */
        ArrayList list2 = new ArrayList((List) Arrays.asList((String[]) (pString2.split(mConfiguration.getClasspathSeparator()))));

        /* si aucune des listes n'est vide */
        if (null != list1 && null != list2) {
            int i = 0;

            /* tant que l'on a pas atteint l'extrémité de la 2nde liste */
            while (i < list2.size()) {
                /* si la première liste ne contient pas l'élément courant 
                 * de la seconde */
                if (!list1.contains(list2.get(i))) {
                    /* alors cet élément est ajouté à la 1ère liste */
                    list1.add(list2.get(i));
                }
                i++;
            }

            i = 0;

            /* tant que l'on a pas atteint l'extrémité de la 1ère liste */
            while (i < list1.size()) {
                /* si l'élément courant n'est pas vide */
                if (!"".equals(list1.get(i))) {
                    /* alors il est ajouté au buffer */
                    result.append(list1.get(i));
                    /* on ajoute le séparateur de classpath */
                    result.append(mConfiguration.getClasspathSeparator());
                }
                i++;
            }
        }

        /* on fait le ménage */
        list1 = null;
        list2 = null;

        /* si le buffer se résume à un séparateur de classpath */
        if (mConfiguration.getClasspathSeparator().equals(result.toString())) {
            /* alors on instance un nouveau buffer vierge */
            result = new StringBuffer();
        }

        /* on retourne le buffer */
        return result.toString();
    }

    /**
     * Cette méthode affiche des paramètres en mode DEBUG uniquement.
     */
    private void printParameters() {
        LOGGER.debug("Project: " + mProject.getName());
        LOGGER.debug("ClassPath:" + (String) (getData().getData(TaskData.CLASSPATH)));
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#buildStarted(org.apache.tools.ant.BuildEvent)
     */
    public void buildStarted(BuildEvent pEvent) {
        log(pEvent);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#buildFinished(org.apache.tools.ant.BuildEvent)
     */
    public void buildFinished(BuildEvent pEvent) {
        log(pEvent);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#targetStarted(org.apache.tools.ant.BuildEvent)
     */
    public void targetStarted(BuildEvent pEvent) {
        log(pEvent);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#targetFinished(org.apache.tools.ant.BuildEvent)
     */
    public void targetFinished(BuildEvent pEvent) {
        log(pEvent);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#taskStarted(org.apache.tools.ant.BuildEvent)
     */
    public void taskStarted(BuildEvent pEvent) {
        log(pEvent);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#taskFinished(org.apache.tools.ant.BuildEvent)
     */
    public void taskFinished(BuildEvent pEvent) {
        log(pEvent);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#messageLogged(org.apache.tools.ant.BuildEvent)
     */
    public void messageLogged(BuildEvent pEvent) {
        log(pEvent);
    }

    /**
     * Log JRAF
     * L'évènement ANT est redirigé vers le log JRAF
     * @param pEvent évènement ANT
     */
    private void log(BuildEvent pEvent) {
        // On adapte les logs en fonction de la gravité du log initial
        String message = pEvent.getMessage();
        Throwable exception = pEvent.getException();
        // Utilisation d'un switch car on ne peut pas
        // se passer du mapping entre en entier et une méthode
        // à appeler : on pourrait mémoriser dans une map
        // la méthode et l'entier mais celà est un peu lourd
        switch (pEvent.getPriority()) {
            case Project.MSG_ERR :
                LOGGER.error(message, exception);
                break;

            case Project.MSG_WARN :
                LOGGER.warn(message, exception);
                manageCompilError(message);
                break;

            case Project.MSG_INFO :
                LOGGER.info(message, exception);
                break;

            case Project.MSG_VERBOSE :
                LOGGER.trace(message, exception);
                break;

            case Project.MSG_DEBUG :
                LOGGER.debug(message, exception);
                break;

            default :
                // Par défaut on log sur le flux de warning
                LOGGER.warn(message, exception);
                // par défaut
                manageCompilError(message);
                break;
        }
    }

    /**
     * gère la création des erreurs de compilation
     * @param pMessage le message d'erreur
     */
    private void manageCompilError(String pMessage) {
        // petit filtrage
        if (pMessage.indexOf("see the compiler error output") == -1) {
            // rajoute "\n" pour formater l'affichage de la jsp
            mCompilErrorMessage += pMessage + "\n";
        }
        // on ne stocke que les erreurs de compil,pas les infos
        if (pMessage.indexOf("^") != -1) {
            // on enlève le chemin de la vue qui n'est pas une information nécessaire
            mCompilErrorMessage = mCompilErrorMessage.replaceAll((String) mData.getData(TaskData.VIEW_PATH), "");
            // détermine le niveau de criticité
            if (mCompilErrorMessage.toLowerCase().indexOf("warning") == -1) {
                initError(mCompilErrorMessage, ErrorBO.CRITICITY_FATAL);
            } else {
                initError(mCompilErrorMessage, ErrorBO.CRITICITY_WARNING);
            }
            // reset
            mCompilErrorMessage = "";
        } else { // pb de classpath sur les chargements de jar
            if (pMessage.indexOf(".jar") != -1) {
                //  on enlève le chemin de la vue qui n'est pas une information nécessaire
                mCompilErrorMessage = mCompilErrorMessage.replaceAll((String) mData.getData(TaskData.VIEW_PATH), "");
                // envoie du message niveau warning
                initError(mCompilErrorMessage, ErrorBO.CRITICITY_WARNING);
                // reset
                mCompilErrorMessage = "";
            }
        }
    }
}