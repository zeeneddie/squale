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
 * Créé le 3 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.spell.exception;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellCheckerNotFound
    extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 4750174715019154209L;

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     */
    public WSpellCheckerNotFound()
    {
        super();
    }

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     * 
     * @param message Message
     */
    public WSpellCheckerNotFound( final String message )
    {
        super( message );
    }

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     * 
     * @param message Message
     * @param cause Cause
     */
    public WSpellCheckerNotFound( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     * 
     * @param cause Cause
     */
    public WSpellCheckerNotFound( final Throwable cause )
    {
        super( cause );
    }

}
