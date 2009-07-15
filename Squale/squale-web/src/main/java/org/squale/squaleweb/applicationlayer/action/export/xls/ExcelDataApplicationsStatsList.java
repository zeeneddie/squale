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

import java.util.List;
import java.util.Locale;

import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.util.MessageResources;

import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.outils.excel.ExcelData;
import com.airfrance.welcom.outils.excel.ExcelGenerateur;
import com.airfrance.welcom.outils.excel.ExcelGenerateurException;
import com.airfrance.welcom.outils.excel.ExcelTable;
import com.airfrance.welcom.outils.excel.ExcelWrapper;

/**
 * Gestion de l'export des statistiques par application
 */
public class ExcelDataApplicationsStatsList
    extends ExcelData
{

    /** La liste des formulaires représentant les statistiques niveau application */
    private List mApplicationsStatsForm;

    /** Matricule administrateur */
    private String mMatricule;

    /**
     * Constructeur
     * 
     * @param pLocale : c'est la locale de l'application pour l'internationnalisation
     * @param pMessages :un MessageResources initialisé avec le fichier ressource contenant les labels à utiliser dans
     *            le fichier excel.
     * @param pApplicationsStatsForm la liste des statistiques
     * @param pMatricule le matricule administrateur
     */
    public ExcelDataApplicationsStatsList( final Locale pLocale, final MessageResources pMessages,
                                           List pApplicationsStatsForm, String pMatricule )
    {
        super( pLocale, pMessages );
        mApplicationsStatsForm = pApplicationsStatsForm;
        mMatricule = pMatricule;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.welcom.outils.excel.ExcelData#fill(com.airfrance.welcom.outils.excel.ExcelGenerateur)
     */
    public void fill( ExcelGenerateur xlGenerateur )
        throws ExcelGenerateurException
    {
        ExcelWrapper monWrapper = (ExcelWrapper) xlGenerateur;
        WritableWorkbook workbook = monWrapper.getWorkbook();
        ExcelTable et = new ExcelTable( messages, locale );
        et.setTable( mApplicationsStatsForm );
        /* les en-têtes du tableau */
        // On récupère le format pour les dates
        // le nom de l'application
        et.addHeader( "stats.application.col", "applicationName" );
        // Application validée ou non
        et.addHeader( "export.excel.applications_stats.validated", "validatedApplicationStr" );
        // l'état
        et.addHeader( "stats.state.col", "lastAuditIsTerminated" );
        // Active ou non
        et.addHeader( "stats.active.col", "activatedApplication" );
        // Archived or not
        et.addHeader( "stats.archived.col", "archivedApplication" );
        // Date dernier audit terminé
        et.addHeader( "stats.last_audit_date.col", "lastTerminatedAuditDate" );
        // Duré dernier audit
        et.addHeader( "stats.last_audit_duration.col", "lastAuditDuration" );
        // Nombre d'audits durant les n derniers jours
        et.addHeader( "stats.nb_audits_last_days.col", "nbAudits" );
        // Nombre d'audits terminés
        et.addHeader( "stats.nb_terminated_audits.col", "nbTerminatedAudits" );
        // Nombre d'audits partiel ou en échec
        et.addHeader( "stats.nb_failed_audits.col", "nbPartialOrFaliedAudits" );
        // Date dernier audit en échec
        et.addHeader( "stats.last_failed_audit.col", "lastFailedAuditDate" );
        // Date premier audit réussi
        et.addHeader( "stats.first_terminated_audit.col", "firstTerminatedAuditDate" );
        // Fréquence de purge
        et.addHeader( "stats.purge.col", "purgeFrequency" );
        // Nom serveur
        et.addHeader( "stats.server.col", "serverName" );
        // Date dernier accès non administrateur
        et.addHeader( "stats.last_access.col", "lastAccess" );

        try
        {
            et.writeTable( workbook );
            // On modifie l'en-tête et pied-de-page
            WritableSheet sheet = workbook.getSheet( 0 );
            SheetSettings settings = sheet.getSettings();
            String title = (String) WebMessages.getString( locale, "export.excel.applications_stats.title" );
            HeaderFooter head = new HeaderFooter();
            head.getCentre().append( title );
            settings.setHeader( head );
            String footerCenter = WebMessages.getString( locale, "stats.page.title" );
            HeaderFooter footer = SqualeExportExcelUtils.getFooter( locale, "SQUALE", footerCenter, "", mMatricule );
            settings.setFooter( footer );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
