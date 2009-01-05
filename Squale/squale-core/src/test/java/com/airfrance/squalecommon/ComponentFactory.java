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
package com.airfrance.squalecommon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditGridDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ServeurDAOImpl;
import com.airfrance.squalecommon.daolayer.config.SourceManagementDAOImpl;
import com.airfrance.squalecommon.daolayer.config.StopTimeDAOImpl;
import com.airfrance.squalecommon.daolayer.config.TaskDAOImpl;
import com.airfrance.squalecommon.daolayer.profile.ProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.profile.UserDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.rule.QualityGridDAOImpl;
import com.airfrance.squalecommon.daolayer.rulechecking.CheckstyleRuleSetDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.Profile_DisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.misc.CommentsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.CheckstyleTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * Factory de création de composants Cette fabrique est utilisée pour la réalisation de tests unitaires simples. Les
 * objets créés par cette fabrique ne contiennent pas des données réélles.
 */
public class ComponentFactory
{

    /** Le nombre de lignes pour les tests */
    public static final int SLOC = 1000;

    /** Le nombre de ligne de commentaires pour les tests */
    public static final int CLOC = 20;

    /** Session */
    private ISession mSession;

    /**
     * Constructeur
     * 
     * @param pSession session
     */
    public ComponentFactory( ISession pSession )
    {
        mSession = pSession;
    }

    /**
     * Obtention d'une session
     * 
     * @param pSession session
     * @return session
     */
    private ISession getSession( ISession pSession )
    {
        ISession session;
        if ( pSession == null )
        {
            session = mSession;
        }
        else
        {
            session = pSession;
        }
        return session;
    }

    /**
     * Création d'un utilisateur
     * 
     * @param pSession session
     * @return user
     * @throws JrafDaoException si erreur
     */
    public UserBO createUser( ISession pSession )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        UserBO user = new UserBO();
        user.setMatricule( "matricule" );
        HashMap rights = new HashMap();
        user.setRights( rights );
        ProfileBO defaultProfile = new ProfileBO();
        defaultProfile.setName( ProfileBO.ADMIN_PROFILE_NAME );
        defaultProfile.setRights( new HashMap() );
        ProfileDAOImpl.getInstance().create( pSession, defaultProfile );
        user.setDefaultProfile( defaultProfile );
        UserDAOImpl.getInstance().create( pSession, user );
        return user;
    }

    /**
     * création d'une tache par défault
     * 
     * @param pSession la session
     * @return la tache par défault
     * @throws JrafDaoException en cas d'échec
     */
    public TaskBO createTask( ISession pSession )
        throws JrafDaoException
    {
        TaskBO task = new TaskBO();
        return createTask( pSession, " ", false, "tache par default" );
    }

    /**
     * Création d'une tache avec ses paramètres
     * 
     * @param pSession la session
     * @param pClassName le nom de la classe
     * @param pIsConfigurable un booléen indiquant si la tache est configurable ou pas
     * @param pName le nom de la tache
     * @return la tache
     * @throws JrafDaoException en cas d'échec
     */
    public TaskBO createTask( ISession pSession, String pClassName, boolean pIsConfigurable, String pName )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        TaskBO task = new TaskBO();
        task.setClassName( pClassName );
        task.setConfigurable( pIsConfigurable );
        task.setName( pName );
        TaskDAOImpl.getInstance().create( pSession, task );
        return task;
    }

    /**
     * Création d'une application
     * 
     * @param pSession session
     * @return application
     * @throws JrafDaoException si erreur
     */
    public ApplicationBO createApplication( ISession pSession )
        throws JrafDaoException
    {
        return createApplicationWithSite( pSession, "server" );
    }

    /**
     * Création d'une application rattachée à un site
     * 
     * @param pSession session
     * @return application
     * @param pSiteName le nom du site
     * @throws JrafDaoException si erreur
     */
    public ApplicationBO createApplicationWithSite( ISession pSession, String pSiteName )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        ApplicationBO application = new ApplicationBO();
        application.setName( "application" + pSiteName );
        ServeurBO server = createServer( pSession, pSiteName );
        application.setServeurBO( server );
        application = ApplicationDAOImpl.getInstance().create( pSession, application );
        return application;
    }

    /**
     * @param pSession la session
     * @return le serveur persistant
     * @throws JrafDaoException si erreur
     */
    public ServeurBO createServer( ISession pSession )
        throws JrafDaoException
    {
        return createServer( pSession, "server" );
    }

    /**
     * @param pSession la session
     * @param pName le nom du site
     * @return le serveur persistant
     * @throws JrafDaoException si erreur
     */
    public ServeurBO createServer( ISession pSession, String pName )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        ServeurBO server = new ServeurBO();
        server.setName( pName );
        // On liste les serveurs
        int nbServers = ServeurDAOImpl.getInstance().listeServeurs( pSession ).size();
        server.setServeurId( nbServers + 1 );
        ServeurDAOImpl.getInstance().create( pSession, server );
        return server;
    }

    /**
     * Création d'un projet
     * 
     * @param pSession session
     * @param pApplication application
     * @param pGrid grille
     * @return projet
     * @throws JrafDaoException si erreur
     */
    public ProjectBO createProject( ISession pSession, ApplicationBO pApplication, QualityGridBO pGrid )
        throws JrafDaoException
    {
        return createProject( pSession, pApplication, pGrid, null, null, null );
    }

    /** variable définissant le nombre de projets avec une valeur par défaut */
    private int projectnb = 0;

    /**
     * Création d'un projet
     * 
     * @param pSession session
     * @param pApplication application
     * @param pGrid grille
     * @param pProfile profil
     * @param pManager source manager
     * @param pParameters parametres
     * @return le projet
     * @throws JrafDaoException si erreur
     */
    public ProjectBO createProject( ISession pSession, ApplicationBO pApplication, QualityGridBO pGrid,
                                    ProjectProfileBO pProfile, SourceManagementBO pManager, MapParameterBO pParameters )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        ProjectBO project = new ProjectBO();
        project.setName( "project" + ( projectnb++ == 0 ? "" : String.valueOf( projectnb ) ) );
        project.setParent( pApplication );
        project.setQualityGrid( pGrid );
        project.setProfile( pProfile );
        project.setSourceManager( pManager );
        project.setParameters( pParameters );
        ProjectDAOImpl.getInstance().create( pSession, project );
        pApplication.addComponent( project );
        ApplicationDAOImpl.getInstance().save( pSession, pApplication );
        return project;
    }

    /**
     * Création d'un audit
     * 
     * @param pSession session
     * @param pProject projet
     * @return audit
     * @throws JrafDaoException si erreur
     */
    public AuditBO createAudit( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        return createAuditWithStatus( pSession, pProject, null );
    }

    /**
     * Création d'un audit
     * 
     * @param pSession session
     * @param pProject projet
     * @param pStatus le status de l'audit
     * @return audit
     * @throws JrafDaoException si erreur
     */
    public AuditBO createAuditWithStatus( ISession pSession, ProjectBO pProject, Integer pStatus )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        AuditBO audit = new AuditBO();
        audit.setName( "audit" );

        if ( pStatus != null )
        {
            audit.setStatus( pStatus.intValue() );
        }
        audit.setType( AuditBO.MILESTONE );
        AuditDAOImpl.getInstance().create( pSession, audit );
        // Création de l'auditGridBO
        addAudit( pSession, pProject, audit );
        return audit;
    }

    /**
     * Ajout d'un audit
     * 
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @throws JrafDaoException si erreur
     */
    private void addAudit( ISession pSession, ProjectBO pProject, AuditBO pAudit )
        throws JrafDaoException
    {
        // Création de l'auditGridBO
        AuditGridBO auditGrid = new AuditGridBO();
        auditGrid.setAudit( pAudit );
        auditGrid.setGrid( pProject.getQualityGrid() );
        auditGrid.setProject( pProject );
        AuditGridDAOImpl.getInstance().create( pSession, auditGrid );
        pAudit.addAuditGrid( auditGrid );
        // Création des auditDisplayConfBO si l'audit n'est pas en attente et que le profil n'est pas nul
        if ( pAudit.getStatus() != AuditBO.NOT_ATTEMPTED && null != pProject.getProfile() )
        {
            // On récupère les configurations associées au profil du projet
            Collection confs = pProject.getProfile().getProfileDisplayConfs();
            for ( Iterator it = confs.iterator(); it.hasNext(); )
            {
                AuditDisplayConfBO auditConf = new AuditDisplayConfBO();
                auditConf.setAudit( pAudit );
                auditConf.setDisplayConf( ( (Profile_DisplayConfBO) it.next() ).getDisplayConf() );
                auditConf.setProject( pProject );
                AuditDisplayConfDAOImpl.getInstance().save( pSession, auditConf );
                pAudit.addAuditDisplayConf( auditConf );
            }
        }
        AuditDAOImpl.getInstance().save( pSession, pAudit );
        pProject.addAudit( pAudit );
        pProject.getParent().addAudit( pAudit );
        AbstractComponentDAOImpl.getInstance().save( mSession, pProject );
    }

    /**
     * Création d'un package
     * 
     * @param pSession session
     * @param pProject projet
     * @return package
     * @throws JrafDaoException si erreur
     */
    public PackageBO createPackage( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        PackageBO pkg = new PackageBO();
        pkg.setName( "package" );
        pkg.setParent( pProject );
        AbstractComponentDAOImpl.getInstance().create( pSession, pkg );
        pProject.addComponent( pkg );
        AbstractComponentDAOImpl.getInstance().save( pSession, pProject );
        return pkg;
    }

    /**
     * Création d'un package qui a un package comme parent
     * 
     * @param pSession session
     * @param pPackageName le nom du package
     * @param pPackage projet
     * @return package
     * @throws JrafDaoException si erreur
     */
    public PackageBO createPackage( ISession pSession, String pPackageName, PackageBO pPackage )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        PackageBO pkg = new PackageBO();
        pkg.setName( pPackageName );
        pkg.setParent( pPackage );
        AbstractComponentDAOImpl.getInstance().create( pSession, pkg );
        pPackage.addComponent( pkg );
        AbstractComponentDAOImpl.getInstance().save( pSession, pPackage );
        return pkg;
    }

    /**
     * Création d'une classe
     * 
     * @param pSession session
     * @param pPackage package
     * @return classe
     * @throws JrafDaoException si erreur
     */
    public ClassBO createClass( ISession pSession, PackageBO pPackage )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        // Création de la classe
        ClassBO cls = new ClassBO();
        cls.setName( "class" );
        cls.setParent( pPackage );
        AbstractComponentDAOImpl.getInstance().create( pSession, cls );
        pPackage.addComponent( cls );
        AbstractComponentDAOImpl.getInstance().save( pSession, pPackage );
        return cls;
    }

    /**
     * Création d'une classe
     * 
     * @param pSession session
     * @param pProject projet
     * @return classe
     * @throws JrafDaoException si erreur
     */
    public ClassBO createCppClass( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        // Création de la classe
        ClassBO cls = new ClassBO();
        cls.setName( "class" );
        cls.setParent( pProject );
        AbstractComponentDAOImpl.getInstance().create( pSession, cls );
        pProject.addComponent( cls );
        AbstractComponentDAOImpl.getInstance().save( pSession, pProject );
        return cls;
    }

    /**
     * Création d'une méthode
     * 
     * @param pSession session
     * @param pClass classe
     * @return méthode
     * @throws JrafDaoException si erreur
     */
    public MethodBO createMethod( ISession pSession, ClassBO pClass )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        // Création de la méthode
        MethodBO method = new MethodBO();
        method.setName( "method" );
        method.setLongFileName( "fileName" );
        method.setParent( pClass );
        AbstractComponentDAOImpl.getInstance().create( pSession, method );
        pClass.addComponent( method );
        AbstractComponentDAOImpl.getInstance().save( pSession, pClass );
        return method;
    }

    /**
     * Création d'une grille
     * 
     * @param pSession session
     * @return grille
     * @throws JrafDaoException si erreur
     */
    public QualityGridBO createGrid( ISession pSession )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        QualityGridBO grid = new QualityGridBO();
        grid.setName( "grid" );
        QualityGridDAOImpl.getInstance().create( pSession, grid );
        return grid;

    }

    /**
     * Création d'un profil avec un nom par défaut
     * 
     * @param pSession session
     * @return le profil
     * @throws JrafDaoException si erreur
     */
    public ProjectProfileBO createProjectProfile( ISession pSession )
        throws JrafDaoException
    {
        return createProjectProfileWithName( pSession, "profile" );

    }

    /**
     * Création d'un profil avec un nom donné
     * 
     * @param pSession session
     * @param pName le nom du profil
     * @return le profil
     * @throws JrafDaoException si erreur
     */
    public ProjectProfileBO createProjectProfileWithName( ISession pSession, String pName )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        ProjectProfileBO profile = new ProjectProfileBO();
        profile.setName( pName );
        ProjectProfileDAOImpl.getInstance().create( pSession, profile );
        return profile;

    }

    /**
     * Création d'un source management
     * 
     * @param pSession session
     * @return le source manager
     * @throws JrafDaoException si erreur
     */
    public SourceManagementBO createSourceManagement( ISession pSession )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        SourceManagementBO manager = new SourceManagementBO();
        manager.setName( "manager" );
        SourceManagementDAOImpl.getInstance().create( pSession, manager );
        return manager;

    }

    /**
     * Création des parametres
     * 
     * @param pSession session
     * @return le source manager
     * @throws JrafDaoException si erreur
     */
    public MapParameterBO createParameters( ISession pSession )
        throws JrafDaoException
    {
        //CHECKSTYLE:OFF
        pSession = getSession( pSession );
        //CHECKSTYLE:ON
        MapParameterBO params = new MapParameterBO();
        ProjectParameterDAOImpl.getInstance().create( pSession, params );
        return params;

    }

    /**
     * Création d'une application de test
     * 
     * @return application de tes
     * @throws JrafDaoException si erreur
     */
    public ApplicationBO createTestApplication()
        throws JrafDaoException
    {
        ApplicationBO application = createApplication( null );
        ProjectBO project = createProject( null, application, createGrid( null ) );
        PackageBO pkg = createPackage( null, project );
        ClassBO cls = createClass( null, pkg );
        MethodBO method = createMethod( null, cls );

        return application;
    }

    /**
     * Création d'un résultat d'audit
     * 
     * @param pApplication application
     * @return audit
     * @throws JrafDaoException si erreur
     */
    public AuditBO createAuditResult( ApplicationBO pApplication )
        throws JrafDaoException
    {
        ProjectBO project = (ProjectBO) pApplication.getChildren().iterator().next();
        AuditBO audit = createAudit( null, project );
        pApplication.addAudit( audit );
        // On crée un audit pour chaque objet présent dans le projet
        deepCreateAuditResult( audit, project.getChildren() );
        return audit;

    }

    /**
     * Création de résultats d'audit
     * 
     * @param pAudit audit
     * @param pComponents composants
     * @throws JrafDaoException si erreur
     */
    private void deepCreateAuditResult( AuditBO pAudit, Collection pComponents )
        throws JrafDaoException
    {
        Iterator it = pComponents.iterator();
        while ( it.hasNext() )
        {
            AbstractComponentBO comp = (AbstractComponentBO) it.next();
            comp.addAudit( pAudit );
            AbstractComponentDAOImpl.getInstance().save( mSession, comp );
            if ( comp instanceof AbstractComplexComponentBO )
            {
                deepCreateAuditResult( pAudit, ( (AbstractComplexComponentBO) comp ).getChildren() );
            }
        }
    }

    /**
     * Création d'une date limite pour la configuration Squalix
     * 
     * @param pSession session
     * @throws JrafDaoException si erreur
     */
    public void createStopTime( ISession pSession )
        throws JrafDaoException
    {
        StopTimeDAOImpl.getInstance().create( pSession, new StopTimeBO() );

    }

    /**
     * @param pSession la session
     * @param pAudit l'audit
     * @param pProject le projet
     * @param pClass la classe
     * @param pMethod la méthode
     * @return des mesures
     * @throws JrafDaoException si erreur
     */
    public MeasureBO[] createMeasures( ISession pSession, AuditBO pAudit, ProjectBO pProject, ClassBO pClass,
                                       MethodBO pMethod )
        throws JrafDaoException
    {
        // Création des mesures
        /*
         * McCabe : - pClass : Maxvg = 2; Sumvg = 2; wmc = 8; - pMethod : nsloc = 2; Checkstyle : - pProject : COD01 =
         * 20;
         */
        McCabeQAClassMetricsBO classMetrics = new McCabeQAClassMetricsBO();
        classMetrics.setAudit( pAudit );
        classMetrics.setComponent( pClass );
        classMetrics.setMaxvg( new Integer( 2 ) );
        classMetrics.setSumvg( new Integer( 2 ) );
        classMetrics.setWmc( new Integer( 8 ) );
        MeasureDAOImpl.getInstance().create( pSession, classMetrics );
        McCabeQAMethodMetricsBO methodMetrics = new McCabeQAMethodMetricsBO();
        methodMetrics.setAudit( pAudit );
        methodMetrics.setComponent( pMethod );
        methodMetrics.setNsloc( new Integer( 2 ) );
        MeasureDAOImpl.getInstance().create( pSession, methodMetrics );
        CommentsBO commentMetrics = new CommentsBO();
        commentMetrics.setCloc( new Integer( CLOC ) );
        commentMetrics.setSloc( new Integer( SLOC ) );
        commentMetrics.setAudit( pAudit );
        commentMetrics.setComponent( pProject );
        MeasureDAOImpl.getInstance().create( pSession, commentMetrics );
        // RuleSet pour checkstyle
        CheckstyleRuleSetDAOImpl daoImpl = CheckstyleRuleSetDAOImpl.getInstance();
        // instanciation de la première VersionBO
        CheckstyleRuleSetBO v = new CheckstyleRuleSetBO();
        v.setValue( "n'import quoi".getBytes() );

        // instanciation d'une règle
        CheckstyleRuleBO rule1 = new CheckstyleRuleBO();
        rule1.setCategory( "programmingstandard" );
        rule1.setCode( "COD01" );
        rule1.setSeverity( "error" );
        v = daoImpl.createCheckstyleRuleSet( pSession, v );
        //
        rule1.setRuleSet( v );
        Map rules = new HashMap();
        rules.put( rule1.getCode(), rule1 );
        v.setRules( rules );
        CheckstyleTransgressionBO checkstyleMetrics = new CheckstyleTransgressionBO();
        checkstyleMetrics.setAudit( pAudit );
        checkstyleMetrics.setComponent( pProject );
        checkstyleMetrics.setRuleSet( v );
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setName( "COD01" );
        metric.setValue( 20 );
        metric.setMeasure( checkstyleMetrics );
        checkstyleMetrics.putMetric( metric );
        MeasureDAOImpl.getInstance().create( pSession, checkstyleMetrics );
        return new MeasureBO[] { classMetrics, methodMetrics, commentMetrics, checkstyleMetrics };
    }

    /**
     * @param pSession la session
     * @param pAudit l'audit
     * @param pProject le projet
     * @return les métriques
     * @throws JrafDaoException si erreur
     */
    public CommentsBO createComments( ISession pSession, AuditBO pAudit, ProjectBO pProject )
        throws JrafDaoException
    {
        CommentsBO commentMetrics = new CommentsBO();
        commentMetrics.setCloc( new Integer( CLOC ) );
        commentMetrics.setSloc( new Integer( SLOC ) );
        commentMetrics.setAudit( pAudit );
        commentMetrics.setComponent( pProject );
        MeasureDAOImpl.getInstance().create( pSession, commentMetrics );
        return commentMetrics;
    }
}
