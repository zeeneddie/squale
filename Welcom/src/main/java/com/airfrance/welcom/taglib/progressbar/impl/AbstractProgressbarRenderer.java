/*
 * Créé le 16 oct. 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.progressbar.impl;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.progressbar.IProgressbarRenderer;

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
