package com.airfrance.squalix.tools.cpd;

/**
 * Fabrique de traitement Cpd
 */
public class CpdProcessingFactory
{

    /**
     * Obtention du traitement Cpd correspondant à un langage
     * 
     * @param pLanguage langage
     * @return traitement correspondant
     * @throws CpdFactoryException si le langage est inconnu
     */
    public AbstractCpdProcessing createCpdProcessing( String pLanguage )
        throws CpdFactoryException
    {
        AbstractCpdProcessing result;
        if ( pLanguage.equals( "java" ) )
        {
            result = new JavaCpdProcessing();
        }
        else if ( pLanguage.equals( "cpp" ) )
        {
            result = new CppCpdProcessing();
        }
        else if ( pLanguage.equals( "jsp" ) )
        {
            result = new JspCpdProcessing();
        }
        else
        {
            throw new CpdFactoryException( CpdMessages.getString( "exception.unknown.language", pLanguage ) );
        }
        return result;
    }
}
