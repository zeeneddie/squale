package org.squale.gwt.distributionmap.test.client;

import java.util.ArrayList;

import org.squale.gwt.distributionmap.widget.data.Parent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync
{

    void getData( AsyncCallback<ArrayList<Parent>> callback );

}
