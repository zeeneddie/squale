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
 * Créé le 6 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.pdf.jasperreports.PDFJasperReportsWrapper;

/**
 * PDFDataEscaleListe
 */
public class PDFDataJasperReports
    extends PDFData
{

    /** la liste des beans */
    private Collection collection;

    /** le nom du template */
    private String templateName;

    /** Liste des parametres */
    private Map parameters;

    /** Active la bufferisation sur le disque */
    private boolean virtualize;

    /**
     * Constructeur
     * 
     * @param locale la locale
     * @param mess le messageRessource
     * @param pCollection la liste
     * @param pTemplateName le nom du template
     */
    public PDFDataJasperReports( final Locale locale, final MessageResources mess, final Collection pCollection,
                                 final String pTemplateName )
    {
        this( locale, mess, pCollection, pTemplateName, false, null );
    }

    /**
     * Constructeur
     * 
     * @param locale la locale
     * @param mess le messageRessource
     * @param pCollection la liste
     * @param pTemplateName le nom du template
     * @param pVirtualize active le bufferisation sur le disque
     */
    public PDFDataJasperReports( final Locale locale, final MessageResources mess, final Collection pCollection,
                                 final String pTemplateName, final boolean pVirtualize )
    {
        this( locale, mess, pCollection, pTemplateName, pVirtualize, null );
    }

    /**
     * Constructeur
     * 
     * @param locale la locale
     * @param mess le messageRessource
     * @param pCollection la liste
     * @param pTemplateName le nom du template
     * @param pParameters Paramtres du report
     * @param pVirtualize active le bufferisation sur le disque
     */
    public PDFDataJasperReports( final Locale locale, final MessageResources mess, final Collection pCollection,
                                 final String pTemplateName, final boolean pVirtualize, final Map pParameters )
    {
        super( locale, mess );
        collection = pCollection;
        templateName = pTemplateName;
        parameters = pParameters;
        virtualize = pVirtualize;
    }

    /**
     * @see com.airfrance.welcom.outils.pdf.PDFData#getTemplateName()
     */
    public String getTemplateName()
    {
        return templateName;
    }

    /**
     * @param pdfGenerateur le pdfGenerateur
     * @throws PDFGenerateurException exception pouvant etre levee
     */
    public void fill( final PDFGenerateur pdfGenerateur )
        throws PDFGenerateurException
    {
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource( collection );
        if ( pdfGenerateur instanceof PDFJasperReportsWrapper )
        {
            ( (PDFJasperReportsWrapper) pdfGenerateur ).setDataSource( dataSource );
        }
    }

    /**
     * @return les parametres du report
     */
    public Map getParameters()
    {
        return parameters;
    }

    /**
     * @return si on active la bufferisation sur le disque
     */
    public boolean isVirtualize()
    {
        return virtualize;
    }

}
