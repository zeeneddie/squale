/**
 * 
 */
package org.squale.test.gwt.client.widget;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author fabrice
 * 
 */
public class DistributionMap extends Composite {

	final private DecoratedPopupPanel detailPopup = new DecoratedPopupPanel(
			true);
	private String bigBoxDetailPopupMessage;
	private String smallBoxDetailPopupMessage;

	public DistributionMap() {
		FlowPanel mainPanel = new FlowPanel();

		for (int i = 0; i < 30; i++) {
			mainPanel.add(createBigBox());
		}

		detailPopup.setWidth("150px");

		initWidget(mainPanel);
	}

	private Widget createBigBox() {
		BigBox box = new BigBox(this, "Big BOX #" + Random.nextInt(100));
		return box;
	}

	public void updateDetailPopup(int xPosition, int yPosition) {
		detailPopup.setPopupPosition(xPosition + 1, yPosition + 1);
	}

	public void hideDetailPopupForSmallBox() {
		detailPopup.setWidget(new HTML(bigBoxDetailPopupMessage));
	}

	public void showDetailPopupForSmallBox(String message, int xPosition,
			int yPosition) {
		smallBoxDetailPopupMessage = message;
		detailPopup.setWidget(new HTML(smallBoxDetailPopupMessage));
		detailPopup.show();
		updateDetailPopup(xPosition, yPosition);
	}

	public void hideDetailPopupForBigBox() {
		detailPopup.hide();
	}

	public void showDetailPopupForBigBox(String message, int xPosition,
			int yPosition) {
		bigBoxDetailPopupMessage = message;
		detailPopup.setWidget(new HTML(bigBoxDetailPopupMessage));
		detailPopup.show();
		updateDetailPopup(xPosition, yPosition);
	}
}
