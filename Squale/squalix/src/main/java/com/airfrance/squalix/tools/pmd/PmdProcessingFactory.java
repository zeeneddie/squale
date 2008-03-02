package com.airfrance.squalix.tools.pmd;

/**
 * Fabrique de traitement Pmd
 */
public class PmdProcessingFactory
{

    /**
     * Obtention du traitement Pmd correspondant à un langage
     * 
     * @param pLanguage langage
     * @return traitement correspondant
     * @throws PmdFactoryException si le langage est inconnu
     */
    public AbstractPmdProcessing createPmdProcessing( String pLanguage )
        throws PmdFactoryException
    {
        AbstractPmdProcessing result;
        if ( pLanguage.equals( "java" ) )
        {
            result = new JavaPmdProcessing();
        }
        else if ( pLanguage.equals( "jsp" ) )
        {
            result = new JspPmdProcessing();
        }
        else
        {
            throw new PmdFactoryException( PmdMessages.getString( "exception.unknown.language", pLanguage ) );
        }
        return result;
    }
}
