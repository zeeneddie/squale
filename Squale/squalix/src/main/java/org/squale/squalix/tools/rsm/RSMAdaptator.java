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
package org.squale.squalix.tools.rsm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMClassMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMMethodMetricsBO;
import org.squale.squalix.util.parser.LanguageParser;
import org.squale.squalix.util.repository.ComponentRepository;

/**
 * Est en charge de remplacer tous les noms des méthodes par les objets correspondant.<br>
 * Ceci permet d'utiliser les relations proposées par la base de données.<br>
 */
public class RSMAdaptator
{

    /**
     * Parser
     */
    private LanguageParser mLanguageParser;

    /**
     * Repository
     */
    private ComponentRepository mRepository;

    /**
     * Map permettant de référencer les différentes méthodes Set dans le cas de polymorphisme pour etre sur de récupérer
     * la bonne méthode
     */
    private Map mMethodsMap;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( RSMAdaptator.class );

    /**
     * Constructeur
     * 
     * @param pParser le parser
     * @param pRepository le repository
     */
    public RSMAdaptator( LanguageParser pParser, ComponentRepository pRepository )
    {
        mLanguageParser = pParser;
        mRepository = pRepository;
        mMethodsMap = new HashMap();
    }

    /**
     * Adapte le bean des résultats des méthodes et le fait persister.<br>
     * En utilisant nom du composant récupéré depuis le rapport RSM, on crée la relation avec le composant adéquat.
     * 
     * @param pMethodResult Ensemble de résultats de la méthode devant être modifiés et persistés.
     * @return true si on a pu correctement rattacher l'objet et le faire persister
     * @throws JrafDaoException si erreur
     */
    public boolean adaptMethodResult( final RSMMethodMetricsBO pMethodResult )
        throws JrafDaoException
    {
        LOGGER.debug( RSMMessages.getString( "logs.debug.adapt_method" ) + pMethodResult.getComponentName() );
        boolean isPersisted = false;
        // Recherche de la méthode associée
        // RSM oblige a faire un traitement sans utiliser le language parser car il ne remonte pas les paramètres de la
        // méthode
        String methodName = null;
        int index = pMethodResult.getComponentName().lastIndexOf( ":" );
        if ( index == -1 )
        {
            index = pMethodResult.getComponentName().lastIndexOf( "." );
        }
        if ( index != -1 )
        {
            methodName =
                pMethodResult.getComponentName().substring( index + 1, pMethodResult.getComponentName().length() );
        }
        if ( null != methodName )
        {
            Collection methods =
                mRepository.getSimilarMethods( methodName, pMethodResult.getFileName(),
                                               pMethodResult.getAudit().getId() );
            // Problème avec le polymorphisme RSM:
            // Si aucune méthode n'est remontée, c'est une erreur du au fait qu'aucun autre outil n'a été passé avant
            // pour créer ces méthodes
            // Si plusieurs méthode sont remontées, on ne sauvegarde pas les résultats sur cette méthode
            // Si un seul résultat est remonté, on l'affecte et on fait persister
            if ( methods.size() == 1 )
            {
                MethodBO methodBo = (MethodBO) methods.iterator().next();
                pMethodResult.setComponent( methodBo );
                mRepository.persisteComponent( methodBo );
                isPersisted = true;
            }
        }
        return isPersisted;
    }

    /**
     * Adapte le bean des résultats des classes et le fait persister.
     * 
     * @param pClassResult Ensemble des résultats de la classe devant être modifiés et persistés.
     * @throws JrafDaoException si erreur
     */
    public void adaptClassResult( final RSMClassMetricsBO pClassResult )
        throws JrafDaoException
    {
        LOGGER.debug( RSMMessages.getString( "logs.debug.adapt_class" ) + pClassResult.getComponentName() );
        ClassBO classBO = mLanguageParser.getClass( pClassResult.getComponentName() );
        if ( null != classBO )
        {
            ClassBO persistentClassBO = (ClassBO) mRepository.persisteComponent( classBO );
            pClassResult.setComponent( persistentClassBO );
        }
    }
}
