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
package com.airfrance.squalix.util.process;

import java.io.File;
import java.io.IOException;

/**
 * Un Mock pour les tests unitaires où il faut utiliser un ProcessManager
 */
public class ProcessManagerMock
    extends ProcessManager
{

    /**
     * Valeur que doit renvoyer le flux
     */
    private String mExpectedOutput;

    /**
     * @param pCommand la liste de commandes à exécuter
     * @param pEnvp les variables d'environnement à surcharger
     * @param pDir le fichier
     */
    public ProcessManagerMock( String[] pCommand, String[] pEnvp, File pDir )
    {
        super( pCommand, pEnvp, pDir );
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalix.util.process.ProcessManager#startProcess(com.airfrance.squalix.util.process.ProcessErrorHandler)
     */
    public int startProcess( ProcessErrorHandler pHandler )
        throws IOException, InterruptedException
    {
        getOutputHandler().processOutput( mExpectedOutput );
        return 0;
    }

    /**
     * @param pExpectedOutput la nouvelle valeur du flux
     */
    public void setExpectedOutput( String pExpectedOutput )
    {
        mExpectedOutput = pExpectedOutput;

    }

}