<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<af:buttonBar>
	<af:button type="form" name="valider" toolTipKey="toolTip.valider"
		callMethod="addParameters" singleSend="true" />
	<af:button type="form" name="remove.configuration" toolTipKey="toolTip.remove.task.configuration"
		callMethod="removeParameters" singleSend="true" messageConfirmationKey="remove.task.configuration.confirm" />
</af:buttonBar>