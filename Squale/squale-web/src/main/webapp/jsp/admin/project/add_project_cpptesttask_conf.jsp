<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabled" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />

<br />
<bean:message key="project_creation.cpp.mccabe.details" />
<%-- Configuration des param�tres g�n�raux de cpptest --%>
<af:form action="add_project_cpptest_config.do">
	<%-- attribut projectId pour les droits d'acc�s --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
		border="0">

		<%-- r�gles CppTest --%>
		<tr class="fondClair">
			<td class="td1"><bean:message
				key="project_creation.cpptest.field.ruleset" />*</td>
			<td><af:select property="selectedRuleSet" lazyLoading="false"
				isRequired="true" disabled="<%=disabled%>">
				<af:option key="cppconfig" value="" />
				<logic:iterate id="cppconfig" name="cppTestForm" property="ruleSets"
					scope="session" type="String">
					<af:option key="cppconfig" value="<%=cppconfig%>" />
				</logic:iterate>
			</af:select></td>
		</tr>
		<tr>
			<af:field key="project_creation.cpptest.field.script_location"
				property="script" size="60" isRequired="true" styleClassLabel="td1"
				disabled="<%=disabled%>" />
		</tr>
	</table>

	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</sec:ifHasProfile>
</af:form>