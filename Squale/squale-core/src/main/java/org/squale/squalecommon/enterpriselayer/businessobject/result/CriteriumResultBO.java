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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\businessobject\\result\\CriteriumResultBO.java

package org.squale.squalecommon.enterpriselayer.businessobject.result;

import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;

/**
 * Contient le résultat du sritère associé
 * 
 * @author m400842
 * @hibernate.subclass discriminator-value="CriteriumResult"
 */
public class CriteriumResultBO
    extends QualityResultBO
{

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42C9354C022B
     */
    public CriteriumResultBO()
    {
        super();
    }

    /**
     * Constructeur complet
     * 
     * @param pMeanMark la note moyenne
     * @param pProject le sous-projet correspondant
     * @param pAudit l'audit correspondant
     * @param pRule la regle à affecter
     * @roseuid 42C9354C026A
     */
    public CriteriumResultBO( float pMeanMark, ProjectBO pProject, AuditBO pAudit, CriteriumRuleBO pRule )
    {
        super( pMeanMark, pProject, pAudit );
        mRule = pRule;
    }
}
