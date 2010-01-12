<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants" %>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabledObj" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />
<%boolean disabled = ((Boolean)pageContext.getAttribute("disabledObj")).booleanValue();%>

<br />
<bean:message key="project_creation.java_pmd_conf.details" />
<br />
<br />
<%-- Configuration des paramètres généraux de java_cpd --%>
<af:form action="add_project_pmd_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<%-- pour conserver la valeur lors de la validation du formulaire --%>
	<html:hidden name="pmdForm" property="jspSourcesRequired" />

	<%-- sélection du ruleset java/pmd --%>
	<table width="100%" class="tblh" cellpadding="0" cellspacing="0"
		border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.pmd.field.conf_java_version" /></th>
			</tr>
		</THEAD>
		<tr>
			<td class="td1"><bean:message
				key="project_creation.pmd.field.conf_java_version" />*</td>
			<td align="left"><af:select isRequired="true"
				property="selectedJavaRuleSet" disabled="<%=disabled%>">
				<af:option key="pmdConfiguration" value="" />
				<logic:iterate id="pmdConfiguration" name="pmdForm"
					property="javaRuleSets" type="String">
					<af:option key="pmdConfiguration" value="<%=pmdConfiguration%>" />
				</logic:iterate>
			</af:select></td>
		</tr>
	</table>

	<%-- sélection du ruleset jsp/pmd (optionnel) --%>
	<logic:equal name="pmdForm" property="jspSourcesRequired" value="true">
		<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
			border="0">
			<THEAD>
				<tr>
					<th></th>
					<th style="width: 50%"><bean:message
						key="project_creation.pmd.field.conf_jsp_version" /></th>
				</tr>
			</THEAD>
			<tr>
				<td class="td1"><bean:message
					key="project_creation.pmd.field.conf_jsp_version" />*</td>
				<td align="left"><af:select isRequired="true"
					property="selectedJspRuleSet" disabled="<%=disabled%>">
					<af:option key="pmdConfiguration" value="" />
					<logic:iterate id="pmdConfiguration" name="pmdForm"
						property="jspRuleSets" type="String">
						<af:option key="pmdConfiguration" value="<%=pmdConfiguration%>" />
					</logic:iterate>
				</af:select></td>
			</tr>
		</table>
	</logic:equal>

	<input type="hidden" name="projectId" value="<%=projectId%>">
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>
</af:form>