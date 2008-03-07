//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\core\\Task.java

package com.airfrance.squalix.core;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.result.ErrorDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.IMailerProvider;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squalix.messages.Messages;

/**
 * Représente une façade permettant d'exécuter une tâche.<br>
 * Une tâche est responsable de la création et de la persistance de ses objets de résultats (implémentant l'interface
 * <code>MeasureBO</code>) ainsi que de ses erreurs (classe <code>ErrorBO</code>). <br />
 * <br />
 * Une tâche peut posséder :
 * <ul>
 * <li>une tâche parent : tant que la tâche parent n'est pas terminée avec succès, celle-ci ne peut être lancée,</li>
 * <li>et/ou des tâches enfants : lancées dès la complétude avec succès de la tâche courante.
 * <li>
 * </ul>
 * <br />
 * Les attributs nécessaires à une classe lui sont attribués via un pattern IOC. Une fois que la tâche a été instanciée,
 * c'est son observateur (le Scheduler ou l'exécuteur d'analyse) qui lui transmet l'instance d'audit, de projet, son
 * statut, et ses tâches enfants. Elle n'est responsable que de se nommer lors de son instanciation. Le constructeur ne
 * doit prendre aucun paramètre. <br />
 * <br />
 * Si la tâche échoue, elle doit passer en statut FAILED, et générer des erreurs dans la base. Il est plus que vivement
 * conseillé de ne pas utiliser les instance de projet et d'audit lorsque les résultats sont persistés, il faut utiliser
 * des nouvelles instances directement à partir des DAO, avec les id des instances offertes par l'observateur. <br />
 * <br />
 * Pour plus de détails sur la création d'une nouvelle tâche, reportez-vous au P720U associé.
 * 
 * @author m400842
 * @version 1.0
 */
public abstract class AbstractTask
    implements Runnable
{

    /**
     * L'application
     */
    protected ApplicationBO mApplication;

    /**
     * Provider de persistence
     */
    private IPersistenceProvider mPersistenceProvider;

    /**
     * Session hibernate
     */
    private ISession mSession;

    /**
     * Précise que l'exécution de la tâche s'est terminée avec succès.
     */
    public static final int TERMINATED = 0;

    /**
     * Précise que l'exécution de la tâche a échouée.
     */
    public static final int FAILED = 1;

    /**
     * Précise que l'exécution de la tâche n'a pas été tentée.
     */
    public static final int NOT_ATTEMPTED = 2;

    /**
     * Précise que l'exécution est en cours.
     */
    public static final int RUNNING = 3;

    /**
     * Etat courant de la tâche
     */
    protected int mStatus = NOT_ATTEMPTED;

    /**
     * Précise que l'exécution de la tâche est suspendue en raison d'un manque de ressources.
     */
    public static final int NO_RESOURCES = 4;

    /**
     * Précise que l'exécution de la tâche est annulée.
     */
    public static final int CANCELLED = 5;

    /**
     * indique si la tache est obligatoire ou optionnelle obligatoire par défaut
     */
    protected boolean mMandatoryTask = true;

    /**
     * Projet attaché à la tache
     */
    protected ProjectBO mProject = null;

    /**
     * Audit durant lequel est effectuée la tâche.
     */
    protected AuditBO mAudit = null;

    /**
     * Le nom de la tâche.
     */
    protected String mName = null;

    /**
     * L'ensemble des paramètres temporaires d'une tâche
     */
    protected TaskData mData = null;

    /** l'id de l'application auquelle est rattaché le projet */
    protected Long mApplicationId;

    /** l'id du projet auquel est rattaché la tâche */
    protected Long mProjectId;

    /** l'id de l'audit auquel est rattaché la tâche */
    protected Long mAuditId;

    /** les erreurs */
    protected ArrayList mErrors;

    /** Paramètres de la tâche */
    private Collection mTaskParameters = new ArrayList();

    /** Taille max du file system au cours de la tache */
    protected long mMaxFileSystemSize = 0;

    /** Taille du file system à la fin de la tache */
    protected long mPersistentFileSystemSize = 0;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( AbstractTask.class );

    /**
     * Access method for the mStatus property.
     * 
     * @return the current value of the mStatus property
     * @roseuid 42CD420D01DA
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Access method for the mProject property.
     * 
     * @return the current value of the mProject property
     * @roseuid 42CD420D0209
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     * @roseuid 42CD420D0238
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * Sets the value of the mStatus property.
     * 
     * @param pStatus the new value of the mStatus property
     * @roseuid 42D22A170000
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * @return le nom de la tache
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return l'objet contenant l'ensemble des parametres temporaires définis
     */
    public TaskData getData()
    {
        return mData;
    }

    /**
     * @param pData le nouvel ensemble de parametres temporaires
     */
    public void setData( TaskData pData )
    {
        mData = pData;
    }

    /**
     * @return l'id de l'application
     */
    public Long getApplicationId()
    {
        return mApplicationId;
    }

    /**
     * @return l'id du projet
     */
    public Long getProjectId()
    {
        return mProjectId;
    }

    /**
     * @return la session
     */
    public ISession getSession()
    {
        return mSession;
    }

    /**
     * @param pLong le nouvel id de l'application
     */
    public void setApplicationId( Long pLong )
    {
        mApplicationId = pLong;
    }

    /**
     * @param pLong le nouvel id du projet
     */
    public void setProjectId( Long pLong )
    {
        mProjectId = pLong;
    }

    /**
     * @return l'id de l'audit
     */
    public Long getAuditId()
    {
        return mAuditId;
    }

    /**
     * @param pLong le nouvel id de l'audit
     */
    public void setAuditId( Long pLong )
    {
        mAuditId = pLong;
    }

    /**
     * @return la liste des errors
     */
    public ArrayList getErrors()
    {
        return mErrors;
    }

    /**
     * @param pList la nouvelle liste d'erreurs
     */
    public void setErrors( ArrayList pList )
    {
        mErrors = pList;
    }

    /**
     * @return paramètres de la tâche (soit la forme de TaskParameterBO)
     */
    public Collection getTaskParameters()
    {
        return mTaskParameters;
    }

    /**
     * @param pTaskParameters paramètres de la tâche
     */
    public void setTaskParameters( Collection pTaskParameters )
    {
        mTaskParameters = pTaskParameters;
    }

    /**
     * @return true si la tache est obligatoire
     */
    public boolean isMandatoryTask()
    {
        return mMandatoryTask;
    }

    /**
     * @param pCategory la nouvelle valeur
     */
    public void setMandatoryTask( boolean pCategory )
    {
        mMandatoryTask = pCategory;
    }

    /**
     * Persiste les objets résultats dans la base de données grâce à la session.
     * 
     * @roseuid 42CD420D018C
     */
    protected void persistResult()
    {
    }

    /**
     * Crée les erreurs et les persiste dans la base
     */
    private void createErrors()
    {
        // la liste a été initialisée à 0 éléments
        if ( mErrors.size() > 0 )
        {
            Iterator it = mErrors.iterator();
            while ( it.hasNext() )
            {
                ErrorBO error = (ErrorBO) it.next();
                // le seul champ à remplir
                if ( null == error.getTaskName() )
                {
                    error.setTaskName( mName );
                }
                // si la tache n'est pas failed(peut arriver si la tache n'a pas levé d'exception)
                // et qu'une erreur est fatale on met le status à Failed
                // on met le status de la tache au status maximum des erreurs
                if ( mStatus != FAILED && error.getLevel() == ErrorBO.CRITICITY_FATAL )
                {
                    mStatus = FAILED;
                }
                // Si la tache a échouée, on met la dernière error
                // au niveau de criticité maximale
                if ( !it.hasNext() && mStatus == FAILED )
                {
                    error.setLevel( ErrorBO.CRITICITY_FATAL );
                    // Dans le cas d'une erreur fatale, on prévient les admins
                    // que la tache a échoué
                    Date current = Calendar.getInstance().getTime();
                    DateFormat formatter = new SimpleDateFormat( "d MMM yyyy HH:mm:ss 'GMT'", Locale.US );
                    formatter.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
                    String hour = formatter.format( current );
                    String[] infos =
                        new String[] { mName, mApplication.getName(), mApplication.getServeurBO().getName(),
                            mProject.getName(), "" + mAudit.getId(), hour };
                    String object =
                        Messages.getString( "mail.sender.squalix.task", new String[] { mApplication.getName() } )
                            + Messages.getString( "mail.task.failed.object", infos );
                    String content =
                        Messages.getString( "mail.header" ) + Messages.getString( "mail.task.failed.content", infos );
                    String dest = SqualeCommonConstants.ONLY_ADMINS;
                    IMailerProvider mailer = MailerHelper.getMailerProvider();
                    SqualeCommonUtils.notifyByEmail(mailer, null, dest, null, object, content, false);
                }
                // Pas de conversion InitialMessage -> Message
                try
                {
                    // On attribue les bonnes valeurs aux erreurs
                    error.setProject( mProject );
                    error.setAudit( mAudit );
                    // enregistrement en base
                    ErrorDAOImpl.getInstance().create( mSession, error );
                    mSession.closeSession();
                }
                catch ( JrafDaoException e )
                {
                    LOGGER.error( e, e );
                }
            }
        }
    }

    /**
     * Méthode qui permet de créer une session, qui effectue le travail propre à la tache et ferme la session
     */
    public final void run()
    {
        if ( mStatus == NOT_ATTEMPTED )
        {
            try
            {
                mStatus = RUNNING;
                // initialisation
                init();
                // code propre à la tache
                execute();
                // signale que la tâche s'est bien déroulée
                String[] tab = getName().split( "." );
                // on ne devrait pas passer dans ce test si le nom de la tache
                // a été correctement défini
                if ( tab == null || tab.length == 0 )
                {
                    LOGGER.info( "la tâche " + mName + " à été exécutée avec SUCCES " );
                }
                else
                { // Prend juste le nom de la classe sans package
                    LOGGER.info( "la tâche " + tab[tab.length - 1] + " à été exécutée avec SUCCES " );
                }
            }
            catch ( Exception e )
            {
                LOGGER.error( e, e );
                mStatus = FAILED;
                // une exception attrapée ici est forcément une erreur critique
                // On récupère le message de l'erreur
                String message = e.getMessage();
                // Si le message est nul, on récupère la description de l'erreur
                if ( null == message )
                {
                    message = e.toString();
                }
                initError( message, ErrorBO.CRITICITY_FATAL );
            }
            finally
            { // on crée les erreurs
                createErrors();
                try
                {
                    mSession.commitTransaction();
                }
                catch ( JrafPersistenceException jpe )
                {
                    LOGGER.error( jpe, jpe );
                }
            }
            // Changement d'état si une erreur n'est pas survenue
            if ( mStatus == AbstractTask.RUNNING )
            {
                mStatus = TERMINATED;
            }
        }
    }

    /**
     * Méthode qui crée la session
     * 
     * @throws JrafDaoException en cas d'échec
     */
    private final void init()
        throws JrafDaoException
    {
        mErrors = new ArrayList( 0 );
        mPersistenceProvider = PersistenceHelper.getPersistenceProvider();
        mSession = mPersistenceProvider.getSession();
        mSession.beginTransaction();
        mAudit = (AuditBO) AuditDAOImpl.getInstance().get( mSession, mAuditId );
        mApplication = (ApplicationBO) ApplicationDAOImpl.getInstance().get( mSession, mApplicationId );
        // Ce n'est pas vraiment du code défensif, mais c'est plutot d'ordre logique:
        // Pour les taches de niveau application on ne recharge pas le projet
        if ( null != mProjectId )
        {
            mProject = (ProjectBO) ProjectDAOImpl.getInstance().get( mSession, mProjectId );
        }
    }

    /**
     * Méthode qui effectue le traitement propre à chaque tache
     * 
     * @throws TaskException car il y a plusieurs types d'exception suivant les tâches
     */
    public abstract void execute()
        throws TaskException;

    /**
     * Crée une erreur avec un niveau de criticité par défaut
     * 
     * @param pMessage le message
     */
    public void initError( String pMessage )
    {
        initError( pMessage, ErrorBO.CRITICITY_WARNING );
    }

    /**
     * Crée une erreur avec un niveau de criticité déterminée
     * 
     * @param pMessage le message
     * @param pCriticyLevel le niveau de criticité de l'erreur
     */
    public void initError( String pMessage, String pCriticyLevel )
    {
        // cas particulier, petit filtrage
        if ( pMessage.indexOf( "see the compiler error output" ) == -1 )
        {
            // Prise en compte de la limite de taille des messages
            // TODO voir comment on peut traiter ces dépassements de chaîne
            // defaçon plus générique (via Hibernate ?)
            final int maxLength = 2000;
            String newMessage = pMessage;
            if ( newMessage.length() > maxLength )
            {
                newMessage = newMessage.substring( 0, maxLength );
            }
            // on est sur une erreur qui a été provoquée
            // par un ProcessManager, on doit affecter le niveau de criticité WARNING par défaut
            ErrorBO error = new ErrorBO( newMessage, newMessage, pCriticyLevel, mName, mAudit, mProject );
            mErrors.add( error );
        }
    }

    /**
     * @return la taille max du file system
     */
    protected long getMaxFileSystemSize()
    {
        return mMaxFileSystemSize;
    }

    /**
     * Positionne la taille max et la taille à la fin du file system pour cette tache
     * 
     * @param pParam le paramètre
     * @param pPersistent un booléen indiquant si la taille max est conservée à la fin ou pas
     */
    protected void affectFileSystemSize( Object pParam, boolean pPersistent )
    {
        // Ca peut etre directement un répertoire
        if ( pParam instanceof File )
        {
            mMaxFileSystemSize = calculateRecursiveSize( (File) pParam );
        }
        else
        {
            // ou alors le nom du répertoire
            if ( pParam instanceof String )
            {
                mMaxFileSystemSize = calculateRecursiveSize( new File( (String) pParam ) );
            }
            else
            {
                // ou un objet plus complexe de type parameter
                // un simpleStringParameterBO indiquant directement le répertoire
                if ( pParam instanceof StringParameterBO )
                {
                    // appel simple de la méthode
                    mMaxFileSystemSize = calculateRecursiveSize( new File( ( (StringParameterBO) pParam ).getValue() ) );
                }
                else
                {
                    // ou une liste de répertoires
                    if ( pParam instanceof ListParameterBO )
                    {
                        // récupère la liste des répertoires
                        List list = ( (ListParameterBO) pParam ).getParameters();
                        for ( int i = 0; i < list.size(); i++ )
                        {
                            // et cumul sur chaque répertoire
                            mMaxFileSystemSize +=
                                calculateRecursiveSize( new File( ( (StringParameterBO) ( list.get( i ) ) ).getValue() ) );
                        }
                    }
                }
            }
        }
        // Si c'est une tache qui conserve la place occupée à la fin, on positionne la valeur
        if ( pPersistent )
        {
            mPersistentFileSystemSize = mMaxFileSystemSize;
        } // sinon c'est 0 par défaut
    }

    /**
     * @param pFile le répertoire racine dont on veut la taille
     * @return la taille du fichier ou la taille de l'ensemble du répertoire si f est un répertoire
     */
    private int calculateRecursiveSize( File pFile )
    {
        int size = 0;
        // si le fichier existe, on le traite, sinon on renvoie 0
        if ( pFile.exists() )
        {
            // Si c'est un répertoire, appel récursif sur tous les sous-éléments
            if ( pFile.isDirectory() )
            {
                File[] tab = pFile.listFiles();
                for ( int i = 0; i < tab.length; i++ )
                {
                    size += calculateRecursiveSize( tab[i] );
                }
            }
            else
            {
                // on est sur un fichier, on récupère simplement la taille
                size += pFile.length();
            }
        }
        return size;
    }

    /**
     * @return la taille du file système à la fin de la tache
     */
    public long getPersistentFileSystemSize()
    {
        return mPersistentFileSystemSize;
    }
}