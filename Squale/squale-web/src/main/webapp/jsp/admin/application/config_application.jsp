<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="org.squale.squalecommon.util.messages.CommonMessages" %>
<%@ page import="org.squale.squaleweb.util.SqualeWebConstants" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.config.ServeurForm" %>

<script type="text/javascript" src="jslib/jquery.js"></script>
<script>
function setEnabledCost()
{
	if ($("#iniDevFalse").attr("checked") == true )
	{
		$("#globalCostMaintenanceField").attr("disabled", false);
		$("#globalCostInitialField").attr("disabled", true);
		$("#devCostField").attr("disabled", true);		
	}
	else
	{
		$("#globalCostMaintenanceField").attr("disabled", true);
		$("#globalCostInitialField").attr("disabled", false);
		$("#devCostField").attr("disabled", false);		
	}
}

function enabledFrequencyAudit()
{
	if($("#milestoneCheck").attr("checked")== true)
	{
		$("#frequencyField").attr("disabled",true);
	}
	else
	{
		$("#frequencyField").attr("disabled",false);
	}
}
</script>
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
	<af:body onload="setEnabledCost(),enabledFrequencyAudit()">
		<af:canvasCenter titleKey="<%=title%>" >
			<!-- subTitleKey="application_creation.subtitle.config"-->
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
					name="<%=org.squale.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM%>"
					value="true">
				<!-- table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<tr-->
					<h2>
						<af:field key="application.creation.field.application_name"
							property="applicationName" styleClassLabel="td1Ext"
							access="READONLY" />
					</h2>
					<!-- /tr>
				</table-->
				<br/>
				<table width="100%" class="formulaire" cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<th class="height_col" colspan="2"><bean:message key="application_creation.config.config"/></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="td1Ext"><bean:message
								key="application_creation.config.site" />*</td>
							<td><af:select property="serveurForm.serveurId" isRequired="true" >
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
								type="CHECKBOX" property="milestone" styleClassLabel="td1Ext"
								styleId="milestoneCheck" onclick="enabledFrequencyAudit()"/>
						</tr>
						<tr>
							<af:field key="application_creation.config.audit_frequency"
								property="auditFrequency" isRequired="true" styleClassLabel="td1Ext" 
								styleId="frequencyField"/>
						</tr>
						<tr >
							<af:field key="application_creation.config.purge_frequency"
								property="purgeDelay" isRequired="true" styleClassLabel="td1Ext" />
						</tr>
						<tr>
							<af:field key="application_creation.config.public" type="CHECKBOX"
								property="public" accessKey="admin" styleClassLabel="td1Ext" overridePageAccess="true"/>
						</tr>
					</tbody>
				</table>
				<br/>
				<br/>
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th class="height_col" colspan="2"><bean:message key="application_creation.config.information"/></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<af:field key="application_creation.config.inProduction"
								type="CHECKBOX" property="isInProduction" styleClassLabel="td1Ext" />
						</tr>
						<tr>
							<af:field key="application_creation.config.externalDev"
								type="CHECKBOX" property="externalDev" styleClassLabel="td1Ext" />
						</tr>
						<tr>
							<af:field key="application_creation.config.qualityAtBeginning"
								type="CHECKBOX" property="qualityApproachOnStart" styleClassLabel="td1Ext" />
						</tr>
					</tbody>
				</table>
				<br/>
				<br/>
				<table width="100%" class="formulaire" cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<th class="height_col" colspan="2"><bean:message key="application_creation.config.cost"/></th>
						</tr>
					</thead>
						<tbody>
						<tr>
							<af:field key="application_creation.config.initialPhase"
								type="RADIO" property="inInitialDev" value="true" styleClassLabel="td1Ext" 
								styleId="iniDevTrue" onclick="setEnabledCost()"/>
						</tr>
						
						
						<tr>
							<af:field key="application_creation.config.initialGlobalCost"
								property="globalCostInitial" isRequired="false" styleClassLabel="td1Ext" 
								styleId="globalCostInitialField"/>
						</tr>
						
						<tr>
							<af:field key="application_creation.config.initialDevCost"
								property="devCost" isRequired="false" styleClassLabel="td1Ext"
								 styleId="devCostField" />
						</tr>
						
						<tr>
							<af:field key="application_creation.config.maintenancePhase"
								type="RADIO" property="inInitialDev" value="false" styleClassLabel="td1Ext" 
								styleId="iniDevFalse" onclick="setEnabledCost()"/>
						</tr>
						<tr>
							<af:field key="application_creation.config.maintenanceGlobalCost"
								property="globalCostMaintenance" isRequired="false" styleClassLabel="td1Ext" 
								styleId="globalCostMaintenanceField"/>
						</tr>
					</tbody>
				</table>
				<br/>
				<br/>
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