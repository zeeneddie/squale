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
package org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.pmd;

import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;
import org.squale.squalecommon.util.ConstantRulesChecking;

/**
 * Jeu de règles PMD Le fichier PMD est stocké sous la forme d'un Blob dans la base de données
 * 
 * @hibernate.subclass  discriminator-value="Pmd"
 */
public class PmdRuleSetBO
    extends RuleSetBO
{

    /** Nom de la règle pour les JSPS non conforme au XHTML */
    public static final String XHTML_RULE_NAME = "NotXHTMLCompliant";

    /** Sévérité de la règle pour les JSPS non conforme au XHTML */
    public static final String XHTML_RULE_SEVERITY = ConstantRulesChecking.ERROR_LABEL;

    /** Catégorie de la règle pour les JSPS non conforme au XHTML */
    public static final String XHTML_RULE_CATEGORY = "jspstandard";

    /**
     * Valeur sous forme de chaine de bytes
     */
    private byte[] mValue;

    /** Langage */
    private String mLanguage;

    /**
     * Access method for the mFileName property.
     * 
     * @return the current value of the FileName property
     * @hibernate.property name="Value" column="FileContent"
     *                     type="org.squale.jraf.provider.persistence.hibernate.BinaryBlobType" not-null="false"
     *                     unique="false" update="true" insert="true"
     */
    public byte[] getValue()
    {
        return mValue;
    }

    /**
     * @param pValue sous forme de byte[]
     */
    public void setValue( byte[] pValue )
    {
        mValue = pValue;
    }

    /**
     * Access method for the mLanguage property.
     * 
     * @return language
     * @hibernate.property name="Language" column="Language" type="string" not-null="false" unique="false" update="true"
     *                     insert="true"
     */
    public String getLanguage()
    {
        return mLanguage;
    }

    /**
     * @param pLanguage langage
     */
    public void setLanguage( String pLanguage )
    {
        mLanguage = pLanguage;
    }
}
