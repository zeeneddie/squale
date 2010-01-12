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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.component.ProfileForm;
import org.squale.squaleweb.applicationlayer.formbean.component.UserForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'informations sur un LogonBean
 */
public class LogonBeanTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] arg0 )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] arg0, WActionForm arg1 )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm arg0 )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
    throws WTransformerException
	{
	    // Extraction des paramètres
	    UserForm pUser = (UserForm) pForm;
	    LogonBean bean = (LogonBean) pObject[0];
	    boolean pIsAdmin = ( (Boolean) pObject[1] ).booleanValue();
	    bean.setId( pUser.getId() );
	    bean.setName( pUser.getName() );
	    bean.setEmail( pUser.getEmail() );
	    bean.setMatricule( pUser.getMatricule() );
	    bean.setUnsubscribed(pUser.getUnsubscribed());
	    // Traitement des profils
	    Iterator profilesEntries = pUser.getProfiles().entrySet().iterator();
	    HashMap idProfiles = new HashMap();
	    HashMap idProfilesPerApplication = new HashMap();
	    while ( profilesEntries.hasNext() )
	    {
	        Map.Entry entry = (Entry) profilesEntries.next();
	        // On stocke l'id ainsi que le nom du profile dans la map
	        idProfiles.put( new Long( ( (ApplicationForm) entry.getKey() ).getId() ),
	                        ( (ProfileForm) entry.getValue() ).getName() );
	        idProfilesPerApplication.put((ApplicationForm) entry.getKey(),
	                ( (ProfileForm) entry.getValue() ).getName() );
	    }
	    bean.setProfiles( idProfiles ); // Contient la liste des applications et les profils associés
	    bean.setProfilesFullApp(idProfilesPerApplication);
	    bean.setApplicationsList( pUser.getApplicationsList() );
	    bean.setInCreationList(pUser.getOnlyAdminApplicationsList());
	    // recupere la liste des applications avec droit administrateurs
	    ArrayList mAdminList = new ArrayList();
	    // Les applications validées ou en cours de validation pour les gestionnaires
	    addManagerProfile( pUser, pIsAdmin, mAdminList, pUser.getApplicationsList() );
	    // Les applications en cours de validation
	    bean.setAdminList( mAdminList );
	    // Liste des applications seulement consultables
	    // cad les applications qui sont accessibles mais pas administrables
	    ArrayList readOnly = new ArrayList( pUser.getApplicationsList() );
	    readOnly.removeAll( bean.getAdminList() );
	    bean.setReadOnlyList( readOnly );
	    bean.setDefaultAccess( pUser.getDefaultAccess().getName() );
	    bean.setAdmin( pIsAdmin );
	    bean.setCurrentAccess( pUser.getDefaultAccess().getName() );
	    bean.setPublicList( pUser.getPublicApplicationsList() );
	}

    /**
     * Ajoute à la liste des applications avec droit administrateurs les applications ayant ce droit se trouvant
     * <code>dans pApplis</code> ou toutes si il s'agit d'un administrateur.
     * 
     * @param pUser le bean utilisateur
     * @param pIsAdmin pour indiquer si il s'agit d'un administrateur ou pas
     * @param mAdminList la liste des applications administrables
     * @param pApplis la liste des applications à ajouter
     */
    private void addManagerProfile( UserForm pUser, boolean pIsAdmin, ArrayList mAdminList, List pApplis )
    {
        Iterator it = pApplis.listIterator();
        while ( it.hasNext() )
        {
            ApplicationForm application = (ApplicationForm) it.next();
            ProfileForm profile = (ProfileForm) pUser.getProfiles().get( application );
            // On positionne la liste des applications avec un privilège admin
            if ( profile.getName().equals( ProfileBO.MANAGER_PROFILE_NAME ) || pIsAdmin )
            {
                mAdminList.add( application );
            }
        }

    }

}
