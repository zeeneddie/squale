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
package com.airfrance.squalix.tools.pmd;

import java.util.HashMap;
import java.util.Map;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;

import net.sourceforge.pmd.SourceType;

/**
 * Traitement PMD sur du code java
 */
public class JavaPmdProcessing
    extends AbstractPmdProcessing
{
    /**
     * Map des relations entre le dialect présent dans les paramètres du projet et les SourceType
     */
    private static final Map SOURCES_TYPE = getSourcesType();

    /** Extensions autorisées */
    private String[] mExtensions = { ".java" };

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.pmd.AbstractPmdProcessing#getLanguage()
     */
    protected String getLanguage()
    {
        return "java";
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.pmd.AbstractPmdProcessing#getSourceType()
     */
    protected SourceType getSourceType()
    {
        return (SourceType) SOURCES_TYPE.get( mDialect );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.pmd.AbstractPmdProcessing#getExtensions()
     */
    protected String[] getExtensions()
    {
        return mExtensions;
    }

    /**
     * @return la map des types de source
     */
    private static Map getSourcesType()
    {
        Map sourcesType = new HashMap();
        sourcesType.put( ParametersConstants.JAVA1_3, SourceType.JAVA_13 );
        sourcesType.put( ParametersConstants.JAVA1_4, SourceType.JAVA_14 );
        sourcesType.put( ParametersConstants.JAVA1_5, SourceType.JAVA_15 );
        return sourcesType;
    }

}
