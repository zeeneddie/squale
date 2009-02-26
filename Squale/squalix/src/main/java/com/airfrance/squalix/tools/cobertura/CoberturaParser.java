package com.airfrance.squalix.tools.cobertura;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaProjectMetricsBO;
import com.airfrance.squalecommon.util.xml.XmlImport;

/**
 * This class allows instantiation of objects which parse result files form Cobertura coverage analysis
 */
public class CoberturaParser
    extends XmlImport
{
    /**
     * Logger of the Cobertura Parser
     */
    private static final Log LOGGER = LogFactory.getLog( CoberturaParser.class );

    /**
     * Default constructor
     */
    public CoberturaParser()
    {
        /* Calling the super one and passing in the LOGGER */
        super( LOGGER );
    }

    /**
     * Main parse method. Used to parse the passed in result file. If you're not familiar with digester please refer to
     * <a href="http://commons.apache.org/digester">Digester main site</a> where you could find usage examples,
     * developers guide and more.
     * 
     * @param pFile the xml file to be parsed
     * @return the result in an object of type {@link CoberturaResultold_TODELETE}
     */
    public CoberturaProjectMetricsBO parse( File pFile )
    {
        /* Instance to store general results */
        CoberturaProjectMetricsBO result = null;
        /* Object to store errors */
        StringBuffer errors = new StringBuffer();
        /* The XML configuration file which sets the parsing rules */
        File configurationFile = new File( "config/coberturaParserXmlRules.xml" );
        /* Preparing processing of the Xml result file */
        Digester digesterXMLconfigured = getConfiguredParser( configurationFile );
        /* Calling the main parsing method */
        result = (CoberturaProjectMetricsBO) executeParsing( digesterXMLconfigured, pFile, errors );
        return result;
    }

    /**
     * <p>
     * This method instantiates a digester rules of which have been set thanks to an external xml configuration file.
     * </p>
     * <p>
     * Please refer to the <a href="http://commons.apache.org/digester/">Digester documentation</a> for more
     * information regarding Digester.
     * </p>
     * 
     * @param pFile the XML configuration input file
     * @return an instance of Digester configured with the specified XML configuration file. Could return null if an
     *         error occurs with the configuration file
     */
    private Digester getConfiguredParser( File pFile )
    {
        /* Instance of digester which will be returned */
        Digester digester = null;

        try
        {
            /* Creating a configured digester thanks to the xml configuration file */
            digester = DigesterLoader.createDigester( pFile.toURL() );
            /* Setting the validation to false */
            digester.setValidating( false );
            /* EntityResolver which sends an empty String to skip DTD validation of the Digester API */
            digester.setEntityResolver( this.new CoberturaResolver() );
        }
        catch ( MalformedURLException urlException )
        {
            /* Logging an error message and sending the absolute path of the configuration file*/
            LOGGER.error( new String (CoberturaMessages.getMessage( "cobertura.parser.urlError", pFile.getAbsolutePath() )) );
        }
        return digester;
    }

    /**
     * Inner class which implements <code>{@link EntityResolver}</code> interface.
     */
    private class CoberturaResolver
        implements EntityResolver
    {
        /**
         * <p>
         * This resolveEntity method is used to specify a local DTD file as it is needed to parse the Cobertura XML
         * result files.<br />
         * <p/>
         * <p>
         * The <b>setValidating(false)</b> method call tells Digester to not validate the XML data. However, it does
         * "not" tell the <b>underlying XML parser</b> to skip the DOCTYPE.
         * </p>
         * <p>
         * We are following advices of the Digester Wiki FAQ that is to say providing our own EntityResolver method
         * whose resolveEntity() method always returns an empty String. That way, the parser won't go traipsing around
         * the network trying to find things that it can't.
         * </p>
         * 
         * @param publicId portable identifier that is essentially a key used to look up the real location of the
         *            corresponding resource
         * @param systemId non-portable identifier usually a reference to a local file
         * @return a new InputSource which is <b>null</b>
         * @throws SAXException if a problem is encountered while reading the DTD
         * @throws IOException if a problem occurs while trying to open the DTD
         */
        public InputSource resolveEntity( String publicId, String systemId )
            throws SAXException, IOException
        {
            InputSource is = new InputSource();
            StringReader sr = new StringReader( "" );
            is.setCharacterStream( sr );
            return is;
        }
    }

    /**
     * <p>
     * This method executes the parsing of the XML result file and closes the stream.
     * </p>
     * 
     * @param pConfigDigester the configured digester
     * @param pFile the file that has to be parsed
     * @param pErrors a StringBuffer intended to store errors
     * @return an Object resulting form the parsing of pStream with pConfigDigester
     */
    protected Object executeParsing( Digester pConfigDigester, File pFile, StringBuffer pErrors )
    {
        /* Object that will be returned */
        Object obj = null;

        /* If the passed in pStream parameter is not null */
        if ( null != pFile )
        {
            try
            {
                /* Executes the parsing of the result file */
                obj = pConfigDigester.parse( pFile );
            }
            catch ( IOException e )
            {
                /* Uses the handleException method of the superClass to append error to the StringBuffer and log it */
                handleException( e, pErrors );
            }
            catch ( SAXException e )
            {
                /* Uses the handleException method of the superClass to append error to the StringBuffer and log it */
                handleException( e, pErrors );
            }
        }
        /* Returning the parsing result */
        return obj;
    }
}
