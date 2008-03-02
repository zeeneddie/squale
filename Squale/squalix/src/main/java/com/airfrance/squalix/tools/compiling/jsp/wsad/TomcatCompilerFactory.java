package com.airfrance.squalix.tools.compiling.jsp.wsad;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.jsp.bean.J2eeWSADProject;

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
