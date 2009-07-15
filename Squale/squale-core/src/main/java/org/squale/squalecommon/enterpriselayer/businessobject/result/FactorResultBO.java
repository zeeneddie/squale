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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\businessobject\\result\\FactorResultBO.java

package org.squale.squalecommon.enterpriselayer.businessobject.result;

import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;

/**
 * Contient le résultat du facteur associé
 * 
 * @author m400842
 * @hibernate.subclass discriminator-value="FactorResult"
 */
public class FactorResultBO
    extends QualityResultBO
{

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42C9372303CE
     */
    public FactorResultBO()
    {
        super();
    }

    /**
     * Constructeur complet
     * 
     * @param pMeanMark la note moyenne
     * @param pProject le sous-projet correspondant
     * @param pAudit l'audit correspondant
     * @param pRule la FactorRule correspondante
     * @roseuid 42C937240063
     */
    public FactorResultBO( float pMeanMark, ProjectBO pProject, AuditBO pAudit, FactorRuleBO pRule )
    {
        super( pMeanMark, pProject, pAudit );
        mRule = pRule;
    }
}
