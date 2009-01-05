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
 * Créé le 16 oct. 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.progressbar.impl;


/**
 * @author 6361371 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProgressbarRendererV2001
    extends AbstractProgressbarRenderer
{

    public String drawRealProgressBar( String id )
    {
        StringBuffer result = new StringBuffer();
        result.append( "<span style=\"float:left;width:100%;background-color:#FFFFFF;border:1px solid #888;height:10px\" >" );
        result.append( "	<span style=\"float:left;width:0%;background-color:#383D90;height:10px\" id=\"" + id
            + "_all_td1\"></span>" );
        result.append( "</span>" );
        return result.toString();
    }
}
