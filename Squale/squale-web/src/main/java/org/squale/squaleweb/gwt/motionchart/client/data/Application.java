/**
 * 
 */
package org.squale.squaleweb.gwt.motionchart.client.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Fabrice BELLINGARD
 */
public class Application
    implements Serializable
{
    private static final long serialVersionUID = 5889015086352096098L;

    private String name;

    private HashMap<Long, AuditValues> auditValues = new HashMap<Long, AuditValues>();

    // for GWT
    public Application()
    {
    }

    public Application( String name )
    {
        this.name = name;
    }

    public AuditValues getAudit( long auditId, Date auditDate )
    {
        AuditValues audit = auditValues.get( auditId );
        if ( audit == null )
        {
            audit = new AuditValues( auditDate );
            auditValues.put( auditId, audit );
        }
        return audit;
    }

    public AuditValues getAudit( long auditId )
    {
        return auditValues.get( auditId );
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public Collection<AuditValues> getAuditValues()
    {
        return auditValues.values();
    }
}
