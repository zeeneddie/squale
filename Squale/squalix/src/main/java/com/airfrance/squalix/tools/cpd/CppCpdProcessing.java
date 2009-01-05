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
package com.airfrance.squalix.tools.cpd;

import java.util.Properties;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;

import net.sourceforge.pmd.cpd.Language;
import net.sourceforge.pmd.cpd.LanguageFactory;

/**
 * Détection de copier/coller en C++
 */
public class CppCpdProcessing
    extends AbstractCpdProcessing
{
    /** Seuil de détection de copier/coller */
    private static final int CPP_THRESHOLD = 100;

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getLanguage()
     */
    protected Language getLanguage()
    {
        LanguageFactory lf = new LanguageFactory();
        Properties p = new Properties();
        p.setProperty( "ignore_literals", "false" );
        return lf.createLanguage( LanguageFactory.CPP_KEY, p );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getTokenThreshold()
     */
    protected int getTokenThreshold()
    {
        return CPP_THRESHOLD;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getExtension()
     */
    protected String[] getExtensions()
    {
        return new String[] { ".h", ".c", ".cc", ".cpp", ".cxx" };
    }

    /**
     * Obtention des répertoires exclus de la compilation
     * 
     * @param pProjectParams paramètres du projet
     * @return répertories exclus sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getExcludedDirs( MapParameterBO pProjectParams )
    {
        // Il n'y a pas de répertoires exclus en C++
        return null;
    }

}
