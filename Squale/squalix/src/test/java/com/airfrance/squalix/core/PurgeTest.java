/*
 * Created on 4 août 06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.airfrance.squalix.core;

import java.util.ArrayList;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * Test pour la purge
 */
// UNIT_KO : Test impossible car le delete ne delete pas en cascade dans hibersonic.
public class PurgeTest
    extends SqualeTestCase
{

    /** application deleted */
    private ApplicationBO appli2;

    /**
     * Set-up
     * 
     * @throws Exception en cas de problèmes
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        // On crée la première application qui aura un audit
        // supprimé et un en attente
        ApplicationBO appli = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        appli.setName( "onepurge" );
        AuditBO audit = new AuditBO();
        audit.setName( "audit1" );
        audit.setType( AuditBO.MILESTONE );
        AuditDAOImpl.getInstance().create( getSession(), audit );
        appli.addAudit( audit );
        audit = new AuditBO();
        audit.setName( "audit2" );
        audit.setType( AuditBO.MILESTONE );
        audit.setStatus( AuditBO.DELETED );
        AuditDAOImpl.getInstance().create( getSession(), audit );
        appli.addAudit( audit );
        ApplicationDAOImpl.getInstance().save( getSession(), appli );

        // On crée la deuxième application avec le statut 'supprimé'
        appli2 = getComponentFactory().createApplication( getSession() );
        appli2.setServeurBO( appli.getServeurBO() );
        appli2.setName( "deletedappli" );
        appli2.setStatus( ApplicationBO.DELETED );
        audit = new AuditBO();
        audit.setName( "audit1" );
        audit.setType( AuditBO.MILESTONE );
        AuditDAOImpl.getInstance().create( getSession(), audit );
        appli2.addAudit( audit );
        ApplicationDAOImpl.getInstance().save( getSession(), appli2 );

        getSession().commitTransactionWithoutClose();

    }

    /**
     * Test de la purge -> impossible en fait :-)
     * 
     * @throws JrafDaoException en cas de pb JRAF
     * @throws InterruptedException en cas de pb JRAF
     */
    public void testPurge()
        throws JrafDaoException, InterruptedException
    {
        // delete ne delete pas en cascade dans hibersonic !
        // donc le test de purge n'est pas possible avec la FK de component_audits
        // Purge purge=new Purge("qvi");
        // purge.start();

        // donc test juste que le nombre d'audit est correct
        // 1 audit supprimé + 1 audit d'appli supprimé
        assertEquals( 2, AuditDAOImpl.getInstance().findDeleted( getSession(), appli2.getServeurBO().getServeurId(),
                                                                 new ArrayList() ).size() );
        getSession().rollbackTransaction();
    }
}
