<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.GeneralParametersForm"%>
<%@ page
	import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"%>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>

<%--
Liste les tâches à configurer en fonction du profile et du source management
du projet ajouté à l'application courante. 
--%>

<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<%
// On définit le booléen indiquant si il faut ouvrir le dropDownPanel
boolean expanded = false;
%>
<logic:empty name="tool">
	<% request.setAttribute("tool", ""); %>
</logic:empty>
<bean:define id="toolBean" name="tool" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="profile"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<af:page>
	<af:head>
		<script type="text/javascript"
			src="theme/charte_v03_001/js/format_page.js"></script>
		<script type="text/javascript">
			/* 
				Permet de soumettre un formulaire sans le valider
				Cette méthode sert pour la compilation java afin de valider
				les changements dans le cas d'jout de règle de compilation
			*/
			function submitWithAction(form, action, kindOfTask) {
				document.forms[form].action.value=action;
				document.getElementById("kOT").value=kindOfTask;
				document.forms[form].submit();
			}
</script>
	</af:head>
	<af:body>
		<af:canvasCenter titleKey="application_modification.title"
			subTitleKey="application_modification.subtitle.modify_project">
			<br />
			<bean:message key="project_creation.field.project_name" />
			<b> <bean:write name="createProjectForm" property="projectName"
				scope="session" /></b>
			<br />
			<br />
			<bean:message key="project_creation.configuration.details" />
			<br />
			<br />
			<div style="color: #f00"><logic:equal name="profile"
				value="<%=ProfileBO.READER_PROFILE_NAME%>">
				<bean:message key="page.readonly" />
			</logic:equal> <html:messages id="message"
				property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
				message="true">
				<bean:write name="message" />
				<br />
			</html:messages></div>
			<p class="surtitre"><bean:message
				key="project_configuration.standard.tasks" /></p>
			<br />
			<logic:iterate id="task" scope="session" name="createProjectForm"
				property="standardTasks">
				<bean:define name="task" property="name" id="taskName" type="String" />
				<%expanded = false;%>
				<logic:equal name="toolBean" value="<%=taskName%>">
					<%expanded = true; // si le nom de la tâche est présente en requête alors on l'affiche %>
				</logic:equal>
				<%
// recuperation des clés
String keyTask = "task." + taskName.toLowerCase() + ".configuration";
// récupération de la page
String pageTask = "add_project_" + taskName.toLowerCase() + "_conf.jsp";%>
				<af:dropDownPanel titleKey="<%=keyTask%>"
					headerStyle="padding-left:20px;" contentStyle="padding:5px;"
					lazyLoading="true" expanded="<%=expanded%>">
					
					<c:import
						url="/${taskName}.do?action=fill&${com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM}=true" />
					<div id="conteneur"><jsp:include page="<%=pageTask%>" /></div>
				</af:dropDownPanel>
				<br />
			</logic:iterate>
			<%-- page de configuration générale --%>
			<%expanded = false;%>
			<logic:equal name="toolBean"
				value="<%=new GeneralParametersForm().getTaskName()%>">
				<%expanded = true;%>
			</logic:equal>
			<af:dropDownPanel titleKey="project_configuration.advanced.tasks"
				headerStyle="padding-left:20px;" contentStyle="padding:5px;"
				lazyLoading="true" expanded="<%=expanded%>">

				<c:import
					url='/generalConfiguration.do?action=fill&${com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM}=true' />
				<div id="conteneur"><jsp:include
					page="add_project_general_parameters.jsp" /></div>
			</af:dropDownPanel>

			<br />
			<bean:size id="advancedSize" name="createProjectForm"
				property="advancedTasks" />
			<logic:greaterThan name="advancedSize" value="0">
				<p class="surtitre"><bean:message
					key="project_configuration.general.parameters" /></p>
				<br />

				<logic:iterate id="task" scope="session" name="createProjectForm"
					property="advancedTasks">
					<bean:define name="task" property="name" id="taskName"
						type="String" />
					<%expanded = false;%>
					<logic:equal name="toolBean" value="<%=taskName%>">
						<%expanded = true; // si le nom de la tâche est présente en requête alors on l'affiche %>
					</logic:equal>
					<%
// recuperation des clés
String keyTask = "task." + taskName.toLowerCase() + ".configuration";
// récupération de la page
String pageTask = "add_project_" + taskName.toLowerCase() + "_conf.jsp";%>
					<af:dropDownPanel titleKey="<%=keyTask%>"
						headerStyle="padding-left:20px;" contentStyle="padding:5px;"
						lazyLoading="false" expanded="<%=expanded%>">
						
						<c:import
							url="/${taskName}.do?action=fill&${com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM}=true" />
						<div id="conteneur"><jsp:include page="<%=pageTask%>" /></div>
					</af:dropDownPanel>
					<br />
				</logic:iterate>
			</logic:greaterThan>

			<af:buttonBar>
				<%-- dans ce cas on spécifie et l'id de l'application et l'id du projet car le lien n'a pas encore été fait --%>
				<af:button type="form" name="retour" toolTipKey="toolTip.retour"
					onclick="<%=\"config_project.do?action=selectProjectToModify&applicationId=\"+applicationId + \"&projectId=\"+ projectId%>" />
				<logic:notEqual name="profile"
					value="<%=ProfileBO.READER_PROFILE_NAME%>">
					<af:button type="form" name="end.configuration"
						toolTipKey="toolTip.end.project.configuration"
						onclick="<%=\"manageApplication.do?action=selectApplicationToConfig&applicationId=\"+applicationId%>" />
				</logic:notEqual>
			</af:buttonBar>

		</af:canvasCenter>
	</af:body>
</af:page>