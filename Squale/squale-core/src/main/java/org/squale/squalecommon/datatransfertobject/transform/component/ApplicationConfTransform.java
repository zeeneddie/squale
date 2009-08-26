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
/*
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.datatransfertobject.component.ProjectConfDTO;
import org.squale.squalecommon.datatransfertobject.config.ServeurDTO;
import org.squale.squalecommon.datatransfertobject.transform.access.UserAccessTransform;
import org.squale.squalecommon.datatransfertobject.transform.config.ServeurTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.UserBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ApplicationConfTransform
    implements Serializable
{

    /**
     * ApplicationConfDTO --> ApplicationBO
     * 
     * @param pApplicationConfDTO ApplicationDTO à transformer
     * @return ApplicationBO
     */
    public static ApplicationBO dto2Bo( ApplicationConfDTO pApplicationConfDTO )
    {

        // Initialisation du retour
        ApplicationBO applicationBO = new ApplicationBO();
        applicationBO.setAuditFrequency( pApplicationConfDTO.getAuditFrequency() );
        applicationBO.setId( pApplicationConfDTO.getId() );
        applicationBO.setName( pApplicationConfDTO.getName() );
        applicationBO.setResultsStorageOptions( pApplicationConfDTO.getResultsStorageOptions() );
        applicationBO.setExternalDev( pApplicationConfDTO.getExternalDev() );
        applicationBO.setInProduction( pApplicationConfDTO.getInProduction() );
        applicationBO.setQualityApproachOnStart( pApplicationConfDTO.getQualityApproachOnStart() );
        applicationBO.setInInitialDev( pApplicationConfDTO.getInInitialDev() );
        applicationBO.setGlobalCost( pApplicationConfDTO.getGlobalCost() );
        applicationBO.setDevCost( pApplicationConfDTO.getDevCost() );
        if ( pApplicationConfDTO.getServeurDTO() != null )
        {
            applicationBO.setServeurBO( (ServeurBO) ServeurTransform.dto2bo( pApplicationConfDTO.getServeurDTO() ) );
        }

        return applicationBO;
    }

    /**
     * ApplicationBO -> ApplicationConfDTO
     * 
     * @param pApplicationBO ApplicationBO
     * @param pUserBOs collection de UserBOs
     * @return ApplicationConfDTO
     */
    public static ApplicationConfDTO bo2Dto( ApplicationBO pApplicationBO, Collection pUserBOs )
    {

        // Initialisation
        Collection Projects = new ArrayList(); // collection de sous-projets
        HashMap users = new HashMap(); // map de Users

        // Initialisation du retour
        ApplicationConfDTO applicationConfDTO = new ApplicationConfDTO();
        applicationConfDTO.setAuditFrequency( pApplicationBO.getAuditFrequency() );
        applicationConfDTO.setId( pApplicationBO.getId() );
        applicationConfDTO.setLastUpdate( pApplicationBO.getLastUpdate() );
        applicationConfDTO.setLastUser( pApplicationBO.getLastUser() );
        applicationConfDTO.setName( pApplicationBO.getName() );
        applicationConfDTO.setResultsStorageOptions( pApplicationBO.getResultsStorageOptions() );
        applicationConfDTO.setStatus( pApplicationBO.getStatus() );
        applicationConfDTO.setPublic( pApplicationBO.getPublic() );
        applicationConfDTO.setExternalDev( pApplicationBO.getExternalDev() );
        applicationConfDTO.setInProduction( pApplicationBO.getInProduction() );
        applicationConfDTO.setInInitialDev( pApplicationBO.getInInitialDev() );
        applicationConfDTO.setQualityApproachOnStart( pApplicationBO.getQualityApproachOnStart() );
        applicationConfDTO.setGlobalCost( pApplicationBO.getGlobalCost() );
        applicationConfDTO.setDevCost( pApplicationBO.getDevCost() );
        if ( null != pApplicationBO.getUserAccesses() )
        {
            applicationConfDTO.setAccesses( UserAccessTransform.bo2dto( pApplicationBO.getUserAccesses() ) );
        }
        if ( pApplicationBO.getServeurBO() != null )
        {
            applicationConfDTO.setServeurDTO( ServeurTransform.bo2dto( pApplicationBO.getServeurBO() ) );
        }
        // Traitement de la collection de sous-projets
        if ( null != pApplicationBO.getChildren() )
        {

            Iterator iterator = pApplicationBO.getChildren().iterator();
            ProjectConfDTO currentProject = null;

            while ( iterator.hasNext() )
            {
                ProjectBO bo = (ProjectBO) iterator.next();
                // On l'ajoute à la liste des projets seulement si
                // il n'est pas supprimé
                if ( bo.getStatus() != ProjectBO.DELETED )
                {
                    currentProject = ProjectConfTransform.bo2Dto( bo );
                    Projects.add( currentProject );
                }
            }
        }
        applicationConfDTO.setProjectConf( Projects );

        // Traitement des Users
        if ( pUserBOs != null )
        {

            Iterator userIterator = pUserBOs.iterator();
            UserBO currentUser = null;

            while ( userIterator.hasNext() )
            {

                currentUser = (UserBO) userIterator.next();

                users.put( currentUser.getMatricule(),
                           ( (ProfileBO) currentUser.getRights().get( pApplicationBO ) ).getName() );

            }
        }
        applicationConfDTO.setUsers( users );

        return applicationConfDTO;
    }

    /**
     * ApplicationBO -> ApplicationConfDTO
     * 
     * @param pApplicationBO ApplicationBO
     * @return ApplicationConfDTO
     */
    public static ApplicationConfDTO bo2Dto( ApplicationBO pApplicationBO )
    {

        // Initialisation
        Collection Projects = new ArrayList(); // collection de sous-projets

        // Initialisation du retour
        ApplicationConfDTO applicationConfDTO = new ApplicationConfDTO();
        applicationConfDTO.setAuditFrequency( pApplicationBO.getAuditFrequency() );
        applicationConfDTO.setId( pApplicationBO.getId() );
        applicationConfDTO.setLastUpdate( pApplicationBO.getLastUpdate() );
        applicationConfDTO.setLastUser( pApplicationBO.getLastUser() );
        applicationConfDTO.setName( pApplicationBO.getName() );
        applicationConfDTO.setResultsStorageOptions( pApplicationBO.getResultsStorageOptions() );
        applicationConfDTO.setStatus( pApplicationBO.getStatus() );
        applicationConfDTO.setPublic( pApplicationBO.getPublic() );
        applicationConfDTO.setExternalDev( pApplicationBO.getExternalDev() );
        applicationConfDTO.setInProduction( pApplicationBO.getInProduction() );
        applicationConfDTO.setInInitialDev( pApplicationBO.getInInitialDev() );
        applicationConfDTO.setQualityApproachOnStart( pApplicationBO.getQualityApproachOnStart() );
        applicationConfDTO.setGlobalCost( pApplicationBO.getGlobalCost() );
        applicationConfDTO.setDevCost( pApplicationBO.getDevCost() );
        if ( pApplicationBO.getServeurBO() != null )
        {
            applicationConfDTO.setServeurDTO( (ServeurDTO) ServeurTransform.bo2dto( pApplicationBO.getServeurBO() ) );
        }
        if ( null != pApplicationBO.getUserAccesses() )
        {
            applicationConfDTO.setAccesses( UserAccessTransform.bo2dto( pApplicationBO.getUserAccesses() ) );
        }
        // Traitement de la collection de sous-projets
        if ( null != pApplicationBO.getChildren() )
        {

            Iterator iterator = pApplicationBO.getChildren().iterator();
            ProjectConfDTO currentProject = null;

            while ( iterator.hasNext() )
            {

                currentProject = ProjectConfTransform.bo2Dto( (ProjectBO) iterator.next() );
                Projects.add( currentProject );
            }
        }
        applicationConfDTO.setProjectConf( Projects );

        return applicationConfDTO;
    }

    /**
     * Permet de modifier les valeurs souhaitées dans ApplicationDTO sans ecraser les relations
     * 
     * @param pApplicationConfDTO ApplicationDTO
     * @param pApplicationBO ApplicationBO
     */
    public static void dto2Bo( ApplicationConfDTO pApplicationConfDTO, ApplicationBO pApplicationBO )
    {

        pApplicationBO.setAuditFrequency( pApplicationConfDTO.getAuditFrequency() );
        pApplicationBO.setId( pApplicationConfDTO.getId() );
        pApplicationBO.setName( pApplicationConfDTO.getName() );
        pApplicationBO.setResultsStorageOptions( pApplicationConfDTO.getResultsStorageOptions() );
        pApplicationBO.setStatus( pApplicationConfDTO.getStatus() );
        pApplicationBO.setPublic( pApplicationConfDTO.getPublic() );
        pApplicationBO.setExternalDev( pApplicationConfDTO.getExternalDev() );
        pApplicationBO.setInProduction( pApplicationConfDTO.getInProduction() );
        pApplicationBO.setLastUpdate( pApplicationConfDTO.getLastUpdate() );
        pApplicationBO.setLastUser( pApplicationConfDTO.getLastUser() );
        pApplicationBO.setQualityApproachOnStart( pApplicationConfDTO.getQualityApproachOnStart() );
        pApplicationBO.setInInitialDev( pApplicationConfDTO.getInInitialDev() );
        pApplicationBO.setGlobalCost( pApplicationConfDTO.getGlobalCost() );
        pApplicationBO.setDevCost( pApplicationConfDTO.getDevCost() );
        if ( pApplicationConfDTO.getServeurDTO() != null )
        {
            pApplicationBO.setServeurBO( (ServeurBO) ServeurTransform.dto2bo( pApplicationConfDTO.getServeurDTO() ) );
        }
        if ( null != pApplicationConfDTO.getAccesses() )
        {
            pApplicationBO.setUserAccesses( UserAccessTransform.dto2bo( pApplicationConfDTO.getAccesses() ) );
        }

    }

}
