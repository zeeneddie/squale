package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.Comparator;
import java.util.Date;

/**
 * Compare les auditBO par rapport à leur date
 */
public class AuditDateComparator
    implements Comparator
{

    /** Permet de savoir sur quelle date on compare */
    private boolean mExecutedDate;

    /**
     * Constructeur par défaut
     */
    public AuditDateComparator()
    {
        mExecutedDate = false;
    }

    /**
     * @param pExecutedDate true si on compare selon le date d'exécution false si on compare sur la date des sources
     */
    public AuditDateComparator( boolean pExecutedDate )
    {
        mExecutedDate = pExecutedDate;
    }

    /**
     * {@inheritDoc} Compare les audits en utilisant leurs dates.
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare( Object pAudit1, Object pAudit2 )
    {
        int result = 1;
        // Vérification du type des objets
        if ( pAudit1 instanceof AuditBO )
        {
            // On récupère par défaut la date réelle des audits
            Date date1 = ( (AuditBO) pAudit1 ).getRealDate();
            Date date2 = ( (AuditBO) pAudit2 ).getRealDate();
            // Si l'attribut précisant le type de comparaison est renseigné
            // on compare selon la date d'exécution
            if ( mExecutedDate )
            {
                date1 = ( (AuditBO) pAudit1 ).getDate();
                date2 = ( (AuditBO) pAudit2 ).getDate();
            }
            // la date réelle ou d'exécution peut être nulle
            if ( date1 != null )
            {
                // On fait donc la vérification pour les deux audits
                if ( date2 != null )
                {
                    result = date1.compareTo( date2 );
                }
                else
                {
                    result = -1;
                }
            }
        }
        return result;
    }
}
