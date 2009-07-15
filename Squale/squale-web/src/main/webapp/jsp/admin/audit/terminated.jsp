<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@ page import="org.squale.squaleweb.util.SqualeWebConstants" %>
<%@ page import="org.squale.squaleweb.util.graph.GraphMaker"%>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.config.ServeurForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.action.results.audit.AuditAction" %>

<af:page titleKey="administration.terminated_audit.title"
	accessKey="default">
	<af:head>
		<script type="text/javascript">
			function reloadWithServerName(serverName) {
				location.href="audits.do?action=displayTerminated&<%=AuditAction.SERVER_NAME_KEY%>="+serverName;
			}
		</script>
	</af:head>
	<af:body>
		<af:canvasCenter>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="audits.previous.terminated.stats.help" />
			</af:dropDownPanel>
			<br />
			<br />
			<af:form action="purgeAuditAction.do" scope="session" method="POST"
				name="selectForm"
				type="org.squale.squaleweb.applicationlayer.formbean.component.AuditListForm">
				<input type="hidden" name="<%=AuditAction.SERVER_NAME_KEY%>"
					value="<%=request%>" />
				<af:table name="splitAuditsListForm" property="terminatedAudits"
					totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="audit" idIndex="index" selectable="true">
						<af:col property="applicationName" key="audit.application_name"
							sortable="true" type="string" editable="false" />
						<af:col key="audit.realBeginningDate" property="realBeginningDate"
							type="DATE" sortable="true"
							dateFormatKey="datetime.format.simple" />
						<af:col key="audit.endDate" property="endDate" type="DATE"
							sortable="true" dateFormatKey="datetime.format.simple" />
						<af:col key="audit.duration" property="duration" type="string"
							sortable="true" />
						<af:col key="audit.fsSize" property="maxFileSystemSize"
							type="long" sortable="true" />
					</af:cols>
				</af:table>
				<br />
				<%-- On affiche une liste déroulante pour sélectionner le nom du serveur --%>
				<bean:message key="audits.previous.terminated.stats.select.server" />
				<select
					onchange="reloadWithServerName(this.options[this.selectedIndex].value)">
					<option value="" /><logic:iterate
						name="<%=AuditAction.SERVER_LIST_KEY%>" property="serveurs"
						scope="session" id="site" type="ServeurForm">
						<logic:equal name="<%=AuditAction.SERVER_NAME_KEY%>"
							value="<%=site.getName()%>">
							<option selected value='<%=site.getName()%>'><%=site.getName()%></option>
						</logic:equal>
						<logic:notEqual name="<%=AuditAction.SERVER_NAME_KEY%>"
							value="<%=site.getName()%>">
							<option value='<%=site.getName()%>'><%=site.getName()%></option>
						</logic:notEqual>
					</logic:iterate>
				</select>
			</af:form>
			<%-- On affiche les graphes que si il a des données pour le serveur sélectionné --%>
			<logic:empty name="splitAuditsListForm" property="timeMaker">
				<logic:notEqual name="<%=AuditAction.SERVER_NAME_KEY%>" value="">
					<bean:define id="serverName" name="<%=AuditAction.SERVER_NAME_KEY%>" type="String"/>
					<%-- Il n'y a pas de données pour le serveur sélectionné --%>
					<b><bean:message key="audits.previous.terminated.no.stats"
						arg0="<%=serverName%>" /></b>
				</logic:notEqual>
			</logic:empty>
			<logic:notEmpty name="splitAuditsListForm" property="timeMaker">
				<%-- graph cumulatif sur le temps --%>
				<bean:define id="srcTimeChart" name="splitAuditsListForm"
					property="timeMaker.srcName" type="String" />
				<bean:define id="imgTimeChart" name="splitAuditsListForm"
					property="timeMaker.useMapName" type="String" />
				<%-- ligne necessaire --%>
				<%=((GraphMaker) ((SplitAuditsListForm) (request.getSession().getAttribute("splitAuditsListForm"))).getTimeMaker()).getMapDescription()%>
				<html:img src="<%=srcTimeChart%>" usemap="<%=imgTimeChart%>"
					border="0" />
				<br />
				<br />
			</logic:notEmpty>
			<logic:notEmpty name="splitAuditsListForm" property="sizeMaker">
				<%-- graph non cumulatif sur la taille du file system --%>
				<bean:define id="srcSizeChart" name="splitAuditsListForm"
					property="sizeMaker.srcName" type="String" />
				<bean:define id="imgSizeChart" name="splitAuditsListForm"
					property="sizeMaker.useMapName" type="String" />
				<%-- ligne necessaire --%>
				<%=((GraphMaker) ((SplitAuditsListForm) (request.getSession().getAttribute("splitAuditsListForm"))).getSizeMaker()).getMapDescription()%>
				<html:img src="<%=srcSizeChart%>" usemap="<%=imgSizeChart%>"
					border="0" />
			</logic:notEmpty>
		</af:canvasCenter>
	</af:body>
</af:page>