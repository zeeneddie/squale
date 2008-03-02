//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\applicationcomponent\\PurgeApplicationComponentAccess.java

package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import com.airfrance.squalecommon.enterpriselayer.facade.component.AuditFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;

/**
 * <p>
 * Title : PurgeApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component pour la purge projet et audit
 * </p>
 * <p>
 * Copyright : Copyright (c) 2005
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
public class PurgeApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( PurgeApplicationComponentAccess.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CBF81602AA
     */
    public PurgeApplicationComponentAccess()
    {
    }

    /**
     * Permet de supprimer une application du portail SQUALE
     * 
     * @param pApplicationConf ApplicationConfDTO renseignant l'ID de l'application
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBF8160323
     */
    public Integer purgeApplication( ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Verification si un jalon a ete posé sur l'application
            if ( !ApplicationFacade.existsMilestone( pApplicationConf, session ) )
            {
                // Si aucun jalon n'a ete posé, on insert le dernier audit dans le referentiel
                ComponentDTO project = new ComponentDTO();
                project.setID( pApplicationConf.getId() );
                // Récupération du dernier audit
                AuditDTO audit = AuditFacade.getLastAudit( project, null, session );
                if ( audit != null )
                {
                    // On insert l'audit récupéré précédemment
                    SqualeReferenceFacade.insertAudit( audit, session );
                }
            }
            // On efface l'application et toutes ses relations associées
            ApplicationFacade.deleteAll( pApplicationConf, session );

            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pApplicationConf.getId() ) };
            String message = ACMessages.getString( "ac.exception.purge.purgeapplication", tab );
            status = new Integer( 1 );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return status;
    }

    /**
     * permet de purger un audit particulier
     * 
     * @param pAudit AuditDTO renseignant l'ID de l'audit
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBF81603D7
     */
    public Integer purgeAudit( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Suppression d'un audit via AuditFacade
            AuditFacade.delete( pAudit, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pAudit.getID() ) };
            String message = ACMessages.getString( "ac.exception.purge.purgeaudit", tab );
            status = new Integer( 1 );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return status;
    }
}
