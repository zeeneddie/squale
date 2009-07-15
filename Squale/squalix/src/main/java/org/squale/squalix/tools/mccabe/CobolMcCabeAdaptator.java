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
package com.airfrance.squalix.tools.mccabe;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO;
import com.airfrance.squalix.util.parser.CobolParser;
import com.airfrance.squalix.util.repository.ComponentRepository;

/**
 * Adaptateur des composants du Cobol.
 */
public class CobolMcCabeAdaptator
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( CobolMcCabeAdaptator.class );

    /**
     * Repository des composants du langage.
     */
    private ComponentRepository mRepository;

    /**
     * Parser du Cobol.
     */
    private CobolParser mParser;

    /**
     * Constructeur.
     * 
     * @param pComponentRepository le repository des composants du langage
     * @param pCobolParser le parser du Cobol
     */
    public CobolMcCabeAdaptator( final ComponentRepository pComponentRepository, final CobolParser pCobolParser )
    {
        mRepository = pComponentRepository;
        mParser = pCobolParser;
    }

    /**
     * Créé les composants <code>programme</code> et <code>module</code> correspondant à une ligne du rapport
     * McCabe, associe les métriques de cette ligne à ces composants, et réalise la persistence en base des composants.
     * 
     * @param pModuleResult ensemble des métriques du module devant être enregistrés en base
     * @throws JrafDaoException si exception Jraf
     */
    public String adaptModuleResult( final McCabeQAMethodMetricsBO pModuleResult )
        throws JrafDaoException
    {
        // analyse des noms de programme et de module
        String lFullModuleName = pModuleResult.getComponentName();
        String lFileName = pModuleResult.getFilename();
        List<String> lPrgAndModuleNames = new ArrayList<String>();
        mParser.getPrgAndModuleNamesForModule( lFullModuleName, lPrgAndModuleNames );

        // enregistrement du programme si non présent en base
        ClassBO lPrgBO = mParser.getProgram( lPrgAndModuleNames.get( 0 ), lFileName );
        // récupération du parent
        PackageBO lPkgBO = (PackageBO) lPrgBO.getParent();
        // enregistrement en base du package parent si non présent en base
        PackageBO persistlPkgBO = (PackageBO) mRepository.persisteComponent( lPkgBO );
        // Mise en place de la relation
        lPrgBO.setParent( persistlPkgBO );
        // enregistrement en base
        ClassBO persistlPrgBO = (ClassBO) mRepository.persisteComponent( lPrgBO );

        // enregistrement du module si non présent en base
        MethodBO lModuleBO = mParser.getModule( lPrgAndModuleNames.get( 1 ), lFileName, persistlPrgBO );
        lModuleBO.setStartLine( Integer.valueOf( pModuleResult.getStartLine() ).intValue() );
        // enregistrement en base
        MethodBO persistlModuleBO = (MethodBO) mRepository.persisteComponent( lModuleBO );

        // association des résultats au module avant enregistrement
        pModuleResult.setComponent( persistlModuleBO );

        return lPrgAndModuleNames.get( 0 );
    }

}
