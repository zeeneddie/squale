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
 * Créé le 15 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColsTei
    extends TagExtraInfo
{

    /**
     * Ajoute le info pour la declaration de variables dans la page
     * 
     * @param data le tag
     * @return l'info sur les variables declarés
     */
    public VariableInfo[] getVariableInfo( final TagData data )
    {
        final String type = "java.lang.Object";
        final VariableInfo typeInfo = new VariableInfo( data.getAttributeString( "id" ), type, true, 0 );
        final String indexId = data.getAttributeString( "idIndex" );
        VariableInfo indexIdInfo = null;

        if ( indexId != null )
        {
            indexIdInfo = new VariableInfo( indexId, "java.lang.Integer", true, 0 );
        }

        if ( indexIdInfo == null )
        {
            return ( new VariableInfo[] { typeInfo } );
        }
        else
        {
            return ( new VariableInfo[] { typeInfo, indexIdInfo } );
        }
    }
}