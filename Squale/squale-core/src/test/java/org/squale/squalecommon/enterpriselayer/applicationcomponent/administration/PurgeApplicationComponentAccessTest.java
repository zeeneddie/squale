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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class PurgeApplicationComponentAccessTest
    extends SqualeTestCase
{

    /** provider de persistence */
    private static IPersistenceProvider PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * test sur la purge d'un composant de niveau application
     * 
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testPurgeApplicationComponentAccess()
        throws JrafEnterpriseException
    {
        // Teste si la construction de l'application component par AccessDelegateHelper
        IApplicationComponent appComponent;
        appComponent = AccessDelegateHelper.getInstance( "Purge" );
        assertNotNull( appComponent );

    }

    /**
     * test sur la purge d'une application
     * 
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testPurgeApplication()
        throws JrafEnterpriseException
    {
        try
        {
            ISession session = PERSISTENT_PROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            FacadeHelper.closeSession( session, "" );
            // Teste si la methode est accessible par AccessDelegateHelper
            // et si l'objet renvoyé n'est pas nul
            IApplicationComponent appComponent;

            // Initialisation des parametres de la methode
            ApplicationConfDTO applicationConf = new ApplicationConfDTO(); // à initialiser
            applicationConf.setId( application.getId() );

            // Initialisation des parametres sous forme de tableaux d'objets
            Object[] paramIn = new Object[1];
            paramIn[0] = applicationConf;

            // Execution de la methode
            appComponent = AccessDelegateHelper.getInstance( "Purge" );
            appComponent.execute( "purgeApplication", paramIn );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected error" );
        }
    }

    /**
     * test sur la purge d'un audit
     * 
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testPurgeAudit()
        throws JrafEnterpriseException
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        AuditDTO audit = new AuditDTO(); // à initialiser
        ComponentDTO application = new ComponentDTO(); // à initialiser
        audit.setApplicationId( application.getID() );

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = audit;

        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Purge" );
        appComponent.execute( "purgeAudit", paramIn );
    }

}
