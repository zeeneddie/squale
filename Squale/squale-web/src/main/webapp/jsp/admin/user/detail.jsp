<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<%@ page import="org.apache.struts.action.ActionMessages" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.action.ManageAccountAction"%>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm"%>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProfileForm"%>

<%--
Permet de modifier un compte
 --%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>


<af:page>
	<af:body>
		<af:canvasCenter titleKey="user_management.title">
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="user_management.details" />
			</af:dropDownPanel>
			<br />
			<div style="color: #f00"><html:messages id="message"
				property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
				message="true">
				<bean:write name="message" />
				<br />
			</html:messages> <br />
			</div>

			<af:form action="admin_account.do">
				<%-- On met en paramètre l'ancien email dans le cas de modification --%>
				<bean:define id="oldEmail" name="userForm" property="email" />
				<input type="hidden" name="<%=ManageAccountAction.OLD_EMAIL_KEY%>"
					value="<%=oldEmail%>" />
				<table class="formulaire">
					<thead>
						<tr>
							<th colspan="2"><bean:message
								key="user_management.personal_information" /></th>
						</tr>
					</thead>
					<tr>
						<af:field key="user_management.field.matricule"
							property="matricule" disabled="true" />
					</tr>
					<tr>
						<af:field key="user_management.field.name" property="name"
							size="50" />
					</tr>
					<tr>
						<af:field key="user_management.field.email" property="email"
							size="50" />
					</tr>
					<tr>
						<af:field key="user_management.field.unsubscribed"
							property="unsubscribed" type="CHECKBOX" />
					</tr>
				</table>
				<af:buttonBar>
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="update" singleSend="true" />
				</af:buttonBar>
			</af:form>
			<h2><bean:message key="user_management.application.list" /></h2>
			<%-- on récupère la hashmap et dans cette hashmap on récupère le profil associé
				à l'application si il existe sinon on affiche rien --%>
			<bean:define id="map" name="userForm" property="profiles"
				type="java.util.HashMap" />
			<af:table name="userForm" scope="session" property="applicationsList"
				totalLabelPos="none" emptyKey="table.results.none">
				<af:cols id="appli" selectable="false">
					<af:col property="applicationName"
						key="user_management.application.name" sortable="true" />
					<af:col property="applicationName"
						key="user_management.application.profile" sortable="true">
						<bean:define id="dtoName"
							value="<%=((ProfileForm) map.get(appli)).getName()%>" />
						<logic:notEqual name="dtoName" value="null">
							<bean:message key="<%=dtoName%>" />
						</logic:notEqual>
						<logic:equal name="dtoName" value="null">
							<bean:message key="empty" />
						</logic:equal>
					</af:col>
				</af:cols>
			</af:table>
			<br />
			<bean:size name="userForm" property="onlyAdminApplicationsList"
				id="inCreationSize" />
			<logic:greaterThan name="inCreationSize" value="0">
				<bean:message key="user_management.application.in_creation.list" />
				<af:table name="userForm" scope="session"
					property="onlyAdminApplicationsList" totalLabelPos="none"
					emptyKey="table.results.none">
					<af:cols id="appli" selectable="false">
						<af:col property="applicationName"
							key="user_management.application.name" sortable="true" />
						<af:col property="applicationName"
							key="user_management.application.profile" sortable="true">
							<bean:define id="dtoName"
								value="<%=((ProfileForm) map.get(appli)).getName()%>" />
							<logic:notEqual name="dtoName" value="null">
								<bean:message key="<%=dtoName%>" />
							</logic:notEqual>
							<logic:equal name="dtoName" value="null">
								<bean:message key="empty" />
							</logic:equal>
						</af:col>
					</af:cols>
				</af:table>
			</logic:greaterThan>
		</af:canvasCenter>
	</af:body>
</af:page>