<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.resources.WebMessages"%>
<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.results.ResultRulesCheckingForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.lang.String"%>
<%@ page import="java.util.ListIterator"%>
<%@ page import="org.squale.squaleweb.util.SqualeWebActionUtils"%>

<bean:define id="projectId" name="projectSummaryForm"
	property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"
	property="previousAuditId" type="String" />

<bean:define id="form" name="projectSummaryForm" property="results" />
<bean:define id="practiceName" name="form" property="name" type="String" />
<bean:define id="repartition" name="form" property="intRepartition"
	type="double[]" />
<bean:define id="transgressions" name="form" property="list"
	type="java.util.ArrayList" />
<bean:define id="version" value="" />
<bean:define id="infoForm" name="form" property="infoForm" />
<%
// On met le form dans la requête pour que l'inclusion
// de la page jsp puisse trouver le bean dans le scope request
request.setAttribute("practiceInformationForm", infoForm);
%>

<af:page titleKey="project.results.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<squale:tracker directWay="false" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />

		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="Consultation::Pratique" />
		</jsp:include>

		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="projectSummaryForm"
				displayComparable="true" />
			<br />
			<br />
			<h2><bean:message key="project.results.practice.subtitle"
				arg0="<%=WebMessages.getString(request, practiceName)%>" /></h2>
			<br />
			<table class="tblh" style="width: 20%">
				<thead>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th><bean:message key="project.result.practice.value" /></th>
						<td><squale:mark mark="currentMark" name="form" /> <squale:trend
							name="form" current="currentMark" predecessor="predecessorMark" /><squale:picto
							name="form" property="currentMark" /></td>
					</tr>
				</tbody>
			</table>
			<br />
			<br />

			<table border="0" cellspacing="0" class="tblh" style="width: 30%">
				<THEAD>
					<tr>
						<th class="sort" colspan="3">
						<div align="center"><bean:message key="distribution.title" /></div>
						</th>
					</tr>
				</THEAD>
				<tr>
					<td width="14">
					<div align="center"><img src="images/pictos/error.png"></div>
					</td>
					<td>
					<div align="left"><bean:message
						key="rulesChecking.rule.severity_error" /></div>
					</td>
					<td>
					<div align="right"><%=(int) repartition[ResultRulesCheckingForm.ERROR_INT]%>
					</div>
					</td>
				</tr>
				<tr>
					<td width="14">
					<div align="center"><img src="images/pictos/warning.png"></div>
					</td>
					<td>
					<div align="left"><bean:message
						key="rulesChecking.rule.severity_warning" /></div>
					</td>
					<td>
					<div align="right"><%=(int) repartition[ResultRulesCheckingForm.WARNING_INT]%>
					</div>
					</td>
				</tr>
				<tr>
					<td width="14">
					<div align="center"><img src="images/pictos/info.png"></div>
					</td>
					<td>
					<div align="left"><bean:message
						key="rulesChecking.rule.severity_info" /></div>
					</td>
					<td>
					<div align="right"><%=(int) repartition[ResultRulesCheckingForm.INFO_INT]%>
					</div>
					</td>
				</tr>
			</table>
			<br />
			<br />
			<logic:empty name="form" property="list">
				<bean:message key="project.result.practice.rulesChecking.noRule" />
			</logic:empty>
			<logic:notEmpty name="form" property="list">
				<h3><bean:message
					key="project.result.practice.rulesChecking.subtitle" /></h3>
				<br />
				<af:table name="form" property="list" totalLabelPos="none"
					emptyKey="table.results.none">
					<%String ruleSeverityKey = "rulesChecking.rule.severity_";%>
					<af:cols id="resultForm">
						<bean:define id="severity" name="resultForm" property="severity"
							type="String" />
						<bean:define id="ruleName" name="resultForm" property="nameRule"
							type="String" />
						<bean:define id="measureID" name="resultForm" property="measureID"
							type="Long" />
						<bean:define id="ruleID" name="resultForm" property="id"
							type="Long" />
						<%// Nom de la clé pour récupérer le nom de la règle
String ruleNameKey = "metric." + ruleName;
String dbMessage = WebMessages.getString(request, ruleNameKey);
// l'intitulé de la règle est affiché à la place du nom lorsqu'il existe
if (dbMessage != null) {
    ruleName = dbMessage;
}
%>
						<af:col property="nameRule"
							key="project.result.practice.rulesChecking.rule.name"
							sortable="true" type="String">
							<img
								src="<%=SqualeWebActionUtils.getImageForRuleSeverity(severity)%>"
								style="float: left" />
							<logic:equal name="resultForm" property="transgressionsNumber"
								value="0">
								<%=ruleName%>
							</logic:equal>
							<logic:greaterThan name="resultForm"
								property="transgressionsNumber" value="0">
								<%
// On construit le lien vers le détail de la transgression
String link =
    "project.do?action=ruleCheckingDetails&projectId="
        + projectId
        + "&currentAuditId="
        + currentAuditId
        + "&previousAuditId="
        + previousAuditId
        + "&measureId="
        + measureID.toString()
        + "&ruleId="
        + ruleID.toString()
        + "&ruleName="
        + ruleName
        + "&practiceName="
        + practiceName;%>
								<a href="<%=link%>"> <%=ruleName%> </a>
							</logic:greaterThan>
						</af:col>
						<af:col property="severityLang"
							key="project.result.practice.rulesChecking.rule.severity"
							sortable="true">
						</af:col>
						<af:col property="transgressionsNumber"
							key="project.result.practice.rulesChecking.rule.TransgressionNumber"
							sortable="true">
						</af:col>
					</af:cols>
				</af:table>
				<h2><bean:message key="exports.title" /></h2>
				<br />
				<af:buttonBar>
					<% String urlExportPDF = "project.do?action=exportTransgressionsToPDF&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;%>
					<af:button type="form" name="export.pdf"
						onclick="<%=\"javascript:xt_clic_AF_v2('T','Rapport::ListeTransgressionsPDF',null,null);location.href='\"+urlExportPDF+\"'\"%>"
						toolTipKey="toolTip.export.pdf.transgressions" />
				</af:buttonBar>
			</logic:notEmpty>
			<br />
			<br />

			<%-- On affiche la description de la pratique --%>
			<fieldset><legend><b><bean:message
				key="qualimetric_element.title" /></b></legend> <br />
			<jsp:include page="/jsp/results/project/information_common.jsp">
				<jsp:param name="expandedDescription" value="false" />
			</jsp:include> <br />
			</fieldset>

		</af:canvasCenter>
	</af:body>
</af:page>