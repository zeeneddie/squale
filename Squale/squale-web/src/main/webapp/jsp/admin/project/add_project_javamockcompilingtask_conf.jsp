<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants" %>
<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=org.squale.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<%-- Pour les champs --%>
<%boolean disabled = false; // Les champs seront en lecture/écriture par défaut %>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true; // Pour indiquer la lecture seule des champs car l'utilisateur est en lecture seule%>
</logic:equal>


<br />
<bean:message key="project_creation.java_mockcompiling_conf.details" />
<%-- Configuration des paramètres généraux --%>
<af:form action="add_project_java_mockcompiling_config.do">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<input name="applicationId" value="<%=applicationId%>" type="hidden">
	<%-- source compilée --%>
	<table id="compiledSrcTable" width="100%" class="formulaire" cellpadding="0"
		cellspacing="0" border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.java_mockcompiling.compiled_src_location" /></th>
			</tr>
		</THEAD>
		<tr style="display: none">
			<af:field styleClassLabel="td1"
				key="project_creation.java_mockcompiling.compiled_src_location"
				property="compiledSources" value="" size="60" />
		</tr>
		<squale:iteratePaths name="javaMockCompilingForm"
			key="project_creation.java_mockcompiling.compiled_src_location"
			property="compiledSources" disabled="<%=disabled%>" isRequired="true" />
	</table>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<af:buttonBar>
			<af:button onclick="addField('compiledSrcTable', 1);" name="add_compiled_source_line"
				singleSend="false" />
		</af:buttonBar>
	</logic:notEqual>
	<br />
	<%-- Chemin vers le classpath --%>
	<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
		border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.java_mockcompiling.field.classpath" /></th>
			</tr>
		</THEAD>
		<tr>
			<af:field key="project_creation.java_mockcompiling.field.classpath"
				property="classpath" size="60" isRequired="true"
				disabled="<%=disabled%>" styleClassLabel="td1"/>
		</tr>
	</table>
	<%-- Dialect des sources java --%>
	<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
		border="0">
		<THEAD>
			<tr>
				<th></th>
				<th style="width: 50%"><bean:message
					key="project_creation.java.version" /></th>
			</tr>
		</THEAD>
		<tr>
			<td class="td1"><bean:message key="project_creation.java.version" />*
			</td>
			<td><af:select isRequired="true" property="dialect"
				disabled="<%=disabled%>">
				<af:option key="empty" value="" />
				<af:option key="project.profile.java.1_3"
					value="<%=ParametersConstants.JAVA1_3%>">
				</af:option>
				<af:option key="project.profile.java.1_4"
					value="<%=ParametersConstants.JAVA1_4%>">
				</af:option>
				<af:option key="project.profile.java.1_5"
					value="<%=ParametersConstants.JAVA1_5%>">
				</af:option>
			</af:select></td>
		</tr>
	</table>
	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>