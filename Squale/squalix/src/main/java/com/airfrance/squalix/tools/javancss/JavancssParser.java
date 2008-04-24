package com.airfrance.squalix.tools.javancss;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;
import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.core.TaskException;

/**
 * This class contains all the methods needed for doing the parse of the javancss xml result file
 */
public class JavancssParser
    extends XmlImport
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JavancssParser.class );

    /**
     * Default Constructor
     */
    public JavancssParser()
    {
        super( LOGGER );
    }

    /**
     * Execute the parsing of the result file
     * 
     * @param inp The stream of the file to parse
     * @return JavancssResult The object with all the result inside
     * @throws TaskException This exception happen if the xml result file does not exist
     */
    public JavancssResult parsing( InputStream inp )
        throws TaskException
    {
        JavancssResult res = null;
        StringBuffer errors = new StringBuffer();
        Digester digester = preSetupDigester( null, null, errors );
        digester.addObjectCreate( "javancss", JavancssResult.class );

        
        //CHECKSTYLE:OFF pb of magic number
        // Rule for the object results
        digester.addCallMethod( "javancss/objects/object", "addClass", 5, new Class[] { String.class, Integer.class,
            Integer.class, Integer.class, Integer.class } );
        digester.addCallParam( "javancss/objects/object/name", 0 );
        digester.addCallParam( "javancss/objects/object/ncss", 1 );
        digester.addCallParam( "javancss/objects/object/functions", 2 );
        digester.addCallParam( "javancss/objects/object/classes", 3 );
        digester.addCallParam( "javancss/objects/object/javadocs", 4 );

        // Rule for the method results
        digester.addCallMethod( "javancss/functions/function", "addMethod", 4, new Class[] { String.class,
            Integer.class, Integer.class, Integer.class } );
        digester.addCallParam( "javancss/functions/function/name", 0 );
        digester.addCallParam( "javancss/functions/function/ncss", 1 );
        digester.addCallParam( "javancss/functions/function/ccn", 2 );
        digester.addCallParam( "javancss/functions/function/javadocs", 3 );

        // Rule for the package results
        digester.addCallMethod( "javancss/packages/package", "addPackage", 8, new Class[] { String.class,
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class } );
        digester.addCallParam( "javancss/packages/package/name", 0 );
        digester.addCallParam( "javancss/packages/package/classes", 1 );
        digester.addCallParam( "javancss/packages/package/functions", 2 );
        digester.addCallParam( "javancss/packages/package/ncss", 3 );
        digester.addCallParam( "javancss/packages/package/javadocs", 4 );
        digester.addCallParam( "javancss/packages/package/javadoc_lines", 5 );
        digester.addCallParam( "javancss/packages/package/single_comment_lines", 6 );
        digester.addCallParam( "javancss/packages/package/multi_comment_lines", 7 );

        // Rule for the project results
        digester.addCallMethod( "javancss/packages/total", "addProject", 7, new Class[] { Integer.class,
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class } );
        digester.addCallParam( "javancss/packages/total/classes", 0 );
        digester.addCallParam( "javancss/packages/total/functions", 1 );
        digester.addCallParam( "javancss/packages/total/ncss", 2 );
        digester.addCallParam( "javancss/packages/total/javadocs", 3 );
        digester.addCallParam( "javancss/packages/total/javadoc_lines", 4 );
        digester.addCallParam( "javancss/packages/total/single_comment_lines", 5 );
        digester.addCallParam( "javancss/packages/total/multi_comment_lines", 6 );
        //CHECKSTYLE:ON
        res = (JavancssResult) doParse( digester, inp, errors );

        return res;

    }

    /**
     * Parsing du fichier XML Le parsing est exécuté, puis le flux est fermé
     * 
     * @param pConfigDigester digester
     * @param pStream flux de grille
     * @param pErrors erreurs
     * @return the last object create by the parsing
     */
    protected Object doParse( Digester pConfigDigester, InputStream pStream, StringBuffer pErrors )
    {
        Object obj = null;
        try
        {
            obj = pConfigDigester.parse( pStream );

        }
        catch ( IOException e )
        {
            // Traitement par défaut de l'exception
            handleException( e, pErrors );
        }
        catch ( SAXException e )
        {
            // Traitement par défaut de l'exception
            handleException( e, pErrors );
        }
        finally
        {
            try
            {
                // Fermeture du flux en entrée
                pStream.close();
            }
            catch ( IOException e1 )
            {
                // Traitement par défaut de l'exception
                handleException( e1, pErrors );
            }
        }
        return obj;
    }

}
