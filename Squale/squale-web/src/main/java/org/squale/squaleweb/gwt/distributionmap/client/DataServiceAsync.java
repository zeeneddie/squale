/**
 * 
 */
package org.squale.squaleweb.gwt.distributionmap.client;

import java.util.ArrayList;

import org.squale.gwt.distributionmap.widget.data.Parent;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author fabrice
 *
 */
public interface DataServiceAsync
{

    /**
     * 
     * @see org.squale.squaleweb.gwt.distributionmap.client.DataService#getData(long, long)
     */
    void getData( long auditId, long projectId, long practiceId, AsyncCallback<ArrayList<Parent>> callback );

}
