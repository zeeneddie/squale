<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>

<%-- 
	This JSP configures the generic task. A task configuration JSP is called if
	the task is present in the squalix_config.xml
--%>

<%-- Getting values for local beans : the project and the application ones --%>
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />

<%-- Getting the property of userProfile variable so as to define the authorisation --%>
<bean:define id="userProfile"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />

<%-- Default value of the boolean for the fields : RW when set to false --%>
<%boolean disabled = false;%>

<%--
	if the variable "userProfile" equals to constant value "READER_PROFILE_NAME"
	then the boolean will be set to true
--%>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true;%>
</logic:equal>

<%-- 
	Main configuration of the generic tool thanks to a form :
	calling action addGenericTaskConfig in the struts-config.xml
--%>
<af:form action="add_project_genericTask_config.do">

	<%-- Defining a hidden input for the "projectId" --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">

	<%-- Printing some info messages for the user --%>
	<p><bean:message key="genericTask.inform.user.intro" /></p>
	<ul>
		<li><bean:message key="genericTask.inform.user.toolDir" /></li>
		<li><bean:message key="genericTask.inform.user.args_params" /></li>
		<li><bean:message key="genericTask.inform.user.workingDir" /></li>
		<li><bean:message key="genericTask.inform.user.resultsDir" /></li>
	</ul>

	<%--
		Creating a table containing the fields to display and/or edit the task configuration. Please notice that
		for the fields that are mandatory (if isRequired=true) one must verify in the Form that the fields are filled
		see GenericTaskForm.java for more info.  
	--%>
	<table id="genericTaskTable" width="100%" class=formulaire cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr><th><bean:message key="genericTask.conf.table.title" /></th><th></th></tr>
		</thead>

		<%-- Field for command that has to be executed --%>
		<tr>
			<af:field key="genericTask.conf.toolLocation" name="genericTaskForm"
				property="toolLocation" isRequired="true" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>
		
		<%-- Field for the working directory --%>
		<tr>
			<af:field key="genericTask.conf.workingDir" name="genericTaskForm"
				property="workingDirectory" isRequired="false" styleClassLabel="td1" size="60"
				disabled="<%=disabled%>" />
		</tr>

		<%-- Field for commands and parameters --%>
		<tr>
			<af:field key="genericTask.conf.commands" type="textarea" name="genericTaskForm"
				property="commands" isRequired="false" styleClassLabel="td1" size="57"
				disabled="<%=disabled%>" />
		</tr>

		<%-- Field(s) for the result files location --%>
		<tr style="display:none">
			<af:field key="genericTask.conf.resultsLocation" name="genericTaskForm" property="resultsLocation"
			 styleClassLabel="td1" value="" size="60" disabled="<%=disabled%>" />
		</tr>
		
		<squale:iteratePaths name="genericTaskForm" property="resultsLocation"
			key="genericTask.conf.resultsLocation" disabled="<%=disabled%>" />
	</table>
	
	<%-- If the user is an administrator --%>
	<logic:notEqual name="userProfile" value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%-- Including a validation button bar --%>
		<af:buttonBar>
			<!-- Specific button that add a field for other potential result locations -->
			<af:button onclick="addField('genericTaskTable', 4);" name="add_resultLocation" singleSend="true" />
		</af:buttonBar>
		<!-- Including the standard button bar -->
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>

</af:form>
