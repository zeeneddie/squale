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
package org.squale.welcom.taglib.progressbar.impl;

import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.progressbar.IProgressbarRenderer;

/**
 * @author 6361371 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public abstract class AbstractProgressbarRenderer
    implements IProgressbarRenderer
{
    public String drawAnimatedProgressBar()
    {
        StringBuffer result = new StringBuffer();
        result.append( "<img id='wImgProgressBar' src=\"" );
        result.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_IMG ) );
        result.append( "img/pb_ini.gif\"" );
        result.append( ">" );
        return result.toString();
    }
}
