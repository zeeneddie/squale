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
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface à implémenter pour le formatage de la sortie des fichiers Excel.
 * 
 * @author Rémy Bouquet
 */
public interface ExcelGenerateur
{

    /**
     * @throws ExcelGenerateurException : Level une erreur sur la production du fichier
     */
    public abstract void writeExcel()
        throws ExcelGenerateurException;

    /**
     * @throws IOException : Probleme lors de l'ouverture des streams
     */
    public abstract void init()
        throws IOException;

    /**
     * assigne l'outputstream
     * 
     * @param os outputStream à setter
     * @throws ExcelGenerateurException exception pouvant etre levee
     */
    public void open( OutputStream os )
        throws ExcelGenerateurException;

}