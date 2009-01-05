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
 * Créé le 7 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.rsh.Impl;

import java.io.IOException;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.bsd.RCommandClient;

import com.airfrance.welcom.outils.rsh.RshClient;

/**
 * Class effectuant l'execution des commandes en rsh
 * 
 * @author M327837
 */
public class JavaRshClient
    extends RshClient
{

    /**
     * @param serveur : Serveur
     * @param loginDistant : login distant
     * @param loginLocal : login local
     */
    public JavaRshClient( final String serveur, final String loginDistant, final String loginLocal )
    {
        super( serveur, loginDistant, loginLocal );
    }

    /**
     * Retourne le resultat d'un commande Unix en rsh Attention : le buffer est limité a 1024.
     * 
     * @param cmd : Commande unix
     * @param buff : Ecrit dans l'entree standard le contenu
     * @throws IOException : Retourne le buffer rcp ou bien une erreur d'execution
     * @return resultat unix
     */
    public int executecmd( final String cmd, final byte buff[] )
        throws IOException
    {
        lastReturnStream = null;
        lastErrorStream = null;

        final RCommandClient rsh = new RCommandClient();

        // Stocke ce que l'on a envoyer
        addMessage( ">" + cmd + "\n" );

        try
        {
            rsh.connect( serveur );
            rsh.rexec( loginLocal, loginDistant, cmd + "\n", true );

            // Si on a quelquechose dans le buffer
            if ( buff != null )
            {
                CopyUtils.copy( buff, rsh.getOutputStream() );
                addMessage( buff );
                rsh.getOutputStream().close();

                // Faut etre déconnecter avant de lire
                if ( ( rsh != null ) && rsh.isConnected() )
                {
                    rsh.disconnect();
                }
            }

            if ( rsh.getInputStream() != null )
            {
                lastReturnStream = IOUtils.toString( rsh.getInputStream() );
                addMessage( lastReturnStream );
            }

            if ( rsh.getErrorStream() != null )
            {
                lastErrorStream = IOUtils.toString( rsh.getErrorStream() );

                if ( lastErrorStream.length() > 0 )
                {
                    addMessage( lastErrorStream );

                    return 1;
                }
            }
        }
        catch ( final IOException ioe )
        {
            addMessage( ioe.getMessage() );
            throw ioe;
        }
        finally
        {
            if ( ( rsh != null ) && rsh.isConnected() )
            {
                rsh.disconnect();
            }
        }

        addMessage( ">OK\n" );

        return 0;
    }
}