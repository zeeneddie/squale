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
package com.airfrance.squaleweb.applicationlayer.action.export.xls;

import java.util.Calendar;
import java.util.Locale;

import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

import jxl.HeaderFooter;

/**
 * Utilitaire pour les exports XLS
 */
public class SqualeExportExcelUtils
{

    /**
     * Pied-de-page générique aux documents XLS : footerLeft appli/project page/total crée le "date" par "matricule"
     * 
     * 
     * @param locale la locale
     * @param appli le nom de l'application
     * @param project le nom du projet
     * @param footerLeft le pied-de-page gauche
     * @param matricule le matricule utilisateur
     * @return le pied-de-page d'un document XLS
     */
    public static HeaderFooter getFooter( Locale locale, String appli, String project, String footerLeft,
                                          String matricule )
    {
        HeaderFooter footer = new HeaderFooter();
        // La date d'aujourd'hui
        String today = SqualeWebActionUtils.getFormattedDate( Calendar.getInstance().getTime(), locale );
        String footerLeft2 =
            (String) WebMessages.getString( locale, "export.pdf.page.footer", new String[] { today, matricule } );
        // A gauche
        footer.getLeft().append( footerLeft );
        footer.getLeft().append( "\n" );
        footer.getLeft().append( footerLeft2 );
        // A droite
        footer.getRight().appendPageNumber();
        footer.getRight().append( "/" );
        footer.getRight().appendTotalPages();
        // Au centre
        footer.getCentre().append( appli );
        footer.getCentre().append( "/" );
        footer.getCentre().append( project );
        footer.getCentre().append( "\n" );
        return footer;
    }

}
