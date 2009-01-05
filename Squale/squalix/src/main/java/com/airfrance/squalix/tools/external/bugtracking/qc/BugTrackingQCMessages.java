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
package com.airfrance.squalix.tools.external.bugtracking.qc;

import com.airfrance.squalecommon.util.messages.BaseMessages;

public class BugTrackingQCMessages
    extends BaseMessages
{
    /** Instance de BugTrackingMessages */
    static private BugTrackingQCMessages mMessages = new BugTrackingQCMessages();

    /**
     * Constructeur par défaut
     */
    private BugTrackingQCMessages()
    {
        super( "com.airfrance.squalix.tools.external.bugtracking.bugtrackingqc" );
    }

    /**
     * Retourne la chaine de caractère identifiée par la clé
     * 
     * @param pKey le nom de la clé
     * @return la chaine de caractère
     */
    public static String getString( String pKey )
    {
        return mMessages.getBundleString( pKey );
    }

}
