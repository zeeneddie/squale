<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c"%>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm"%>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.GeneralParametersForm"%>
<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"%>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>

<%--
	The project configuration page allows the global configuration of a project :
		->	Standard part of the configuration (source management,java compilation, JSP compilation...) = first part
		->	Advanced configuration (tools needing configuration such as Checkstyle, Macker,UMLQuality...) = second part
--%>

<%-- 
	Getting the "applicationId" value of "createProjectForm" and putting
	it into a scripting variable called "applicationId"
--%>
<bean:define id="applicationId" name="createProjectForm" property="applicationId" type="String" />

<%-- Getting the "projectId" value of "createProjectForm" and putting
it into a scripting variable called "projectId" --%>	
<bean:define id="projectId" name="createProjectForm" property="projectId" type="String" />

<%-- Declaring a boolean which will serve for the drop down panel on a later stage --%>
<% boolean expanded = false;%>

<%-- If tool is empty or is absent --%>
<logic:empty name="tool">
	<% request.setAttribute("tool", ""); %>
</logic:empty>

<%-- Duplicating an attribute of a bean and declaring a scripting variable --%>
<bean:define id="toolBean" name="tool" />

<%-- 
	Defining a new 'page scope attribute' called "profile" by accessing the "profile+applicationId" value stored in
	the "logonBean" (see com.airfrance.welcom.struts.util.WConstants.USER_KEY  for more info)
--%>
<bean:define id="profile" name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>" 
property="<%=\"profile(\"+applicationId+\")\"%>" />

<%-- Starting here the page --%>
<af:page>
	
	<%-- The header --%>
	<af:head>
		<%-- Global layout defined by a javascript --%>
		<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>
			
		<%--
			This script allows auto-validation of the form when the user wishes to add :
				-> an Ant script and its target
				-> an Eclipse 2 or 3 project
		 --%>
		<script type="text/javascript">
			function submitWithAction(form, action, kindOfTask) {
				document.forms[form].action.value=action;
				document.getElementById("kOT").value=kindOfTask;
				document.forms[form].submit();
			}
		</script>
	</af:head>
	
	<%-- The body --%>
	<af:body>
		
		<%-- Defines the main part of the page (i.e centre part) --%>
		<af:canvasCenter titleKey="application_modification.title" subTitleKey="application_modification.subtitle.modify_project">
			<br />
			<bean:message key="project_creation.field.project_name" />

			<%-- Writes down the name of the project --%>
			<b><bean:write name="createProjectForm" property="projectName" scope="session" /></b>
			<br /><br />
			
			<bean:message key="project_creation.configuration.details" />
			<br /><br />
			
			<div style="color: #f00">			
				<%-- Displays a message if the connected user is a simple user and not an admin --%>
				<logic:equal name="profile" value="<%=ProfileBO.READER_PROFILE_NAME%>">
					<bean:message key="page.readonly" />
				</logic:equal>
				
				<html:messages id="message" property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
					message="true">
					<bean:write name="message" /><br />
				</html:messages>
			</div>
			
			<%-- Standard tasks configuration part --%>
			<p class="surtitre"><bean:message key="project_configuration.standard.tasks" /></p>
			<br />
			
			<%-- Iterating : for each element contained in "standardTasks" (from "createProjectForm") : creating a "task"--%>
			<logic:iterate id="task" scope="session" name="createProjectForm" property="standardTasks">
				
				<%-- Defining a new scripting variable "taskName" (type String) and putting the value of the "name" of each "task" in it --%>
				<bean:define name="task" property="name" id="taskName" type="String" />
				<%expanded = false;%>
				
				<%-- If "toolBean" equals to "taskName" --%>
				<logic:equal name="toolBean" value="<%=taskName%>">
					<%-- Modifying the value of the boolean "expanded" --%>
					<%expanded = true;%>
				</logic:equal>
				
				<%--
					Creating the name of the key by concatenating the "taskName" with hard-coded values.
					The value of "keyTask" will be displayed as a dropDownPanel title
				--%>
				<% String keyTask = "task." + taskName.toLowerCase() + ".configuration"; %>
				<%-- Creating the name of the task configuration JSP which will be included --%>
				<% String pageTask = "add_project_" + taskName.toLowerCase() + "_conf.jsp"; %>

				<af:dropDownPanel titleKey="<%=keyTask%>"
					headerStyle="padding-left:20px;" contentStyle="padding:5px;"
					lazyLoading="true" expanded="<%=expanded%>">
		
					<c:import url="/${taskName}.do?action=fill&${com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM}=true" />
					<div id="conteneur"><jsp:include page="<%=pageTask%>" /></div>
				</af:dropDownPanel>
				<br />
			</logic:iterate>
			
			<%expanded = false;%>
			<logic:equal name="toolBean" value="<%=new GeneralParametersForm().getTaskName()%>">
				<%expanded = true;%>
			</logic:equal>
			
			<%-- Displays a Drop Down Panel and open it if expanded == true --%>
			<af:dropDownPanel titleKey="project_configuration.advanced.tasks" headerStyle="padding-left:20px;" contentStyle="padding:5px;"
				lazyLoading="true" expanded="<%=expanded%>">

				<%-- 
					Retrieves content of this URL (created with the "taskName" and a constant) and displays it
					e.g : /scmTask.do?action=fill&doNotResetForm=true 
				--%>
				<c:import url='/generalConfiguration.do?action=fill&${com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM}=true' />
				
				<%-- Including the task configuration page (JSP) --%>
				<div id="conteneur">
					<jsp:include page="add_project_general_parameters.jsp" />
				</div>
				
			</af:dropDownPanel>
			<br />

			<%-- Advanced Tasks configuration --%>
			<%-- Creating a bean named "advancedSize" (type Integer) value = number of elements in the collection "advancedTasks" (from "createProjectForm") --%>
			<bean:size id="advancedSize" name="createProjectForm" property="advancedTasks" />
			
			<%-- If "advanced size" is greater than zero --%>
			<logic:greaterThan name="advancedSize" value="0">
				
				<p class="surtitre"><bean:message key="project_configuration.general.parameters" /></p>
				<br />
				
				<%-- Iterating : foreach element contained in "advancedTasks" --%>
				<logic:iterate id="task" scope="session" name="createProjectForm" property="advancedTasks">
					
					<%-- Defining a new scripting variable "taskName" (type String) and putting the value of the "name" of each "task" in it --%>
					<bean:define name="task" property="name" id="taskName" type="String" />
					<%expanded = false;%>
					
					<%-- If "toolBean" equals to "taskName" --%>
					<logic:equal name="toolBean" value="<%=taskName%>">
						<%-- Modifying the value of the boolean "expanded" --%>
						<%expanded = true;%>
					</logic:equal>

					<%--
						Creating the name of the key by concatenating the "taskName" with hard-coded values.
						The value of "keyTask" will be displayed as title of a dropDownPanel
					--%>
					<% String keyTask = "task." + taskName.toLowerCase() + ".configuration"; %>

					<%-- Creating the name of the task configuration jsp which will be included --%> 
					<% String pageTask = "add_project_" + taskName.toLowerCase() + "_conf.jsp"; %>
					
					<%-- Displays a Drop Down Panel and open it if expanded == true --%>
					<af:dropDownPanel titleKey="<%=keyTask%>" headerStyle="padding-left:20px;" contentStyle="padding:5px;"
						lazyLoading="false" expanded="<%=expanded%>">

						<%-- 
							Retrieves content of this URL (created with the "taskName" and a constant) and displays it
							e.g : /scmTask.do?action=fill&doNotResetForm=true 
						--%>
						<c:import url="/${taskName}.do?action=fill&${com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM}=true" />
						
						<%-- Including the task configuration page (JSP) --%>
						<div id="conteneur"><jsp:include page="<%=pageTask%>" /></div>
					
					</af:dropDownPanel>
					<br />
				</logic:iterate>
			</logic:greaterThan>

			<%-- Preparing the bottom validation bar --%>
			<af:buttonBar>
				
				<%-- If it is a creation (which means that "modification" is empty), preparing the action.do --%>
				<logic:empty name="modification">
					<af:button type="form" name="retour" toolTipKey="toolTip.retour"
						onclick="<%=\"config_project.do?action=selectProjectToModify&applicationId=\"+applicationId + \"&projectId=\"+ projectId%>" />
				</logic:empty>
				
				<%-- If it is a modification (which means that "modification" is not empty), preparing the action.do and adding at the end "&modification=true" --%>
				<logic:notEmpty name="modification">
					<af:button type="form" name="retour" toolTipKey="toolTip.retour"
						onclick="<%=\"config_project.do?action=selectProjectToView&applicationId=\"+applicationId + \"&projectId=\"+ projectId+\"&modification=true\"%>" />
				</logic:notEmpty>
				
				<%-- If the user is not a simple reader--%>
				<logic:notEqual name="profile" value="<%=ProfileBO.READER_PROFILE_NAME%>">
					<af:button type="form" name="end.configuration" toolTipKey="toolTip.end.project.configuration"
					onclick="<%=\"manageApplication.do?action=selectApplicationToConfig&applicationId=\"+applicationId%>" />
				</logic:notEqual>
				
			</af:buttonBar>
		</af:canvasCenter>
	</af:body>
</af:page>