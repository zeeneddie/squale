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
package com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters;

import java.text.SimpleDateFormat;

/**
 * Les paramètres des projets
 */
public class ParametersConstants
{

    // *********************** Projet JAVA *********************************

    // constantes définissant le dialect
    /** version 1.3 */
    public static final String JAVA1_3 = "1.3";

    /** version 1.4 */
    public static final String JAVA1_4 = "1.4";

    /** version 1.5 */
    public static final String JAVA1_5 = "1.5";

    // Les paramètres obligatoires:

    /** Le dialecte du language */
    public static final String DIALECT = "dialect";

    // Un projet java doit avoir le paramètre ant OU le paramètre WSAD

    // Cas d'un projet ant
    /** Projet ANT */
    public static final String ANT = "ant";

    /** Localisation ant du build.xml */
    public static final String ANT_BUILD_FILE = "buildfile";

    /** Nom de la target ant */
    public static final String ANT_TARGET = "target";

    // Cas d'un projet wsad
    /** Pour récupérer les projets WSAD */
    public static final String WSAD = "wsad";

    /** Pour récupérer les paramètres des projets wsad */
    public static final String WSAD_PROJECT_PARAM = "wsadProjectsParams";

    /** Indique le chemin vers le manifest */
    public static final String MANIFEST_PATH = "manifestPath";

    // Cas d'un projet rsa
    /** Pour récupérer les projets RSA */
    public static final String RSA = "rsa";

    /** L'index indiquant le chemin vers le workspace RSA */
    public static final int WORKSPACE_ID = 0;

    /** L'index indiquant le nom du projet ear si il y en a un */
    public static final int EAR_NAME_ID = 1;

    /** L'index indiquant le chemin vers le manifest */
    public static final int MANIFEST_PATH_ID = 2;

    // Les paramètres facultatifs:

    /** Le chemin vers le bundle eclipse */
    public static final String BUNDLE_PATH = "bundlePath";

    /** Les paramètres eclipse */
    public static final String ECLIPSE = "eclipseParameters";

    /** Les variables eclipse */
    public static final String ECLIPSE_VARS = "eclipseVars";

    /** Les librairies utilisateur eclipse */
    public static final String ECLIPSE_LIBS = "eclipseLibs";

    /** Advanced options for plugin command */
    public final static String ECLIPSE_ADVANCED_OPTIONS = "advancedOptions";

    /** Les répertoire exclus de la compilation */
    public static final String EXCLUDED_DIRS = "excludedDirs";

    /** Les répertoire exclus de la compilation des JSP */
    public static final String JSP_EXCLUDED_DIRS = "jspExcludedDirs";

    /** Les patterns à exclure */
    public static final String EXCLUDED_PATTERNS = "excludedPatterns";

    /** Les patterns à inclure */
    public static final String INCLUDED_PATTERNS = "includedPatterns";

    // Pour l'administrateur uniquement
    /** Le type de compilation */
    public static final String ECLIPSE_COMPILATION = "eclipseCompilation";

    // ************** Tache Clearcase ***************************************

    /** Pour récupérer les infos ClearCase */
    public static final String CLEARCASE = "clearcase";

    // Les paramètres obligatoires:

    /** Pour récupérer la liste des projets */
    public static final String APPLI = "appli";

    /** La branche ClearCase */
    public static final String BRANCH = "branch";

    /** Les vobs ClearCase */
    public static final String VOBS = "vobs";

    // ************** Scm task ***************************************

    /** To retrive Scm data */
    public static final String SCM = "scm";

    // Scm parameters:

    /** User to connect to the remote repository */
    public static final String SCMLOGIN = "scmlogin";

    /** Password */
    public static final String SCMPASSWORD = "scmpassword";
    
    /** Location **/
    public static final String SCMLOCATION = "scmlocation";

    
    //********************************** Generic Task **********************************
    
    /** Static final String : name of the task to retrieve the values */
    public static final String GENERICTASK = "genericTask";
    
    // The constants are "gt" prefixed
    
    /** Tool Directory */
    public static final String GENERICTASK_TOOLDIR = "gtToolLocation";

    /** Working Directory */
    public static final String GENERICTASK_WORKDIR = "gtWorkingDirectory";
    
    /** Results Directory */
    public static final String GENERICTASK_RESULTSDIR = "gtResultsLocation";
    
    /** Commands */
    public static final String GENERICTASK_COMMANDS = "gtCommands";
    
    // *************** Projet C++ ******************************************

    /** Les informations C++ */
    public static final String CPP = "cpp";

    // constantes définissant le dialect pour McCabe
    /** SUNWS_5X */
    public static final String SUNWS_5X = "Sunws_5x";

    /** SUNWS_5X */
    public static final String FORTE = "Forte";

    /** Script de compilation C++ */
    public static final String CPP_SCRIPTFILE = "scriptFile";
    
    // *************** Projet Cobol ******************************************
    /** Les informations C++ */
    public static final String COBOL = "cobol";

    // constantes définissant les dialectes pour McCabe
    /** IBM Enterprise Cobol II release 3*/
    public static final String IBM341 = "Ibm_341";

    /** IBM Enterprise Cobol II release 4*/
    public static final String IBM4 = "Ibm_4";

    // ********************* Tâche CppTest **********************************
    /** Informations Cpptest */
    public static final String CPPTEST = "cpptest";

    /** Liste des fichiers projet CppTest */
    public static final String CPPTEST_SCRIPT = "script";

    /** Nom du set de règles CppTest */
    public static final String CPPTEST_RULESET_NAME = "rulesetname";

    // ********************* Tâche checkstyle *****************************
    /** Nom du set de règles Checkstyle */
    public static final String CHECKSTYLE_RULESET_NAME = "rulesetname";

    
    // ********************* Tâche PMD ************************************
    /** Informations PMD */
    public static final String PMD = "pmd";

    /** Nom du jeu de règles PMD pour java */
    public static final String PMD_JAVA_RULESET_NAME = "javarulesetname";

    /** Nom du jeu de règles PMD pour jsp */
    public static final String PMD_JSP_RULESET_NAME = "jsprulesetname";

    // ********************* Tâche McCabe **********************************

    // Les paramètres obligatoires:

    /** Le chemin des sources */
    public static final String SOURCES = "sources";

    /** Le chemin des pages JSP dans le cas d'un profil J2EE */
    public static final String JSP = "jsp";

    /** Chemin vers le répertoire d'application web */
    public static final String WEB_APP = "webApp";

    /** Version du j2ee */
    public static final String J2EE_VERSION = "j2ee_version";

    // constantes définissant les versions
    /** version 2.2 */
    public static final String J2EE1_2 = "1.2";

    /** version 2.3 */
    public static final String J2EE1_3 = "1.3";

    /** version 2.4 */
    public static final String J2EE1_4 = "1.4";

    /** version 2.5 */
    public static final String J2EE1_5 = "1.5";

    /** tableau récapitulatif des versions disponibles */
    public static final String[] J2EE_VERSIONS = new String[] { J2EE1_2, J2EE1_3, J2EE1_4, J2EE1_5 };

    // ********************* Tâche Macker **********************************
    /** Information Macker */
    public static final String MACKER = "macker";

    /** L'emplacement du fichier de configuration */
    public static final String MACKER_CONFIGURATION = "configurationFile";

    // ********************* Tâche UMLQuality **********************************

    /** Informations UMLQuality */
    public static final String UMLQUALITY = "umlquality";

    /** L'emplacement du fichier xmi à analyser */
    public static final String UMLQUALITY_SOURCE_XMI = "xmi";

    // Les paramètres facultatifs:

    /** Les classes à exclure */
    public static final String MODEL_EXCLUDED_CLASSES = "modelExcludedClasses";

    // ********************* Tâche d'analyse du code source **********************************
    /** Informations analyser */
    public static final String ANALYSER = "analyser";

    /** Chemin vers l'arborescence des fichiers à analyser */
    public static final String PATH = "path";

    // ********************* Tâche de compilation dans le cas de projets déjà compilés *******
    /** Informations sur la compilation des projets déjà compilés */
    public static final String COMPILED = "compiled";

    /** Emplacement des sources compilées */
    public static final String COMPILED_SOURCES_DIRS = "compiledSourcesDirs";

    /** Le classpath dans le cas d'un projet java */
    public static final String CLASSPATH = "classpath";

    // ********************* Quality Center extraction task *******
    /** Entry in parameters map */
    public final static String QC = "qc";

    /** Path to data base */
    public final static String QC_URL = "url";

    /** User readonly to access data base */
    public final static String QC_USER = "user";

    /** Password */
    public final static String QC_PASSWORD = "password";

    /** Database name */
    public final static String QC_DB_NAME = "dbName";

    /** Admin database name */
    public final static String QC_ADMIN_DB_NAME = "dbAdminName";

    /** Prefix database name */
    public final static String QC_PREFIX_DB_NAME = "dbPrefixName";

    /** Date of last version */
    public final static String QC_LAST_DATE = "lastDate";

    /** Date of previous version */
    public final static String QC_PREVIOUS_DATE = "prevDate";

    /** format de date */
    public static final SimpleDateFormat QC_DATE_FORMAT = new SimpleDateFormat( "dd/MM/yyyy" );

    /** Included requirements */
    public final static String QC_INCLUDED_REQ = "incReq";

    /** Included TestPlan */
    public final static String QC_INCLUDED_TEST_PLAN = "incTestPlan";

    /** Included TestLab */
    public final static String QC_INCLUDED_TEST_LAB = "incTestLab";

    /** Covered requirements states */
    public final static String QC_REQ_COVERED = "coveredReq";

    /** Passed tests states */
    public final static String QC_TEST_PASSED = "passedTest";

    /** Passed steps states */
    public final static String QC_STEP_PASSED = "passedStep";

    /** Ok runs states */
    public final static String QC_RUN_OK = "okRun";

    /** Closed defects states */
    public final static String QC_CLOSED_DEFECT = "closedDefect";

    /** Opened requirements states */
    public final static String QC_OPENED_REQ = "openedReq";

    /** To validated requirements states */
    public final static String QC_TO_VALIDATE_REQ = "toValidatedReq";

}