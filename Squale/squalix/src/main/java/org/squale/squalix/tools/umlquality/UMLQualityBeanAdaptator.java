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
package org.squale.squalix.tools.umlquality;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityModelMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityPackageMetricsBO;
import org.squale.squalix.util.repository.ComponentRepository;

/**
 * Prend en charge de remplacer tous les noms des composants par les objets
 * 
 * @author sportorico
 */
public class UMLQualityBeanAdaptator
{
    /**
     * Contient la liste des composant complexe
     */
    private Map mComplexeComponents = null;

    /**
     * Projet sur lequel est réalisée l'analyse.
     */
    private ProjectBO mProject = null;

    /**
     * Repository
     */
    private ComponentRepository mRepository;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( UMLQualityBeanAdaptator.class );

    /**
     * Constructeur
     * 
     * @param pProject le projet sur lequel est réalisée l'analyse.
     * @param pRepository le repository
     */
    public UMLQualityBeanAdaptator( final ProjectBO pProject, ComponentRepository pRepository )
    {
        mProject = pProject;
        mRepository = pRepository;
        mComplexeComponents = new HashMap();

    }

    /**
     * Adapte le bean des résultats des composants UML et le fait persister.<br>
     * En utilisant nom du composant récupéré depuis les rapports(resultats)UMLQuality, on crée<br>
     * la relation avec le composant adéquat.<br>
     * 
     * @param pComponentResult Ensemble de résultats devant être modifiés et persistés.
     * @return true si le resultat a été adapté si non false
     * @throws Exception si le composant UML n'a pas été instancier
     */
    public boolean adaptComponentResult( final UMLQualityMetricsBO pComponentResult )
        throws Exception
    {
        LOGGER.debug( UMLQualityMessages.getString( "logs.debug.adapt_composant" )
            + pComponentResult.getComponentName() );

        boolean result = false; // resultat de la methode
        String name = null; // nom du composant
        String parentName = null; // nom du parent direct
        AbstractComponentBO component = null, adaptedComponent; // un composant UML

        name = UMLQualityUtility.getName( pComponentResult.getComponentName() );// recupere le nom du composant
        // sur lequel les resultats(métriques)
        // a été générés
        if ( name != null )
        {// nom non vide
            parentName = UMLQualityUtility.getParentName( pComponentResult.getComponentName() );// le nom du parent
                                                                                                // direct

            if ( null != parentName )
            {// le parent direct n'existe pas
                AbstractComplexComponentBO conplexComponentBO =
                    (AbstractComplexComponentBO) mComplexeComponents.get( parentName );// recherche du parent direct
                if ( null != conplexComponentBO )
                {// le parent direct existe
                    /* on instancie un composant */
                    component =
                        UMLQualityUtility.newInstance( UMLQualityMessages.getString( "uml.component.package.name" )
                            + UMLQualityMessages.getString( "uml.component.prefix.name" )
                            + UMLQualityUtility.getUmlComponentName( pComponentResult.getClass().getName() )
                            + UMLQualityMessages.getString( "uml.component.sufix.name" ), name );

                    if ( component instanceof AbstractComplexComponentBO )
                    {
                        mComplexeComponents.put( pComponentResult.getComponentName(), component );

                    }

                    component.setParent( conplexComponentBO );// établie le lien de parenté entre deux composants
                    adaptedComponent = mRepository.persisteComponent( component );

                    pComponentResult.setComponent( adaptedComponent );// établie le lien entre resutat(metrique) et
                                                                        // composant

                    if ( pComponentResult instanceof UMLQualityPackageMetricsBO )
                    {
                        // on ne persiste pas les mesures UMLQualityPackage
                        result = false;
                    }
                    else
                    {
                        result = true;
                    }

                }
            }
            else
            {
                // si c'est le resultat(métrique)d'un composant UML racine (Model)
                if ( pComponentResult instanceof UMLQualityModelMetricsBO )
                {

                    component =
                        UMLQualityUtility.newInstance( UMLQualityMessages.getString( "uml.component.package.name" )
                            + UMLQualityMessages.getString( "uml.component.prefix.name" )
                            + UMLQualityUtility.getUmlComponentName( pComponentResult.getClass().getName() )
                            + UMLQualityMessages.getString( "uml.component.sufix.name" ), name );

                    mComplexeComponents.put( pComponentResult.getComponentName(), component );

                    component.setParent( mProject );// établie le lien de parenté entre le composant
                    // et le sous-projet
                    adaptedComponent = mRepository.persisteComponent( component );

                    pComponentResult.setComponent( adaptedComponent );
                    result = true;
                }
            }
        }
        return result;
    }

}
