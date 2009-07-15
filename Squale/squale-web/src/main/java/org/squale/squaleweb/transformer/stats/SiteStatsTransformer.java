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

import com.airfrance.squalecommon.datatransfertobject.stats.SiteStatsDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurForm;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.SiteStatsForm;
import com.airfrance.squaleweb.transformer.ServeurTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class SiteStatsTransformer
    implements WITransformer
{

    /**
     * @param pObject le tableau d'objet contenant l'objet à transformer
     * @return le form résultat de la transformation
     * @throws WTransformerException en cas d'échec
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        SiteStatsForm form = new SiteStatsForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject le tableau d'objet contenant l'objet à transformer
     * @param pForm le form résultat
     * @throws WTransformerException en cas d'échec
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        SiteStatsForm form = (SiteStatsForm) pForm;
        SiteStatsDTO dto = (SiteStatsDTO) pObject[0];
        form.setLoc( dto.getLoc() );
        form.setNbProjects( dto.getNbProjects() );
        form.setServeurForm( (ServeurForm) WTransformerFactory.objToForm( ServeurTransformer.class, dto.getServeurDTO() ) );

        form.setNbAppliToValidate( dto.getNbAppliToValidate() );
        form.setNbAppliWithAuditsSuccessful( dto.getNbAppliWithAuditsSuccessful() );
        form.setNbAppliWithoutSuccessfulAudits( dto.getNbAppliWithoutSuccessfulAudits() );
        form.setNbValidatedApplis( dto.getNbValidatedApplis() );
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
