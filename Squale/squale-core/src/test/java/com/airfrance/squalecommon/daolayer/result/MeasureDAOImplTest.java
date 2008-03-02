package com.airfrance.squalecommon.daolayer.result;

import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * @author M400843 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class MeasureDAOImplTest
    extends SqualeTestCase
{

    /**
     * @throws JrafDaoException si erreur
     */
    public void testRemoveWithComponentISessionAbstractComplexComponentBO_Recursive()
        throws JrafDaoException
    {
        ISession session = getSession();
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        ApplicationDAOImpl applicationDAO = ApplicationDAOImpl.getInstance();
        AuditDAOImpl auditDAO = AuditDAOImpl.getInstance();

        session.beginTransaction();
        Iterator itApplication = applicationDAO.findAll( session ).iterator();
        while ( itApplication.hasNext() )
        {
            ApplicationBO application = (ApplicationBO) itApplication.next();
            Iterator itAudit = auditDAO.findAll( session ).iterator();
            while ( itAudit.hasNext() )
            {
                AuditBO audit = (AuditBO) itAudit.next();
                measureDAO.removeWhereComponent( session, application, audit, true );
            }
        }
        session.commitTransactionWithoutClose();

        session = getSession();
        Collection measuresByFindAll = measureDAO.findAll( session );
        assertTrue( measuresByFindAll.size() == 0 );
    }
}
