<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%--
Permet l'importation d grille qualité
--%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>



<af:page>
	<af:body >
		<af:canvasCenter titleKey="message_admin.title" subTitleKey="message_admin.subtitle">
			<br />
			<br />
			<bean:message key="message_admin.details" />
			<br />
			<br />
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<af:form action="message.do" method="POST"
				enctype="multipart/form-data">
				<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
					border="0">
					<%--Fichier à charger --%>
					<tr>
						<td class="td1"><bean:message key="message_admin.file"/></td>
						<td><html:file property="file" /></td>
					</tr>
				</table>
				<af:buttonBar>
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="importMessages" singleSend="true" />
					<af:button type="form" name="messages.reload" toolTipKey="toolTip.valider"
						callMethod="reloadMessages" singleSend="true" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>