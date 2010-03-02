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
package org.squale.squaleweb.transformer.sharedrepository;

import org.squale.squalecommon.datatransfertobject.sharedrepository.SharedRepoStatsDTO;
import org.squale.squaleweb.applicationlayer.formbean.results.SharedRepoStatForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformer object <=> form for the sharedRepositoryStats
 */
public class SharedReposStatTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {

    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        SharedRepoStatForm form = new SharedRepoStatForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        SharedRepoStatForm currentform = (SharedRepoStatForm) form;
        SharedRepoStatsDTO dto = (SharedRepoStatsDTO) object[0];
        currentform.setReferenceMean( Float.toString( dto.getMean() ) );
        currentform.setReferenceMax( Float.toString( dto.getMax() ) );
        currentform.setReferenceMin( Float.toString( dto.getMin() ) );
        currentform.setReferenceDeviation( Float.toString( dto.getDeviation() ) );
    }
}
