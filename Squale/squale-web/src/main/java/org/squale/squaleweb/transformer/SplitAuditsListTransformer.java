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
package org.squale.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squaleweb.applicationlayer.formbean.component.AuditForm;
import org.squale.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import org.squale.squaleweb.comparator.AuditComparator;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforme les audits des applications publiques ou non
 */
public class SplitAuditsListTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc} - les listes des audits
     * @return le formulaire transformé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        SplitAuditsListForm form = new SplitAuditsListForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc} - les listes des audits
     * @param pForm {@inheritDoc} - le splitAuditsListForm
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        SplitAuditsListForm form = (SplitAuditsListForm) pForm;
        // Les audits publiques
        List publicAudits = (List) pObject[0];
        // On transforme en formulaire et on affecte
        form.setPublicAudits( transformAuditsList( publicAudits ) );
        // Les audits des applications appartenant à l'utilisateur
        List audits = (List) pObject[1];
        // On transforme en formulaire et on affecte
        form.setAudits( transformAuditsList( audits ) );

    }

    /**
     * @param pAudits les auditDTO à transformer en formulaire
     * @return la collection transformée
     * @throws WTransformerException si erreur
     */
    private List transformAuditsList( List pAudits )
        throws WTransformerException
    {
        int size = pAudits.size();
        List listAuditForm = new ArrayList( size );
        for ( int i = 0; i < size; i++ )
        {
            AuditDTO dto = (AuditDTO) pAudits.get( i );
            AuditForm auditForm = (AuditForm) WTransformerFactory.objToForm( AuditTransformer.class, dto );
            auditForm.setApplicationName( dto.getApplicationName() );
            listAuditForm.add( auditForm );
        }
        Collections.sort( listAuditForm, new AuditComparator() );
        Collections.reverse( listAuditForm );
        return listAuditForm;
    }

    /**
     * {@inheritDoc}
     * 
     * @param form {@inheritDoc}
     * @return le tableau des objets transformés
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "Not Yet Implemented" );
    }

    /**
     * {@inheritDoc}
     * 
     * @param form {@inheritDoc}
     * @param object {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "Not Yet Implemented" );
    }
}
