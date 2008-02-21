<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<%
// On récupère les paramètres pour les ouvertures des dropPanels
String applisStatsHelpPanel = (String) request.getParameter("applisStatsHelpPanel");
if (null == applisStatsHelpPanel) {
    applisStatsHelpPanel = "false";
}
String applisStatsParamPanel = (String) request.getParameter("applisStatsParamPanel");
if (null == applisStatsParamPanel) {
    applisStatsParamPanel = "false";
}
%>
<af:page>
	<af:head>
		<script type="text/javascript" src="/squale/jslib/manage_tab.js"></script>
	</af:head>
	<af:body>
		<af:canvasCenter titleKey="stats.page.title">
			<bean:define id="profils" name="setOfStatsForm"
				property="listOfProfilsStatsForm" />
			<bean:define id="sites" name="setOfStatsForm"
				property="listOfSiteStatsForm" />
			<bean:define id="audits" name="setOfStatsForm"
				property="auditsStatsForm" />
			<bean:define id="factors" name="setOfStatsForm"
				property="factorsStatsForm" />
			<af:form action="stats.do">
				<input type="hidden" name="<%="applisStatsHelpPanel"%>"
					id="<%="applisStatsHelpPanel"%>" value="<%=applisStatsHelpPanel%>" />
				<input type="hidden" name="<%="applisStatsParamPanel"%>"
					id="<%="applisStatsParamPanel"%>" value="<%=applisStatsParamPanel%>" />
				<B><bean:message key="stats.applications" /></B>
				<br />
				<br />
				<af:dropDownPanel titleKey="stats.help"
					expanded="<%=Boolean.valueOf(applisStatsHelpPanel).booleanValue()%>"
					onExpand="changeVarValue('applisStatsHelpPanel', 'true');"
					onCollapse="changeVarValue('applisStatsHelpPanel', 'false');">
					<bean:message key="stats.applications.help.details" />
				</af:dropDownPanel>
				<br />
				<af:dropDownPanel titleKey="stats.applications.configure"
					expanded="<%=Boolean.valueOf(applisStatsParamPanel).booleanValue()%>"
					onExpand="changeVarValue('applisStatsParamPanel', 'true');"
					onCollapse="changeVarValue('applisStatsParamPanel', 'false');">
					<table class="tblh">
						<thead>
							<tr>
								<th colspan="2"><bean:message key="stats.applications.configure" /></th>
							</tr>
						</thead>
						<tr>
							<af:field key="stats.application.nbDaysTerminated"
								property="nbDaysForTerminated" />
						</tr>
						<tr>
							<af:field key="stats.application.nbDaysAll"
								property="nbDaysForAll" />
						</tr>
					</table>
					<af:buttonBar>
						<af:button name="valider" callMethod="displayAdmin" />
					</af:buttonBar>
				</af:dropDownPanel>
				<br />
				<br />
				<af:table name="setOfStatsForm"
					property="listOfApplicationsStatsForm">
					<af:cols id="applicationStatForm">
						<af:col key="stats.application.col" property="applicationName"
							sortable="true">
							<logic:equal name="applicationStatForm"
								property="validatedApplication" value="true">
								<bean:write name="applicationStatForm"
									property="applicationName" />
							</logic:equal>
							<logic:equal name="applicationStatForm"
								property="validatedApplication" value="false">
								<b><bean:write name="applicationStatForm"
									property="applicationName" /></b>
							</logic:equal>
						</af:col>
						<af:col key="stats.state.col" property="lastAuditIsTerminated"
							sortable="true">
						</af:col>
						<af:col key="stats.active.col" property="activatedApplication"
							sortable="true">
						</af:col>
						<af:col key="stats.last_audit_date.col"
							property="lastTerminatedAuditDate"/>
						<af:col key="stats.last_audit_duration.col"
							property="lastAuditDuration" sortable="true" type="NUMBER" />
						<af:col key="stats.nb_audits_last_days.col" property="nbAudits"
							sortable="true" type="NUMBER" />
						<af:col key="stats.nb_terminated_audits.col"
							property="nbTerminatedAudits" sortable="true" type="NUMBER" />
						<af:col key="stats.nb_failed_audits.col"
							property="nbPartialOrFaliedAudits" sortable="true" type="NUMBER" />
						<af:col key="stats.last_failed_audit.col"
							property="lastFailedAuditDate"/>
						<af:col key="stats.first_terminated_audit.col"
							property="firstTerminatedAuditDate"/>
						<af:col key="stats.purge.col" property="purgeFrequency"
							sortable="true" type="NUMBER"/>
						<af:col key="stats.server.col" property="serverName"
							sortable="true" />
						<af:col key="stats.last_access.col" property="lastAccess"/>
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button name="export.excel"
						toolTipKey="toolTip.export.excel.applications_stats"
						callMethod="ApplicationsStatsExportExcel" singleSend="false" />
				</af:buttonBar>
				<br />
				<br />
				<B><bean:message key="stats.bySite" /></B>
				<br />
				<br />
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th><bean:message key="stats.siteNames" /></th>
							<logic:iterate name="sites" id="siteNames">
								<th align="right"><bean:write name="siteNames"
									property="serveurForm.name" /></th>
							</logic:iterate>
						</tr>
					</thead>
					<tr class="fond clair">
						<td><bean:message key="stats.applis.audits.successful" /></td>
						<logic:iterate name="sites" id="sitesSuccess">
							<td align="right"><bean:write name="sitesSuccess"
								property="nbAppliWithAuditsSuccessful" /></td>
						</logic:iterate>
					</tr>
					<tr>
						<td><bean:message key="stats.applis.audits.no.successfuls" /></td>
						<logic:iterate name="sites" id="sitesWithoutSuccess">
							<td align="right"><bean:write name="sitesWithoutSuccess"
								property="nbAppliWithoutSuccessfulAudits" /></td>
						</logic:iterate>
					</tr>
					<tr class="fond clair">
						<td><bean:message key="stats.applis.validated" /></td>
						<logic:iterate name="sites" id="sitesValidated">
							<td align="right"><bean:write name="sitesValidated"
								property="nbValidatedApplis" /></td>
						</logic:iterate>
					</tr>
					<tr>
						<td><bean:message key="stats.applis.to_validate" /></td>
						<logic:iterate name="sites" id="sitesValidated">
							<td align="right"><bean:write name="sitesValidated"
								property="nbAppliToValidate" /></td>
						</logic:iterate>
					</tr>
					<tr class="fond clair">
						<td><bean:message key="stats.nbProjects" /></td>
						<logic:iterate name="sites" id="siteNb">
							<td align="right"><bean:write name="siteNb" property="nbProjects" />
							</td>
						</logic:iterate>
					</tr>
					<tr>
						<td><bean:message key="stats.loc" /></td>
						<logic:iterate name="sites" id="siteLoc">
							<td align="right"><bean:write name="siteLoc" property="loc" /></td>
						</logic:iterate>
					</tr>
					<tfoot>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
					</tfoot>



					
				</table>
				<br />
				<B><bean:message key="stats.byProfile" /></B>
				<br />
				<br />
				<bean:size id="profilesSize" name="profils" />
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th><bean:message key="stats.profileNames" /></th>
							<logic:iterate name="profils" id="profilsNames">
								<th align="right"><bean:write name="profilsNames"
									property="profileName" /></th>
							</logic:iterate>
						</tr>
					</thead>
					<tr class="fond clair">
						<td><bean:message key="stats.nbProjects" /></td>
						<logic:iterate name="profils" id="profilsNb">
							<td align="right"><bean:write name="profilsNb"
								property="nbProjects" /></td>
						</logic:iterate>
					</tr>
					<tr>
						<td><bean:message key="stats.loc" /></td>
						<logic:iterate name="profils" id="profilsLoc">
							<td align="right"><bean:write name="profilsLoc" property="loc" />
							</td>
						</logic:iterate>
					</tr>
					<tfoot>
						<tr>
							<td colspan="<%=profilesSize.intValue() + 1%>">&nbsp;</td>
						</tr>
					</tfoot>
				</table>
				<br />
				<br />
				<B><bean:message key="stats.audits" /></B>
				<br />
				<br />
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th><bean:message key="audits.type" /></th>
							<th align="right"><bean:message key="stats.nb" /></th>
						</tr>
					</thead>
					<tr class="fond clair">
						<td><bean:message key="audits.successfuls" /></td>
						<td align="right"><bean:write name="audits"
							property="nbSuccessfuls" /></td>
					</tr>
					<tr>
						<td><bean:message key="audits.failed" /></td>
						<td align="right"><bean:write name="audits" property="nbFailed" />
						</td>
					</tr>
					<tr class="fond clair">
						<td><bean:message key="audits.partial" /></td>
						<td align="right"><bean:write name="audits" property="nbPartial" />
						</td>
					</tr>
					<tr>
						<td><bean:message key="audits.notAttempted" /></td>
						<td align="right"><bean:write name="audits"
							property="nbNotAttempted" /></td>
					</tr>
					<tr class="fond clair">
						<td><bean:message key="audits.total" /></td>
						<td align="right"><bean:write name="audits" property="nbTotal" /></td>
					</tr>
					<tfoot>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</tfoot>
				</table>
				<br />
				<br />
				<B><bean:message key="stats.factors" /></B>
				<br />
				<br />
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th><bean:message key="factors.type" /></th>
							<th align="right"><bean:message key="stats.nb" /></th>
						</tr>
					</thead>
					<tr class="fond clair">
						<td><bean:message key="factors.refused" /></td>
						<td align="right"><bean:write name="factors"
							property="nbFactorsRefused" /></td>
					</tr>
					<tr>
						<td><bean:message key="factors.reserved" /></td>
						<td align="right"><bean:write name="factors"
							property="nbFactorsReserved" /></td>
					</tr>
					<tr class="fond clair">
						<td><bean:message key="factors.accepted" /></td>
						<td align="right"><bean:write name="factors"
							property="nbFactorsAccepted" /></td>
					</tr>
					<tfoot>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</tfoot>
				</table>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>