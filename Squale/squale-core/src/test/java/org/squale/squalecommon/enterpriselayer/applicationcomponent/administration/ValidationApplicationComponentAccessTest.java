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

import java.util.ArrayList;
import java.util.Collection;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.SqualeTestCase;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ValidationApplicationComponentAccessTest
    extends SqualeTestCase
{

    /**
     * teste la validation d'un composant de niveau application
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testValidationApplicationComponentAccess()
        throws JrafEnterpriseException
    {
        // Teste si la construction de l'application component par AccessDelegateHelper
        IApplicationComponent appComponent;
        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Validation" );
        assertNotNull( appComponent );
    }

    /**
     * Teste sur la suppression d'une application venant d'etre créée
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testRemoveApplicationsCreation()
        throws JrafEnterpriseException
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        Collection applicationsToRemove = new ArrayList(); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = applicationsToRemove;

        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Validation" );
        appComponent.execute( "removeApplicationsCreation", paramIn );

    }

    /**
     * teste l'enregistrement d'une application venant d'etre créée
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testValidateApplicationsCreation()
        throws JrafEnterpriseException
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        Collection applicationsToValidate = new ArrayList(); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = applicationsToValidate;

        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Validation" );
        appComponent.execute( "validateApplicationsCreation", paramIn );
    }

    /**
     * test la récupération d'une application créée
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testGetApplicationsCreated()
        throws JrafEnterpriseException
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Validation" );
        appComponent.execute( "getApplicationsCreated" );

    }

    /**
     * teste la suppression des références sur une application
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testRemoveApplicationsReference()
        throws JrafEnterpriseException
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        Collection applicationsToRemove = new ArrayList(); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = applicationsToRemove;

        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Validation" );
        appComponent.execute( "removeApplicationsReference", paramIn );
    }

    /**
     * Teste la récupération des références d'une application
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void tesGetReference()
        throws JrafEnterpriseException
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation du retour de la methode
        Collection references = new ArrayList();

        // Initialisation des parametres de la methode
        Integer nbLignes = new Integer( 20 ); // à initialiser
        Integer indexDepart = new Integer( 0 ); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[2];
        paramIn[0] = nbLignes;
        paramIn[1] = indexDepart;
        // utilisateur admin
        paramIn[2] = new Boolean( true );
        paramIn[3] = new ArrayList( 0 );
        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance( "Results" );
        appComponent.execute( "getReference", paramIn );
    }

}
