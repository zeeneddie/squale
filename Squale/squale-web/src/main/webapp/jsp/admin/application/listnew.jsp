<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<%--
Affiche la liste des nouvelles applications en attente de confirmation
--%>
<af:page>
	<af:body>
		<af:canvasCenter titleKey="applications_ack.title">
			<br />
			<br />
			<bean:message key="applications_ack.details" />
			<br />
			<br />
			<div style="color:#f00;">
				<html:errors/>
			</div>
			<%-- Parcours de la liste des applications et affichage de ceux-ci --%>
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<af:form action="ack_newapplication.do">
				<af:table name="applicationListForm" scope="session" property="list"
					totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="appli">
						<af:colSelect />
						<af:col property="applicationName" key="application.name"
							href="manageApplication.do?action=selectApplicationToConfig"
							paramName="appli" paramId="applicationId"
							paramProperty="applicationId" sortable="true" />
						<af:col property="lastUpdate" key="application.last_update"
							sortable="true" type="DATE">
							<logic:notEmpty name="appli" property="lastUpdate">
								<bean:define id="lastUpdate" name="appli" property="lastUpdate"
									type="java.util.Date" />
								<%=org.squale.squaleweb.util.SqualeWebActionUtils.getFormattedDate(lastUpdate, request.getLocale())%>
							</logic:notEmpty>
						</af:col>
						<af:col property="lastUser" key="application.last_user"
							sortable="true" />
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" name="confirmer"
						toolTipKey="toolTip.confirmer" callMethod="acknowledge" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>