<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>
<%@taglib uri="http://www.squale.org/squale/security" prefix="sec"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants" %>
<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<sec:notHasProfile var="disabled" profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>" />

<br />
<bean:message key="project_creation.exclude.details" />
<%-- Message d'avertissement dans le cas J2EE --%>
<logic:equal name="generalParametersForm" property="jspSourcesRequired" value="true">
	<br/><bean:message key="project_creation.jsp_path.warning"/>
</logic:equal>
<af:form action="add_project_general_parameters.do" method="POST">
	<%-- attribut projectId pour les droits d'accès --%>
	<html:hidden name="createProjectForm" property="projectId" />
	<%-- pour que la valeur soit envoyée lors de la validation du formulaire --%>
	<html:hidden name="generalParametersForm" property="jspSourcesRequired" />
	<br />
	<%-- source a auditer --%>
	<table id="srcTable" width="100%" class="tblh" cellpadding="0"
		cellspacing="0" border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.field.project_src_location" /></th>
			</tr>
		</THEAD>
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.field.project_src_location" property="sources"
				value="" size="60" />
		</tr>
		<squale:iteratePaths name="generalParametersForm"
			key="project_creation.field.project_src_location" property="sources"
			isRequired="true" disabled="<%=disabled%>" />
	</table>
	<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
		<af:buttonBar>
			<af:button onclick="addField('srcTable', 1);" name="add_source_line"
				singleSend="false" />
		</af:buttonBar>
	</sec:ifHasProfile>
	<br />

	<%-- Le panneau avec la liste des sources jsp est optionnel --%>
	<logic:equal name="generalParametersForm" property="jspSourcesRequired"
		value="true">
		<%-- source jsp a auditer seulement dans le cas où le JSP est audité --%>
		<table id="jspTable" width="100%" class="tblh" cellpadding="0"
			cellspacing="0" border="0">
			<THEAD>
				<tr>
					<th></th>
					<th style="width: 50%"><bean:message
						key="project_creation.field.project_jsp_location" /></th>
				</tr>
			</THEAD>
			<tr style="display: none">
				<af:field styleClassLabel="td1"
					key="project_creation.field.project_jsp_location"
					property="jspSources" value="" size="60" />
			</tr>
			<squale:iteratePaths name="generalParametersForm"
				key="project_creation.field.project_jsp_location"
				property="jspSources" isRequired="true" disabled="<%=disabled%>" />
		</table>
		<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
			<af:buttonBar>
				<af:button onclick="addField('jspTable', 1);" name="add_jsp_line"
					singleSend="false" />
			</af:buttonBar>
		</sec:ifHasProfile>
	</logic:equal>

		<%-- patterns à exclure --%>
		<table id="excludedPatternsTable" width="100%" class="tblh"
			cellpadding="0" cellspacing="0" border="0">
			<THEAD>
				<tr>
					<th></th>
					<th style="width: 50%"><bean:message
						key="project_creation.exclude.analysis.title" /></th>
				</tr>
			</THEAD>
			<tr style="display: none">
				<af:field styleClassLabel="td1"
					key="project_creation.exclude.analysis.title"
					property="excludePatterns" value="" size="60" />
			</tr>
			<squale:iteratePaths name="generalParametersForm"
				property="excludePatterns"
				key="project_creation.exclude.analysis.title"
				disabled="<%=disabled%>" />
		</table>
		<br />
		<%-- patterns à inclure --%>
		<table id="includedPatternsTable" width="100%" class="tblh"
			cellpadding="0" cellspacing="0" border="0">
			<THEAD>
				<tr>
					<th></th>
					<th style="width: 50%"><bean:message
						key="project_creation.include.analysis.title" /></th>
				</tr>
			</THEAD>
			<tr style="display: none">
				<af:field styleClassLabel="td1"
					key="project_creation.include.analysis.title"
					property="includePatterns" value="" size="60" />
			</tr>
			<squale:iteratePaths name="generalParametersForm"
				property="includePatterns"
				key="project_creation.include.analysis.title"
				disabled="<%=disabled%>" />
		</table>
		<sec:ifHasProfile profiles="<%=ProfileBO.ADMIN_PROFILE_NAME + ',' + ProfileBO.MANAGER_PROFILE_NAME%>" applicationId="<%=applicationId%>">
			<af:buttonBar>
				<af:button onclick="addField('excludedPatternsTable', 1);"
					name="add_ex_anal_line" singleSend="false" />
				<af:button onclick="addField('includedPatternsTable', 1);"
					name="add_in_anal_line" singleSend="false" />
			</af:buttonBar>
			<jsp:include page="common_parameters_buttons.jsp" />
		</sec:ifHasProfile>
		</af:form>