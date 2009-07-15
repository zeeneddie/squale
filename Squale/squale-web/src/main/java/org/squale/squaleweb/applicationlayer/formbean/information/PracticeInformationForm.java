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
package org.squale.squaleweb.applicationlayer.formbean.information;

import java.util.Collection;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Classe permettant d'afficher des informations sur une pratique
 */
public class PracticeInformationForm
    extends RootForm
{

    /** le trigger */
    private String trigger = "";

    /**
     * Les mesures extraites de la formule sous la forme ckjm.classe.cbo (i.e outil.typeComposant.attribut)
     */
    private Collection usedTres;

    /** la formule utilisée pour le calcul */
    private String formula = "";

    /**
     * Dans le cas de formule conditionnelle
     */
    private String[] formulaCondition;

    /** la correction à apporter */
    private String correction = "";

    /** la description de la pratique */
    private String description = "";

    /** le nom de la pratique */
    private String name = "";

    /**
     * @return la correction
     */
    public String getCorrection()
    {
        return correction;
    }

    /**
     * @return la description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return la formule
     */
    public String getFormula()
    {
        return formula;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return le trigger
     */
    public String getTrigger()
    {
        return trigger;
    }

    /**
     * @param pCorrection la correction
     */
    public void setCorrection( String pCorrection )
    {
        correction = pCorrection;
    }

    /**
     * @param pDescription la description
     */
    public void setDescription( String pDescription )
    {
        description = pDescription;
    }

    /**
     * @param pFormula la formule
     */
    public void setFormula( String pFormula )
    {
        formula = pFormula;
    }

    /**
     * @param pName le nom
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * @param pTrigger le trigger
     */
    public void setTrigger( String pTrigger )
    {
        trigger = pTrigger;
    }

    /**
     * @return les conditions de la formule
     */
    public String[] getFormulaCondition()
    {
        return formulaCondition;
    }

    /**
     * @param strings les conditions de la formule
     */
    public void setFormulaCondition( String[] strings )
    {
        formulaCondition = strings;
    }

    /**
     * @return les mesures utilisées dans la formule
     */
    public Collection getUsedTres()
    {
        return usedTres;
    }

    /**
     * @param pTres les mesures utilisées dans la formule
     */
    public void setUsedTres( Collection pTres )
    {
        usedTres = pTres;
    }

}
