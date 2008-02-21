package com.airfrance.squalix.tools.ruleschecking;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Commandline.Argument;

import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Processus checkstyle
 * L'exécution de l'outil Checkstyle se fait par
 * l'intermédiaire de cette classe. Elle exécute checkstyle
 * dans une tâche ant de type java pour permettre l'accès
 * aux jars spécifique de checkstyle qui sont en conflit
 * avec ceux de hibernate.
 * Le processus prend en entrée le fichier de configuration
 * au format xml ainsi que la liste des répertoires contenant
 * les fichiers à analyser. Il fournit en sortie un fichier
 * au format XML qui contient les trasngressions repérées
 */
public class CheckStyleProcess implements BuildListener {
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog(CheckStyleProcess.class);
    /** Répertoire des jars */
    private File mJarDirectory;
    /** Répertoire des rapports */
    private File mReportDirectory;
    /** Indicateur d'erreur d'exécution */
    private boolean mErrorOccurred;
    /** version des sources java */
    private String mJavaVersion;

    /**
     * Constructeur
     * @param pJarDirectory répertoire des jars
     * @param pReportDirectory répertoire des rapports
     * @param pJavaVersion la version des sources
     * @throws ConfigurationException si erreur
     */
    public CheckStyleProcess(File pJarDirectory, File pReportDirectory, String pJavaVersion) throws ConfigurationException {
        checkDirectory(pJarDirectory);
        mJarDirectory = pJarDirectory;
        checkDirectory(pReportDirectory);
        mReportDirectory = pReportDirectory;
        mJavaVersion = pJavaVersion;
    }

    /**
     * Vérification d'un répertoire
     * @param pDirectory répertoire
     * @throws ConfigurationException si répertoire inexistant
     */
    private void checkDirectory(File pDirectory) throws ConfigurationException {
        if (!pDirectory.exists() || !pDirectory.isDirectory()) {
            // On essaye de le créer sinon on retourne une exception
            if (!pDirectory.mkdirs()) {
                throw new ConfigurationException(RulesCheckingMessages.getString("error.bad_directory", pDirectory));
            }
        }
    }
    /**
     * Analyse des sources avec Checkstyle
     * @param pRuleFile fichier de règles
     * @param pSources répertoires source
     * @param pReportName nom du rapport généré
     * @return fichier généré
     */
    public File analyseSources(File pRuleFile, String[] pSources, String pReportName) {
        File result = new File(mReportDirectory, pReportName);
        Java task = createAntTask(pRuleFile, pSources, result);
        mErrorOccurred = false;
        task.execute();
        // mErrorOccured peut passer à true
        return result;
    }

    /**
     * Indique si une erreur s'est produite
     * @return true si une erreur s'est produite
     */
    public boolean hasErrorOccurred() {
        return mErrorOccurred;
    }

    /**
     * Création de la tâche ANT
     * @param pRuleFile fichier de règles
     * @param pSources sources à analyser
     * @param pResultFile fichier donnant le résultat de l'analyse
     * @return tâche ANT
     */
    private Java createAntTask(File pRuleFile, String[] pSources, File pResultFile) {
        Java task = new Java();
        Project antProject = new Project();
        antProject.addBuildListener(this);
        task.setProject(antProject);
        // On fork pour éviter les problèmes de classpath
        // liés à la version 2.5 de checkstyle qui utilise une version
        // non compatible de ANTLR dans le contexte hibernate
        task.setFork(true);
        task.setClassname("com.puppycrawl.tools.checkstyle.Main");
        // Création du classpath
        Path path = createClassPath(antProject);
        task.setClasspath(path);
        Argument arg = task.createArg();
        // Augmentation de la mémoire allouée à la JVM
        Argument jvmArg = task.createJvmarg();
        jvmArg.setValue("-Xmx128M");
        jvmArg = task.createJvmarg();
        jvmArg.setValue("-Xss1M");
        arg = task.createArg();
        arg.setValue("-f");
        arg = task.createArg();
        arg.setValue("xml");
        arg = task.createArg();
        arg.setValue("-o");
        arg = task.createArg();
        arg.setValue(pResultFile.getAbsolutePath());
        arg = task.createArg();
        arg.setValue("-c");
        arg = task.createArg();
        arg.setValue(pRuleFile.getAbsolutePath());
        // Passage en revue des différents répertoires source
        for (int i = 0; i < pSources.length; i++) {
            arg = task.createArg();
            arg.setValue("-r");
            arg = task.createArg();
            arg.setValue(pSources[i]);
        }
        return task;
    }

    /**
     * @param pAntProject projet ANT
     * @return classpath créé
     */
    private Path createClassPath(Project pAntProject) {
        Path path = new Path(pAntProject);
        // On cherche tous les fichiers jar présents dans le répertoire adéquat
        File[] jarFiles = mJarDirectory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
        for (int i = 0; i < jarFiles.length; i++) {
            path.setPath(jarFiles[i].getAbsolutePath());
        }
        // On ajoute le j2ee.jar en fonction de la version de java
        /* on crée un buffer pour définir le chemin du dossier 
         * contenant les ressources nécessaires à la compilation 
         */
        StringBuffer j2ee = new StringBuffer(RulesCheckingMessages.getString("dir.root.java"));
        j2ee.append("/");
        j2ee.append(mJavaVersion);
        j2ee.append("/");
        /* on crée le descripteur de fichier et on ajoute au path */
        File f = new File(j2ee.toString().replace('.', '_') + "j2ee.jar");
        path.setPath(f.getAbsolutePath());
        return path;
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#buildStarted(org.apache.tools.ant.BuildEvent)
     */
    public void buildStarted(BuildEvent event) {
        log(event);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#buildFinished(org.apache.tools.ant.BuildEvent)
     */
    public void buildFinished(BuildEvent event) {
        log(event);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#targetStarted(org.apache.tools.ant.BuildEvent)
     */
    public void targetStarted(BuildEvent event) {
        log(event);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#targetFinished(org.apache.tools.ant.BuildEvent)
     */
    public void targetFinished(BuildEvent event) {
        log(event);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#taskStarted(org.apache.tools.ant.BuildEvent)
     */
    public void taskStarted(BuildEvent event) {
        log(event);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#taskFinished(org.apache.tools.ant.BuildEvent)
     */
    public void taskFinished(BuildEvent event) {
        log(event);
    }

    /** (non-Javadoc)
     * @see org.apache.tools.ant.BuildListener#messageLogged(org.apache.tools.ant.BuildEvent)
     */
    public void messageLogged(BuildEvent event) {
        log(event);
    }

    /**
     * @param pEvent évènement
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
                mErrorOccurred = true;
                break;

            case Project.MSG_WARN :
                LOGGER.warn(message, exception);
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
                break;
        }
    }

}
