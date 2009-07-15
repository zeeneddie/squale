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
package com.airfrance.squalix.tools.ruleschecking;

/**
 * Traitement d'un rapport checkstyle Cette interface permet de réaliser le traitement des anomalies reportées dans un
 * rapport checkstyle. Toutes les informations disponibles dans le rapport sont passées en paramètre
 */
public interface ReportHandler
{
    /**
     * @param pFileName fichier
     * @param pLine ligne
     * @param pColumn colonne
     * @param pSeverity sévérité
     * @param pMessage message
     * @param pSource source
     */
    public void processError( String pFileName, String pLine, String pColumn, String pSeverity, String pMessage,
                              String pSource );
}
