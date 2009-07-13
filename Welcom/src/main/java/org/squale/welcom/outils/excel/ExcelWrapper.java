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
/*
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.outils.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;

/**
 * @author Rémy BOuquet
 */
public class ExcelWrapper
    implements ExcelGenerateur
{

    /**
     * Document Excel
     */
    protected WritableWorkbook workbook = null;

    /**
     * Flux ou l'on ecrit le resultat de la generation excel
     */
    protected OutputStream os = null;

    /**
     * @param fluxSortant Stream pour ecrire le flux
     */
    public ExcelWrapper( final OutputStream fluxSortant )
    {
        os = fluxSortant;
    }

    /**
     * Constreur vide
     */
    public ExcelWrapper()
    {
    }

    /**
     * Initilisation du classeur Excel
     * 
     * @throws IOException Erreur a la creation du classeur
     */
    public void init()
        throws IOException
    {
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale( Locale.FRENCH );
        workbook = Workbook.createWorkbook( os, workbookSettings );
    }

    /**
     * @see org.squale.welcom.outils.excel.ExcelGenerateur#writeExcel()
     */
    public void writeExcel()
        throws ExcelGenerateurException
    {
        if ( workbook == null )
        {
            throw new ExcelGenerateurException( "Le workbook est vide ou n'a pas été initialisé." );
        }

        if ( os == null )
        {
            throw new ExcelGenerateurException( "Le flux de sortie n'a pas été initialisé" );
        }

        try
        {
            workbook.write();
            workbook.close();
        }
        catch ( final Exception e )
        {
            throw new ExcelGenerateurException( "Une erreur s'est produite lors de l'écriture de fichier excel", e );
        }
    }

    /**
     * Setter du flux de sortie.
     * 
     * @param fluxSortant : le flux de sortie.
     */
    public void setOutputStream( final OutputStream fluxSortant )
    {
        os = fluxSortant;
    }

    /**
     * @return le classeur Excel
     */
    public WritableWorkbook getWorkbook()
    {
        return workbook;
    }

    /**
     * @see org.squale.welcom.outils.excel.ExcelGenerateur#open(java.io.OutputStream)
     */
    public void open( final OutputStream pOs )
        throws ExcelGenerateurException
    {
        setOutputStream( pOs );
    }

}