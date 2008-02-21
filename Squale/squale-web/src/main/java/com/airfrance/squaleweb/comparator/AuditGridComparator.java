package com.airfrance.squaleweb.comparator;

import java.util.Comparator;

import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;

/**
 * Comparateur de grilles d'audit
 */
public class AuditGridComparator implements Comparator {

    /**
     * Compare les griles d'audit en utilisant les dates de leur audit.
     * @param pAudit1 la première application
     * @param pAudit2 la seconde application
     * @return la comparaison des noms des applications.
     */
    public int compare(Object pAudit1, Object pAudit2) {
        int result = 0;
            result = ((AuditGridDTO) pAudit1).getAudit().getRealDate().compareTo(((AuditGridDTO) pAudit2).getAudit().getRealDate());
        return result;
    }
}
