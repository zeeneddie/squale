/*
 * Créé le 18 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalix.tools.computing.project;

import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.core.AbstractTask;

/**
 * UNIT_PENDING : Long à mettre à compléter. Test insuffisant, aucun assert!
 */
public class ComputeResultTaskTest
    extends SqualeTestCase
{

    /**
     * Test de ComputeResultTask : run()
     */
    public void testRun()
    {
        try
        {
            ISession session = getSession();
            ComputeResultTask computeResultTask = null;
            ApplicationBO application = getComponentFactory().createApplication( session );
            // Collection audits = application.getAudits();
            AuditBO audit =
                AuditDAOImpl.getInstance().getLastAuditByApplication( session, application.getId(), null,
                                                                      AuditBO.TERMINATED );
            Iterator itProject = application.getChildren().iterator();
            while ( itProject.hasNext() )
            {
                ProjectBO project = (ProjectBO) itProject.next();
                // Iterator itAudit = audits.iterator();
                // while (itAudit.hasNext()) {
                // AuditBO audit = (AuditBO) itAudit.next();
                computeResultTask = new ComputeResultTask();
                computeResultTask.setAuditId( new Long( audit.getId() ) );
                computeResultTask.setProjectId( new Long( project.getId() ) );
                computeResultTask.setStatus( AbstractTask.NOT_ATTEMPTED );
                computeResultTask.run();
                // }
            }
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
        }
    }

}
