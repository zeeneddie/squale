package org.squale.test.gwt.client.widget;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * @author fabrice
 * 
 */
abstract public class AbstractBox extends Composite implements
		MouseMoveHandler, MouseOutHandler, MouseOverHandler {

	final private DistributionMap distributionMap;

	private String name;

	public AbstractBox(DistributionMap dm, String name) {
		this.distributionMap = dm;
		this.name = name;

		setupMouseEvents();
	}

	private void setupMouseEvents() {
		addDomHandler(this, MouseMoveEvent.getType());
		addDomHandler(this, MouseOverEvent.getType());
		addDomHandler(this, MouseOutEvent.getType());
	}

	protected DistributionMap getDistributionMap() {
		return distributionMap;
	}

	protected String getName() {
		return name;
	}

	public void onMouseMove(MouseMoveEvent event) {
		distributionMap.updateDetailPopup(event.getClientX(), event
				.getClientY());
	}

	abstract public void onMouseOut(MouseOutEvent event);

	abstract public void onMouseOver(MouseOverEvent event);

}
