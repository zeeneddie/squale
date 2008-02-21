//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\util\\csv\\CSVParser.java

package com.airfrance.squalix.util.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

/**
 * Permet de parser un fichier CSV (comma separated values) et de le mapper avec un Bean selon 
 * un configuration.<br>
 * Les beans à mapper doivent implémenter l'interface <code>CSVBean</code> et posséder les 
 * setters publics adéquats.<br><br>
 * Les dépendances avec le logger JRAF et la classe 
 * com.airfrance.squalix.configurationmanager.ConfigUtility doivent être résolues.
 * <br>
 * Le fichier de mapping (ici csv-mapping.xml) :<br>
 * <code>
 * &nbsp;&nbsp;&lt;templates><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;template name="method"><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;csvbean>test.MonBean&lt;/csvbean><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;header size="2" /><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;footer size="3" /><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;field name="name" type="java.lang.String" column="0">&lt;/field><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;field name="misc" type="java.lang.String" column="1">&lt;/field><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;field name="val1" type="java.lang.Integer" column="2">&lt;/field><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;field name="val2" type="java.lang.Double" column="3">&lt;/field><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/template><br>
 * &nbsp;&nbsp;&lt;/templates><br>
 * </code>
 * 
 * Pour chaque template, spécifier :<br>
 * <ul>
 * <li>son nom avec l'attribut name</li>
 * <li>le nom de la classe correspondante avec l'élement csvbean</li>
 * <li>le nombre de lignes de l'en-tête</li>
 * <li>le nombre de lignes du pied de page</li>
 * <li>chaque attribut avec pour chacun :
 * <ul>
 * <li>son nom (attribut name) (si la valeur de cet attribut est monNom, le setter recherché est alors setMonNom)</li>
 * <li>son type objet (pas de type simple) qui accepte un constructeur avec une <code>String</code> en paramètre</li>
 * <li>le numéro de la colonne correspondante dans le fichier CSV (à partir de 0)</li>
 * </ul>
 * </li>
 * </ul> 
 * <br><br>
 * Les classes mappées doivent posséder les setters associés aux
 * attributs mappés sur le CSV.<br>
 * Exemple : si la classe possède un attribut nommé <code>name</code>, elle devra 
 * implémenter une méthode publique nommée <code>setName</code>.<br>
 * D'autre part les paramètres des setters ne peuvent être des types simples. Ils 
 * doivent être des objets qui possèdent un constructeur prenant un seul paramètre 
 * <code>String</code>. 
 * 
 * @author m400842
 * @version 1.0
 */
public class CSVParser {

    /**
     * Configuration du framework
     */
    private CSVConfiguration mConfiguration;

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactoryImpl.getLog(CSVParser.class);

    /**
     * Buffer de lecture du fichier CSV
     */
    private BufferedReader mBuffreader = null;

    /**
     * Chaine contenant l'expression régulière permettant de récupérer les champs du fichier CSV
     * séparément.
     */
    private String mREGEXPCSV = "\"([^\"]|\"\")*\"(,|$)|[^,]+(?=,|$)";

    /**
     * Instance du récupérateur de configuration
     */
    private CSVConfigurationGetter mConfigGetter = null;

    /**
     * Crée une nouvelle instance de CSVParser avec la configuration donnée.
     * 
     * @param pConfigFile Chemin du fichier de configuration
     * @throws CSVException si un problème apparaît
     * @roseuid 429431A903D2
     */
    public CSVParser(final String pConfigFile) {
        mConfigGetter = new CSVConfigurationGetter(pConfigFile);
    }

    /**
     * Crée une nouvelle instance de CSVParser
     * 
     * @throws CSVException si un problème apparaît
     * @roseuid 429431C201AF
     */
    public CSVParser() throws CSVException {
        mConfigGetter = new CSVConfigurationGetter();
    }

    /**
     * Retourne les valeurs ordonnées de la ligne sous la forme d'un vecteur
     * 
     * @return les éléments de la ligne
     * @roseuid 4294384A01A5
     */
    private ArrayList readNextLine() {
        ArrayList results = null;
        String line = null;
        Matcher m;
        Pattern p;
        try {
            // On crée le pattern de partage des valeurs
            p = Pattern.compile(mREGEXPCSV);
            // On récupère la nouvelle ligne
            line = mBuffreader.readLine();

            if (line != null) {
                results = new ArrayList();
                // On ajoute dans le tableau chacune des valeurs issues
                // du parsing de la ligne
                m = p.matcher(line);
                while (m.find()) {
                    results.add(m.group());
                }
            }
        } catch (Exception e) {
            // Si un problème de lecture apparait
            LOGGER.error(e);
        }

        return results;
    }

    /**
     * Charge le fichier, crée la collection de CSVBean correspondant, puis 
     * renvoie cette collection
     * 
     * @param pTemplateName le nom du modèle de parsing à appliquer.
     * @param pFilename le nom du fichier à parser.
     * @return la collection d'objets issus du fichier.
     * @throws CSVException si un problème de parsing apparaît.
     * 
     * @roseuid 4294287000A6
     */
    public Collection parse(final String pTemplateName, final String pFilename) throws CSVException {
        Collection beans = new ArrayList();
        CSVBeanInstanciator instanciator = new CSVBeanInstanciator();
        BeanCSVHandler handler = new BeanCSVHandler(beans, instanciator);
        parseLines(pTemplateName, pFilename, handler);
        return beans;
    }

    /**
     * Instanciation de bean par lecture de CSV
     *
     */
    class BeanCSVHandler implements CSVHandler {
        /** beans */
        private Collection mBeans;
        /** instanciateur */
        private CSVBeanInstanciator mInstanciator;
        /**
         * Constructeur
         * @param pBeans beans
         * @param pInstanciator instanciateur
         */
        public BeanCSVHandler(Collection pBeans, CSVBeanInstanciator pInstanciator) {
            mBeans = pBeans;
            mInstanciator = pInstanciator;
        }
        /** 
         * {@inheritDoc}
         * @see com.airfrance.squalix.util.csv.CSVParser.CSVHandler#processLine(java.util.ArrayList)
         */
        public void processLine(List pLine) {
            mBeans.add(fullfillBean(pLine, mInstanciator));
        }
    }
    /**
     * Parsing des lignes du fichier
     * @param pTemplateName template de définition
     * @param pFilename fichier à parser
     * @param pHandler handler de parsing
     * @throws CSVException si erreur
     */
    public void parseLines(final String pTemplateName, final String pFilename, CSVHandler pHandler) throws CSVException {
        // récupère la configuration
        mConfiguration = mConfigGetter.getConfiguration(pTemplateName);
        try {
            instanciateBufferedReader(pFilename);
            ArrayList values = readNextLine();
            // On compte le nombre de lignes à lire
            int fileSize = getLineCount(pFilename) - mConfiguration.getFooterSize();
            // analyse chaque ligne du fichier qui a été mise sous forme d'une 
            // liste d'objets
            for (int i = mConfiguration.getHeaderSize(); i < fileSize && null != values; i++) {
                pHandler.processLine(values);
                values = readNextLine();
            }
        } catch (Exception e) {
            // Si un problème de lecture du fichier apparaît.
            throw new CSVException(e);
        } finally {
            // on essaye de fermer le buffer dans tous les cas
            try {
                if (mBuffreader != null) {
                    mBuffreader.close();
                }
            } catch (IOException e1) {
                LOGGER.error(e1, e1);
            }
        }
    }

    /**
     * Remplit un bean avec les valeurs passées en paramètre.
     * 
     * @param pValues la liste des valeurs ordonnées.
     * @param pInstanciator l'instance d'instanciateur utilisée.
     * @return un objet rempli.
     */
    private Object fullfillBean(final List pValues, final CSVBeanInstanciator pInstanciator) {
        Object bean = null;
        String value = null;
        try {
            // On instancie un bean par ligne
            bean = pInstanciator.instanciate(mConfiguration.getCSVBean());
            for (int j = 0; j < pValues.size(); j++) {
                // Pour chaque valeur de la ligne, on attribue la valeur au 
                // bean, en récupérant le nom de l'attribut de la configuration.
                Method setter = mConfiguration.getMappingData(j);
                if (null != setter) {
                    value = String.valueOf(pValues.get(j));
                    pInstanciator.setValue(bean, setter, value);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e, e);
            bean = null;
        }
        return bean;
    }

    /**
     * Retourne le nombre de lignes du fichier
     * @param pFileName Le nom du fichier
     * @return Le nombre de ligne du fichier
     * @throws Exception si un problème de lecture apparait.
     * @roseuid 42DFB3500158
     */
    private int getLineCount(String pFileName) throws Exception {
        int count = 0;
        FileReader fr = null;
        BufferedReader br = null;
        fr = new FileReader(pFileName);
        br = new BufferedReader(fr);
        while (null != br.readLine()) {
            count++;
        }
        br.close();
        fr.close();
        return count;
    }

    /**
     * Instancie un BufferedReader lié au fichier
     * @param pFileName le nom du fichier
     * @throws Exception si un problème de lecture apparait.
     * @roseuid 42DFB3500168
     */
    private void instanciateBufferedReader(String pFileName) throws Exception {
        FileReader fr;
        mBuffreader = null;
        fr = new FileReader(pFileName);
        mBuffreader = new BufferedReader(fr);
        if (mConfiguration.getHeaderSize() > 0) {
            // S'il y a des lignes d'en-tête dont il ne faut pas tenir compte,
            // elles sont lues pour placer le lecteur de buffer à la première ligne utile.
            for (int i = 0; i < mConfiguration.getHeaderSize(); i++) {
                mBuffreader.readLine();
            }
        }
    }

    /**
     * Traitement des données d'un fichier CSV
     *
     */
    public interface CSVHandler {
        /**
         * Traitement d'une ligne
         * @param pLine ligne à lire, les données sont contenues dans une liste
         */
        public void processLine(List pLine);
    }
}
