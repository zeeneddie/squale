package com.airfrance.squalix.tools.rsm;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.w3c.dom.Node;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.TaskData;
/**
 */
public class RSMConfiguration {

    /** les entetes des fichiers à analyser */
    private String[] mHeaders;

    /**
    * Espace de travail autorisé (voire réservé) à RSM : il permet d'accueillir 
    * tous les fichiers générés par RSM
    */
    private File mWorkspace = null;

    /**
     * Le chemin du rapport généré
     */
    private String mReportPath = null;

    /**
     * Le chemin du rapport de class généré après préprocessing
     */
    private String mClassReportPath = null;

    /**
     * Le chemin du rapport de méthodes générés après préprocessing
     */
    private String mMethodsReportPath = null;

    /**
     * Le chemin du fichier liste passé à RSM
     */
    private String mInputFile;

    /**
     * Le chemin du fichier liste passé à RSM
     */
    private String mAuxFile;

    /**
    * Projet à analyser
    */
    private ProjectBO mProject = null;

    /**
     * Extensions des fichiers à analyser
     */
    private String mExtensions[] = null;

    /** la commande a exécuter */
    private String mExecCommand;

    /**
     * Paramètres d'analyse des fichiers
     */
    private String mParseParameters[] = null;

    /**
     * Access method for the mWorkspace property.
     * 
     * @return   the current value of the mWorkspace property
     * @roseuid 42D3A4EB033E
     */
    public File getWorkspace() {
        return mWorkspace;
    }

    /**
     * @param pReportPath le chemin du rapport
     */
    public void setReportPath(String pReportPath) {
        mReportPath = pReportPath;
    }

    /**
     * Access method for the mReportsPath property.
     * 
     * @return   the current value of the mReportsPath property
     * @roseuid 42D3A4EB034E
     */
    public String getReportPath() {
        return mReportPath;
    }

    /**
     * @param pMethodsReportPath le chemin du rapport de méthodes
     */
    public void setMethodsReportPath(String pMethodsReportPath) {
        mMethodsReportPath = pMethodsReportPath;
    }

    /**
     * Access method for the mMethodsReportPath property.
     * 
     * @return   the current value of the mMethodsReportPath property
     * @roseuid 42D3A4EB034E
     */
    public String getMethodsReportPath() {
        return mMethodsReportPath;
    }

    /**
     * @param pClassReportPath le chemin du rapport de classes
     */
    public void setClassReportPath(String pClassReportPath) {
        mClassReportPath = pClassReportPath;
    }

    /**
     * Access method for the mClassReportPath property.
     * 
     * @return   the current value of the mReportsPath property
     * @roseuid 42D3A4EB034E
     */
    public String getClassReportPath() {
        return mClassReportPath;
    }

    /**
     * Rectourne le projet associé à la configuration.
     * @return le projet.
     * @roseuid 42DE0375023D
     */
    public ProjectBO getProject() {
        return mProject;
    }

    /**
     * Met en place l'instance du projet.
     * @param pProject le projet analysé.
     * @roseuid 42E5F0CF00D2
     */
    public void setProject(final ProjectBO pProject) {
        mProject = pProject;
    }

    /**
     * @return la commande pour exécuter RSM
     */
    public String getExecCommand() {
        return mExecCommand;
    }

    /**
     * @param pExecCommand la commande pour exécuter RSM
     */
    public void setExecCommand(String pExecCommand) {
        mExecCommand = pExecCommand;
    }

    /**
     * @return les paramètres pour le parsing
     */
    public String[] getParseParameters() {
        return mParseParameters;
    }

    /**
     * @param pParameters les paramètres pour le parsing
     */
    public void setParseParameters(String[] pParameters) {
        mParseParameters = pParameters;
    }

    /**
     * @return al liste des extensions
     */
    public String[] getExtensions() {
        return mExtensions;
    }

    /**
     * @param pExtensions la liste des extensions
     */
    public void setMExtensions(String[] pExtensions) {
        mExtensions = pExtensions;
    }

    /**
     * Met la configuration générale en place.
     * @param pNode le noeud XML à parser
     * @param pConfiguration l'instance de configuration à mettre en place.
     * @throws Exception si la configuration générale n'est pas correcte.
     * @roseuid 42D520A303C2
     */
    private static void setGeneral(final Node pNode, final RSMConfiguration pConfiguration) throws Exception {
        // Workspace
        pConfiguration.mWorkspace = new File(ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.workspace")).getFirstChild().getNodeValue().trim());
        // Chemin du fichier contenant les rapports
        pConfiguration.mReportPath = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.reportpath")).getFirstChild().getNodeValue().trim();
        // Chemin du rapport de méthodes après préprocessing
        pConfiguration.mMethodsReportPath = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.reportmethodspath")).getFirstChild().getNodeValue().trim();
        // Chemin du rapport de classes après préprocessing
        pConfiguration.mClassReportPath = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.reportclassespath")).getFirstChild().getNodeValue().trim();
        // Chemin du fichier auxillliaire utilisé pour le préprocessing
        pConfiguration.mAuxFile = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.auxfile")).getFirstChild().getNodeValue().trim();
        // Commande d'exécution
        pConfiguration.mExecCommand = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.execcommand")).getFirstChild().getNodeValue().trim();
        // Commande d'exécution
        pConfiguration.mInputFile = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.inputfile")).getFirstChild().getNodeValue().trim();
    }

    /**
     * Met la configuration du profil en place.
     * @param pNode le noeud XML à parser.
     * @param pConfiguration l'instance de configuration à mettre en place.
     * @roseuid 42D520A303D2
     */
    private static void setProfile(final Node pNode, final RSMConfiguration pConfiguration) {
        // Liste des extensions
        Node extensionsNode = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.profile.extensions"));
        // Les extensions sont séparées par des "," dans le fichier XML
        StringTokenizer token = new StringTokenizer(extensionsNode.getFirstChild().getNodeValue().trim(), ",");
        ArrayList extensions = new ArrayList();
        while (token.hasMoreTokens()) {
            extensions.add(token.nextToken().trim());
        }
        String[] type = new String[0];
        // transformation de la liste des extensions en tableau
        pConfiguration.mExtensions = (String[]) extensions.toArray(type);

        //  Liste des entetes
        Node headersNode = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.profile.entetes"));
        // Suivant le profile, il n'y a pas forcément d'en-tetes
        if (headersNode.hasChildNodes()) {
            // si il en a, ils sont séparés par des ","
            token = new StringTokenizer(headersNode.getFirstChild().getNodeValue().trim(), ",");
            ArrayList headers = new ArrayList();
            while (token.hasMoreTokens()) {
                headers.add(token.nextToken().trim());
            }
            String[] headersTab = new String[0];
            pConfiguration.mHeaders = (String[]) headers.toArray(headersTab);
        } else {
            pConfiguration.mHeaders = new String[0];
        }
        // Liste des paramètres de parsing
        Node parametersNode = ConfigUtility.getNodeByTagName(pNode, RSMMessages.getString("configuration.profile.parameters"));
        ArrayList parameters = new ArrayList();
        // Un tag par paramètre
        Node parameterNode = parametersNode.getFirstChild();
        while (null != parameterNode) {
            if (parameterNode.getNodeType() == Node.ELEMENT_NODE) {
                parameters.add(parameterNode.getFirstChild().getNodeValue());
            }
            parameterNode = parameterNode.getNextSibling();
        }
        // transformation en tableau
        pConfiguration.mParseParameters = (String[]) parameters.toArray(type);
    }

    /**
     * Parse le fichier de configuration afin d'en extraire la configuration au format 
     * objet.
     * @param pProject projet à analyser.
     * @param pFile nom du fichier de configuration.
     * @param pDatas la liste des paramètres temporaires du projet
     * @return la configuration demandée
     * @throws Exception si un problème de parsing apparait.
     * @roseuid 42B97169031D
     */
    public static RSMConfiguration build(final ProjectBO pProject, final String pFile, TaskData pDatas) throws Exception {
        RSMConfiguration config = new RSMConfiguration();
        config.mProject = pProject;
        // Recuperation de la configuration
        Node root = ConfigUtility.getRootNode(pFile, RSMMessages.getString("configuration.root"));
        Node general = ConfigUtility.getNodeByTagName(root, RSMMessages.getString("configuration.general"));
        if (null != general) {
            setGeneral(general, config);
        }
        // Récupération du noeud contenant la configuration des profils
        Node profilesNode = ConfigUtility.getNodeByTagName(root, RSMMessages.getString("configuration.profiles"));
        Node profileNode = profilesNode.getFirstChild();
        boolean found = false;
        // Recherche du profil associé au projet
        String valueProject = pProject.getProfile().getName();
        // On ne s'occupe pas du dialecte
        while (null != profileNode && !found) {
            String valueConfig = ConfigUtility.getAttributeValueByName(profileNode, RSMMessages.getString("configuration.profile.name"));
            if (profileNode.getNodeType() == Node.ELEMENT_NODE && valueConfig.equals(valueProject)) {
                found = true;
                // On parse la configuration associée au profil
                setProfile(profileNode, config);
            } else {
                profileNode = profileNode.getNextSibling();
            }
        }
        if (!found) {
            throw new RSMException(RSMMessages.getString("exception.no_profile") + valueProject);
        }
        return config;
    }

    /**
     * @return le fichier liste
     */
    public String getInputFile() {
        return mInputFile;
    }

    /**
     * @param pInputFile le fichier liste à passer à RSM
     */
    public void setInputFile(String pInputFile) {
        mInputFile = pInputFile;
    }

    /**
     * @param pWorkspace le workspace
     */
    public void setWorkspace(File pWorkspace) {
        mWorkspace = pWorkspace;
    }

    /**
     * @return les entetes
     */
    public String[] getHeaders() {
        return mHeaders;
    }

    /**
     * @param pHeaders les entetes
     */
    public void setHeaders(String[] pHeaders) {
        mHeaders = pHeaders;
    }

    /**
     * @return le chemin du fichier auxilliaire
     */
    public String getAuxFile() {
        return mAuxFile;
    }

    /**
     * @param pAuxFile le chemin du fichier auxilliaire
     */
    public void setAuxFile(String pAuxFile) {
       mAuxFile = pAuxFile;
    }

}
