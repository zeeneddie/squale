<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<script type="text/javascript"
	src="theme/charte_v03_001/js/tagManagement.js"></script>
<script type="text/javascript" src="jslib/jquery.js"></script>

<%-- recupere le type de list dans le parametre kind du formulaire --%>
<bean:define name="splitAuditsListForm" property="kind" id="kind"
	type="String" />
<bean:define name="splitAuditsListForm" property="currentAuditId"
	id="currentAuditId" type="String" />
<bean:define name="splitAuditsListForm" property="previousAuditId"
	id="previousAuditId" type="String" />
<%
/* recuperation des differents "messages" de la jsp dépendant du type des audits */
// Le titre
String title = "application.results.audits." + kind + ".title";
// Les détails
String details = "application.results.audits." + kind + ".details";
// On construit le lien lorsqu'on cliquera sur un audit
String link = "audits.do?action=select&kind=" + kind + "&oldAudit=" + currentAuditId + "&oldPreviousAudit=" + previousAuditId;
%>


<bean:define id="applicationName" name="splitAuditsListForm"
	property="applicationName" type="String" />
<bean:define id="applicationId" name="splitAuditsListForm"
	property="applicationId" type="String" />

<af:page titleKey="application.results.title" subTitleKey="<%=title%>"
	accessKey="default">
	<af:body canvasLeftPageInclude="/jsp/canvas/application_menu.jsp">
		<af:canvasCenter>
			<br />
			<br />
			<squale:resultsHeader name="splitAuditsListForm" />
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<br />
				<bean:message key="<%=details%>" />
			</af:dropDownPanel>
			<div style="color: #f00"><html:errors /> <html:messages
				id="message" message="true">
				<bean:write name="message" />
			</html:messages></div>
			<br />
			<af:form action="audits.do" scope="session" method="POST"
				name="selectForm"
				type="org.squale.squaleweb.applicationlayer.formbean.component.AuditListForm">
				<input type="hidden" name="applicationId" value="<%=applicationId%>" />
				<input type="hidden" name="kind" value="<%=kind%>" />
				<af:table name="splitAuditsListForm" property="audits"
					totalLabelPos="none" emptyKey="table.results.none" scope="session">
					<af:cols id="element" selectable="true">
						<af:col property="realDate" key="audit.date" sortable="true"
							href="<%=link%>" paramName="element" paramId="currentAuditId"
							paramProperty="id" type="DATE" width="200px"
							dateFormatKey="datetime.format" />
						<af:col property="name" key="audit.label" sortable="true"
							href="<%=link%>" paramName="element" paramId="currentAuditId"
							paramProperty="id" width="250px" />
						<af:col property="type" key="audit.type" sortable="true"
							width="250px">
							<bean:message name="element" property="type" />
						</af:col>
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" callMethod="purge" name="audits.purger"
						toolTipKey="toolTip.audit.purger"
						messageConfirmationKey="audits_purge.confirm" accessKey="manager" />
					<logic:notEqual name="kind" value="failed">
						<af:button type="form" callMethod="select" name="validate"
							toolTipKey="toolTip.valider" />
					</logic:notEqual>
				</af:buttonBar>
			</af:form>
			<%-- On affiche un encadré pour expliquer la comparaison de deux audits sauf dans le cas des audits en échec non comparable --%>
			<logic:notEqual name="splitAuditsListForm" property="kind"
				value="failed">
				<fieldset><legend><img
					src="images/pictos/info.png" alt="info_image" style="float: left" />
				<b><bean:message
					key="application.results.audits.comparison.legend" /></b> </legend> <bean:message
					key="application.results.audits.comparison.details" /></fieldset>
			</logic:notEqual>
		</af:canvasCenter>
	</af:body>
</af:page>