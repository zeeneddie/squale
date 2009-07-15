<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

<af:buttonBar>
	<af:button type="form" name="valider" toolTipKey="toolTip.valider"
		callMethod="addParameters" singleSend="true" />
	<af:button type="form" name="delete" toolTipKey="toolTip.remove.task.configuration"
		callMethod="removeParameters" singleSend="true" messageConfirmationKey="remove.task.configuration.confirm" />
</af:buttonBar>