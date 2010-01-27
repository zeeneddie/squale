/**
 * 
 */
package org.squale.test.gwt.client.widget;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author fabrice
 * 
 */
public class BigBox extends AbstractBox {

	public BigBox(DistributionMap dm, String name) {
		super(dm, name);

		FlowPanel mainPanel = new FlowPanel();
		for (int i = 0; i < (Random.nextInt(30) + 1); i++) {
			mainPanel.add(new SmallBox(dm, "Small BOX #" + i));
		}
		initWidget(mainPanel);

		setWidth("100px");
		setStylePrimaryName("big-box");
	}

	public void onMouseOut(MouseOutEvent event) {
		getDistributionMap().hideDetailPopupForBigBox();
	}

	public void onMouseOver(MouseOverEvent event) {
		getDistributionMap().showDetailPopupForBigBox("Hi from " + getName(),
				event.getClientX(), event.getClientY());
	}

}
