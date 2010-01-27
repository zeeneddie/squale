/**
 * 
 */
package org.squale.test.gwt.client.widget;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author fabrice
 * 
 */
public class SmallBox extends AbstractBox {

	public SmallBox(DistributionMap dm, String name) {
		super(dm, name);

		HTML divElement = new HTML();
		divElement.setSize("10px", "10px");
		divElement.setStylePrimaryName("small-box");

		initWidget(divElement);
	}

	public void onMouseOut(MouseOutEvent event) {
		getDistributionMap().hideDetailPopupForSmallBox();
	}

	public void onMouseOver(MouseOverEvent event) {
		getDistributionMap().showDetailPopupForSmallBox("Hi from " + getName(),
				event.getClientX(), event.getClientY());
	}

}
