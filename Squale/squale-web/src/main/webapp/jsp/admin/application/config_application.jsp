<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.airfrance.squalecommon.util.messages.CommonMessages" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurForm" %>

<%--  
Assure la configuration de l'application
--%>
<%
// On construit le titre par rapport à la création ou la modification d'une application
java.lang.String modification = (java.lang.String) request.getParameter("modification");
String title = "application_creation.title";
if (modification != null) {
    // On modifie l'application
    pageContext.setAttribute("modification", modification);
    title = "application_modification.title";
}
%>

<bean:define id="applicationId" name="createApplicationForm"
	property="applicationId" type="String" />

<af:page>
	<af:body>
		<af:canvasCenter titleKey="<%=title%>"
			subTitleKey="application_creation.subtitle.config">
			<br />
			<br />
			<bean:message key="application_creation.config.details" />

			<div style="color: #f00"><html:messages id="message"
				property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
				message="true">
				<bean:write name="message" />
				<br />
			</html:messages> <br />
			</div>

			<af:form action="manageApplication.do">
				<input type="hidden"
					name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM%>"
					value="true">
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<af:field key="application.creation.field.application_name"
							property="applicationName" styleClassLabel="td1"
							access="READONLY" />
					</tr>
					<tr class="fondClair">
						<td class="td1"><bean:message
							key="application_creation.config.site" />*</td>
						<td><af:select property="serveurForm.serveurId" isRequired="true">
							<af:option key="empty" value=""></af:option>
							<logic:iterate name="listeServeur" property="serveurs" scope="request" id="site"
								type="ServeurForm">
								<af:option 
									value='<%=site.getServeurId() + ""%>' ><%=site.getName()%></af:option>
							</logic:iterate>
						</af:select></td>
					</tr>
					<tr>
						<af:field key="application_creation.config.audit_milestone"
							type="CHECKBOX" property="milestone" styleClassLabel="td1" />
					</tr>
					<tr>
						<af:field key="application_creation.config.public" type="CHECKBOX"
							property="public" styleClassLabel="td1" accessKey="admin"/>
					</tr>

					<tr>
						<af:field key="application_creation.config.audit_frequency"
							property="auditFrequency" isRequired="true" styleClassLabel="td1" />
					</tr>
					<tr class="fondClair">
						<af:field key="application_creation.config.purge_frequency"
							property="purgeDelay" isRequired="true" styleClassLabel="td1" />
					</tr>
					<tr>
						<af:field key="application_creation.config.inProduction"
							type="CHECKBOX" property="isInProduction" styleClassLabel="td1" />
					</tr>
					<tr class="fondClair">
						<af:field key="application_creation.config.externalDev"
							type="CHECKBOX" property="externalDev" styleClassLabel="td1" />
					</tr>
				</table>
				<af:buttonBar>
					<input type="hidden" name="applicationId"
						value="<%=applicationId%>">
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="config" />
					<logic:equal name="modification" value="true">
						<input type="hidden" name="modification" value="<%=modification%>" />
						<af:button name="configure.rights"
							toolTipKey="toolTip.configure.rights"
							callMethod="configAndForward" />
					</logic:equal>
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>