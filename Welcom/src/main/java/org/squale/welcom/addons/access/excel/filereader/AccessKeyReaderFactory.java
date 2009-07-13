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
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.access.excel.filereader;

import java.io.IOException;
import java.net.URL;

import org.squale.welcom.outils.Util;

import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AccessKeyReaderFactory
{

    /**
     * Initialise un reader en fonction de la version du fichier Excel
     * 
     * @param url : url du fichier Excel
     * @return : Le reader
     * @throws AccessKeyReaderException Probleme sur la lecture
     */
    public static IAccessKeyReader read( final URL url )
        throws AccessKeyReaderException
    {
        try
        {

            final Workbook workbook = Workbook.getWorkbook( url.openStream() );
            final String version = workbook.findCellByName( "VERSION" ).getContents();

            if ( Util.isEquals( version, "Welcom AccesKey 1.0" ) )
            {
                return new AccessKeyReader10( workbook );
            }
            else
            {
                throw new AccessKeyReaderException( "Version ou type de fichier de droit d'accés non supporté" );
            }

        }
        catch ( final BiffException e )
        {
            throw new AccessKeyReaderException( e.getMessage() );
        }
        catch ( final IOException e )
        {
            throw new AccessKeyReaderException( e.getMessage() );
        }

    }

}
