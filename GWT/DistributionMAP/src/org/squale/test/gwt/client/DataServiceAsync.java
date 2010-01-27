package org.squale.test.gwt.client;

import java.util.ArrayList;

import org.squale.test.gwt.client.widget.data.Parent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync
{

    void getData( AsyncCallback<ArrayList<Parent>> callback );

}
