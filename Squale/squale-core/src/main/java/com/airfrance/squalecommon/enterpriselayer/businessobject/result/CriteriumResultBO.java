//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\CriteriumResultBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;

/**
 * Contient le résultat du sritère associé
 * @author m400842
 * 
 * @hibernate.subclass
 * discriminator-value="CriteriumResult"
 */
public class CriteriumResultBO extends QualityResultBO {
       
    /**
     * Constructeur par défaut
     * @roseuid 42C9354C022B
     */
    public CriteriumResultBO() {
        super();
    }
    
    /**
     * Constructeur complet
     * @param pMeanMark la note moyenne
     * @param pProject le sous-projet correspondant
     * @param pAudit l'audit correspondant
     * @param pRule la regle à affecter
     * @roseuid 42C9354C026A
     */
    public CriteriumResultBO(float pMeanMark, ProjectBO pProject, AuditBO pAudit, CriteriumRuleBO pRule) {
        super(pMeanMark, pProject, pAudit);
        mRule = pRule;
    }
}
