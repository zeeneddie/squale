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
package org.squale.squalix.tools.compiling.jsp.wsad;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalix.tools.compiling.CompilingMessages;
import org.squale.squalix.tools.compiling.jsp.bean.J2eeWSADProject;

/**
 * Fabrique de compilateur Tomcat
 */
public class TomcatCompilerFactory
{

    /**
     * Obtention du traitement de compilation des Jsps avec Tomcat correspondant à la version des servlets
     * 
     * @param pJ2eeProject le projet wsad
     * @param pServletVersion les spécifications servlets
     * @return compilateur correspondant
     * @throws TomcatCompilerException si la version est inconnue
     */
    public AbstractTomcatCompiler createCompiler( J2eeWSADProject pJ2eeProject, String pServletVersion )
        throws TomcatCompilerException
    {
        AbstractTomcatCompiler result;
        if ( pServletVersion.equals( ParametersConstants.J2EE1_5 )
            || pServletVersion.equals( ParametersConstants.J2EE1_4 ) )
        {
            result = new JWSADJspTomcat2_4Compiler( pJ2eeProject );
        }
        else if ( pServletVersion.equals( ParametersConstants.J2EE1_3 )
            || pServletVersion.equals( ParametersConstants.J2EE1_2 ) )
        {
            result = new JWSADJspTomcat2_3Compiler( pJ2eeProject );
        }
        else
        {
            throw new TomcatCompilerException( CompilingMessages.getString( "exception.unknown.servlet_version",
                                                                            pServletVersion ) );
        }
        return result;
    }

}
