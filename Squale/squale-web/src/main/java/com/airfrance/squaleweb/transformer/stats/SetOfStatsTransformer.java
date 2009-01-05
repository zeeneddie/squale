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
package com.airfrance.squaleweb.transformer.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.airfrance.squalecommon.datatransfertobject.stats.SetOfStatsDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.AuditsStatsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.FactorsStatsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.SetOfStatsForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class SetOfStatsTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        SetOfStatsForm form = new SetOfStatsForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        SetOfStatsDTO dto = (SetOfStatsDTO) pObject[0];
        Locale locale = (Locale) pObject[1];
        SetOfStatsForm form = (SetOfStatsForm) pForm;
        // Récupère les 3 listes contenant les objets bas niveau à transformer
        List profilDto = dto.getListOfProfilsStatsDTO();
        List siteDto = dto.getListOfSiteStatsDTO();
        List applicationsStatsDto = dto.getListOfApplicationsStatsDTO();
        // Les 3 listes qui contiendront les objets transformés
        List profilFormList = new ArrayList( 0 );
        List siteFormList = new ArrayList( 0 );
        List applicationFormList = new ArrayList( 0 );
        for ( int i = 0; i < profilDto.size(); i++ )
        {
            // Transforme et ajoute chaque objet profil
            profilFormList.add( WTransformerFactory.objToForm( ProfileStatsTransformer.class, profilDto.get( i ) ) );
        }
        for ( int i = 0; i < siteDto.size(); i++ )
        {
            // Transforme et ajoute chaque
            siteFormList.add( WTransformerFactory.objToForm( SiteStatsTransformer.class, siteDto.get( i ) ) );
        }
        for ( int i = 0; i < applicationsStatsDto.size(); i++ )
        {
            // Transforme et ajoute chaque statistique
            applicationFormList.add( WTransformerFactory.objToForm( ApplicationStatsTransformer.class, new Object[] {
                applicationsStatsDto.get( i ), locale } ) );
        }
        form.setListOfProfilsStatsForm( profilFormList );
        form.setListOfSiteStatsForm( siteFormList );
        form.setListOfApplicationsStatsForm( applicationFormList );
        form.setAuditsStatsForm( (AuditsStatsForm) WTransformerFactory.objToForm( AuditsStatsTransformer.class,
                                                                                  dto.getAuditsStatsDTO() ) );
        form.setFactorsStatsForm( (FactorsStatsForm) WTransformerFactory.objToForm( FactorsStatsTransformer.class,
                                                                                    dto.getFactorsStatsDTO() ) );
    }

    /**
     * @deprecated n'a pas de sens
     * @param pForm le formulaire
     * @throws WTransformerException si un pb apparait.
     * @return rien mais lance systématiquement une exception
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        throw new WTransformerException( "deprecated" );
    }

    /**
     * @deprecated n'a pas de sens
     * @param pForm le formulaire
     * @param pTab les paramètres
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pTab )
        throws WTransformerException
    {
        throw new WTransformerException( "deprecated" );
    }

}
