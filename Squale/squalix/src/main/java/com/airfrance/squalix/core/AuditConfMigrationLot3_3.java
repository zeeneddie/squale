package com.airfrance.squalix.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.bootstrap.initializer.Initializer;
import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.config.web.AbstractDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.jspvolumetry.JSPVolumetryProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.misc.CommentsBO;

/**
 * Main servant à affecter des configurations d'affichage aux audits exécutés avant la livraison de SQUALE 3.3 Cette
 * classe pourra être supprimée une fois lancée en production lors de la livraison de SQUALE 3.3
 */
public class AuditConfMigrationLot3_3
{

    /**
     * Logger
     */
    private static Log mLOGGER = null;

    /* Pour la configuration du bubble */
    /** tre pour les abscisses */
    private static final String BUBBLE_VG = "mccabe.method.vg";

    /** tre pour les ordonnées */
    private static final String BUBBLE_EVG = "mccabe.method.evg";

    /** poistion de l'axe des abscisses */
    private static final int BUBBLE_HORIZONTAL_POS = 10;

    /** position de l'axe des ordonnées */
    private static final int BUBBLE_VERTICAL_POS = 7;

    /* Pour la configuration de la volumétrie */
    /** tre pour le nombre de ligne java et cpp avec RSM (à partir de la 3.0) */
    private static final String VOL_RSM_SLOC = "rsm.project.sloc";

    /** tre pour le nombre de ligne java et cpp avec Pure comments (avant la 3.0) */
    private static final String VOL_PURECOMMENTS_SLOC = "purecomments.project.sloc";

    /** tre pour le nombre de ligne jsp avec jspvolumetry */
    private static final String VOL_JSPVOL_LINES = "jspvolumetry.project.numberOfJSPCodeLines";

    /** tre pour le nombre de commentaires avec RSM */
    private static final String VOL_RSM_COMMENTS = "rsm.project.comments";

    /** tre pour le nombre de commentaires avec Pure comments (avant la 3.0) */
    private static final String VOL_PURECOMMENTS_COMMENTS = "purecomments.project.cloc";

    /** tre pour le nombre de lignes de code avec RSM */
    private static final String VOL_RSM_CODE_LINES = "rsm.project.numberofcodelines";

    /** tre pour le nombre de classes */
    private static final String VOL_NB_CLASSES = "mccabe.project.numberOfClasses";

    /** tre pour le nombre de méthodes */
    private static final String VOL_NB_METHODS = "mccabe.project.numberOfMethods";

    /** tre pour le nombre de JSPs avec jspvolumetry */
    private static final String VOL_JSPVOL_NB_JSPS = "jspvolumetry.project.numberOfJSP";

    /** la configuration du bubble */
    private static BubbleConfBO oldBubbleConf;

    /** La volumétrie pour les applications avant RSM */
    private static VolumetryConfBO oldVolConf;

    /** La volumétrie niveau pour application avec RSM */
    private static VolumetryConfBO oldRsmVolConf;

    /** La volumétrie niveau application avec RSM dont un projet est J2EE */
    private static VolumetryConfBO oldJ2eeRsmVolConf;

    /** La volumétrie niveau projet sans RSM (avant il n'y avait pas la volumétrie sur les JSPs */
    private static VolumetryConfBO oldProjectVolConf;

    /** La volumétrie pour les projets avec RSM hors profils J2ee */
    private static VolumetryConfBO oldProjectRsmVolConf;

    /** La volumétrie pour les projets J2ee avec RSM */
    private static VolumetryConfBO oldProjectJ2eeRsmVolConf;

    /**
     * Méthode main
     * 
     * @param args les arguments
     * @throws JrafPersistenceException si erreur
     */
    public static void main( String[] args )
        throws JrafPersistenceException
    {
        // lancement de l’initialisation JRAF
        String rootPath = args[0];
        String configFile = "/config/providers-config.xml";
        Initializer init = new Initializer( rootPath, configFile );
        init.initialize();
        // Maintenant que le socle JRAF est initialisé, on peut créer un logger
        mLOGGER = LogFactory.getLog( AuditConfMigrationLot3_3.class );
        ISession session = null;
        try
        {
            session = PersistenceHelper.getPersistenceProvider().getSession();
            mLOGGER.info( "Création des anciennes configurations :" );
            /* On crée les configurations à utiliser pour les audits d'avant la livraison 3.3 */
            createoldConfigurations( session );

            /*
             * On récupère tous les audits terminés, partiels ou en échec (supprimé et en cours n'ont pas besoin de
             * configuration) de chaque projet non supprimé Et pour chacun, on lui affecte la bonne configuration :
             * configuration du bubble + configurations de la volumétrie dépendant des résultats (si résultats RSM alors
             * conf RSM sinon l'autre) en regardant aussi si il y a des résultats pour les JSPs (dans ce cas conf J2ee)
             */
            mLOGGER.info( "Création des liens audits-configurations" );
            createAuditDisplayConfs( session );
            mLOGGER.info( "MIGRATION TERMINEE!" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( null != session )
            {
                session.closeSession();
            }
        }
    }

    /**
     * Créer les configurations à affecter aux audits d'avant la livraison 3.3
     * 
     * @param session la session hibernate
     * @throws JrafDaoException si erreur
     */
    public static void createoldConfigurations( ISession session )
        throws JrafDaoException
    {
        AbstractDisplayConfDAOImpl confDAO = AbstractDisplayConfDAOImpl.getInstance();
        // Le bubble
        mLOGGER.info( "Bubble :" );
        oldBubbleConf = new BubbleConfBO( BUBBLE_VG, BUBBLE_EVG, BUBBLE_HORIZONTAL_POS, BUBBLE_VERTICAL_POS );
        confDAO.create( session, oldBubbleConf );

        // La volumétrie pour les applications avant RSM
        mLOGGER.info( "Volumétrie niveau application avant SQUALE 3.0 :" );
        oldVolConf = new VolumetryConfBO();
        oldVolConf.setComponentType( DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        oldVolConf.addTre( VOL_PURECOMMENTS_SLOC );
        confDAO.create( session, oldVolConf );

        // La volumétrie niveau pour application avec RSM
        mLOGGER.info( "Volumétrie niveau application après SQUALE 3.0" );
        oldRsmVolConf = new VolumetryConfBO();
        oldRsmVolConf.setComponentType( DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        oldRsmVolConf.addTre( VOL_RSM_SLOC );
        confDAO.create( session, oldRsmVolConf );

        // La volumétrie niveau application avec RSM dont un projet est J2EE
        mLOGGER.info( "Volumétrie niveau application après SQUALE 3.0 pour un profil J2EE" );
        oldJ2eeRsmVolConf = new VolumetryConfBO();
        oldJ2eeRsmVolConf.setComponentType( DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        oldJ2eeRsmVolConf.addTre( VOL_RSM_SLOC );
        oldJ2eeRsmVolConf.addTre( VOL_JSPVOL_LINES );
        confDAO.create( session, oldJ2eeRsmVolConf );

        // La volumétrie niveau projet sans RSM (avant il n'y avait pas la volumétrie sur les JSPs
        mLOGGER.info( "Volumétrie niveau projet avant SQUALE 3.0" );
        oldProjectVolConf = new VolumetryConfBO();
        oldProjectVolConf.setComponentType( DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        oldProjectVolConf.addTre( VOL_NB_CLASSES );
        oldProjectVolConf.addTre( VOL_NB_METHODS );
        oldProjectVolConf.addTre( VOL_PURECOMMENTS_COMMENTS );
        oldProjectVolConf.addTre( VOL_PURECOMMENTS_SLOC );
        confDAO.create( session, oldProjectVolConf );

        // La volumétrie pour les projets avec RSM hors profils J2ee
        mLOGGER.info( "Volumétrie niveau projet après SQUALE 3.0" );
        oldProjectRsmVolConf = new VolumetryConfBO();
        oldProjectRsmVolConf.setComponentType( DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        oldProjectRsmVolConf.addTre( VOL_NB_CLASSES );
        oldProjectRsmVolConf.addTre( VOL_NB_METHODS );
        oldProjectRsmVolConf.addTre( VOL_RSM_COMMENTS );
        oldProjectRsmVolConf.addTre( VOL_RSM_SLOC );
        oldProjectRsmVolConf.addTre( VOL_RSM_CODE_LINES );
        confDAO.create( session, oldProjectRsmVolConf );

        // La volumétrie pour les projets J2ee avec RSM
        mLOGGER.info( "Volumétrie niveau projet après SQUALE 3.0 pour des profils J2EE" );
        oldProjectJ2eeRsmVolConf = new VolumetryConfBO();
        oldProjectJ2eeRsmVolConf.setComponentType( DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        oldProjectJ2eeRsmVolConf.addTre( VOL_NB_CLASSES );
        oldProjectJ2eeRsmVolConf.addTre( VOL_NB_METHODS );
        oldProjectJ2eeRsmVolConf.addTre( VOL_RSM_COMMENTS );
        oldProjectJ2eeRsmVolConf.addTre( VOL_RSM_SLOC );
        oldProjectJ2eeRsmVolConf.addTre( VOL_RSM_CODE_LINES );
        // Plus la volumétrie sur les JSPs
        oldProjectJ2eeRsmVolConf.addTre( VOL_JSPVOL_LINES );
        oldProjectJ2eeRsmVolConf.addTre( VOL_JSPVOL_NB_JSPS );
        confDAO.create( session, oldProjectJ2eeRsmVolConf );
    }

    /**
     * Créee les liens audits-configurations
     * 
     * @param session la session hibernate
     * @throws JrafDaoException si erreur
     */
    public static void createAuditDisplayConfs( ISession session )
        throws JrafDaoException
    {
        // Initialisation
        AuditDAOImpl auditDAO = AuditDAOImpl.getInstance();
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        Collection projects = projectDAO.findAll( session );
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        ProjectBO curProject;
        AuditBO curAudit;
        ArrayList audits = new ArrayList();
        Long projectId;
        Long auditId;
        // Itération sur les projets
        for ( Iterator it = projects.iterator(); it.hasNext(); )
        {
            curProject = (ProjectBO) it.next();
            projectId = new Long( curProject.getId() );
            // On récupère ses audits
            audits.clear();
            if ( ProjectBO.DELETED != curProject.getStatus() )
            { // Inutile pour les projets supprimés
                audits.addAll( auditDAO.findWhereComponent( session, curProject.getId(), null, null, null,
                                                            AuditBO.TERMINATED ) );
                audits.addAll( auditDAO.findWhereComponent( session, curProject.getId(), null, null, null,
                                                            AuditBO.PARTIAL ) );
                audits.addAll( auditDAO.findWhereComponent( session, curProject.getId(), null, null, null,
                                                            AuditBO.FAILED ) );
            }
            // Pour tous les audits, on ajoute la conf du bubble et les configurations pour la volumétrie
            // selon les résultats de l'audit
            for ( int i = 0; i < audits.size(); i++ )
            {
                curAudit = (AuditBO) audits.get( i );
                auditId = new Long( curAudit.getId() );
                addAuditDisplayConfFor( session, curAudit, oldBubbleConf, curProject );
                // Pour la volumétrie, cela dépend des résultats de l'audit pour ce projet
                // Si il a des purecomments, on ajoute les configurations d'avant la 3.0 (sans RSM)
                if ( null != measureDAO.load( session, projectId, auditId, CommentsBO.class ) )
                {
                    addAuditDisplayConfFor( session, curAudit, oldVolConf, curProject );
                    addAuditDisplayConfFor( session, curAudit, oldProjectVolConf, curProject );
                }
                else if ( null != measureDAO.load( session, projectId, auditId, JSPVolumetryProjectBO.class ) )
                {
                    // On ajoute la conf RSM pour J2ee
                    addAuditDisplayConfFor( session, curAudit, oldJ2eeRsmVolConf, curProject );
                    addAuditDisplayConfFor( session, curAudit, oldProjectJ2eeRsmVolConf, curProject );
                }
                else
                {
                    // On ajoute la conf RSM
                    addAuditDisplayConfFor( session, curAudit, oldRsmVolConf, curProject );
                    addAuditDisplayConfFor( session, curAudit, oldProjectRsmVolConf, curProject );
                }
            }
        }
    }

    /**
     * Ajoute un lien entre l'audit et la configuration d'affichage
     * 
     * @param pSession la session hibernate
     * @param pAudit l'audit
     * @param pConf la configuration
     * @param pProject le projet
     * @throws JrafDaoException si erreur
     */
    public static void addAuditDisplayConfFor( ISession pSession, AuditBO pAudit, AbstractDisplayConfBO pConf,
                                               ProjectBO pProject )
        throws JrafDaoException
    {
        AuditDisplayConfBO auditConf = new AuditDisplayConfBO();
        auditConf.setDisplayConf( pConf );
        auditConf.setProject( pProject );
        auditConf.setAudit( pAudit );
        AuditDisplayConfDAOImpl.getInstance().create( pSession, auditConf );
        // Ajout à l'audit
        pAudit.addAuditDisplayConf( auditConf );

    }
}
