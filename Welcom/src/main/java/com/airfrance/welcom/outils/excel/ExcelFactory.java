/*
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

/**
 * @author Rémy Bouquet Factory pour export Excel
 */
public class ExcelFactory
{
    /** Constante */
    private final static String CONTENT_TYPE = "application/vnd.ms-excel";

    /** logger */
    private static Log log = LogFactory.getLog( ExcelFactory.class );

    /**
     * Classe de factory pour générer des fichiers Excel
     * 
     * @param xlGenerateur : le gnérateur Excel associé à la génération de ce fichier
     * @param xlData : les données Excel associées à la génération de ce fichier
     * @throws ExcelGenerateurException : Level une erreur sur la production du fichier
     * @deprecated
     */
    public static void generationExcel( final ExcelGenerateur xlGenerateur, final ExcelData xlData )
        throws ExcelGenerateurException
    {
        // initialisation du generateur
        try
        {
            xlGenerateur.init();

            // Remplissage du workbook
            xlData.fill( xlGenerateur );

            // Generation du fichier excel
            xlGenerateur.writeExcel();

        }
        catch ( final IOException e )
        {
            log.error( e, e );
            throw new ExcelGenerateurException( e.getMessage(), e );
        }

    }

    /**
     * @since welcom 2.3
     * @param exceldata les datas
     * @param os outputStream l'outputstream
     * @throws ExcelGenerateurException exception pouvant etre levee
     */
    public static void generateExcel( final ExcelData exceldata, final OutputStream os )
        throws ExcelGenerateurException
    {
        generateExcel( exceldata, os, ExcelEngine.JEXCEL );
    }

    /*******************************************************************************************************************
     * @param exceldata les datas
     * @param response la response
     * @param attachementFileName le nom par defaut si on sauvegarde le fichier
     * @throws ExcelGenerateurException exception pouvant etre levee
     */
    public static void generateExcelToHTTPResponse( final ExcelData exceldata, final HttpServletResponse response,
                                                    final String attachementFileName )
        throws ExcelGenerateurException
    {
        generateExcelToHTTPResponse( exceldata, response, attachementFileName, ExcelEngine.JEXCEL );
    }

    /**
     * @since welcom 2.3
     * @param exceldata le exceldata
     * @param os l'outputstream
     * @param engine le excelengine
     * @throws ExcelGenerateurException exception pouvant etre levee
     */
    public static void generateExcel( final ExcelData exceldata, final OutputStream os, final ExcelEngine engine )
        throws ExcelGenerateurException
    {
        final ExcelGenerateur generateur = getGenerateur( engine );
        // initialisation du generateur
        generateur.open( os );
        try
        {
            generateur.init();

            // Remplissage du workbook
            exceldata.fill( generateur );

            // Generation du fichier excel
            generateur.writeExcel();

        }
        catch ( final IOException e )
        {
            log.error( e, e );
            throw new ExcelGenerateurException( e.getMessage(), e );
        }
    }

    /**
     * @param exceldata les datas
     * @param response la response
     * @param attachementFileName le nom du fichier
     * @param engine l'engine
     * @throws ExcelGenerateurException exception pouvant etre levee
     */
    public static void generateExcelToHTTPResponse( final ExcelData exceldata, final HttpServletResponse response,
                                                    final String attachementFileName, final ExcelEngine engine )
        throws ExcelGenerateurException
    {
        try
        {
            if ( response.isCommitted() )
            {
                throw new ExcelGenerateurException(
                                                    "Le header a deja été envoyé ... impossible d'ajouter le content-disposition" );
            }
            final OutputStream os = response.getOutputStream();
            response.setContentType( CONTENT_TYPE );
            if ( !GenericValidator.isBlankOrNull( attachementFileName ) )
            {
                response.setHeader( "Content-Disposition", "attachment;filename=" + attachementFileName + ";" );
            }
            generateExcel( exceldata, os, engine );
        }
        catch ( final IOException e )
        {
            throw new ExcelGenerateurException( e.getMessage(), e );
        }
    }

    /**
     * @param engine l'engine
     * @return le generateur
     */
    private static ExcelGenerateur getGenerateur( final ExcelEngine engine )
    {
        if ( engine == ExcelEngine.JEXCEL )
        {
            return new ExcelWrapper();
        }
        return null;
    }

}