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

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.struts.util.MessageResources;

import com.airfrance.squaleweb.applicationlayer.formbean.results.MarkForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.outils.excel.ExcelData;
import com.airfrance.welcom.outils.excel.ExcelGenerateur;
import com.airfrance.welcom.outils.excel.ExcelGenerateurException;
import com.airfrance.welcom.outils.excel.ExcelTable;
import com.airfrance.welcom.outils.excel.ExcelWrapper;

/**
 * Gestion de l'export des composants par note de pratiques
 */
public class ExcelDataMarkList
    extends ExcelData
{

    /** Le formulaire */
    private MarkForm mMarkForm;

    /** Le nom de l'utilisateur */
    private String mMatricule;

    /**
     * Constructeur
     * 
     * @param pLocale : c'est la locale de l'application pour l'internationnalisation
     * @param pMessages :un MessageResources initialisé avec le fichier ressource contenant les labels à utiliser dans
     *            le fichier excel.
     * @param pMarkForm le formulaire
     * @param pMatricule le matricule de l'utilsateur
     */
    public ExcelDataMarkList( final Locale pLocale, final MessageResources pMessages, MarkForm pMarkForm,
                              String pMatricule )
    {
        super( pLocale, pMessages );
        mMarkForm = pMarkForm;
        mMatricule = pMatricule;
    }

    /**
     * @see com.airfrance.welcom.outils.excel.ExcelData#fill(com.airfrance.welcom.outils.excel.ExcelGenerateur)
     *      {@inheritDoc}
     */
    public void fill( ExcelGenerateur xlGenerateur )
        throws ExcelGenerateurException
    {
        ExcelWrapper monWrapper = (ExcelWrapper) xlGenerateur;
        WritableWorkbook workbook = monWrapper.getWorkbook();
        Collection components = mMarkForm.getComponents();
        if ( null != components )
        {
            ExcelTable et = new ExcelTable( messages, locale );
            et.setTable( components );
            /* les en-têtes du tableau */
            // le nom du composant
            et.addHeader( "component.name", "fullName" );
            // la note
            et.addHeader( "project.result.practice.value", "metrics[0]" );
            // les métriques
            List metricNames = mMarkForm.getTreNames();
            int cpt = 0;
            for ( int i = 0; i < metricNames.size(); i++ )
            {
                cpt = i + 1;
                et.addHeader( (String) metricNames.get( i ), "metrics[" + cpt + "]" );
            }
            try
            {
                et.writeTable( workbook );
                // On modifie l'en-tête et pied-de-page
                WritableSheet sheet = workbook.getSheet( 0 );
                SheetSettings settings = sheet.getSettings();
                String title =
                    (String) WebMessages.getString( locale, "export.pdf.project.results.title",
                                                    new String[] { mMarkForm.getProjectName() } );
                HeaderFooter head = new HeaderFooter();
                head.getCentre().append( title );
                settings.setHeader( head );
                String footerLeft =
                    (String) WebMessages.getString( locale, "description.name.audit",
                                                    new String[] { mMarkForm.getAuditDate() } );
                footerLeft += "\n";
                footerLeft +=
                    (String) WebMessages.getString( locale, "evolution.components.previous_audit",
                                                    new String[] { mMarkForm.getPreviousAuditDate() } );
                HeaderFooter footer =
                    SqualeExportExcelUtils.getFooter( locale, mMarkForm.getApplicationName(),
                                                      mMarkForm.getProjectName(), footerLeft, mMatricule );
                settings.setFooter( footer );
            }
            catch ( WriteException e )
            {
                e.printStackTrace();
            }
        }
    }

}
