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
package com.airfrance.squaleweb.resources;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.messages.MessageProvider;
import com.airfrance.squaleweb.transformer.message.MessagesDTOTransformer;

/**
 * Proxy des ressources struts Les messages affichés sous struts proviennent de deux sources : les messages statiqques
 * définis dans un fichier de propriétés les messages dynamiques définis dans la base de données
 */
public class MessageResourcesProxy
    extends MessageResources
    implements Observer
{
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( MessageResourcesProxy.class );

    /** Ressources dans le fichier de configuration */
    private MessageResources mMessageResources;

    /**
     * Constructeur
     * 
     * @param factory factory
     * @param config configuration
     * @param pMessageResources messages struts
     */
    public MessageResourcesProxy( MessageResourcesFactory factory, String config, MessageResources pMessageResources )
    {
        super( factory, config );
        mMessageResources = pMessageResources;
        IApplicationComponent ac;
        DataBaseMessages.registerObserver( this );
        try
        {
            ac = AccessDelegateHelper.getInstance( "Messages" );
            MessageProvider messages = MessagesDTOTransformer.transform( ac.execute( "getMessages" ) );
            DataBaseMessages.update( messages );
        }
        catch ( JrafEnterpriseException e )
        {
            log.error( e, e );
        }
    }

    /**
     * @see org.apache.struts.util.MessageResources#getMessage(java.util.Locale, java.lang.String) {@inheritDoc}
     */
    public String getMessage( Locale locale, String key )
    {
        String result = mMessageResources.getMessage( locale, key );
        // Récupération du message dans la base de données
        if ( result == null )
        {
            result = DataBaseMessages.getMessage( locale, key );
        }
        return result;
    }

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object) {@inheritDoc}
     */
    public void update( Observable o, Object arg )
    {
        // On reinitialise la map interne des formats gérée par struts
        // Cette map contient un ensemble de MessageFormat
        synchronized ( formats )
        {
            formats.clear();
        }
    }
}
