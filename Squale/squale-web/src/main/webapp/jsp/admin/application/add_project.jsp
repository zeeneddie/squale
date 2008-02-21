<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ page import="org.apache.struts.action.ActionMessages" %>
<%@ page import="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>

<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />
<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />

<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<%-- Pour les champs --%>
<%boolean disabled = false;%>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true;%>
</logic:equal>

<%
// On récupère l'attribut qui indique si on modifie l'application
java.lang.String modification = (java.lang.String) session.getAttribute("modification");
// On construit ensuite le titre en fonction de l'action (par défaut création)
String subtitle = "application_creation.subtitle.add_project";
String details = "project_creation.details";
if (modification != null) {
    // On modifie l'application
    subtitle = "application_modification.subtitle.modify_project";
    details = "project_modification.details";
}
%>

<af:page accessKey="default">
	<af:head>
		<script type="text/javascript">
			/* On définit un tableau associatif pour faire le lien entre les profils et les grilles */
			var profilesGridsTab = new Array();
			/* On ajoute un champ vide dans le cas où aucun profil n'est sélectionné */
			profilesGridsTab[""] = ";";
			
			/* On ajoute une entrée au tableau clé=le nom du profil, valeur=les grilles séparées par ; */
			function addGrids(profileName, grids) {
				profilesGridsTab[profileName] = grids;
			}
			
			/* Modifie la liste des grilles en fonction du profil sélectionné */
			function setGridList(profileName) {
				/* On va sélectionner la grille si elle existe dans la nouvelle liste */
				index = 0;
				gridName = document.createProjectForm.qualityGrid.value;
				grids = profilesGridsTab[profileName];
				var reg=new RegExp(";", "g");
				/* On crée le tableau des grilles à partir de la chaîne passée en paramètre */
				var newOptions=grids.split(reg);
				document.createProjectForm.qualityGrid.options.length = newOptions.length;
				/* On parcours les grilles pour créer les options */
				for (i=0; i<newOptions.length; i++) {
	      			document.createProjectForm.qualityGrid.options[i].value = newOptions[i];
	      			document.createProjectForm.qualityGrid.options[i].text = newOptions[i];
	      			if(gridName == newOptions[i]) {
	      				index = i;
	      			}
	      		}
	    		document.createProjectForm.qualityGrid.options.selectedIndex = index;
			}
			
			/* Permet d'initialiser les grilles en fonction du profil sélectionné */
			function initGrids() {
				setGridList(document.createProjectForm.profile.value);
			}
			
			/* Permet d'indiquer à l'utilisateur de sélectionner un profil pour pouvoir sélectionner une grille */
			function verifProfile(message) {
				if(document.createProjectForm.profile.value.length == 0) {
					alert(message);
				}
			}
		</script>
	</af:head>
	<af:body onload="initGrids()">
		<af:canvasCenter titleKey="application_modification.title"
			subTitleKey="<%=subtitle%>">
			<div style="color: #f00"><logic:equal name="userProfile"
				value="<%=ProfileBO.READER_PROFILE_NAME%>">
				<bean:message key="page.readonly" />
			</logic:equal> <html:messages id="message"
				property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
				message="true">
				<bean:write name="message" />
				<br />
			</html:messages></div>

			<bean:message key="<%=details%>" />
			<af:form action="add_project.do">
				<input type="hidden"
					name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM%>"
					value="true">
				<input name="projectId" value="<%=projectId%>" type="hidden">
				<input name="applicationId" value="<%=applicationId%>" type="hidden">
				<table class="formulaire">
					<thead>
						<tr align="left">
							<th colspan="4" align="center"><bean:message key="project_creation.form.title"/></th>
						</tr>
					</thead>
					<tr>
						<af:field key="application.creation.field.application_name"
							name="createApplicationForm" property="applicationName" size="50"
							styleClassLabel="td1" disabled="true" accessKey="manager" />
					</tr>
					<%-- Paramètres généraux du projet --%>

					<%-- Nom --%>
					<tr class="fondClair">
						<af:field key="project_creation.field.project_name"
							property="projectName" isRequired="true" styleClassLabel="td1"
							disabled="<%=disabled%>" size="50"/>
					</tr>
					<%-- Profile --%>
					<tr>
						<td class="td1"><bean:message
							key="project_creation.field.project_profile" />*</td>
						<td><af:select property="profile" lazyLoading="false" 
							isRequired="true" disabled="<%=disabled%>" 
							onchange='setGridList(this.options[this.selectedIndex].value)'>
							<af:option key="empty" value="" />
							<logic:iterate id="profile" name="profiles">
								<bean:define id="profileName" name="profile" property="name"
									type="String" />
								<bean:define id="grids" name="profile" property="grids" 
									type="java.util.Collection" />
								<af:option key="<%=profileName%>" value="<%=profileName%>">
									<%=profileName%>
								</af:option>
								<script type="text/javascript">
								/* On ajoute une entrée au tableau pour chaque profil */
								addGrids('<%=profileName%>', '<%=SqualeWebActionUtils.formatArray(grids, ";")%>');
								</script>
							</logic:iterate>
						</af:select></td>
					</tr>
					<%-- SourceManagement --%>
					<tr class="fondClair">
						<td class="td1"><bean:message
							key="project_creation.field.source_management" />*</td>
						<td><af:select property="sourceManagement" lazyLoading="false"
							isRequired="true" disabled="<%=disabled%>">
							<af:option key="empty" value="" />
							<logic:iterate id="sourceManagement" name="sourceManagements">
								<bean:define id="managerName" name="sourceManagement"
									property="name" type="String" />
								<af:option key="<%=managerName%>" value="<%=managerName%>">
									<%=managerName%>
								</af:option>
							</logic:iterate>
						</af:select></td>
					</tr>
					<%-- QualityGrid --%>
					<tr>
						<td class="td1"><bean:message
							key="project_creation.field.quality_grid" />*</td>
						<td><af:select property="qualityGrid" lazyLoading="false"
							isRequired="true" disabled="<%=disabled%>" 
								onclick='<%="verifProfile(\'"+ WebMessages.getString(request, "project_creation.info.selection") +"\');"%>'>
						</af:select></td>
					</tr>
				</table>
				<af:buttonBar>
					<%
// On construit le lien pour le bouton retour
String action = "manageApplication.do?action=selectApplicationToConfig&applicationId=" + applicationId;
String href = "location.href='" + action + "'";%>
					<af:button type="form" name="retour" toolTipKey="toolTip.retour"
						onclick="<%=href%>" />
					<logic:empty name="modification">
						<input type="hidden" name="applicationId"
							value="<%=applicationId%>" />
					</logic:empty>
					<input type="hidden"
						name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_UPDATE_PROJECT_NAME%>"
						value="true">
					<logic:notEqual name="userProfile"
						value="<%=ProfileBO.READER_PROFILE_NAME%>">
						<logic:notEmpty name="modification">
							<input type="hidden" name="projectId" value="<%=projectId%>">
							<af:button type="form" name="save.configuration"
								toolTipKey="toolTip.save.project.configuration"
								callMethod="saveProject" />
						</logic:notEmpty>
						<af:button type="form" name="continue"
							toolTipKey="toolTip.configure.parameters" callMethod="addProject"
							singleSend="true" />
					</logic:notEqual>
					<%-- Dans le cas ou l'utilisateur est seulement lecteur, il n'a rien modifié
					On utilise donc une action beaucoup plus simple qui ne rafraichit rien --%>
					<logic:equal name="userProfile"
						value="<%=ProfileBO.READER_PROFILE_NAME%>">
						<af:button type="form" name="continue"
							toolTipKey="toolTip.configure.parameters"
							callMethod="viewProjectConfig" singleSend="true" />
					</logic:equal>
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>