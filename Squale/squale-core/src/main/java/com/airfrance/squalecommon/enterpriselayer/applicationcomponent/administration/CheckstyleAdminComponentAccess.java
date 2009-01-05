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
package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.CheckstyleFacade;

/**
 * <p>
 * Title : RulesCheckingAdminComponentAccess.java
 * </p>
 * <p>
 * Description : Application component de l'outil checkstyle.
 * </p>
 * <p>
 * Copyright : Copyright (c) 2006
 * </p>
 * <p>
 * Company : HENIX
 * </p>
 */
public class CheckstyleAdminComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * permet de recupérer toutes les versions existantes du fichier de configuration checkstyle
     * 
     * @return List la liste des versions
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getAllConfigurations()
        throws JrafEnterpriseException
    {
        return CheckstyleFacade.getAllVersions();
    }

    /**
     * Destruction des jeu de règles inutilisés
     * 
     * @param pRuleSets rulesets devant être détruits
     * @return rulesets utilisés et ne pouvant donc pas être détruits
     * @throws JrafEnterpriseException si erreur
     */
    public Collection deleteRuleSets( Collection pRuleSets )
        throws JrafEnterpriseException
    {
        return CheckstyleFacade.deleteRuleSets( pRuleSets );
    }

    /**
     * Parse le fichier de configuration en fonction du nom du fichier
     * 
     * @param pStream flux fu fichier
     * @param pErrors les erreurs générés pendant le parsing.
     * @return un CheckstyleDTO Complet
     * @throws JrafEnterpriseException exception JRAF
     */
    public CheckstyleDTO importConfiguration( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {

        CheckstyleDTO dto = null;
        dto = CheckstyleFacade.importCheckstyleConfFile( pStream, pErrors );
        return dto;
    }

}
