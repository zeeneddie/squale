<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%--
Affiche la liste des applications sur lesquelles l'utilisateur est
administrateur
--%>

<script type="text/javascript" src="/squale/jslib/format_page.js">
</script>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="applications_admin.title">
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="applications_admin.details" />
			</af:dropDownPanel>
			<br />
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<!-- Parcours de la liste des applications et affichage de celles-ci -->
			<af:table
				name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
				scope="session" property="adminList" totalLabelPos="none"
				emptyKey="table.results.none" pageLength="30">
				<af:cols id="appli">
					<af:col property="applicationName"
						href="/squale/manageApplication.do?action=selectApplicationToConfig"
						paramName="appli" paramId="applicationId" paramProperty="id"
						sortable="true" key="application.name" />
					<af:col property="lastUpdate" key="application.last_update"
						sortable="true" type="DATE">
						<logic:notEmpty name="appli" property="lastUpdate">
							<bean:define id="lastUpdate" name="appli" property="lastUpdate"
								type="java.util.Date" />
							<%=com.airfrance.squaleweb.util.SqualeWebActionUtils.getFormattedDate(lastUpdate, request.getLocale())%>
						</logic:notEmpty>
					</af:col>
					<af:col property="lastUser" key="application.last_user"
						sortable="true" />
				</af:cols>
			</af:table>
			<br />
			<h2><bean:message key="applications_admin.readonly.title" /></h2>
			<br />

			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="applications_admin.readonly.details" />
			</af:dropDownPanel>

			<br />
			<!-- Parcours de la liste des applications en lecture seule et affichage de celles-ci -->
			<af:table
				name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
				scope="session" property="readOnlyList" totalLabelPos="none"
				emptyKey="table.results.none" pageLength="30">
				<af:cols id="appli">
					<af:col property="applicationName"
						href="/squale/manageApplication.do?action=selectApplicationToConfig"
						paramName="appli" paramId="applicationId" paramProperty="id" />
				</af:cols>
			</af:table>
		</af:canvasCenter>
	</af:body>
</af:page>