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
package org.squale.squalecommon.daolayer.result;

import java.util.Collection;
import java.util.Iterator;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.ApplicationDAOImpl;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;

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
