<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

<%--
Affiche la liste des profiles et des types de récupération des sources
pour que l'administrateur de squale puisse configurer le portail.
--%>

<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/config_menu.jsp">
		<af:canvasCenter titleKey="portail.configuration.title">
			<br />
			<br />
			<bean:message key="config.details" />
			<br />
			<br />
			<div style="color:#f00;">
            <html:messages id="msg" message="true">
             <bean:write name="msg"/><br>
            </html:messages>
			</div>
			<br/>
			<af:form action="config_portail.do" scope="session" method="POST">
				<bean:message key="config.sourceManagement.title" />
				<br/><br/>
				<af:table name="configPortailForm" property="sourceManagements"
					totalLabelPos="none" emptyKey="config.sourceManagement.empty">
					<af:cols id="manager">
						<af:col property="id" key="config.sourceManagement.name" sortable="true">
							<bean:write name="manager" property="name"/>
						</af:col>
					</af:cols>
				</af:table>
				<br/>
				<bean:message key="config.projectProfile.title" />
				<br/><br/>
				<af:table name="configPortailForm" property="profiles"
					totalLabelPos="none" emptyKey="config.projectProfile.empty">
					<af:cols id="profile">
						<af:col property="id" key="config.projectProfile.name" sortable="true">
							<bean:write name="profile" property="name" />
						</af:col>
					</af:cols>
				</af:table>
				<br/>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>