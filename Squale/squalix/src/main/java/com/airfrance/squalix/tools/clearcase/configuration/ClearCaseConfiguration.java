//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\clearcase\\ClearCaseConfiguration.java

package com.airfrance.squalix.tools.clearcase.configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.clearcase.utility.ClearCaseStringCleaner;

/**
 * Cette classe gère la configuration pour les tâches de montage et démontage des vues ClearCase snapshot.
 * 
 * @author m400832 (by rose)
 * @version 2.1
 */
public class ClearCaseConfiguration
{

    /**
     * le répertoire correspondant au chemin de la vue + nom de la branche Permet de récupérer la taille du file system
     * que crée la tache
     */
    private String mWriteDirectory;

    /**
     * Provider de persistence
     */
    private IPersistenceProvider mPersistenceProvider;

    /**
     * Session hibernate
     */
    private ISession mSession;

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( ClearCaseConfiguration.class );

    /**
     * Constante Séparateur UNIX.
     */
    public static final String UNIX_SEPARATOR = "/";

    /**
     * Constante Espace.
     */
    public static final String SPACE = " ";

    /**
     * Constante Underscore.
     */
    public static final String UNDERSCORE = "_";

    /**
     * Commande permettant de monter une vue de travail (branche).<br />
     * Concerne les audits de suivi.
     */
    private String mMountWorkViewCommand;

    /**
     * Commande permettant de monter une vue de consultation (label).<br />
     * Concerne les audits de jalon.
     */
    private String mMountConsultationViewCommand;

    /**
     * Commande de démontage / suppression de la vue ClearCase.
     */
    private String mUmountViewCommand;

    /**
     * Commande permettant de vérifier si la vue existe déjà ou non.
     */
    private String mVerifyViewExistenceCommand;

    /**
     * Commande permettant de supprimer le répertoire récursivement dans le cas où le répertoire existe mais pas la vue
     */
    private String mRemoveDirectoryCommand;

    /**
     * Commande permettant de supprimer la vue quand le .view existe encore mais plus le répertoire
     */
    private String mAuxUnmountViewCommand;

    /**
     * Tableau contenant les vobs (principale et secondaires).
     */
    private String[] mVobList;

    /**
     * Option <code>-vob</code>.
     */
    private String mVobOption;

    /**
     * HashMap utilisée pour la réflexion.
     */
    private HashMap mMap = null;

    /**
     * Constructeur par défaut. <br />
     * Appelle les méthodes <code>getConfigurationFromXML(String)</code> et
     * <code>process(ProjectBO, AuditBo, String)</code>.
     * 
     * @throws Exception lance une exception si la configuration a échoué.
     * @param pProject projet.
     * @param pAudit audit.
     */
    public ClearCaseConfiguration( ProjectBO pProject, AuditBO pAudit )
        throws Exception
    {
        /* création de la session */
        mPersistenceProvider = PersistenceHelper.getPersistenceProvider();
        mSession = mPersistenceProvider.getSession();
        mSession.beginTransaction();

        /* on construit la hashmap de réflexion */
        createReflectionMap();

        /* on récupère la config depuis le fichier XML */
        getConfigurationFromXML( ClearCaseMessages.getString( "configuration.file" ) );

        MapParameterBO clearcaseMapBO = (MapParameterBO) pProject.getParameter( ParametersConstants.CLEARCASE );
        if ( null == clearcaseMapBO )
        {
            String message = ClearCaseMessages.getString( "clearcase.exception.no_configuration" );
            throw new ConfigurationException( message );
        }
        String branchName;
        /* audit de jalon */
        if ( pAudit.getType().equals( AuditBO.MILESTONE ) )
        {
            branchName = pAudit.getName();

            /* audit de suivi */
        }
        else
        {
            branchName =
                ( (StringParameterBO) clearcaseMapBO.getParameters().get( ParametersConstants.BRANCH ) ).getValue();
        }

        // Recupere l'application du projet
        String appli =
            ( (StringParameterBO) clearcaseMapBO.getParameters().get( ParametersConstants.APPLI ) ).getValue();

        List vobs =
            ( (ListParameterBO) clearcaseMapBO.getParameters().get( ParametersConstants.VOBS ) ).getParameters();
        /* on lance le remplacement des motifs par leur valeur */
        process( pProject, pAudit, branchName, appli, vobs );

        /* fermeture de la session */
        mSession.commitTransactionWithoutClose();
        mSession.closeSession();
    }

    /**
     * Remplace le tag <code>{APP_NAME}</code> par la valeur fournie en paramètre. Cette méthode est appelée par la
     * tâche <code>
     * ClearCaseTask</code>, une fois que cette dernière a récupéré le nom ClearCase de l'application.
     * 
     * @param pApplicationName valeur de remplacement du tag.
     */
    public void processApplicationName( String pApplicationName )
    {
        replace( ClearCaseMessages.getString( "tag.application.name" ), pApplicationName );
    }

    /**
     * Appelle les méthodes qui remplacent les patterns par leurs valeurs.
     * 
     * @param pProject projet.
     * @param pAudit audit.
     * @param pBranchName nom de la branche.
     * @param pAppli Application (au sens AF Clearcase i.e. vob d'adm)
     * @param pVobList la liste des vobs
     * @see #processVob(String)
     * @see #processBranchSuffix(String, String)
     * @see #processBranchName(AuditBO, String)
     * @see #printCommands()
     */
    private void process( ProjectBO pProject, AuditBO pAudit, String pBranchName, String pAppli, List pVobList )
    {
        processVob( pVobList );
        processBranchSuffix( pProject.getParent().getName(), pProject.getName() );
        processBranchName( pAudit, pBranchName );
        processApplicationName( pAppli );
    }

    /**
     * Casse la liste des vobs suivant le séparateur fourni dans le fichier <code>common_messages.properties</code>.
     * <br />
     * Remplace le tag <code>{VOB_NAME}</code> par la prmière chaîne trouvée.<br />
     * Remplace le tag <code>{VOB_LIST}</code>.
     * 
     * @param pVobList liste des vobs.
     */
    private void processVob( List pVobList )
    {
        /* s'il y a plusieurs noms de VOB */
        mVobList = new String[pVobList.size()];
        for ( int i = 0; i < pVobList.size(); i++ )
        {
            mVobList[i] = ( (StringParameterBO) pVobList.get( i ) ).getValue();
        }
        /* on remplace le tag {VOB_NAME} par la première chaîne trouvée. */
        replace( ClearCaseMessages.getString( "tag.vob.name" ), mVobList[0] );

        /* on crée la chaîne de remplacement du tag {VOB_LIST}. */
        StringBuffer buf = new StringBuffer();
        int i = 0;

        /* on itère que les éléments de mVobList */
        while ( i < mVobList.length )
        {
            buf.append( mVobOption + " " );
            String vobElement = mVobList[i++].trim();
            buf.append( vobElement );
            buf.append( ClearCaseConfiguration.SPACE );
        }

        /* on remplace le tag {VOB_LIST} */
        replace( ClearCaseMessages.getString( "tag.vob.list" ), buf.toString().trim() );

        buf = null;
    }

    /**
     * Remplace le tag <code>{BRANCH_NAME}</code> par la valeur fournie en paramètre.
     * 
     * @param pBranchName valeur de remplacement du tag.
     * @param pAudit audit.
     * @see #lowerBranchName(String)
     */
    private void processBranchName( AuditBO pAudit, String pBranchName )
    {
        replace( ClearCaseMessages.getString( "tag.branch.name" ), pBranchName );
        mWriteDirectory = pBranchName;
        /* si c'est un audit de jalon */
        if ( pAudit.getType().equals( AuditBO.MILESTONE ) )
        {

            /*
             * on met toute la commande en minuscules (n'a dans l'effet d'impact que sur le nom du jalon, le reste étant
             * déjà en minuscules)
             */
            mVerifyViewExistenceCommand = mVerifyViewExistenceCommand.toLowerCase();

            /*
             * on agit de la même manière pour les commandes de montage et de démontage d'une vue de jalon
             */
            mMountConsultationViewCommand = lowerBranchName( mMountConsultationViewCommand );
            mUmountViewCommand = lowerBranchName( mUmountViewCommand );
        }
    }

    /**
     * Remplace le tag <code>{BRANCH_SUFFIX}</code>.
     * 
     * @param pApplicationName nom de l'application.
     * @param pProjectName nom du projet.
     * @see ClearCaseStringCleaner#getCleanedStringFrom(String)
     * @see #replace(String, String)
     */
    private void processBranchSuffix( String pApplicationName, String pProjectName )
    {
        /*
         * on concatène le nom de l'application, un underscore, et le nom du projet
         */
        StringBuffer buf = new StringBuffer( ClearCaseStringCleaner.getCleanedStringFrom( pApplicationName ) );
        buf.append( ClearCaseConfiguration.UNDERSCORE );
        buf.append( ClearCaseStringCleaner.getCleanedStringFrom( pProjectName ) );

        /* on applique la modif aux commandes */
        replace( ClearCaseMessages.getString( "tag.branch.suffix" ), buf.toString() );
        buf = null;
    }

    /**
     * Cette méthode est appelée dans le cas d'un audit de jalon pour mettre en minuscules une partie précise (le nom de
     * la branche) de la commande.<br />
     * Typiquement, elle modifie la chaîne de la façon suivante : <br />
     * 
     * <pre>
     * /usr/atria/bin/Perl -S /DINB/outils/gcl/script/mkview.pl -application 
     * {APP_NAME} -vob /vobs/tonus_intranet -consultation TONUS_INTRANET_V1_2_ACT 
     * -vws /app/SQUALE/clearcase/cc_storage/views/
     * TONUS_INTRANET_V1_2_ACT_mon_application_mon_projet_squale.vws 
     * -login mon_application_mon_projet_squale -snap -dir 
     * /app/SQUALE/&lt;b&gt;TONUS_INTRANET_V1_2_ACT&lt;/b&gt;_mon_application_mon_projet_squale
     * </pre>
     * 
     * <br />
     * en <br />
     * 
     * <pre>
     * /usr/atria/bin/Perl -S /DINB/outils/gcl/script/mkview.pl -application 
     * {APP_NAME} -vob /vobs/tonus_intranet -consultation TONUS_INTRANET_V1_2_ACT 
     * -vws /app/SQUALE/clearcase/cc_storage/views/
     * TONUS_INTRANET_V1_2_ACT_mon_application_mon_projet_squale.vws 
     * -login mon_application_mon_projet_squale -snap -dir 
     * /app/SQUALE/&lt;b&gt;tonus_intranet_v1_2_act&lt;/b&gt;_mon_application_mon_projet_squale
     * </pre>
     * 
     * @param pCommand commande à modifier.
     * @return la commande modifiée.
     */
    private String lowerBranchName( String pCommand )
    {
        StringBuffer buf = new StringBuffer( pCommand );

        /*
         * on cherche la position du caractère situé juste après le dernier séparateur UNIX ("/") de la chaîne.
         */
        int pos = buf.lastIndexOf( ClearCaseConfiguration.UNIX_SEPARATOR ) + 1;

        /* longueur de la chaine. */
        int length = buf.length();

        /* on met en minuscules la chaine comprise entre "pos" et "length". */
        buf.replace( pos, length, buf.substring( pos, length ).toLowerCase() );

        return buf.toString();
    }

    /**
     * Cette méthode remplace un motif par sa valeur pour toutes les commandes UNIX disponibles dans le fichier xml de
     * configuration.
     * 
     * @param pPattern motif à remplacer. Typiquement <code>{MOTIF}</code>.
     * @param pValue valeur de remplacement.
     */
    private void replace( final String pPattern, final String pValue )
    {
        mUmountViewCommand = mUmountViewCommand.replaceAll( pPattern, pValue );
        mMountConsultationViewCommand = mMountConsultationViewCommand.replaceAll( pPattern, pValue );
        mMountWorkViewCommand = mMountWorkViewCommand.replaceAll( pPattern, pValue );
        mVerifyViewExistenceCommand = mVerifyViewExistenceCommand.replaceAll( pPattern, pValue );
        mAuxUnmountViewCommand = mAuxUnmountViewCommand.replaceAll( pPattern, pValue );
        mRemoveDirectoryCommand = mRemoveDirectoryCommand.replaceAll( pPattern, pValue );
    }

    /**
     * Cette méthode provoque la récupération des chemins, des commandes UNIX et des patterns nécessaires à la tâche
     * ClearCase.<br />
     * <b>ATTENTION :</b> les données de la balise <code>commands</code> doivent absolument être récupérées en
     * premier.<br />
     * En effet, la méthode <code>processFromXML(Node pNode)</code> sur le noeud <code>patterns</code> fait appel à
     * la méthode <code>replace(String pPattern, String pValue)</code> qui lance une <code>NullPointerException</code>
     * si les variables contenant les commandes UNIX n'ont pas été initialisées.
     * 
     * @param pFile chemin du fichier à charger.
     * @throws Exception en cas d'erreur de parsing du fichier XML de configuration.
     * @see #processFromXML(Node, String, String, String)
     * @see ConfigUtility
     */
    private void getConfigurationFromXML( final String pFile )
        throws Exception
    {
        /* on récupère le noeud racine */
        Node root = ConfigUtility.getRootNode( pFile, ClearCaseMessages.getString( "configuration.root" ) );

        if ( null != root )
        {

            /* on récupère le noeud concernant les commandes */
            Node myNode =
                ConfigUtility.getNodeByTagName( root, ClearCaseMessages.getString( "configuration.commands" ) );
            /*
             * on traite les noeuds command en appliquant la méthode "mapKeyValue"
             */
            processFromXML( myNode, ClearCaseMessages.getString( "configuration.commands.command" ),
                            ClearCaseMessages.getString( "configuration.commands.command.key" ), "mapKeyValue" );

            /* on récupère le noeud concernant les patterns */
            myNode = ConfigUtility.getNodeByTagName( root, ClearCaseMessages.getString( "configuration.patterns" ) );
            /* on traite les noeuds pattern en appliquant la méthode "replace" */
            processFromXML( myNode, ClearCaseMessages.getString( "configuration.patterns.pattern" ),
                            ClearCaseMessages.getString( "configuration.patterns.pattern.key" ), "replace" );

            /* on récupère le noeud concernant les options */
            myNode = ConfigUtility.getNodeByTagName( root, ClearCaseMessages.getString( "configuration.options" ) );
            /*
             * on traite les noeuds option en appliquant la méthode "mapKeyValue"
             */
            processFromXML( myNode, ClearCaseMessages.getString( "configuration.options.option" ),
                            ClearCaseMessages.getString( "configuration.options.option.key" ), "mapKeyValue" );

            myNode = null;
        }

        root = null;
    }

    /**
     * Dans un premier temps, cette méthode récupère l'attribut et la valeur d'un noeud donné. Puis elle appelle la
     * méthode dont le nom est passé en paramètre par réflexion. <br />
     * Elle ne fonctionne que si la méthode dont le nom est passé en paramètre prend elle-même pour paramètres 2
     * <code>java.lang.String</code>.
     * 
     * @param pNode le noeud XML à parser.
     * @param pRootAnchor le nom de la balise racine à trouver
     * @param pChildAnchor le nom de la / des balise(s) fille(s) à trouver.
     * @param pMethodName le nom de la méthode à appeler par réflexion. ATTENTION : on ne peut passer en paramètre que
     *            des méthodes prenant elles-même 2 <code>java.lang.String</code> en paramètres.
     * @throws Exception génère une exception si le nom du noeud ne correspond pas à une des valeurs définies dans le
     *             fichier de configuration <code>
     * com.airfrance.squalix.tools.clearcase.clearcase.properties</code>.
     * @see ConfigUtility
     */
    private void processFromXML( final Node pNode, final String pRootAnchor, final String pChildAnchor,
                                 final String pMethodName )
        throws Exception
    {
        /* on récupère le 1er noeud contenant une commande */
        Node myNode = ConfigUtility.getNodeByTagName( pNode, pRootAnchor );

        /*
         * instanciation des variables qui vont servir dans la boucle qui va suivre
         */
        String nodeName = null;
        String pattern = null;
        String attrName = null;
        String nodeValue = null;
        NamedNodeMap attrMap = null;

        /* tant que le noeud n'est pas nul */
        while ( null != myNode )
        {
            /* noeud de type ELEMENT */
            if ( Node.ELEMENT_NODE == myNode.getNodeType() )
            {
                /* on récupère le nom du noeud. */
                nodeName = myNode.getFirstChild().getNodeName();

                /* on récupère tous les attributs du noeud. */
                attrMap = ( myNode.getAttributes() );

                /* on récupère le nom de l'attribut qui nous intéresse. */
                attrName = (String) ( attrMap.getNamedItem( pChildAnchor ) ).getNodeValue().trim();

                if ( null != attrName && !"".equals( attrName ) )
                {
                    // On recupere la valeur de la balise
                    // le texte peut etre parsemé de commentaires
                    // utilsiés dans le migConfig
                    // d'où la nécessité de parcourir tous les fils de type
                    // texte
                    nodeValue = null;
                    NodeList nodes = myNode.getChildNodes();
                    for ( int i = 0; ( i < nodes.getLength() ) && ( nodeValue == null ); i++ )
                    {
                        Node currentNode = nodes.item( i );
                        if ( currentNode.getNodeType() == Node.TEXT_NODE )
                        {
                            /* valeur du noeud en question. */
                            String value = currentNode.getNodeValue().trim();
                            if ( value.length() > 0 )
                            {
                                nodeValue = value;
                            }
                        }
                    }
                    // Affichage d'un warning lorsque qu'aucune valeur n'a été trouvée
                    if ( nodeValue == null )
                    {
                        nodeValue = "";
                        LOGGER.warn( ClearCaseMessages.getString( "logs.cfg.empty" ) + attrName );
                    }
                    /*
                     * on crée la tableau de type de paramètres ici, un tableau de 2 String
                     */
                    Class[] param = { String.class, String.class };

                    /* on crée le tableau des 2 paramètres en rapport */
                    Object[] args = { attrName, nodeValue };

                    /* on invoque la méthode fournie en paramètre */
                    ( (Method) ( this.getClass().getDeclaredMethod( pMethodName, param ) ) ).invoke( this, args );
                }
                else
                {
                    /* on lance une exception */
                    throw new Exception( ClearCaseMessages.getString( "exception.xml.null_or_void_attribute" ) );
                }
            }
            /* on itère */
            myNode = myNode.getNextSibling();
        }

        /* ménage */
        myNode = null;
        nodeName = null;
        nodeValue = null;
        pattern = null;
        attrName = null;
        attrMap = null;
    }

    /**
     * Cette méthode attribue à des variables de classes des valeurs par réflexion.<br />
     * La méthode est utilisée, mais uniquement par réflexion.
     * 
     * @param pKey nom de commande.
     * @param pValue valeur de la commande.
     * @throws Exception exception si le nom de la commande passé en paramètre n'est pas reconnu.
     * @see #createReflectionMap()
     */
    protected void mapKeyValue( final String pKey, final String pValue )
        throws Exception
    {
        /*
         * on invoque le setter correspondant à la clé pKey, en lui passant la valeur pValue
         */
        Object[] obj = { pValue };
        ( (Method) ( mMap.get( pKey ) ) ).invoke( this, obj );
    }

    /**
     * Cette méthode crée une map contenant des clés associées à des méthodes de type setter. <br />
     * En procédant ainsi, on pourra facilement affecter une valeur à une variable par réflexion.
     * 
     * @throws Exception exception de réflexion.
     * @see #mapKeyValue(String, String)
     */
    private void createReflectionMap()
        throws Exception
    {
        /*
         * tableau contenant la classe du paramètre à passer à chaque setter. ici, java.lang.String.
         */
        Class[] param = { String.class };

        mMap = new HashMap();

        /* commande vérifiant l'existence d'une vue */
        mMap.put( ClearCaseMessages.getString( "configuration.commands.command.key.verify_view_existence" ),
                  this.getClass().getDeclaredMethod( "setVerifyViewExistenceCommand", param ) );

        /* commande montant une vue de travail -> audit de suivi */
        mMap.put( ClearCaseMessages.getString( "configuration.commands.command.key.mount_work_view" ),
                  this.getClass().getDeclaredMethod( "setMountWorkViewCommand", param ) );

        /* commande montant une vue de consultation -> audit de jalon */
        mMap.put( ClearCaseMessages.getString( "configuration.commands.command.key.mount_consultation_view" ),
                  this.getClass().getDeclaredMethod( "setMountConsultationViewCommand", param ) );

        /* commande de démontage d'une vue */
        mMap.put( ClearCaseMessages.getString( "configuration.commands.command.key.umount_view" ),
                  this.getClass().getDeclaredMethod( "setUmountViewCommand", param ) );

        /* commande de démontage d'une vue dans le cas où le répertoire n'existe plus */
        mMap.put( ClearCaseMessages.getString( "configuration.commands.command.key.aux_umount_view" ),
                  this.getClass().getDeclaredMethod( "setAuxUmountViewCommand", param ) );

        /* commande de suppression récursive du répertoire quand le .view n'existe plus */
        mMap.put( ClearCaseMessages.getString( "configuration.commands.command.key.remove_directory" ),
                  this.getClass().getDeclaredMethod( "setRemoveDirectoryCommand", param ) );

        /* option -vob */
        mMap.put( ClearCaseMessages.getString( "configuration.options.option.key.vob" ),
                  this.getClass().getDeclaredMethod( "setVobOption", param ) );
    }

    /**
     * Getter.
     * 
     * @return la commande UNIX permettant de monter une vue de consultation sous ClearCase.
     */
    public String getMountConsultationViewCommand()
    {
        return mMountConsultationViewCommand;
    }

    /**
     * Getter.
     * 
     * @return la commande UNIX permettant de monter une vue de travail sous ClearCase.
     */
    public String getMountWorkViewCommand()
    {
        return mMountWorkViewCommand;
    }

    /**
     * Getter.
     * 
     * @return la commande UNIX permettant de démonter une vue sous ClearCase.
     */
    public String getUmountViewCommand()
    {
        return mUmountViewCommand;
    }

    /**
     * Getter.
     * 
     * @return la commande UNIX permettant de vérifier l'existence d'une vue sous ClearCase.
     */
    public String getVerifyViewExistenceCommand()
    {
        return mVerifyViewExistenceCommand;
    }

    /**
     * Getter.
     * 
     * @return la valeur de l'option.
     */
    public String getVobOption()
    {
        return mVobOption;
    }

    /**
     * Setter.
     * 
     * @param pVobOption la valeur de l'option.
     */
    public void setVobOption( String pVobOption )
    {
        mVobOption = pVobOption;
    }

    /**
     * Setter.
     * 
     * @param pMountConsultationViewCommand la commande UNIX permettant de monter une vue de consultation sous
     *            ClearCase.
     */
    public void setMountConsultationViewCommand( String pMountConsultationViewCommand )
    {
        mMountConsultationViewCommand = pMountConsultationViewCommand;
    }

    /**
     * Setter.
     * 
     * @param pMountWorkViewCommand la commande UNIX permettant de monter une vue de travail sous ClearCase.
     */
    public void setMountWorkViewCommand( String pMountWorkViewCommand )
    {
        mMountWorkViewCommand = pMountWorkViewCommand;
    }

    /**
     * Setter.
     * 
     * @param pUmountViewCommand la commande UNIX permettant de démonter une vue sous ClearCase.
     */
    public void setUmountViewCommand( String pUmountViewCommand )
    {
        mUmountViewCommand = pUmountViewCommand;
    }

    /**
     * Setter.
     * 
     * @param pVerifyViewExistenceCommand la commande UNIX permettant de vérifier l'existence d'une vue sous ClearCase.
     */
    public void setVerifyViewExistenceCommand( String pVerifyViewExistenceCommand )
    {
        mVerifyViewExistenceCommand = pVerifyViewExistenceCommand;
    }

    /**
     * @return le répertoire ou clearcase travaille
     */
    public String getWriteDirectory()
    {
        return mWriteDirectory;
    }

    /**
     * @return la commande pour supprimer le répertoire
     */
    public String getRemoveDirectoryCommand()
    {
        return mRemoveDirectoryCommand;

    }

    /**
     * @return la commande pour supprimer la vue dans le cas où le répertoire n'existe plus
     */
    public String getAuxUmountViewCommand()
    {
        return mAuxUnmountViewCommand;

    }

    /**
     * @param pRemoveDirectoryCommand la commande pour supprimer le répertoire
     */
    public void setRemoveDirectoryCommand( String pRemoveDirectoryCommand )
    {
        mRemoveDirectoryCommand = pRemoveDirectoryCommand;

    }

    /**
     * @param pAuxUmountViewCommand la commande pour supprimer la vue si le répertoire n'existe plus
     */
    public void setAuxUmountViewCommand( String pAuxUmountViewCommand )
    {
        mAuxUnmountViewCommand = pAuxUmountViewCommand;

    }

}
