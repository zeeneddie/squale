//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\FactorResultBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;

/**
 * Contient le résultat du facteur associé
 * @author m400842
 * 
 * @hibernate.subclass
 * discriminator-value="FactorResult"
 */
public class FactorResultBO extends QualityResultBO {
    
    
    /**
     * Constructeur par défaut
     * @roseuid 42C9372303CE
     */
    public FactorResultBO() {
        super();
    }
    
    /**
     * Constructeur complet
     * @param pMeanMark la note moyenne
     * @param pProject le sous-projet correspondant
     * @param pAudit l'audit correspondant
     * @param pRule la FactorRule correspondante
     * @roseuid 42C937240063
     */
    public FactorResultBO(float pMeanMark, ProjectBO pProject, AuditBO pAudit, FactorRuleBO pRule) {
        super(pMeanMark, pProject, pAudit);
        mRule = pRule;
    }
}
