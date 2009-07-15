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
package org.squale.squalecommon.datatransfertobject.transform.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class UserTransformTest
    extends SqualeTestCase
{

    /**
     * teste la transformation d'un UserDTO en UserBO
     */
    public void testDto2Bo()
    {

        // Initialisation du retour et autres
        UserBO userBO = null;

        // boolean isApplicationMapped = true; // Permet de savoir si tous les fichiers sont mappés

        // Initialisation des projets relatifs à un utilisateur
        Collection applications = new ArrayList();
        ComponentDTO application1 = new ComponentDTO();
        application1.setID( 1 );
        application1.setName( "squale" );
        ComponentDTO application2 = new ComponentDTO();
        application2.setID( 2 );
        application2.setName( "squalix" );
        applications.add( application1 );
        applications.add( application2 );

        UserDTO userDTO = new UserDTO(); // a initialiser
        userDTO.setEmail( "buzz@hotmail.com" );
        userDTO.setFullName( "alexandre bozas" );
        userDTO.setID( 1 );
        userDTO.setMatricule( "m400841" );
        userDTO.setPassword( "xyz" );
        userDTO.setProfiles( new HashMap() );

        userBO = UserTransform.dto2Bo( userDTO );
        userBO.setDefaultProfile( new ProfileBO() );
        assertEquals( userBO.getEmail(), userDTO.getEmail() );
        assertEquals( userBO.getFullName(), userDTO.getFullName() );
        assertEquals( userBO.getMatricule(), userDTO.getMatricule() );
        assertEquals( userBO.getPassword(), userDTO.getPassword() );
        assertEquals( userBO.getId(), userDTO.getID() );

        // TODO Verifier par un test que le projet soit bien construit
        // --> A ete verifie en debug

    }

    /**
     * teste la transformation d'un UserBO en UserDTO
     */
    public void testBo2Dto()
    {

        // Initialisation du retour et autres
        UserDTO userDTO = null;
        // boolean isApplicationMapped = true; // Permet de savoir si tous les fichiers sont mappés

        // Initialisation des projets relatifs à un utilisateur
        Map applications = new HashMap();
        ApplicationBO application1 = new ApplicationBO();
        application1.setId( 1 );
        application1.setName( "squale" );
        ApplicationBO application2 = new ApplicationBO();
        application2.setId( 2 );
        application2.setName( "squalix" );
        applications.put( application1, null );
        applications.put( application2, null );

        UserBO userBO = new UserBO(); // a initialiser
        // Initialisation du profil par defaut
        ProfileBO defaultProfile = new ProfileBO();
        defaultProfile.setName( "admin" );

        userBO.setDefaultProfile( defaultProfile );
        userBO.setEmail( "buzz@hotmail.com" );
        userBO.setFullName( "alexandre bozas" );
        userBO.setId( 1 );
        userBO.setMatricule( "m400841" );
        userBO.setPassword( "xyz" );
        userBO.setRights( applications );

        userDTO = UserTransform.bo2Dto( userBO );

        assertEquals( userBO.getEmail(), userDTO.getEmail() );
        assertEquals( userBO.getDefaultProfile().getName(), userDTO.getDefaultProfile().getName() );
        assertEquals( userBO.getFullName(), userDTO.getFullName() );
        assertEquals( userBO.getMatricule(), userDTO.getMatricule() );
        assertEquals( userBO.getPassword(), userDTO.getPassword() );
        assertEquals( userBO.getId(), userDTO.getID() );

        // Verifier que le projet est bien construit

    }

}
