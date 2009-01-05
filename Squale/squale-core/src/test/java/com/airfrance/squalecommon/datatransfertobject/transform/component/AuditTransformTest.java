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
package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.util.GregorianCalendar;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AuditTransformTest
    extends SqualeTestCase
{

    /**
     * Ces tests ne sont plus utilisés
     */
    public void testDto2Bo()
    {
        // Initialisation des parametres de la methode
        AuditDTO auditIn = new AuditDTO(); // à initialiser
        auditIn.setDate( GregorianCalendar.getInstance().getTime() );
        auditIn.setName( "bonjour" );
        auditIn.setID( 123 );

        ComponentDTO application = new ComponentDTO();
        application.setID( 123 );
        auditIn.setApplicationId( application.getID() );

        // Initialisation du retour
        AuditBO auditOut = null;

        // Execution de la methode
        auditOut = AuditTransform.dto2Bo( auditIn );

        // Verification de l'égalité des attributs des classes
        assertEquals( auditIn.getID(), auditOut.getId() );
        assertSame( auditIn.getType(), auditOut.getType() );
        assertSame( auditIn.getName(), auditOut.getName() );
        assertSame( auditIn.getDate(), auditOut.getDate() );
        // assertEquals(auditIn.getApplicationId(), auditOut.getApplication().getId());

    }

    /**
     * Ces tests ne sont plus utilisés
     */
    public void testBo2Dto()
    {

        // Initialisation des parametres de la methode
        AuditBO auditIn = new AuditBO(); // à initialiser integralement ???
        long applicationId = 3;
        auditIn.setDate( GregorianCalendar.getInstance().getTime() );
        auditIn.setName( "audit1" );
        auditIn.setId( 123 );

        // Initialisation du retour
        AuditDTO auditOut = null;

        // Execution de la methode
        auditOut = AuditTransform.bo2Dto( auditIn, applicationId );

        // Verification de l'égalité des attributs des classes
        assertEquals( auditIn.getId(), auditOut.getID() );
        assertSame( auditIn.getType(), auditOut.getType() );
        assertSame( auditIn.getName(), auditOut.getName() );
        assertSame( auditIn.getDate(), auditOut.getDate() );
        // assertEquals(auditIn.getApplication().getId(), auditOut.getApplicationId());

    }

}
