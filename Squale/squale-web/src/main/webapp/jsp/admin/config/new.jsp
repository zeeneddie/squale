<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%--
Permet l'importation d grille qualité
--%>

<script type="text/javascript" src="/squale/jslib/format_page.js">
</script>



<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/config_menu.jsp">
		<af:canvasCenter titleKey="config_import.title" subTitleKey="config_import.subtitle">
			<br />
			<br />
			<bean:message key="config_import.details" />
			<br />
			<br />
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<af:form action="createConfig.do" method="POST"
				enctype="multipart/form-data">

				<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
					border="0">
					<%--Fichier à charger --%>
					<tr>
						<td class="td1"><bean:message key="config_import.file"/></td>
						<td><html:file property="file" /></td>
					</tr>
				</table>
				<af:buttonBar>
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"/>
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>