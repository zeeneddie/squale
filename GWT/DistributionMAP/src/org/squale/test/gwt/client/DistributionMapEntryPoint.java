package org.squale.test.gwt.client;

import java.util.ArrayList;

import org.squale.test.gwt.client.widget.DistributionMap;
import org.squale.test.gwt.client.widget.data.Parent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class DistributionMapEntryPoint implements EntryPoint {

	private DataServiceAsync dataService = GWT.create(DataService.class);

	public void onModuleLoad() {
		final Button sendButton = new Button("Load distribution map!");
		final AsyncCallback<ArrayList<Parent>> callback = new AsyncCallback<ArrayList<Parent>>() {
			public void onSuccess(ArrayList<Parent> result) {
			    /*
				RootPanel.get("distributionmap").clear();
				for (Parent parent : result) {
					RootPanel rp = RootPanel.get("distributionmap");
					rp.add(new HTML("- " + parent.toString() + "<br/>"));
					for (Child child : parent.getChildren()) {
						rp.add(new HTML("<span style=\"padding-left:15px;\">* " + child.toString() + "</span><br/>"));
					}
				}
				*/
			}

			public void onFailure(Throwable caught) {
				RootPanel.get("distributionmap").add(new HTML(caught.toString()));
			}
		};
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dataService.getData(callback);
			}
		});
		RootPanel.get("main").add(sendButton);

        DistributionMap dmWidget = new DistributionMap();
        RootPanel.get( "distributionmap" ).add( dmWidget );

	}

}
