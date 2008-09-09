<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants"%>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils"%>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>
<%@ page import="com.airfrance.welcom.taglib.table.InternalTableUtil" %>

<bean:define name="markForm" property="practiceId" id="practiceKey"
	type="String" />
<bean:define name="markForm" property="practiceName"
	id="practiceNameKey" type="String" />
<bean:define name="markForm" property="factorId" id="factorKey"
	type="String" />
<bean:define name="markForm" property="factorName" id="factorName"
	type="String" />
<bean:define name="markForm" property="minMark" id="minMark"
	type="Double" />
<bean:define name="markForm" property="maxMark" id="maxMark"
	type="Double" />
<bean:define name="markForm" property="markValue" id="markValue"
	type="Integer" />
<%
// Le nom de la pratique est récupérée en base
String practiceName = WebMessages.getString(request, practiceNameKey);
// Si les notes min et max sont égales, alors il s'agit d'un index
String minMarkMessage = WebMessages.getString(request, "project.results.mark.status_" + markValue.toString());
String intro = "project.results.mark.intro";
String maxMarkMessage = "";
String subTitleKey = "project.results.intro.subtitle";
// si les notes min et max sont différentes, alors il s'agit d'un intervalle de notes
if (minMark.compareTo(maxMark) != 0) {
    minMarkMessage = minMark.toString();
    maxMarkMessage = maxMark.toString();
    subTitleKey = "project.results.interval.subtitle";
    intro = "project.results.mark.interval.intro";
}
String directComponentWay = (String) session.getAttribute(SqualeWebConstants.TRACKER_BOOL);
%>

<bean:define id="projectId" name="markForm" property="projectId"
	type="String" />
<bean:define id="projectName" name="markForm" property="projectName"
	type="String" />
<bean:define id="currentAuditId" name="markForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="markForm"
	property="previousAuditId" type="String" />
<bean:define id="tres" name="markForm" property="treNames"
	type="java.util.List" />

<af:page titleKey="project.results.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<squale:tracker directWay="<%=directComponentWay%>"
			factorKey="<%=factorKey%>" practiceKey="<%=practiceKey%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" projectName="<%=projectName%>"
			projectId="<%=projectId%>" />

		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="Consultation::ListeComposant" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="projectSummaryForm"
				displayComparable="true" />
			<br />
			<h2><bean:message key="<%=subTitleKey%>"
				arg0="<%=minMarkMessage%>" arg1="<%=maxMarkMessage%>"
				arg2="<%=practiceName%>" /></h2>
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="<%=intro%>" arg0="<%=minMarkMessage%>"
					arg1="<%=practiceName%>"
					arg2='<%=WebMessages.getString(request, "component.max")%>'
					arg3="<%=maxMarkMessage%>" />
			</af:dropDownPanel>

			<br />
			<%-- les variables cachées pour les droits --%>
			<html:hidden name="markForm" property="projectId" />
			<html:hidden name="markForm" property="currentAuditId" />
			<html:hidden name="markForm" property="previousAuditId" />
			<af:table name="markForm" property="components" scope="session">
				<af:cols id="component">
					<bean:define id="fullName" name="component" property="fullName"
						type="String" />
					<bean:define id="name" name="component" property="name" type="String"/>
					<bean:define id="componentId" name="component" property="id"
						type="Long" />
					<%
							String urlComponent =  "project_component.do?action=component&projectId="
    												+ projectId
    												+ "&currentAuditId="
    												+ currentAuditId
    												+ "&previousAuditId="
    												+ previousAuditId
   													+ "&"
    												+ SqualeWebConstants.FROM_MARK_PAGE_KEY
    												+ "=true"
    												+"&component=" + componentId.toString();
						%>
					<af:col property="name" sortable="true" key="component.name"
						width="400px" contentTruncate="80">
						<a href="<%=urlComponent%>" title="<%=fullName%>">
							<%=InternalTableUtil.getTruncatedString(name, "80", "string")%></a>
					</af:col>
					<af:col property="metrics[0]" sortable="true"
						key="project.result.practice.value" type="NUMBER">
						<squale:mark name="component" mark="metrics[0]" />
					</af:col>
					<logic:iterate name="markForm" property="treNames" scope="session"
						id="tre" indexId="index" type="String">
						<logic:notEqual name="tre" value="tre.measure.mccabe.percent">
							<%
// l'index correspondant aux résultats du tre
int cpt = index.intValue() + 1;
// On va afficher en tooltip la signification du tre
// et mettre son acronyme comme nom de colonne
String shortTre = tre;
int lastDot = tre.lastIndexOf(".");
if (lastDot > 0) {
    shortTre = "<span title=\"" + WebMessages.getString(request, tre) + "\">" + tre.substring(lastDot + 1) + "</span>";
}%>
							<af:col key="<%=shortTre%>" sortable="true"
								property='<%="metrics[" + cpt + "]"%>' type="NUMBER">
								<logic:notEmpty name="component"
									property='<%="metrics[" + cpt + "]"%>'>
									<bean:define id="metric" name="component"
										property='<%="metrics[" + cpt + "]"%>' type="String" />
									<bean:define id="metricHtml"
										value='<%=metric.replaceAll("\\n", "<br/>")%>' />
									<bean:write name="metricHtml" filter="false" />
								</logic:notEmpty>
							</af:col>
						</logic:notEqual>
					</logic:iterate>
				</af:cols>
			</af:table>
			<%-- 
				Il ne faut pas mettre les boutons dans le form
				sinon l'action se termine mal (null si l'on veut invoquer un nouvel export
				sans recharger la page
			--%>
			<h2><bean:message key="exports.title" /></h2>
			<br />
			<af:buttonBar>
				<%
					String urlExportPDF = "mark.do?action=exportMarkToPDF&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
					String urlExportExcel = "mark.do?action=exportMarkToExcel&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
				%>
				<af:button type="form" name="export.pdf"
					onclick="<%=\"javascript:xt_clic_AF_v2('T','Rapport::DetailComposantPDF',null,null);location.href='\"+urlExportPDF+\"'\"%>"
					toolTipKey="toolTip.export.pdf.component" />
				<af:button type="form" name="export.excel"
					onclick="<%=\"javascript:xt_clic_AF_v2('T','Rapport::DetailComposantExcel',null,null);location.href='\"+urlExportExcel+\"'\"%>"
					toolTipKey="toolTip.export.excel.component" />
			</af:buttonBar>
			<br />
			<fieldset><legend><b><bean:message
				key="image.legend" /></b></legend> <%=SqualeWebActionUtils.getLegendForTres(request, tres)%>
			</fieldset>
		</af:canvasCenter>
	</af:body>
</af:page>