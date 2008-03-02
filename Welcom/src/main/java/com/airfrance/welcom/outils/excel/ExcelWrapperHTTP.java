/*
 * Créé le 2 nov. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 * @deprecated
 */
public class ExcelWrapperHTTP
    extends ExcelWrapper
{
    /** logger */
    private static Log log = LogFactory.getLog( ExcelWrapperHTTP.class );

    /** Constante */
    private final static String CONTENT_TYPE = "application/vnd.ms-excel";

    /** Constante */
    private final static String CONTENT_DISPOSITION = "attachment;filename=";

    /** le fichier */
    private String fileName = "classeur.xls";

    /** la reponse */
    private HttpServletResponse response = null;

    /**
     * Constructeur
     * 
     * @param pResponse la servletResponse
     * @throws IOException exception pouvant etre levee
     */
    public ExcelWrapperHTTP( final HttpServletResponse pResponse )
        throws IOException
    {
        super( pResponse.getOutputStream() );
        response = pResponse;
    }

    /**
     * @see com.airfrance.welcom.outils.excel.ExcelGenerateur#writeExcel()
     */
    public void writeExcel()
        throws ExcelGenerateurException
    {
        // Initilise les valeur du Header http
        response.setContentType( CONTENT_TYPE );
        response.setHeader( "Content-Disposition", CONTENT_DISPOSITION + fileName + ";" );

        // Ecrit le resultat dans le response
        try
        {
            super.writeExcel();
            os.flush();
            os.close();
        }
        catch ( final Exception e )
        {
            log.error( e, e );
            throw new ExcelGenerateurException( e.getMessage() );
        }
    }

    /**
     * Spécifie le nom du fichier par default
     * 
     * @param pFileName le filename
     */
    public void setFileName( final String pFileName )
    {
        fileName = pFileName;
    }
}